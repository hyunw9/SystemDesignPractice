package systemdesign.shard_demo.external

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Configuration
class Shard1EntityConfig {


    @Bean
    fun shard1EntityManagerFactory(
        @Qualifier("shard1DataSource") dataSource: DataSource,
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean =
        builder
            .dataSource(dataSource)
            .packages("systemdesign.shard_demo.user")
            .persistenceUnit("SHARD1")
            .build()

}
