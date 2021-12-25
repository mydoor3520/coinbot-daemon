package io.jeongho.coin.daemon.beans;

import lombok.Data;

@Data
public class Exchange {
    private String id;      // 거래소 id
    private String name;    // 거래소 이름
    private String use;     // 거래소 가격 가져오기 사용 여부
}
