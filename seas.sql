DROP DATABASE IF EXISTS SEAS;
CREATE DATABASE SEAS;
USE SEAS; 

/* Tables */

DROP TABLE IF EXISTS SOURCE;
CREATE TABLE SOURCE (
	waterID INT AUTO_INCREMENT,
	waterName VARCHAR(30) NOT NULL,
	location VARCHAR(30) NOT NULL, 
	PRIMARY KEY(waterID)
);
ALTER table SOURCE AUTO_INCREMENT = 101;


DROP TABLE IF EXISTS WATERBODY;
CREATE TABLE WATERBODY (
	waterbodyID INT AUTO_INCREMENT,
	waterbodyName VARCHAR(30) UNIQUE NOT NULL,
	waterID INT,
	minCredentials INT NOT NULL DEFAULT 1,
    PRIMARY KEY (waterbodyID),
	FOREIGN KEY(waterID) REFERENCES Source(waterID) on delete cascade
);
ALTER table WATERBODY AUTO_INCREMENT = 201;


DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
	userID INT AUTO_INCREMENT,
	userName VARCHAR(30) NOT NULL,
	password VARCHAR(30) NOT NULL,
	credentials INT NOT NULL DEFAULT 1,
	type VARCHAR(10) NOT NULL DEFAULT 'user',
	PRIMARY KEY(userID)
);
ALTER table USER AUTO_INCREMENT = 1001;


DROP TABLE IF EXISTS REVIEW;
CREATE TABLE REVIEW (
	userID INT,
	waterbodyID INT,
	reviewDate DATE NOT NULL DEFAULT '0000-00-00',
	rating INT NOT NULL DEFAULT 1,
    PRIMARY KEY (userID, waterbodyID),
	FOREIGN KEY (userID) REFERENCES User(userID) on delete cascade,
	FOREIGN KEY (waterbodyID) REFERENCES Waterbody(waterbodyID) on delete cascade
);


DROP TABLE IF EXISTS WATERRATING;
CREATE TABLE WATERRATING (
	waterbodyID INT,
	numRating INT NOT NULL DEFAULT 0,
	avgRating INT,
	lastUpdated DATE NOT NULL DEFAULT '0000-00-00',
    FOREIGN KEY (waterbodyID) REFERENCES Waterbody(waterbodyID) on delete cascade	
);


DROP TABLE IF EXISTS ARCHIVEREVIEW;
CREATE TABLE ARCHIVEREVIEW (
	userID INT,
	waterbodyID INT,
	reviewDate DATE NOT NULL DEFAULT '0000-00-00',
	rating INT NOT NULL DEFAULT 1,
    PRIMARY KEY (userID, waterbodyID),
	FOREIGN KEY (userID) REFERENCES User(userID) on delete cascade,
	FOREIGN KEY (waterbodyID) REFERENCES Waterbody(waterbodyID) on delete cascade
);


/* Triggers */

/* When new Review added, 
    increment numRating, update lastUpdated, recalculate avgRating for corresponding WaterRating */
DROP TRIGGER IF EXISTS addWaterRating; 
DELIMITER //
CREATE TRIGGER addWaterRating 
AFTER INSERT on Review 
FOR EACH ROW 
BEGIN 
	UPDATE WaterRating SET numRating = numRating + 1 WHERE waterbodyID = New.waterbodyID; 
    UPDATE WaterRating SET lastUpdated = New.reviewDate WHERE waterbodyID = New.waterbodyID; 
    UPDATE WaterRating SET avgRating = 
        (SELECT avg(rating) FROM Review GROUP BY waterbodyID HAVING waterbodyID = New.waterbodyID); 
END;
//
DELIMITER ;

/* When existing Review deleted, 
    decrement numRating and recalculate avgRating for corresponding WaterRating */
DROP TRIGGER IF EXISTS updateWaterRating;
DELIMITER //
CREATE TRIGGER updateWaterRating 
AFTER DELETE on Review 
FOR EACH ROW 
BEGIN 
	UPDATE WaterRating SET numRating = numRating-1 WHERE waterbodyID = Old.waterbodyID; 
    UPDATE WaterRating SET avgRating = 
        (SELECT avg(rating) FROM Review GROUP BY waterbodyID HAVING waterbodyID = Old.waterbodyID); 
END; //
DELIMITER ;

/* When Waterbody credentials are updated,
    delete existing Reviews where the User's credentials are lower than the Waterbody's new credentials */
DROP TRIGGER IF EXISTS updateWaterCredentials;
DELIMITER // 
CREATE TRIGGER updateWaterCredentials 
AFTER UPDATE ON Waterbody 
FOR EACH ROW 
BEGIN 
	IF (New.minCredentials  != Old.minCredentials) 
	THEN  
	DELETE FROM Review WHERE New.waterbodyID = waterbodyID and EXISTS 
        (SELECT * FROM User JOIN Review 
        WHERE New.waterbodyID = waterbodyID and New.minCredentials > credentials); 
	END IF; 
END; //
DELIMITER ;

/* When new Waterbody is added, insert a new WaterRating to represent it */
DROP TRIGGER IF EXISTS newWaterbody; 
DELIMITER // 
CREATE TRIGGER newWaterbody 
AFTER INSERT ON Waterbody 
FOR EACH ROW 
BEGIN 
	INSERT INTO WaterRating (waterbodyID) VALUES (New.waterbodyID); 
END; //
DELIMITER ;


/* Stored Procedures */

/* To archive reviews that are older than the cutoff date */
DROP PROCEDURE IF EXISTS archiveReviews;
DELIMITER // 
CREATE PROCEDURE archiveReviews(IN cutoff DATE)
BEGIN	
	INSERT INTO ARCHIVEREVIEW(SELECT * FROM REVIEW WHERE reviewDate < cutoff); 
	DELETE FROM REVIEW WHERE reviewDate < cutoff; 
END; //
DELIMITER ;

/* To store the user accounts into USER table based on the input name, input passwords */
Drop procedure if exists createAccount;
Delimiter //
create procedure createAccount(in  inputName varchar(30), in inputPassword varchar(30))
begin
		insert into USER values (null, inputName ,inputPassword , 1, 'user' );
end//
delimiter ;


/* Admin updates credentials of a waterbody */
DROP PROCEDURE IF EXISTS updateWaterbody;
DELIMITER //
CREATE PROCEDURE updateWaterbody(IN inputCredentials INT, IN searchName VARCHAR(30))
BEGIN
	UPDATE Waterbody
	SET minCredentials = inputCredentials
	WHERE waterbodyName = searchName
END//
DELIMITER ;

/* Admin deletes a rating */
DROP PROCEDURE IF EXISTS deleteReview;
DELIMITER //
CREATE PROCEDURE deleteReview(IN searchName VARCHAR(30), IN inputUserID INT, IN inputDate DATE, IN inputRating INT)
BEGIN
	DELETE FROM Review
	WHERE searchName = (SELECT waterbodyName FROM Waterbody) AND
		(inputUserID, inputDate, inputRating) IN 
			(SELECT userID, reviewDate, rating FROM Review)
END//
DELIMITER ;


/* Admin views all ratings of a waterbody */
DROP PROCEDURE IF EXISTS viewAllRatings;
DELIMITER //
CREATE PROCEDURE viewAllRatings(IN inputName VARCHAR(30))
BEGIN 
	SELECT userID, rating, reviewDate
	FROM Waterbody wb
	WHERE EXISTS (SELECT * FROM Review r WHERE wb.waterbodyID = r.waterbodyID)
		AND waterbodyName = inputName;
END//
DELIMITER ;

/* Admin adds a new waterbody */
DROP PROCEDURE IF EXISTS addWaterbody;
DELIMITER //
CREATE PROCEDURE addWaterbody(IN inputWaterbodyName VARCHAR(30), IN inputMinCredentials INT)
BEGIN 
	INSERT INTO waterbody(waterbodyName, waterID, minCredentials) 
		VALUES (inputWaterbodyName, 
			(SELECT waterID FROM Origin WHERE inputWaterID = waterID), 
			inputMinCredentials);
END//
DELIMITER ;

/* system updates user credentials */
DROP PROCEDURE IF EXISTS updateCredentials
DELIMITER //
CREATE PROCEDURE updateCredentials(IN newCredential, targetUserID)
BEGIN
	UPDATE User
	SET credentials = newCredential
	WHERE user.userID = targetUserID;
END//
DELIMITER ;


/* Load Data */
/* LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/mysql/books.txt' INTO TABLE BOOK;
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/mysql/users.txt' INTO TABLE USER;
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/mysql/loans.txt' INTO TABLE LOAN; */
