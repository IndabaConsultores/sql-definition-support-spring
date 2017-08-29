package es.indaba.sqld.annotations.spring.impl;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import es.indaba.sqld.QueryDefinitionsHolder;
import es.indaba.sqld.annotations.spring.api.QueryResolver;

@Component
public class QueryResolverProducer {

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public String resolveQuery(InjectionPoint ip) {

        QueryResolver resolver = AnnotationUtils.getAnnotation(ip.getAnnotatedElement(), QueryResolver.class);
        if (resolver == null) {
            return null;
        }

        String key = resolver.name();
        return QueryDefinitionsHolder.getQueryAsString(key);


    }
}
