package com.sky.openfeign.demo.client;

import com.sky.openfeign.demo.data.MyResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * TestOpenFeignAPI
 *
 * @author skyfour
 * @date 2018/06/28 15:00
 */
public interface TestOpenFeignAPI {

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    MyResponse testController1();

    @GetMapping(value = "/test2")
    String testController2();

    @RequestMapping(value = "/test3", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String test3(@RequestPart("file") MultipartFile multipartFile);
}
