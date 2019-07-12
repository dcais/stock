package org.dcais.stock.stock.common.result;

public enum ErrorCode {
    /************************************************
     * 登录注册相关错误码(业务级错误码)
     ************************************************/

    /************************************************
     * 接口安全验证失败(系统级错误码)
     ************************************************/
    ERROR_SECURITY_CODE("-1", "接口安全认证失败！");


    private String code;
    private String message;

    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
