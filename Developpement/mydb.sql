-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le :  sam. 15 fév. 2020 à 13:39
-- Version du serveur :  8.0.18
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bd`
--

-- --------------------------------------------------------

--
-- Structure de la table `acte`
--

DROP TABLE IF EXISTS `acte`;
CREATE TABLE IF NOT EXISTS `acte` (
  `MedicalFolder_idFolder` int(11) NOT NULL,
  `Nom` varchar(45) DEFAULT NULL,
  `DateDebut` date DEFAULT NULL,
  `DateFin` date DEFAULT NULL,
  `Responsable` int(11) NOT NULL,
  `Prix` int(11) DEFAULT NULL,
  `DocumentLink` varchar(45) DEFAULT NULL,
  `IsADraft` tinyint(1) DEFAULT NULL,
  `DocumentType_idDocumentType` int(11) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `idActe` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idActe`),
  KEY `fk_Acte_MedicalFolder1_idx` (`MedicalFolder_idFolder`),
  KEY `fk_Acte_StaffMember1_idx` (`Responsable`),
  KEY `DocumentType_idDocumentType` (`DocumentType_idDocumentType`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `acte`
--

INSERT INTO `acte` (`MedicalFolder_idFolder`, `Nom`, `DateDebut`, `DateFin`, `Responsable`, `Prix`, `DocumentLink`, `IsADraft`, `DocumentType_idDocumentType`, `Description`, `idActe`) VALUES
(1, 'Coronavirus', '0001-01-01', '0001-01-01', 1, 250, '//document//link', 1, 1, NULL, 1),
(1, 'Greffe de poumon', '2019-10-10', NULL, 2, NULL, 'ici', 1, 1, NULL, 2),
(1, 'Coro2', '2020-02-13', NULL, 1, NULL, 'PatientDirectory\\Patient1\\defaultName1', 0, 1, 'ici', 8);

-- --------------------------------------------------------

--
-- Structure de la table `actedocument`
--

DROP TABLE IF EXISTS `actedocument`;
CREATE TABLE IF NOT EXISTS `actedocument` (
  `MedicalFolder_idFolder` int(11) NOT NULL,
  `idActe` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(45) DEFAULT NULL,
  `DateDebut` date DEFAULT NULL,
  `Responsable` int(11) DEFAULT NULL,
  `Description` varchar(45) DEFAULT NULL,
  `IsADraft` tinyint(1) DEFAULT NULL,
  `DocumentType_idDocumentType` int(11) NOT NULL,
  PRIMARY KEY (`idActe`),
  KEY `acte_ibfk_1` (`DocumentType_idDocumentType`),
  KEY `fk_Acte_MedicalFolder1` (`MedicalFolder_idFolder`),
  KEY `fk_Acte_StaffMember1` (`Responsable`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `actedocument`
--

INSERT INTO `actedocument` (`MedicalFolder_idFolder`, `idActe`, `Nom`, `DateDebut`, `Responsable`, `Description`, `IsADraft`, `DocumentType_idDocumentType`) VALUES
(1, 1, 'Coronavirus', '2020-01-01', 1, 'Symptome de coronavirus', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `affectation`
--

DROP TABLE IF EXISTS `affectation`;
CREATE TABLE IF NOT EXISTS `affectation` (
  `idAffectation` int(11) NOT NULL,
  `Symptome` varchar(255) DEFAULT NULL,
  `Unit_idHospital` int(11) NOT NULL,
  `Acte_idActe` int(11) NOT NULL,
  PRIMARY KEY (`idAffectation`,`Unit_idHospital`,`Acte_idActe`) USING BTREE,
  KEY `fk_Affectation_Unit1_idx` (`Unit_idHospital`),
  KEY `Acte_idActe` (`Acte_idActe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `affectation`
--

INSERT INTO `affectation` (`idAffectation`, `Symptome`, `Unit_idHospital`, `Acte_idActe`) VALUES
(0, 'Fièvre', 16, 1);

-- --------------------------------------------------------

--
-- Structure de la table `applicationuser`
--

DROP TABLE IF EXISTS `applicationuser`;
CREATE TABLE IF NOT EXISTS `applicationuser` (
  `Login` varchar(45) NOT NULL,
  `PassWord` varchar(45) DEFAULT NULL,
  `Mail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `applicationuser`
--

INSERT INTO `applicationuser` (`Login`, `PassWord`, `Mail`) VALUES
('dmaul', 'dMaul', 'dMaul@Aphp.fr'),
('jBapier', 'jBapier', 'jBapier@aphp.fr'),
('mDupont', 'mDupont', 'mDupont@Aphp.fr');

-- --------------------------------------------------------

--
-- Structure de la table `city`
--

DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
  `CPCity` int(11) NOT NULL,
  `NameCity` varchar(45) NOT NULL,
  `idCity` int(11) NOT NULL,
  PRIMARY KEY (`idCity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `city`
--

INSERT INTO `city` (`CPCity`, `NameCity`, `idCity`) VALUES
(69600, 'OULLINS', 1),
(75013, 'Paris 13', 13);

-- --------------------------------------------------------

--
-- Structure de la table `demoinformations`
--

DROP TABLE IF EXISTS `demoinformations`;
CREATE TABLE IF NOT EXISTS `demoinformations` (
  `NumSecu` bigint(20) NOT NULL,
  `Name` varchar(65) NOT NULL,
  `FirstName` varchar(65) NOT NULL,
  `BirthDate` date NOT NULL,
  `Adress` varchar(65) NOT NULL,
  `Sexe` char(1) NOT NULL,
  `Profession` varchar(45) DEFAULT NULL,
  `FamilialSituation` varchar(45) DEFAULT NULL,
  `PersonToContact_idPatient` int(11) DEFAULT NULL,
  `PhoneNumber` varchar(11) DEFAULT NULL,
  `EnumNationnality_idNat` int(11) DEFAULT NULL,
  `City_idCity` int(11) DEFAULT NULL,
  `Poid` double DEFAULT NULL,
  `Taille` double DEFAULT NULL,
  PRIMARY KEY (`NumSecu`),
  KEY `fk_DemoInformations_EnumNationnality1_idx` (`EnumNationnality_idNat`),
  KEY `fk_DemoInformations_City1_idx` (`City_idCity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `demoinformations`
--

INSERT INTO `demoinformations` (`NumSecu`, `Name`, `FirstName`, `BirthDate`, `Adress`, `Sexe`, `Profession`, `FamilialSituation`, `PersonToContact_idPatient`, `PhoneNumber`, `EnumNationnality_idNat`, `City_idCity`, `Poid`, `Taille`) VALUES
(20098765432, 'Jean', 'Bapier', '1978-02-03', '187 Rue de Bapoiur', 'H', NULL, 'Marié', NULL, '0987654321', NULL, NULL, NULL, NULL),
(170056960019, 'Dark', 'Jean', '1970-05-01', '4 boulevard Amiral Courbet', 'H', 'Médecin', 'Marié', NULL, '0789460620', 0, 1, 75, 1),
(270056960012, 'Dupont', 'Alice', '1970-05-01', '44 boulevard Amiral Courbet', 'F', 'Peintre', 'Célibataire', NULL, '0789415620', 0, 1, 67, 1),
(179093456544545, 'Dupont', 'Maurice', '1979-09-01', '128 Avenu du jardin Fleuri, Paris 75013', 'M', 'Medecin', 'Divorcé', NULL, '0651676789', 0, 13, 89, 1);

-- --------------------------------------------------------

--
-- Structure de la table `dmp`
--

DROP TABLE IF EXISTS `dmp`;
CREATE TABLE IF NOT EXISTS `dmp` (
  `UUID` int(11) NOT NULL AUTO_INCREMENT,
  `idDoctor` int(11) NOT NULL,
  `DemoInformations_NumSecu` bigint(20) NOT NULL,
  PRIMARY KEY (`UUID`),
  KEY `fk_DMP_DemoInformations1_idx` (`DemoInformations_NumSecu`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `dmp`
--

INSERT INTO `dmp` (`UUID`, `idDoctor`, `DemoInformations_NumSecu`) VALUES
(1, 1, 270056960012);

-- --------------------------------------------------------

--
-- Structure de la table `documenttype`
--

DROP TABLE IF EXISTS `documenttype`;
CREATE TABLE IF NOT EXISTS `documenttype` (
  `idDocumentType` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idDocumentType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `documenttype`
--

INSERT INTO `documenttype` (`idDocumentType`, `Name`) VALUES
(1, 'Rapport d\'analyse');

-- --------------------------------------------------------

--
-- Structure de la table `enumnationnality`
--

DROP TABLE IF EXISTS `enumnationnality`;
CREATE TABLE IF NOT EXISTS `enumnationnality` (
  `idNat` int(11) NOT NULL AUTO_INCREMENT,
  `nationnality` varchar(45) NOT NULL,
  PRIMARY KEY (`idNat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `enumnationnality`
--

INSERT INTO `enumnationnality` (`idNat`, `nationnality`) VALUES
(0, 'Française');

-- --------------------------------------------------------

--
-- Structure de la table `enumstafftype`
--

DROP TABLE IF EXISTS `enumstafftype`;
CREATE TABLE IF NOT EXISTS `enumstafftype` (
  `idEnumStaffType` int(11) NOT NULL AUTO_INCREMENT,
  `JobName` varchar(45) DEFAULT NULL,
  `WebsiteRigth` varchar(45) DEFAULT NULL,
  `TypeRigth_idTypeRigth` int(11) NOT NULL,
  PRIMARY KEY (`idEnumStaffType`),
  KEY `fk_EnumStaffType_TypeRigth1_idx` (`TypeRigth_idTypeRigth`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `enumstafftype`
--

INSERT INTO `enumstafftype` (`idEnumStaffType`, `JobName`, `WebsiteRigth`, `TypeRigth_idTypeRigth`) VALUES
(1, 'Médecin', 'Publier Actes ...', 1);

-- --------------------------------------------------------

--
-- Structure de la table `responsable`
--

DROP TABLE IF EXISTS `responsable`;
CREATE TABLE IF NOT EXISTS `responsable` (
  `StaffMember_idStaffMember` int(11) NOT NULL,
  `DMP_UUID` int(11) NOT NULL,
  PRIMARY KEY (`StaffMember_idStaffMember`,`DMP_UUID`),
  KEY `fk_StaffMember_has_DMP_DMP1_idx` (`DMP_UUID`),
  KEY `fk_StaffMember_has_DMP_StaffMember1_idx` (`StaffMember_idStaffMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `staffmember`
--

DROP TABLE IF EXISTS `staffmember`;
CREATE TABLE IF NOT EXISTS `staffmember` (
  `idStaffMember` int(11) NOT NULL,
  `Skills` longtext,
  `EnumStaffType_idEnumStaffType` int(11) NOT NULL,
  `DemoInformations_NumSecu` bigint(20) NOT NULL,
  `IBAN` varchar(27) DEFAULT NULL,
  `BIC` varchar(11) DEFAULT NULL,
  `Hospital_idHospital` int(11) NOT NULL,
  `NbBureau` int(11) DEFAULT NULL,
  `Login` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idStaffMember`),
  KEY `fk_StaffMember_EnumStaffType1_idx` (`EnumStaffType_idEnumStaffType`),
  KEY `fk_StaffMember_DemoInformations1_idx` (`DemoInformations_NumSecu`),
  KEY `fk_StaffMember_Hospital1_idx` (`Hospital_idHospital`),
  KEY `Login` (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `staffmember`
--

INSERT INTO `staffmember` (`idStaffMember`, `Skills`, `EnumStaffType_idEnumStaffType`, `DemoInformations_NumSecu`, `IBAN`, `BIC`, `Hospital_idHospital`, `NbBureau`, `Login`) VALUES
(1, 'Soigne des gens', 1, 170056960019, 'FR9717569000702419328881N33', 'CIE MONEGAS', 1, 1, 'dmaul'),
(2, 'Cardiologue', 1, 179093456544545, NULL, NULL, 15, 666, 'mDupont');

-- --------------------------------------------------------

--
-- Structure de la table `streamextension`
--

DROP TABLE IF EXISTS `streamextension`;
CREATE TABLE IF NOT EXISTS `streamextension` (
  `idStream` int(11) NOT NULL,
  `LinkStream` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idStream`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `streamextension`
--

INSERT INTO `streamextension` (`idStream`, `LinkStream`) VALUES
(1, 'www.linkStream.fr');

-- --------------------------------------------------------

--
-- Structure de la table `unit`
--

DROP TABLE IF EXISTS `unit`;
CREATE TABLE IF NOT EXISTS `unit` (
  `idHospital` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `Director` int(11) DEFAULT NULL,
  `ratache` int(11) DEFAULT NULL,
  PRIMARY KEY (`idHospital`),
  KEY `fk_Hospital_StaffMember1_idx` (`Director`),
  KEY `fk_Hospital_Hospital1_idx` (`ratache`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `unit`
--

INSERT INTO `unit` (`idHospital`, `Name`, `Type`, `Director`, `ratache`) VALUES
(1, 'Hôpital de tests', 1, 1, 17),
(11, 'Kremlin Bicetre', 1, 1, 17),
(12, 'Coeur/Poumon', 2, 2, 11),
(15, 'Cardiologie', 3, 2, 12),
(16, 'pneumologie', 3, NULL, 12),
(17, 'APHP', NULL, NULL, NULL);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `acte`
--
ALTER TABLE `acte`
  ADD CONSTRAINT `acte_ibfk_1` FOREIGN KEY (`DocumentType_idDocumentType`) REFERENCES `documenttype` (`idDocumentType`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_Acte_MedicalFolder1` FOREIGN KEY (`MedicalFolder_idFolder`) REFERENCES `dmp` (`UUID`),
  ADD CONSTRAINT `fk_Acte_StaffMember1` FOREIGN KEY (`Responsable`) REFERENCES `staffmember` (`idStaffMember`);

--
-- Contraintes pour la table `affectation`
--
ALTER TABLE `affectation`
  ADD CONSTRAINT `affectation_ibfk_1` FOREIGN KEY (`Unit_idHospital`) REFERENCES `unit` (`idHospital`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `affectation_ibfk_2` FOREIGN KEY (`Acte_idActe`) REFERENCES `acte` (`idActe`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `demoinformations`
--
ALTER TABLE `demoinformations`
  ADD CONSTRAINT `fk_DemoInformations_City1` FOREIGN KEY (`City_idCity`) REFERENCES `city` (`idCity`),
  ADD CONSTRAINT `fk_DemoInformations_EnumNationnality1` FOREIGN KEY (`EnumNationnality_idNat`) REFERENCES `enumnationnality` (`idNat`);

--
-- Contraintes pour la table `dmp`
--
ALTER TABLE `dmp`
  ADD CONSTRAINT `fk_DMP_DemoInformations1` FOREIGN KEY (`DemoInformations_NumSecu`) REFERENCES `demoinformations` (`NumSecu`);

--
-- Contraintes pour la table `responsable`
--
ALTER TABLE `responsable`
  ADD CONSTRAINT `fk_StaffMember_has_DMP_DMP1` FOREIGN KEY (`DMP_UUID`) REFERENCES `dmp` (`UUID`),
  ADD CONSTRAINT `fk_StaffMember_has_DMP_StaffMember1` FOREIGN KEY (`StaffMember_idStaffMember`) REFERENCES `staffmember` (`idStaffMember`);

--
-- Contraintes pour la table `staffmember`
--
ALTER TABLE `staffmember`
  ADD CONSTRAINT `fk_StaffMember_DemoInformations1` FOREIGN KEY (`DemoInformations_NumSecu`) REFERENCES `demoinformations` (`NumSecu`),
  ADD CONSTRAINT `fk_StaffMember_EnumStaffType1` FOREIGN KEY (`EnumStaffType_idEnumStaffType`) REFERENCES `enumstafftype` (`idEnumStaffType`),
  ADD CONSTRAINT `fk_StaffMember_Hospital1` FOREIGN KEY (`Hospital_idHospital`) REFERENCES `unit` (`idHospital`),
  ADD CONSTRAINT `staffmember_ibfk_1` FOREIGN KEY (`Login`) REFERENCES `applicationuser` (`Login`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `unit`
--
ALTER TABLE `unit`
  ADD CONSTRAINT `fk_Hospital_Hospital1` FOREIGN KEY (`ratache`) REFERENCES `unit` (`idHospital`),
  ADD CONSTRAINT `fk_Hospital_StaffMember1` FOREIGN KEY (`Director`) REFERENCES `staffmember` (`idStaffMember`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
