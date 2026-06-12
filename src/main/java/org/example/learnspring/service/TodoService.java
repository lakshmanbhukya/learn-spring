package org.example.learnspring.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learnspring.model.Todo;
import org.example.learnspring.repository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getMyTodos(Long id) {
        return todoRepository.findByUserId(id);
    }

    public Todo createTodo(Todo todo){
        log.info("Attempting to create a new todo with title : {} ",todo.getName());

        if(todo.getName() == null || todo.getName().isEmpty()) {
            log.warn("Todo Creation blocked ; title parameter  is blank or empty");
            throw new IllegalArgumentException("Name cannot be empty");
        }
//        return todoRepository.save(todo);
        Todo savedTodo = todoRepository.save(todo);
        log.info("Todo Successfully committed to neon db with generated Id :", savedTodo.getId());
        return savedTodo;
    }

    public Todo updateTodo(Long id, Todo updatedTodo){
        Todo todo=todoRepository.findById(id).orElseThrow(()->
                new RuntimeException("Todo Item not found with id :"+id));
        todo.setName(updatedTodo.getName());
        todo.setCompleted(updatedTodo.isCompleted());
        return todoRepository.save(todo);
    }
    public Todo getTodoById(Long id) {
        log.debug("Fetching todo data details for ID {} :",id);

        return todoRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Resource not found with id {}",id);
                    return new RuntimeException("Resource not found with id "+id);
                });
    }


    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }
}
