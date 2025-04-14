drop database todo_db;
create database todo_db;
use todo_db;

/*
	note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
*/

-- mysql
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(125) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    description VARCHAR(255),
    status INT,
    note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
);

CREATE TABLE role (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(100) not null,
    status INT,
	note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
);

create table user_role (
    user_id int,
    role_id int,
    primary key (user_id, role_id)
);

create table `group` (
    group_id int auto_increment primary key,
    group_name varchar(100) not null,
    description text,
    status int,
	note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
);

create table group_user (
    group_id int,
    user_id int,
    role_id int,
    joined_at datetime,
    status int,
	note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
);

create table task (
    task_id int auto_increment primary key,
    parent_task_id int,
    title varchar(100) not null,
    content TEXT,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status int,
	note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
);

create table task_relation (
    task_id int,
    user_id int null,
    group_id int null,
    note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255),
    unique key (task_id, user_id, group_id)
);

create table status (
    status_id int auto_increment primary key,
    status_type varchar(255) not null,
    status_name varchar(255) not null,
    description varchar(255),
	note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
);

create table invalidated_token (
	jit_id int auto_increment primary key,
    jit varchar(255),
    expiry DATETIME
);

CREATE INDEX idx_token_value ON invalidated_token (jit);

create table setting (
    setting_id int auto_increment primary key,
    setting_name varchar(100) not null,
    setting_value varchar(100) not null,
    setting_type varchar(100) not null,
    status int,
	note TEXT,
    delete_flg INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
);