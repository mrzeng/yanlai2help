package org.spring.springboot.domain;

public class PerIntegrationUser {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 描述
     */
    private String name;

    /**
     * 
     */
    private String rights;

    /**
     * 
     */
    private String roleId;

    /**
     * 最后登录时间
     */
    private String lastLogin;

    /**
     * 
     */
    private String ip;

    /**
     * 是否有效
     */
    private String status;

    /**
     * 
     */
    private String bz;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 
     */
    private String sfid;

    /**
     * 
     */
    private String startTime;

    /**
     * 
     */
    private String endTime;

    /**
     * 
     */
    private Integer years;

    /**
     * 手机号码
     */
    private String number;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String orgid;

    /**
     * 
     */
    private String titile;

    /**
     * 0男1女2未知
     */
    private String sex;

    /**
     * 
     */
    private String birthday;

    /**
     * 用户id
     * @return USER_ID 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 用户名
     * @return USERNAME 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 用户密码
     * @return PASSWORD 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 用户密码
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 描述
     * @return NAME 描述
     */
    public String getName() {
        return name;
    }

    /**
     * 描述
     * @param name 描述
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return RIGHTS 
     */
    public String getRights() {
        return rights;
    }

    /**
     * 
     * @param rights 
     */
    public void setRights(String rights) {
        this.rights = rights == null ? null : rights.trim();
    }

    /**
     * 
     * @return ROLE_ID 
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 
     * @param roleId 
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * 最后登录时间
     * @return LAST_LOGIN 最后登录时间
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * 最后登录时间
     * @param lastLogin 最后登录时间
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin == null ? null : lastLogin.trim();
    }

    /**
     * 
     * @return IP 
     */
    public String getIp() {
        return ip;
    }

    /**
     * 
     * @param ip 
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 是否有效
     * @return STATUS 是否有效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 是否有效
     * @param status 是否有效
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * @return BZ 
     */
    public String getBz() {
        return bz;
    }

    /**
     * 
     * @param bz 
     */
    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    /**
     * 电话号码
     * @return PHONE 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话号码
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 
     * @return SFID 
     */
    public String getSfid() {
        return sfid;
    }

    /**
     * 
     * @param sfid 
     */
    public void setSfid(String sfid) {
        this.sfid = sfid == null ? null : sfid.trim();
    }

    /**
     * 
     * @return START_TIME 
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime 
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    /**
     * 
     * @return END_TIME 
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime 
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    /**
     * 
     * @return YEARS 
     */
    public Integer getYears() {
        return years;
    }

    /**
     * 
     * @param years 
     */
    public void setYears(Integer years) {
        this.years = years;
    }

    /**
     * 手机号码
     * @return NUMBER 手机号码
     */
    public String getNumber() {
        return number;
    }

    /**
     * 手机号码
     * @param number 手机号码
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 
     * @return EMAIL 
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 
     * @return ORGID 
     */
    public String getOrgid() {
        return orgid;
    }

    /**
     * 
     * @param orgid 
     */
    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    /**
     * 
     * @return TITILE 
     */
    public String getTitile() {
        return titile;
    }

    /**
     * 
     * @param titile 
     */
    public void setTitile(String titile) {
        this.titile = titile == null ? null : titile.trim();
    }

    /**
     * 0男1女2未知
     * @return SEX 0男1女2未知
     */
    public String getSex() {
        return sex;
    }

    /**
     * 0男1女2未知
     * @param sex 0男1女2未知
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 
     * @return BIRTHDAY 
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 
     * @param birthday 
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }
}