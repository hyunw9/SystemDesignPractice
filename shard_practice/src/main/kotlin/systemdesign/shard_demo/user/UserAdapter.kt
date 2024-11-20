package systemdesign.shard_demo.user

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import systemdesign.shard_demo.external.ThreadLocalDatabaseContextHolder

@Component
@RequiredArgsConstructor
class UserAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun create(user: User): User {
        try{
            val age = user.age
            ThreadLocalDatabaseContextHolder.setShardKey(age)
            println("Shard key is ${ThreadLocalDatabaseContextHolder.getShardKey()}")
            return userJpaRepository.save(user)
        }finally {
            ThreadLocalDatabaseContextHolder.clear()
        }
    }
}
