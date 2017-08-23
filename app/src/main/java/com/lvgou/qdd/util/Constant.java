package com.lvgou.qdd.util;

/**
 * Created by sampson on 2017/8/17.
 */

public interface Constant {

    public static int GO_HOME_ACTIVITY_FROM_VERIFY_ACTIVITY = 2000;  //从个人认证跳回首页
    public static int GO_HOME_ACTIVITY_AFTER_REFUSE_SIGN = 2001; //驳回合同后跳回首页
    public static int GO_HOME_ACTIVITY_AFTER_SIGN_SUCESS = 2003; //合同签署成功后跳回首页

    public static int GO_MESSAGE_ACTIVITY_AFTER_MESSAGE_READ = 2004; //阅读消息后跳回消息列表

    public static int NO_VERIFY = 0; //未认证
    public static int UNDER_VERIFYING = 1; //审核中
    public static int HAVE_VERIFY = 2; //已认证
    public static int NOT_PASS_VERIFY = 3;// 审核不通过



    public static int NOT_AUTH = 1; //未授权
    public static int HAVE_AUTH = 2; //授权


    public static int SIGN_BY_PERSON = 0; //个人签署
    public static int SIGN_BY_ENTERPRISE = 1; //企业签署

}
