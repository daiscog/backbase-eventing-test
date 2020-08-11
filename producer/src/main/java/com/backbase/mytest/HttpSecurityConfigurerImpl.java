package com.backbase.mytest;

import com.backbase.buildingblocks.security.HttpSecurityConfigurationException;
import com.backbase.buildingblocks.security.HttpSecurityConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class HttpSecurityConfigurerImpl implements HttpSecurityConfigurer {

    private static final Logger log = LoggerFactory.getLogger(HttpSecurityConfigurerImpl.class);

    @Override
    public void configure(HttpSecurity http) throws HttpSecurityConfigurationException {
        try {
            log.warn("Disabling CSRF");
            http.csrf().disable();
        } catch (Exception ex) {
            log.error("Error disabling CSRF", ex);
        }
    }
}
