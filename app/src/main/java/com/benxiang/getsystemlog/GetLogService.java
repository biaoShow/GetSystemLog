package com.benxiang.getsystemlog;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GetLogService extends IntentService {

    private static String log_rank;
    private static boolean isStop = false;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GetLogService(String name) {
        super(name);
    }

    public GetLogService() {
        this("");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    private void clear() {
        ArrayList<String> clearLog = new ArrayList<String>();  //设置命令  logcat -c 清除日志
        clearLog.add("logcat -c");
        ShellUtils.execCommand(clearLog, true);
//            Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));  //清理日志....这里至关重要，不清理的话，任何操作都将产生新的日志，代码进入死循环，直到bufferreader满
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (!isStop) {
            try {
                ArrayList<String> cmdLine = new ArrayList<String>();   //设置命令   logcat -d 读取日志
                cmdLine.add(log_rank);
//                ShellUtils.CommandResult c =
                ShellUtils.execCommand(cmdLine, true);
//                Log.i("BBBBBB", c.errorMsg + c.successMsg + c.result);
                clear();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void setLogRank(String str) {
        log_rank = str;
    }

    public static void setIsStop(boolean stop) {
        isStop = stop;
    }
}
