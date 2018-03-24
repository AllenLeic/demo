﻿-- 数据库
drop database if exists javaee79_shopdb;
create database if not exists javaee79_shopdb;
use javaee79_shopdb;

-- 用户表
drop table if exists tab_user;
create table if not exists tab_user(
	uid int(11) primary key auto_increment,
 	username varchar(20) not null,
  	password varchar(32) not null,
  	name varchar(20) not null,
  	email varchar(30) not null,
  	telephone varchar(20) not null,
  	birthday varchar(30) not null,
  	sex varchar(10) not null,
  	state varchar(1) not null,
  	code varchar(64) not null
);

-- 类别表
drop table if exists tab_category;
create table if not exists tab_category(
	cid int(11) primary key auto_increment,
	cname varchar(20) not null unique
);
insert into tab_category(cname) values('手机数码');
insert into tab_category(cname) values('运动户外');
insert into tab_category(cname) values('家具家居');
insert into tab_category(cname) values('鞋靴箱包');
insert into tab_category(cname) values('图书音像');
insert into tab_category(cname) values('母婴孕婴');
insert into tab_category(cname) values('汽车用品');

-- 产品表
drop table if exists tab_product;
create table if not exists tab_product(
	pid int(11) primary key auto_increment,
	pname varchar(50) not null, 
	market_price double not null,
	shop_price double not null,
	pimage varchar(200) not null, 
	pdate date not null,
	is_hot int(11) not null,
	pdesc varchar(255) not null,
	pflag int(11) not null,
	cid int(11) not null,
	constraint cid_fk foreign key(cid) references tab_category(cid)
);
insert into tab_product values(1,'小米 1c 标准版',105,100,'products/1/c_0001.jpg','2016-12-03',1,'小米 1c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(2,'小米 2c 标准版',105,100,'products/1/c_0002.jpg','2016-12-02',0,'小米 2c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(3,'小米 3c 标准版',105,100,'products/1/c_0003.jpg','2016-12-01',1,'小米 3c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(4,'小米 4c 标准版',105,100,'products/1/c_0004.jpg','2016-11-02',0,'小米 4c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(5,'小米 5c 标准版',105,100,'products/1/c_0005.jpg','2016-11-02',1,'小米 5c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(6,'小米 6c 标准版',105,100,'products/1/c_0006.jpg','2016-11-02',0,'小米 6c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(7,'小米 7c 标准版',105,100,'products/1/c_0007.jpg','2016-10-02',1,'小米 7c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(8,'小米 8c 标准版',105,100,'products/1/c_0008.jpg','2016-10-02',0,'小米 8c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(9,'小米 9c 标准版',105,100,'products/1/c_0009.jpg','2016-10-02',1,'小米 9c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(10,'小米 10c 标准版',105,100,'products/1/c_0010.jpg','2016-9-02',0,'小米 10c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(11,'小米 11c 标准版',105,100,'products/1/c_0011.jpg','2016-9-02',1,'小米 11c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(12,'小米 12c 标准版',105,100,'products/1/c_0012.jpg','2016-9-02',0,'小米 12c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(13,'小米 13c 标准版',105,100,'products/1/c_0013.jpg','2016-8-02',1,'小米 13c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(14,'小米 14c 标准版',105,100,'products/1/c_0014.jpg','2016-8-02',0,'小米 14c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(15,'小米 15c 标准版',105,100,'products/1/c_0015.jpg','2016-8-02',1,'小米 15c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(16,'小米 16c 标准版',105,100,'products/1/c_0016.jpg','2016-7-02',0,'小米 16c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(17,'小米 17c 标准版',105,100,'products/1/c_0017.jpg','2016-7-02',1,'小米 17c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');
insert into tab_product values(18,'小米 18c 标准版',105,100,'products/1/c_0018.jpg','2016-7-02',0,'小米 18c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1');

-- 订单表
drop table if exists tab_order;
create table if not exists tab_order(
	oid int(11) primary key auto_increment,
 	ordertime datetime not null,
 	total double not null,
 	state int(11) not null,
  	address varchar(30) not null,
  	name varchar(20) not null,
  	telephone varchar(20) not null,
  	uid int(11) not null,
  	constraint uid_fk foreign key(uid) references tab_user(uid)
);

-- 订单项表
drop table if exists tab_orderitem;
create table if not exists tab_orderitem(
	orderitemid int(11) primary key auto_increment,
	count int(11) not null,
	subtotal double not null,
	pid int(11) not null,
	oid int(11) not null,
	constraint pid_fk foreign key(pid) references tab_product(pid),
	constraint oid_fk foreign key(oid) references tab_order(oid)
);

-- 显示所有表的内容
select * from tab_user order by uid asc;
select * from tab_category order by cid asc;
select * from tab_product order by pid asc;
select * from tab_order order by oid asc;
select * from tab_orderitem order by orderitemid asc;








