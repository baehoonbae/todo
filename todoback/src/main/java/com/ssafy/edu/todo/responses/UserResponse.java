package com.ssafy.edu.todo.responses;

import com.ssafy.edu.todo.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserResponse {
    private int userSeq;
    private String userId;
    private String userName;    
    private String description;

    public UserResponse(User user) {
        this.userSeq = user.getUserSeq();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.description = user.getDescription();
    }
}
