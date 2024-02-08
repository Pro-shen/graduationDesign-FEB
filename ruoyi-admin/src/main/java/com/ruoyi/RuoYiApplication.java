package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  基于深度卷积网络的餐饮管理系统启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "                                                        ,d      ,d\n" +
                "                                                        88      88\n" +
                ",adPPYba,  ,adPPYba,    88,dPYba,,adPYba,  ,adPPYYba, MM88MMM MM88MMM\n" +
                "I8[    \"\" a8\"     \"\"    88P'   \"88\"    \"8a \"\"     `Y8   88      88\n" +
                " `\"Y8ba,  8b            88      88      88 ,adPPPPP88   88      88\n" +
                "aa    ]8I \"8a,   ,aa    88      88      88 88,    ,88   88,     88,\n" +
                "`\"YbbdP\"'  `\"Ybbd8\"'    88      88      88 `\"8bbdP\"Y8   \"Y888   \"Y888\n");
    }
}
