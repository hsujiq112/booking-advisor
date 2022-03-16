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
-- Table structure for table `vacation_package`
--

DROP TABLE IF EXISTS `vacation_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacation_package` (
  `vacationPackageId` varchar(255) NOT NULL,
  `endPeriod` date DEFAULT NULL,
  `extraDetails` varchar(255) DEFAULT NULL,
  `startPeriod` date DEFAULT NULL,
  `vacationCapacity` int DEFAULT NULL,
  `vacationName` varchar(255) DEFAULT NULL,
  `vacationPrice` float DEFAULT NULL,
  `destinationId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`vacationPackageId`),
  KEY `FKpqbs9hkmk6n1hwo0mvs58wlmo` (`destinationId`),
  CONSTRAINT `FKpqbs9hkmk6n1hwo0mvs58wlmo` FOREIGN KEY (`destinationId`) REFERENCES `destination` (`destinationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_package`
--

LOCK TABLES `vacation_package` WRITE;
/*!40000 ALTER TABLE `vacation_package` DISABLE KEYS */;
INSERT INTO `vacation_package` VALUES ('19bef910-50a4-4c94-911c-2a62d837b921','2022-03-04','Pickup Available','2022-03-04',5,'Half-Day Desert Safari',215.3,'e1b8b13f-0a7d-4359-889d-a3b71584983f'),('39ab0625-e406-467a-aeee-1961c2213b7e','2022-05-20','Around the city we go','2022-05-11',10,'City Trip',200.5,'410c9009-4e15-4e29-b785-fc900d384c48'),('6988ae84-1592-4e36-93ec-8d15450ad29a','2022-03-04','Best Tower from the Old Center','2022-03-01',4,'Stephan\'s Tower',100,'c4c8b714-4fa5-4636-9157-dbcaca558f5e'),('8a42b5ce-64c2-4246-b2f9-6faf3dc4a7b8','2022-03-26','A great place for a vacation','2022-03-25',2,'Old center',50.3,'c4c8b714-4fa5-4636-9157-dbcaca558f5e'),('bb5b0af6-9501-4abe-8166-859253aa1bcc','2022-03-02','Idk, visit museums?','2022-03-02',1,'Museum trip',50,'483a2f21-c564-4f87-a7d8-3a660e201ead'),('cefa12a2-969b-4626-bd5b-6fbaf7371fa1','2022-05-11','Drink and have a good time','2022-05-04',1,'Belvedere Trip',250.99,'483a2f21-c564-4f87-a7d8-3a660e201ead'),('d4f2067c-20ef-46aa-ae46-29d25dd95061','2022-06-18','Rivieras & Railroads','2022-06-12',7,'Mediterranean Express',1000,'9f382f73-bd99-479b-9efe-a39c6f32bebe');
/*!40000 ALTER TABLE `vacation_package` ENABLE KEYS */;
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
