package es.indaba.sqld.annotations.spring.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import es.indaba.sqld.annotations.spring.api.QueryRepositories;
import es.indaba.sqld.annotations.spring.api.QueryRepository;
import es.indaba.sqld.api.QueryDefinitionRepository;
import es.indaba.sqld.impl.loader.QueryDefinitionClassPathLoader;

@Component
public class QueryRepositoryLoader implements BeanPostProcessor {

    private final ConfigurableListableBeanFactory configurableBeanFactory;
    
    private QueryDefinitionRepository repository;
    
    @Autowired
    public QueryRepositoryLoader(final ConfigurableListableBeanFactory beanFactory) {
        this.configurableBeanFactory = beanFactory;
    }
    
    @Bean
    public QueryDefinitionRepository getRepository() {
        if (repository == null) {
            repository = new QueryDefinitionRepository(); 
        }
        return repository;
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
        
        QueryDefinitionRepository repository =  getRepository();
        
        if (!prefixes.isEmpty()) {
            for (final String prefix:prefixes) {
                QueryDefinitionClassPathLoader.loadQueryDefinitionFiles(prefix, repository);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
