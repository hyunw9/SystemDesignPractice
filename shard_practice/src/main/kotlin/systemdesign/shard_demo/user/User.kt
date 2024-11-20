package systemdesign.shard_demo.user

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val age: Long
) {

}
