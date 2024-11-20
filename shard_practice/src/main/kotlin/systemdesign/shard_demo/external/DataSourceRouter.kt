package systemdesign.shard_demo.external

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.helpers.Reporter.info
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

@Slf4j
@RequiredArgsConstructor
class DataSourceRouter : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey(): Any {
        val shardKey: Long? = ThreadLocalDatabaseContextHolder.getShardKey()
        val dataSource = when {
            shardKey == null -> {
                info("Current datasource is null")
                "shard1"
            }

            shardKey % 2 == 0L -> {
                info("now datasource is shard1")
                "shard1"
            }

            else -> {
                info("now datasource is shard2")
                "shard2"
            }
        }
        if (shardKey != null) {
            info("Current key is %d".format(shardKey % 2))
        }
        return dataSource
    }
}
