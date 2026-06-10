package org.example.learnspring.service;

import org.example.learnspring.model.Todo;
import org.example.learnspring.repository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getMyTodos(Long id) {
        return todoRepository.findByUserId(id);
    }

    public Todo createTodo(Todo todo){
        if(todo.getName() == null || todo.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updatedTodo){
        Todo todo=todoRepository.findById(id).orElseThrow(()->
                new RuntimeException("Todo Item not found with id :"+id));
        todo.setName(updatedTodo.getName());
        todo.setCompleted(updatedTodo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }
}
