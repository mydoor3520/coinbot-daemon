package io.jeongho.coin.daemon.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Cheol
 * 2020.10.14
 */
@Component
public class CoinDaemonHandler {
	
	@Autowired
	private CoinDaemonListener listener;

	/**
	 * 코인마켓캡의 코인 정보 검색 및 push
	 */
	@Scheduled(fixedDelay = 7200000, initialDelay = 3000)
	public void updateCoinLists() {
		listener.updateCoinInfo();
	}
}