DROP DATABASE IF EXISTS trip_disk_db;
CREATE DATABASE trip_disk_db DEFAULT CHARACTER SET utf8mb4;
USE trip_disk_db;

CREATE TABLE `User` (
	`user_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`username` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255)	NOT NULL,
	`email`	VARCHAR(255) NOT NULL UNIQUE,
	`profile_img` VARCHAR(255) DEFAULT NULL, 
	`refresh_token`	VARCHAR(255) NOT NULL,
	`social_type` ENUM('ZERO', 'KAKAO') NOT NULL
);

CREATE TABLE `Schedule` (
	`schedule_id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`start_date` DATETIME NOT NULL,
	`end_date` DATETIME NOT NULL,
    `location` VARCHAR(255) NOT NULL,
    PRIMARY KEY(schedule_id),
    CONSTRAINT `user_schedule_fk` FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Post` (
	`post_id` INT NOT NULL AUTO_INCREMENT,
    `schedule_id` INT NOT NULL,
	`place` VARCHAR(255) NOT NULL,
    `date` DATETIME NOT NULL,
	`title`	VARCHAR(100) NOT NULL,
	`content` TEXT NOT NULL,
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- TIMESTAMP DEFAULT now() 가능 
	`updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	`is_shared`	TINYINT NOT NULL,
    PRIMARY KEY(post_id),
    CONSTRAINT `schedule_post_fk` FOREIGN KEY (`schedule_id`) REFERENCES `Schedule`(`schedule_id`) ON DELETE CASCADE
);

CREATE TABLE `Files` (
	`file_id` VARCHAR(255) NOT NULL PRIMARY KEY, -- 고유한 이름(이대로 저장하면 확장자 날라감)
	`user_id` INT NOT NULL,
	`file_name`	VARCHAR(255) NOT NULL, -- 실제파일 이름
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE
    -- id와 name 나눔 
    -- id : UUID(128비트)+내부적 처리로 유니크한 id 만들 수 있음
    -- UUID는 사용자에게 노출하지 X (보기 싫게 생겨서) -> name 따로 사용
);

CREATE TABLE `Likes` (
	`like_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`user_id` INT NOT NULL,
	`schedule_id` INT NOT NULL,
	FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`schedule_id`) REFERENCES `Schedule` (`schedule_id`) ON DELETE CASCADE
);

CREATE TABLE `SearchCondition` (
	`key` VARCHAR(255) NULL	COMMENT 'title, content, location, is_boolean',
	`word` VARCHAR(255)	NULL,
	`orderBy` VARCHAR(255) NULL	COMMENT '좋아요순',
	`orderByDir` VARCHAR(255) NULL COMMENT 'asc, desc'
	-- gpt 추천
    -- `orderBy` ENUM('title', 'content', 'location', 'is_boolean') NULL COMMENT '검색 기준',
	-- `orderByDir` ENUM('asc', 'desc') NULL COMMENT '정렬 방향'
);


INSERT INTO `User` (`username`, `password`, `email`, `profile_img`, `refresh_token`, `social_type`)
VALUES ('user1', '1234', 'user1@example.com', 'https://example.com/user1.jpg', 'refresh_token_123', 'KAKAO'),
('user2', '1234', 'user2@example.com', 'https://example.com/user2.jpg', 'refresh_token_456', 'KAKAO'),
('user3', '1234', 'user3@example.com', 'https://example.com/user3.jpg', 'refresh_token_789', 'ZERO');

INSERT INTO `Schedule` (`user_id`, `start_date`, `end_date`, `location`)
VALUES (1, '2024-11-15 00:00:00', '2024-11-22 00:00:00', '대만'),
(1, '2024-11-22 00:00:00', '2024-11-25 00:00:00', '일본'),
(1, '2024-11-05 00:00:00', '2024-11-10 00:00:00', '스위스');

INSERT INTO `Post` (`schedule_id`, `place`, `date`, `title`, `content`, `is_shared`)
VALUES (1, '타이베이 101', '2024-11-16 10:30:00', '대만 첫날', '대만에 도착해서 타이베이 101을 구경했습니다. 전망이 정말 아름다웠어요.', 1),
(1, '스린 야시장', '2024-11-17 19:00:00', '야시장에서 먹방', '스린 야시장에서 다양한 먹거리를 즐겼습니다. 버블티가 특히 맛있었어요!', 1),
(2, '오사카 성', '2024-11-23 14:00:00', '일본 여행 시작!', '오사카 성에서 사진을 찍었어요. 역사적인 느낌이 물씬 납니다.', 0),
(3, '마터호른', '2024-11-06 09:00:00', '스위스의 절경', '마터호른을 등산하며 대자연의 경이로움을 느꼈습니다.', 1),
(3, '취리히', '2024-11-08 15:30:00', '취리히에서의 하루', '취리히의 아름다운 도시를 산책하며 시간을 보냈습니다.', 0);

INSERT INTO `Likes` (`user_id`, `schedule_id`)
VALUES (2, 1),
(3, 1); -- 사용자 3이 일정1에 좋아요

