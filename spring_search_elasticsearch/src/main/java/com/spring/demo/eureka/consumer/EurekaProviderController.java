package com.spring.demo.eureka.consumer;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.spring.demo.eureka.consumer
 *
 * @author: liuyong
 * @date: 2019-05-16
 */

@RequestMapping("/provider")
@RestController
public class EurekaProviderController {

    @RequestMapping("/test")
    public String provide(@RequestParam("param") String param){
        if (StringUtils.isBlank(param)){
            param = "给你一个靠谱的provider";
        }
        return param;
    }


}
