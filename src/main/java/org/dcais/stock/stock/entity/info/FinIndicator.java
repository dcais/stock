package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class FinIndicator extends FinBase {
  private BigDecimal eps; //基本每股收益
  private BigDecimal dtEps; //稀释每股收益
  private BigDecimal totalRevenuePs; //每股营业总收入
  private BigDecimal revenuePs; //每股营业收入
  private BigDecimal capitalResePs; //每股资本公积
  private BigDecimal surplusResePs; //每股盈余公积
  private BigDecimal undistProfitPs; //每股未分配利润
  private BigDecimal extraItem; //非经常性损益
  private BigDecimal profitDedt; //扣除非经常性损益后的净利润
  private BigDecimal grossMargin; //毛利
  private BigDecimal currentRatio; //流动比率
  private BigDecimal quickRatio; //速动比率
  private BigDecimal cashRatio; //保守速动比率
  private BigDecimal invturnDays; //存货周转天数
  private BigDecimal arturnDays; //应收账款周转天数
  private BigDecimal invTurn; //存货周转率
  private BigDecimal arTurn; //应收账款周转率
  private BigDecimal caTurn; //流动资产周转率
  private BigDecimal faTurn; //固定资产周转率
  private BigDecimal assetsTurn; //总资产周转率
  private BigDecimal opIncome; //经营活动净收益
  private BigDecimal valuechangeIncome; //价值变动净收益
  private BigDecimal interstIncome; //利息费用
  private BigDecimal daa; //折旧与摊销
  private BigDecimal ebit; //息税前利润
  private BigDecimal ebitda; //息税折旧摊销前利润
  private BigDecimal fcff; //企业自由现金流量
  private BigDecimal fcfe; //股权自由现金流量
  private BigDecimal currentExint; //无息流动负债
  private BigDecimal noncurrentExint; //无息非流动负债
  private BigDecimal interestdebt; //带息债务
  private BigDecimal netdebt; //净债务
  private BigDecimal tangibleAsset; //有形资产
  private BigDecimal workingCapital; //营运资金
  private BigDecimal networkingCapital; //营运流动资本
  private BigDecimal investCapital; //全部投入资本
  private BigDecimal retainedEarnings; //留存收益
  private BigDecimal diluted2Eps; //期末摊薄每股收益
  private BigDecimal bps; //每股净资产
  private BigDecimal ocfps; //每股经营活动产生的现金流量净额
  private BigDecimal retainedps; //每股留存收益
  private BigDecimal cfps; //每股现金流量净额
  private BigDecimal ebitPs; //每股息税前利润
  private BigDecimal fcffPs; //每股企业自由现金流量
  private BigDecimal fcfePs; //每股股东自由现金流量
  private BigDecimal netprofitMargin; //销售净利率
  private BigDecimal grossprofitMargin; //销售毛利率
  private BigDecimal cogsOfSales; //销售成本率
  private BigDecimal expenseOfSales; //销售期间费用率
  private BigDecimal profitToGr; //净利润/营业总收入
  private BigDecimal saleexpToGr; //销售费用/营业总收入
  private BigDecimal adminexpOfGr; //管理费用/营业总收入
  private BigDecimal finaexpOfGr; //财务费用/营业总收入
  private BigDecimal impaiTtm; //资产减值损失/营业总收入
  private BigDecimal gcOfGr; //营业总成本/营业总收入
  private BigDecimal opOfGr; //营业利润/营业总收入
  private BigDecimal ebitOfGr; //息税前利润/营业总收入
  private BigDecimal roe; //净资产收益率
  private BigDecimal roeWaa; //加权平均净资产收益率
  private BigDecimal roeDt; //净资产收益率(扣除非经常损益)
  private BigDecimal roa; //总资产报酬率
  private BigDecimal npta; //总资产净利润
  private BigDecimal roic; //投入资本回报率
  private BigDecimal roeYearly; //年化净资产收益率
  private BigDecimal roa2Yearly; //年化总资产报酬率
  private BigDecimal roeAvg; //平均净资产收益率(增发条件)
  private BigDecimal opincomeOfEbt; //经营活动净收益/利润总额
  private BigDecimal investincomeOfEbt; //价值变动净收益/利润总额
  private BigDecimal nOpProfitOfEbt; //营业外收支净额/利润总额
  private BigDecimal taxToEbt; //所得税/利润总额
  private BigDecimal dtprofitToProfit; //扣除非经常损益后的净利润/净利润
  private BigDecimal salescashToOr; //销售商品提供劳务收到的现金/营业收入
  private BigDecimal ocfToOr; //经营活动产生的现金流量净额/营业收入
  private BigDecimal ocfToOpincome; //经营活动产生的现金流量净额/经营活动净收益
  private BigDecimal capitalizedToDa; //资本支出/折旧和摊销
  private BigDecimal debtToAssets; //资产负债率
  private BigDecimal assetsToEqt; //权益乘数
  private BigDecimal dpAssetsToEqt; //权益乘数(杜邦分析)
  private BigDecimal caToAssets; //流动资产/总资产
  private BigDecimal ncaToAssets; //非流动资产/总资产
  private BigDecimal tbassetsToTotalassets; //有形资产/总资产
  private BigDecimal intToTalcap; //带息债务/全部投入资本
  private BigDecimal eqtToTalcapital; //归属于母公司的股东权益/全部投入资本
  private BigDecimal currentdebtToDebt; //流动负债/负债合计
  private BigDecimal longdebToDebt; //非流动负债/负债合计
  private BigDecimal ocfToShortdebt; //经营活动产生的现金流量净额/流动负债
  private BigDecimal debtToEqt; //产权比率
  private BigDecimal eqtToDebt; //归属于母公司的股东权益/负债合计
  private BigDecimal eqtToInterestdebt; //归属于母公司的股东权益/带息债务
  private BigDecimal tangibleassetToDebt; //有形资产/负债合计
  private BigDecimal tangassetToIntdebt; //有形资产/带息债务
  private BigDecimal tangibleassetToNetdebt; //有形资产/净债务
  private BigDecimal ocfToDebt; //经营活动产生的现金流量净额/负债合计
  private BigDecimal ocfToInterestdebt; //经营活动产生的现金流量净额/带息债务
  private BigDecimal ocfToNetdebt; //经营活动产生的现金流量净额/净债务
  private BigDecimal ebitToInterest; //已获利息倍数(EBIT/利息费用)
  private BigDecimal longdebtToWorkingcapital; //长期债务与营运资金比率
  private BigDecimal ebitdaToDebt; //息税折旧摊销前利润/负债合计
  private BigDecimal turnDays; //营业周期
  private BigDecimal roaYearly; //年化总资产净利率
  private BigDecimal roaDp; //总资产净利率(杜邦分析)
  private BigDecimal fixedAssets; //固定资产合计
  private BigDecimal profitPrefinExp; //扣除财务费用前营业利润
  private BigDecimal nonOpProfit; //非营业利润
  private BigDecimal opToEbt; //营业利润／利润总额
  private BigDecimal nopToEbt; //非营业利润／利润总额
  private BigDecimal ocfToProfit; //经营活动产生的现金流量净额／营业利润
  private BigDecimal cashToLiqdebt; //货币资金／流动负债
  private BigDecimal cashToLiqdebtWithinterest; //货币资金／带息流动负债
  private BigDecimal opToLiqdebt; //营业利润／流动负债
  private BigDecimal opToDebt; //营业利润／负债合计
  private BigDecimal roicYearly; //年化投入资本回报率
  private BigDecimal totalFaTrun; //固定资产合计周转率
  private BigDecimal profitToOp; //利润总额／营业收入
  private BigDecimal qOpincome; //经营活动单季度净收益
  private BigDecimal qInvestincome; //价值变动单季度净收益
  private BigDecimal qDtprofit; //扣除非经常损益后的单季度净利润
  private BigDecimal qEps; //每股收益(单季度)
  private BigDecimal qNetprofitMargin; //销售净利率(单季度)
  private BigDecimal qGsprofitMargin; //销售毛利率(单季度)
  private BigDecimal qExpToSales; //销售期间费用率(单季度)
  private BigDecimal qProfitToGr; //净利润／营业总收入(单季度)
  private BigDecimal qSaleexpToGr; //销售费用／营业总收入 (单季度)
  private BigDecimal qAdminexpToGr; //管理费用／营业总收入 (单季度)
  private BigDecimal qFinaexpToGr; //财务费用／营业总收入 (单季度)
  private BigDecimal qImpairToGrTtm; //资产减值损失／营业总收入(单季度)
  private BigDecimal qGcToGr; //营业总成本／营业总收入 (单季度)
  private BigDecimal qOpToGr; //营业利润／营业总收入(单季度)
  private BigDecimal qRoe; //净资产收益率(单季度)
  private BigDecimal qDtRoe; //净资产单季度收益率(扣除非经常损益)
  private BigDecimal qNpta; //总资产净利润(单季度)
  private BigDecimal qOpincomeToEbt; //经营活动净收益／利润总额(单季度)
  private BigDecimal qInvestincomeToEbt; //价值变动净收益／利润总额(单季度)
  private BigDecimal qDtprofitToProfit; //扣除非经常损益后的净利润／净利润(单季度)
  private BigDecimal qSalescashToOr; //销售商品提供劳务收到的现金／营业收入(单季度)
  private BigDecimal qOcfToSales; //经营活动产生的现金流量净额／营业收入(单季度)
  private BigDecimal qOcfToOr; //经营活动产生的现金流量净额／经营活动净收益(单季度)
  private BigDecimal basicEpsYoy; //基本每股收益同比增长率(%)
  private BigDecimal dtEpsYoy; //稀释每股收益同比增长率(%)
  private BigDecimal cfpsYoy; //每股经营活动产生的现金流量净额同比增长率(%)
  private BigDecimal opYoy; //营业利润同比增长率(%)
  private BigDecimal ebtYoy; //利润总额同比增长率(%)
  private BigDecimal netprofitYoy; //归属母公司股东的净利润同比增长率(%)
  private BigDecimal dtNetprofitYoy; //归属母公司股东的净利润-扣除非经常损益同比增长率(%)
  private BigDecimal ocfYoy; //经营活动产生的现金流量净额同比增长率(%)
  private BigDecimal roeYoy; //净资产收益率(摊薄)同比增长率(%)
  private BigDecimal bpsYoy; //每股净资产相对年初增长率(%)
  private BigDecimal assetsYoy; //资产总计相对年初增长率(%)
  private BigDecimal eqtYoy; //归属母公司的股东权益相对年初增长率(%)
  private BigDecimal trYoy; //营业总收入同比增长率(%)
  private BigDecimal orYoy; //营业收入同比增长率(%)
  private BigDecimal qGrYoy; //营业总收入同比增长率(%)(单季度)
  private BigDecimal qGrQoq; //营业总收入环比增长率(%)(单季度)
  private BigDecimal qSalesYoy; //营业收入同比增长率(%)(单季度)
  private BigDecimal qSalesQoq; //营业收入环比增长率(%)(单季度)
  private BigDecimal qOpYoy; //营业利润同比增长率(%)(单季度)
  private BigDecimal qOpQoq; //营业利润环比增长率(%)(单季度)
  private BigDecimal qProfitYoy; //净利润同比增长率(%)(单季度)
  private BigDecimal qProfitQoq; //净利润环比增长率(%)(单季度)
  private BigDecimal qNetprofitYoy; //归属母公司股东的净利润同比增长率(%)(单季度)
  private BigDecimal qNetprofitQoq; //归属母公司股东的净利润环比增长率(%)(单季度)
  private BigDecimal equityYoy; //净资产同比增长率
  private BigDecimal rdExp; //研发费用

}
