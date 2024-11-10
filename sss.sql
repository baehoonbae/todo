-- drop if exists schema todo;
-- create schema todo;
use todo;

select * from user;
select * from category;
select * from todo;
select * from follower;
select * from following;
select * from refresh_tokens;

CREATE TABLE refresh_tokens (
    user_id VARCHAR(50) PRIMARY KEY,
    token VARCHAR(500) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);

-- user 테이블 생성
CREATE TABLE user (
    user_seq INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL
);

-- Category 테이블 생성
CREATE TABLE Category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_seq INT not null,
    color varchar(255) not null,
    title VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_seq) REFERENCES user(user_seq) ON DELETE CASCADE
);

-- Todo 테이블 생성
CREATE TABLE Todo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    content TEXT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Category(id) ON DELETE CASCADE
);

# 2024-11-10 추가 쿼리문
ALTER TABLE user 
ADD description VARCHAR(500) DEFAULT NULL;

-- Follower 테이블 생성 (나를 팔로우하는 사람들)
CREATE TABLE follower (
    follower_seq INT AUTO_INCREMENT PRIMARY KEY,
    user_seq INT NOT NULL,          -- 팔로우 받는 사람 (me)
    follower_user_seq INT NOT NULL, -- 팔로우 하는 사람
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_seq) REFERENCES user(user_seq),
    FOREIGN KEY (follower_user_seq) REFERENCES user(user_seq),
    UNIQUE KEY unique_follower (user_seq, follower_user_seq)
);

-- Following 테이블 생성 (내가 팔로우하는 사람들)
CREATE TABLE following (
    following_seq INT AUTO_INCREMENT PRIMARY KEY,
    user_seq INT NOT NULL,           -- 팔로우 하는 사람 (me)
    following_user_seq INT NOT NULL, -- 팔로우 받는 사람
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_seq) REFERENCES user(user_seq),
    FOREIGN KEY (following_user_seq) REFERENCES user(user_seq),
    UNIQUE KEY unique_following (user_seq, following_user_seq)
);






