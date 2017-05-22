package com.woyou.statemachine;

import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.util.Log;

import com.woyou.common.BuildConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2017 UCWeb Inc. All Rights Reserved
 * Description  :  com.uc.framework.statemachine.State.java
 * <p>  状态机中运转的单元：状态
 * Creation     : 2017/4/28
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public abstract class State {
    @IntDef({INIT_STATE,NORMAL_STATE,FINISH_STATE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface StateType{}
    public static final int INIT_STATE = 0;
    public static final int NORMAL_STATE = 1;
    public static final int FINISH_STATE = 2;
    private static final String TAG = "StateMachine";
    private final int mStateType;

    public State(){
        this(NORMAL_STATE);
    }
    public State(int stateType){
        this.mStateType = stateType;
    }

    /**
     * 上一个状态收到了迁移消息，刚迁移到了本状态 **/
    @CallSuper
    public void onEnter(){
        if (BuildConstant.IS_DEVELOP_VERSION){
            Log.i(TAG, "Transfer to " + getName());
        }
    }

    /**
     * 响应该状态下收到的消息，决定下一步迁移的状态
     * @param data 随消息携带的数据
     * @return true：消息已处理，false：消息未处理
     */
    public boolean onHandleEvent(int event, Object data){
        return false;
    }

    /**
     * 处理该状态下没有在onHandleEvent中响应的消息
     */
    @CallSuper
    void onUnhandleEvent(int event, Object obj) {
    }
    /**
     * 状态名，主要用于日志记录，方便调试
     * @return
     */
    public abstract String getName();

    final @StateType int getStateType(){
        return mStateType;
    }

    @Override
    public String toString() {
        return getName();
    }
}
