package org.dcais.stock.stock.biz.tushare;

public class TushareRequestFields {
  public static String[] basic = {
    "ts_code",
    "symbol",
    "name",
    "area",
    "industry",
    "fullname",
    "enname",
    "market",
    "exchange",
    "curr_type",
    "list_status",
    "list_date",
    "delist_date",
    "is_hs"
  };

  public static String[] tradeCal = {
    "exchange",
    "cal_date",
    "is_open",
    "pretrade_date"
  };

  public static String[] daily = {
    "ts_code"
    , "trade_date"
    , "open"
    , "high"
    , "low"
    , "close"
    , "pre_close"
    , "change"
    , "pct_chg"
    , "vol"
    , "amount"
  };

  public static String[] adjFactor = {
    "ts_code"
    , "trade_date"
    , "adj_factor"
  };

  public static String[] dailyBasic = {
    "ts_code",
    "trade_date",
    "close",
    "turnover_rate",
    "turnover_rate_f",
    "volume_ratio",
    "pe",
    "pe_ttm",
    "pb",
    "ps",
    "ps_ttm",
    "total_share",
    "float_share",
    "free_share",
    "total_mv",
    "circ_mv"
  };
}
