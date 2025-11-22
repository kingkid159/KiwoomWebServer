package com.kiwoom.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StockDetailResponse {

  private String curPrc;
  private String stkCd;
  private String stkNm;
  private String rmndQty;
  private String buyUv;
  private String buyWght;
  private String evltvPrft;
  private String prftRt;
  private String evltAmt;
  private String evltWght;
}
