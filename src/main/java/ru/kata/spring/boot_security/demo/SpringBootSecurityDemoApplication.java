package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpFilter(){
        FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new HiddenHttpMethodFilter());
        registrationBean.addUrlPatterns("/admin/*","/admin-rest/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }

}
