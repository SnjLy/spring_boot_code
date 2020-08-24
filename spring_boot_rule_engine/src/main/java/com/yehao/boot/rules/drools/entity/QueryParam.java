package com.yehao.boot.rules.drools.entity;

import lombok.Data;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rule.drools.QueryParam
 */
@Data
public class QueryParam {

    private String paramId ;
    private String paramSign ;

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamSign() {
        return paramSign;
    }

    public void setParamSign(String paramSign) {
        this.paramSign = paramSign;
    }
}
