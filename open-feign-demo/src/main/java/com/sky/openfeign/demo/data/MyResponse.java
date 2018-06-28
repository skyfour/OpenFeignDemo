package com.sky.openfeign.demo.data;

import lombok.Data;

/**
 * MyResponse
 *
 * @author skyfour
 * @date 2018/06/28 15:54
 */
@Data
public class MyResponse {
    private byte[] file;
    private String header;
}
