
CREATE DATABASE /*!32312 IF NOT EXISTS*/`statistic_tool` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*Table structure for table `node` */

DROP TABLE IF EXISTS `node`;

CREATE TABLE `node` (
  `block_number` bigint(255) NOT NULL COMMENT '区块号',
  `node_id` varchar(255) NOT NULL COMMENT '节点ID',
  `sign_count` bigint(20) DEFAULT '0' COMMENT '签名次数',
  `settle_epoch` int(11) DEFAULT NULL COMMENT '当前记录结算周期',
  `consensus_epoch` int(11) DEFAULT NULL COMMENT '当前记录共识周期',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `stat_validator_time` int(11) DEFAULT '0' COMMENT '成为共识验证人的次数',
  `sign_rate` double(6,2) DEFAULT NULL COMMENT '签名率(已经x100)',
  `block_timestamp` bigint(20) DEFAULT NULL COMMENT '区块时间戳',
  PRIMARY KEY (`block_number`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;