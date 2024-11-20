package systemdesign.shard_demo.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/user")
    fun createUser(
        @RequestBody request: UserDto
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                UserDto.fromEntity(userService.create(request))
            )
    }
}
