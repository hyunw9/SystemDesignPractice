package systemdesign.shard_demo.external

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.HashMap


@Configuration
class JpaConfig {

    @Bean
    fun entityManagerFactoryBuilder(
        jpaVendorAdapter: JpaVendorAdapter?
    ): EntityManagerFactoryBuilder {
        return EntityManagerFactoryBuilder(jpaVendorAdapter, HashMap<String, Any>(), null)
    }

    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter {
        return HibernateJpaVendorAdapter()
    }
}
