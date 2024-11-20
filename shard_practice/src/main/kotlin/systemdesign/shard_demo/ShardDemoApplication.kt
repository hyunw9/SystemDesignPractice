package systemdesign.shard_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@EnableConfigurationProperties
class ShardDemoApplication

fun main(args: Array<String>) {
	runApplication<ShardDemoApplication>(*args)
}
