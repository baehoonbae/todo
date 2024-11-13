-- drop if exists schema todo;
-- create schema todo;
use todo;

select * from user;
select * from category;
select * from todo;
select * from follower;
select * from following;
select * from refresh_tokens;

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
    is_public bool not null default true,
    FOREIGN KEY (user_seq) REFERENCES user(user_seq) ON DELETE CASCADE
);

-- Todo 테이블 생성
CREATE TABLE Todo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    content TEXT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Category(id) ON DELETE CASCADE,
    FOREIGN KEY (user_seq) REFERENCES user(user_seq) ON DELETE CASCADE
);

# 2024-11-10 추가 쿼리문
ALTER TABLE user 
ADD description VARCHAR(500) DEFAULT NULL;

-- refresh_tokens 테이블 생성
CREATE TABLE refresh_tokens (
    user_id VARCHAR(50) PRIMARY KEY,
    token VARCHAR(500) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);

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

# 11-11 추가 쿼리문
ALTER TABLE category 
ADD COLUMN is_public BOOL NOT NULL DEFAULT true;

# user 가 새롭게 생성되면, 카테고리 1,카테고리 2,카테고리 3을 default로 생성함
DELIMITER //
CREATE TRIGGER create_default_categories
AFTER INSERT ON user
FOR EACH ROW
BEGIN
    -- 카테고리1 생성
    INSERT INTO Category (user_seq, color, title, is_public)
    VALUES (NEW.user_seq, '#2857aa', '카테고리 1', true);
    
    -- 카테고리2 생성
    INSERT INTO Category (user_seq, color, title, is_public)
    VALUES (NEW.user_seq, '#191919', '카테고리 2', true);
    
    -- 카테고리3 생성
    INSERT INTO Category (user_seq, color, title, is_public)
    VALUES (NEW.user_seq, '#785cb4', '카테고리 3', true);
END//

DELIMITER ;

ALTER TABLE user 
MODIFY COLUMN description VARCHAR(25) DEFAULT NULL;

# 11-12 쿼리 추가문
-- NOT NULL 제약조건도 필요한 경우
ALTER TABLE todo 
ADD COLUMN user_seq INT,
ADD CONSTRAINT fk_todo_user 
FOREIGN KEY (user_seq) REFERENCES user(user_seq)
ON DELETE CASCADE;  -- 사용자가 삭제되면 todo도 삭제

#11-13 쿼리 추가문
ALTER TABLE todo
ADD CONSTRAINT fk_todo_category
FOREIGN KEY (category_id) 
REFERENCES category(id)
ON DELETE CASCADE;