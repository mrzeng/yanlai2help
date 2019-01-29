/**
 * <p>Description: </p>
 * <p>Title: Const.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月26日 下午4:29:44</p>
 */
package org.spring.springboot.zw.util;

import redis.clients.jedis.Jedis;

import java.util.ResourceBundle;

/**
 * Title: Const Description:
 * 
 * @author zhaowei
 * @version 2018年4月26日 下午4:29:44
 */
public class Const {
    public static final String SUCCESS_CODE = "1000";
    public static final String FAILURE_CODE = "1001";
    public static final String LOGINFAILURE_CODE = "1002";
    public static final String UNUSED_CODE = "1003";
    public static final String EMPTY_CODE = "1004";
    public static final String NODATA = "1005";
    public static final String REPEATQUEST = "1006";
    public static final String NOTALLOWED = "1007"; // 该操作不允许
    public static final String OUTOFINDEX = "1008"; // 報名已滿
    public static final String NOTQUALIFIED = "1009"; // 不符合报名条件
    public static final String JOINED = "1010"; // 已加入
    public static final String CHECKING = "1011"; // 正在审核
    public static final String REFUSED = "1012"; // 被拒绝
    public static final String NOTJOINED = "1013"; // 未加入
    public static final String SAMECODE = "1014"; // 旧值新值一致
    public static final String NONEEDUPDATE = "1015"; //APP無需更新
    public static final String LOGIN_TIME_OUT = "9999"; //登陆超时 错误信息码
    //积分相关信息码
    public static final String ALREADY_OBTAINED_SCORE = "1201"; // 表示本次变更无效，已经存在有效记录
    public static final String IRREGULAR_PARAMETERS = "1202"; // 表示传送参数不合规范
    public static final String SHORT_INTEGRAL = "1203"; // 表示积分不足（仅用于兑换返回，其余情况提示成功）
    public static final String EXCEEDING_QUOTA = "1204"; // 表示兑换积分超过限额，不予兑换
    public static final String MALICIOUS_COMMENTS = "1205"; // 表示恶意评论
    public static final String OTHER_ERROR = "1206"; // 表示出现其他错误
    public static final String EXCEEDING_PER_QUOTA = "1207"; // 表示当天兑换人数超过限额，不予兑换

    public static final String NO_SESSIONOUTTIMEKEY = "fqTvHv6fyVijQXHyZvJKRHB/zc5c7TgxSRgXt3lbuOQ="; // 不需要session验证标志
    public static final String ALLENCRYPTCODE = "xzxzxzxzxzxzxzxz"; // AESaes秘钥密匙
    public static final String SPECIAL_USERID = "e3n6b4cgky8nxvaYxeKL+XB/zc5c7TgxSRgXt3lbuOQ="; // 特殊意义用户号，代表列表查询
    //问题查询相关类型
    public static final String FILE_PATH = "videosAndBooks"; //上传的视频文件、手册文件的储存路径,是在src的基础上补充的部分
    //redis最多留存条数
    public static final int REDIS_MAX_QUESTIONLIST = 20; //redis最多存储公共问题信息条数
    public static final String REDIS_PATH = "127.0.0.1"; //redis 路径
    public static final String REDIS_PASSWD = "123456"; //redis 密码
    //问题查询相关类型
    public static final String QUERY_PRELIMINARY = "1110"; // 表示查询入门
    public static final String QUERY_QUESTION_COMMON_LIST = "0000C"; // 表示查询公共问题汇总列表
    public static final String QUERY_QUESTION_LIST = "0000"; // 表示查询问题汇总列表
    public static final String QUERY_QUESTION_REPLY_LIST = "1111"; // 表示查询回复汇总列表
    public static final String QUERY_QUESTION_TYPE_LIST = "10000"; // 表示问题类别列表
    public static final String QUERY_VIDEOS_LIST = "20000"; // 表示视频列表
    public static final String QUERY_BOOKS_LIST = "30000"; // 表示手册列表

    //问题变更相关类型
    public static final String UPDATA_QUESTION_REPLY = "00009"; // 表示更改回复信息
    public static final String UPDATA_PRELIMINARY = "11110"; // 表示更改入门
    public static final String UPDATA_QUESTION = "00000"; // 表示变更问题汇总
    public static final String UPDATA_QUESTION_TYPE = "00001"; // 表示变更问题类别
    public static final String UPDATA_VIDEOS = "00002"; // 表示变更视频信息
    public static final String UPDATA_BOOKS = "00003"; // 表示变更手册信息

    //问题变更相关类型
    public static final String DELETE_QUESTION_REPLY = "00090"; // 表示删除回复信息
    public static final String DELETE_PRELIMINARY = "111110"; // 表示删除入门
    public static final String DELETE_QUESTION = "000000"; // 表示删除问题汇总
    public static final String DELETE_QUESTION_TYPE = "00010"; // 表示删除问题类别
    public static final String DELETE_VIDEOS = "00020"; // 表示删除视频信息
    public static final String DELETE_BOOKS = "00030"; // 表示删除手册信息

    //问题增加相关类型
    public static final String ADD_QUESTION_REPLY = "00900"; // 表示增加回复信息
    public static final String ADD_PRELIMINARY = "1111110"; // 表示增加入门信息
    public static final String ADD_QUESTION = "0000000"; // 表示增加问题汇总
    public static final String ADD_QUESTION_TYPE = "00100"; // 表示增加问题类别
    public static final String ADD_VIDEOS = "00200"; // 表示增加视频信息
    public static final String ADD_BOOKS = "00300"; // 表示增加手册信息

    //问题增加相关类型
    public static final Integer CONTENTSHORT_NUM = 9; // 表示问题展示最大长度

    //手册/视频区分
    public static final String VIDEOS = "1"; // 表示变更视频信息
    public static final String BOOKS = "0"; // 表示变更手册信息


    //积分查询相关类型
    public static final String QUERYSCORE_PER = "00000"; // 表示查询个人汇总积分
    public static final String QUERYSCORE_LOG = "10000"; // 表示查询个人积分变更日志列表
    public static final String QUERYSCORE_COURSE = "20000"; // 表示查询个人课程积分列表
    public static final String QUERYSCORE_QUESTION = "30000"; // 表示个人问题积分列表
    public static final String QUERYSCORE_TRAINING  = "40000"; // 表示个人培训班积分列表
    public static final String QUERYSCORE_EXCHANGE = "50000"; // 表示查询个人兑换积分列表
    public static final String QUERYSCORE_QUESTIONNAIRE  = "60000"; // 表示个人问卷调查积分列表
    public static final String QUERYSCORE_TASK = "70000"; // 表示查询个人作业积分列表
    public static final String QUERYSCORE_DAYEND = "80000"; // 表示查询日终跑批信息列表

    //积分变更相关类型
    public static final String LOGIN_UPDSCORE = "00001"; // 表示用户登陆获取积分
    public static final String DETAIL_LOGIN_UPDSCORE = "系统登陆"; //
    public static final String DEFAULT_LOGIN_UPDSCORE = "5"; // 表示用户登陆获取积分默认是5分
    public static final String LEARN_UPDSCORE = "00002"; // 表示用户通过课程学习获取积分
    public static final String DETAIL_LEARN_UPDSCORE = "课程学习"; //
    public static final String DEFAULT_LEARN_UPDSCORE = "5"; // 表示用户课程学习获取积分默认是5分
    public static final String COMMENT_UPDSCORE = "00003"; // 表示用户通过课程评论获取积分
    public static final String DETAIL_COMMENT_UPDSCORE = "课程评论";
    public static final String DEFAULT_COMMENT_UPDSCORE = "10"; // 默认用户第一次评论获得积分10分，具体以积分配置表为准
    public static final String COMMENT_UPDSCORE_CREATE_NUM = "0"; // 用户初始课程评论次数
    public static final String HOSTILITY_COMMENT_UPDSCORE = "00004"; // 表示用户通过恶意评论损失积分
    public static final String DETAIL_HOSTILITY_COMMENT_UPDSCORE = "恶意评论";
    public static final String DEFAULT_HOSTILITY_COMMENT_NUM = "3"; // 恶意评论次数默认值，大于这个值就被视为
    public static final String DEFAULT_HOSTILITY_COMMENT_UPDSCORE = "-10"; // 表示用户通过恶意评论损失积分的默认值
    public static final String INVESTIGATION_UPDSCORE  = "00005"; // 表示用户通过问卷调查获取积分
    public static final String DETAIL_INVESTIGATION_UPDSCORE  = "问卷调查";
    public static final String DEFAULT_INVESTIGATION_UPDSCORE  = "5"; // 表示用户通过问卷调查获取积分的默认值
    public static final String SHARE_UPDSCORE = "00006"; // 表示用户通过分享课程获取积分
    public static final String DETAIL_SHARE_UPDSCORE = "分享课程";
    public static final String TASK_UPDSCORE = "00007"; // 表示用户通过作业被指定为精华获取积分
    public static final String DETAIL_TASK_UPDSCORE = "作业被指定为精华/非精华"; // 表示用户通过课程类型获取积分
    public static final String DEFAULT_TASK_UPDSCORE = "5"; // 表示用户通过作业被指定为精华获取积分如果没有在配置表中配置，默认值是5分
    public static final String APPRECIATE_UPDSCORE = "00008"; // 表示用户通过问题点赞获取积分
    public static final String DETAIL_APPRECIATE_UPDSCORE = "问题点赞";
    public static final String DEFAULT_APPRECIATE_UPDSCORE = "5"; // 表示用户通过问题点赞获取积分
    public static final String MAX_EXCHANGE_PER = "00009"; // 表示最大兑换人数
    public static final String DEFAULT_MAX_EXCHANGE_PER = "100"; // 表示最大兑换人数，默认值
    public static final String MAX_EXCHANGE_SCORE  = "00010"; // 表示个人最大兑换金额
    public static final String DEFAULT_MAX_EXCHANGE_SCORE = "100"; // 表示最大兑换积分数量，默认值
    public static final String DETAIL_EXCHANGE_UPDSCORE  = "兑换"; // 表示个人兑换
    public static final String ACTUAL_EFFECT_TIME  = "00011"; // 表示奖励金额实效时间
    public static final String DEFAULT_ACTUAL_EFFECT_TIME  = "30"; // 表示奖励金额实效时间，默认值是30天
    public static final String EXCHANGE_UPDSCORE  = "00012"; // 表示用户通过积分兑换、损失积分了变更类型
    public static final String TRAINING_UPDSCORE  = "00013"; // 表示培训班结业获取积分
    public static final String DEFAULT_TRAINING_UPDSCORE  = "5"; // 表示培训班结业获取积分的默认值
    public static final String DETAIL_TRAINING_UPDSCORE  = "培训班结业"; // 表示培训班结业
    public static final String AUTO_UPDSCORE  = "00014"; // 系统自动销毁过期积分类型
    public static final String DEFAULT_AUTO_UPDSCORE  = "系统日终过期积分清理"; // 系统自动销毁过期积分类型



    //帮助模块类型
    public static final String IF_RELEASE_YES  = "1"; // 已发布
    public static final String IF_RELEASE_NO  = "0"; // 未发布
    public static final String TYPE1  = "1"; // 点赞
    public static final String TYPE2  = "2"; // 点击
    public static final String TYPE3  = "3"; // 拍砖
    public static final String CONUSER_ID_INTERFACE  = "0KVHN/knEQVvMVqrW4AdHw=="; // 外部接口中文加密



    //课程学习种类
    public static final String IF_SHARE_YES  = "1"; // 课程已经分享
    public static final String IF_SHARE_NO  = "0"; // 课程未曾分享
    public static final String IF_LEARN_YES  = "1"; // 课程已经学习
    public static final String IF_LEARN_NO  = "0"; // 课程未曾学习
    public static final String IF_COMMENT_YES  = "1"; // 课程已经评论
    public static final String IF_COMMENT_NO  = "0"; // 课程未曾评论
    public static final String IF_ASSESS_YES  = "1"; // 课程已评价
    public static final String IF_ASSESS_NO  = "0"; // 课程未曾评价
    public static final String IF_SORCE_YES  = "1"; // 已得分,已精华
    public static final String IF_SORCE__NO  = "0"; // 未得分,未精华
    public static final String IF_GRADUATION_YES  = "1"; // 已结业
    public static final String IF_GRADUATION__NO  = "0"; // 未结业
    public static final String IF_BATCH_SUCC_NO  = "1"; // 跑批失败
    public static final String IF_BATCH_SUCC_YES  = "0"; // 跑批成功
    public static final String ISENABLED_YES  = "0"; // 有效期积分是否有效--有效，
    public static final String ISENABLED_NO  = "1"; // 有效期积分是否有效--无效
    public static final String LOSEEFFICACY_YES  = "1"; // 全部失效
    public static final String LOSEEFFICACY_NO  = "0"; // 部分失效
    public static final String STATUS_YES  = "0"; // 有效
    public static final String STATUS_NO  = "1"; // 失效

    public static final String EARLIEST_TIME = "1000-01-01 00:00:01";
    public static final String LATE_ARRIVAL_TIME = "5000-01-01 00:00:01";
    public static final String EARLIEST_DAY = "10000101";
    public static final String LATE_ARRIVAL_DAY = "5000-01-01 00:00:01";
    public static final String ORIGINALSCORE = "50";//用户初始积分
    public static final Integer MAX_PAGENUM = 4000;//最多支持8000万条数据列表   //4000页
    public static final String MAX_PAGESIZE = "20000";//最多一次查出20000条
    public static final String PAGE = "10";
    public static final int NUMBER_TEN = 10;
    public static final String RECOMMENDTCCOUNT = "3";
    public static final String FTPROOTCWIMAGE = "ftpRoot/image/kj/"; // 课件图片上传路径
    public static final String FTPROOTCIMAGE = "ftpRoot/image/kc/"; // 课程图片上传路径
    public static final String FTPROOTTCIMAGE = "ftpRoot/image/trainingcourse/"; // 培训班图片上传路径
    public static final String FTPROOTFILE = "ftpRoot/resource/kj/"; // 课件文件夹上传路径
    public static final String FTPROOTZIP = "ftpRoot/zip/kj/"; // 课件文件夹压缩路径
    public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";// WEBSOCKET配置路径
    public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; // 二维码存放路径
    public static ResourceBundle res = ResourceBundle.getBundle("ApplicationResources");
    public static final String NOTICEHTML = res.getString("NOTICEHTML");
    public static final String WEBPATH = res.getString("WEBPATH");
    public static final String PLAYHTML = res.getString("PLAYHTML");
    public static final String FTPROOTPAHT = res.getString("FTPROOTPAHT");
    public static final String NGINXIP = res.getString("NGINXIP");
    public static final String PAGETYPECOUNT = res.getString("PAGETYPECOUNT");
    public static final String TEACHERIMG = res.getString("TEACHERIMG");
    public static final String APPPAGECOUNT = res.getString("APPPAGECOUNT");
    public static final String LOGOFOLDER = res.getString("LOGOFOLDER");
    public static final String ANDROIDQRCODE = res.getString("ANDROIDQRCODE");
    public static final String IOSQRCODE = res.getString("IOSQRCODE");
    public static final String NEWSIMG = res.getString("NEWSIMG");
    public static final String INDEXPAGECOUNT = res.getString("INDEXPAGECOUNT");
    public static final String FTPROOTHTML = res.getString("FTPROOTHTML"); // PC端视频播放路径
    public static final String FTPROOTIMG = res.getString("FTPROOTIMG"); // PC端视频播放左侧缩略图
    public static final String HELPCENTERHTML = res.getString("HELPCENTERHTML"); // 帮助中心html路径
    public static final String RECOMENDCPICPATH = res.getString("RECOMENDCPICPATH");// 首页推荐默认用课件夹里面的图片
    public static final String PHONEFTPROOTANHTML = res.getString("PHONEFTPROOTANHTML");// 针对an1文件夹的播放路径
    public static final String APPCOURSEIMG = res.getString("APPCOURSEIMG"); // 移动端展示课程图片
    public static final String MOBILEFTPROOTHTML = res.getString("MOBILEFTPROOTHTML");// 移动端展示页面
    public static final String LOADCLASSTYPEDATE = res.getString("LOADCLASSTYPEDATE");
    public static final String IPCONNECT = res.getString("IPCONNECT");// 获取第三方学习平台ip
    public static final String VALIDCLAZZIDS = res.getString("VALIDCLAZZIDS"); // 移动端展示培训班id
    public static final String INTEVALDATE = res.getString("INTEVALDATE"); // 间隔
    public static final String FTPROOTRECOMMENDIMAGE = res.getString("FTPROOTRECOMMENDIMAGE");
    public static final String FTPROOTCERIMAGE = res.getString("FTPROOTCERIMAGE");
    public static final String FTPROOTCERIPERSONMAGE = res.getString("FTPROOTCERIPERSONMAGE"); // 培训班证书(个人)图片上传路径
    public static final String FONTLISHUROOT = res.getString("FONTLISHUROOT"); // 隶书字体路径
    public static final String COURSEWAREPIC = res.getString("COURSEWAREPIC"); // 课件图片目录
    /**
     * 模版文件地址
     */
    public static final String FILETEMPLATE = res.getString("FILETEMPLATE");
    // 证书内容展示行数(姓名、正文、致谢)
    public static final String CERTIFICATE_CONTENT_ROWS = res.getString("CERTIFICATE_CONTENT_ROWS");
    // 学习计量单位
    public static final String STUDYUNIT = res.getString("STUDYUNIT");
    // 证书编号标记位
    public static final String CERTIFICATE_FIRST_TAG = res.getString("CERTIFICATE_FIRST_TAG");
    // 证书顺序编号的位数
    public static final String CERTIFICATE_NO_NUM = res.getString("CERTIFICATE_NO_NUM");
    // 前台展示管理员联系电话
    public static final String FRONT_ADMIN_PHONE = res.getString("FRONT_ADMIN_PHONE");
    // 发送失败的短信重复发送的次数
    public static final String FAIL_SEDNSMS_COUNT = res.getString("FAIL_SEDNSMS_COUNT");
    /**
     * 培训班证书中，日期和姓名的文字大小
     */
    public static final Integer user2dateFontSize = 22;
    /**
     * 姓名和感叹语左侧空间
     */
    public static final Integer userLeftWidth = 60;
    /**
     * 姓名和感叹语右侧空间
     */
    public static final Integer userRightWidth = 15;
    /**
     * 行间距
     */
    public static final Integer rowSpace = 5;
    /**
     * 证书感叹语
     */
    public static final String certificateThanks = "特发此证。";
    
    //课程评价所需达到的课程学习进度临界值
    public static final String COMMENT_COURSE_SCHEDULE = res.getString("COMMENT_COURSE_SCHEDULE");
}
