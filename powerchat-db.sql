create database powerchat;
create schema powerchat;

create table if not exists powerchat.user (
NAME varchar(255),
EMAIL varchar(255),
PHONE_NUMBER varchar(50) primary key,
);

create table if not exists powerchat.plan (
id varchar(255) primary key,
monthly_prompt_limit numeric
);

create table if not exists powerchat.subscription (
id uuid default gen_random_uuid() primary key,
subscription_user varchar(50) references powerchat.user(phone_number),
plan varchar(255) references powerchat.plan(id),
created_at timestamp,
expiration_date timestamp,
is_active boolean
);