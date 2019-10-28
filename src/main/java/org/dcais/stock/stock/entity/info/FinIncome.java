package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class FinIncome extends BaseEntity {
  private String tsCode;
  private String annDate;
  private String fAnnDate;
  private String endDate;
  private String reportType;
  private String compType;
  //基本每股收益
  private BigDecimal basicEps;
  //稀释每股收益
  private BigDecimal dilutedEps;
  //营业总收入
  private BigDecimal totalRevenue;
  //营业收入
  private BigDecimal Revenue;
  //利息收入
  private BigDecimal intIncome;
  //已赚保费
  private BigDecimal premEarned;
  //手续费及佣金收入
  private BigDecimal commIncome;
  //手续费及佣金净收入
  private BigDecimal nCommisIncome;
  //其他经营净收益
  private BigDecimal nOthIncome;
  //加:其他业务净收益
  private BigDecimal nOthBIncome;
  //保险业务收入
  private BigDecimal premIncome;
  //减:分出保费
  private BigDecimal outPrem;
  //提取未到期责任准备金
  private BigDecimal unePremReser;
  //其中:分保费收入
  private BigDecimal reinsIncome;
  //其中:分保费收入
  private BigDecimal nSecTbIncomel;
  //证券承销业务净收入
  private BigDecimal nSecUwIncome;
  //受托客户资产管理业务净收入
  private BigDecimal nAssetMgIncome;
  //其他业务收入
  private BigDecimal othBIncome;
  //加:公允价值变动净收益
  private BigDecimal fvValueChgGain;
  //加:投资净收益
  private BigDecimal investIncome;
  //其中:对联营企业和合营企业的投资收益
  private BigDecimal assInvestIncome;
  //加:汇兑净收益
  private BigDecimal forexGain;
  //营业总成本
  private BigDecimal totalCogs;
  //减:营业成本
  private BigDecimal operCost;
  //减:利息支出
  private BigDecimal intExp;
  //减:手续费及佣金支出
  private BigDecimal commExp;
  //减:营业税金及附加
  private BigDecimal bizTaxSurchg;
  //减:销售费用
  private BigDecimal sellExp;
  //减:管理费用
  private BigDecimal adminExp;
  //减:财务费用
  private BigDecimal finExp;
  //减:资产减值损失
  private BigDecimal assetsImpairLoss;
  //退保金
  private BigDecimal premRefund;
  //赔付总支出
  private BigDecimal compensPayout;
  //提取保险责任准备金
  private BigDecimal reserInsurLiab;
  //保户红利支出
  private BigDecimal divPayt;
  //分保费用
  private BigDecimal reinsExp;
  //营业支出
  private BigDecimal operExp;
  //减:摊回赔付支出
  private BigDecimal compensPayoutRefu;
  //减:摊回保险责任准备金
  private BigDecimal insurReserRefu;
  //减:摊回分保费用
  private BigDecimal reinsCostRefund;
  //其他业务成本
  private BigDecimal otherBusCost;
  //营业利润
  private BigDecimal operateProfit;
  //加:营业外收入
  private BigDecimal nonOperIncome;
  //减:营业外支出
  private BigDecimal nonOperExp;
  //其中:减:非流动资产处置净损失
  private BigDecimal ncaDisploss;
  //利润总额
  private BigDecimal totalProfit;
  //所得税费用
  private BigDecimal incomeTax;
  //净利润(含少数股东损益)
  private BigDecimal nIncome;
  //净利润(不含少数股东损益)
  private BigDecimal nIncomeAttrP;
  //少数股东损益
  private BigDecimal minorityGain;
  //其他综合收益
  private BigDecimal othComprIncome;
  //综合收益总额
  private BigDecimal tComprIncome;
  //归属于母公司(或股东)的综合收益总额
  private BigDecimal comprIncAttrP;
  //归属于少数股东的综合收益总额
  private BigDecimal comprIncAttrMS;
  //息税前利润
  private BigDecimal ebit;
  //息税折旧摊销前利润
  private BigDecimal ebitda;
  //息税折旧摊销前利润
  private BigDecimal insuranceExp;
  //年初未分配利润
  private BigDecimal undistProfit;
  //可分配利润
  private BigDecimal distableProfit;
  //更新标识
  private String updateFlag;
  private int reportSeason;
  private int reportYear;
}
