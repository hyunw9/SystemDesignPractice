package systemdesign.shard_demo.user

interface UserRepository {
    fun create(user: User): User
}
