CREATE TABLE `stock_adj_factor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator` int(11) NOT NULL COMMENT '创建人ID',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `modifier` int(11) NOT NULL COMMENT '更新人ID',
  `is_deleted` char(1) DEFAULT 'N' COMMENT '是否删除，Y删除，N未删除',
  `ts_code` varchar(16) NOT NULL COMMENT 'ts_code',
  `trade_date` datetime NOT NULL COMMENT '交易日期',
  `adj_factor` decimal(20,8) NOT NULL COMMENT '复权因子',
  PRIMARY KEY (`id`),
  KEY idx_ts_code(`ts_code`),
  KEY idx_ts_code_date(`ts_code`,`trade_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='复权因子';