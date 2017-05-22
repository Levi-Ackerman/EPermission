package com.woyou.util;

/**
 * ************************************************************
 * Description  :  com.woyou.util.AssetUtils.java
 * <p>
 * Creation     : 2017/5/17
 * Author       : lizhengxian1991@126.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public class AssetUtils {
    public static final void mustOk(boolean result){
        mustOk(result,null);
    }
    public static final void mustOk(boolean result,String message){
        if (!result){
            throw new Error(message);
        }
    }
    public static final void fail(String message){
        throw new Error(message);
    }
}
