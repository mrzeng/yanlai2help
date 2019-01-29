drop table if exists per_integration_basis;

/*==============================================================*/
/* Table: per_integration_basis                                 */
/*==============================================================*/
create table per_integration_basis
(
   ID                   varchar(32) not null comment '个人id',
   SCORE                varchar(20) default NULL comment '当前积分值',
   ORIGINAL_SCORE       varchar(20) comment '原始积分',
   LAST_TIME_SCORE      varchar(20) default NULL comment '上次积分',
   YESTERDAY_SCORE      varchar(20) comment '上日积分',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   primary key (ID),
   unique key AK_Key_2 (CREATION_TM)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_integration_basis comment '个人积分汇总表';
ALTER TABLE per_integration_basis ADD INDEX indexName(CREATION_TM);






 DROP TABLE IF EXISTS per_integration_configure;
/*==============================================================*/
/* Table: per_integration_configure                             */
/*==============================================================*/
CREATE TABLE per_integration_configure
(
   ID                   VARCHAR(32) NOT NULL COMMENT '规则id',
   STATUS               VARCHAR(2) DEFAULT NULL COMMENT '是否有效（0有效1无效2待审核）',
   SCORE_TYPE           VARCHAR(5) NOT NULL  COMMENT '积分变更规则类型   用户登陆00001，课程学习00002，课程评论00003（备注1值为最大评价次数）,恶意评论00004，问卷调查00005,分享

课程00006,精华课程00007（备注1值为课程类型），问题点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"，课程评价分享"00012"，培训班分享"00013"，奖励积分过期自动销毁"00014"',
   CREATION_PER         VARCHAR(32) COMMENT '更新操作员',
   CREATION_TM          VARCHAR(23) DEFAULT NULL COMMENT '创建时间',
   UPDATE_TM            VARCHAR(23) DEFAULT NULL COMMENT '更新时间',
   TM_SMP               VARCHAR(23) DEFAULT NULL COMMENT '时间戳',
   REMARK1              VARCHAR(100) DEFAULT NULL COMMENT '备用字段1',
   REMARK2              VARCHAR(200) DEFAULT NULL COMMENT '备用字段2',
   REMARK3              VARCHAR(200) DEFAULT NULL COMMENT '备用字段3',
   REMARK4              VARCHAR(500) DEFAULT NULL COMMENT '备用字段4',
   REMARK5              VARCHAR(500) DEFAULT NULL COMMENT '备用字段5',
   PRIMARY KEY (ID),
   UNIQUE KEY AK_Key_4 (SCORE_TYPE,REMARK1)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE per_integration_configure COMMENT '个人积分配置表';




drop table if exists per_integration_log;

/*==============================================================*/
/* Table: per_integration_log                                   */
/*==============================================================*/
create table per_integration_log
(
   ID                   varchar(32) not null comment '流水id',
   USER_ID              varchar(32) default NULL comment '用户id',
   COURSE_ID            varchar(32) comment '课程id',
   QUESTION_ID          varchar(32) default NULL comment '问题id',
   TASK_ID              varchar(32) comment '作业id',
   TRAINING_ID          varchar(32) comment '培训班id',
   EXCHANGE_ID          varchar(32) comment '兑换余额id',
   SCORE                varchar(20) default NULL comment '变更后积分',
   LAST_TIME_SCORE      varchar(20) comment '变更前积分',
   UPD_SCORE            varchar(20) comment '变更值',
   SCORE_TYPE           varchar(5) default NULL comment '积分变更规则类型  登陆00001，学习00002，评价00003（备注1值为最大评价次数）,恶意评价00004，问卷调查00005,分享课程00006,精华课程00007（备注1值为课程类型），点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"，积分过期系统自动销毁处置00014。',
   LOGNAME              text comment '详细描述',
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
   primary key (ID),
   unique key AK_Key_2 (USER_ID, ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_integration_log comment '个人积分日志表';
ALTER TABLE per_integration_log ADD INDEX indexName(CREATION_DT);




DROP TABLE IF EXISTS per_course_integration;

/*==============================================================*/
/* Table: per_course_integration                                */
/*==============================================================*/
CREATE TABLE per_course_integration
(
   ID                   VARCHAR(32) COMMENT '课程id',
   USER_ID              VARCHAR(32) DEFAULT NULL COMMENT '用户id',
   SCORE                VARCHAR(20) DEFAULT NULL COMMENT '当前积分分值',
   IF_SHARE             VARCHAR(1) COMMENT '是否分享（0未分享，1已分享）',
   IF_LEARN             VARCHAR(1) COMMENT '是否学完（0未学完，1已学完）',
   IF_COMMENT           VARCHAR(1) DEFAULT NULL COMMENT '是否评论（0未评论，1已评论）',
   IF_ASSESS            VARCHAR(1) DEFAULT NULL COMMENT '是否评价（0未评价，1已评价）',
   COMMENT_NUM          VARCHAR(10) COMMENT '评论次数',
   LEARNING_TM          VARCHAR(20) DEFAULT NULL COMMENT '学习时间',
   CREATION_TM          VARCHAR(23) DEFAULT NULL COMMENT '创建时间',
   UPDATE_TM            VARCHAR(23) DEFAULT NULL COMMENT '更新时间',
   TM_SMP               VARCHAR(23) DEFAULT NULL COMMENT '时间戳',
   REMARK1              VARCHAR(100) DEFAULT NULL COMMENT '备用字段1',
   REMARK2              VARCHAR(200) DEFAULT NULL COMMENT '备用字段2',
   REMARK3              VARCHAR(200) DEFAULT NULL COMMENT '备用字段3',
   REMARK4              VARCHAR(500) DEFAULT NULL COMMENT '备用字段4',
   REMARK5              VARCHAR(500) DEFAULT NULL COMMENT '备用字段5',
   UNIQUE KEY AK_Key_2 (ID, USER_ID)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE per_course_integration COMMENT '个人课程积分汇总表';
ALTER TABLE per_course_integration ADD INDEX indexName(CREATION_TM);





drop table if exists per_task_integration;

/*==============================================================*/
/* Table: per_task_integration                                  */
/*==============================================================*/
create table per_task_integration
(
   ID                   varchar(32) comment '作业id',
   USER_ID              varchar(32) default NULL comment '用户id',
   SCORE                varchar(20) default NULL comment '得分之后积分分值',
   IF_SCORE             varchar(1) comment '是否精华（0未精华，1已精华）',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   unique key AK_Key_1 (ID, USER_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_task_integration comment '个人作业积分汇总表';
ALTER TABLE per_task_integration ADD INDEX indexName(CREATION_TM);





drop table if exists per_question_integration;

/*==============================================================*/
/* Table: per_question_integration                              */
/*==============================================================*/
create table per_question_integration
(
   ID                   varchar(32) comment '问题id',
   USER_ID              varchar(32) default NULL comment '用户id',
   SCORE                varchar(20) default NULL comment '得分之后积分分值',
   IF_SCORE             varchar(1) comment '是否得分（0未得分，1已得分）',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   unique key AK_Key_1 (ID, USER_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_question_integration comment '个人提交问题积分汇总表';
ALTER TABLE per_question_integration ADD INDEX indexName(CREATION_TM);






drop table if exists per_exchange_integration;

/*==============================================================*/
/* Table: per_exchange_integration                              */
/*==============================================================*/
create table per_exchange_integration
(
   ID                   varchar(32) comment '兑换id',
   USER_ID              varchar(32) default NULL comment '用户id',
   SCORE                varchar(20) default NULL comment '兑换积分后分值',
   EXCHANGE_SCORE       varchar(20) comment '兑换金额',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   primary key (ID),
   unique key AK_Key_2 (ID, USER_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_exchange_integration comment '个人积分兑换信息表';
ALTER TABLE per_exchange_integration ADD INDEX indexName(CREATION_TM);





drop table if exists per_training_integration;

/*==============================================================*/
/* Table: per_training_integration                              */
/*==============================================================*/
create table per_training_integration
(
   ID                   varchar(32) comment '培训班id',
   USER_ID              varchar(32) default NULL comment '用户id',
   SCORE                varchar(20) default NULL comment '得分后积分分值',
   IF_GRADUATION        varchar(1) comment '是否结业（0未结业，1已结业）',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   unique key AK_Key_1 (ID, USER_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_training_integration comment '培训班积分信息表';
ALTER TABLE per_training_integration ADD INDEX indexName(CREATION_TM);



drop table if exists per_questionnaire_integration;

/*==============================================================*/
/* Table: per_questionnaire_integration                         */
/*==============================================================*/
create table per_questionnaire_integration
(
   ID                   varchar(32) comment '培训班id',
   USER_ID              varchar(32) default NULL comment '用户id',
   SCORE                varchar(20) default NULL comment '得分后积分分值',
   IF_SCORE             varchar(1) comment '是否得分（0未得分，1已得分）',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   unique key AK_Key_1 (ID, USER_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table per_questionnaire_integration comment '问卷信息积分汇总表';
ALTER TABLE per_questionnaire_integration ADD INDEX indexName(CREATION_TM);





drop table if exists per_dayend_runbatch;

/*==============================================================*/
/* Table: per_dayend_runbatch                                   */
/*==============================================================*/
create table per_dayend_runbatch
(
   ID                   varchar(32) not null comment '流水id',
   USER_ID              varchar(32) default NULL comment '用户id',
   BATCH_TYPE           varchar(20) comment '批次类型 日终过期积分清理:00014',
   IS_SUCC              varchar(32) default NULL comment '是否成功(0成功,1失败)',
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

alter table per_dayend_runbatch comment '个人日终跑批信息表';
ALTER TABLE per_dayend_runbatch ADD INDEX indexName(CREATION_TM);











drop table if exists effective_integral;

/*==============================================================*/
/* Table: effective_integral                                    */
/*==============================================================*/
create table effective_integral
(
   ID                   varchar(32) not null comment '流水id',
   USER_ID              varchar(32) default NULL comment '用户id',
   SCORE                varchar(20) comment '分值',
   IS_ENABLED           varchar(2) default NULL comment '是否有效(0有效,1无效)',
   END_DT               varchar(23) comment '有效截止日期',
   DAYS                 varchar(10) comment '有效期天数',
   CREATION_DT          varchar(23) comment '创建日期',
   CREATION_TM          varchar(23) default NULL comment '创建时间',
   UPDATE_TM            varchar(23) default NULL comment '更新时间',
   TM_SMP               varchar(23) default NULL comment '时间戳',
   REMARK1              varchar(100) default NULL comment '备用字段1',
   REMARK2              varchar(200) default NULL comment '备用字段2',
   REMARK3              varchar(200) default NULL comment '备用字段3',
   REMARK4              varchar(500) default NULL comment '备用字段4',
   REMARK5              varchar(500) default NULL comment '备用字段5',
   primary key (ID),
   unique key AK_Key_2 (USER_ID, END_DT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table effective_integral comment '积分有效期信息表';




drop table if exists per_integration_user;

/*==============================================================*/
/* Table: per_integration_user                                  */
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

insert into `per_integration_user` (`USER_ID`, `USERNAME`, `PASSWORD`, `NAME`, `RIGHTS`, `ROLE_ID`, `LAST_LOGIN`, `IP`, `STATUS`, `BZ`, `PHONE`, `SFID`, `START_TIME`, `END_TIME`, `YEARS`, `NUMBER`, `EMAIL`, `ORGID`, `TITILE`, `SEX`, `BIRTHDAY`) values('1123123','111','kQXUntmTeD9pTppRz7scPg==','111','1','1','2019-01-15 11:23:52','127.0.0.1','0','1','15010209523','1','1','1','1','1','1','1','1','1','1');
/*初始用户名111密码222*/
