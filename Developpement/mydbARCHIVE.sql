-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acte`
--

DROP TABLE IF EXISTS `acte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `acte` (
  `MedicalFolder_idFolder` int(11) NOT NULL,
  `idActe` varchar(45) NOT NULL,
  `Nom` varchar(45) DEFAULT NULL,
  `DateDebut` date DEFAULT NULL,
  `DateFin` date DEFAULT NULL,
  `Responsable` int(11) NOT NULL,
  `Prix` int(11) DEFAULT NULL,
  PRIMARY KEY (`idActe`),
  KEY `fk_Acte_MedicalFolder1_idx` (`MedicalFolder_idFolder`),
  KEY `fk_Acte_StaffMember1_idx` (`Responsable`),
  CONSTRAINT `fk_Acte_MedicalFolder1` FOREIGN KEY (`MedicalFolder_idFolder`) REFERENCES `dmp` (`UUID`),
  CONSTRAINT `fk_Acte_StaffMember1` FOREIGN KEY (`Responsable`) REFERENCES `staffmember` (`idStaffMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acte`
--

LOCK TABLES `acte` WRITE;
/*!40000 ALTER TABLE `acte` DISABLE KEYS */;
/*!40000 ALTER TABLE `acte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `affectation`
--

DROP TABLE IF EXISTS `affectation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `affectation` (
  `idAffectation` int(11) NOT NULL,
  `Symptome` varchar(255) DEFAULT NULL,
  `Acte_idActe` varchar(45) NOT NULL,
  `Unit_idHospital` int(11) NOT NULL,
  PRIMARY KEY (`idAffectation`,`Acte_idActe`,`Unit_idHospital`),
  KEY `fk_Affectation_Acte1_idx` (`Acte_idActe`),
  KEY `fk_Affectation_Unit1_idx` (`Unit_idHospital`),
  CONSTRAINT `fk_Affectation_Acte1` FOREIGN KEY (`Acte_idActe`) REFERENCES `acte` (`idActe`),
  CONSTRAINT `fk_Affectation_Unit1` FOREIGN KEY (`Unit_idHospital`) REFERENCES `unit` (`idHospital`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `affectation`
--

LOCK TABLES `affectation` WRITE;
/*!40000 ALTER TABLE `affectation` DISABLE KEYS */;
/*!40000 ALTER TABLE `affectation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allergie`
--

DROP TABLE IF EXISTS `allergie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `allergie` (
  `idAllergie` int(11) NOT NULL,
  `NomAllergie` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idAllergie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allergie`
--

LOCK TABLES `allergie` WRITE;
/*!40000 ALTER TABLE `allergie` DISABLE KEYS */;
/*!40000 ALTER TABLE `allergie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicationuser`
--

DROP TABLE IF EXISTS `applicationuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `applicationuser` (
  `Login` varchar(45) NOT NULL,
  `PassWord` varchar(45) DEFAULT NULL,
  `Mail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationuser`
--

LOCK TABLES `applicationuser` WRITE;
/*!40000 ALTER TABLE `applicationuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `applicationuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chambre`
--

DROP TABLE IF EXISTS `chambre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chambre` (
  `idChambre` int(11) NOT NULL,
  `HospitalUnit_idHospitalUnit` int(11) NOT NULL,
  `Hospital_idHospital` int(11) NOT NULL,
  PRIMARY KEY (`idChambre`),
  KEY `fk_Chambre_Hospital1_idx` (`Hospital_idHospital`),
  CONSTRAINT `fk_Chambre_Hospital1` FOREIGN KEY (`Hospital_idHospital`) REFERENCES `unit` (`idHospital`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chambre`
--

LOCK TABLES `chambre` WRITE;
/*!40000 ALTER TABLE `chambre` DISABLE KEYS */;
/*!40000 ALTER TABLE `chambre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chambre_has_person`
--

DROP TABLE IF EXISTS `chambre_has_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chambre_has_person` (
  `Nuité_IdNuité` int(11) NOT NULL,
  `Date_DateDébut` date NOT NULL,
  `DMP_UUID` int(11) NOT NULL,
  PRIMARY KEY (`Nuité_IdNuité`,`Date_DateDébut`,`DMP_UUID`),
  KEY `fk_Chambre_has_Person_Nuité1_idx` (`Nuité_IdNuité`),
  KEY `fk_Chambre_has_Person_Date1_idx` (`Date_DateDébut`),
  KEY `fk_Chambre_has_Person_DMP1_idx` (`DMP_UUID`),
  CONSTRAINT `fk_Chambre_has_Person_DMP1` FOREIGN KEY (`DMP_UUID`) REFERENCES `dmp` (`UUID`),
  CONSTRAINT `fk_Chambre_has_Person_Date1` FOREIGN KEY (`Date_DateDébut`) REFERENCES `date` (`DateDébut`),
  CONSTRAINT `fk_Chambre_has_Person_Nuité1` FOREIGN KEY (`Nuité_IdNuité`) REFERENCES `nuité` (`IdNuité`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chambre_has_person`
--

LOCK TABLES `chambre_has_person` WRITE;
/*!40000 ALTER TABLE `chambre_has_person` DISABLE KEYS */;
/*!40000 ALTER TABLE `chambre_has_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `champsobligatoire`
--

DROP TABLE IF EXISTS `champsobligatoire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `champsobligatoire` (
  `Name` varchar(45) NOT NULL,
  `Value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `champsobligatoire`
--

LOCK TABLES `champsobligatoire` WRITE;
/*!40000 ALTER TABLE `champsobligatoire` DISABLE KEYS */;
/*!40000 ALTER TABLE `champsobligatoire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `city` (
  `CPCity` int(11) NOT NULL,
  `NameCity` varchar(45) NOT NULL,
  `idCity` int(11) NOT NULL,
  PRIMARY KEY (`idCity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `constant`
--

DROP TABLE IF EXISTS `constant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `constant` (
  `idConstant` int(11) NOT NULL,
  `NameConstant` varchar(45) DEFAULT NULL,
  `ValueReferenceMin` decimal(10,0) DEFAULT NULL,
  `ValueReferenceMax` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`idConstant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `constant`
--

LOCK TABLES `constant` WRITE;
/*!40000 ALTER TABLE `constant` DISABLE KEYS */;
/*!40000 ALTER TABLE `constant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `date`
--

DROP TABLE IF EXISTS `date`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `date` (
  `DateDébut` date NOT NULL,
  `DateFin` date DEFAULT NULL,
  PRIMARY KEY (`DateDébut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `date`
--

LOCK TABLES `date` WRITE;
/*!40000 ALTER TABLE `date` DISABLE KEYS */;
/*!40000 ALTER TABLE `date` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `demandeexamen`
--

DROP TABLE IF EXISTS `demandeexamen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `demandeexamen` (
  `idDemandeExamen` int(11) NOT NULL,
  `nameDemande` varchar(45) DEFAULT NULL,
  `DMP_UUID` int(11) NOT NULL,
  `Unit_idHospital` int(11) NOT NULL,
  `StaffMember_idStaffMember` int(11) NOT NULL,
  `Valider` tinyint(4) DEFAULT NULL,
  `DateExamens` date DEFAULT NULL,
  PRIMARY KEY (`idDemandeExamen`),
  KEY `fk_DemandeExamen_DMP1_idx` (`DMP_UUID`),
  KEY `fk_DemandeExamen_Unit1_idx` (`Unit_idHospital`),
  KEY `fk_DemandeExamen_StaffMember1_idx` (`StaffMember_idStaffMember`),
  CONSTRAINT `fk_DemandeExamen_DMP1` FOREIGN KEY (`DMP_UUID`) REFERENCES `dmp` (`UUID`),
  CONSTRAINT `fk_DemandeExamen_StaffMember1` FOREIGN KEY (`StaffMember_idStaffMember`) REFERENCES `staffmember` (`idStaffMember`),
  CONSTRAINT `fk_DemandeExamen_Unit1` FOREIGN KEY (`Unit_idHospital`) REFERENCES `unit` (`idHospital`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demandeexamen`
--

LOCK TABLES `demandeexamen` WRITE;
/*!40000 ALTER TABLE `demandeexamen` DISABLE KEYS */;
/*!40000 ALTER TABLE `demandeexamen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `demoinformations`
--

DROP TABLE IF EXISTS `demoinformations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `demoinformations` (
  `NumSecu` int(11) NOT NULL,
  `Name` varchar(65) NOT NULL,
  `FirstName` varchar(65) NOT NULL,
  `BirthDate` date NOT NULL,
  `Adress` varchar(65) NOT NULL,
  `Sexe` char(1) NOT NULL,
  `Profession` varchar(45) DEFAULT NULL,
  `FamilialSituation` varchar(45) DEFAULT NULL,
  `PersonToContact_idPatient` int(11) NOT NULL,
  `PhoneNumber` varchar(10) DEFAULT NULL,
  `EnumNationnality_idNat` int(11) NOT NULL,
  `City_idCity` int(11) NOT NULL,
  `Poid` double DEFAULT NULL,
  `Taille` double DEFAULT NULL,
  `PhoneLandLine` varchar(10) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`NumSecu`),
  KEY `fk_DemoInformations_EnumNationnality1_idx` (`EnumNationnality_idNat`),
  KEY `fk_DemoInformations_City1_idx` (`City_idCity`),
  CONSTRAINT `fk_DemoInformations_City1` FOREIGN KEY (`City_idCity`) REFERENCES `city` (`idCity`),
  CONSTRAINT `fk_DemoInformations_EnumNationnality1` FOREIGN KEY (`EnumNationnality_idNat`) REFERENCES `enumnationnality` (`idNat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demoinformations`
--

LOCK TABLES `demoinformations` WRITE;
/*!40000 ALTER TABLE `demoinformations` DISABLE KEYS */;
/*!40000 ALTER TABLE `demoinformations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dmp`
--

DROP TABLE IF EXISTS `dmp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dmp` (
  `UUID` int(11) NOT NULL AUTO_INCREMENT,
  `Person_idPatient` int(11) NOT NULL,
  `idDoctor` int(11) NOT NULL,
  `DemoInformations_NumSecu` int(11) NOT NULL,
  PRIMARY KEY (`UUID`),
  KEY `fk_DMP_DemoInformations1_idx` (`DemoInformations_NumSecu`),
  CONSTRAINT `fk_DMP_DemoInformations1` FOREIGN KEY (`DemoInformations_NumSecu`) REFERENCES `demoinformations` (`NumSecu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dmp`
--

LOCK TABLES `dmp` WRITE;
/*!40000 ALTER TABLE `dmp` DISABLE KEYS */;
/*!40000 ALTER TABLE `dmp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documenttype`
--

DROP TABLE IF EXISTS `documenttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `documenttype` (
  `idDocumentType` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idDocumentType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documenttype`
--

LOCK TABLES `documenttype` WRITE;
/*!40000 ALTER TABLE `documenttype` DISABLE KEYS */;
/*!40000 ALTER TABLE `documenttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enumnationnality`
--

DROP TABLE IF EXISTS `enumnationnality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `enumnationnality` (
  `idNat` int(11) NOT NULL AUTO_INCREMENT,
  `nationnality` varchar(45) NOT NULL,
  PRIMARY KEY (`idNat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enumnationnality`
--

LOCK TABLES `enumnationnality` WRITE;
/*!40000 ALTER TABLE `enumnationnality` DISABLE KEYS */;
/*!40000 ALTER TABLE `enumnationnality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enumstafftype`
--

DROP TABLE IF EXISTS `enumstafftype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `enumstafftype` (
  `idEnumStaffType` int(11) NOT NULL AUTO_INCREMENT,
  `JobName` varchar(45) DEFAULT NULL,
  `WebsiteRigth` varchar(45) DEFAULT NULL,
  `TypeRigth_idTypeRigth` int(11) NOT NULL,
  PRIMARY KEY (`idEnumStaffType`),
  KEY `fk_EnumStaffType_TypeRigth1_idx` (`TypeRigth_idTypeRigth`),
  CONSTRAINT `fk_EnumStaffType_TypeRigth1` FOREIGN KEY (`TypeRigth_idTypeRigth`) REFERENCES `typerigth` (`idTypeRigth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enumstafftype`
--

LOCK TABLES `enumstafftype` WRITE;
/*!40000 ALTER TABLE `enumstafftype` DISABLE KEYS */;
/*!40000 ALTER TABLE `enumstafftype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `facture` (
  `Url` varchar(100) NOT NULL,
  `Unit_idHospital` int(11) NOT NULL,
  `DMP_UUID` int(11) NOT NULL,
  `DateEdition` date DEFAULT NULL,
  `DateDebut` date DEFAULT NULL,
  `DateFin` date DEFAULT NULL,
  `idFacture` int(11) NOT NULL,
  `TotalPrice` float DEFAULT NULL,
  PRIMARY KEY (`idFacture`),
  KEY `fk_Facture_Unit1_idx` (`Unit_idHospital`),
  KEY `fk_Facture_DMP1_idx` (`DMP_UUID`),
  CONSTRAINT `fk_Facture_DMP1` FOREIGN KEY (`DMP_UUID`) REFERENCES `dmp` (`UUID`),
  CONSTRAINT `fk_Facture_Unit1` FOREIGN KEY (`Unit_idHospital`) REFERENCES `unit` (`idHospital`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facture`
--

LOCK TABLES `facture` WRITE;
/*!40000 ALTER TABLE `facture` DISABLE KEYS */;
/*!40000 ALTER TABLE `facture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `langue`
--

DROP TABLE IF EXISTS `langue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `langue` (
  `idLangue` varchar(2) NOT NULL,
  `Pays` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idLangue`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `langue`
--

LOCK TABLES `langue` WRITE;
/*!40000 ALTER TABLE `langue` DISABLE KEYS */;
INSERT INTO `langue` VALUES ('en','anglais'),('fr','français');
/*!40000 ALTER TABLE `langue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicaldocument`
--

DROP TABLE IF EXISTS `medicaldocument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicaldocument` (
  `idMedicalDocument` int(11) NOT NULL,
  `DocumentName` varchar(45) NOT NULL,
  `IsADraft` tinyint(4) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `DocumentLink` varchar(45) DEFAULT NULL,
  `DocumentType_idDocumentType` int(11) NOT NULL,
  `ChampsObligatoire_Name` varchar(45) NOT NULL,
  `Stream_Extension` int(11) NOT NULL,
  `Acte_idActe` varchar(45) NOT NULL,
  PRIMARY KEY (`idMedicalDocument`),
  KEY `fk_MedicalDocument_DocumentType1_idx` (`DocumentType_idDocumentType`),
  KEY `fk_MedicalDocument_ChampsObligatoire1_idx` (`ChampsObligatoire_Name`),
  KEY `fk_MedicalDocument_Stream2_idx` (`Stream_Extension`),
  KEY `fk_MedicalDocument_Acte1_idx` (`Acte_idActe`),
  CONSTRAINT `fk_MedicalDocument_Acte1` FOREIGN KEY (`Acte_idActe`) REFERENCES `acte` (`idActe`),
  CONSTRAINT `fk_MedicalDocument_ChampsObligatoire1` FOREIGN KEY (`ChampsObligatoire_Name`) REFERENCES `champsobligatoire` (`Name`),
  CONSTRAINT `fk_MedicalDocument_DocumentType1` FOREIGN KEY (`DocumentType_idDocumentType`) REFERENCES `documenttype` (`idDocumentType`),
  CONSTRAINT `fk_MedicalDocument_Stream2` FOREIGN KEY (`Stream_Extension`) REFERENCES `streamextension` (`idStream`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicaldocument`
--

LOCK TABLES `medicaldocument` WRITE;
/*!40000 ALTER TABLE `medicaldocument` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicaldocument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalfolder_has_allergie`
--

DROP TABLE IF EXISTS `medicalfolder_has_allergie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicalfolder_has_allergie` (
  `Allergie_idAllergie` int(11) NOT NULL,
  `MedicalFolder_idFolder` int(11) NOT NULL,
  PRIMARY KEY (`Allergie_idAllergie`,`MedicalFolder_idFolder`),
  KEY `fk_Allergie_has_MedicalFolder_Allergie1_idx` (`Allergie_idAllergie`),
  KEY `fk_Allergie_has_MedicalFolder_MedicalFolder1_idx` (`MedicalFolder_idFolder`),
  CONSTRAINT `fk_Allergie_has_MedicalFolder_Allergie1` FOREIGN KEY (`Allergie_idAllergie`) REFERENCES `allergie` (`idAllergie`),
  CONSTRAINT `fk_Allergie_has_MedicalFolder_MedicalFolder1` FOREIGN KEY (`MedicalFolder_idFolder`) REFERENCES `dmp` (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalfolder_has_allergie`
--

LOCK TABLES `medicalfolder_has_allergie` WRITE;
/*!40000 ALTER TABLE `medicalfolder_has_allergie` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicalfolder_has_allergie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalfolder_has_vaccination`
--

DROP TABLE IF EXISTS `medicalfolder_has_vaccination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicalfolder_has_vaccination` (
  `MedicalFolder_idFolder` int(11) NOT NULL,
  `Vaccination_idVaccination` int(11) NOT NULL,
  PRIMARY KEY (`MedicalFolder_idFolder`,`Vaccination_idVaccination`),
  KEY `fk_MedicalFolder_has_Vaccination_Vaccination1_idx` (`Vaccination_idVaccination`),
  KEY `fk_MedicalFolder_has_Vaccination_MedicalFolder1_idx` (`MedicalFolder_idFolder`),
  CONSTRAINT `fk_MedicalFolder_has_Vaccination_MedicalFolder1` FOREIGN KEY (`MedicalFolder_idFolder`) REFERENCES `dmp` (`UUID`),
  CONSTRAINT `fk_MedicalFolder_has_Vaccination_Vaccination1` FOREIGN KEY (`Vaccination_idVaccination`) REFERENCES `vaccination` (`idVaccination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalfolder_has_vaccination`
--

LOCK TABLES `medicalfolder_has_vaccination` WRITE;
/*!40000 ALTER TABLE `medicalfolder_has_vaccination` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicalfolder_has_vaccination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicament`
--

DROP TABLE IF EXISTS `medicament`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicament` (
  `idMedicament` int(11) NOT NULL,
  `NameMedicament` varchar(45) DEFAULT NULL,
  `prix` decimal(10,0) DEFAULT NULL,
  `Medicamentcol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMedicament`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicament`
--

LOCK TABLES `medicament` WRITE;
/*!40000 ALTER TABLE `medicament` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicament` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicament_has_soins infirmiers`
--

DROP TABLE IF EXISTS `medicament_has_soins infirmiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicament_has_soins infirmiers` (
  `Medicament_idMedicament` int(11) NOT NULL,
  `MedicalFolder_idFolder` int(11) NOT NULL,
  `Date_DateDébut` date NOT NULL,
  PRIMARY KEY (`Medicament_idMedicament`,`MedicalFolder_idFolder`,`Date_DateDébut`),
  KEY `fk_Medicament_has_Soins infirmiers_Medicament1_idx` (`Medicament_idMedicament`),
  KEY `fk_Medicament_has_Soins infirmiers_MedicalFolder1_idx` (`MedicalFolder_idFolder`),
  KEY `fk_Medicament_has_Soins infirmiers_Date1_idx` (`Date_DateDébut`),
  CONSTRAINT `fk_Medicament_has_Soins infirmiers_Date1` FOREIGN KEY (`Date_DateDébut`) REFERENCES `date` (`DateDébut`),
  CONSTRAINT `fk_Medicament_has_Soins infirmiers_MedicalFolder1` FOREIGN KEY (`MedicalFolder_idFolder`) REFERENCES `dmp` (`UUID`),
  CONSTRAINT `fk_Medicament_has_Soins infirmiers_Medicament1` FOREIGN KEY (`Medicament_idMedicament`) REFERENCES `medicament` (`idMedicament`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicament_has_soins infirmiers`
--

LOCK TABLES `medicament_has_soins infirmiers` WRITE;
/*!40000 ALTER TABLE `medicament_has_soins infirmiers` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicament_has_soins infirmiers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nuité`
--

DROP TABLE IF EXISTS `nuité`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `nuité` (
  `IdNuité` int(11) NOT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Price` decimal(10,0) DEFAULT NULL,
  `Chambre_idChambre1` int(11) NOT NULL,
  PRIMARY KEY (`IdNuité`),
  KEY `fk_Nuité_Chambre1_idx` (`Chambre_idChambre1`),
  CONSTRAINT `fk_Nuité_Chambre1` FOREIGN KEY (`Chambre_idChambre1`) REFERENCES `chambre` (`idChambre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nuité`
--

LOCK TABLES `nuité` WRITE;
/*!40000 ALTER TABLE `nuité` DISABLE KEYS */;
/*!40000 ALTER TABLE `nuité` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `page` (
  `idPage` int(11) NOT NULL,
  `LinkPage` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page`
--

LOCK TABLES `page` WRITE;
/*!40000 ALTER TABLE `page` DISABLE KEYS */;
/*!40000 ALTER TABLE `page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page_has_typerigth`
--

DROP TABLE IF EXISTS `page_has_typerigth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `page_has_typerigth` (
  `Page_idPage` int(11) NOT NULL,
  `TypeRigth_idTypeRigth` int(11) NOT NULL,
  PRIMARY KEY (`Page_idPage`,`TypeRigth_idTypeRigth`),
  KEY `fk_Page_has_TypeRigth_TypeRigth1_idx` (`TypeRigth_idTypeRigth`),
  KEY `fk_Page_has_TypeRigth_Page1_idx` (`Page_idPage`),
  CONSTRAINT `fk_Page_has_TypeRigth_Page1` FOREIGN KEY (`Page_idPage`) REFERENCES `page` (`idPage`),
  CONSTRAINT `fk_Page_has_TypeRigth_TypeRigth1` FOREIGN KEY (`TypeRigth_idTypeRigth`) REFERENCES `typerigth` (`idTypeRigth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page_has_typerigth`
--

LOCK TABLES `page_has_typerigth` WRITE;
/*!40000 ALTER TABLE `page_has_typerigth` DISABLE KEYS */;
/*!40000 ALTER TABLE `page_has_typerigth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsable`
--

DROP TABLE IF EXISTS `responsable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `responsable` (
  `DMP_UUID` int(11) NOT NULL,
  `StaffMember_idStaffMember` int(11) NOT NULL,
  PRIMARY KEY (`DMP_UUID`,`StaffMember_idStaffMember`),
  KEY `fk_DMP_has_StaffMember_StaffMember1_idx` (`StaffMember_idStaffMember`),
  KEY `fk_DMP_has_StaffMember_DMP1_idx` (`DMP_UUID`),
  CONSTRAINT `fk_DMP_has_StaffMember_DMP1` FOREIGN KEY (`DMP_UUID`) REFERENCES `dmp` (`UUID`),
  CONSTRAINT `fk_DMP_has_StaffMember_StaffMember1` FOREIGN KEY (`StaffMember_idStaffMember`) REFERENCES `staffmember` (`idStaffMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsable`
--

LOCK TABLES `responsable` WRITE;
/*!40000 ALTER TABLE `responsable` DISABLE KEYS */;
/*!40000 ALTER TABLE `responsable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spécialité`
--

DROP TABLE IF EXISTS `spécialité`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `spécialité` (
  `idSpécialité` int(11) NOT NULL,
  `NomSpé` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idSpécialité`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spécialité`
--

LOCK TABLES `spécialité` WRITE;
/*!40000 ALTER TABLE `spécialité` DISABLE KEYS */;
/*!40000 ALTER TABLE `spécialité` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staffmember_has_spécialité`
--

DROP TABLE IF EXISTS `staffmember_has_spécialité`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `staffmember_has_spécialité` (
  `StaffMember_idStaffMember` int(11) NOT NULL,
  `Spécialité_idSpécialité` int(11) NOT NULL,
  `OrganismeObtention` varchar(45) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`StaffMember_idStaffMember`,`Spécialité_idSpécialité`),
  KEY `fk_StaffMember_has_Spécialité_Spécialité1_idx` (`Spécialité_idSpécialité`),
  KEY `fk_StaffMember_has_Spécialité_StaffMember1_idx` (`StaffMember_idStaffMember`),
  CONSTRAINT `fk_StaffMember_has_Spécialité_Spécialité1` FOREIGN KEY (`Spécialité_idSpécialité`) REFERENCES `spécialité` (`idSpécialité`),
  CONSTRAINT `fk_StaffMember_has_Spécialité_StaffMember1` FOREIGN KEY (`StaffMember_idStaffMember`) REFERENCES `staffmember` (`idStaffMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffmember_has_spécialité`
--

LOCK TABLES `staffmember_has_spécialité` WRITE;
/*!40000 ALTER TABLE `staffmember_has_spécialité` DISABLE KEYS */;
/*!40000 ALTER TABLE `staffmember_has_spécialité` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `streamextension`
--

DROP TABLE IF EXISTS `streamextension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `streamextension` (
  `idStream` int(11) NOT NULL,
  `LinkStream` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idStream`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `streamextension`
--

LOCK TABLES `streamextension` WRITE;
/*!40000 ALTER TABLE `streamextension` DISABLE KEYS */;
/*!40000 ALTER TABLE `streamextension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `string`
--

DROP TABLE IF EXISTS `string`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `string` (
  `idString` varchar(45) NOT NULL,
  `Langue_idLangue` varchar(2) NOT NULL,
  `StringContent` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idString`,`Langue_idLangue`),
  KEY `fk_String_Langue1_idx` (`Langue_idLangue`),
  CONSTRAINT `fk_String_Langue1` FOREIGN KEY (`Langue_idLangue`) REFERENCES `langue` (`idLangue`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `string`
--

LOCK TABLES `string` WRITE;
/*!40000 ALTER TABLE `string` DISABLE KEYS */;
INSERT INTO `string` VALUES ('test','en','this is a test'),('test','fr','ceci est un test');
/*!40000 ALTER TABLE `string` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typerigth`
--

DROP TABLE IF EXISTS `typerigth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `typerigth` (
  `idTypeRigth` int(11) NOT NULL,
  `NameTypeRigth` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTypeRigth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typerigth`
--

LOCK TABLES `typerigth` WRITE;
/*!40000 ALTER TABLE `typerigth` DISABLE KEYS */;
/*!40000 ALTER TABLE `typerigth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `unit` (
  `idHospital` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `Director` int(11) NOT NULL,
  `rataché` int(11) NOT NULL,
  PRIMARY KEY (`idHospital`),
  KEY `fk_Hospital_StaffMember1_idx` (`Director`),
  KEY `fk_Hospital_Hospital1_idx` (`rataché`),
  CONSTRAINT `fk_Hospital_Hospital1` FOREIGN KEY (`rataché`) REFERENCES `unit` (`idHospital`),
  CONSTRAINT `fk_Hospital_StaffMember1` FOREIGN KEY (`Director`) REFERENCES `staffmember` (`idStaffMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaccination`
--

DROP TABLE IF EXISTS `vaccination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vaccination` (
  `idVaccination` int(11) NOT NULL,
  `NomVAccin` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idVaccination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaccination`
--

LOCK TABLES `vaccination` WRITE;
/*!40000 ALTER TABLE `vaccination` DISABLE KEYS */;
/*!40000 ALTER TABLE `vaccination` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-18 13:21:12
