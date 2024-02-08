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
                "                                  __    __\n" +
                "  ______ ____       _____ _____ _/  |__/  |_\n" +
                " /  ___// ___\\     /     \\\\__  \\\\   __\\   __\\\n" +
                " \\___ \\\\  \\___    |  Y Y  \\/ __ \\|  |  |  |\n" +
                "/____  >\\___  >   |__|_|  (____  /__|  |__|\n" +
                "     \\/     \\/          \\/     \\/    ");
    }
}
