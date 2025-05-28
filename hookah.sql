-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: hookah
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `additives`
--

DROP TABLE IF EXISTS `additives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `additives` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `additives`
--

LOCK TABLES `additives` WRITE;
/*!40000 ALTER TABLE `additives` DISABLE KEYS */;
INSERT INTO `additives` VALUES (2,'Лёд'),(3,'Лимон'),(1,'Мед'),(4,'Мята');
/*!40000 ALTER TABLE `additives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_hookah`
--

DROP TABLE IF EXISTS `booking_hookah`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_hookah` (
  `booking_id` int NOT NULL,
  `hookah_id` int NOT NULL,
  PRIMARY KEY (`booking_id`,`hookah_id`),
  KEY `hookah_id` (`hookah_id`),
  CONSTRAINT `booking_hookah_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE,
  CONSTRAINT `booking_hookah_ibfk_2` FOREIGN KEY (`hookah_id`) REFERENCES `hookahorders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_hookah`
--

LOCK TABLES `booking_hookah` WRITE;
/*!40000 ALTER TABLE `booking_hookah` DISABLE KEYS */;
INSERT INTO `booking_hookah` VALUES (1,1),(1,2),(2,2),(14,4),(18,7),(19,8),(21,10),(22,11),(22,12),(23,13),(24,14),(25,15),(26,16),(26,17),(27,18),(27,19);
/*!40000 ALTER TABLE `booking_hookah` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `tableId` int NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `tableId` (`tableId`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`tableId`) REFERENCES `halltables` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,1,1,'2025-05-26 18:00:00','2025-05-26 20:00:00',0),(2,2,3,'2025-05-26 19:00:00','2025-05-26 21:00:00',0),(14,1,5,'2025-05-29 13:00:00','2025-05-29 14:00:00',0),(18,1,5,'2025-05-29 22:00:00','2025-05-29 23:00:00',1),(19,1,5,'2025-05-29 21:00:00','2025-05-29 22:00:00',1),(21,1,4,'2025-05-29 22:00:00','2025-05-29 23:00:00',1),(22,1,4,'2025-05-29 21:00:00','2025-05-29 22:00:00',1),(23,1,1,'2025-05-29 12:00:00','2025-05-29 13:00:00',0),(24,1,2,'2025-05-29 21:00:00','2025-05-29 23:00:00',0),(25,1,1,'2025-05-30 12:00:00','2025-05-30 13:00:00',0),(26,1,1,'2025-05-29 21:00:00','2025-05-29 23:00:00',0),(27,2,1,'2025-05-29 19:00:00','2025-05-29 21:00:00',1);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flavors`
--

DROP TABLE IF EXISTS `flavors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flavors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flavors`
--

LOCK TABLES `flavors` WRITE;
/*!40000 ALTER TABLE `flavors` DISABLE KEYS */;
INSERT INTO `flavors` VALUES (3,'Арбуз'),(4,'Кола'),(1,'Яблоко'),(2,'Ягоды');
/*!40000 ALTER TABLE `flavors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `halltables`
--

DROP TABLE IF EXISTS `halltables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `halltables` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tableNumber` int NOT NULL,
  `zone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `capacity` int NOT NULL,
  `hasConsole` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tableNumber` (`tableNumber`),
  CONSTRAINT `halltables_chk_1` CHECK ((`capacity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `halltables`
--

LOCK TABLES `halltables` WRITE;
/*!40000 ALTER TABLE `halltables` DISABLE KEYS */;
INSERT INTO `halltables` VALUES (1,1,'VIP',4,1),(2,2,'VIP',2,0),(3,3,'Standard',4,0),(4,4,'Standard',6,1),(5,5,'Standard',2,0);
/*!40000 ALTER TABLE `halltables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hookahorderadditive`
--

DROP TABLE IF EXISTS `hookahorderadditive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hookahorderadditive` (
  `hookahId` int NOT NULL,
  `additiveId` int NOT NULL,
  PRIMARY KEY (`hookahId`,`additiveId`),
  KEY `additiveId` (`additiveId`),
  CONSTRAINT `hookahorderadditive_ibfk_1` FOREIGN KEY (`hookahId`) REFERENCES `hookahorders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `hookahorderadditive_ibfk_2` FOREIGN KEY (`additiveId`) REFERENCES `additives` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hookahorderadditive`
--

LOCK TABLES `hookahorderadditive` WRITE;
/*!40000 ALTER TABLE `hookahorderadditive` DISABLE KEYS */;
INSERT INTO `hookahorderadditive` VALUES (1,1),(5,1),(16,1),(1,2),(5,2),(12,2),(13,2),(16,2),(19,2),(2,3),(7,3),(10,3),(16,3),(18,3),(2,4),(3,4),(7,4),(10,4),(14,4),(15,4),(17,4);
/*!40000 ALTER TABLE `hookahorderadditive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hookahorderflavor`
--

DROP TABLE IF EXISTS `hookahorderflavor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hookahorderflavor` (
  `hookahId` int NOT NULL,
  `flavorId` int NOT NULL,
  PRIMARY KEY (`hookahId`,`flavorId`),
  KEY `flavorId` (`flavorId`),
  CONSTRAINT `hookahorderflavor_ibfk_1` FOREIGN KEY (`hookahId`) REFERENCES `hookahorders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `hookahorderflavor_ibfk_2` FOREIGN KEY (`flavorId`) REFERENCES `flavors` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hookahorderflavor`
--

LOCK TABLES `hookahorderflavor` WRITE;
/*!40000 ALTER TABLE `hookahorderflavor` DISABLE KEYS */;
INSERT INTO `hookahorderflavor` VALUES (1,1),(3,1),(4,1),(6,1),(10,1),(11,1),(12,1),(13,1),(15,1),(16,1),(18,1),(19,1),(1,2),(5,2),(6,2),(7,2),(10,2),(11,2),(13,2),(14,2),(15,2),(16,2),(18,2),(19,2),(2,3),(3,3),(7,3),(9,3),(12,3),(19,3),(17,4);
/*!40000 ALTER TABLE `hookahorderflavor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hookahorders`
--

DROP TABLE IF EXISTS `hookahorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hookahorders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `base` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `strength` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bowl` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `coal` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hookahorders`
--

LOCK TABLES `hookahorders` WRITE;
/*!40000 ALTER TABLE `hookahorders` DISABLE KEYS */;
INSERT INTO `hookahorders` VALUES (1,'вода','средний','глиняная','кокосовый',_binary '\0'),(2,'молоко','легкий','силиконовая','классический',_binary '\0'),(3,'вода','крепкий','глиняная','классический',_binary '\0'),(4,'вода','легкий','глиняная','классический',_binary '\0'),(5,'молоко','легкий','глиняная','классический',_binary '\0'),(6,'вода','легкий','глиняная','классический',_binary '\0'),(7,'вода','легкий','глиняная','классический',_binary ''),(8,'вода','легкий','глиняная','классический',_binary ''),(9,'вода','легкий','глиняная','классический',_binary '\0'),(10,'молоко','средний','глиняная','классический',_binary ''),(11,'вода','легкий','силиконовая','классический',_binary ''),(12,'вода','легкий','глиняная','классический',_binary ''),(13,'вино','крепкий','силиконовая','кокосовый',_binary '\0'),(14,'молоко','легкий','фруктовая','кокосовый',_binary '\0'),(15,'вода','легкий','глиняная','классический',_binary '\0'),(16,'вино','средний','силиконовая','кокосовый',_binary '\0'),(17,'молоко','крепкий','глиняная','классический',_binary '\0'),(18,'молоко','крепкий','фруктовая','классический',_binary ''),(19,'молоко','легкий','глиняная','классический',_binary '');
/*!40000 ALTER TABLE `hookahorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `patronymic` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Иван','Петров','Александрович','ivan@example.com','qwer','USER'),(2,'Мария','Сидорова','Петровна','maria@gmail.com','qwer','USER'),(3,'Анна','Кузнецова','Игоревна','anna@gmail.com','qwer','USER'),(4,'Админ','Компьютеров','Экранович','admin@example.com','qwer','ADMIN'),(5,'Виктор','Петрович','Баринов','Kuhnya@gmail.com','qwer','USER'),(6,'Коля','Данилов','Дмитриевич','Dima@gmail.com','qwer','USER'),(7,'Вася','Данилов','Алексеевич','Vasya@gmail.com','qwer','USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-29  1:57:49
