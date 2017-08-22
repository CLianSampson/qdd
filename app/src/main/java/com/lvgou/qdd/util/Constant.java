package com.lvgou.qdd.util;

/**
 * Created by sampson on 2017/8/17.
 */

public interface Constant {

    public static int GO_HOME_ACTIVITY_FROM_VERIFY_ACTIVITY = 2000;  //从个人认证跳回首页
    public static int GO_HOME_ACTIVITY_AFTER_REFUSE_SIGN = 2001; //驳回合同后跳回首页


    public static int NO_VERIFY = 0; //未认证
    public static int UNDER_VERIFYING = 1; //审核中
    public static int HAVE_VERIFY = 2; //已认证
    public static int NOT_PASS_VERIFY = 3;// 审核不通过



    public static int NOT_AUTH = 1; //未授权
    public static int HAVE_AUTH = 2; //授权


}
