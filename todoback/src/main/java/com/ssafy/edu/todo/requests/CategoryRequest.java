package com.ssafy.edu.todo.requests;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private int id;
    @NonNull private String title;
    @NonNull private String color;
    private boolean isPublic;
}
