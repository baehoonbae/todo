package com.ssafy.edu.todo.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private int id;
    @NonNull private String title;
    @NonNull private String color;
}
