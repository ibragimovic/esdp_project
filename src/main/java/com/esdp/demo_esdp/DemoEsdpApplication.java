package com.esdp.demo_esdp;

import freemarker.ext.jsp.TaglibFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import static java.util.Collections.singletonList;

@SpringBootApplication
public class DemoEsdpApplication {
    public DemoEsdpApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(singletonList("/META-INF/security.tld"));
        TaglibFactory tagLibFactory = freeMarkerConfigurer.getTaglibFactory();
        if (tagLibFactory.getObjectWrapper() == null) {
            tagLibFactory.setObjectWrapper(freeMarkerConfigurer.getConfiguration().getObjectWrapper());
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoEsdpApplication.class, args);
    }

}
