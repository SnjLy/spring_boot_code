package com.yehao.boot.search.client;

import lombok.Data;

/**
 * com.yehao.boot.search.client
 *
 * @author: SNJly
 * @date: 2019-07-22
 */
@Data
public final class FinalClient {
    private String name;
    private Integer value;
    private final String className = this.getClassName();


    public static void main(String[] args) {
        FinalClient finalClient = new FinalClient();
        System.out.println(finalClient.toString());
    }
}
