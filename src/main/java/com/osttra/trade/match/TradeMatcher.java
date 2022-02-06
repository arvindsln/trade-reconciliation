/**
 * 
 */
package com.osttra.trade.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.osttra.trade.pojo.Trade;
import com.osttra.trade.reader.TradeReader;

/**
 * @author Arvind
 *
 */
@Component
public class TradeMatcher {

	@Autowired
	private Environment env;

	List<Trade> tradeList = new LinkedList<>();
	List<Trade> matchedTradeList;

	public boolean matchTrade(Trade newTrade) {
		boolean isMatched = false;
		if (newTrade != null) {
			if (tradeList.size() == 0) {
				tradeList.add(newTrade);
			} else {
				matchedTradeList = new ArrayList<>();

				for (Trade existingTrade : tradeList) {
					int weightage = calCulateWeightage(newTrade, existingTrade);

					if (weightage == 100) {
						System.out.println("100% Trade matched, It's eligible for removing from system");
						matchedTradeList.add(existingTrade);
						isMatched = true;
					}
				}

				if (!isMatched) {
					tradeList.add(newTrade);
				}
				// Remove matched trades from system
				for (Trade trade : matchedTradeList) {
					tradeList.remove(trade);
				}
			}
			System.out.println("Final Trade List Size->" + tradeList.size());
		}
		return isMatched;
	}

	private int calCulateWeightage(Trade newTrade, Trade existingTrade) {
		// TODO Auto-generated method stub
		int totalWeight = 0;
		if (matchParties(newTrade, existingTrade)) {
			System.out.println("First and second parties matched, trade is eligible for matching");
			totalWeight = Integer.valueOf(env.getProperty("first_party"))
					+ Integer.valueOf(env.getProperty("second_party"));
			totalWeight += matchAmount(newTrade, existingTrade);
			totalWeight += matchTradeRef(newTrade, existingTrade);
			totalWeight += matchProduct(newTrade, existingTrade);
		}

		return totalWeight;
	}

	private int matchProduct(Trade newTrade, Trade existingTrade) {
		if (existingTrade.getProduct().equals(newTrade.getProduct())) {
			System.out.println("product matched");
			return Integer.valueOf(env.getProperty("product"));
		}
		return 0;
	}

	private int matchTradeRef(Trade newTrade, Trade existingTrade) {

		if (existingTrade.getTradeRef().equals(newTrade.getTradeRef())) {
			System.out.println("trade ref matched");
			return Integer.valueOf(env.getProperty("trade_ref"));
		}
		return 0;
	}

	private int matchAmount(Trade newTrade, Trade existingTrade) {
		if (existingTrade.getAmount().equals(newTrade.getAmount())) {
			System.out.println("Amount matched");
			return Integer.valueOf(env.getProperty("amount"));
		}
		return 0;
	}

	private boolean matchParties(Trade newTrade, Trade existingTrade) {
		boolean isMatched = false;
		if (existingTrade.getFirstParty().equals(newTrade.getSecondParty())
				&& existingTrade.getSecondParty().equals(newTrade.getFirstParty())) {
			isMatched = true;
		}
		return isMatched;
	}
}
