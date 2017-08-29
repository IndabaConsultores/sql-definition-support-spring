package es.indaba.sqld.annotations.spring.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import es.indaba.sqld.annotations.spring.api.QueryRepositories;
import es.indaba.sqld.annotations.spring.api.QueryRepository;
import es.indaba.sqld.loader.SQLDClassPathLoader;

@Component
public class QueryRepositoryLoader implements BeanPostProcessor {

    private final ConfigurableListableBeanFactory configurableBeanFactory;

    @Autowired
    public QueryRepositoryLoader(final ConfigurableListableBeanFactory beanFactory) {
        this.configurableBeanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<? extends Object> beanClass = bean.getClass();
        List<String> prefixes = new ArrayList<>();
        if (beanClass.isAnnotationPresent(QueryRepository.class)) {
            QueryRepository respository = beanClass.getAnnotation(QueryRepository.class);
            prefixes.add(respository.value());
        } else if (beanClass.isAnnotationPresent(QueryRepositories.class)) {
            QueryRepositories respositories = beanClass.getAnnotation(QueryRepositories.class);
            QueryRepository[] repositories = respositories.value();
            for (QueryRepository repository : Arrays.asList(repositories)) {
                final String prefixLookup = repository.value();
                prefixes.add(prefixLookup);
            }
        }
        
        if (!prefixes.isEmpty()) {
            for (final String prefix:prefixes) {
                SQLDClassPathLoader.loadSqlds(prefix);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
