CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` datetime DEFAULT NULL,
  `is_used` bigint(1) NOT NULL DEFAULT '1',
  `full_path` varchar(100) DEFAULT NULL,
  `full_path_name` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcmtwvovxcdalvlgifxh6luun8` (`parent`) USING BTREE,
  CONSTRAINT `FKcmtwvovxcdalvlgifxh6luun8` FOREIGN KEY (`parent`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8;

CREATE TABLE `program` (
  `id` varchar(255) NOT NULL,
  `cast` varchar(255) DEFAULT NULL,
  `category1_name` varchar(255) DEFAULT NULL,
  `category2_name` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `grade` int(11) DEFAULT 0,
  `name` varchar(255) DEFAULT NULL,
  `original_name` varchar(255) DEFAULT NULL,
  `product_country` varchar(255) DEFAULT NULL,
  `product_year` int(11) NOT NULL,
  `production` varchar(255) DEFAULT NULL,
  `summary` text,
  `category` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2wep6dkcpotc85hj9vcubsrll` (`category`),
  CONSTRAINT `FK2wep6dkcpotc85hj9vcubsrll` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category(`name`, `is_used`) value("PROGRAM", 1);
UPDATE category SET `full_path`=id, `full_path_name`=`name` WHERE `name`="PROGRAM" AND `parent` IS NULL;
