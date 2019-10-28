package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class FinIncome extends FinBase {
  private String reportType;
  private String compType;
  private BigDecimal basicEps; //基本每股收益
  private BigDecimal dilutedEps; //稀释每股收益
  private BigDecimal totalRevenue; //营业总收入
  private BigDecimal revenue; //营业收入
  private BigDecimal intIncome; //利息收入
  private BigDecimal premEarned; //已赚保费
  private BigDecimal commIncome; //手续费及佣金收入
  private BigDecimal nCommisIncome; //手续费及佣金净收入
  private BigDecimal nOthIncome; //其他经营净收益
  private BigDecimal nOthBIncome; //加:其他业务净收益
  private BigDecimal premIncome; //保险业务收入
  private BigDecimal outPrem; //减:分出保费
  private BigDecimal unePremReser; //提取未到期责任准备金
  private BigDecimal reinsIncome; //其中:分保费收入
  private BigDecimal nSecTbIncome; //代理买卖证券业务净收入
  private BigDecimal nSecUwIncome; //证券承销业务净收入
  private BigDecimal nAssetMgIncome; //受托客户资产管理业务净收入
  private BigDecimal othBIncome; //其他业务收入
  private BigDecimal fvValueChgGain; //加:公允价值变动净收益
  private BigDecimal investIncome; //加:投资净收益
  private BigDecimal assInvestIncome; //其中:对联营企业和合营企业的投资收益
  private BigDecimal forexGain; //加:汇兑净收益
  private BigDecimal totalCogs; //营业总成本
  private BigDecimal operCost; //减:营业成本
  private BigDecimal intExp; //减:利息支出
  private BigDecimal commExp; //减:手续费及佣金支出
  private BigDecimal bizTaxSurchg; //减:营业税金及附加
  private BigDecimal sellExp; //减:销售费用
  private BigDecimal adminExp; //减:管理费用
  private BigDecimal finExp; //减:财务费用
  private BigDecimal assetsImpairLoss; //减:资产减值损失
  private BigDecimal premRefund; //退保金
  private BigDecimal compensPayout; //赔付总支出
  private BigDecimal reserInsurLiab; //提取保险责任准备金
  private BigDecimal divPayt; //保户红利支出
  private BigDecimal reinsExp; //分保费用
  private BigDecimal operExp; //营业支出
  private BigDecimal compensPayoutRefu; //减:摊回赔付支出
  private BigDecimal insurReserRefu; //减:摊回保险责任准备金
  private BigDecimal reinsCostRefund; //减:摊回分保费用
  private BigDecimal otherBusCost; //其他业务成本
  private BigDecimal operateProfit; //营业利润
  private BigDecimal nonOperIncome; //加:营业外收入
  private BigDecimal nonOperExp; //减:营业外支出
  private BigDecimal ncaDisploss; //其中:减:非流动资产处置净损失
  private BigDecimal totalProfit; //利润总额
  private BigDecimal incomeTax; //所得税费用
  private BigDecimal nIncome; //净利润(含少数股东损益)
  private BigDecimal nIncomeAttrP; //净利润(不含少数股东损益)
  private BigDecimal minorityGain; //少数股东损益
  private BigDecimal othComprIncome; //其他综合收益
  private BigDecimal tComprIncome; //综合收益总额
  private BigDecimal comprIncAttrP; //归属于母公司(或股东)的综合收益总额
  private BigDecimal comprIncAttrMS; //归属于少数股东的综合收益总额
  private BigDecimal ebit; //息税前利润
  private BigDecimal ebitda; //息税折旧摊销前利润
  private BigDecimal insuranceExp; //保险业务支出
  private BigDecimal undistProfit; //年初未分配利润
  private BigDecimal distableProfit; //可分配利润

}
