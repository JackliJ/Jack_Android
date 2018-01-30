package jack.project.com.imdatautil.model;

/**
 * Create by www.lijin@foxmail.com on 2018/1/29 0029.
 * <br/>
 * 好友实体
 */

public class MMFrend {

    public int MFlogin_uid;//所属用户Id
    public int MFUid;//用户Id
    public int MFStoreId;//店铺id
    public String MFUUName;//环信用户名
    public String MFAvatar;//用户头像
    public String MFUserName;//用户昵称
    public int MFUnionLevel;//商盟等级
    public int MFVipLevel;//Vip等级 0.非vip    1.月vip 2.季vip 3.半年vip 4.年vip 5.十年
    public int MFAuthStatus;//实名认证 0.待认证 1.通过 2.未通过
    public int MFBusinessAuthStatus;//企业认证 0.待认证 1.通过 2.未通过
    public int MFGender;//性别 1.男 2.女
    public String MFOccupation;//行业
    public String MFUserNote;//备注
    public String MFBirthPeriod;//年龄段
    public String MFConstellation;//星座
    public String MFCompany;//公司名称
    public String MFPosition;// 就业岗位
    public String MFBgImage;//脉友背景图片
    public int MFIsSingle;//是否单身  1.单身 2.非单身 3.保密
    public String PinYin;//脉友排序

    public int getMFlogin_uid() {
        return MFlogin_uid;
    }

    public void setMFlogin_uid(int MFlogin_uid) {
        this.MFlogin_uid = MFlogin_uid;
    }

    public int getMFUid() {
        return MFUid;
    }

    public void setMFUid(int MFUid) {
        this.MFUid = MFUid;
    }

    public int getMFStoreId() {
        return MFStoreId;
    }

    public void setMFStoreId(int MFStoreId) {
        this.MFStoreId = MFStoreId;
    }

    public String getMFUUName() {
        return MFUUName;
    }

    public void setMFUUName(String MFUUName) {
        this.MFUUName = MFUUName;
    }

    public String getMFAvatar() {
        return MFAvatar;
    }

    public void setMFAvatar(String MFAvatar) {
        this.MFAvatar = MFAvatar;
    }

    public String getMFUserName() {
        return MFUserName;
    }

    public void setMFUserName(String MFUserName) {
        this.MFUserName = MFUserName;
    }

    public int getMFUnionLevel() {
        return MFUnionLevel;
    }

    public void setMFUnionLevel(int MFUnionLevel) {
        this.MFUnionLevel = MFUnionLevel;
    }

    public int getMFVipLevel() {
        return MFVipLevel;
    }

    public void setMFVipLevel(int MFVipLevel) {
        this.MFVipLevel = MFVipLevel;
    }

    public int getMFAuthStatus() {
        return MFAuthStatus;
    }

    public void setMFAuthStatus(int MFAuthStatus) {
        this.MFAuthStatus = MFAuthStatus;
    }

    public int getMFBusinessAuthStatus() {
        return MFBusinessAuthStatus;
    }

    public void setMFBusinessAuthStatus(int MFBusinessAuthStatus) {
        this.MFBusinessAuthStatus = MFBusinessAuthStatus;
    }

    public int getMFGender() {
        return MFGender;
    }

    public void setMFGender(int MFGender) {
        this.MFGender = MFGender;
    }

    public String getMFOccupation() {
        return MFOccupation;
    }

    public void setMFOccupation(String MFOccupation) {
        this.MFOccupation = MFOccupation;
    }

    public String getMFUserNote() {
        return MFUserNote;
    }

    public void setMFUserNote(String MFUserNote) {
        this.MFUserNote = MFUserNote;
    }

    public String getMFBirthPeriod() {
        return MFBirthPeriod;
    }

    public void setMFBirthPeriod(String MFBirthPeriod) {
        this.MFBirthPeriod = MFBirthPeriod;
    }

    public String getMFConstellation() {
        return MFConstellation;
    }

    public void setMFConstellation(String MFConstellation) {
        this.MFConstellation = MFConstellation;
    }

    public String getMFCompany() {
        return MFCompany;
    }

    public void setMFCompany(String MFCompany) {
        this.MFCompany = MFCompany;
    }

    public String getMFPosition() {
        return MFPosition;
    }

    public void setMFPosition(String MFPosition) {
        this.MFPosition = MFPosition;
    }

    public String getMFBgImage() {
        return MFBgImage;
    }

    public void setMFBgImage(String MFBgImage) {
        this.MFBgImage = MFBgImage;
    }

    public int getMFIsSingle() {
        return MFIsSingle;
    }

    public void setMFIsSingle(int MFIsSingle) {
        this.MFIsSingle = MFIsSingle;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }
}
