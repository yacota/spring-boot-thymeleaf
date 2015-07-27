package com.ctv;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.servlet.Servlet;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;


@Configuration
@AutoConfigureAfter(ThymeleafAutoConfiguration.class)
@ConditionalOnClass({SpringTemplateEngine.class})
public class ThymeleafConfiguration {
	
    
	@Configuration
	@ConditionalOnClass({ Servlet.class })
	@ConditionalOnWebApplication
	protected static class ThymeleafTemplateResolverConfiguration {


        @Autowired
        private ApplicationContext  applicationContext;
        
        @Autowired
		private ThymeleafProperties properties;
        
		@Autowired
		private SpringTemplateEngine templateEngine;
        
        @Autowired
        private SpringResourceResourceResolver resourceResolver;


		@Bean
		public ThymeleafViewResolver thymeleafViewResolver() {
            ThymeleafViewResolver resolver = new ViewResolverCustom(applicationContext);
			resolver.setTemplateEngine(this.templateEngine);
			resolver.setCharacterEncoding(this.properties.getEncoding());
			resolver.setContentType(appendCharset(this.properties.getContentType(), resolver.getCharacterEncoding()));
			resolver.setExcludedViewNames(this.properties.getExcludedViewNames());
			resolver.setViewNames(this.properties.getViewNames());
			resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
            
            // adding some templateResolvers here
            TemplateResolverCustom tr1 = new TemplateResolverCustom(properties, resourceResolver, 100);
            TemplateResolverCustom tr2 = new TemplateResolverCustom(properties, resourceResolver, -100);
            TemplateResolverCustom tr3 = new TemplateResolverCustom(properties, resourceResolver, null);
            templateEngine.addTemplateResolver(tr1);
            templateEngine.addTemplateResolver(tr2);
            templateEngine.addTemplateResolver(tr3);
			templateEngine.getTemplateResolvers().forEach(r->r.initialize());

			return resolver;
		}
        
        private String appendCharset(String type, String charset) {
			if (type.contains("charset=")) {
				return type;
			}
			return type + ";charset=" + charset;
		}
	}
}