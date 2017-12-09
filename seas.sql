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
	reviewID INT AUTO_INCREMENT,
	userID INT,
	waterbodyID INT,
	reviewDate DATE NOT NULL DEFAULT '0000-00-00',
	rating INT NOT NULL DEFAULT 1,
  PRIMARY KEY (reviewID),
	FOREIGN KEY (userID) REFERENCES User(userID) on delete cascade,
	FOREIGN KEY (waterbodyID) REFERENCES Waterbody(waterbodyID) on delete cascade
);
ALTER TABLE REVIEW AUTO_INCREMENT = 2001;


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
	reviewID INT AUTO_INCREMENT,
	userID INT,
	waterbodyID INT,
	reviewDate DATE NOT NULL DEFAULT '0000-00-00',
	rating INT NOT NULL DEFAULT 1,
  PRIMARY KEY (reviewID),
	FOREIGN KEY (userID) REFERENCES User(userID) on delete cascade,
	FOREIGN KEY (waterbodyID) REFERENCES Waterbody(waterbodyID) on delete cascade
);
ALTER TABLE ARCHIVEREVIEW AUTO_INCREMENT = 2001;


/* Triggers */

/* When new Review added, 
    increment numRating, update lastUpdated, recalculate avgRating for corresponding WaterRating */
DROP TRIGGER IF EXISTS addWaterRating; 
DELIMITER //
CREATE TRIGGER addWaterRating 
AFTER INSERT on Review 
FOR EACH ROW 
BEGIN 
	UPDATE WaterRating SET numRating = numRating+1 WHERE waterbodyID = New.waterbodyID; 
    UPDATE WaterRating SET lastUpdated = New.reviewDate WHERE waterbodyID = New.waterbodyID; 
    UPDATE WaterRating SET avgRating = 
        (SELECT avg(rating) FROM Review GROUP BY waterbodyID HAVING waterbodyID = New.waterbodyID)
		WHERE waterbodyID = New.waterbodyID; 
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
        (SELECT avg(rating) FROM Review GROUP BY waterbodyID HAVING waterbodyID = Old.waterbodyID)
		WHERE waterbodyID = Old.waterbodyID;
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
	IF (New.minCredentials > Old.minCredentials) 
	THEN  
	DELETE FROM Review WHERE New.waterbodyID = waterbodyID AND userID IN
		(SELECT userID FROM User WHERE New.minCredentials > credentials);
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
create procedure createAccount(in inputName varchar(30), in inputPass varchar(30))
begin
	insert into USER values (null, inputName ,inputPass , 1, 'user' );
end; //
delimiter ;

/* To get the correct user based on the inputName and inputPassword from USER if exists*/
Drop procedure if exists userLogin;
Delimiter //
create procedure userLogin(in inputName varchar(30), in inputPass varchar(30))
begin
	select *
	from user where inputName = userName and inputPass = pass;
end; //
delimiter ;

/* To search for a waterbody or location from user input*/ 
Drop procedure if exists searchWaterbodyOrLocation;
Delimiter //
create procedure searchWaterBodyOrLocation(in searchName varchar(30))
begin
	select waterbodyID as ID, "waterbody" as searchType
		from Waterbody where searchName = waterbodyName
	union 
	select waterID as ID, "location" as searchType
		from origin where searchName = location;
end//
delimiter ;


/* Admin updates credentials of a waterbody */
DROP PROCEDURE IF EXISTS updateWaterbody;
DELIMITER //
CREATE PROCEDURE updateWaterbody(IN inputCredentials INT, IN searchName VARCHAR(30))
BEGIN
	UPDATE Waterbody
	SET minCredentials = inputCredentials
	WHERE waterbodyName = searchName;
END//
DELIMITER ;

/* Admin deletes rating */
DROP PROCEDURE IF EXISTS deleteReview;
DELIMITER //
CREATE PROCEDURE deleteReview(IN inputReviewID INT)
BEGIN
	DELETE FROM Review
	WHERE inputReviewID = reviewID;
END//
DELIMITER ;


/* Admin views all ratings of a waterbody */
DROP PROCEDURE IF EXISTS viewAllRatings;
DELIMITER //
CREATE PROCEDURE viewAllRatings(IN inputName VARCHAR(30))
BEGIN 
	SELECT rr.userID, rr.rating, reviewDate
	FROM Waterbody wb, Review rr
	WHERE EXISTS (SELECT * FROM Review r WHERE wb.waterbodyID = r.waterbodyID)
		AND waterbodyName = inputName;
END//
DELIMITER ;

/* Admin adds a new waterbody */
DROP PROCEDURE IF EXISTS addWaterbody;
DELIMITER //
CREATE PROCEDURE addWaterbody(IN inputWaterbodyName VARCHAR(30), IN inputOrigin VARCHAR(30), IN inputMinCredentials INT)
BEGIN 
	INSERT INTO waterbody(waterbodyName, waterID, minCredentials) 
		VALUES (inputWaterbodyName, 
			(SELECT waterID FROM Origin WHERE inputOrigin = waterName), 
			inputMinCredentials);
END//
DELIMITER ;

/* System updates user credentials */
DROP PROCEDURE IF EXISTS updateCredentials;
DELIMITER //
CREATE PROCEDURE updateCredentials(IN newCredential int,  in targetUserID int)
BEGIN
	UPDATE User
	SET credentials = newCredential
	WHERE User.userID = targetUserID; 
END; //
DELIMITER ;
	


/* To view information for a waterbody search */
Drop procedure if exists viewInfo;
Delimiter //
create procedure viewInfo(in inputWaterbodyID int)
begin
	SELECT location, waterName, waterbodyName, avgRating, numRating
	FROM Origin JOIN Waterbody USING(waterID) JOIN Waterrating USING(waterbodyID)
	WHERE inputWaterbodyID = Waterbody.waterbodyID;
end; //
delimiter ;


/* To view list of waterbodies for a location search */
Drop procedure if exists viewWaterbodiesFromLocation;
Delimiter //
create procedure viewWaterbodiesFromLocation(in userSearchID varchar(30))
begin
	SELECT waterName, waterbodyName
	FROM Origin JOIN Waterbody USING(waterID)
	WHERE Origin.waterID = userSearchID;
end; //
delimiter ;


/* To let user add a rating*/
Drop procedure if exists addRating;
Delimiter //
create procedure addRating(in inputUserID int, in inputWaterbodyID int, in inpurRating int, in inputDate DATE)
begin
	INSERT into Review (userID, waterbodyID, reviewDate, rating) 
	VALUES (inputUserID, inputWaterbodyID, inputDate, inpurRating);
end; //
delimiter ;


/* User views number of reviews given */
Drop procedure if exists viewNumReviews;
Delimiter //
create procedure viewNumReviews(in inputUser int)
begin
	SELECT count(rating)
	FROM Review
	WHERE inputUser = userID;
end; //
delimiter ;


/* Admin views all revies given by every user*/
Drop procedure if exists adminViewReview;
Delimiter //
Create procedure adminViewReview()
begin
	SELECT User.userID, userName, waterbodyName, reviewDate, rating, reviewID
	FROM User LEFT OUTER JOIN 
		(SELECT userID, waterbodyName, reviewDate, rating 
		FROM Review INNER JOIN Waterbody USING (waterbodyID)) as AllReviews
		USING (userID);	
end; //
delimiter ;


/* System checks user credentials to waterbody credentials*/
Drop procedure if exists checkCredentials;
Delimiter //
Create procedure checkCredentials(in inputUser int, in inputWater int)
begin
	SELECT *
	FROM Waterbody JOIN User ON(minCredentials <= credentials)
	WHERE inputUser = userID AND inputWater = waterbodyID;
end; //
delimiter ;


/* User views average rating for all waterbodies in a location */
DROP PROCEDURE IF EXISTS avgRatingLocation;
DELIMITER // 
CREATE PROCEDURE avgRatingLocation(IN loc VARCHAR(30)) 
BEGIN	
	SELECT avg(avgRating)
	FROM Origin JOIN Waterbody USING(waterID) JOIN Waterrating USING (waterbodyID)
	GROUP BY location
	HAVING location = loc; 
END; //
DELIMITER ;




/* Load Data */
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/Projects/SeaS/data/origin.txt' INTO TABLE ORIGIN 
	FIELDS TERMINATED BY ',' LINES STARTING BY '\t';
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/Projects/SeaS/data/waterbody.txt' INTO TABLE WATERBODY 
	FIELDS TERMINATED BY ',' LINES STARTING BY '\t';
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/Projects/SeaS/data/user.txt' INTO TABLE USER 
	FIELDS TERMINATED BY ',' LINES STARTING BY '\t';
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/Desktop/Projects/SeaS/data/review.txt' INTO TABLE REVIEW 
	FIELDS TERMINATED BY ',' LINES STARTING BY '\t';	
