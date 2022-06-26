package net.openwebinars.springboot.multitenant.app.config.web;

import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.multitenant.app.multitenant.intercept.TenantRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TenantRequestInterceptor tenantRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantRequestInterceptor).addPathPatterns("/**");
    }
}
