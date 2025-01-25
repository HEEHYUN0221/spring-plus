package org.example.expert.domain.todo.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import org.example.expert.domain.comment.entity.Comment
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.manager.entity.Manager
import org.example.expert.domain.user.entity.User

@Getter
@NoArgsConstructor
@Table(name = "todos")
@Entity
class Todo(
    //생성자+ init() =  java파일 37번째줄
    var title: String,
    var contents: String,
    var weather: String,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "user_id",
        nullable = false
    ) val user: User,

) : Timestamped() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(
        mappedBy = "todo",
        cascade = [CascadeType.REMOVE]
    ) val comments: MutableList<Comment> = mutableListOf()

    @OneToMany(
        mappedBy = "todo",
        cascade = [CascadeType.PERSIST],
        orphanRemoval = true
    ) val managers: MutableList<Manager> = mutableListOf()

    init {
        managers.add(Manager(user, this))
    }

}