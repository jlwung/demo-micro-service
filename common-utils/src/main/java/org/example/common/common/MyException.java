package org.example.common.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MyException extends RuntimeException {
    private int code;
    private String message;
}
