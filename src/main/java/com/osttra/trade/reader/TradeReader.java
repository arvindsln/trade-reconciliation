/**
 * 
 */
package com.osttra.trade.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.osttra.trade.match.TradeMatcher;
import com.osttra.trade.pojo.Trade;

/**
 * @author Arvind
 *
 */
@Component
public class TradeReader {

	@Autowired
	TradeMatcher tradeMatcher;

	public void readTrade() {

		File file;
		try {
			file = ResourceUtils.getFile("classpath:trades/trade.txt");
			// Read File Content
			Scanner sc = new Scanner(file);

			while (sc.hasNext()) {
				String[] tradeStr = sc.nextLine().split(",");

				Trade newTrade = Trade.builder().firstParty(tradeStr[0]).secondParty(tradeStr[1])
						.amount(new BigDecimal(tradeStr[2])).tradeRef(tradeStr[3]).location(tradeStr[4])
						.product(tradeStr[5]).build();

				tradeMatcher.matchTrade(newTrade);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
