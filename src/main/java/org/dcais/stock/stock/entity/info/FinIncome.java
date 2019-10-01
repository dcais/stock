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
  private BigDecimal basicEps;
  private BigDecimal DilutedEps;
  private BigDecimal totalRevenue;
  private BigDecimal Revenue;
  private BigDecimal intIncome;
  private BigDecimal premEarned;
  private BigDecimal commIncome;
  private BigDecimal nCommisIncome;
  private BigDecimal nOthIncome;
  private BigDecimal nOthBIncome;
  private BigDecimal premIncome;
  private BigDecimal outPrem;
  private BigDecimal unePremReser;
  private BigDecimal reinsIncome;
  private BigDecimal nSecTbIncomel;
  private BigDecimal nSecUwIncome;
  private BigDecimal nAssetMgIncome;
  private BigDecimal othBIncome;
  private BigDecimal fvValueChgGain;
  private BigDecimal investIncome;
  private BigDecimal assInvestIncome;
  private BigDecimal forexGain;
  private BigDecimal totalCogs;
  private BigDecimal operCost;
  private BigDecimal intExp;
  private BigDecimal commExp;
  private BigDecimal bizTaxSurchg;
  private BigDecimal sellExp;
  private BigDecimal adminExp;
  private BigDecimal finExp;
  private BigDecimal assetsImpairLoss;
  private BigDecimal premRefund;
  private BigDecimal compensPayout;
  private BigDecimal reserInsurLiab;
  private BigDecimal divPayt;
  private BigDecimal reinsExp;
  private BigDecimal operExp;
  private BigDecimal compensPayoutRefu;
  private BigDecimal insurReserRefu;
  private BigDecimal reinsCostRefund;
  private BigDecimal otherBusCost;
  private BigDecimal operateProfit;
  private BigDecimal nonOperIncome;
  private BigDecimal nonOperExp;
  private BigDecimal ncaDisploss;
  private BigDecimal totalProfit;
  private BigDecimal incomeTax;
  private BigDecimal nIncome;
  private BigDecimal nIncomeAttrP;
  private BigDecimal minorityGain;
  private BigDecimal othComprIncome;
  private BigDecimal tComprIncome;
  private BigDecimal comprIncAttrP;
  private BigDecimal comprIncAttrMS;
  private BigDecimal ebit;
  private BigDecimal ebitda;
  private BigDecimal insuranceExp;
  private BigDecimal undistProfit;
  private BigDecimal distableProfit;

}
