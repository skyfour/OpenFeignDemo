package com.sky.openfeign.demo.mvc;

import com.sky.openfeign.demo.client.TestOpenFeignAPI;
import com.sky.openfeign.demo.data.MyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * TestController
 *
 * @author skyfour
 * @date 2018/06/28 15:11
 */
@Slf4j
@RestController
public class Controller {

    @Autowired
    private TestOpenFeignAPI testOpenFeignAPI;

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1() {
        MyResponse m = testOpenFeignAPI.testController1();
        return m.getHeader();
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2() {
        String s = testOpenFeignAPI.testController2();
        log.info("",s);
        return s;
    }

    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    public String test3(@RequestParam("file") MultipartFile multipartFile) {
        return testOpenFeignAPI.test3(multipartFile);
    }
}
