**关于GitHub取消使用TLS1.0 1.1的解决方法(包含SourceTree)**


----------


github 2月1日 发布公告
[Weak cryptographic standards removal notice](https://githubengineering.com/crypto-removal-notice/)
大体意思就是说 TSL1.0 1.1不支持了  这直接导致原先使用该方式的程序猿无法进行正常提交了

    解决办法

 1. 更新git版本 [链接地址](https://git-scm.com/downloads)
 2. 检查本地TSL版本 并更新 `检查版本命令行：git config http.sslVersion ，更新命令行：git config --global http.sslVersion tlsv1.2`
 3. 到这一步就可以使用git本身的工具进行正常的push操作了
 4. 这里说一下SourceTree的更新方式 依次点击：  工具->选项->git->由原先的Use Embedded git（内置） 改为 Use System Git （电脑原版）参考链接：[配置更改链接](https://stackoverflow.com/questions/48944875/sourcetree-error1407742essl-routinesssl23-get-server-hellotlsv1-alert-protoc#)