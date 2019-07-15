CREATE TABLE `stock_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator` int(11) NOT NULL COMMENT '创建人ID',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `modifier` int(11) NOT NULL COMMENT '更新人ID',
  `is_deleted` char(1) DEFAULT 'N' COMMENT '是否删除，Y删除，N未删除',
  `ts_code` varchar(16) DEFAULT NULL COMMENT '股票代码',
  `trade_date` datetime DEFAULT NULL COMMENT '交易日期',
  `open` decimal(10,6) DEFAULT NULL COMMENT '开盘价',
  `high` decimal(10,6) DEFAULT NULL COMMENT '最高价',
  `low` decimal(10,6) DEFAULT NULL COMMENT '最低价',
  `close` decimal(10,6) DEFAULT NULL COMMENT '收盘价',
  `pre_close` decimal(10,6) DEFAULT NULL COMMENT '昨收价',
  `pct_chg` decimal(10,6) DEFAULT NULL COMMENT '涨跌额',
  `vol` decimal(10,6) DEFAULT NULL COMMENT '成交量 （手）',
  `amount` decimal(10,6) DEFAULT NULL COMMENT '成交额 （千元）',
  PRIMARY KEY (`id`),
  KEY idx_ts_code(`ts_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='日线行情';