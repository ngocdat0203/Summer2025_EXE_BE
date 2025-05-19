package com.example.lovenhavestopsystem.core.base;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private int status;
    private String message;
    private T data;

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
