/**
 * <p>Description: </p>
 * <p>Title: OperateLog.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月9日 上午10:25:10</p>
 */
package org.spring.springboot.domain;

/**
 * Title: OperateLog  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月9日 上午10:25:10
 */
public class OperateLog {
    private String LOGID;// 日志id
	private String LOGNAME;// 日志描述
	private String LOGTYPE;// 日志类型0新增;1修改;2删除;3审核;4登录;5学习;6查询;7选课;8培训班学习;9异常;10证书生成
	private String CREATETIME;// 记录时间
	private String IP;// 记录id
	private String OPERATER;// 记录人
	private OperateTypeEnum OPERATETYPE;// 操作类型
	private ModuleTypeEnum MODULETYPE;// 所属模块
	private LogTypeEnum LOGGERTYPE;// 日志类型
	private String operatorName;// 操作人名称
	private String orgName;// 操作人所属部门

	public OperateLog() {
		super();
	}

	public OperateLog(String lOGID, String lOGNAME, String lOGTYPE,
			String cREATETIME, String iP, String oPERATER,
			OperateTypeEnum oPERATETYPE, ModuleTypeEnum mODULETYPE,
			LogTypeEnum lOGGERTYPE) {
		super();
		LOGID = lOGID;
		LOGNAME = lOGNAME;
		LOGTYPE = lOGTYPE;
		CREATETIME = cREATETIME;
		IP = iP;
		OPERATER = oPERATER;
		OPERATETYPE = oPERATETYPE;
		MODULETYPE = mODULETYPE;
		LOGGERTYPE = lOGGERTYPE;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLOGID() {
		return LOGID;
	}

	public void setLOGID(String lOGID) {
		LOGID = lOGID;
	}

	public String getLOGNAME() {
		return LOGNAME;
	}

	public void setLOGNAME(String lOGNAME) {
		LOGNAME = lOGNAME;
	}

	public String getLOGTYPE() {
		return LOGTYPE;
	}

	public void setLOGTYPE(String lOGTYPE) {
		LOGTYPE = lOGTYPE;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}

	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getOPERATER() {
		return OPERATER;
	}

	public void setOPERATER(String oPERATER) {
		OPERATER = oPERATER;
	}

	public OperateTypeEnum getOPERATETYPE() {
		return OPERATETYPE;
	}

	public void setOPERATETYPE(OperateTypeEnum oPERATETYPE) {
		OPERATETYPE = oPERATETYPE;
	}

	public ModuleTypeEnum getMODULETYPE() {
		return MODULETYPE;
	}

	public void setMODULETYPE(ModuleTypeEnum mODULETYPE) {
		MODULETYPE = mODULETYPE;
	}

	public LogTypeEnum getLOGGERTYPE() {
		return LOGGERTYPE;
	}

	public void setLOGGERTYPE(LogTypeEnum lOGGERTYPE) {
		LOGGERTYPE = lOGGERTYPE;
	}

	@Override
	public String toString() {
		return "OperateLog [LOGID=" + LOGID + ", LOGNAME=" + LOGNAME
				+ ", LOGTYPE=" + LOGTYPE + ", CREATETIME=" + CREATETIME
				+ ", IP=" + IP + ", OPERATER=" + OPERATER + ", OPERATETYPE="
				+ OPERATETYPE.getValue() + ", MODULETYPE="
				+ MODULETYPE.getValue() + ", LOGGERTYPE="
				+ LOGGERTYPE.getValue() + "]";
	}
}
