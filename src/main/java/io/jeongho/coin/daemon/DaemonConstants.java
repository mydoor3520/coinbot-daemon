package io.jeongho.coin.daemon;

public class DaemonConstants {
	private DaemonConstants() {
		throw new IllegalStateException("DaemonConstants class");
	}

	public static final String DB_SOURCE_COIN_STATION = "jdbcCoinStation";

	public static final String API_COIN_MARKET_CAP_HEADER_API_KEY = "X-CMC_PRO_API_KEY";

	/* 코인 마켓캡 요청 url */
	public static final String API_COIN_MARKET_CAP_LIST = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=5000";

	/* 두나무 제공 환율 url */
	public static final String API_FOREX = "https://quotation-api-cdn.dunamu.com/v1/forex/recent?codes=FRX.KRWUSD";
}