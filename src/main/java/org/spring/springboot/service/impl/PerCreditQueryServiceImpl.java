package org.spring.springboot.service.impl;

import com.easy521.base.mapper.BaseMapper;
import com.easy521.base.service.impl.AbstractBaseServiceImpl;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import net.transino.pbcrs.pbi.constant.CreditConstants;
import net.transino.pbcrs.pbi.constant.MessageCodeConstants;
import net.transino.pbcrs.pbi.dto.MessageResultDTO;
import net.transino.pbcrs.pbi.dto.ParseReportDTO;
import net.transino.pbcrs.pbi.model.BcmZxaccinfo;
import net.transino.pbcrs.pbi.model.RulDesensitizecfg;
import net.transino.pbcrs.pbi.model.RulWatermarkcfg;
import net.transino.pbcrs.pbi.parse.CreditReportCheck;
import net.transino.pbcrs.pbi.parse.per.PerCreditReportParse;
import net.transino.pbcrs.pbi.parse.per.PerJsonCombine;
import net.transino.pbcrs.pbi.service.BcmWarnStopCheckService;
import net.transino.pbcrs.pbi.service.BcmZxaccinfoService;
import net.transino.pbcrs.pbi.service.CmmCreditQueryService;
import net.transino.pbcrs.pbi.service.PerCreditQueryService;
import net.transino.pbcrs.pbi.utils.*;
import net.transino.pbcrs.qwf.mapper.PerCreditinfoMapper;
import net.transino.pbcrs.qwf.model.*;
import net.transino.pbcrs.qwf.service.PerCreditinfoService;
import net.transino.pbcrs.qwf.service.PerCreditviewService;
import net.transino.pbcrs.qwf.service.PerCustinfoService;
import net.transino.utils.exception.BusinessException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wangyanlai
 * @date 2018.8.20
 * @jdk.version 1.8
 * @desc 征信查询接口服务实现类（个人数据入库）
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PerCreditQueryServiceImpl extends AbstractBaseServiceImpl<PerCreditinfo, PerCreditinfoExample, String> implements PerCreditQueryService {

    private static final Logger logger = LoggerFactory.getLogger(PerCreditQueryServiceImpl.class);
    @Autowired
    PerCreditinfoMapper perCreditinfoMapper;
    @Autowired
    PerCreditRptDisplayBySegment perCreditRptDisplayBySegment;

    @Override
    public BaseMapper<PerCreditinfo, PerCreditinfoExample, String> getMapper() {
        return this.perCreditinfoMapper;
    }

    @Autowired
    private PerCustinfoService perCustinfoService;

    @Autowired
    EntCreditRptDisplayBySegment entCreditRptDisplayBySegment;
    @Autowired
    private PerCreditinfoService perCreditinfoService;

    @Autowired
    private PerCreditviewService perCreditviewService;

    @Autowired
    private CmmCreditQueryService cmmCreditQueryService;

    @Autowired
    private BcmZxaccinfoService bcmZxaccinfoService;

    @Autowired
    private PerCreditReportParse perCreditReportParse;

    @Autowired
    private CreditReportCheck creditReportCheck;

    @Autowired
    private BcmWarnStopCheckService bcmWarnStopCheckService;

    @Autowired
    private RulDesensitizeUtil rulDesensitizeUtil;

    private Map<String, Map<String, RulDesensitizecfg>> rulDesensitizecfg00Map = null;
    private Map<String, Map<String, RulDesensitizecfg>> rulDesensitizecfg01Map = null;
    private Map<String, Map<String, RulDesensitizecfg>> rulDesensitizecfg02Map = null;

    /**
     * 校验本地是否存在有效的个人征信报告
     *
     * @param perCreditinfo 个人征信申请查询信息
     * @return
     * @throws Exception
     */
    @Override
    public MessageResultDTO existPerCreditRpt(PerCreditinfo perCreditinfo,String orgCode) throws Exception {
        logger.info("PerCreditQueryServiceImpl.existPerCreditRpt Start ......");
        MessageResultDTO messageResultDTO = new MessageResultDTO();
        messageResultDTO.setCode(MessageCodeConstants.Success);
        // 1.判断是否为空
        if (perCreditinfo == null) {
            messageResultDTO.setCode(MessageCodeConstants.Failure);
            messageResultDTO.setMessage("个人征信查询申请对象为NULL！");
            return messageResultDTO;
        }
        //客户姓名
        String custName = perCreditinfo.getCustName();
        //证件类型
        String custCertype = perCreditinfo.getCustCertype();
        //证件号码
        String custCertno = perCreditinfo.getCustCertno();
        //查询状态
        String qrySts = CreditConstants.Credit_Query_Sts_5;
        PerCreditinfoExample perCreditinfoExample = new PerCreditinfoExample();
        perCreditinfoExample.setOrderByClause("RPT_QRYDT  DESC");
        PerCreditinfoExample.Criteria criteria = perCreditinfoExample.createCriteria();
        criteria.andCustNameEqualTo(custName);
        if (!StringUtils.isEmpty(orgCode)) {
            criteria.andAppUserdeptEqualTo(orgCode);
        }
        criteria.andCustCertypeEqualTo(custCertype);
        criteria.andCustCertnoEqualTo(custCertno);
        criteria.andQryStsEqualTo(qrySts);
        criteria.andRptSrcEqualTo(CreditConstants.RPT_SRC_2);

        PerCreditinfo qryPerCreditinfo = super.selectOneByExample(perCreditinfoExample);
        boolean existFlg = false;
        if (qryPerCreditinfo != null) {
            logger.info("PerCreditQueryServiceImpl.existPerCreditRpt qryPerCreditinfo=" + qryPerCreditinfo);
            String appDt = qryPerCreditinfo.getRptQrydt().substring(0, 10);
            int intervalDay = DateUtils.daysBetween(appDt, DateUtils.getTime(null));
            intervalDay += 1;
            logger.info("PerCreditQueryServiceImpl.existPerCreditRpt intervalDay=" + intervalDay);
            String sysCode = perCreditinfo.getSysCode();
            BcmReqcfg reqcfg = cmmCreditQueryService.getBcmReqcfgBySysCode(sysCode, messageResultDTO);

            if (reqcfg != null && (intervalDay <= reqcfg.getGrRptvalidday())) {
                messageResultDTO.setCode(MessageCodeConstants.Success);
                messageResultDTO.setMessage("查询成功！");
                messageResultDTO.setPerCreditinfo(qryPerCreditinfo);
                existFlg = true;
            }
        }
        if (!existFlg) {
            messageResultDTO.setCode(MessageCodeConstants.Failure);
            messageResultDTO.setMessage("本地不存在给定条件的个人信用报告");

        }
        logger.info("PerCreditQueryServiceImpl.existPerCreditRpt existFlg=" + existFlg);
        logger.info("PerCreditQueryServiceImpl.existPerCreditRpt End ......");
        return messageResultDTO;
    }

    /**
     * 显示个人征信报告
     *
     * @param perCreditinfo 个人征信申请查询信息
     * @return
     * @throws Exception
     */
    @Override
    public MessageResultDTO disPlayPerCreditRpt(PerCreditinfo perCreditinfo, String ip,String orgCode,String userId,String sysUserIp,String sysUserMac,String userName,String orgName) throws Exception {
        logger.info("PerCreditQueryServiceImpl.disPlayPerCreditRpt Start ......");
        MessageResultDTO messageResultDTO = new MessageResultDTO();
        // 1.判断是否为空
        if (perCreditinfo == null) {
            messageResultDTO.setCode(MessageCodeConstants.Failure);
            messageResultDTO.setMessage("个人征信查询申请对象NULL！");
            return messageResultDTO;
        }

        //预警阻断验证
      /*  List<String> interceptTypeList = new ArrayList();
        interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_04);
        interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_05);
        interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_06);
        String interceptDtl = bcmWarnStopCheckService.checkWarnStopIntercept(interceptTypeList,perCreditinfo);
        logger.info("PerCreditQueryServiceImpl.disPlayPerCreditRpt interceptDtl="+interceptDtl);
        if(!StringUtils.isEmpty(interceptDtl)){
            messageResultDTO.setCode(MessageCodeConstants.INTERCEPT);
            messageResultDTO.setMessage(interceptDtl);
            return messageResultDTO;
        }**/

        String recId = perCreditinfo.getId();
        logger.info("PerCreditQueryServiceImpl.disPlayPerCreditRpt recId=" + recId);
        PerCreditinfo qryPerCreditinfo = perCreditinfoService.selectByPrimaryKey(recId);
        String qrySts = qryPerCreditinfo.getQrySts();
        if (qryPerCreditinfo != null && (CreditConstants.Credit_Query_Sts_5.equals(qrySts) || CreditConstants.Credit_Query_Sts_7.equals(qrySts))) {
            String htmlFileName = qryPerCreditinfo.getRptPath().concat(File.separator).concat(recId).concat(CreditConstants.Credit_Report_Suffix_Html);
            String id = SecureUtil.simpleUUID();
            htmlFileName = htmlFileName.replaceAll("//", "/");
            String htmlStr = FileUtils.readFileToString(htmlFileName, null);
            BcmReqcfg bcmReqcfg = DataCacheUtils.getBcmReqcfgBySysCode(CreditConstants.SYS_CODE_LOCAL);
            String rootPath = bcmReqcfg.getPerrptPath();
            //解决页面乱码
            htmlStr = htmlStr.replaceAll("GBK", "UTF-8");

            Document document = Jsoup.parse(htmlStr);
            //屏蔽右键
            htmlStr = handleReportStrWinPrint(document);
            //水印处理
            htmlStr = handleReportStrWaterMark(document, htmlStr, perCreditinfo, ip,userName,orgName);

            ParseReportDTO parseReportDTO = this.parseAndGenerateFileOfPer(messageResultDTO, true, document, bcmReqcfg, perCreditinfo, htmlStr);
            logger.info("PerCreditQueryServiceImpl.disPlayPerCreditRpt id=" + id + ";rootPath=" + rootPath);
            //防止重复脱敏
            perCreditinfoService.updatePerCreditinfo(perCreditinfo,userName,orgCode);
            //登记查阅日志流水
            insertPerCreditview(perCreditinfo, id, rootPath, bcmReqcfg,orgCode,userId,sysUserIp,sysUserMac);
            messageResultDTO.setHtmlReportStr(htmlStr);
            messageResultDTO.setCode(MessageCodeConstants.Success);
            messageResultDTO.setMessage("查询成功！");
        } else {
            messageResultDTO.setCode(MessageCodeConstants.Failure);
            messageResultDTO.setMessage("本地不存在给定条件的个人信用报告");
        }
        logger.info("PerCreditQueryServiceImpl.disPlayPerCreditRpt code=" + messageResultDTO.getCode() +
                ";message=" + messageResultDTO.getMessage());
        logger.info("PerCreditQueryServiceImpl.disPlayPerCreditRpt End ......");
        return messageResultDTO;
    }


    /**
     * 查阅日志登记入库
     *
     * @param perCreditinfo
     * @param id
     * @param rootPath
     * @param bcmReqcfg
     */
    private void insertPerCreditview(PerCreditinfo perCreditinfo, String id, String rootPath, BcmReqcfg bcmReqcfg,String orgCode,String userId,String sysUserIp,String sysUserMac) throws Exception {
        logger.info("PerCreditQueryServiceImpl.insertPerCreditview Start ......");
        PerCreditview newCreditview = new PerCreditview();
        BeanUtils.copyProperties(perCreditinfo, newCreditview);
        newCreditview.setId(id);
        newCreditview.setAppType(CreditConstants.APP_TYPE_01);
        if (StringUtils.isEmpty(orgCode)) {
            orgCode = perCreditinfo.getAppUser();
        }

        if (StringUtils.isEmpty(userId)) {
            userId = perCreditinfo.getAppUserdept();
        }
        newCreditview.setRptUser(userId);//报告使用人
        newCreditview.setRptUserdept(orgCode);//报告使用人所属部门

        newCreditview.setRptSrc(CreditConstants.RPT_SRC_1);//报告来源
        newCreditview.setRptPath(rootPath + File.separator + DateUtils.getCurrentDate());//报告路径
        newCreditview.setRptQrydt(DateUtils.getTimeStamp(null));//报告查询日期
        newCreditview.setRecType(CreditConstants.REC_TYPE_1);//查询记录类型

        newCreditview.setZxUser(perCreditinfo.getZxUser());
        newCreditview.setZxUsername(perCreditinfo.getZxUsername());
        newCreditview.setRptNo(perCreditinfo.getRptNo());

        //判断是否解析
        if (CreditConstants.RPT_PARSEFLG_Y.equals(bcmReqcfg.getRptParseflg())) {
            newCreditview.setParseSts(CreditConstants.Credit_Parse_Sts_2); //解析成功
        } else {
            newCreditview.setParseSts(CreditConstants.Credit_Parse_Sts_0); //不解析
        }
        //是否推送行内
        if (CreditConstants.RPT_SNDBANKFLG_Y.equals(bcmReqcfg.getRptSndbankflg())) {
            newCreditview.setSendBankSts(CreditConstants.SEND_BANK_STS_1); //未推送
        } else {
            newCreditview.setParseSts(CreditConstants.SEND_BANK_STS_0);//不推送
        }

        //是否上报管控
        if (CreditConstants.RPT_UPLOADFLG_Y.equals(bcmReqcfg.getRptUploadflg())) {
            newCreditview.setUploadSts(CreditConstants.UPLOAD_STS_1); //未报送
        } else {
            newCreditview.setUploadSts(CreditConstants.UPLOAD_STS_0);//不报送
        }
        //是否已对账
        if (CreditConstants.CHK_IN_SUC.equals(bcmReqcfg.getChkIn())) {
            newCreditview.setChkRst(CreditConstants.CHK_IN_RST1); //未对账
        } else {
            newCreditview.setChkRst(CreditConstants.CHK_IN_RST0);//不对账
        }
        newCreditview.setEncrySts(CreditConstants.ENCRY_STS_0);//加密状态
        newCreditview.setCompressSts(CreditConstants.COMPRESS_STS_0);//加压状态

        newCreditview.setFeeType(bcmReqcfg.getFeeType());//费用类别
        newCreditview.setSysCode(CreditConstants.SYS_CODE_LOCAL);//接入系统码
        newCreditview.setActionType(CreditConstants.Credit_Action_Type_3);//操作类型
        newCreditview.setCreUser(userId);//创建人
        newCreditview.setCreUserdept(orgCode);//创建人所属机构
        newCreditview.setAppUser(userId);//查询申请人
        newCreditview.setAppDt(DateUtils.getTimeStamp(null));//查询申请日期
        newCreditview.setCreDt(DateUtils.getTimeStamp(null));//创建日期
        logger.info("操作用户Ip:" + sysUserIp + "|mac:" + sysUserMac);
        newCreditview.setRptuserIp(sysUserIp);
        newCreditview.setRptuserMac(sysUserMac);

        perCreditviewService.insert(newCreditview);
        //查阅日志上报流水登记
        cmmCreditQueryService.insertIupReviewlogupload(newCreditview);
        logger.info("PerCreditQueryServiceImpl.insertPerCreditview End ......");
    }

    @Override
    public MessageResultDTO queryPerCredit(PerCreditinfo perCreditinfo,Map<String,String> paramMap) throws Exception {

        logger.info("PerCreditQueryServiceImpl.queryPerCredit Start ......");
        long queryTime = System.currentTimeMillis();
        logger.info("查询开始时间" + DateUtils.getCurrentMillTime());
        logger.info("查询开始时间" + DateUtils.getCurrentMillTime());
        String errInfo = null;
        MessageResultDTO messageResultDTO = new MessageResultDTO();
        messageResultDTO.setCode(MessageCodeConstants.Failure);
        String remark33 = "a|e|f|g|b|c|d|";
        String Path = "/Users/apple/Documents/shidaizhengbang/切片工作/切片/切片/企业报文json.json";
        FileInputStream fileInputStream = new FileInputStream(Path);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
        //切片json文件生成路径
        BufferedReader reader = null;
        reader = new BufferedReader(inputStreamReader);
        String jsonStr = reader.readLine();
        //具体切片处理
        //权限字段remark33
        //json流inputStreamReader
        //返回租转好的html流文件String
        String allhtml = "";
        String alljson = "";
        List<String> returnlist = entCreditRptDisplayBySegment.getDisplaySegment(remark33, jsonStr);
        if(returnlist.size() == 2){
            allhtml = returnlist.get(0);
            alljson = returnlist.get(1);
        }
        String fileame = "Allnew" + ".html";
        fileame = "/Users/apple/Documents/shidaizhengbang/pbcrs/pbcrs-control/src/main/webapp/htmltemple/" + fileame;// 生成的html文件保存路径。
        System.out.print("文件输出路径:" + fileame);
        System.out.print("writeStringToFile");
        FileUtils.writeStringToFile(fileame, allhtml);
        if (true) {
            return messageResultDTO;
        }
         errInfo = null;
         messageResultDTO = new MessageResultDTO();
        messageResultDTO.setCode(MessageCodeConstants.Failure);
        messageResultDTO.setPerCreditinfo(perCreditinfo);
        messageResultDTO.setQrySts(perCreditinfo.getQrySts());

        if (CreditConstants.QRY_TYPE_1.equals(perCreditinfo.getQryType())) {
            errInfo = "系统暂不支持身份信息核查！";
            logger.info(errInfo);
            // 更新个人征信查询申请信息
            perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
            perCreditinfo.setErrInfo(errInfo);
            perCreditinfoService.updateByPrimaryKey(perCreditinfo);
            messageResultDTO.setCode(MessageCodeConstants.Failure);
            messageResultDTO.setQrySts(perCreditinfo.getQrySts());
            messageResultDTO.setPerCreditinfo(perCreditinfo);
            messageResultDTO.setMessage(errInfo);
            return messageResultDTO;
        }

        logger.info("PerCreditQueryServiceImpl.queryPerCredit perCreditinfo.id=" + perCreditinfo.getId());
        String sysCode = perCreditinfo.getSysCode();
        if (StringUtils.isBlank(sysCode)) {
            errInfo = "查询个人征信：系统代码不允许为空！";
            logger.info(errInfo);
            // 更新个人征信查询申请信息
            perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
            perCreditinfo.setErrInfo(errInfo);
            perCreditinfoService.updateByPrimaryKey(perCreditinfo);
            messageResultDTO.setCode(MessageCodeConstants.Failure);
            messageResultDTO.setQrySts(perCreditinfo.getQrySts());
            messageResultDTO.setPerCreditinfo(perCreditinfo);
            messageResultDTO.setMessage(errInfo);
            return messageResultDTO;
        }
        /**
         * 2.根据系统ID获取系统配置信息
         */
        BcmReqcfg bcmReqcfg = cmmCreditQueryService.getBcmReqcfgBySysCode(sysCode, messageResultDTO);
        if (bcmReqcfg == null) {
            errInfo = "查询个人征信：系统公共配置信息不存在！";
            logger.info(errInfo);
            perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
            perCreditinfo.setErrInfo(errInfo);
            perCreditinfoService.updateByPrimaryKey(perCreditinfo);
            messageResultDTO.setQrySts(perCreditinfo.getQrySts());
            messageResultDTO.setPerCreditinfo(perCreditinfo);
            messageResultDTO.setMessage(errInfo);
            return messageResultDTO;
        }
        //预警阻断验证

        String batchIreqid = perCreditinfo.getBatchIreqid();
        String interfaceNoInteceptCfg = null;
        boolean delInterceptFlg = false;
        if (StringUtils.isNotBlank(batchIreqid)) {
            interfaceNoInteceptCfg = DataCacheUtils.getSysDictMapByaramDesc(CreditConstants.INTERFACE_NOINTERCEPTTYPE).get(CreditConstants.INTERFACE_NOINTERCEPTTYPE);
            if (StringUtils.isNotBlank(interfaceNoInteceptCfg)) {
                delInterceptFlg = true;
            } else {
                delInterceptFlg = false;
            }
        } else {
            delInterceptFlg = false;
        }
        List<String> interceptTypeList = new ArrayList();
        if (delInterceptFlg) {
            if (!interfaceNoInteceptCfg.contains(CreditConstants.WARNSTOPINTERCETP_TYPE_01)) {
                interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_01);
            }
            if (!interfaceNoInteceptCfg.contains(CreditConstants.WARNSTOPINTERCETP_TYPE_02)) {
                interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_02);
            }
            if (!interfaceNoInteceptCfg.contains(CreditConstants.WARNSTOPINTERCETP_TYPE_04)) {
                interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_04);
            }
            if (!interfaceNoInteceptCfg.contains(CreditConstants.WARNSTOPINTERCETP_TYPE_05)) {
                interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_05);
            }
            if (!interfaceNoInteceptCfg.contains(CreditConstants.WARNSTOPINTERCETP_TYPE_06)) {
                interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_06);
            }
        } else {
            interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_01);
            interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_02);
            interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_04);
            interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_05);
            interceptTypeList.add(CreditConstants.WARNSTOPINTERCETP_TYPE_06);
        }
        if (interceptTypeList.size() > 0) {
            String interceptDtl = bcmWarnStopCheckService.checkWarnStopIntercept(interceptTypeList, perCreditinfo);
            logger.info("PerCreditQueryServiceImpl.queryPerCredit interceptDtl=" + interceptDtl);
            if (!StringUtils.isEmpty(interceptDtl)) {
                errInfo = interceptDtl;
                logger.info(errInfo);
                // 更新个人征信查询申请信息
                logger.info("操作用户Ip:" + paramMap.get(CreditConstants.SYS_USER_IP) + "|mac:" + paramMap.get(CreditConstants.SYS_USER_MAC));
                perCreditinfo.setRemark1(paramMap.get(CreditConstants.SYS_USER_IP));
                perCreditinfo.setRemark2(paramMap.get(CreditConstants.SYS_USER_MAC));
                perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
                perCreditinfo.setErrInfo(errInfo);
                perCreditinfoService.updateByPrimaryKey(perCreditinfo);
                messageResultDTO.setCode(MessageCodeConstants.INTERCEPT);
                messageResultDTO.setQrySts(perCreditinfo.getQrySts());
                messageResultDTO.setPerCreditinfo(perCreditinfo);
                messageResultDTO.setMessage(errInfo);
                return messageResultDTO;
            }
        }

        /**
         * 3.查询个人征信报告
         */
        String qrySts = perCreditinfo.getQrySts();
        perCreditinfo.setCustName(perCreditinfo.getCustName().trim());
        logger.info("PerCreditQueryServiceImpl.queryPerCredit qrySts=" + qrySts + ";appUser=" + perCreditinfo.getAppUser());
        // 4-待查询, 6-查询失败
        if ((CreditConstants.Credit_Query_Sts_4).equals(qrySts) || (CreditConstants.Credit_Query_Sts_6).equals(qrySts)) {
            String rptPath = null;
            String currentDate = null;
            BcmZxaccinfo bcmZxaccinfo = DataCacheUtils.getBcmZxaccinfoByAppUser(CreditConstants.BUSITYPE_PER, perCreditinfo.getAppUser());
            //校验征信账号
            boolean zxcxUserChkFlg = cmmCreditQueryService.checkZxcxUser(messageResultDTO, bcmZxaccinfo);
            if (!zxcxUserChkFlg) {
                errInfo = messageResultDTO.getMessage();
                perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
                perCreditinfo.setErrInfo(errInfo);
                perCreditinfoService.updateByPrimaryKey(perCreditinfo);
                messageResultDTO.setQrySts(CreditConstants.Credit_Query_Sts_6);
                messageResultDTO.setPerCreditinfo(perCreditinfo);
                return messageResultDTO;
            }

            boolean simulatePbcFlg = cmmCreditQueryService.simulatePbcQuery();
            boolean localQryFlg = false;
            boolean localQryFalFlg = false;

            logger.info("PerCreditQueryServiceImpl.queryPerCredit simulatePbcFlg=" + simulatePbcFlg);
            logger.info("PerCreditQueryServiceImpl.queryPerCredit batchIreqid=" + batchIreqid);
            if (StringUtils.isNotBlank(batchIreqid)) {
                String qryType = perCreditinfo.getRemark4();
                if(StringUtils.isEmpty(qryType)){
                    qryType = bcmReqcfg.getQryType();
                }
                logger.info("PerCreditQueryServiceImpl.queryPerCredit qryType=" + qryType);
                if (CreditConstants.BCM_REQCFG_QRY_TYPE_1.equals(qryType)) {//1-本地优先
                    messageResultDTO = existPerCreditRpt(perCreditinfo,paramMap.get(CreditConstants.ORG_CODE));
                    if (MessageCodeConstants.Success.equals(messageResultDTO.getCode())) {
                        perCreditinfo.setRemark3(messageResultDTO.getPerCreditinfo().getId());
                    }
                } else if (CreditConstants.BCM_REQCFG_QRY_TYPE_2.equals(qryType)) {//2-仅查询本地
                    messageResultDTO = existPerCreditRpt(perCreditinfo,paramMap.get(CreditConstants.ORG_CODE));
                    if (MessageCodeConstants.Success.equals(messageResultDTO.getCode())) {
                        perCreditinfo.setRemark3(messageResultDTO.getPerCreditinfo().getId());
                    } else {
                        localQryFlg = true;
                        localQryFalFlg = true;
                    }
                } else {//仅查询人行
                    simulatePbcFlg = false;
                    localQryFlg = false;
                }
            }

            String custCertno = perCreditinfo.getCustCertno();
            /*if(CreditConstants.CUST_CERTTYPE_0.equals(custCertno)){//身份证
                String areaFlg = DataCacheUtils.perAreaChk(perCreditinfo.getAppUser(),custCertno);
                perCreditinfo.setAddressFlg(areaFlg);
            }*/
            String htmlStr = null;

            String remark3 = perCreditinfo.getRemark3();

            logger.info("PerCreditQueryServiceImpl.queryPerCredit remark3=" + remark3);

            if (!StringUtils.isEmpty(remark3)) {//从本地获取征信报告
                PerCreditinfo localCreditInfo = perCreditinfoService.selectByPrimaryKey(remark3);
                String htmlFileName = localCreditInfo.getRptPath().concat(File.separator).concat(localCreditInfo.getId()).concat(CreditConstants.Credit_Report_Suffix_Html);
                htmlFileName = htmlFileName.replaceAll("//", "/");
                htmlStr = FileUtils.readFileToString(htmlFileName, null);
                localQryFlg = true;
            }

            logger.info("PerCreditQueryServiceImpl.queryPerCredit localQryFlg=" + localQryFlg);
            logger.info("PerCreditQueryServiceImpl.queryPerCredit simulatePbcFlg=" + simulatePbcFlg);
            if (simulatePbcFlg || localQryFlg) {
                if (!localQryFalFlg) {
                    try {
                        if (!localQryFlg) {
                            String htmlPath = bcmReqcfg.getGrZxurlbase();
                            File file = new File(htmlPath);
                            File [] fList = file.listFiles();
                            for(File temp :fList){
                                if(temp.getName().startsWith(perCreditinfo.getCustCertno())) {
                                    FileInputStream fis = new FileInputStream(temp);
                                    htmlStr = FileUtils.streamToString(fis);
                                    fis.close();
                                    break;
                                }
                            }
                        }
                        if(htmlStr == null){
                            errInfo = "本地不存在给定条件的征信报告";

                            perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
                            perCreditinfo.setErrInfo(errInfo);
                            perCreditinfoService.updateByPrimaryKey(perCreditinfo);

                            messageResultDTO.setQrySts(CreditConstants.Credit_Query_Sts_6);
                            messageResultDTO.setMessage(errInfo);
                            messageResultDTO.setPerCreditinfo(perCreditinfo);
                            return messageResultDTO;
                        }

                        perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_5);
                        messageResultDTO.setCode(MessageCodeConstants.Success);
                        messageResultDTO.setQrySts(perCreditinfo.getQrySts());
                        messageResultDTO.setMessage("查询成功");
                        messageResultDTO.setHtmlReportStr(htmlStr);
                        messageResultDTO.setPerCreditinfo(perCreditinfo);

                        String basePath = bcmReqcfg.getPerrptPath();
                        String curDate = DateUtils.getCurrentDate();
                        if (basePath.endsWith("/") || basePath.endsWith("\\")) {
                            rptPath = basePath.concat(curDate);
                        } else {
                            rptPath = basePath.concat(File.separator).concat(curDate);
                        }
                        Document document = Jsoup.parse(htmlStr);
                        //打印及屏蔽右键处理
//                    htmlStr = handleReportStrWinPrint(document);
                        //水印处理
//                    htmlStr = handleReportStrWaterMark(document,htmlStr,perCreditinfo);
                        ParseReportDTO parseReportDTO = this.parseAndGenerateFileOfPer(messageResultDTO, false, document, bcmReqcfg, perCreditinfo, htmlStr);

                    } catch (Exception e) {
                        logger.info("出现异常："+e);
                        errInfo = "查询出现异常，请稍后重试";
                        messageResultDTO.setCode(MessageCodeConstants.Failure);
                        messageResultDTO.setMessage(errInfo);
                        if (CreditConstants.Credit_Query_Sts_4.equals(perCreditinfo.getQrySts())) {
                            messageResultDTO.setQrySts(CreditConstants.Credit_Query_Sts_6);
                        } else {
                            messageResultDTO.setQrySts(perCreditinfo.getQrySts());
                        }
                        messageResultDTO.setPerCreditinfo(perCreditinfo);
                        logger.info(e.toString());
                    }
                } else {
                    updateLocalQry(perCreditinfo, bcmReqcfg);
                }

                perCreditinfo.setRptSrc(CreditConstants.RPT_SRC_1);
                logger.info("PerCreditQueryServiceImpl.queryPerCredit cmmCreditQueryService.simulatePbcQuery()=true End");

            } else {
                logger.info("PerCreditQueryServiceImpl.queryPerCredit 人行征信查询 Start");
                try {
                    //登陆人行征信系统
                    HttpClientUtils hclient = new HttpClientUtils();
                    HttpResponse hLoginResponse = loginPbcPer(hclient, bcmReqcfg, bcmZxaccinfo);

                    //校验登录信息
                    messageResultDTO = cmmCreditQueryService.loginPBOCCheck(hLoginResponse, bcmZxaccinfo, bcmReqcfg, CreditConstants.BUSITYPE_PER);

                    if (messageResultDTO.getCode().equals(MessageCodeConstants.Success)) {
                        logger.info("PerCreditQueryServiceImpl.queryPerCredit 登陆成功 Start");
                        String cookie = hLoginResponse.getCookies();
                        HttpClientUtils.setCookies(bcmZxaccinfo.getZxUser().concat("per"), cookie);

                        HttpResponse hMastResponse = masterSendQryApp(hclient, bcmReqcfg, bcmZxaccinfo, perCreditinfo);

                        if (hMastResponse != null && hMastResponse.isSuccess()) {
                            String qryReportStr = (String) hMastResponse.getData();
                            ParseReportDTO checkParseDTO = creditReportCheck.reportCheck(qryReportStr);
                            String checkCode = checkParseDTO.getCode();
                            String newQrySts = checkParseDTO.getQrySts();
                            String checkMsg = checkParseDTO.getMessage();

                            messageResultDTO.setCode(checkCode);
                            messageResultDTO.setQrySts(newQrySts);
                            messageResultDTO.setMessage(checkMsg);
                            messageResultDTO.setPerCreditinfo(perCreditinfo);

                            logger.info("PerCreditQueryServiceImpl.queryPerCredit checkCode=" + checkCode + ";newQrySts=" + newQrySts + ";checkMsg=" + checkMsg);
                            perCreditinfo.setQrySts(newQrySts);
                            perCreditinfo.setErrInfo(checkMsg);

                            Document document = Jsoup.parse(qryReportStr);
                            //打印及屏蔽右键处理
//                            qryReportStr = handleReportStrWinPrint(document);
                            //水印处理
//                            qryReportStr = handleReportStrWaterMark(document,qryReportStr,perCreditinfo);
                            if (CreditConstants.Credit_Query_Sts_5.equals(newQrySts) || CreditConstants.Credit_Query_Sts_7.equals(newQrySts)) {
                                perCreditinfo.setQrySts(newQrySts);
                                ParseReportDTO parseReportDTO = this.parseAndGenerateFileOfPer(messageResultDTO, false, document, bcmReqcfg, perCreditinfo, qryReportStr);

                                String basePath = bcmReqcfg.getPerrptPath();
                                String curDate = DateUtils.getCurrentDate();
                                if (basePath.endsWith("/") || basePath.endsWith("\\")) {
                                    rptPath = basePath.concat(curDate);
                                } else {
                                    rptPath = basePath.concat(File.separator).concat(curDate);
                                }
                                messageResultDTO.setMessage("征信报告查询成功！");
                            }
                        } else {
                            logger.info("  perCreditinfo.setQrySts" + perCreditinfo.getQrySts());
                            errInfo = "查询失败";
                            perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
                            messageResultDTO.setCode(MessageCodeConstants.Failure);
                            if (CreditConstants.Credit_Query_Sts_4.equals(perCreditinfo.getQrySts())) {
                                messageResultDTO.setQrySts(CreditConstants.Credit_Query_Sts_6);
                            } else {
                                messageResultDTO.setQrySts(perCreditinfo.getQrySts());
                            }
                            messageResultDTO.setPerCreditinfo(perCreditinfo);
                            messageResultDTO.setMessage(errInfo);
                        }
                    } else {
                        logger.info("  perCreditinfo.setQrySts" + perCreditinfo.getQrySts());

                        perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
                        perCreditinfo.setErrInfo(errInfo);
                        perCreditinfoService.updateByPrimaryKey(perCreditinfo);
                        messageResultDTO.setQrySts(CreditConstants.Credit_Query_Sts_6);
                        messageResultDTO.setPerCreditinfo(perCreditinfo);
                        return messageResultDTO;
                    }
                    logger.info("  perCreditinfo.setQrySts" + perCreditinfo.getQrySts());

                    logger.info("PerCreditQueryServiceImpl.queryPerCredit 人行征信查询 End");

                } catch (Exception e) {
                    logger.info("出现异常："+e);
                    errInfo = "查询出现异常，请稍后重试";
                    messageResultDTO.setCode(MessageCodeConstants.Failure);
                    messageResultDTO.setMessage(errInfo);
                    if (CreditConstants.Credit_Query_Sts_4.equals(perCreditinfo.getQrySts())) {
                        messageResultDTO.setQrySts(CreditConstants.Credit_Query_Sts_6);
                    } else {
                        messageResultDTO.setQrySts(perCreditinfo.getQrySts());
                    }
                    messageResultDTO.setPerCreditinfo(perCreditinfo);
                    logger.info(e.toString());
                }
                perCreditinfo.setRptSrc(CreditConstants.RPT_SRC_2);
            }
            currentDate = DateUtils.getTimeStamp(null);
            String userId = null;
            String orgCode = null;
            if (StringUtils.isEmpty(paramMap.get(CreditConstants.USER_ID))) {
                userId = perCreditinfo.getAppUser();
            }
            if (StringUtils.isEmpty(paramMap.get(CreditConstants.ORG_CODE))) {
                orgCode = perCreditinfo.getAppUserdept();
            }
            if (CreditConstants.Credit_Query_Sts_5.equals(perCreditinfo.getQrySts()) || CreditConstants.Credit_Query_Sts_7.equals(perCreditinfo.getQrySts())) {
                perCreditinfo.setRptPath(rptPath.concat(File.separator).concat(perCreditinfo.getQryReason()));
                perCreditinfo.setErrInfo("");
            } else {
                perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);
                perCreditinfo.setErrInfo(errInfo);
            }
            perCreditinfo.setRptQrydt(currentDate);
            perCreditinfo.setActionType(CreditConstants.Credit_Action_Type_2);
            perCreditinfo.setUpdUser(userId);
            perCreditinfo.setUpdUserdept(orgCode);
            perCreditinfo.setUpdDt(currentDate);
            perCreditinfo.setZxUser(bcmZxaccinfo.getZxUser());
            perCreditinfo.setZxUsername(bcmZxaccinfo.getZxUsername());
            logger.info("操作用户Ip:" + paramMap.get(CreditConstants.SYS_USER_IP) + "|mac:" + paramMap.get(CreditConstants.SYS_USER_MAC));
            perCreditinfo.setRemark1(paramMap.get(CreditConstants.SYS_USER_IP));
            perCreditinfo.setRemark2(paramMap.get(CreditConstants.SYS_USER_MAC));

            //是否推送行内
            if (CreditConstants.RPT_SNDBANKFLG_Y.equals(bcmReqcfg.getRptSndbankflg())) {
                perCreditinfo.setSendBankSts(CreditConstants.SEND_BANK_STS_1); //未推送
            } else {
                perCreditinfo.setSendBankSts(CreditConstants.SEND_BANK_STS_0);//不推送
            }

            //是否上报管控
            if (CreditConstants.RPT_UPLOADFLG_Y.equals(bcmReqcfg.getRptUploadflg())) {
                perCreditinfo.setUploadSts(CreditConstants.UPLOAD_STS_1); //未报送
            } else {
                perCreditinfo.setUploadSts(CreditConstants.UPLOAD_STS_0);//不报送
            }
            //是否已对账
            if (CreditConstants.CHK_IN_SUC.equals(bcmReqcfg.getChkIn())) {
                perCreditinfo.setChkRst(CreditConstants.CHK_IN_RST1); //未对账
            } else {
                perCreditinfo.setChkRst(CreditConstants.CHK_IN_RST0);//不对账
            }

            // 当日剩余查询笔数
            long balCnt = bcmZxaccinfo.getBalCnt();
            if (balCnt >= 1) {
                long newBalCnt = balCnt - 1;
                if (newBalCnt >= 0) {
                    bcmZxaccinfo.setBalCnt(newBalCnt);
                    // 更新数据
                    this.updatePerCreditinfoAndBcmZxaccinfo(perCreditinfo, bcmZxaccinfo,orgCode);
                }
            }
            if (CreditConstants.Credit_Query_Sts_4.equals(perCreditinfo.getQrySts())) {
                messageResultDTO.setQrySts(CreditConstants.Credit_Query_Sts_6);
            } else {
                messageResultDTO.setQrySts(perCreditinfo.getQrySts());
            }
            logger.info("PerCreditQueryServiceImpl.queryPerCredit 征信报告查询成功");
        } else {
            cmmCreditQueryService.judgeOtherQueryStatus(messageResultDTO, qrySts);
        }
        logger.info("PerCreditQueryServiceImpl.queryPerCredit messageResultDTO.code="
                + messageResultDTO.getCode() + ";messageResultDTO.message=" + messageResultDTO.getMessage());
        logger.info("PerCreditQueryServiceImpl.queryPerCredit End ......");
        messageResultDTO.setPerCreditinfo(perCreditinfo);
        logger.info("查询耗时" + (System.currentTimeMillis() - queryTime) + "毫秒");
        return messageResultDTO;
    }

    private void updateLocalQry(PerCreditinfo perCreditinfo, BcmReqcfg bcmReqcfg) {
        logger.info("PerCreditQueryServiceImpl.updateLocalQry Start ......");
        perCreditinfo.setQrySts(CreditConstants.Credit_Query_Sts_6);//查询状态
        if (CreditConstants.RPT_PARSEFLG_Y.equals(bcmReqcfg.getRptParseflg())) {//解析状态
            perCreditinfo.setParseSts(CreditConstants.Credit_Parse_Sts_1);
        } else {
            perCreditinfo.setParseSts(CreditConstants.Credit_Parse_Sts_0);
        }

        if (CreditConstants.RPT_SNDBANKFLG_Y.equals(bcmReqcfg.getRptSndbankflg())) {//报告推送行内标识
            perCreditinfo.setSendBankSts(CreditConstants.SEND_BANK_STS_1);
        } else {
            perCreditinfo.setSendBankSts(CreditConstants.SEND_BANK_STS_0);
        }

        if (CreditConstants.RPT_UPLOADFLG_Y.equals(bcmReqcfg.getRptUploadflg())) {//报告上报标识
            perCreditinfo.setUploadSts(CreditConstants.UPLOAD_STS_1);
        } else {
            perCreditinfo.setUploadSts(CreditConstants.UPLOAD_STS_0);
        }

        if (CreditConstants.DESENSITIXEFLG_Y.equals(bcmReqcfg.getDesensitizeFlg())) {//脱敏标识
            perCreditinfo.setDesensitizeSts(CreditConstants.DESENSITIZE_STS_1);
        } else {
            perCreditinfo.setDesensitizeSts(CreditConstants.DESENSITIZE_STS_0);
        }

        if (CreditConstants.WATERMARKFLG_Y.equals(bcmReqcfg.getWatermarkFlg())) {//水印标识
            perCreditinfo.setWatermarkSts(CreditConstants.WATERMARKFLG_STS_2);
        } else {
            perCreditinfo.setWatermarkSts(CreditConstants.WATERMARKFLG_STS_0);
        }
        if (CreditConstants.ENCRYCOMPRESS_FLG_1.equals(bcmReqcfg.getEncrycompressFlg())) {//加密加压标识
            perCreditinfo.setEncrySts(CreditConstants.ENCRY_STS_0);
            perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_0);
        } else if (CreditConstants.ENCRYCOMPRESS_FLG_2.equals(bcmReqcfg.getEncrycompressFlg())) {
            perCreditinfo.setEncrySts(CreditConstants.ENCRY_STS_1);
            perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_0);
        } else if (CreditConstants.ENCRYCOMPRESS_FLG_3.equals(bcmReqcfg.getEncrycompressFlg())) {
            perCreditinfo.setEncrySts(CreditConstants.ENCRY_STS_0);
            perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_1);
        } else {
            perCreditinfo.setEncrySts(CreditConstants.ENCRY_STS_1);
            perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_1);
        }
        logger.info("PerCreditQueryServiceImpl.updateLocalQry End ......");
    }


    /**
     * 查询个人添加水印征信报告
     *
     * @param htmlStr
     * @return
     */
    @Override
    public MessageResultDTO transerToPerWarterMarkRpt(String htmlStr) throws Exception {
        BcmReqcfg bcmReqcfg = DataCacheUtils.getBcmReqcfgBySysCode(CreditConstants.SYS_CODE_LOCAL);
        String flag = bcmReqcfg.getWatermarkFlg();
        //判断水印标识 有无 1 有 2 无
        if (flag.trim().equals("1")) {

            htmlStr = htmlStr.replace("<BODY>", "<BODY ");
        }

        return null;
    }

    /**
     * 解析并生成个人征信报告
     *
     * @param bcmReqcfg
     * @param perCreditinfo
     * @param htmlReportStr
     * @return
     * @throws Exception
     */
    private ParseReportDTO parseAndGenerateFileOfPer(MessageResultDTO messageResultDTO, boolean displayFlg, Document document, BcmReqcfg bcmReqcfg, PerCreditinfo perCreditinfo, String htmlReportStr)
            throws Exception {
        logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer......Start");
        String basePath = bcmReqcfg.getPerrptPath();
        String qryReason = perCreditinfo.getQryReason();
        String id = perCreditinfo.getId();
        String rptParseflag = bcmReqcfg.getRptParseflg();
        String jsonReportStr = null;

        ParseReportDTO parseReportDTO = new ParseReportDTO();
        try {
            String currentDate = DateUtils.getCurrentDate();
            String rptPath = basePath.concat(File.separator).concat(currentDate);
            //1-解析
            long parseTime = System.currentTimeMillis();
            if ((CreditConstants.Value_1).equals(rptParseflag)) {
                if (CreditConstants.Credit_Parse_Sts_1.equals(perCreditinfo.getParseSts())
                        || CreditConstants.Credit_Parse_Sts_3.equals(perCreditinfo.getParseSts())
                        || StringUtils.isBlank(perCreditinfo.getParseSts())
                        || StringUtils.isBlank(perCreditinfo.getParseSts().trim())) {
                    if (CreditConstants.Credit_Query_Sts_5.equals(perCreditinfo.getQrySts())) {
                        parseReportDTO = perCreditReportParse.parsePerCreditRpt(htmlReportStr);
                        if (MessageCodeConstants.Success.equals(parseReportDTO.getCode())) {
                            jsonReportStr = parseReportDTO.getJsonReportStr();

                            String jsonCombine = DataCacheUtils.getSysDictListByParamType(CreditConstants.JSONCOMBINE).get(0).getParamValue();
                            if (jsonCombine.equalsIgnoreCase("Y")) {
                                jsonReportStr = PerJsonCombine.jsonToCombine(jsonReportStr);
                                parseReportDTO.setJsonReportStr(jsonReportStr);
                            }
                            parseReportDTO.setParseSts(CreditConstants.Credit_Parse_Sts_2);
                        } else {
                            parseReportDTO.setParseSts(CreditConstants.Credit_Parse_Sts_3);
                        }
                    } else {
                        parseReportDTO.setParseSts(CreditConstants.Credit_Parse_Sts_1);
                    }
                }

            } else {//不解析
                parseReportDTO.setParseSts(CreditConstants.Credit_Parse_Sts_0);
            }
            if (!StringUtils.isEmpty(jsonReportStr)) {
                int index = jsonReportStr.indexOf("ReportNo");
                if (index > -1) {
                    perCreditinfo.setRptNo(jsonReportStr.substring(index + 11, index + 33));
                }
            }
            logger.info("解析耗时" + (System.currentTimeMillis() - parseTime) + "毫秒");
            //脱敏
            long desTime = System.currentTimeMillis();
            if (CreditConstants.DESENSITIXEFLG_Y.equals(bcmReqcfg.getDesensitizeFlg())) {
                String desensitizeSts = perCreditinfo.getDesensitizeSts();
                if (StringUtils.isBlank(desensitizeSts)
                        || CreditConstants.DESENSITIZE_STS_1.equals(desensitizeSts)
                        || CreditConstants.DESENSITIZE_STS_3.equals(desensitizeSts)) {
                    rulDesensitizeUtil.transerToPerDesensitizeRpt(messageResultDTO, document, htmlReportStr, jsonReportStr);
                    perCreditinfo.setDesensitizeSts(messageResultDTO.getDesensitizeSts());
                }
            }
            logger.info("脱敏耗时" + (System.currentTimeMillis() - desTime) + "毫秒");
            if (messageResultDTO.getHtmlReportStr() == null) {
                messageResultDTO.setHtmlReportStr(htmlReportStr);
            }
            if (messageResultDTO.getJsonReportStr() == null && jsonReportStr != null) {
                messageResultDTO.setJsonReportStr(jsonReportStr);
            }
            boolean genHtmlFileFlg = true;
            if (displayFlg) {
                if (!CreditConstants.DESENSITIZE_STS_2.equals(messageResultDTO.getDesensitizeSts())) {
                    genHtmlFileFlg = false;
                }
            }
            logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer genHtmlFileFlg="+genHtmlFileFlg);

            boolean genJsonFileFlg = true;
            if (displayFlg) {
                if (!CreditConstants.Credit_Parse_Sts_2.equals(parseReportDTO.getParseSts())) {
                    genJsonFileFlg = false;
                }
            }
            logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer genJsonFileFlg="+genJsonFileFlg);

            String dispalySegmentHtml ="";
            String dispalySegmentJson ="";

            String filePath = null;
            String htmlFlg = null;
            if (genHtmlFileFlg) {
                boolean rptDisplayBySeg = false;
                logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer remark5="+perCreditinfo.getRemark5());
                logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer ratchIreqid="+perCreditinfo.getBatchIreqid());
                if (StringUtils.isNotBlank(perCreditinfo.getRemark5())
                        && !CreditConstants.perRptRstAllSegment.equals(perCreditinfo.getRemark5())) {
                    if (StringUtils.isNotBlank(perCreditinfo.getBatchIreqid())) {

                        List<String> segRptList = perCreditRptDisplayBySegment.getDisplaySegment(perCreditinfo.getRemark5(), messageResultDTO.getJsonReportStr());
                        logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer segRptList="+segRptList.toString());
                        if(segRptList.size() ==1){
                            dispalySegmentHtml = segRptList.get(0);
                        }else if(segRptList.size() ==2){
                            dispalySegmentHtml = segRptList.get(0);
                            dispalySegmentJson = segRptList.get(1);
                        }
                        if(!StringUtils.isEmpty(dispalySegmentHtml)){
                            cmmCreditQueryService.generateReportFile(messageResultDTO.getHtmlReportStr(), basePath, qryReason, id.concat("_all"), CreditConstants.Credit_Report_Suffix_Html);
                            filePath = cmmCreditQueryService.generateReportFile(dispalySegmentHtml, basePath, qryReason, id, CreditConstants.Credit_Report_Suffix_Html);
                            rptDisplayBySeg = true;
                        }
                    }
                }

                logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer rptDisplayBySeg="+rptDisplayBySeg);
                if (!rptDisplayBySeg) {
                    filePath = cmmCreditQueryService.generateReportFile(messageResultDTO.getHtmlReportStr(), basePath, qryReason, id, CreditConstants.Credit_Report_Suffix_Html);
                }

                htmlFlg = "oldHtml";
                String srcHtmlFile = filePath.concat(File.separator).concat(id).concat(CreditConstants.Credit_Report_Suffix_Html);
                String destHtmlFile = filePath.concat(File.separator).concat(id).concat("_en").concat(CreditConstants.Credit_Report_Suffix_Html);

                boolean encryptHtmlFlg = encryFile(bcmReqcfg, perCreditinfo, srcHtmlFile, destHtmlFile);
                if (encryptHtmlFlg) {
                    htmlFlg = "encryHtml";
                }
            }
            String jsonFlg = null;
            if (messageResultDTO.getJsonReportStr() != null && genJsonFileFlg) {
                boolean rptDisplayBySeg = false;
                if (StringUtils.isNotBlank(perCreditinfo.getRemark5())
                        && !CreditConstants.perRptRstAllSegment.equals(perCreditinfo.getRemark5())) {
                    if (StringUtils.isNotBlank(perCreditinfo.getBatchIreqid())) {
                        if(StringUtils.isEmpty(dispalySegmentJson)){
                           List<String> segRptList = perCreditRptDisplayBySegment.getDisplaySegment(perCreditinfo.getRemark5(), messageResultDTO.getJsonReportStr());
                           if(segRptList.size() == 2){
                               dispalySegmentJson = segRptList.get(1);
                           }
                        }
                        if(StringUtils.isNotEmpty(dispalySegmentJson)){
                            cmmCreditQueryService.generateReportFile(messageResultDTO.getJsonReportStr(), basePath, qryReason, id.concat("_all"), CreditConstants.Credit_Report_Suffix_Json);
                            filePath = cmmCreditQueryService.generateReportFile(messageResultDTO.getJsonReportStr(), basePath, qryReason, id, CreditConstants.Credit_Report_Suffix_Json);
                            rptDisplayBySeg = true;
                        }
                    }
                }
                logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer rptDisplayBySeg="+rptDisplayBySeg);

                if (!rptDisplayBySeg) {
                    filePath = cmmCreditQueryService.generateReportFile(messageResultDTO.getJsonReportStr(), basePath, qryReason, id, CreditConstants.Credit_Report_Suffix_Json);
                }

                jsonFlg = "oldJson";
                String srcHtmlFile = filePath.concat(File.separator).concat(id).concat(CreditConstants.Credit_Report_Suffix_Json);
                String destJsonFile = filePath.concat(File.separator).concat(id).concat("_en").concat(CreditConstants.Credit_Report_Suffix_Json);
                boolean encryptJsonFlg = encryFile(bcmReqcfg, perCreditinfo, srcHtmlFile, destJsonFile);
                if (encryptJsonFlg) {
                    jsonFlg = "encryJson";
                }
            }
            compressFile(bcmReqcfg, perCreditinfo, htmlFlg, jsonFlg, filePath);

        } catch (Exception e) {
            parseReportDTO.setParseSts(CreditConstants.Credit_Parse_Sts_3);
            messageResultDTO.setCode(MessageCodeConstants.Failure);
            messageResultDTO.setMessage("查询出现异常，请稍后重试");
            logger.info(e.toString());
            logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer......End");
            throw new BusinessException("解析并生成个人征信报告发生错误！");
        }
        logger.info("PerCreditQueryServiceImpl.parseAndGenerateFileOfPer......End");
        return parseReportDTO;
    }

    private boolean encryFile(BcmReqcfg bcmReqcfg, PerCreditinfo perCreditinfo, String filePath, String destFile) throws Exception {
        logger.info("PerCreditQueryServiceImpl.encryFile Start....");
        boolean encryptFlg = false;
        String encrycompressFlg = bcmReqcfg.getEncrycompressFlg();
        logger.info("PerCreditQueryServiceImpl.encryFile encrycompressFlg=" + encrycompressFlg);
        if (CreditConstants.ENCRYCOMPRESS_FLG_2.equals(encrycompressFlg)
                || CreditConstants.ENCRYCOMPRESS_FLG_4.equals(encrycompressFlg)) {
            String encrySts = perCreditinfo.getEncrySts();
            logger.info("EntCreditQueryServiceImpl.encryFile encrySts=" + encrySts);
            if ((StringUtils.isBlank(encrySts)
                    || CreditConstants.ENCRY_STS_1.equals(encrySts)
                    || CreditConstants.ENCRY_STS_3.equals(encrySts))
            ) {

                IoutSyscfg ioutSyscfg = DataCacheUtils.getIoutSyscfgBySyscode(perCreditinfo.getSysCode());
                if (ioutSyscfg == null) {
                    perCreditinfo.setEncrySts(CreditConstants.ENCRY_STS_3);
                    logger.info("EntCreditQueryServiceImpl.encryFile IoutSyscfg表中" + perCreditinfo.getSysCode() + "相关参数未配置");
                } else {
                    String secretKey = ioutSyscfg.getPubKey();
                    encryptFlg = CipherUtil.encryptStr(filePath, destFile, secretKey);
                    if (encryptFlg) {
                        if (destFile.endsWith(CreditConstants.Credit_Report_Suffix_Json)) {
                            perCreditinfo.setEncrySts(CreditConstants.ENCRY_STS_2);
                        }
                    }
                }
            }
        } else if (CreditConstants.ENCRYCOMPRESS_FLG_1.equals(encrycompressFlg)) {
            perCreditinfo.setEncrySts(CreditConstants.ENCRY_STS_0);
        }
        logger.info("PerCreditQueryServiceImpl.encryFile End....");
        return encryptFlg;
    }


    private void compressFile(BcmReqcfg bcmReqcfg, PerCreditinfo perCreditinfo, String htmlFlg, String jsonFlg, String filePath) {
        logger.info("PerCreditQueryServiceImpl.compressFile Start....");
        logger.info("PerCreditQueryServiceImpl.compressFile htmlFlg=" + htmlFlg + ";jsonFlg=" + jsonFlg);
        if (StringUtils.isNotBlank(htmlFlg) || StringUtils.isNotBlank(jsonFlg)) {
            String encrycompressFlg = bcmReqcfg.getEncrycompressFlg();
            logger.info("PerCreditQueryServiceImpl.compressFile encrycompressFlg=" + encrycompressFlg);
            if (CreditConstants.ENCRYCOMPRESS_FLG_3.equals(encrycompressFlg)
                    || CreditConstants.ENCRYCOMPRESS_FLG_4.equals(encrycompressFlg)) {
                String compressSts = perCreditinfo.getCompressSts();
                logger.info("PerCreditQueryServiceImpl.compressFile compressSts=" + compressSts);
                if ((StringUtils.isBlank(compressSts)
                        || CreditConstants.COMPRESS_STS_1.equals(compressSts)
                        || CreditConstants.COMPRESS_STS_3.equals(compressSts))
                ) {
                    String id = perCreditinfo.getId();
                    if (CreditConstants.ENCRY_STS_2.equals(perCreditinfo.getEncrySts())) {
                        id = id.concat("_en");
                    }
                    List<File> fileList = ZipUtils.getFileList(filePath, id);
                    if (fileList.size() > 0) {
                        boolean compressFlg = ZipUtils.zipFile(filePath.concat(File.separator).concat(perCreditinfo.getId().concat(CreditConstants.Credit_Report_Suffix_ZIP)), fileList);
                        if (compressFlg) {
                            perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_2);
                        } else {
                            perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_3);
                        }
                    } else {
                        perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_3);
                        logger.info("PerCreditQueryServiceImpl.compressFile 不存在待压缩文件.");
                    }
                }
            } else {
                perCreditinfo.setCompressSts(CreditConstants.COMPRESS_STS_0);
            }
            logger.info("PerCreditQueryServiceImpl.compressFile End....");
        }
    }


    /**
     * 更新个人征信查询信息和人行账户信息
     *
     * @param perCreditinfo
     * @param bcmZxaccinfo
     * @throws Exception
     */
    public void updatePerCreditinfoAndBcmZxaccinfo(PerCreditinfo perCreditinfo, BcmZxaccinfo bcmZxaccinfo,String orgCode) throws Exception {
        logger.info("PerCreditQueryServiceImpl.updatePerCreditinfoAndBcmZxaccinfo......Start");
        // 1.更新个人征信查询申请信息
        perCreditinfoService.updateByPrimaryKey(perCreditinfo);
        // 2.登记查询流水
        cmmCreditQueryService.insertPerIupQrylogupload(perCreditinfo);
        // 3.更新人行账号信息
        bcmZxaccinfoService.updateByPrimaryKey(bcmZxaccinfo);
        DataCacheUtils.loadBcmZxaccinfo();
        // 4.更新客户信息
        //getPerCustinfoAndUpd(perCreditinfo);
        logger.info("PerCreditQueryServiceImpl.updatePerCreditinfoAndBcmZxaccinfo......End");
    }

    public void getPerCustinfoAndUpd(PerCreditinfo perCreditinfo,String userName,String orgCode) throws Exception {
        logger.info("PerCreditQueryServiceImpl.getPerCustinfoAndUpd......Start");
        String custId = perCreditinfo.getCustId();
        if (custId != null) {
            PerCreditinfoExample perCreditinfoExample = new PerCreditinfoExample();
            PerCreditinfoExample.Criteria criteria = perCreditinfoExample.createCriteria();
            criteria.andCustIdEqualTo(perCreditinfo.getCustId());
            List<PerCreditinfo> perCreditinfoList = perCreditinfoService.selectByExample(perCreditinfoExample);
            int relCnt = 0;
            if (perCreditinfoList != null && perCreditinfoList.size() > 0) {
                relCnt = perCreditinfoList.size();
            }
            PerCustinfo perCustinfo = perCustinfoService.selectByPrimaryKey(perCreditinfo.getCustId());
            if (StringUtils.isEmpty(perCustinfo.getCreUser())) {
                perCustinfo.setCreUser(perCreditinfo.getAppUser());
            }
            if (StringUtils.isEmpty(perCustinfo.getUpdUser())) {
                perCustinfo.setUpdUser(perCreditinfo.getAppUser());
            }
            if (StringUtils.isEmpty(perCustinfo.getCreUserdept())) {
                perCustinfo.setCreUserdept(perCreditinfo.getCreUserdept());
            }
            if (StringUtils.isEmpty(perCustinfo.getUpdUserdept())) {
                perCustinfo.setUpdUserdept(perCreditinfo.getUpdUserdept());
            }
            if (StringUtils.isEmpty(perCustinfo.getCreDt())) {
                perCustinfo.setCreDt(perCreditinfo.getCreDt());
            }
            if (StringUtils.isEmpty(perCustinfo.getUpdDt())) {
                perCustinfo.setUpdDt(perCreditinfo.getUpdDt());
            }

            perCustinfo.setRelauthcreditId(relCnt + "");
            perCustinfoService.updatePerCustinfo(perCustinfo,userName,orgCode);
        }
        logger.info("PerCreditQueryServiceImpl.getPerCustinfoAndUpd......End");
    }

    /**
     * 个人登录
     *
     * @param hclient
     * @return
     */
    @Override
    public HttpResponse loginPbcPer(HttpClientUtils hclient, BcmReqcfg bcmReqcfg, BcmZxaccinfo bcmZxaccinfo) throws Exception {
        logger.info("PerCreditQueryServiceImpl.loginPbc......Start");
        //人行征信系统URL
        String perBasePath = bcmReqcfg.getGrZxurlbase();
        //登录URL
        String loginPerUrl = cmmCreditQueryService.buildCreditUrl(perBasePath, CreditConstants.PBOC_Key_Per_Login_Half_URL);

        String zxUser = bcmZxaccinfo.getZxUser();
        String zxUserPwd = EncryptUtils.getPassDecrypt(bcmZxaccinfo.getZxUserpwd());

        NameValuePair[] loginNvp = new NameValuePair[2];

        NameValuePair zxUserNvp = new NameValuePair();
        zxUserNvp.setName(CreditConstants.PBOC_Key_userid);
        zxUserNvp.setValue(zxUser);
        loginNvp[0] = (zxUserNvp);

        NameValuePair zxUserPwdNvp = new NameValuePair();
        zxUserPwdNvp.setName(CreditConstants.PBOC_Key_password);
        zxUserPwdNvp.setValue(zxUserPwd);
        loginNvp[1] = (zxUserPwdNvp);

        HttpClientUtils.setCookies(zxUser.concat("per"), "");
        logger.info("PerCreditQueryServiceImpl.loginPbc......End");
        return hclient.doHttpPost("gbk", zxUser.concat("per"), loginPerUrl, loginNvp);
    }

    private HttpResponse masterSendQryApp(HttpClientUtils hclient, BcmReqcfg bcmReqcfg, BcmZxaccinfo bcmZxaccinfo, PerCreditinfo perCreditinfo) throws Exception {
        logger.info("PerCreditQueryServiceImpl.masterSendQryApp......Start");
        String zxUser = bcmZxaccinfo.getZxUser();

        NameValuePair[] qryAppVals = new NameValuePair[7];
        NameValuePair custNameNvp = new NameValuePair();
        NameValuePair custCertypeNvp = new NameValuePair();
        NameValuePair custCernoNvp = new NameValuePair();
        NameValuePair qryReasonNvp = new NameValuePair();
        NameValuePair qryTypeNvp = new NameValuePair();
        NameValuePair qryFormatNvp = new NameValuePair();
        NameValuePair policeTypeNvp = new NameValuePair();

        String custName = perCreditinfo.getCustName();
        String custCertype = perCreditinfo.getCustCertype();
        String custCerno = perCreditinfo.getCustCertno();
        String qryReason = perCreditinfo.getQryReason();
        String qryType = perCreditinfo.getQryType();
        String qryFormat = perCreditinfo.getQryFormat();

        custNameNvp.setName(CreditConstants.PBOC_Key_username);
        custNameNvp.setValue(custName);
        qryAppVals[0] = (custNameNvp);

        custCertypeNvp.setName(CreditConstants.PBOC_Key_certype);
        custCertypeNvp.setValue(custCertype);
        qryAppVals[1] = (custCertypeNvp);

        custCernoNvp.setName(CreditConstants.PBOC_Key_cercode);
        custCernoNvp.setValue(custCerno);
        qryAppVals[2] = (custCernoNvp);

        qryReasonNvp.setName(CreditConstants.PBOC_Key_queryreason);
        qryReasonNvp.setValue(qryReason);
        qryAppVals[3] = (qryReasonNvp);

        qryTypeNvp.setName(CreditConstants.PBOC_Key_idauthflag);
        qryTypeNvp.setValue(qryType);
        qryAppVals[4] = (qryTypeNvp);

        qryFormatNvp.setName(CreditConstants.PBOC_Key_vertype);
        qryFormatNvp.setValue(qryFormat);
        qryAppVals[5] = (qryFormatNvp);

        policeTypeNvp.setName(CreditConstants.PBOC_Key_policetype);
        policeTypeNvp.setValue(CreditConstants.Value_0);
        qryAppVals[6] = (policeTypeNvp);

        String perBasePath = bcmReqcfg.getGrZxurlbase();
        String perQueryUrl = cmmCreditQueryService.buildCreditUrl(perBasePath, CreditConstants.PBOC_Key_Per_Query_Half_URL);

        HttpClientUtils.setCookies(zxUser.concat("per"), "");
        logger.info("PerCreditQueryServiceImpl.masterSendQryApp......End");
        return hclient.doHttpPost("gbk", zxUser.concat("per"), perQueryUrl, qryAppVals);
    }

    /**
     * 查询个人添加水印
     *
     * @param reportStr,path
     * @return reportStr
     */
    public String handleReportStrWaterMark(Document document, String reportStr, PerCreditinfo perCreditinfo, String ip,String userName,String orgName) throws Exception {
        logger.info("PerCreditQueryServiceImpl.handleReportStrWaterMark......Start");
        BcmReqcfg bcmReqcfg = DataCacheUtils.getBcmReqcfgBySysCode(CreditConstants.SYS_CODE_LOCAL);
        String flag = bcmReqcfg.getWatermarkFlg();
        //判断水印标识 有无 1 有 2 无
        if ((CreditConstants.WATERMARKFLG_Y).equals(flag.trim())) {
            //判断水印规则设置表
            List<RulWatermarkcfg> rulWatermarkcfgList = DataCacheUtils.getRulWatermarkcfgListByBusiType(CreditConstants.RULWATERMARKCFG_STS_00);
            if (rulWatermarkcfgList.size() > 0) {
                String waterMarkFilePath = rulWatermarkcfgList.get(0).getWatermarkFilepath();
                String waterMarkPro = rulWatermarkcfgList.get(0).getWatermarkPro();
                String imagePath = "'" + waterMarkFilePath.concat(waterMarkPro) + "'";
                if (null != rulWatermarkcfgList.get(0).getRemark2()) {
                    String os = System.getProperty("os.name");
                    String webpath = "";
                    String physicalPath = "";
                    String path = this.getClass().getResource("/").getPath();
                    if (os.toLowerCase().startsWith("win")) {
                        if (path.contains("target")) {
                            webpath = path.substring(1, path.indexOf("target"));
                            physicalPath = webpath.concat("src/main/webapp/images/watermark/");
                        }
                        if (path.contains("WEB-INF")) {
                            webpath = path.substring(1, path.indexOf("WEB-INF"));
                            physicalPath = webpath.concat("images/watermark/");
                        }
                    } else {
                        if (path.contains("target")) {
                            webpath = path.substring(0, path.indexOf("target"));
                            physicalPath = webpath.concat("src/main/webapp/images/watermark/");
                        }
                        if (path.contains("WEB-INF")) {
                            webpath = path.substring(0, path.indexOf("WEB-INF"));
                            physicalPath = webpath.concat("images/watermark/");
                        }
                    }
                    Integer r = 0;
                    Integer g = 0;
                    Integer b = 0;
                    String srcImgPath = physicalPath.concat(waterMarkPro);
                    String tarImgPath = physicalPath.concat("targetImage").concat(waterMarkPro);
                    String wordContent = "查阅人员账号:".concat(userName).concat(" 部门:").concat(orgName).concat(" 查阅日期:").concat(DateUtils.getCurrentDate().concat(" IP:").concat(ip));
                    String colors[] = rulWatermarkcfgList.get(0).getRemark2().split(",");
                    if (colors.length == 3) {
                        r = Integer.parseInt(colors[0]);
                        g = Integer.parseInt(colors[1]);
                        b = Integer.parseInt(colors[2]);
                    }
                    Color color = new Color(r, g, b);
                    File file = new File(srcImgPath);
                    if (file.exists()) {
                        new FileUtils().addWordToPic(srcImgPath, tarImgPath, wordContent, color, CreditConstants.RULWATERMARKCFG_STS_00);
                        imagePath = "'" + waterMarkFilePath.concat("targetImage").concat(waterMarkPro) + "'";
                    }
                }
                document.select("body").attr("style", "background-image: url(" + imagePath + ");background-repeat: repeat-y;background-position: center top;background-size:720px;");
                reportStr = document.toString();
                perCreditinfo.setWatermarkSts(CreditConstants.WATERMARKFLG_STS_1);
            } else {
                perCreditinfo.setWatermarkSts(CreditConstants.WATERMARKFLG_STS_2);
            }
        } else {
            perCreditinfo.setWatermarkSts(CreditConstants.WATERMARKFLG_STS_0);
        }
        perCreditinfoService.updateByPrimaryKey(perCreditinfo);

        logger.info("PerCreditQueryServiceImpl.handleReportStrWaterMark......End");
        return reportStr;
    }

    /**
     * 个人查询打印及屏蔽右键处理
     *
     * @param document
     * @return reportStr
     */
    public String handleReportStrWinPrint(Document document) throws Exception {
        logger.info("PerCreditQueryServiceImpl.handleReportStrWinPrint......Start");
        document.select("input[name=doPrint]").attr("onclick", "javascript:window.print()");
        document.select("html").attr("oncontextmenu", "return false").attr("ondragstart", "return false").attr("onselectstart", "return false");
        String reportStr = document.toString();
        logger.info("PerCreditQueryServiceImpl.handleReportStrWinPrint......End");
        return reportStr;
    }

}