package com.woyou.statemachine;

/**
 * ************************************************************
 * Description  :  com.uc.framework.statemachine.IMachineEventListener.java
 * <p>  状态机时间回调接口
 * Creation     : 2017/5/5
 * Author       : lizhengxian1991@126.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public interface IMachineEventListener {
    public void onMachineEvent(StateMachine machine, MachineEvent event);
}
