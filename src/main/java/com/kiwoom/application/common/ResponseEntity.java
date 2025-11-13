package com.kiwoom.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseEntity<T> {
    private T data;
    private String message;
    private Boolean success;

    public static <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(data,"success",true);
    }

    public static <T> ResponseEntity<T> error(T data) {
        return new ResponseEntity<>(null,"fail",false);
    }
}
