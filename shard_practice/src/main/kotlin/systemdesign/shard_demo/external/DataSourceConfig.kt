package systemdesign.shard_demo.external

import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.shard1")
    fun shard1DataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.shard2")
    fun shard2DataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean
    fun shard1DataSource(): DataSource = shard1DataSourceProperties()
        .initializeDataSourceBuilder()
        .build()

    @Bean
    fun shard2DataSource(): DataSource = shard2DataSourceProperties()
        .initializeDataSourceBuilder()
        .build()

    @Bean
    fun routingDataSource(): DataSource {
        val targetDataSource: MutableMap<Any, Any> = HashMap()
        targetDataSource["shard1"] = shard1DataSource()
        targetDataSource["shard2"] = shard2DataSource()
        val router = DataSourceRouter()
        router.setTargetDataSources(targetDataSource)
        router.setDefaultTargetDataSource(shard1DataSource())
        return router
    }

    @Bean
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(routingDataSource())
            .packages("systemdesign.shard_demo.user")
            .persistenceUnit("default")
            .build()
    }

    @Bean
    fun transactionManager(
        entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}
