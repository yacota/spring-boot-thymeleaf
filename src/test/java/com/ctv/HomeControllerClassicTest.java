package com.ctv;

import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HomeControllerClassicTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SpringTemplateEngine engine;
    
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void verifiesHomePageLoads() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verifyTemplateResolverOrder() {
        Set<ITemplateResolver> templateResolvers = engine.getTemplateResolvers();
        Assert.assertNotNull(templateResolvers);
        Assert.assertEquals(4, templateResolvers.size());
        Iterator<ITemplateResolver> iterator = templateResolvers.iterator();
        ITemplateResolver minenull = iterator.next();
        Assert.assertNull(minenull.getOrder());
        Assert.assertEquals("com.ctv.TemplateResolverCustom", minenull.getName());
        ITemplateResolver springbootsone = iterator.next();
        Assert.assertNull(springbootsone.getOrder());
        Assert.assertEquals("org.thymeleaf.templateresolver.TemplateResolver", springbootsone.getName());
        ITemplateResolver mine1 = iterator.next();
        Assert.assertEquals(Integer.valueOf(-100), mine1.getOrder());
        Assert.assertEquals("com.ctv.TemplateResolverCustom", mine1.getName());
        ITemplateResolver mine2 = iterator.next();
        Assert.assertEquals(Integer.valueOf(100), mine2.getOrder());
        Assert.assertEquals("com.ctv.TemplateResolverCustom", mine2.getName());
    }
}