package org.spring.springboot.zw.controller;

import base.zw.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Date;


/**
 * Title: AppController Description:
 *
 * @author wangyanlai
 * @version 2019年1月20日 下午4:53:32
 */

@Configuration
public class UsingStaticController extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/videosAndBooks/**").addResourceLocations("classpath:/videosAndBooks/");
        registry.addResourceHandler("/helpcenter/videosAndBooks/**").addResourceLocations("classpath:/helpcenter/videosAndBooks/");
    }

}