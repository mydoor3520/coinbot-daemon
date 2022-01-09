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
	 * 코인마켓캡의 코인 정보 검색 및 push (1시간마다)
	 */
	@Scheduled(fixedDelay = 7200000, initialDelay = 3000)
	public void updateCoinLists() {
		listener.updateCoinInfo();
	}

	/**
	 *  두나무제공 환율 업데이트 (30분마다)
	 */
	@Scheduled(fixedDelay = 3600000, initialDelay = 3000)
	public void updateForex() {
		listener.updateForex();
	}
}