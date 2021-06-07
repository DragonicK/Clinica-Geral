/*
 Navicat Premium Data Transfer

 Source Server         : Connection
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : engine

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 07/06/2021 14:17:00
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `PersonId` int(0) NOT NULL,
  `Address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CityId` int(0) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_AddressCityId`(`CityId`) USING BTREE,
  INDEX `FK_AddressPersonId`(`PersonId`) USING BTREE,
  CONSTRAINT `FK_AddressCityId` FOREIGN KEY (`CityId`) REFERENCES `city` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_AddressPersonId` FOREIGN KEY (`PersonId`) REFERENCES `person` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CountryId` int(0) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_CityCountryId`(`CountryId`) USING BTREE,
  CONSTRAINT `FK_CityCountryId` FOREIGN KEY (`CountryId`) REFERENCES `country` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `PersonId` int(0) NOT NULL,
  `Phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_ContactPersonId`(`PersonId`) USING BTREE,
  CONSTRAINT `FK_ContactPersonId` FOREIGN KEY (`PersonId`) REFERENCES `person` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `PersonId` int(0) NOT NULL,
  `AdmissionDate` date NOT NULL,
  `Role` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_EmployeePersonId`(`PersonId`) USING BTREE,
  CONSTRAINT `FK_EmployeePersonId` FOREIGN KEY (`PersonId`) REFERENCES `person` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `PersonId` int(0) NOT NULL,
  `CompanionPersonId` int(0) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  UNIQUE INDEX `FK_PatientPersonId`(`PersonId`) USING BTREE,
  INDEX `FK_PatientCompanionPersonId`(`CompanionPersonId`) USING BTREE,
  CONSTRAINT `FK_PatientCompanionPersonId` FOREIGN KEY (`CompanionPersonId`) REFERENCES `person` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_PatientPersonId` FOREIGN KEY (`PersonId`) REFERENCES `person` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `Name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Document` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Birthday` date NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `Code` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Price` decimal(10, 2) NULL DEFAULT NULL,
  `Quantity` int(0) NULL DEFAULT NULL,
  `SupplierId` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_ProductSupplierId`(`SupplierId`) USING BTREE,
  CONSTRAINT `FK_ProductSupplierId` FOREIGN KEY (`SupplierId`) REFERENCES `supplier` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `PatientId` int(0) NOT NULL,
  `EmployeeId` int(0) NOT NULL,
  `Date` datetime(0) NOT NULL,
  `State` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_SchedulePatientId`(`PatientId`) USING BTREE,
  INDEX `FK_ScheduleEmployeeId`(`EmployeeId`) USING BTREE,
  CONSTRAINT `FK_ScheduleEmployeeId` FOREIGN KEY (`EmployeeId`) REFERENCES `employee` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_SchedulePatientId` FOREIGN KEY (`PatientId`) REFERENCES `patient` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `PersonId` int(0) NOT NULL,
  `FantasyName` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_SupplierPersonId`(`PersonId`) USING BTREE,
  CONSTRAINT `FK_SupplierPersonId` FOREIGN KEY (`PersonId`) REFERENCES `person` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for treatment
-- ----------------------------
DROP TABLE IF EXISTS `treatment`;
CREATE TABLE `treatment`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `ScheduleId` int(0) NOT NULL DEFAULT 0,
  `FinishedDate` datetime(0) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  UNIQUE INDEX `FK_TreatmentScheduleId`(`ScheduleId`) USING BTREE,
  CONSTRAINT `FK_TreatmentScheduleId` FOREIGN KEY (`ScheduleId`) REFERENCES `schedule` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for treatment_product
-- ----------------------------
DROP TABLE IF EXISTS `treatment_product`;
CREATE TABLE `treatment_product`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `TreatmentId` int(0) NOT NULL,
  `ProductId` int(0) NOT NULL,
  `ProductCount` int(0) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `FK_TreatmentProductProductId`(`ProductId`) USING BTREE,
  INDEX `FK_TreatmentProductTreatmentId`(`TreatmentId`) USING BTREE,
  CONSTRAINT `FK_TreatmentProductProductId` FOREIGN KEY (`ProductId`) REFERENCES `product` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_TreatmentProductTreatmentId` FOREIGN KEY (`TreatmentId`) REFERENCES `treatment` (`Id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
