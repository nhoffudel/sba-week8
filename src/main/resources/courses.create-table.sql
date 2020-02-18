DROP TABLE IF EXISTS `Course`;

CREATE TABLE `courses` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `instructor` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;