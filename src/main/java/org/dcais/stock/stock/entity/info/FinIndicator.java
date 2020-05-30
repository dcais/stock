package org.dcais.stock.stock.entity.info;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("stock_fin_indicator")
public class FinIndicator extends FinBase {

  /**
   * 基本每股收益
   */
  private BigDecimal eps;

  /**
   * 稀释每股收益
   */
  private BigDecimal dtEps;

  /**
   * 每股营业总收入
   */
  private BigDecimal totalRevenuePs;

  /**
   * 每股营业收入
   */
  private BigDecimal revenuePs;

  /**
   * 每股资本公积
   */
  private BigDecimal capitalResePs;

  /**
   * 每股盈余公积
   */
  private BigDecimal surplusResePs;

  /**
   * 每股未分配利润
   */
  private BigDecimal undistProfitPs;

  /**
   * 非经常性损益
   */
  private BigDecimal extraItem;

  /**
   * 扣除非经常性损益后的净利润
   */
  private BigDecimal profitDedt;

  /**
   * 毛利
   */
  private BigDecimal grossMargin;

  /**
   * 流动比率
   */
  private BigDecimal currentRatio;

  /**
   * 速动比率
   */
  private BigDecimal quickRatio;

  /**
   * 保守速动比率
   */
  private BigDecimal cashRatio;

  /**
   * 存货周转天数
   */
  private BigDecimal invturnDays;

  /**
   * 应收账款周转天数
   */
  private BigDecimal arturnDays;

  /**
   * 存货周转率
   */
  private BigDecimal invTurn;

  /**
   * 应收账款周转率
   */
  private BigDecimal arTurn;

  /**
   * 流动资产周转率
   */
  private BigDecimal caTurn;

  /**
   * 固定资产周转率
   */
  private BigDecimal faTurn;

  /**
   * 总资产周转率
   */
  private BigDecimal assetsTurn;

  /**
   * 经营活动净收益
   */
  private BigDecimal opIncome;

  /**
   * 价值变动净收益
   */
  private BigDecimal valuechangeIncome;

  /**
   * 利息费用
   */
  private BigDecimal interstIncome;

  /**
   * 折旧与摊销
   */
  private BigDecimal daa;

  /**
   * 息税前利润
   */
  private BigDecimal ebit;

  /**
   * 息税折旧摊销前利润
   */
  private BigDecimal ebitda;

  /**
   * 企业自由现金流量
   */
  private BigDecimal fcff;

  /**
   * 股权自由现金流量
   */
  private BigDecimal fcfe;

  /**
   * 无息流动负债
   */
  private BigDecimal currentExint;

  /**
   * 无息非流动负债
   */
  private BigDecimal noncurrentExint;

  /**
   * 带息债务
   */
  private BigDecimal interestdebt;

  /**
   * 净债务
   */
  private BigDecimal netdebt;

  /**
   * 有形资产
   */
  private BigDecimal tangibleAsset;

  /**
   * 营运资金
   */
  private BigDecimal workingCapital;

  /**
   * 营运流动资本
   */
  private BigDecimal networkingCapital;

  /**
   * 全部投入资本
   */
  private BigDecimal investCapital;

  /**
   * 留存收益
   */
  private BigDecimal retainedEarnings;

  /**
   * 期末摊薄每股收益
   */
  private BigDecimal diluted2eps;

  /**
   * 每股净资产
   */
  private BigDecimal bps;

  /**
   * 每股经营活动产生的现金流量净额
   */
  private BigDecimal ocfps;

  /**
   * 每股留存收益
   */
  private BigDecimal retainedps;

  /**
   * 每股现金流量净额
   */
  private BigDecimal cfps;

  /**
   * 每股息税前利润
   */
  private BigDecimal ebitPs;

  /**
   * 每股企业自由现金流量
   */
  private BigDecimal fcffPs;

  /**
   * 每股股东自由现金流量
   */
  private BigDecimal fcfePs;

  /**
   * 销售净利率
   */
  private BigDecimal netprofitMargin;

  /**
   * 销售毛利率
   */
  private BigDecimal grossprofitMargin;

  /**
   * 销售成本率
   */
  private BigDecimal cogsOfSales;

  /**
   * 销售期间费用率
   */
  private BigDecimal expenseOfSales;

  /**
   * 净利润/营业总收入
   */
  private BigDecimal profitToGr;

  /**
   * 销售费用/营业总收入
   */
  private BigDecimal saleexpToGr;

  /**
   * 管理费用/营业总收入
   */
  private BigDecimal adminexpOfGr;

  /**
   * 财务费用/营业总收入
   */
  private BigDecimal finaexpOfGr;

  /**
   * 资产减值损失/营业总收入
   */
  private BigDecimal impaiTtm;

  /**
   * 营业总成本/营业总收入
   */
  private BigDecimal gcOfGr;

  /**
   * 营业利润/营业总收入
   */
  private BigDecimal opOfGr;

  /**
   * 息税前利润/营业总收入
   */
  private BigDecimal ebitOfGr;

  /**
   * 净资产收益率
   */
  private BigDecimal roe;

  /**
   * 加权平均净资产收益率
   */
  private BigDecimal roeWaa;

  /**
   * 净资产收益率(扣除非经常损益)
   */
  private BigDecimal roeDt;

  /**
   * 总资产报酬率
   */
  private BigDecimal roa;

  /**
   * 总资产净利润
   */
  private BigDecimal npta;

  /**
   * 投入资本回报率
   */
  private BigDecimal roic;

  /**
   * 年化净资产收益率
   */
  private BigDecimal roeYearly;

  /**
   * 年化总资产报酬率
   */
  private BigDecimal roa2yearly;

  /**
   * 平均净资产收益率(增发条件)
   */
  private BigDecimal roeAvg;

  /**
   * 经营活动净收益/利润总额
   */
  private BigDecimal opincomeOfEbt;

  /**
   * 价值变动净收益/利润总额
   */
  private BigDecimal investincomeOfEbt;

  /**
   * 营业外收支净额/利润总额
   */
  private BigDecimal nOpProfitOfEbt;

  /**
   * 所得税/利润总额
   */
  private BigDecimal taxToEbt;

  /**
   * 扣除非经常损益后的净利润/净利润
   */
  private BigDecimal dtprofitToProfit;

  /**
   * 销售商品提供劳务收到的现金/营业收入
   */
  private BigDecimal salescashToOr;

  /**
   * 经营活动产生的现金流量净额/营业收入
   */
  private BigDecimal ocfToOr;

  /**
   * 经营活动产生的现金流量净额/经营活动净收益
   */
  private BigDecimal ocfToOpincome;

  /**
   * 资本支出/折旧和摊销
   */
  private BigDecimal capitalizedToDa;

  /**
   * 资产负债率
   */
  private BigDecimal debtToAssets;

  /**
   * 权益乘数
   */
  private BigDecimal assetsToEqt;

  /**
   * 权益乘数(杜邦分析)
   */
  private BigDecimal dpAssetsToEqt;

  /**
   * 流动资产/总资产
   */
  private BigDecimal caToAssets;

  /**
   * 非流动资产/总资产
   */
  private BigDecimal ncaToAssets;

  /**
   * 有形资产/总资产
   */
  private BigDecimal tbassetsToTotalassets;

  /**
   * 带息债务/全部投入资本
   */
  private BigDecimal intToTalcap;

  /**
   * 归属于母公司的股东权益/全部投入资本
   */
  private BigDecimal eqtToTalcapital;

  /**
   * 流动负债/负债合计
   */
  private BigDecimal currentdebtToDebt;

  /**
   * 非流动负债/负债合计
   */
  private BigDecimal longdebToDebt;

  /**
   * 经营活动产生的现金流量净额/流动负债
   */
  private BigDecimal ocfToShortdebt;

  /**
   * 产权比率
   */
  private BigDecimal debtToEqt;

  /**
   * 归属于母公司的股东权益/负债合计
   */
  private BigDecimal eqtToDebt;

  /**
   * 归属于母公司的股东权益/带息债务
   */
  private BigDecimal eqtToInterestdebt;

  /**
   * 有形资产/负债合计
   */
  private BigDecimal tangibleassetToDebt;

  /**
   * 有形资产/带息债务
   */
  private BigDecimal tangassetToIntdebt;

  /**
   * 有形资产/净债务
   */
  private BigDecimal tangibleassetToNetdebt;

  /**
   * 经营活动产生的现金流量净额/负债合计
   */
  private BigDecimal ocfToDebt;

  /**
   * 经营活动产生的现金流量净额/带息债务
   */
  private BigDecimal ocfToInterestdebt;

  /**
   * 经营活动产生的现金流量净额/净债务
   */
  private BigDecimal ocfToNetdebt;

  /**
   * 已获利息倍数(EBIT/利息费用)
   */
  private BigDecimal ebitToInterest;

  /**
   * 长期债务与营运资金比率
   */
  private BigDecimal longdebtToWorkingcapital;

  /**
   * 息税折旧摊销前利润/负债合计
   */
  private BigDecimal ebitdaToDebt;

  /**
   * 营业周期
   */
  private BigDecimal turnDays;

  /**
   * 年化总资产净利率
   */
  private BigDecimal roaYearly;

  /**
   * 总资产净利率(杜邦分析)
   */
  private BigDecimal roaDp;

  /**
   * 固定资产合计
   */
  private BigDecimal fixedAssets;

  /**
   * 扣除财务费用前营业利润
   */
  private BigDecimal profitPrefinExp;

  /**
   * 非营业利润
   */
  private BigDecimal nonOpProfit;

  /**
   * 营业利润／利润总额
   */
  private BigDecimal opToEbt;

  /**
   * 非营业利润／利润总额
   */
  private BigDecimal nopToEbt;

  /**
   * 经营活动产生的现金流量净额／营业利润
   */
  private BigDecimal ocfToProfit;

  /**
   * 货币资金／流动负债
   */
  private BigDecimal cashToLiqdebt;

  /**
   * 货币资金／带息流动负债
   */
  private BigDecimal cashToLiqdebtWithinterest;

  /**
   * 营业利润／流动负债
   */
  private BigDecimal opToLiqdebt;

  /**
   * 营业利润／负债合计
   */
  private BigDecimal opToDebt;

  /**
   * 年化投入资本回报率
   */
  private BigDecimal roicYearly;

  /**
   * 固定资产合计周转率
   */
  private BigDecimal totalFaTrun;

  /**
   * 利润总额／营业收入
   */
  private BigDecimal profitToOp;

  /**
   * 经营活动单季度净收益
   */
  private BigDecimal qOpincome;

  /**
   * 价值变动单季度净收益
   */
  private BigDecimal qInvestincome;

  /**
   * 扣除非经常损益后的单季度净利润
   */
  private BigDecimal qDtprofit;

  /**
   * 每股收益(单季度)
   */
  private BigDecimal qEps;

  /**
   * 销售净利率(单季度)
   */
  private BigDecimal qNetprofitMargin;

  /**
   * 销售毛利率(单季度)
   */
  private BigDecimal qGsprofitMargin;

  /**
   * 销售期间费用率(单季度)
   */
  private BigDecimal qExpToSales;

  /**
   * 净利润／营业总收入(单季度)
   */
  private BigDecimal qProfitToGr;

  /**
   * 销售费用／营业总收入
   */
  private BigDecimal qSaleexpToGr;

  /**
   * 管理费用／营业总收入
   */
  private BigDecimal qAdminexpToGr;

  /**
   * 财务费用／营业总收入
   */
  private BigDecimal qFinaexpToGr;

  /**
   * 资产减值损失／营业总收入(单季度)
   */
  private BigDecimal qImpairToGrTtm;

  /**
   * 营业总成本／营业总收入
   */
  private BigDecimal qGcToGr;

  /**
   * 营业利润／营业总收入(单季度)
   */
  private BigDecimal qOpToGr;

  /**
   * 净资产收益率(单季度)
   */
  private BigDecimal qRoe;

  /**
   * 净资产单季度收益率(扣除非经常损益)
   */
  private BigDecimal qDtRoe;

  /**
   * 总资产净利润(单季度)
   */
  private BigDecimal qNpta;

  /**
   * 经营活动净收益／利润总额(单季度)
   */
  private BigDecimal qOpincomeToEbt;

  /**
   * 价值变动净收益／利润总额(单季度)
   */
  private BigDecimal qInvestincomeToEbt;

  /**
   * 扣除非经常损益后的净利润／净利润(单季度)
   */
  private BigDecimal qDtprofitToProfit;

  /**
   * 销售商品提供劳务收到的现金／营业收入(单季度)
   */
  private BigDecimal qSalescashToOr;

  /**
   * 经营活动产生的现金流量净额／营业收入(单季度)
   */
  private BigDecimal qOcfToSales;

  /**
   * 经营活动产生的现金流量净额／经营活动净收益(单季度)
   */
  private BigDecimal qOcfToOr;

  /**
   * 基本每股收益同比增长率(%)
   */
  private BigDecimal basicEpsYoy;

  /**
   * 稀释每股收益同比增长率(%)
   */
  private BigDecimal dtEpsYoy;

  /**
   * 每股经营活动产生的现金流量净额同比增长率(%)
   */
  private BigDecimal cfpsYoy;

  /**
   * 营业利润同比增长率(%)
   */
  private BigDecimal opYoy;

  /**
   * 利润总额同比增长率(%)
   */
  private BigDecimal ebtYoy;

  /**
   * 归属母公司股东的净利润同比增长率(%)
   */
  private BigDecimal netprofitYoy;

  /**
   * 归属母公司股东的净利润-扣除非经常损益同比增长率(%)
   */
  private BigDecimal dtNetprofitYoy;

  /**
   * 经营活动产生的现金流量净额同比增长率(%)
   */
  private BigDecimal ocfYoy;

  /**
   * 净资产收益率(摊薄)同比增长率(%)
   */
  private BigDecimal roeYoy;

  /**
   * 每股净资产相对年初增长率(%)
   */
  private BigDecimal bpsYoy;

  /**
   * 资产总计相对年初增长率(%)
   */
  private BigDecimal assetsYoy;

  /**
   * 归属母公司的股东权益相对年初增长率(%)
   */
  private BigDecimal eqtYoy;

  /**
   * 营业总收入同比增长率(%)
   */
  private BigDecimal trYoy;

  /**
   * 营业收入同比增长率(%)
   */
  private BigDecimal orYoy;

  /**
   * 营业总收入同比增长率(%)(单季度)
   */
  private BigDecimal qGrYoy;

  /**
   * 营业总收入环比增长率(%)(单季度)
   */
  private BigDecimal qGrQoq;

  /**
   * 营业收入同比增长率(%)(单季度)
   */
  private BigDecimal qSalesYoy;

  /**
   * 营业收入环比增长率(%)(单季度)
   */
  private BigDecimal qSalesQoq;

  /**
   * 营业利润同比增长率(%)(单季度)
   */
  private BigDecimal qOpYoy;

  /**
   * 营业利润环比增长率(%)(单季度)
   */
  private BigDecimal qOpQoq;

  /**
   * 净利润同比增长率(%)(单季度)
   */
  private BigDecimal qProfitYoy;

  /**
   * 净利润环比增长率(%)(单季度)
   */
  private BigDecimal qProfitQoq;

  /**
   * 归属母公司股东的净利润同比增长率(%)(单季度)
   */
  private BigDecimal qNetprofitYoy;

  /**
   * 归属母公司股东的净利润环比增长率(%)(单季度)
   */
  private BigDecimal qNetprofitQoq;

  /**
   * 净资产同比增长率
   */
  private BigDecimal equityYoy;

  /**
   * 研发费用
   */
  private BigDecimal rdExp;

}
