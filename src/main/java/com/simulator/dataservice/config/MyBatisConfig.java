package com.simulator.dataservice.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnClass({ EnableTransactionManagement.class, EntityManager.class })
@MapperScan("com.simulator.dataservice.entity")
public class MyBatisConfig {

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSourc) {
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSourc);
            sessionFactory.setTypeAliasesPackage("com.simulator.dataservice.entity");
            //添加XML目录
            ResourcePatternResolver resolver = ResourcePatternUtils
                    .getResourcePatternResolver(new DefaultResourceLoader());
         // MyBatis のコンフィグレーションファイル
            sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
            sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
            return sessionFactory.getObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}