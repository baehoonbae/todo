package com.ssafy.edu.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Category {
    private Integer id;
    @NonNull private Integer userSeq;
    @NonNull private String color;
    @NonNull private String title;
    private Boolean isPublic;
}