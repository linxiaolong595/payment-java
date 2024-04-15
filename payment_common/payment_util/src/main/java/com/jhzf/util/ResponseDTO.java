package com.jhzf.util;

public class ResponseDTO {
    private int code;
    private String msg;
    private Object data;

    public ResponseDTO() {
    }

    public ResponseDTO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseDTO success() {
        return new ResponseDTO(1,"success",null);
    }
    public static ResponseDTO success(String msg) {
        return new ResponseDTO(1,msg,null);
    }

    public static ResponseDTO success(String msg, Object data) {
        return new ResponseDTO(1,msg,data);
    }
    public static ResponseDTO success(int code, String msg, Object data) {
        return new ResponseDTO(code,msg,data);
    }

    public static ResponseDTO error() {
        return new ResponseDTO(0,"error",null);
    }
    public static ResponseDTO error(String msg) {
        return new ResponseDTO(0,msg,null);
    }
    public static ResponseDTO error(int code, String msg) {
        return new ResponseDTO(code,msg,null);
    }


    /**
     *
     * 成功：1，msg可以默认success也可以是自定义，data可以有可以没有
     * 失败：0，msg可以是默认的error也可以是自定义，data一般是null
     */

    @Override
    public String toString() {
        return "ResponseDto{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
