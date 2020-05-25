package lazy.configurations;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("lazy/**")
@EntityScan("lazy/**")
public class MyConfiguration {

    @Autowired
    private BeanFactory beanFactory;

    @Value("${spring.jpa.generate-ddl}")
    private String generateDdl;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialectName;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hbm2ddlAuto;

    @Value("${spring.jpa.properties.hibernate.implicit-naming-strategy}")
    private String implicitNamingStrategy;

    @Value("${spring.jpa.globally-quoted-identifiers}")
    private String globallyQuotedIdentifiers;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factoryBuilder, @Qualifier("dataSource") DataSource dataSource) throws ClassNotFoundException {
        List<String> packagesToScan = AutoConfigurationPackages.get(beanFactory);
        packagesToScan.add("lazy.database");
        return factoryBuilder.dataSource(dataSource)
                .packages(packagesToScan.toArray(new String[]{}))
                .properties(getProperties())
                .persistenceUnit("default")
                .build();
    }

    private Map<String, Object> getProperties() throws ClassNotFoundException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("generate-ddl", generateDdl);
        properties.put(Environment.DIALECT, Class.forName(dialectName));
        properties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        properties.put(Environment.IMPLICIT_NAMING_STRATEGY, implicitNamingStrategy);
        properties.put(Environment.GLOBALLY_QUOTED_IDENTIFIERS, globallyQuotedIdentifiers);
        return properties;
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
