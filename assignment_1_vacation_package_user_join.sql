-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: assignment_1
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `vacation_package_user_join`
--

DROP TABLE IF EXISTS `vacation_package_user_join`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacation_package_user_join` (
  `vacationPackageId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  KEY `FKh0eq7d48s8t4rk9uq1wqop8pa` (`userId`),
  KEY `FKqmxogwr9ik17r2yfottg5s0ou` (`vacationPackageId`),
  CONSTRAINT `FKh0eq7d48s8t4rk9uq1wqop8pa` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `FKqmxogwr9ik17r2yfottg5s0ou` FOREIGN KEY (`vacationPackageId`) REFERENCES `vacation_package` (`vacationPackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_package_user_join`
--

LOCK TABLES `vacation_package_user_join` WRITE;
/*!40000 ALTER TABLE `vacation_package_user_join` DISABLE KEYS */;
INSERT INTO `vacation_package_user_join` VALUES ('bb5b0af6-9501-4abe-8166-859253aa1bcc','8e6b572e-3be7-449f-8bb3-496fa4477a09'),('cefa12a2-969b-4626-bd5b-6fbaf7371fa1','8e6b572e-3be7-449f-8bb3-496fa4477a09'),('6988ae84-1592-4e36-93ec-8d15450ad29a','8e6b572e-3be7-449f-8bb3-496fa4477a09'),('6988ae84-1592-4e36-93ec-8d15450ad29a','b35ac730-9701-46d9-9638-2fa501fef011');
/*!40000 ALTER TABLE `vacation_package_user_join` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-15 22:45:21
