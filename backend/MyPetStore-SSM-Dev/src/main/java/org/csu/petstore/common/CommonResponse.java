package org.csu.petstore.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


// 通用的响应对象
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private int status;
    private String message;
    private T data;// 泛型

    private CommonResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    private CommonResponse(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    // 公开的方法调用
    public static <T> CommonResponse<T> createForSuccess(){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
    }

    public static <T> CommonResponse<T> createForSuccessMessage(String message){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(), message);
    }

    public static <T> CommonResponse<T> createForSuccess(T data){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }

    public static <T> CommonResponse<T> createForSuccess(String message, T data){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResponse<T> createForError(){
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> CommonResponse<T> createForError(String message){
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(), message);
    }

    public static <T> CommonResponse<T> createForError(int code, String message){
        return new CommonResponse<T>(code, message);
    }

    public static <T> CommonResponse<T> createForError(int code, String message,T data){
        return new CommonResponse<T>(code, message, data);
    }
}
