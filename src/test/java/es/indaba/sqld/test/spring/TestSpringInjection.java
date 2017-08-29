package es.indaba.sqld.test.spring;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.indaba.sqld.test.spring.beans.TestConfiguration;
import es.indaba.sqld.test.spring.beans.TestInjectionBean;
import org.junit.Assert;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class TestSpringInjection {

    @Autowired
    TestInjectionBean bean;

    @Test
    public void testQueryInjection() {
        Assert.assertEquals("QUERY1_CONTENT", bean.getQuery1());
    }
}
