package org.dcais.stock.stock.biz.tushare;

public class TushareRequestFields {
    public static String[] basic= {
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
        ,"trade_date"
        ,"open"
        ,"high"
        ,"low"
        ,"close"
        ,"pre_close"
        ,"change"
        ,"pct_chg"
        ,"vol"
        ,"amount"
    };
}
