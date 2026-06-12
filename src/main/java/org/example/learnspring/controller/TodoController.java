package org.example.learnspring.controller;

import jakarta.validation.Valid;
import org.example.learnspring.dto.TodoRequest;
import org.example.learnspring.model.Todo;
import org.example.learnspring.model.User;
import org.example.learnspring.repository.UserRepository;
import org.example.learnspring.service.TodoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/todo")
@CrossOrigin(origins = "*") //allows all the origins from the fronted frameworks
public class TodoController {

    private final TodoService todoService;
    private  final UserRepository userRepository;

    public TodoController(TodoService todoService, UserRepository userRepository) {
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    @GetMapping("/allTask")
    public  List<Todo> getMyTodos(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        List<Todo> myTodos = todoService.getMyTodos(user.getId());
        return myTodos;
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(todoService.getTodoById(id));
    }


    @PostMapping("/addTask")
    // using @Valid we can validate the data before reaching the service layers, we
    // create a DTO class regarding the data that to be validated and use that in this
    // controller and , that DTO class is taken as the requester, and we created a new
    // object from the DTO object and if the data is validated, it will create a new
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoRequest request){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        Todo todo=new Todo();
        todo.setName(request.getName());
        todo.setCompleted(request.isCompleted());
        todo.setUser(user);
        return ResponseEntity.ok(todoService.createTodo(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo){
        return ResponseEntity.ok(todoService.updateTodo(id,todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        ResponseEntity<String> deleted = ResponseEntity.ok("Deleted");
        return deleted;
    }

}
