package org.example.expert.domain.todo.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import org.example.expert.domain.comment.entity.Comment
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.manager.entity.Manager
import org.example.expert.domain.user.entity.User


@NoArgsConstructor
@Table(name = "todos")
@Entity
class Todo(
) : Timestamped() {

    var title: String?=null
    var contents: String?=null
    var weather: String?=null

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "user_id",
        nullable = false
    )
    var user: User?=null


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

    constructor(title: String, contents: String, weather: String, user: User) : this() {
        this.title=title
        this.contents=contents
        this.weather=weather
        this.user=user
        managers.add(Manager(user,this))
    }

}