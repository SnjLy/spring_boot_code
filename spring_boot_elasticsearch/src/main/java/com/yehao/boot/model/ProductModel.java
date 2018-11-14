package com.yehao.boot.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package: com.yehao.boot.model
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/10/12.
 */
@Data
@Document(indexName = "product", type = "product")
public class ProductModel implements Serializable{

    private Long id;
    private String productId;
    private String name;
    private Double price;
    private String description;
    private String coverPic;
    private Date createTime;
    private Date updateTime;


    public ProductModel() {
    }

    public ProductModel(String productId, String name) {
        this.productId = productId;
        this.name = name;
        this.price = 0.0d;
        this.createTime = new Date();
        this.id= System.currentTimeMillis();
    }



}
