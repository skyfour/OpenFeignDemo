package com.sky.openfeigncontroller.mvc;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OpenController
 *
 * @author skyfour
 * @date 2018/06/28 15:40
 */
@RestController
public class OpenController {

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public void testController1(HttpServletResponse response) {
        response.addHeader("test1","test1");
    }

    @GetMapping(value = "/test2")
    public String testController2() {
        return "test2";
    }

    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    public String test3(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        byte[] b = multipartFile.getBytes();
        return "test3";
    }
}
