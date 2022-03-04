drop database if exists SuperheroSightingTestDB;
create database SuperheroSightingTestDB;

use SuperheroSightingTestDB;

create table Superpower(
	`PowerId` int primary key auto_increment,
	`PowerName` varchar(50) not null,
	`Description` varchar(255)
);

create table Hero(
	`HeroId` int primary key auto_increment,
	`Name` varchar(50) not null,
	`Description` varchar(255),
	`IsHero` boolean not null default 1
);

create table HeroSuperpower(
	`HeroId` int not null,
	`PowerId` int not null,
	primary key pk_HeroSuperpower (HeroId, PowerId),
	foreign key fk_HeroSuperpower_Hero(HeroId) 
		references Hero(HeroId),
	foreign key fk_HeroSuperpower_Power(PowerId) 
		references Superpower(PowerId)
);

create table Location(
	`LocationId`int primary key auto_increment,
	`Name` varchar(50) not null,
	`Description` varchar(255),
    `Address` varchar(255),
	`Longitude` decimal(8,5) not null,
    `Latitude` decimal(8,5) not null
);

create table HeroSighting(
	`SightingId` int primary key auto_increment,
	`HeroId` int not null,
	`LocationId` int not null,
    `Date` date not null,
	foreign key fk_HeroSighting_Hero(HeroId) 
		references Hero(HeroId),
	foreign key fk_HeroSighting_Location(LocationId) 
		references Location(LocationId)
);

create table `Organization`(
	`OrganizationId`int primary key auto_increment,
	`Name` varchar(50) not null,
	`Description` varchar(255),
    `Address` varchar(255),
    `Contact` varchar(255),
	`IsHero` boolean not null default 1
);

create table HeroOrganization(
	`HeroId` int not null,
	`OrganizationId` int not null,
	primary key pk_HeroOrganization (HeroId, OrganizationId),
	foreign key fk_HeroOrganization_Hero(HeroId) 
		references Hero(HeroId),
	foreign key fk_HeroOrganization_Organization(OrganizationId) 
		references `Organization`(OrganizationId)
);