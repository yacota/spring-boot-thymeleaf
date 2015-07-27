/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctv;

import java.util.Locale;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import static org.thymeleaf.spring4.view.ThymeleafViewResolver.FORWARD_URL_PREFIX;
import static org.thymeleaf.spring4.view.ThymeleafViewResolver.REDIRECT_URL_PREFIX;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 *
 * @author jllach
 */
public class ViewResolverCustom 
extends ThymeleafViewResolver {

    private final ApplicationContext applicationContext;

    public ViewResolverCustom(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext, "Application context is MANDATORY");
        this.applicationContext = applicationContext;
    }
    

    //
    // Business methods
    //
    
    /**
     * Checking the real existance of the content
     * @param viewName
     * @param locale
     * @return 
     */
    @Override
    protected boolean   canHandle(String viewName, Locale locale) {
        if (viewName.startsWith(REDIRECT_URL_PREFIX) || viewName.startsWith(FORWARD_URL_PREFIX)) return true;

        if(!super.canHandle(viewName, locale)) return false;

        for(ITemplateResolver resolver: getTemplateEngine().getTemplateResolvers()) {
            if(resolver instanceof TemplateResolver){
                TemplateResolver templateResolver = (TemplateResolver) resolver;
                String prefix = templateResolver.getPrefix();
                String suffix = templateResolver.getSuffix();
                // Remove the include part if the view is an include
                String viewNameFinal = cleanViewName(viewName);
                Resource res = applicationContext.getResource(prefix + viewNameFinal + suffix);
                if(res != null && res.exists()) return true;
            }
        }
        return false;
    }
    
    
    
    //
    // Private ones
    //

    /**
     * @param viewName
     * @return 
     */
    private String cleanViewName(String viewName) {
        String cleanedViewName = viewName;
        if(viewName != null) {
            int indexColons = viewName.indexOf("::");
            if (indexColons > -1) {
                cleanedViewName = viewName.substring(0, indexColons).trim();
            }
        }
        return cleanedViewName;
    }
}