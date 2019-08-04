package com.linkin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ckfinder.connector.ConnectorServlet;

@Configuration
public class CKFinderServletConfig {

    @Value("${ckeditor.storage.image.path}")
    private String baseDir;
    
    @Value("${ckeditor.access.image.url}")
    private String baseURL;

    @Value("${ckeditor.license.key}")
    private String licenseKey;
    
    @Value("${ckeditor.license.name}")
    private String licenseName;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
    public ServletRegistrationBean connectCKFinder(){
		ServletRegistrationBean registrationBean=new ServletRegistrationBean(new ConnectorServlet(),"/ckfinder/core/connector/java/connector.java");
        registrationBean.addInitParameter("XMLConfig","classpath:/static/ckfinder.xml");
        registrationBean.addInitParameter("debug","false");
        registrationBean.addInitParameter("configuration","com.linkin.CKFinderConfig");

        registrationBean.addInitParameter("baseDir",baseDir);
        registrationBean.addInitParameter("baseURL",baseURL);
        registrationBean.addInitParameter("licenseKey",licenseKey);
        registrationBean.addInitParameter("licenseName",licenseName);
        return registrationBean;
    }

}
