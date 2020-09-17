package com.yehao.boot.rules.function.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : LiuYong at 2020-08-25
 * @package: com.yehao.boot.rules.function.model
 */
@Data
@Accessors(chain = true)
public class Recall {

    private int id;
    private String name;
    private Long userId;


}
