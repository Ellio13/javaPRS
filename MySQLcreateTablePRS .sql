CREATE DATABASE PRSmySQL;

Use PRSmySQL;

CREATE TABLE `User` (
ID int AUTO_INCREMENT PRIMARY KEY,
UserName varchar(20) NOT NULL,
Password varchar(10) NOT NULL,
FirstName varchar(20) NOT NULL,
LastName varchar(20) NOT NULL,
PhoneNumber varchar(12) NOT NULL,
Email varchar(75) NOT NULL,
Reviewer tinyint(1) DEFAULT 0,
Admin tinyint(1) DEFAULT 0,
CONSTRAINT uname UNIQUE(UserName));

CREATE TABLE Vendor (
ID int AUTO_INCREMENT PRIMARY KEY,
`Code` varchar(10) NOT NULL,
`Name` varchar(255) NOT NULL,
Address varchar(255) NOT NULL,
City varchar(255) NOT NULL,
State varchar(2) NOT NULL,
Zip varchar(5) NOT NULL,
PhoneNumber varchar(12) NOT NULL,
Email varchar(100) NOT NULL,
CONSTRAINT vcode UNIQUE(Code));

CREATE TABLE Product (
ID int AUTO_INCREMENT PRIMARY KEY,
VendorID int NOT NULL,
PartNumber varchar(50) NOT NULL,
`Name` varchar(150) NOT NULL,
Price decimal(10,2) NOT NULL,
Unit varchar(255) NULL,
PhotoPath varchar(255) NULL,
CONSTRAINT vendor_part UNIQUE(VendorID, PartNumber),
FOREIGN KEY (VendorID) REFERENCES Vendor(ID));

CREATE TABLE Request (
ID int AUTO_INCREMENT PRIMARY KEY,
UserID int NOT NULL,
RequestNumber varchar(20) NOT NULL,
`Description` varchar(100) NOT NULL,
Justification varchar(255) NOT NULL,
DateNeeded date NOT NULL,
DeliveryMode varchar(25) NOT NULL,
`Status` varchar(20) NOT NULL Default 'NEW',
Total decimal(10, 2) NOT NULL Default 0.0,
SubmittedDate date NOT NULL,
ReasonForRejection varchar(100) NULL,
FOREIGN KEY (UserID) REFERENCES User(ID));

CREATE TABLE LineItem (
ID int AUTO_INCREMENT PRIMARY KEY,
RequestID int NOT NULL,
ProductID int NOT NULL,
Quantity int NOT NULL,
FOREIGN KEY (RequestID) REFERENCES Request(ID),
FOREIGN KEY (ProductID) REFERENCES Product(ID),
CONSTRAINT req_pdt UNIQUE(RequestID, ProductID));

use prsmysql;
-- create a user and grant privileges to that user
DROP USER IF EXISTS prsmysql_user@localhost;
CREATE USER prsmysql_user@localhost IDENTIFIED BY 'sesame';
GRANT SELECT, INSERT, DELETE, UPDATE ON prsmysql.* TO prsmysql_user@localhost;
