-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: student
-- ------------------------------------------------------
-- Server version	8.0.21

SET FOREIGN_KEY_CHECKS=0;

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `id_thread` int unsigned DEFAULT NULL,
  `id_course` int unsigned DEFAULT NULL,
  `id_user` int unsigned DEFAULT NULL,
  `comment_type` tinyint unsigned DEFAULT 0,
  `comment_body` varchar(255) DEFAULT NULL,
  `no_of_likes` int unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_comment_user_idx` (`id_user`),
  KEY `fk_comment_course_idx` (`id_course`),
  CONSTRAINT `fk_comment_course` FOREIGN KEY (`id_course`) REFERENCES `course` (`id`),
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id_thread`, `id_course`, `id_user`, `comment_type`, `comment_body`, `no_of_likes`) VALUES
    (NULL, 1, 1, 0, 'Koment1', 0),
    (1, NULL, 2, 1, 'Reply1Koment1', 0),
    (1, NULL, 3, 1, 'Reply2Koment1', 0),
    (NULL, 1, 2, 0, 'Koment2', 0),
    (4, NULL, 1, 1, 'Reply1Koment2', 0),
    (4, NULL, 3, 1, 'Reply2Koment2', 1);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `dita` int DEFAULT NULL,
  `orari` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Math',1,'5:00-6:00'),(2,'Biology',2,'7:00-8:00'),(3,'Anime Science',7,'16:00-18:00');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_request`
--

DROP TABLE IF EXISTS `friend_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_request` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_receiver` int unsigned DEFAULT NULL,
  `user_sender` int unsigned DEFAULT NULL,
  `state` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_receiver_idx` (`user_receiver`),
  KEY `fk_user_sender_idx` (`user_sender`),
  CONSTRAINT `fk_user_receiver` FOREIGN KEY (`user_receiver`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_sender` FOREIGN KEY (`user_sender`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_request`
--

LOCK TABLES `friend_request` WRITE;
/*!40000 ALTER TABLE `friend_request` DISABLE KEYS */;
INSERT INTO `friend_request` VALUES (4,2,4,0);
/*!40000 ALTER TABLE `friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `friend_user` int unsigned DEFAULT NULL,
  `friend_users` int unsigned DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fried_user_idx` (`friend_user`),
  KEY `fk_friend_users_idx` (`friend_users`),
  CONSTRAINT `fk_friend_user` FOREIGN KEY (`friend_user`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_friend_users` FOREIGN KEY (`friend_users`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (1,1,4,NULL),(2,4,1,NULL),(3,2,4,NULL),(4,4,2,NULL);
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_main` int unsigned DEFAULT NULL,
  `active` tinyint DEFAULT NULL,
  `notification` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_main_idx` (`user_main`),
  CONSTRAINT `fk_user_main` FOREIGN KEY (`user_main`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,1,0,'Artan sent you a friend request'),(2,4,0,'Andi accepted your  friend request'),(3,2,0,'Artan sent you a friend request'),(4,4,0,'Beni accepted your  friend request'),(5,1,0,'Whathaveidonetoyou12345 sent you a friend request'),(6,6,1,'Andi rejected  your friend request'),(7,4,0,'Andi has enrolled in Math'),(8,4,0,'Andi has enrolled in Math'),(9,1,1,'Artan has enrolled in Math'),(10,2,1,'Artan has enrolled in Math'),(11,1,1,'Artan has enrolled in Biology'),(12,2,1,'Artan has enrolled in Biology'),(13,1,1,'Artan has enrolled in Anime Science'),(14,2,1,'Artan has enrolled in Anime Science');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `Text` longtext NOT NULL,
  `likeNumber` int NOT NULL DEFAULT '0',
  `post_user` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_post_user_idx` (`post_user`),
  CONSTRAINT `fk_post_user` FOREIGN KEY (`post_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (3,'Testing',0,4),(4,'Testing',0,4),(6,'Wow this Math is Amaizing',0,1),(7,'Wow this Math is Amaizing',0,4),(8,'Wow this Biology is Amaizing',0,4),(9,'We are the champions',0,4),(10,'We are the champions',0,4),(11,'Wow this Anime Science is Amaizing',0,4);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply` (
  `id` int unsigned NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `like_number` int unsigned DEFAULT '0',
  `reply_post` int unsigned DEFAULT NULL,
  `reply_comment` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `reply_post_idx` (`reply_post`),
  KEY `fk_reply_comment_idx` (`reply_comment`),
  CONSTRAINT `fk_reply_comment` FOREIGN KEY (`reply_comment`) REFERENCES `comment` (`id`),
  CONSTRAINT `fk_reply_post` FOREIGN KEY (`reply_post`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `notification` tinyint NOT NULL DEFAULT '1',
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Andi','Andi',1,'default.png'),(2,'Beni','Beni',1,'default.png'),(3,'','',1,'default.png'),(4,'Artan','Artan',1,'default.png'),(5,'AlpacupacabraAlpacupacabra','AlpacupacabraAlpacupacabra',1,'default.png'),(6,'Whathaveidonetoyou12345','Whathaveidonetoyou12345',1,'default.png');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_course`
--

DROP TABLE IF EXISTS `user_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_course` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL,
  `course_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id_idx` (`course_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_course`
--

LOCK TABLES `user_course` WRITE;
/*!40000 ALTER TABLE `user_course` DISABLE KEYS */;
INSERT INTO `user_course` VALUES (11,2,1),(17,1,1),(18,4,1),(20,4,3);
/*!40000 ALTER TABLE `user_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'student'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-05  8:43:05
