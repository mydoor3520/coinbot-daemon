package io.jeongho.coin.daemon;

public class DaemonConstants {
	private DaemonConstants() {
		throw new IllegalStateException("DaemonConstants class");
	}

	public static final String DB_SOURCE_COIN_STATION = "jdbcCoinStation";

	public static final String API_COIN_MARKET_CAP_HEADER_API_KEY = "X-CMC_PRO_API_KEY";

	/* 코인 마켓캡 요청 url */
	public static final String API_COIN_MARKET_CAP_LIST = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=5000";

}