package com.osttra.trade.match;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.osttra.trade.match.TradeMatcher;
import com.osttra.trade.pojo.Trade;

@SpringBootTest(properties = "spring.profiles.active:test")
class TradeMatcherTest {

	@Autowired
	TradeMatcher tradeMatcher;

	@BeforeEach
	public void setup() {
		tradeMatcher.tradeList.clear();
	}

	@Test
	void matchTradeTestNullTrade() {

		boolean isMatched = tradeMatcher.matchTrade(null);

		assertFalse(isMatched);
		assertEquals(0, tradeMatcher.tradeList.size());
	}

	@Test
	void matchTradeTestEmptyTrade() {

		boolean isMatched = tradeMatcher.matchTrade(Trade.builder().build());

		assertFalse(isMatched);
		assertEquals(1, tradeMatcher.tradeList.size());
	}

	@Test
	void matchTradeTestSingleValidTrade() {

		Trade trade = Trade.builder().firstParty("a").secondParty("a").amount(new BigDecimal(100)).tradeRef("a")
				.location("a").product("a").build();
		boolean isMatched = tradeMatcher.matchTrade(trade);

		assertFalse(isMatched);
		assertEquals(1, tradeMatcher.tradeList.size());
	}

	@Test
	void matchTradeTestWithMatchingTrade() {

		Trade trade = Trade.builder().firstParty("a").secondParty("a").amount(new BigDecimal(100)).tradeRef("a")
				.location("a").product("a").build();

		boolean isMatched = tradeMatcher.matchTrade(trade);

		assertFalse(isMatched);
		assertEquals(1, tradeMatcher.tradeList.size());

		isMatched = tradeMatcher.matchTrade(trade);

		assertTrue(isMatched);
		assertEquals(0, tradeMatcher.tradeList.size());
	}

	@Test
	void matchTradeTestWithUnmatchingTrade() {

		Trade trade = Trade.builder().firstParty("a").secondParty("a").amount(new BigDecimal(100)).tradeRef("a")
				.location("a").product("a").build();

		boolean isMatched = tradeMatcher.matchTrade(trade);

		assertFalse(isMatched);
		assertEquals(1, tradeMatcher.tradeList.size());

		Trade trade1 = Trade.builder().firstParty("b").secondParty("b").amount(new BigDecimal(10)).tradeRef("b")
				.location("b").product("b").build();

		isMatched = tradeMatcher.matchTrade(trade1);

		assertFalse(isMatched);
		assertEquals(2, tradeMatcher.tradeList.size());
	}

}
