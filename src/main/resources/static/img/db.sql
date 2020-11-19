
create database voice_work;
use voice_work;
DROP TABLE IF EXISTS circle;
create table circle(
    circle_id varchar(10) not null comment '社团id',
    circle_name varchar (20) not null comment '社团名',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (circle_id)
);
DROP TABLE IF EXISTS work_info;
create table work_info(
    work_id varchar(6) not null comment 'rjcode',
    work_title varchar(100) not null comment '标题',
    circle_id varchar(10) not null comment '社团',
    age_ratings varchar(40) comment '年龄指定',
    work_release varchar(40) comment '发售日期',
    series_id varchar(20) comment '系列',
    work_scenario varchar(20) comment '剧本',
    image_author varchar(20) comment '插画作者',
    work_format varchar(40) comment '作品类型',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (work_id)
);
DROP TABLE IF EXISTS series;
create table series(
    series_id varchar(10) not null comment '系列id',
    series_name varchar (20) not null comment '系列名',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (series_id)
);
DROP TABLE IF EXISTS tag;
create table tag(
    tag_id varchar(10) not null comment 'tagid',
    tag_name varchar (20) not null comment 'tag名',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (tag_id)
);
DROP TABLE IF EXISTS work_tag;
create table work_tag(
    work_id varchar(6) not null comment 'rjcode',
    tag_id varchar(20) not null comment 'tagid',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (work_id)
);
DROP TABLE IF EXISTS cv;
create table cv(
    cv_id varchar(20) not null comment 'cvid',
    cv_name varchar (20) not null comment 'cv名',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (cv_id)
);
DROP TABLE IF EXISTS work_cv;
create table work_cv(
    work_id varchar(6) not null comment 'rjcode',
    cv_id varchar(20) not null comment 'cvid',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (work_id)
);
create table work_dyn(
    work_id varchar(6) not null comment 'rjcode',
    price int comment '价格',
    dl_count int comment '售出数',
    rate_average int comment '平均评价',
    rate_average_2dp double comment '平均评价',
    rate_count int comment '评价数量',
    review_count int comment '评论数量',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (work_id)
);

create table rate_count_detail(
    id int not null auto_increment,
    work_id varchar(6) not null comment 'rjcode',
    review_point int not null comment '评价星级',
    count int not null comment '数量',
    ratio int not null comment 'ratio',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id)
);
create table table_rank(
                       id int not null auto_increment,
                       work_id varchar(6) not null comment 'rjcode',
                       term varchar(40) not null comment '周期',
                       category varchar(40) not null comment '分类',
                       ranking int not null comment '排名',
                       rank_date varchar(40) not null comment '排名日期',
                       create_time timestamp not null default current_timestamp comment '创建时间',
                       update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                       primary key (id)
);
create table work_dir(
    work_id varchar(6) not null comment 'rjcode',
    work_dir varchar(100) not null comment '文件夹绝对路径',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (work_id)
);