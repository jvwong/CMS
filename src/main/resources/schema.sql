CREATE DATABASE cms DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';
CREATE USER 'cmsUser'@'localhost' IDENTIFIED BY 'cmsPassword';
GRANT ALL ON cms.* TO 'cmsUser'@'localhost';

USE cms;

CREATE TABLE CMSUser (
  UserId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Revision BIGINT UNSIGNED NOT NULL,
  CreatedDate TIMESTAMP NULL,
  LastModifiedDate TIMESTAMP NULL,
  Username VARCHAR(30) NOT NULL UNIQUE,
  Password VARCHAR(100) NOT NULL,
  FullName VARCHAR(100) NOT NULL,
  Role VARCHAR(30) NULL,
  Email VARCHAR(50) NOT NULL UNIQUE
  ) ENGINE = InnoDB;

CREATE TABLE Article (
  ArticleId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Revision BIGINT UNSIGNED NOT NULL,
  CreatedDate TIMESTAMP NULL,
  LastModifiedDate TIMESTAMP NULL,
  CreatedBy VARCHAR(100) NULL,
  UpdatedBy VARCHAR(100) NULL,
  CMSUserId BIGINT UNSIGNED NOT NULL,
  Title VARCHAR(100) NOT NULL,
  Description VARCHAR(100) NULL,
  Keywords VARCHAR(100) NULL,
  CONSTRAINT Article_CMSUser FOREIGN KEY (CMSUserId) REFERENCES CMSUser(UserId) ON DELETE CASCADE
  ) ENGINE = InnoDB;
  
CREATE TABLE Page (
  PageId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Revision BIGINT UNSIGNED NOT NULL,
  CreatedDate TIMESTAMP NULL,
  LastModifiedDate TIMESTAMP NULL,
  CreatedBy VARCHAR(100) NULL,
  UpdatedBy VARCHAR(100) NULL,
  ArticleId BIGINT UNSIGNED NOT NULL,
  Content VARCHAR(1000) NULL,
  CONSTRAINT Page_Article FOREIGN KEY (ArticleId) REFERENCES Article(ArticleId) ON DELETE CASCADE
) ENGINE = InnoDB;