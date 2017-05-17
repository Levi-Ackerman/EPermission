package com.woyou.statemachine;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2017 UCWeb Inc. All Rights Reserved
 * Description  :  com.uc.framework.statemachine.IMachineEventListener.java
 * <p>  状态机时间回调接口
 * Creation     : 2017/5/5
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public interface IMachineEventListener {
    public void onMachineEvent(StateMachine machine, MachineEvent event);
}
