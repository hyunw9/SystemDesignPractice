package systemdesign.shard_demo

import jakarta.annotation.security.RunAs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import systemdesign.shard_demo.external.DataSourceRouter
import systemdesign.shard_demo.external.ThreadLocalDatabaseContextHolder
import systemdesign.shard_demo.user.User
import systemdesign.shard_demo.user.UserDto
import systemdesign.shard_demo.user.UserService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SpringBootTest
class InsertionTest {

    @Autowired
    lateinit var userService: UserService

    @Test
    @Rollback(false)
    fun testInsertion() {
        val users = (1..1000).map { id -> UserDto(name = "user$id", age = id.toLong()) }
        users.forEach { user ->
            ThreadLocalDatabaseContextHolder.setShardKey(user.age)
            val expectedDatasource = if (user.age % 2 == 0L) "shard1" else "shard2"
            userService.create(user)
            ThreadLocalDatabaseContextHolder.clear()
        }

    }

    @Test
    @Rollback(false)
    fun `멀티스레드 환경에서 삽입 테스트`() {
        val users = (1..1000).map { id -> UserDto(name = "user$id", age = id.toLong()) }

        val executor = Executors.newFixedThreadPool(15)

        val tasks = users.map { user ->
            Runnable {
                try {
                    ThreadLocalDatabaseContextHolder.setShardKey(user.age)
                    println("Inserting user: ${user.name} in thread ${Thread.currentThread().id}")
                    userService.create(user)
                } catch (e: Exception) {
                    println("Error inserting user: ${user.name}, ${e.message}")
                } finally {
                    ThreadLocalDatabaseContextHolder.clear()
                }
            }
        }

        tasks.forEach { executor.submit(it) }
        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.MINUTES)
    }
}
