package com.hypertars.neighborChat.enums;

import com.hypertars.neighborChat.utils.StringUtils;

public enum  NBCResultCodeEnum {

    /** ---------- web 相关操作码 ---------- */
    /** 操作成功 */
    SUCCESS("SUCCESS", "操作成功"),

    /** 操作失败 */
    ERROR("ERROR", "操作失败"),

    /** 系统错误 */
    SYSTEM_ERROR("SYETEM_ERROR", "系统错误"),

    /** 用户未登录 */
    USER_NOT_LOGIN_IN("USER_NOT_LOGIN_IN", "用户未登录"),

    /** invalid data */
    INVALID_DATA("INVALID_DATA", "invalid data"),

    ;

    /** 结果code */
    private String code;

    /** 结果描述 */
    private String description;

    /**
     * 构造方法
     * @param code code
     * @param description 描述
     */
    NBCResultCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code 获取对应枚举
     * @param code 枚举code
     * @return 有则返回对应枚举，无则返回null
     */
    public NBCResultCodeEnum getEnumByCode(String code) {
        for(NBCResultCodeEnum NBCResultCodeEnum: NBCResultCodeEnum.values()) {
            if(StringUtils.equal(code, NBCResultCodeEnum.getCode())) {
                return NBCResultCodeEnum;
            }
        }
        return null;
    }

    /**
     * Gets the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code
     *
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}