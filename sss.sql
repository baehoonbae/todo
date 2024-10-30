drop schema todo;

use todo;

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

select * from user;
select * from category;
select * from todo;