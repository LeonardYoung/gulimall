package com.yangsj.common.constant;

/**
 * @author yangsj
 */
public class ProductConstant {
    /**
     * 产品属性枚举
     */
    public enum AttrEnum{
        /**
         * 产品属性类型枚举
         */
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");

        private int code;
        private String msg;
        AttrEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg(){
            return msg;
        }
    }
}
