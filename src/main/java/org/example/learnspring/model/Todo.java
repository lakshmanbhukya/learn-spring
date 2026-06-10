package org.example.learnspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "todos")
@Data // generates getters,setters and constructors via lombok
public class Todo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false,nullable = false)
    private String name;

//    @column(unique=false, nullable=false)
    private boolean completed;

    // many tods belong to the user
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "user_Id", nullable = false) // foreign key column mapping the users table
    @JsonIgnore // prevents from the infinite recursion loops during jackson serializations
    private User user;
}
