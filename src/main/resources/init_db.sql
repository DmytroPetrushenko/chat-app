CREATE SCHEMA IF NOT EXISTS `trackensure` DEFAULT CHARACTER SET utf8;
USE `trackensure`;
SET NAMES utf8_general_ci;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for messages
-- ----------------------------
CREATE TABLE `messages` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `user_id` bigint NOT NULL,
                            `message` varchar(200) NOT NULL,
                            `time_stamp` varchar(45) NOT NULL,
                            `is_deleted` tinyint NOT NULL DEFAULT '0',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
-- ----------------------------
-- Table structure for users
-- ----------------------------
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `login` varchar(45) NOT NULL,
                         `is_deleted` tinyint NOT NULL DEFAULT '0',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `name_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3