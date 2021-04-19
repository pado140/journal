
create TABLE IF NOT EXISTS journalisation.affiche(
Id_affiche INT(10) NOT NULL 
 ,Libelle VARCHAR(50) NULL 
 ,Infos TEXT(65535) NULL 
 ,Ref_type_concept INT(10) NULL 
 ,user_id INT(10) NULL 
 ,PRIMARY KEY(Id_affiche)
);
create TABLE IF NOT EXISTS journalisation.auteur(
id INT(10) NOT NULL AUTO_INCREMENT
 ,nom VARCHAR(50) NULL 
 ,prenom VARCHAR(50) NULL 
 ,Notice TEXT(65535) NULL 
 ,user_id INT(10) NULL 
 ,pathImage TEXT(65535) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.concept(
id INT(10) NOT NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.conception(
Id_conception INT(10) NOT NULL 
 ,Date_debut_concept Date NULL 
 ,Date_fin_concept Date NULL 
 ,Format_concept VARCHAR(50) NULL 
 ,Ref_type_concept INT(10) NULL 
 ,user_id INT(10) NULL 
 ,PRIMARY KEY(Id_conception)
);
create TABLE IF NOT EXISTS journalisation.concevoir(
Ref_conception INT(10) NULL 
 ,Ref_graphiste INT(10) NULL 
 ,user_id INT(10) NULL 
);
create TABLE IF NOT EXISTS journalisation.correcteur(
id INT(10) NOT NULL AUTO_INCREMENT
 ,nom VARCHAR(50) NULL 
 ,prenom VARCHAR(50) NULL 
 ,users_id INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.correction(
Id_correction INT(10) NOT NULL 
 ,Date_debut_C Date NULL 
 ,Date_fin_C Date NULL 
 ,Ref_livre INT(10) NULL 
 ,user_id INT(10) NULL 
 ,PRIMARY KEY(Id_correction)
);
create TABLE IF NOT EXISTS journalisation.corriger(
Ref_correcton INT(10) NULL 
 ,Ref_correcteur INT(10) NULL 
 ,user_id INT(10) NULL 
);
create TABLE IF NOT EXISTS journalisation.cover(
id INT(10) NOT NULL 
 ,livres_id INT(10) NULL 
 ,concept_id INT(10) NULL 
 ,created Datetime NULL 
 ,cover_id INT(10) NULL 
 ,user_id INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.diriger(
Ref_auteur INT(10) NULL 
 ,Ref_livre INT(10) NULL 
);
create TABLE IF NOT EXISTS journalisation.docs(
id INT(10) NOT NULL AUTO_INCREMENT
 ,path TEXT(65535) NULL 
 ,name TEXT(65535) NULL 
 ,pages INT(10) NULL 
 ,mots INT(10) NULL 
 ,characters INT(10) NULL 
 ,allcharacters INT(10) NULL 
 ,created Date NULL 
 ,modified Date NULL 
 ,creator VARCHAR(45) NULL 
 ,description TEXT(65535) NULL 
 ,modifiedby VARCHAR(45) NULL 
 ,revision INT(10) NULL 
 ,format VARCHAR(45) NULL 
 ,livres_id INT(10) NULL 
 ,users_id INT(10) NULL 
 ,isarchive BIT(1) NULL 
 ,no INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.ecrire(
Ref_auteur INT(10) NULL 
 ,Ref_livre INT(10) NULL 
);
create TABLE IF NOT EXISTS journalisation.etape_c(
Id_etape_C INT(10) NOT NULL 
 ,Date_etape_c Date NULL 
 ,Remarque_etape_c TEXT(65535) NULL 
 ,Ref_correction INT(10) NULL 
 ,PRIMARY KEY(Id_etape_C)
);
create TABLE IF NOT EXISTS journalisation.etape_v(
Id_etape_V INT(10) NOT NULL 
 ,Date_etape_V Date NULL 
 ,Remarque_etape_V TEXT(65535) NULL 
 ,Ref_verification INT(10) NULL 
 ,PRIMARY KEY(Id_etape_V)
);
create TABLE IF NOT EXISTS journalisation.genre(
id INT(10) NOT NULL AUTO_INCREMENT
 ,nom VARCHAR(50) NULL 
 ,created Datetime NULL 
 ,user_id INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.graphiste(
id INT(10) NOT NULL AUTO_INCREMENT
 ,nom VARCHAR(50) NULL 
 ,prenom VARCHAR(50) NULL 
 ,users_id INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.groups(
id INT(10) NOT NULL AUTO_INCREMENT
 ,name VARCHAR(45) NULL 
 ,description VARCHAR(45) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.groups_roles(
id INT(10) NOT NULL AUTO_INCREMENT
 ,groups_id INT(10) NULL 
 ,roles_id INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.historicite(
id INT(10) NOT NULL AUTO_INCREMENT
 ,users_id INT(10) NULL 
 ,created Datetime NULL 
 ,action TEXT(65535) NULL 
 ,doc_id INT(10) NULL 
 ,res_id INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.livre(
id INT(10) NOT NULL AUTO_INCREMENT
 ,Titre TEXT(65535) NULL 
 ,Soustitre TEXT(65535) NULL 
 ,pages INT(10) NULL 
 ,mots INT(10) NULL 
 ,format VARCHAR(50) NULL 
 ,receipt Date NULL 
 ,genre_id INT(10) NULL 
 ,typeConcept_id INT(10) NULL 
 ,status VARCHAR(45) NOT NULL 
 ,user_id INT(10) NULL 
 ,closed_Date Datetime NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.project_user_task(
id INT(10) NOT NULL AUTO_INCREMENT
 ,user_id INT(10) NOT NULL 
 ,livre_id INT(10) NOT NULL 
 ,duree INT(10) NOT NULL 
 ,task_id INT(10) NOT NULL 
 ,created Datetime NULL 
 ,state VARCHAR(45) NOT NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.ressource(
id INT(10) NOT NULL AUTO_INCREMENT
 ,path TEXT(65535) NULL 
 ,name TEXT(65535) NULL 
 ,created Date NULL 
 ,modified Date NULL 
 ,creator VARCHAR(45) NULL 
 ,description TEXT(65535) NULL 
 ,modifiedby VARCHAR(45) NULL 
 ,format VARCHAR(45) NULL 
 ,livres_id INT(10) NULL 
 ,type VARCHAR(45) NULL 
 ,users_id INT(10) NULL 
 ,isarchive BIT(1) NULL 
 ,no INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.role_access(
id INT(10) NOT NULL AUTO_INCREMENT
 ,role_id INT(10) NULL 
 ,item VARCHAR(45) NULL 
 ,type VARCHAR(45) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.roles(
id INT(10) NOT NULL AUTO_INCREMENT
 ,name VARCHAR(45) NULL 
 ,abbreviation VARCHAR(45) NULL 
 ,permission VARCHAR(45) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.settings(
id INT(10) NOT NULL AUTO_INCREMENT
 ,name VARCHAR(45) NULL 
 ,directory TEXT(65535) NULL 
 ,docPath TEXT(65535) NULL 
 ,imagePath TEXT(65535) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.task(
id INT(10) NOT NULL AUTO_INCREMENT
 ,task VARCHAR(45) NOT NULL 
 ,user_id INT(10) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.user(
id INT(10) NOT NULL AUTO_INCREMENT
 ,username VARCHAR(45) NULL 
 ,pass VARCHAR(45) NULL 
 ,defaultPass VARCHAR(45) NULL 
 ,type VARCHAR(45) NOT NULL 
 ,groups_id INT(10) NULL 
 ,nom VARCHAR(45) NULL 
 ,prenom VARCHAR(45) NULL 
 ,adresse TEXT(65535) NULL 
 ,naissance Date NULL 
 ,sexe VARCHAR(45) NULL 
 ,mail VARCHAR(45) NULL 
 ,tel VARCHAR(45) NULL 
 ,user_id INT(10) NULL 
 ,enable BIT(1) NULL 
 ,PRIMARY KEY(id)
);
create TABLE IF NOT EXISTS journalisation.user_project(
livre_id INT(10) NOT NULL 
 ,user_id INT(10) NOT NULL 
 ,status VARCHAR(45) NOT NULL 
 ,PRIMARY KEY(livre_id,user_id)
);
create TABLE IF NOT EXISTS journalisation.verification(
Id_verification INT(10) NOT NULL 
 ,Date_debut_V Date NULL 
 ,Date_fin_V Date NULL 
 ,Ref_conception INT(10) NULL 
 ,PRIMARY KEY(Id_verification)
);
create TABLE IF NOT EXISTS journalisation.visualisation(
Id_visualisation INT(10) NOT NULL 
 ,Remarque TEXT(65535) NULL 
 ,Date_visee Date NULL 
 ,Ref_livre INT(10) NULL 
 ,ref_doc INT(10) NULL 
 ,PRIMARY KEY(Id_visualisation)
);
