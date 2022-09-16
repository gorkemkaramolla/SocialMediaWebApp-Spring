package com.example.javalearnbook.model;

import com.example.javalearnbook.dto.requests.WriterRequest;
import lombok.Data;

@Data
public class UserNameRequest extends WriterRequest {
    private String userName;
}
