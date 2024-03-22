-- fsm.tb_order definition

CREATE TABLE `tb_order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `status` varchar(32) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;