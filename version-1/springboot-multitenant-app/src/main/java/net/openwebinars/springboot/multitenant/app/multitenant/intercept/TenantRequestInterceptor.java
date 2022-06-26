package net.openwebinars.springboot.multitenant.app.multitenant.intercept;


import lombok.extern.java.Log;
import net.openwebinars.springboot.multitenant.app.multitenant.context.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(TenantRequestInterceptor.class);

    public static final String TENANT_ID_HEADER = "X-TenantID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return Optional.ofNullable(request)
                .map(this::extractTenantIdFromRequest)
                .map(this::setTenantContext)
                .orElse(false);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }

    private boolean setTenantContext(String tenant) {
        logger.debug(String.format("Current Tenant Context: %s", tenant));
        TenantContext.setCurrentTenant(tenant);
        return true;
    }

    private String extractTenantIdFromRequest(HttpServletRequest request) {
        String uri = request.getRequestURI().toString();
        String tenantId = request.getHeader(TENANT_ID_HEADER);

        String tenantForLog = (tenantId != null) ? tenantId : "public";

        logger.debug(String.format("Requested URI: %s, Tenant ID: %s", uri, tenantForLog));

        return tenantId;
    }




}
