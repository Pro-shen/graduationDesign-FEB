package com.ruoyi.web.controller.identify;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.TBase64Info;
import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.domain.TSalestable;
import com.ruoyi.system.service.ITFixPriceService;
import com.ruoyi.system.service.ITMenuService;
import com.ruoyi.system.service.ITSalestableService;
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
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    private ITMenuService itMenuService;

    @Autowired
    private ITSalestableService itSalestableService;

    @Autowired
    private ITFixPriceService itFixPriceService;


    @PreAuthorize("@ss.hasAnyPermi('identify:identify:add')")
    @PostMapping("/addList")
    @Log(title = "销售表添加", businessType = BusinessType.INSERT)
    public AjaxResult addList(@RequestBody List<TSalestable> tSalestables) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itSalestableService.addList(tSalestables));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('identify:identify:upload')")
    @PostMapping("/upload")
    @Log(title = "识别图片", businessType = BusinessType.IMPORT)
    public AjaxResult upload(@RequestBody TBase64Info tBase64Info) {
        AjaxResult ajax = AjaxResult.success();
//        String url = "http://119.45.195.240:5555/upload";
        String url = "http://127.0.0.1:5555/upload";
//        String url = "http://119.45.195.240:7777/upload";
//        String url = "http://127.0.0.1:7777/upload";
        ResponseEntity<TBase64Info> responseEntity = restTemplate.postForEntity(url, tBase64Info, TBase64Info.class);
        ajax.put("data", responseEntity);
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:identify:listTree')")
    @Log(title = "菜单树查询", businessType = BusinessType.OTHER)
    @PostMapping("/listTree")
    public AjaxResult listTree(@Validated @RequestBody List<TFixPrice> tFixPrices){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itMenuService.listTree(tFixPrices));
        return ajax;
    }

    @PreAuthorize("@ss.hasPermi('restaurant:identify:listTree')")
    @GetMapping("/listPrice")
    @Log(title = "价格管理列表", businessType = BusinessType.OTHER)
    public TableDataInfo list(TFixPrice tFixPrice) {
        startPage();
        List<TFixPrice> tFixPrices = itFixPriceService.list(tFixPrice);
        return getDataTable(tFixPrices);
    }


    public String uploadFile(String filePath) {
        String requestUrl = "http://119.45.195.240:5555/image";
//        String requestUrl = "http://127.0.0.1:5555/image";
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
