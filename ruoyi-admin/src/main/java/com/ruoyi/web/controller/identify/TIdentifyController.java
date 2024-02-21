package com.ruoyi.web.controller.identify;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.TBase64Info;
import com.ruoyi.system.domain.TPerformance;
import com.ruoyi.system.domain.Test111;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/identify/identify")
public class TIdentifyController extends BaseController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @PreAuthorize("@ss.hasPermi('identify:identify:list')")
    @GetMapping("/list")
    public int list(){
//        System.out.println("111111");
//        Test111 test111 = new Test111();
//        test111.setTest("test");
//        String url = "http://127.0.0.1:5555/test";
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,test111,String.class);
//        String a = responseEntity.getBody();
//        System.out.println(a);
        uploadFile("/Users/shencheng/Pictures/006.jpg");
//        startPage();
//        List<TMenu> tMenus = itMenuService.selectTMenuList(tMenu);
        return 1;
    }

    @PreAuthorize("@ss.hasAnyPermi('identify:identify:upload')")
    @PostMapping("/upload")
    public AjaxResult upload(@RequestBody TBase64Info tBase64Info){
        AjaxResult ajax = AjaxResult.success();
        String url = "http://127.0.0.1:5555/upload";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,tBase64Info,String.class);
        System.out.println(responseEntity);
//        String a = responseEntity.getBody();
//        ajax.put("data",itPerformanceService.edit(tPerformance));
        return ajax;
    }

    public String uploadFile(String filePath) {
        String requestUrl = "http://127.0.0.1:5555/image";
        File file = new File(filePath);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        //1.设置请求类型 上传文件必须用表单类型
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //2.设置内容长度，必须设置
        headers.setContentLength(file.length());
        //3.设置文件名称，处理文件名是中文的情况
        headers.setContentDispositionFormData("fileName", URLEncoder.encode(file.getName()));
        //4.设置请求体，注意是LinkedMultiValueMap
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        //5.把文件填充到表单里，注意使用FileSystemResource
        form.add("imagefile", new FileSystemResource(file));
        HttpEntity requestEntity = new HttpEntity<>(form, headers);
        //6.发起请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        return "2";
    }



//    public void main(String[] args){
//        String test = "test";
//        String url = "http://localhost:8081/Test/test/";
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,test,String.class);
//        return responseEntity.getBody();
//    }




}
