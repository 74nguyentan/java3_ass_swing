create database FPL;
use FPL;

create table GRADE (
ID int primary key IDENTITY,
MaSV nvarchar(50) ,
Tienganh int ,
Tinhoc int ,
GDTC int ,

);

create table users (
username nvarchar(50) primary key not null,
password nvarchar(50) ,
role nvarchar(50),--0:nhan vien , 1: giaovien , -1 login that bai
);

insert into users (username,password,role) values
('GiaoVien','giaovien','1'),
('NhanVien','nhanvien','0');

create table students(
MaSV nvarchar(50) primary key not null,
Hoten nvarchar(50) ,
Email nvarchar(50) ,
SDT nvarchar(15),
Gioitinh bit ,
Diachi nvarchar(50),
Hinh nvarchar(50),
);

alter table GRADE add foreign key (MaSV) references students(MaSV);
alter table students add constraint Email unique(Email);
alter table students add constraint ktsdt check(len(SDT) between 10 and 11);