package systemdesign.shard_demo.user

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService(

    private val userAdapter: UserAdapter
) {

    fun create(
        request: UserDto
    ): User {
        val userEntity: User = UserDto.toEntity(request)
        return userAdapter.create(userEntity)
    }
}
