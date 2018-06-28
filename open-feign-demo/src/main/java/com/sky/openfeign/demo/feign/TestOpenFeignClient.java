package com.sky.openfeign.demo.feign;

import com.sky.openfeign.demo.client.TestOpenFeignAPI;
import com.sky.openfeign.demo.data.MyResponse;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * TestOpenFeignClient
 *
 * @author skyfour
 * @date 2018/06/28 15:43
 */
@FeignClient(name = "${service.feign.name}", url = "${service.feign.url:}",
        fallback = TestOpenFeignClient.HystrixClientFallback.class,
        configuration = TestOpenFeignClient.MyConfig.class)
public interface TestOpenFeignClient extends TestOpenFeignAPI {


    @Slf4j
    @Component
    class HystrixClientFallback implements TestOpenFeignClient {
        @Override
        public MyResponse testController1() {
            return null;
        }

        @Override
        public String testController2() {
            return null;
        }

        @Override
        public String test3(MultipartFile multipartFile) {
            return null;
        }
    }

    @Configuration
    class MyConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        /**
         * 定义一个解析器,用来解析feign返回的response
         */
        class MyDecoder implements Decoder {
            @Autowired
            private ObjectFactory<HttpMessageConverters> messageConverters;

            @Override
            @SuppressWarnings("unchecked")
            public Object decode(Response response, Type type) throws IOException {
                System.out.println("test111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
                if (((Class) type).isAssignableFrom(MyResponse.class)) {
                    MyResponse myResponse = new MyResponse();
                    myResponse.setHeader(response.headers().get("test1").toString());
                    myResponse.setFile(read(response.body().asInputStream()));
                    return myResponse;
                }
                return new SpringDecoder(messageConverters).decode(response, type);
            }

            private byte[] read(InputStream inStream) throws IOException {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                inStream.close();
                return outputStream.toByteArray();
            }
        }

        @Bean
        public Decoder feignDecoder() {
            return new MyDecoder();
        }

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}
