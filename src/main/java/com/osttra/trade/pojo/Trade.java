package com.osttra.trade.pojo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Arvind
 *
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Trade {
String firstParty="";
String secondParty="";
BigDecimal amount=BigDecimal.ZERO;
String tradeRef="";
String location="";
String product="";
}
