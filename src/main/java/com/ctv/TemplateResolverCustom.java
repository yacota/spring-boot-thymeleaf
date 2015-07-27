/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctv;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 *
 * @author jllach
 */
public class TemplateResolverCustom 
extends      TemplateResolver {
    
    
    public TemplateResolverCustom(ThymeleafProperties properties, IResourceResolver resourceResolver, Integer order) {
        super();
        this.setOrder(order); // no matter what order I use here, TemplateResolver will always be prefered
        this.setResourceResolver(resourceResolver);
        this.setPrefix(properties.getPrefix());
        this.setSuffix(properties.getSuffix());
        this.setTemplateMode(properties.getMode());
        this.setCharacterEncoding(properties.getEncoding());
        this.setCacheable(properties.isCache());
    }
    
    @Override
    protected String computeResourceName(final TemplateProcessingParameters templateProcessingParameters) {
        return "/mine/"+super.computeResourceName(templateProcessingParameters);
    }
}