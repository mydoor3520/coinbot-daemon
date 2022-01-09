package io.jeongho.coin.daemon.service;
import io.jeongho.coin.daemon.DaemonConstants;
import io.jeongho.coin.daemon.beans.CoinInfoCMC;
import io.jeongho.coin.daemon.beans.ForexVo;
import io.jeongho.coin.daemon.config.CommonValues;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

	@Autowired
	private CommonService commonService;

	@Autowired
	private CommonValues commonValues;

	@Autowired
	@Qualifier(DaemonConstants.DB_SOURCE_COIN_STATION)
	private SqlSession sqlSession;

	/**
	 * 환율정보 업데이트
	 */
	public void updateForex() {

		HttpGet http = new HttpGet(DaemonConstants.API_FOREX);
		commonService.setHeadersWithoutKey(http);

		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToListMap(dataStr).get(0);

			ForexVo vo =  new ForexVo(
					map.get("currencyCode").toString(),
					Float.parseFloat(map.get("basePrice").toString()),
					Float.parseFloat(map.get("changePrice").toString()),
					map.get("change").toString()
			);

			sqlSession.update("forex.updateForex", vo);
		} catch(Exception e) {
			logger.error("Cmc Coin List Get Request error", e);
		}

	}
}
