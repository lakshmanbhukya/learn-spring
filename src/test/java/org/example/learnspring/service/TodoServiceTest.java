package org.example.learnspring.service;

import org.example.learnspring.model.Todo;
import org.example.learnspring.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class) // Initializes Mockito framework annotations
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository; // Mocked database gateway simulator

    @InjectMocks
    private TodoService todoService; // Automatically receives the mock object above via its constructor

    private Todo validTodo;

    @BeforeEach
    void setUp() {
        // Runs before every individual @Test method to ensure a clean starting point
        validTodo = new Todo();
        validTodo.setId(101L);
        validTodo.setName("Complete Spring Boot Tutorial");
        validTodo.setCompleted(false);
    }

    @Test
    void createTodo_ValidInput_ReturnsSavedTodo() {
        // Arrange: Tell the mock repository exactly what to return when called
        Mockito.when(todoRepository.save(any(Todo.class))).thenReturn(validTodo);

        // Act: Execute the method we are trying to test
        Todo result = todoService.createTodo(validTodo);

        // Assert: Verify the behavior and structural data outputs are accurate
        assertNotNull(result);
        assertEquals(101L, result.getId());
        assertEquals("Complete Spring Boot Tutorial", result.getName());

        // Verify: Ensure the repository's save method was physically executed exactly 1 time
        Mockito.verify(todoRepository, Mockito.times(1)).save(any(Todo.class));
    }

    @Test
    void createTodo_EmptyTitle_ThrowsIllegalArgumentException() {
        // Arrange: Prepare an intentionally invalid object layout
        Todo invalidTodo = new Todo();
        invalidTodo.setName(""); // Breaks the business logic rule!

        // Act & Assert: Verify that the method throws the expected exception type
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            todoService.createTodo(invalidTodo);
        });

        assertEquals("Name cannot be empty", exception.getMessage());

        // Verify: Ensure the repository save function was NEVER called since validation failed
        Mockito.verify(todoRepository, Mockito.never()).save(any(Todo.class));
    }

    @Test
    void getTodoById_EntityExists_ReturnsTodo() {
        // Arrange: Simulate database finding the record
        Mockito.when(todoRepository.findById(101L)).thenReturn(Optional.of(validTodo));

        // Act
        Todo result = todoService.getTodoById(101L);

        // Assert
        assertNotNull(result);
        assertEquals("Complete Spring Boot Tutorial", result.getName());
    }

    @Test
    void getTodoById_EntityMissing_ThrowsRuntimeException() {
        // Arrange: Simulate database returning an empty container (not found)
        Mockito.when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            todoService.getTodoById(999L);
        });
    }
}
