
DROP TABLE IF EXISTS common_question;

/*==============================================================*/
/* Table: common_question   !!!!!!!!!暂未使用，直接使用的问题基本信息表help_question_information                                     */
/*==============================================================*/
CREATE TABLE common_question
(
   ID                   VARCHAR(32) NOT NULL COMMENT '问题id',
   TITLE                VARCHAR(200) DEFAULT NULL COMMENT '标题',
   CONTENT              VARCHAR(500) COMMENT '内容',
   QUESTION_TYPE_ID     VARCHAR(32) DEFAULT NULL COMMENT '问题类别id',
   IF_RELEASE           VARCHAR(2) COMMENT '是否发布（0未发布 1已发布）',
   IF_COMMON            VARCHAR(2) COMMENT '是否是常见问题（0不常见 1常见）',
   VISUAL_RANGE         VARCHAR(2) COMMENT '可视范围（0平台所有，1提交学员自己）',
   CLICK_NUMBER         VARCHAR(10) DEFAULT NULL COMMENT '点击数量',
   NUMBER_POINTS        VARCHAR(10) COMMENT '点赞次数',
   NUMBER_BRICKS        VARCHAR(10) COMMENT '拍砖次数',
   RELEASE_TM           VARCHAR(23) DEFAULT NULL COMMENT '发布时间',
   IP                   VARCHAR(64) COMMENT '操作用户IP',
   CREATION_PER         VARCHAR(32) COMMENT '更新操作员',
   CREATION_DT          VARCHAR(23) COMMENT '创建日期',
   CREATION_TM          VARCHAR(23) DEFAULT NULL COMMENT '创建时间',
   UPDATE_TM            VARCHAR(23) DEFAULT NULL COMMENT '更新时间',
   TM_SMP               VARCHAR(23) DEFAULT NULL COMMENT '时间戳',
   REMARK1              VARCHAR(100) DEFAULT NULL COMMENT '备用字段1',
   REMARK2              VARCHAR(200) DEFAULT NULL COMMENT '备用字段2',
   REMARK3              VARCHAR(200) DEFAULT NULL COMMENT '备用字段3',
   REMARK4              VARCHAR(500) DEFAULT NULL COMMENT '备用字段4',
   REMARK5              VARCHAR(500) DEFAULT NULL COMMENT '备用字段5',
   PRIMARY KEY (ID)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE common_question COMMENT '常见问题信息表';
ALTER TABLE common_question ADD INDEX indexName(CREATION_TM);




DROP TABLE IF EXISTS preliminary_information;

/*==============================================================*/
/* Table: preliminary_information                               */
/*==============================================================*/
CREATE TABLE preliminary_information
(
   ID                   VARCHAR(32) NOT NULL COMMENT '入门id',
   PRELIMINARY_NAME     VARCHAR(32) COMMENT '入门名称',
   VIDEO_NUM            VARCHAR(32) DEFAULT NULL COMMENT '视频个数',
   MANUAL_NUM           VARCHAR(32) COMMENT '手册个数',
   `STATUS`               VARCHAR(32) COMMENT '是否有效（0无效 1有效）',
   RELEASE_TM           VARCHAR(23) DEFAULT NULL COMMENT '发布时间',
   IP                   VARCHAR(64) COMMENT '操作用户IP',
   CREATION_PER         VARCHAR(32) COMMENT '更新操作员',
   CREATION_DT          VARCHAR(23) COMMENT '创建日期',
   CREATION_TM          VARCHAR(23) DEFAULT NULL COMMENT '创建时间',
   UPDATE_TM            VARCHAR(23) DEFAULT NULL COMMENT '更新时间',
   TM_SMP               VARCHAR(23) DEFAULT NULL COMMENT '时间戳',
   REMARK1              VARCHAR(100) DEFAULT NULL COMMENT '备用字段1',
   REMARK2              VARCHAR(200) DEFAULT NULL COMMENT '备用字段2',
   REMARK3              VARCHAR(200) DEFAULT NULL COMMENT '备用字段3',
   REMARK4              VARCHAR(500) DEFAULT NULL COMMENT '备用字段4',
   REMARK5              VARCHAR(500) DEFAULT NULL COMMENT '备用字段5',
   PRIMARY KEY (ID),
   UNIQUE KEY AK_Key_2 (VIDEO_NUM, ID)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;
ALTER TABLE preliminary_information COMMENT '用户入门信息表';
ALTER TABLE preliminary_information ADD INDEX indexName(CREATION_TM);




DROP TABLE IF EXISTS video_preliminary_relation;

/*==============================================================*/
/* Table: video_preliminary_relation                            */
/*==============================================================*/
CREATE TABLE video_preliminary_relation
(
   ID                   VARCHAR(32) NOT NULL COMMENT '视频id',
   PRELIMINARY_ID       VARCHAR(32) DEFAULT NULL COMMENT '入门id',
   VIDEO_PATH           VARCHAR(200) COMMENT '视频路径',
   VIDEO_TYPE           VARCHAR(10) DEFAULT NULL COMMENT '视频格式',
   VIDEO_SIZE           VARCHAR(10) COMMENT '视频大小(M)',
   `STATUS`             VARCHAR(2) COMMENT '有效标志（0无效 1有效）',
   IF_RELEASE           varchar(2) comment '是否发布（0未发布 1已发布）',
   IP                   VARCHAR(64) COMMENT '操作用户IP',
   CREATION_PER         VARCHAR(32) COMMENT '更新操作员',
   CREATION_DT          VARCHAR(23) COMMENT '创建日期',
   CREATION_TM          VARCHAR(23) DEFAULT NULL COMMENT '创建时间',
   RELEASE_TM          varchar(23) default NULL comment '发布时间',
   UPDATE_TM            VARCHAR(23) DEFAULT NULL COMMENT '更新时间',
   TM_SMP               VARCHAR(23) DEFAULT NULL COMMENT '时间戳',
   REMARK1              VARCHAR(100) DEFAULT NULL COMMENT '备用字段1',
   REMARK2              VARCHAR(200) DEFAULT NULL COMMENT '备用字段2',
   REMARK3              VARCHAR(200) DEFAULT NULL COMMENT '备用字段3',
   REMARK4              VARCHAR(500) DEFAULT NULL COMMENT '备用字段4',
   REMARK5              VARCHAR(500) DEFAULT NULL COMMENT '备用字段5',
   PRIMARY KEY (ID)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE video_preliminary_relation COMMENT '入门视频信息表';
ALTER TABLE video_preliminary_relation ADD INDEX indexName(PRELIMINARY_ID);
ALTER TABLE video_preliminary_relation ADD INDEX indexName1(RELEASE_TM);




DROP TABLE IF EXISTS manual_preliminary_relation;

/*==============================================================*/
/* Table: manual_preliminary_relation                           */
/*==============================================================*/
CREATE TABLE manual_preliminary_relation
(
   ID                   VARCHAR(32) NOT NULL COMMENT '手册id',
   PRELIMINARY_ID       VARCHAR(32) DEFAULT NULL COMMENT '入门id',
   MANUAL_PATH          VARCHAR(200) COMMENT '手册路径',
   MANUAL_TYPE          VARCHAR(10) DEFAULT NULL COMMENT '手册格式',
   MANUAL_SIZE          VARCHAR(10) COMMENT '手册大小(M)',
   `STATUS`               VARCHAR(2) COMMENT '有效标志（0无效 1有效）',
   IF_RELEASE           varchar(2) comment '是否发布（0未发布 1已发布）',
   IP                   VARCHAR(64) COMMENT '操作用户IP',
   CREATION_PER         VARCHAR(32) COMMENT '更新操作员',
   CREATION_DT          VARCHAR(23) COMMENT '创建日期',
   CREATION_TM          VARCHAR(23) DEFAULT NULL COMMENT '创建时间',
   RELEASE_TM          varchar(23) default NULL comment '发布时间',
   UPDATE_TM            VARCHAR(23) DEFAULT NULL COMMENT '更新时间',
   TM_SMP               VARCHAR(23) DEFAULT NULL COMMENT '时间戳',
   REMARK1              VARCHAR(100) DEFAULT NULL COMMENT '备用字段1',
   REMARK2              VARCHAR(200) DEFAULT NULL COMMENT '备用字段2',
   REMARK3              VARCHAR(200) DEFAULT NULL COMMENT '备用字段3',
   REMARK4              VARCHAR(500) DEFAULT NULL COMMENT '备用字段4',
   REMARK5              VARCHAR(500) DEFAULT NULL COMMENT '备用字段5',
   PRIMARY KEY (ID)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;
ALTER TABLE manual_preliminary_relation COMMENT '入门手册信息表';
ALTER TABLE manual_preliminary_relation ADD INDEX indexName(PRELIMINARY_ID);
ALTER TABLE manual_preliminary_relation ADD INDEX indexName1(RELEASE_TM);



drop table if exists help_question_information;

/*==============================================================*/
/* Table: help_question_information                             */
/*==============================================================*/
create table help_question_information
(
   ID                   varchar(32) not null comment '问题id',
   QUESTION_CONTENT     varchar(500) default NULL comment '问题内容',
   QUESTION_KEYWORDS    varchar(250) comment '问题关键字（使用，号隔开最多5个）',
   `STATUS`               varchar(2) comment '有效标志（0无效 1有效）',
   USER_ID              varchar(32) comment '用户id',
   QUESTION_TYPE_ID     varchar(32) default NULL comment '问题类别id',
   IF_RELEASE           varchar(2) comment '是否发布（0未发布 1已发布）',
   IF_COMMON            varchar(2) comment '是否是常见问题（0不常见 1常见）',
   VISUAL_RANGE         varchar(2) comment '可视范围（0平台所有，1提交学员自己）',
   CLICK_NUMBER         varchar(10) default NULL comment '点击数量',
   NUMBER_POINTS        varchar(10) comment '点赞次数',
   NUMBER_BRICKS        varchar(10) comment '拍砖次数',
   CREATION_PER         varchar(32) comment '更新操作员',
   IP                   varchar(64) comment '操作用户IP',
   CREATION_DT          varchar(23) comment '创建日期',
   RELEASE_TM          varchar(23) default NULL comment '发布时间',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   primary key (ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table help_question_information comment '帮助问题信息表';
ALTER TABLE help_question_information ADD INDEX indexName(QUESTION_KEYWORDS);
ALTER TABLE help_question_information ADD INDEX indexName2(CREATION_TM);
ALTER TABLE help_question_information ADD INDEX indexName3(QUESTION_TYPE_ID);
ALTER TABLE help_question_information ADD INDEX indexName4(RELEASE_TM);






DROP TABLE IF EXISTS help_question_reply_information;

/*==============================================================*/
/* Table: help_question_reply_information                       */
/*==============================================================*/
CREATE TABLE help_question_reply_information
(
   ID                   VARCHAR(32) NOT NULL COMMENT '回复id',
   QUESTION_ID          VARCHAR(32) DEFAULT NULL COMMENT '问题id',
   REPLY_CONTENT        VARCHAR(500) COMMENT '回复内容',
   `STATUS`             VARCHAR(2) comment '有效标志（0无效 1有效）',
   USER_ID              varchar(32) comment '用户id',
   IP                   VARCHAR(64) COMMENT '回复人员IP',
   CREATION_PER         VARCHAR(32) COMMENT '更新操作员',
   CREATION_DT          VARCHAR(23) COMMENT '创建日期',
   CREATION_TM          VARCHAR(23) DEFAULT NULL COMMENT '创建时间',
   UPDATE_TM            VARCHAR(23) DEFAULT NULL COMMENT '更新时间',
   TM_SMP               VARCHAR(23) DEFAULT NULL COMMENT '时间戳',
   REMARK1              VARCHAR(100) DEFAULT NULL COMMENT '备用字段1',
   REMARK2              VARCHAR(200) DEFAULT NULL COMMENT '备用字段2',
   REMARK3              VARCHAR(200) DEFAULT NULL COMMENT '备用字段3',
   REMARK4              VARCHAR(500) DEFAULT NULL COMMENT '备用字段4',
   REMARK5              VARCHAR(500) DEFAULT NULL COMMENT '备用字段5',
   PRIMARY KEY (ID)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE help_question_reply_information COMMENT '帮助问题回复信息表';
ALTER TABLE help_question_reply_information ADD INDEX indexName(QUESTION_ID);
ALTER TABLE help_question_reply_information ADD INDEX indexName2(CREATION_TM);




drop table if exists question_type_info;

/*==============================================================*/
/* Table: question_type_info                                    */
/*==============================================================*/
create table question_type_info
(
   ID                   varchar(32) not null comment '类别id',
   QUESTION_TYPE_NAME   varchar(32) default NULL comment '类别名称',
   FATHER_ID            varchar(32) comment '上级id',
   `STATUS`               varchar(32) comment '是否有效（0无效 1有效）',
   IP                   varchar(64) comment '操作用户IP',
   CREATION_PER         varchar(32) comment '更新操作员',
   CREATION_DT          varchar(23) comment '创建日期',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   primary key (ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table question_type_info comment '用户问题类别信息表';
ALTER TABLE question_type_info ADD INDEX indexName(UPDATE_TM);
ALTER TABLE question_type_info ADD INDEX indexName1(CREATION_TM);

drop table if exists per_integration_user;

/*==============================================================*/
/* Table: per_integration_user       该表和用户积分信息表共用    */
/*==============================================================*/
create table per_integration_user
(
   USER_ID              varchar(100) not null comment '用户id',
   USERNAME             varchar(255) default NULL comment '用户名',
   PASSWORD             varchar(255) default NULL comment '用户密码',
   NAME                 varchar(255) default NULL comment '描述',
   RIGHTS               varchar(255) default NULL,
   ROLE_ID              varchar(100) default NULL,
   LAST_LOGIN           varchar(255) default NULL comment '最后登录时间',
   IP                   varchar(100) default NULL,
   STATUS               varchar(32) default NULL comment '是否有效',
   BZ                   varchar(255) default NULL,
   PHONE                varchar(100) default NULL comment '电话号码',
   SFID                 varchar(100) default NULL,
   START_TIME           varchar(23) default NULL,
   END_TIME             varchar(23) default NULL,
   YEARS                int(10) default NULL,
   NUMBER               varchar(100) default NULL comment '手机号码',
   EMAIL                varchar(32) default NULL,
   ORGID                varchar(32) default NULL,
   TITILE               varchar(100) default NULL,
   SEX                  varchar(2) default '2' comment '0男1女2未知',
   BIRTHDAY             varchar(23) default NULL,
   primary key (USER_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_integration_user comment '个人用户信息表';
ALTER TABLE per_integration_user ADD INDEX indexName(LAST_LOGIN);

insert into `per_integration_user` (`USER_ID`, `USERNAME`, `PASSWORD`, `NAME`, `RIGHTS`, `ROLE_ID`, `LAST_LOGIN`, `IP`, `STATUS`, `BZ`, `PHONE`, `SFID`, `START_TIME`, `END_TIME`, `YEARS`, `NUMBER`, `EMAIL`, `ORGID`, `TITILE`, `SEX`, `BIRTHDAY`) values('yanlai','111','kQXUntmTeD9pTppRz7scPg==','111','1','1','2019-01-15 11:23:52','127.0.0.1','0','1','15010209523','1','1','1','1','1','1','1','1','1','1');
/*初始用户名111密码222*/