package org.spring.springboot.zw.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lvhq
 * @date 2018.5.16
 * @jdk.version 1.8
 * @desc 征信查询服务常量类
 */
public class CreditConstants {
    /**
     * 密码修改原因
     */
    /*普通密码修改*/
    public static final String CHGPWD_TYP0 = "0";
    /*初始登录密码重置或者，密码过期密码修改*/
    public static final String CHGPWD_TYP1 = "1";

    /**
     *  审核配置表状态 0-启用 1-停用
     */
    public static final String BCM_FLOW_STS_START = "0";
    public static final String BCM_FLOW_STS_STOP = "1";

    /**
     * 接口申请是否使用本系统审批流程1-是 2-否
     */
    public static final String AUDIT_CHK_1 = "1";
    public static final String AUDIT_CHK_2 = "2";

    /**
     * 查询申请审批级别 00-查询申请不审批 01-查询申请一级审批 02-查询申请二级审批 03-查询申请三级审批
     */
    public static final String AUDIT_LEVEL_00 = "00";
    public static final String AUDIT_LEVEL_01 = "01";
    public static final String AUDIT_LEVEL_02 = "02";
    public static final String AUDIT_LEVEL_03 = "03";

    /**
     * 接口查询响应代码 00-成功  01-失败
     */
    public static final String API_RES_CODE_SUC = "00";
    public static final String API_RES_CODE_FAL = "01";

    /**
     * 字符集
     */
    public static final String CharSet_UTF_8 = "UTF-8";
    public static final String CharSet_GBK = "GBK";

    /**
     * 赋值常量
     */
    public static final String Value_0 = "0";
    public static final String Value_1 = "1";
    public static final int Value_0_Integer = 0;

    /**
     * 征信报告文件后缀名
     */
    public static final String Credit_Report_Suffix_Html = ".html";
    public static final String  Credit_Report_Suffix_Json= ".json";
    public static final String Credit_Report_Suffix_XLS = ".xls";
    public static final String Credit_Report_Suffix_ZIP = ".zip";

    /**
     * 系统查询请求配置表：查询方式，1-本地优先；2-仅查询本地；3-仅查询征信系统
     */
    public static final String BCM_REQCFG_QRY_TYPE_1 = "1";
    public static final String BCM_REQCFG_QRY_TYPE_2 = "2";
    public static final String BCM_REQCFG_QRY_TYPE_3 = "3";

    /**
     * 系统查询请求配置表：系统状态，0-启用；1-停用
     */
    public static final String BCM_REQCFG_STS_0 = "0";
    public static final String BCM_REQCFG_STS_1 = "1";

    /**
     * 人行账户管理表：系统状态，0-启用；1-停用；2-锁定；3-密码错误
     */
    public static final String BCM_ZXACCINFO_STS_0 = "0";
    public static final String BCM_ZXACCINFO_STS_1 = "1";
    public static final String BCM_ZXACCINFO_STS_2 = "2";
    public static final String BCM_ZXACCINFO_STS_3 = "3";

    /**
     * 人行账户管理表：00-个人征信查询,01-企业征信查询,10-个人征信报送,11-企业征信报送
     */
    public static final String[] BCM_ZXACCINFO_Busi_Type_Per = {"00", "10"};
    public static final String[] BCM_ZXACCINFO_Busi_Type_Ent = {"01", "11"};

    /**
     * 业务类型 00-个人征信查询  01-企业征信查询 99-其他
     *
     */
    public static final String BUSITYPE_PER = "00";
    public static final String BUSITYPE_ENT = "01";
    public static final String BUSITYPE_OTH = "99";
    /**
     * 信用报告查询方式 1-本地优先 2-仅查询本地 3-仅查询征信系统
     *
     */
    public static final List<String> rptQryTypeList = Arrays.asList(null,"","1","2","3");
    /**
     * 个人信用报告返回结果段落 a-个人基本信息 b-信息概要 c-信贷交易信息明细 d-公共信息明细 e-本人声明 f-异议标注 g-查询记录
     *
     */
    public static final List<String> perRptRstSegmentList = Arrays.asList(null,"","a","b","c","d","e","f","g");
    /**
     * 企业信用报告返回结果段落 a-基本信息 b-有直接关联关系的其他企业 c-财务报表 d-信息概要 e-信贷记录明细 f-公共记录明细 g-声明信息明细
     *
     */
    public static final List<String> entPptRstSegmentList = Arrays.asList(null,"","a","b","c","d","e","f","g");

    /**
     * 个人信用报告返回结果段落-全段
     */
    public static final String perRptRstAllSegment = "a|b|c|d|e|f|g";
    /**
     * 企业信用报告返回结果段落-全段
     */
    public static final String entRptRstAllSegment = "a|b|c|d|e|f|g";
    /**
     * 预警阻断类型 1-预警 2-阻断
     *
     */
    public static final String WARNSTOP_TYPE_1 = "1";
    public static final String WARNSTOP_TYPE_2 = "2";

    /**
     * 查询状态：1-未提交;2-待复核审批;3-复核审批拒绝;4-待查询;5-查询成功;6-查询失败;7-查无此人;8-弃用;9-已失效
     */
    public static final String Credit_Query_Sts_1 = "1";
    public static final String Credit_Query_Sts_2 = "2";
    public static final String Credit_Query_Sts_3 = "3";
    public static final String Credit_Query_Sts_4 = "4";
    public static final String Credit_Query_Sts_5 = "5";
    public static final String Credit_Query_Sts_6 = "6";
    public static final String Credit_Query_Sts_7 = "7";
    public static final String Credit_Query_Sts_8 = "8";
    public static final String Credit_Query_Sts_9 = "9";

    /**
     * 报告解析标识 1-解析 2-不解析
     */
    public static final String RPT_PARSEFLG_Y = "1";
    public static final String RPT_PARSEFLG_N = "2";

    /**
     * 解析状态：0-不解析;1-未解析;2-解析成功;3-解析失败;4-已失效
     */
    public static final String Credit_Parse_Sts_0 = "0";
    public static final String Credit_Parse_Sts_1 = "1";
    public static final String Credit_Parse_Sts_2 = "2";
    public static final String Credit_Parse_Sts_3 = "3";
    public static final String Credit_Parse_Sts_4 = "4";

    /**
     * 报告推送行内标识 1-不推送 2-推送
     */
    public static final String RPT_SNDBANKFLG_N = "1";
    public static final String RPT_SNDBANKFLG_Y = "2";
    /**
     * 推送行内状态 0-不推送 1-未推送 2-推送成功 3-推送失败
     */
    public static final String SEND_BANK_STS_0 = "0";
    public static final String SEND_BANK_STS_1 = "1";
    public static final String SEND_BANK_STS_2 = "2";
    public static final String SEND_BANK_STS_3 = "3";

    /**
     * 报告脱敏标识 1-脱敏 2-不脱敏
     */
    public static final String DESENSITIXEFLG_Y = "1";
    public static final String DESENSITIXEFLG_N = "2";

    /**
     * 脱敏状态 0-不脱敏 1-未脱敏 2-脱敏成功 3-脱敏失败
     */
    public static final String DESENSITIZE_STS_0 = "0";
    public static final String DESENSITIZE_STS_1 = "1";
    public static final String DESENSITIZE_STS_2 = "2";
    public static final String DESENSITIZE_STS_3 = "3";

    /**
     * 报告水印标识 1-有 2-无
     */
    public static final String WATERMARKFLG_Y = "1";
    public static final String WATERMARKFLG_N = "2";

    /**
     * 水印状态 0-不需要水印 1-添加水印成功 2-添加水印失败
     */
    public static final String WATERMARKFLG_STS_0 = "0";
    public static final String WATERMARKFLG_STS_1 = "1";
    public static final String WATERMARKFLG_STS_2 = "2";

    /**
     * 外接系统参数状态 0-启用 1-禁用 2-锁定
     */
    public static final String IOUTSYSCFG_STS_0 = "0";
    public static final String IOUTSYSCFG_STS_1 = "1";
    public static final String IOUTSYSCFG_STS_2 = "2";

    /**
     * 水印规则设置参数 00-个人信用报告 01-企业信用报告 02-企业信用报告二级链接
     */
    public static final String RULWATERMARKCFG_STS_00 = "00";
    public static final String RULWATERMARKCFG_STS_01 = "01";
    public static final String RULWATERMARKCFG_STS_02 = "02";

    /**
     * 企业二级报告脱敏标识 1-脱敏 2-不脱敏
     */
    public static final String QYSUBRPT_DESENSITIXEFLG_Y = "1";
    public static final String QYSUBRPT_DESENSITIXEFLG_N = "2";

    /**
     * 企业二级报告水印标识 1-有 2-无
     */
    public static final String QYSUBRPT_WATERMARKFLG_Y = "1";
    public static final String QYSUBRPT_WATERMARKFLG_N = "2";

    /**
     * 报告上报标识 1-不报送 2-报送
     */
    public static final String RPT_UPLOADFLG_N = "1";
    public static final String RPT_UPLOADFLG_Y = "2";

    /**
     * 上报状态 0-不报送 1-未报送 2-报送成功 3-报送失败
     */
    public static final String UPLOAD_STS_0 = "0";
    public static final String UPLOAD_STS_1 = "1";
    public static final String UPLOAD_STS_2 = "2";
    public static final String UPLOAD_STS_3 = "3";

    /**
     * 操作类型：1-预警阻断;2-查询;3-查阅
     */
    public static final String Credit_Action_Type_1 = "1";
    public static final String Credit_Action_Type_2 = "2";
    public static final String Credit_Action_Type_3 = "3";

    /**
     * 查询申请类型 01-人工申请 02-银行网银接口申请 03-银行手机银行接口申请 04-非银手机APP申请 05-非银网页申请
     */
    public static final String APP_TYPE_01 = "01";
    public static final String APP_TYPE_02 = "02";
    public static final String APP_TYPE_03 = "03";
    public static final String APP_TYPE_04 = "04";
    public static final String APP_TYPE_05 = "05";
    /**
     * 报告来源 1-前置系统 2-征信系统
     */
    public static final String RPT_SRC_1 = "1";
    public static final String RPT_SRC_2 = "2";
    /**
     * 查询记录类型 1-联机查询 2-手工导入
     */
    public static final String REC_TYPE_1 = "1";
    public static final String REC_TYPE_2 = "2";
    /**
     * 接入系统码  LOCAL-本地系统
     */
    public static final String SYS_CODE_LOCAL = "LOCAL";

    /**
     *授权书校验标识 1-是  2-否
     */
    public static final String AUTH_CHK_1 = "1";
    public static final String AUTH_CHK_2 = "2";

    /**
     *客户授权状态 1-无效 2-有效 3-失效 4-弃用
     */
    public static final String AUTH_STS_1 = "1";
    public static final String AUTH_STS_2 = "2";
    public static final String AUTH_STS_3 = "3";
    public static final String AUTH_STS_4 = "4";

    /**
     * 个人客户基本信息获取数据方式 1-手动 2-接口/批量
     */
    public static  final String GETDATA_TYPE_1 = "1";
    public static  final String GETDATA_TYPE_2 = "2";

    /**
     * 是否对账 1-不对账 2-对账
     */
    public static final String CHK_IN_SUC = "1";
    public static final String CHK_IN_FAL = "2";

    /**
     * 工作日数据字典 0-工作日  1-非工作日
     */
    public static final String WORKTIME_Y = "0";
    public static final String WORKTIME_N = "1";

    /**
     * 对账结果 0-不对账 1-未对账 2-已对账
     */
    public static final String CHK_IN_RST0 = "0";
    public static final String CHK_IN_RST1 = "1";
    public static final String CHK_IN_RST2 = "2";

    /**
     * 批量标识 Y-批量 N-单笔
     */
    public static final String BATCH_Y = "Y";
    public static final String BATCH_N = "N";

    /**
     * 批次处理状态 1-查询请求已接收 2-正在查询 3-查询已完成 4-结果待通知 5-结果已通知 6-结果已反馈 7-结果反馈失败
     */
    public static final String API_BATCH_STS_1 = "1";
    public static final String API_BATCH_STS_2 = "2";
    public static final String API_BATCH_STS_3 = "3";
    public static final String API_BATCH_STS_4 = "4";
    public static final String API_BATCH_STS_5 = "5";
    public static final String API_BATCH_STS_6 = "6";
    public static final String API_BATCH_STS_7 = "7";

    /**
     * 审批状态 1-未审批 2-审批通过 3-审批拒绝
     */
    public static final String AUDIT_STS_1 = "1";
    public static final String AUDIT_STS_2 = "2";
    public static final String AUDIT_STS_3 = "3";

    /**
     * 接口通讯响应码 000-成功  555-阻断 999-阻断
     */
    public static final String RESCODE_SUC = "000";
    public static final String RESCODE_FAL = "999";
    public static final String RESCODE_INTCEPT = "555";

    /**
     * 查询申请审批类型 01-人工审批 02-自动审批
     */
    public static final String AUDIT_TYPE_MANUAL = "01";
    public static final String AUDIT_TYPE_AUTO = "02";

    /**
     * 成功代码
     */
    public static final String Success = "000";
    public static final String INTERCEPT = "555";
    public static final String Failure = "999";

    /**
     *  状态 0-启用 1-停用
     */
    public static final String STS_RUNNING = "0";
    public static final String STS_STOP = "1";
    /**
     *  脱敏规则适用范围
     *  00-个人征信报告原版及解析版
     *  01-仅个人征信报告原版
     *  02-仅个人信用报告解析版
     *  10-企业征信报告原版及解析版
     *  11-仅企业征信报告原版
     *  12-仅企业信用报告解析版
     */
    public static final String USE_SCOPE_00 = "00";
    public static final String USE_SCOPE_01 = "01";
    public static final String USE_SCOPE_02 = "02";
    public static final String USE_SCOPE_10 = "10";
    public static final String USE_SCOPE_11 = "11";
    public static final String USE_SCOPE_12 = "12";

    public static final int Login_Success = 0;
    public static final int Login_Network_No = -1;
    public static final int Login_Status_Error = -2;
    public static final int Login_Forced_Change_Pwd_Error = 1;
    public static final int Login_Pwd_Expired = 10;
    public static final int Login_First_Use = 11;
    /**
     * 征信用户状态 0-启用 1-停用 2-锁定 3-密码错误
     */
    public static final String ZXUSER_STS_RUN = "0";
    public static final String ZXUSER_STS_STOP = "1";
    public static final String ZXUSER_STS_LOCKED = "2";
    public static final String ZXUSER_STS_PWDERR = "3";

    /**
     * 审批方式 01-人工审批 02-自动审批
     */
    public static final String APP_TYPE_MANUAL = "01";
    public static final String APP_TYPE_AUTO = "02";
    /**
     * 上报方式 1-手动
     * 2-自动"
     */
    public static final String UP_TYPE_MANUAL = "1";
    public static final String UP_TYPE_AUTO = "2";

    /**
     * 人行账号权限 0-查询 1-报送 2-异议 3-管理
     */
    public static final String ZX_RIGHT_0 = "0";
    public static final String ZX_RIGHT_1 = "1";
    public static final String ZX_RIGHT_2 = "2";
    public static final String ZX_RIGHT_3 = "3";
    /**
     * 个人征信查询类型 0-信息报告查询 1-身份信息核查
     */
    public static final String QRY_TYPE_0 = "0";
    public static final String QRY_TYPE_1 = "1";

    /**
     * 企业查询 是否为扩展页标识 Y-是  N-否
     */
    public static final String EXTENDFLAG_Y = "Y";
    public static final String EXTEND_FLAG_N = "N";

    /**
     * 加密状态 0-不加密 1-未加密 2-加密成功 3-加密失败
     */
    public static final String ENCRY_STS_0 = "0";
    public static final String ENCRY_STS_1 = "1";
    public static final String ENCRY_STS_2 = "2";
    public static final String ENCRY_STS_3 = "3";

    /**
     * 加压状态 0-不加压 1-未加压 2-加压成功 3-加压失败
     */
    public static final String COMPRESS_STS_0 = "0";
    public static final String COMPRESS_STS_1 = "1";
    public static final String COMPRESS_STS_2 = "2";
    public static final String COMPRESS_STS_3 = "3";

    /**
     * 加压加密标识
     * 1-不加压不加密
     * 2-不加压加密
     * 3-加压不加密
     * 4-加压加密
     */
    public static final String ENCRYCOMPRESS_FLG_1 = "1";
    public static final String ENCRYCOMPRESS_FLG_2 = "2";
    public static final String ENCRYCOMPRESS_FLG_3 = "3";
    public static final String ENCRYCOMPRESS_FLG_4 = "4";

    /**
     * 报文处理状态
     * 01-等待生成报文
     * 02-正在生成报文
     * 03-完成生成报文
     * 04-正在发送报文
     * 05-完成发送报文
     * 06-确认报文已接收
     * 07-结果报文已接收
     * 08-结果报文处理中
     * 09-处理成功
     * 10-处理失败
     */
    public static final String UPMSG_STS_01 = "01";
    public static final String UPMSG_STS_02 = "02";
    public static final String UPMSG_STS_03 = "03";
    public static final String UPMSG_STS_04 = "04";
    public static final String UPMSG_STS_05 = "05";
    public static final String UPMSG_STS_06 = "06";
    public static final String UPMSG_STS_07 = "07";
    public static final String UPMSG_STS_08 = "08";
    public static final String UPMSG_STS_09 = "09";
    public static final String UPMSG_STS_10 = "10";

    /**
     * 预警阻断拦截类型
     * 01-查询用户每日查询量阀值申请拦截
     * 02-查询用户非工作时间查询申请拦截
     * 03-查询用户非工作机查询申请拦截
     * 04-查询用户针对同一被查询人一天多次查询拦截
     * 05-非工作人员查询申请拦截
     * 06-无授权信息查询申请拦截
     * 07-授权信息不正确查询申请拦截
     * 99-其他异常查询申请拦截
     */
    public static final String WARNSTOPINTERCETP_TYPE_01="01";
    public static final String WARNSTOPINTERCETP_TYPE_02="02";
    public static final String WARNSTOPINTERCETP_TYPE_03="03";
    public static final String WARNSTOPINTERCETP_TYPE_04="04";
    public static final String WARNSTOPINTERCETP_TYPE_05="05";
    public static final String WARNSTOPINTERCETP_TYPE_06="06";
    public static final String WARNSTOPINTERCETP_TYPE_07="07";
    public static final String WARNSTOPINTERCETP_TYPE_99="99";

    /**
     * 定义与人行征信系统交互关键字：必须为此值，不能私自改变，除非人行系统发生变化
     */
    public static final String PBOC_Key_Per_Login_Half_URL = "logon.do?isDissentLogin=null";
    public static final String PBOC_Key_Per_Query_Half_URL = "queryAction.do";
    public static final String PBOC_Key_Ent_Login_Half_URL = "logon.do";
    public static final String PBOC_Key_Ent_Query_Half_URL = "newconfirmProfessionReport.do";
    public static final String PBOC_Key_Ent_Expend_Query_Half_URL = "professionReportExpend.do";
    public static final String PBOC_Key_NewLoginUpdPass_URL = "forceChangePasswordAction.do";
    public static final String PBOC_Key_UpdPass_URL = "changePasswordAction.do";

    public static final String PBOC_Key_Quit_System_URL = "quitSystemAction.do";

    // 登录相关
    public static final String PBOC_Key_userid = "userid";
    public static final String PBOC_Key_password = "password";
    public static final String PBOC_Key_orgCode = "orgCode";
    /**
     * 个人征信交互关键字
     */
    // 客户姓名
    public static final String PBOC_Key_username = "username";
    // 证件类型
    public static final String PBOC_Key_certype = "certype";
    // 证件号码
    public static final String PBOC_Key_cercode = "cercode";
    // 查询原因
    public static final String PBOC_Key_queryreason = "queryreason";
    // 查询类型：0-信用报告查询; 1-身份信息核查
    public static final String PBOC_Key_idauthflag = "idauthflag";
    // 信用报告版式
    public static final String PBOC_Key_vertype = "vertype";
    // ??? 查询个人征信时必须携带此参数，固定值为0
    public static final String PBOC_Key_policetype = "policetype";

    /**
     * 企业证件类型 1-机构信用码 2-统一社会信用码 3-组织机构代码 4-中征码
     */
    public static  final String ENT_CERTTYPE_1 = "1";
    public static  final String ENT_CERTTYPE_2 = "2";
    public static  final String ENT_CERTTYPE_3 = "3";
    public static  final String ENT_CERTTYPE_4 = "4";

    /**
     * 企业征信交互关键字
     */
    // 机构信用码
    public static final String PBOC_Key_Ent_creditcode = "creditcode";
    // 中征码
    public static final String PBOC_Key_Ent_loancardno = "loancardno";
    // 组织机构代码
    public static final String PBOC_Key_Ent_corpno = "corpno";
    // 查询原因
    public static final String PBOC_Key_Ent_searchreason = "searchreason";
    // 查询版式
    public static final String PBOC_Key_Ent_type = "type";
    public static final String PBOC_Key_Ent_searchType = "searchType";
    // 查询企业征信报告固定传输属性
    public static final String PBOC_Key_Ent_attribute = "attribute";
    public static final String PBOC_Key_Ent_radioValue = "radioValue";
    public static final String PBOC_Key_Ent_offline = "offline";
    public static final String PBOC_Key_Ent_offline_Value = "null";
    public static final String PBOC_Key_Ent_displayreason = "displayreason";


    /**
     * 企业征信展开查询交互关键字
     */
    public static final String PBOC_Key_Ent_Expend_reportcode = "reportcode";
    public static final String PBOC_Key_Ent_Expend_loancardcode = "loancardcode";
    public static final String PBOC_Key_Ent_Expend_financecode = "financecode";
    public static final String PBOC_Key_Ent_Expend_creditCode = "creditCode";
    public static final String PBOC_Key_Ent_Expend_borrowernamecn = "borrowernamecn";
    public static final String PBOC_Key_Ent_Expend_searchReason = "searchReason";
    public static final String PBOC_Key_Ent_Expend_searchReasonCode = "searchReasonCode";
    public static final String PBOC_Key_Ent_Expend_borrownatuCode = "borrownatuCode";
    public static final String PBOC_Key_Ent_Expend_crccode = "crccode";
    public static final String PBOC_Key_Ent_Expend_reqid = "reqid";
    public static final String PBOC_Key_Ent_Expend_szzkx = "sz_zk.x";
    public static final String PBOC_Key_Ent_Expend_szzky = "sz_zk.y";


    /**
     * 定义登陆之后修改密码相关key userid-账号 password-新密码 confirmpwd-确认密码 oldpassword-原密码
     */

    public static final String ZXUSER_USERIDTEXT = "useridtext";
    public static final String ZXUSER_USERID = "userid";
    public static final String ZXUSER_NEWPWD = "password";
    public static final String ZXUSER_CONFIRMPWD = "confirmpwd";
    public static final String ZXUSER_OLDPWD = "oldpassword";

    /**
     * 定义初始登录修改密码发送人行参数相关key B1-校验码 passwordflag-修改类型 newpasswordvalid-确认密码 oldpassword-原密码 newpassword-新密码
     */

    public static final String PASSWORD_FLAG_VALUE1 = "1";
    public static final String PASSWORD_FLAG = "passwordflag";
    public static final String OLDPASSWORD = "oldpassword";
    public static final String NEWPASSWORD = "newpassword";
    public static final String NEWPASSWORD_VALID = "newpasswordvalid";
    public static final String B1 = "B1";
    //人行变更密码必传字段值
    public static final String B1_VALUE = "%CC%E1++%BD%BB";
    /**
     * 定义上报管控相关KEY IUP_ORG_ID-接入机构个人征信机构代码
     *                  IUP_PBC_ID-金融机构代码
     *                  IUP_AREA_CODE-机构所属行政区划代码
     *                  IUP_ORG_TYPE-机构类型
     */
    public static final String IUP_ORG_ID = "IUP_ORG_ID";
    public static final String IUP_PBC_ID = "IUP_PBC_ID";
    public static final String IUP_AREA_CODE = "IUP_AREA_CODE";
    public static final String IUP_ORG_TYPE = "IUP_ORG_TYPE";
    public static final String INTERFACE_NOINTERCEPTTYPE = "INTERFACE_NOINTERCEPTTYPE";

    /**
     * 操作类型 01-调阅报告 02-删除报告
     */
    public static final String OPR_TYPE_VIEW = "01";
    public static final String OPR_TYPE_DEL = "02";

    /**
     * 企业信用报告二级链接添加键值
     */
    public static final String BINDING_ID = "bindingId";
    public static final String ENTCREDITINFO_ID = "entCreditinfoId";
    public static final String SCRIPT_CODE = "scriptCode";
    /**
     * 资产负债表
     */
    public static final String REPORT_TYPE = "reporttype";
    public static final String REPORT_TYPE_SUBSECTION = "reporttypesubsection";
    public static final String REPORT_YEAR = "reportyear";
    public static final String MAIN_CREDIT = "maincredit";
    public static final String BLOCK_CODE = "blockcode";
    public static final String REQ_FLAG = "reqFlag";
    public static final String BORROW_CODE = "borrowcode";
    public static final String SEARCH_REASON = "searchreason";
    public static final String SEARCH_REASON_CODE = "searchreasoncode";
    public static final String EDITION_TYPE = "editiontype";
    public static final String CREDIT_CODE = "creditCode";

    /**
     * 利润表
     */
    public static final String FINANCE_CODE =  "financecode";
    public static final String FINANCE_NAME =  "financename";
    public static final String PBOC_Key_Ent_loancardcode =  "loancardcode";

    /**
     * 担保 展期
     */
    public static final String LOANKIND =  "loankind";
    public static final String LOANCONTRACT_CODE =  "loancontractcode";
    public static final String TYPE =  "type";
    public static final String ID =  "id";
    public static final String LOANKIND_CODE =  "loankindcode";

    /**
     * 未结清正常票据贴现明细
     */
    public static final String REPORT_CODE =  "reportcode";
    public static final String KIND_TYPE =  "kindType";
    public static final String SOURCE =  "source";
    public static final String TOPORG =  "toporg";

    /**
     * 查看垫款
     */
    public static final String OPERATION_CODE = "operationcode";
    public static final String KIND = "kind";

    /**
     * 异地查询字段 1-本省  2-本市 3-外省
     */
    public static final String ADDRESS_FLG_1 = "1";
    public static final String ADDRESS_FLG_2 = "2";
    public static final String ADDRESS_FLG_3 = "3";

    /**
     * 证件类型  0-身份证
     */
    public static final String CUST_CERTTYPE_0 = "0";

    /**
     * 授权方式
     * 01-线下本人面签授权书
     * 11-线上有生物识别的电子签名授权
     * 12-线上无生物识别的电子签名授权
     * 19-线上其他授权方式
     */
    public static final String AUTH_TYPE_01 = "01";
    public static final String AUTH_TYPE_11 = "11";
    public static final String AUTH_TYPE_12 = "12";
    public static final String AUTH_TYPE_19 = "19";

    /**
     * 生物识别技术
     * 01-人脸识别
     * 02-指纹识别
     * 03-虹膜识别
     * 04-发音识别
     * 05-签名识别
     * 06-视网膜识别
     * 99-其他生物识别
     */
    public static final String BIOMETRICS_01 = "01";
    public static final String BIOMETRICS_02 = "02";
    public static final String BIOMETRICS_03 = "03";
    public static final String BIOMETRICS_04 = "04";
    public static final String BIOMETRICS_05 = "05";
    public static final String BIOMETRICS_06 = "06";
    public static final String BIOMETRICS_99 = "99";

    /**
     * 个人查询原因 01-贷后管理
     */
    public static final String PER_QUERYREASON_01 = "01";

    /**
     * 企业查询原因 03-贷后管理
     */
    public static final String ENT_QUERYREASON_03 = "03";
    /**
     * 客户简称
     */
    public static final String CUST_SHORTNAME = "CUST_SHORTNAME";
    /**
     * 广农商简称
     */
    public static final String GRCBANK = "GRCBANK";

    /**
     * json合并
     */
    public static final String JSONCOMBINE = "jsonCombine";

    public static final String USER_ID = "userId";
    public static final String ORG_CODE = "orgCode";
    public static final String SYS_USER_IP = "sysUserIp";
    public static final String SYS_USER_MAC = "sysUserMac";
}