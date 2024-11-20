package systemdesign.shard_demo.external

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Configuration
class Shard2EntityConfig {

    @Bean
    fun shard2EntityMangerFactory(
        @Qualifier("shard2DataSource") dataSource: DataSource,
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages("systemdesign.shard_demo.user")
            .persistenceUnit("SHARD2")
            .build()
    }
}
