package io.jeongho.coin.daemon.service;
import io.jeongho.coin.daemon.DaemonConstants;
import io.jeongho.coin.daemon.beans.CoinInfoCMC;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmcService {
	
	private static final Logger logger = LoggerFactory.getLogger(CmcService.class);

	@Autowired
	private CommonService commonService;

	@Autowired
    private Environment environment;
	
	@Autowired
	@Qualifier(DaemonConstants.DB_SOURCE_COIN_STATION)
	private SqlSession SqlSession;

	public CoinInfoCMC getCoinInfo(String symbol){
		return SqlSession.selectOne("cmc.getCoinInfo", symbol);
	}

	/**
	 * 코인 정보 업데이트
	 * @param
	 */
	public void updateCoinInfo(){
		HttpGet http = new HttpGet(DaemonConstants.API_COIN_MARKET_CAP_LIST);
		commonService.setHeadersHasKey(http, DaemonConstants.API_COIN_MARKET_CAP_HEADER_API_KEY, DaemonConstants.API_COIN_MARKET_CAP_HEADER_API_KEY_VALUE);

		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();
		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);
			cMapList = (List<Map<String,Object>>)map.get("data");
		} catch(Exception e) {
			logger.error("Cmc Coin List Get Request error", e);
		}
		List<CoinInfoCMC> cList = new ArrayList<CoinInfoCMC>();
		int lth = 0;
		for(Map<String,Object> m : cMapList){
			CoinInfoCMC c = new CoinInfoCMC();

			c.setId(Integer.parseInt(m.get("id").toString()));
			c.setName(m.get("name").toString());
			c.setSymbol(m.get("symbol").toString());
			c.setSlug(m.get("slug").toString());

			if(m.get("num_market_pairs") != null){
				c.setNum_market_pairs(Integer.parseInt(m.get("num_market_pairs").toString()));
			}else{
				c.setNum_market_pairs(null);
			}
			c.setDate_added((m.get("date_added").toString()).split("T")[0]);
			if(m.get("max_supply") != null){
				c.setMax_supply(new BigDecimal(m.get("max_supply").toString()));
			}else{
				c.setMax_supply(null);
			}
			if(m.get("circulating_supply") != null){
				c.setCirculating_supply(new BigDecimal(m.get("circulating_supply").toString()));
			}else{
				c.setMax_supply(null);
			}
			if(m.get("total_supply") != null){
				c.setTotal_supply(new BigDecimal(m.get("total_supply").toString()));
			}else{
				c.setMax_supply(null);
			}
			c.setCmc_rank(Integer.parseInt(m.get("cmc_rank").toString()));
			if(m.get("last_updated") != null){
				String temp_date = m.get("last_updated").toString();
				if(temp_date.split("T").length > 1){
					c.setLast_updated((temp_date).split("T")[0] + " " +
								( ( (temp_date).split("T")[1]) ).substring(0,8) );
				}else{
					c.setLast_updated(null);
				}
			}else{
				c.setLast_updated(null);
			}

			Map<String,Object> priceData = (Map<String,Object>)((Map<String,Object>)m.get("quote")).get("USD");
			c.setPrice(Double.parseDouble(priceData.get("price").toString()));
			if(priceData.get("volume_24h") != null){
				c.setVolume_24h(new BigDecimal(priceData.get("volume_24h").toString()));
			}else{
				c.setVolume_24h(null);
			}
			if(priceData.get("volume_change_24h") != null){
				c.setVolume_change_24h(Double.parseDouble(priceData.get("volume_change_24h").toString()));
			}else{
				c.setVolume_change_24h(null);
			}
			if(priceData.get("percent_change_1h") != null){
				c.setPercent_change_1h(Double.parseDouble(priceData.get("percent_change_1h").toString()));
			}else{
				c.setPercent_change_1h(null);
			}
			if(priceData.get("percent_change_24h") != null){
				c.setPercent_change_24h(Double.parseDouble(priceData.get("percent_change_24h").toString()));
			}else{
				c.setPercent_change_24h(null);
			}
			if(priceData.get("percent_change_7d") != null){
				c.setPercent_change_7d(Double.parseDouble(priceData.get("percent_change_7d").toString()));
			}else{
				c.setPercent_change_7d(null);
			}
			if(priceData.get("percent_change_30d") != null){
				c.setPercent_change_30d(Double.parseDouble(priceData.get("percent_change_30d").toString()));
			}else{
				c.setPercent_change_30d(null);
			}
			if(priceData.get("percent_change_60d") != null){
				c.setPercent_change_60d(Double.parseDouble(priceData.get("percent_change_60d").toString()));
			}else{
				c.setPercent_change_60d(null);
			}
			if(priceData.get("percent_change_90d") != null){
				c.setPercent_change_90d(Double.parseDouble(priceData.get("percent_change_90d").toString()));
			}else{
				c.setPercent_change_90d(null);
			}
			if(priceData.get("market_cap") != null){
				c.setMarket_cap(new BigDecimal(priceData.get("market_cap").toString()));
			}else{
				c.setMarket_cap(null);
			}
			c.setMarket_cap_dominance(Float.parseFloat(priceData.get("market_cap_dominance").toString()));
			if(priceData.get("fully_diluted_market_cap") != null){
				c.setFully_diluted_market_cap(new BigDecimal(priceData.get("fully_diluted_market_cap").toString()));
			}else{
				c.setFully_diluted_market_cap(null);
			}
			cList.add(c);
			lth++;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("coin_list", cList);
		int insertRow = SqlSession.insert("cmc.updateCoinInfo", paramMap);
		logger.info("Processed Coin size : {}", cList.size());
	}

}
