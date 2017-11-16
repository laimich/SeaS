set sql_mode='';
DROP DATABASE IF EXISTS SEAS;
CREATE DATABASE SEAS;
USE SEAS; 

/* Tables */

DROP TABLE IF EXISTS ORIGIN;
CREATE TABLE ORIGIN (
	waterID INT AUTO_INCREMENT,
	waterName VARCHAR(30) NOT NULL,
	location VARCHAR(30) NOT NULL, 
	PRIMARY KEY(waterID)
);
ALTER table ORIGIN AUTO_INCREMENT = 101;


DROP TABLE IF EXISTS WATERBODY;
CREATE TABLE WATERBODY (
	waterbodyID INT AUTO_INCREMENT,
	waterbodyName VARCHAR(30) UNIQUE NOT NULL,
	waterID INT,
	minCredentials INT NOT NULL DEFAULT 1,
    PRIMARY KEY (waterbodyID),
	FOREIGN KEY(waterID) REFERENCES ORIGIN(waterID) on delete cascade
);
ALTER table WATERBODY AUTO_INCREMENT = 201;


DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
	userID INT AUTO_INCREMENT,
	userName VARCHAR(30) NOT NULL,
	pass VARCHAR(30) NOT NULL,
	credentials INT NOT NULL DEFAULT 1,
	title VARCHAR(10) NOT NULL DEFAULT 'user',
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
create procedure createAccount(in  inputName varchar(30), in inputPass varchar(30))
begin
	insert into USER values (null, inputName ,inputPass , 1, 'user' );
end; //
delimiter ;

/* To get the correct user based on the inputName and inputPassword from USER if exists*/
Drop procedure if exists userLogin;
Delimiter //
create procedure userLogin(in inputName varchar(30), in inputPass varchar(30), 
	out userID int, out userName varchar(30))
begin
	select userID, userName
	from user where inputName = userName and inputPass = pass;

end; //
delimiter ;

/* To search for a waterbody or location from user input*/ 
Drop procedure if exists searchWaterbodyOrLocation;
Delimiter //
create procedure searchWaterBodyOrLocation(in searchName varchar(30), out desiredID int)
begin
	select waterbodyID from Waterbody where searchName = waterbodyName
		union 
	select waterID from origin where searchName = location;
end; //
delimiter ;


/* To view information for a waterbody search */
Drop procedure if exists viewInfo;
Delimiter //
create procedure viewInfo(in inputWaterbodyName varchar(30))
begin
	SELECT location, waterName, waterbodyName, avgRating
	FROM Origin, Waterbody, Waterrating
	WHERE inputWaterbodyName = waterbodyName AND 
		Origin.waterID = waterbody.waterID AND 
		waterbody.waterbodyID = waterrating.waterbodyID;

end; //
delimiter //


/* To view list of waterbodies for a location search */
Drop procedure if exists viewWaterbodiesFromLocation;
Delimiter //
create procedure viewWaterbodiesFromLocation(in userSearchLocation varchar(30))
begin
	SELECT waterName, waterbodyName
	FROM Origin JOIN Waterbody USING(waterID)
	WHERE userSeachLocation = location;
end; //
delimiter //


/* To let user add a rating*/
Drop procedure if exists addRating;
Delimiter //
create procedure addRating(in inputUserID int, in inputWaterbodyID int, in inpurRating int)
begin
	INSERT into Review (userID, waterbodyID, reviewDate, rating) 
	VALUES (inputUserID, inputWaterbodyID, null, inpurRating);
end; //
delimiter //



/* User views average  rating for all waterbodies in a location */
DROP PROCEDURE IF EXISTS avgRatingLocation;
DELIMITER // 
CREATE PROCEDURE avgRatingLocation(IN loc VARCHAR(30), OUT avg INT) 
BEGIN	
	SELECT avg(avgRating)
	FROM Origin JOIN Waterbody USING(waterID) JOIN Waterrating USING (waterbodyID)
	GROUP BY location
	HAVING location = loc; 
END; //
DELIMITER ;

/* Load Data */
/* LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/mysql/books.txt' INTO TABLE BOOK;
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/mysql/users.txt' INTO TABLE USER;
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/mysql/loans.txt' INTO TABLE LOAN; */
