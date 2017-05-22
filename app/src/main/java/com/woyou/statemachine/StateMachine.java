package com.woyou.statemachine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.woyou.common.BuildConstant;
import com.woyou.util.AssetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2017 UCWeb Inc. All Rights Reserved
 * Description  :  com.uc.framework.statemachine.StateMachine.java
 * <p>
 * Creation     : 2017/4/26
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public class StateMachine{
    private static int sBaseID = 2795;
    /**
     * 每个状态机有唯一标识的id，同时id也用来作为向系统提交权限申请时候的requestID
     */
    private int mId = -1;

    private boolean mIsWorking = false;
    private Handler mWorkHandler;

    private List<State> mStates = new ArrayList<State>();
    private State mInitState;
    private State mCurrentState;
    private IMachineEventListener mMachineEventListener;

    public StateMachine() {
        Looper looper = Looper.getMainLooper();
        mWorkHandler = new MainHandler(looper);
    }

    protected void addState(State state){
        if (state.getStateType() == State.INIT_STATE){
            if (BuildConstant.IS_DEVELOP_VERSION) {
                //init state只能有一个，所以add时，状态集合里面一定没有initState
                for (State s : mStates) {
                    AssetUtils.mustOk(s.getStateType() != State.INIT_STATE);
                }
            }
            mInitState = state;
        }

        mStates.add(state);
    }

    public void setMachineEventListener(IMachineEventListener machineEventListener){
        mMachineEventListener = machineEventListener;
    }

    public int getId(){
        if (mId == -1){
            mId = ++ sBaseID;
        }
        return mId;
    }

    protected final void transferTo(@NonNull final State toState) {
        AssetUtils.mustOk(mStates.contains(toState));
        mCurrentState = toState;
        mCurrentState.onEnter();
        if (mCurrentState.getStateType() == State.FINISH_STATE){
            mIsWorking = false;
            if (mMachineEventListener != null){
                mMachineEventListener.onMachineEvent(this,MachineEvent.FINISH);
            }
        }
    }

    public void start() {
        if (mInitState == null){
            AssetUtils.fail("You must add an init state in the machine first !");
            return ;
        }
        mCurrentState = mInitState;
        mIsWorking = true;
        if (mMachineEventListener != null) {
            mMachineEventListener.onMachineEvent(this,MachineEvent.START);
        }
        transferTo(mInitState);
    }

    public void postEvent(int event){
        postEvent(event, null);
    }

    public void postEvent(int event,long delay){
        postEvent(event,null,delay);
    }

    public void postEvent(int event, Object data){
        postEvent(event,data,0);
    }

    public void postEvent(int event, Object data, long delay) {
        mWorkHandler.sendMessageDelayed(Message.obtain(mWorkHandler,event,data),delay);
    }

    public boolean isWorking(){
        return mIsWorking;
    }

    private class MainHandler extends Handler {
        MainHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            if (!mCurrentState.onHandleEvent(msg.what,msg.obj)) {
                mCurrentState.onUnhandleEvent(msg.what,msg.obj);
            }
        }
    }
}
