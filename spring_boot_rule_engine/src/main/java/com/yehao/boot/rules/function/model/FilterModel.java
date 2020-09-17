package com.yehao.boot.rules.function.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * @author : LiuYong at 2020-08-25
 * @package: com.yehao.boot.rules.function.model
 */
@Data
@Accessors(chain = true)
public class FilterModel {
    private boolean login;
    private Set<Integer> whiteBrands;
    private List<Long> blackCompanies;
}
