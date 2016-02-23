CREATE DATABASE `moe`;

CREATE TABLE `moe`.`employee` (
	`id` INT(10) AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(30) NOT NULL,
	`surname` VARCHAR(30) NOT NULL,
	`salary` VARCHAR(30) NOT NULL,
	`id_team` INT(8),
	`id_project` INT(8)
)   ENGINE=INNODB;
	
CREATE TABLE `moe`.`team` (
	`id` INT(8) AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(30) NOT NULL,
	`members` VARCHAR(100),
	`id_team_leader` INT(8),
	`id_project` INT(8)
)   ENGINE=INNODB;

CREATE TABLE `moe`.`project` (
	`id` INT(8) AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(30) NOT NULL
)   ENGINE=INNODB;

ALTER TABLE `moe`.`employee`
ADD FOREIGN KEY (`id_team`)
REFERENCES `moe`.`team`(`id`);

ALTER TABLE `moe`.`employee`
ADD FOREIGN KEY (`id_project`)
REFERENCES `moe`.`project`(`id`);

ALTER TABLE `moe`.`team`
ADD FOREIGN KEY (`id_team_leader`)
REFERENCES `moe`.`employee`(`id`);

ALTER TABLE `moe`.`team`
ADD FOREIGN KEY (`id_project`)
REFERENCES `moe`.`project`(`id`);

INSERT INTO `moe`.`project`(`id`, `name`) VALUES (1, 'Facebook');
INSERT INTO `moe`.`team`(`id`, `name`, `id_project`, `members`, `id_team_leader`) VALUES (1, 'Alfa', 1, 'Jan, Monika, Marta', NULL);
INSERT INTO `moe`.`team`(`id`, `name`, `id_project`, `members`, `id_team_leader`) VALUES (2, 'Beta', 1, 'Adam, Ewa, Ala', NULL);
INSERT INTO `moe`.`team`(`id`, `name`, `id_project`, `members`, `id_team_leader`) VALUES (3, 'Gamma', 1, 'Jarek, Seba, Adrian', NULL);
INSERT INTO `moe`.`employee`(`id`, `name`, `surname`, `salary`, `id_team`, `id_project`) VALUES (1, 'Jan', 'Kowalski', 1111, 1, 1);
INSERT INTO `moe`.`employee`(`id`, `name`, `surname`, `salary`, `id_team`, `id_project`) VALUES (2, 'Adam', 'Zzzz', 2222, 1, 1);
INSERT INTO `moe`.`employee`(`id`, `name`, `surname`, `salary`, `id_team`, `id_project`) VALUES (3, 'Przemyslaw', 'Paluch', 333, 2, 1);
INSERT INTO `moe`.`employee`(`id`, `name`, `surname`, `salary`, `id_team`, `id_project`) VALUES (4, 'Marcin', 'Kowalski', 4444, 2, 1);
INSERT INTO `moe`.`employee`(`id`, `name`, `surname`, `salary`, `id_team`, `id_project`) VALUES (5, 'Adrian', 'Sienbida', 55, 2, 1);
INSERT INTO `moe`.`employee`(`id`, `name`, `surname`, `salary`, `id_team`, `id_project`) VALUES (6, 'Monika', 'Nowak', 66, 2, 1);
INSERT INTO `moe`.`employee`(`id`, `name`, `surname`, `salary`, `id_team`, `id_project`) VALUES (7, 'Artur', 'Kowal', 777, 3, 1);

UPDATE `moe`.`team` SET id_team_leader = 2 WHERE id=1;
UPDATE `moe`.`team` SET id_team_leader = 3 WHERE id=2;
UPDATE `moe`.`team` SET id_team_leader = 7 WHERE id=3;



--DROP DATABASE `moe`;