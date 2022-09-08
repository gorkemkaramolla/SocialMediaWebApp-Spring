package com.example.javalearnbook.dto.requests;

import com.example.javalearnbook.model.RefreshToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    private Long writerId;
    private String refreshToken;
}
