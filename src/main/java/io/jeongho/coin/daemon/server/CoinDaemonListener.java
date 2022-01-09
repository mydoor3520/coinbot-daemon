package io.jeongho.coin.daemon.server;

import io.jeongho.coin.daemon.DaemonConstants;
import io.jeongho.coin.daemon.beans.CoinInfoCMC;
import io.jeongho.coin.daemon.service.CmcService;
import io.jeongho.coin.daemon.service.InfoService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * @author Cheol
 * 2020.10.14
 */
@Service
public class CoinDaemonListener {
	private static final Logger logger = LoggerFactory.getLogger(CoinDaemonListener.class);
	
	@Autowired
	@Qualifier(DaemonConstants.DB_SOURCE_COIN_STATION)
	private SqlSession chubSqlSession;

	@Autowired
	private CmcService cmcService;

	@Autowired
	private InfoService infoService;
	
	@Transactional
	public CoinInfoCMC getCoinInfo() {
		CoinInfoCMC coin = cmcService.getCoinInfo("btc".toUpperCase(Locale.ROOT));
		logger.info("coin = {}", coin);
		return coin;
	}

	@Transactional
	public void updateCoinInfo() {
		// 코인정보 업데이트
		cmcService.updateCoinInfo();
	}

	@Transactional
	public void updateForex() {
		// 환율정보 업데이트
		infoService.updateForex();
	}
}