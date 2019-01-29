package org.spring.springboot.domain;
/**
 * Title: State
 * Description:
 * @author wangyanlai
 * @version 2019年1月3日 下午7:03:31
 */
public class SysOperatelog {
    /**
     * 
     */
    private String logid;

    /**
     * 类型0新增;1修改;2删除;3审核;4登录;5学习;6查询;7选课;8培训班学习;9异常;10证书生成
     */
    private String logtype;

    /**
     * 操作类型：增删改查等
     */
    private String operatetype;

    /**
     * 功能模块枚举
     */
    private String moduletype;

    /**
     * 日志类型--BUSINESS:业务日志;SYSTEM:系统日志;OPERATE:操作日志
     */
    private String loggertype;

    /**
     * 
     */
    private String createtime;

    /**
     * 
     */
    private String ip;

    /**
     * 操作人
     */
    private String operater;

    /**
     * 操作对象
     */
    private String logname;

    /**
     * 
     * @return LOGID 
     */
    public String getLogid() {
        return logid;
    }

    /**
     * 
     * @param logid 
     */
    public void setLogid(String logid) {
        this.logid = logid == null ? null : logid.trim();
    }

    /**
     * 类型0新增;1修改;2删除;3审核;4登录;5学习;6查询;7选课;8培训班学习;9异常;10证书生成
     * @return LOGTYPE 类型0新增;1修改;2删除;3审核;4登录;5学习;6查询;7选课;8培训班学习;9异常;10证书生成
     */
    public String getLogtype() {
        return logtype;
    }

    /**
     * 类型0新增;1修改;2删除;3审核;4登录;5学习;6查询;7选课;8培训班学习;9异常;10证书生成
     * @param logtype 类型0新增;1修改;2删除;3审核;4登录;5学习;6查询;7选课;8培训班学习;9异常;10证书生成
     */
    public void setLogtype(String logtype) {
        this.logtype = logtype == null ? null : logtype.trim();
    }

    /**
     * 操作类型：增删改查等
     * @return OPERATETYPE 操作类型：增删改查等
     */
    public String getOperatetype() {
        return operatetype;
    }

    /**
     * 操作类型：增删改查等
     * @param operatetype 操作类型：增删改查等
     */
    public void setOperatetype(String operatetype) {
        this.operatetype = operatetype == null ? null : operatetype.trim();
    }

    /**
     * 功能模块枚举
     * @return MODULETYPE 功能模块枚举
     */
    public String getModuletype() {
        return moduletype;
    }

    /**
     * 功能模块枚举
     * @param moduletype 功能模块枚举
     */
    public void setModuletype(String moduletype) {
        this.moduletype = moduletype == null ? null : moduletype.trim();
    }

    /**
     * 日志类型--BUSINESS:业务日志;SYSTEM:系统日志;OPERATE:操作日志
     * @return LOGGERTYPE 日志类型--BUSINESS:业务日志;SYSTEM:系统日志;OPERATE:操作日志
     */
    public String getLoggertype() {
        return loggertype;
    }

    /**
     * 日志类型--BUSINESS:业务日志;SYSTEM:系统日志;OPERATE:操作日志
     * @param loggertype 日志类型--BUSINESS:业务日志;SYSTEM:系统日志;OPERATE:操作日志
     */
    public void setLoggertype(String loggertype) {
        this.loggertype = loggertype == null ? null : loggertype.trim();
    }

    /**
     * 
     * @return CREATETIME 
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * 
     * @param createtime 
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
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
     * 操作人
     * @return OPERATER 操作人
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 操作人
     * @param operater 操作人
     */
    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    /**
     * 操作对象
     * @return LOGNAME 操作对象
     */
    public String getLogname() {
        return logname;
    }

    /**
     * 操作对象
     * @param logname 操作对象
     */
    public void setLogname(String logname) {
        this.logname = logname == null ? null : logname.trim();
    }
}