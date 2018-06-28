# OpenFeignDemo
OpenFeign demo
服务间调用feign,在进行文件的传递会出现的情况
所使用的是spring boot 2.0
jdk是1.8
要进行pom文件的添加
spring boot 2.0 的feign依赖&lt;br&gt;
&lt;dependency&gt;&lt;br&gt;
&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.springframework.cloud&lt;/groupId&gt;
&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;spring-cloud-starter-openfeign&lt;/artifactId&gt;
&nbsp;&nbsp;&nbsp;&lt;version&gt;2.0.0.RELEASE&lt;/version&gt;&lt;br&gt;
&lt;dependency&gt;


如果要进行服务间调用以formData传递文件的接口,需要引入openfeign文件，如下<br>
&lt;dependency&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;io.github.openfeign.form&lt;/groupId&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;feign-form&lt;/artifactId&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;3.3.0&lt;/version&gt;<br>
&lt;/dependency&gt;<br>
&lt;dependency&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;io.github.openfeign.form&lt;/groupId&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;feign-form-spring&lt;/artifactId&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;3.3.0&lt;/version&gt;<br>
&lt;/dependency&gt;<br>

feign接口里面写的接收参数要写@RequestPart如果按照正常写url用@RequestParam的话会报
the request was rejected because no multipart boundary was

同事要在feign里面定义一个配置configuration

<code>      
   
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
</code>

配置一个Decoder是为了如果feign调用的接口传回来的是空,文件放到response的流里面等等,
如果不用自己定义的decoder,会什么也收不到,所以需要定义一个Decoder来解析feign传回来的response,
我们可以在feign的response里面获取到我们想要的,也可以不用自己定义Decoder,但是Encoder必须要有,
否则文件无法传送.
