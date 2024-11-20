package systemdesign.shard_demo.user

data class UserDto(
    val name: String,
    val age: Long
) {
    companion object {
        fun toEntity(user: UserDto): User {
            return User(
                name = user.name,
                age = user.age
            )
        }

        fun fromEntity(user: User): UserDto {
            return UserDto(
                name = user.name,
                age = user.age
            )
        }
    }
}
