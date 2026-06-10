package org.example.learnspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TodoRequest {

    @NotBlank(message = "title is required and cannot be empty")
    @Size(max=100, message="name cannot exceed 100 characters")
    private String name;
    private boolean completed;
}
