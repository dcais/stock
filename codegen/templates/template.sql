CREATE TABLE `stock_basic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator` int(11) NOT NULL COMMENT '创建人ID',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `modifier` int(11) NOT NULL COMMENT '更新人ID',
  `is_deleted` char(1) DEFAULT 'N' COMMENT '是否删除，Y删除，N未删除',
  `ts_code` char(16) DEFAULT NULL COMMENT 'TS代码',
  `symbol` char(16) DEFAULT NULL COMMENT '股票代码',
  `name` varchar(16) DEFAULT NULL COMMENT '股票名称',
  `area` varchar(16) DEFAULT NULL COMMENT '所在地域',
  `industry` varchar(32) DEFAULT NULL COMMENT '所在行业',
  `fullname` varchar(64) DEFAULT NULL COMMENT '股票全称',
  `enname` CHAR(255) DEFAULT NULL COMMENT '英文全称',
  `market` varchar(16) DEFAULT NULL COMMENT '市场类型',
  `exchange` char(8) DEFAULT NULL COMMENT '交易所代码 SSE上交所 SZSE深交所 HKEX港交所(未上线)',
  `curr_type` char(8) DEFAULT NULL COMMENT '交易货币',
  `list_status` char(1) DEFAULT NULL COMMENT '上市状态  L上市 D退市 P暂停上市',
  `list_date` datetime DEFAULT NULL COMMENT '上市日期',
  `delist_date` datetime DEFAULT NULL COMMENT '退市日期',
  `is_hs` char(1) DEFAULT NULL COMMENT '是否沪深港通标的，N否 H沪股通 S深股通',
  PRIMARY KEY (`id`),
  KEY idx_ts_code (`ts_code`),
  KEY idx_symbol(`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='活动场次表';