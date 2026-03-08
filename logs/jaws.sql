-- Create Table for Membership Details

CREATE TABLE `membership_details` (
  `uniqueId` int NOT NULL AUTO_INCREMENT,
  `membershipNo` varchar(50) NOT NULL,
  `name` varchar(500) NOT NULL,
  `mobileNo` varchar(15) DEFAULT NULL,
  `doj` date DEFAULT NULL,
  `photo` mediumblob,
  PRIMARY KEY (`uniqueId`)
) ENGINE=InnoDB AUTO_INCREMENT=398 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create Table for Event Details
CREATE TABLE `events` (
  `eventId` int NOT NULL AUTO_INCREMENT,
  `eventName` varchar(200) NOT NULL,
  `eventDate` date NOT NULL,
  `eventYear` int NOT NULL,
  `description` varchar(500) NOT NULL,
  `createdOn` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`eventId`),
  KEY `idx_event_year` (`eventYear`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Create Table for Event Attendance
CREATE TABLE `event_attendance` (
  `attendanceId` int NOT NULL AUTO_INCREMENT,
  `eventId` int NOT NULL,
  `memberUniqueId` int NOT NULL,
  `markedOn` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`attendanceId`),
  UNIQUE KEY `uk_event_member` (`eventId`,`memberUniqueId`),
  KEY `idx_eventId` (`eventId`),
  KEY `idx_memberUniqueId` (`memberUniqueId`),
  CONSTRAINT `fk_attendance_event` FOREIGN KEY (`eventId`) REFERENCES `events` (`eventId`) ON DELETE CASCADE,
  CONSTRAINT `fk_attendance_member` FOREIGN KEY (`memberUniqueId`) REFERENCES `membership_details` (`uniqueId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-----------------------------------------------------------------
-- select statement for all tables
SELECT * FROM membership_details;
SELECT * FROM event_details;
SELECT * FROM event_attendance;


-- For Adding New Event in Events Table
INSERT INTO `jaws`.`events`
(`eventName`,`eventDate`,`eventYear`,`description`)
VALUES('Labour Day', '2027-05-01', '2027', 'Dasara 2027');

-- For Adding New Member in Membership_Details Table
INSERT INTO `jaws`.`membership_details`
(`membershipNo`,`name`,`mobileNo`,`doj`)
VALUES('MEM001', 'John Doe', '1234567890', '2023-01-01');


-- For Updating members mobile number in membership_details table by name search
update membership_details set mobileNo = '<mobileNo>' where name  = '<name>';-- name should be exactly same