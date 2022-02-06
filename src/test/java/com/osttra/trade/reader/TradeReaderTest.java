package com.osttra.trade.reader;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.osttra.trade.match.TradeMatcher;
import com.osttra.trade.reader.TradeReader;

@SpringBootTest(properties = "spring.profiles.active:test")
class TradeReaderTest {

	@Autowired
	TradeReader tradeReader;

	@Test
	void readTradeTest() {

		TradeMatcher tradeMatcherMock = Mockito.mock(TradeMatcher.class);
		Mockito.when(tradeMatcherMock.matchTrade(Mockito.any())).thenReturn(true);

		tradeReader.readTrade();

	}
}
