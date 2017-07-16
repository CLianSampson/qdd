package com.lvgou.qdd.util;

import android.util.Log;

/**
 * Created by sampson on 2017/6/28 0028.
 */

public class Logger {
    private  Object logObject;

    public static Logger getInstance(Object logObject){
        Logger logger = new Logger();
        logger.logObject  = logObject;
        return  logger;
    }

    public void info(String string)
    {
        StackTraceElement ste = new Throwable().getStackTrace()[1];

        Log.i(logObject.getClass().getSimpleName() ,  " " + ste.getMethodName()  + "()" + " " + ste.getLineNumber() + " " +string ) ;
    }

    public void error(String string){
        StackTraceElement ste = new Throwable().getStackTrace()[1];

        Log.e(logObject.getClass().getName(),logObject.getClass() + " " + ste.getMethodName()  + "()" + " " + ste.getLineNumber() + " " +string ) ;
    }
}
