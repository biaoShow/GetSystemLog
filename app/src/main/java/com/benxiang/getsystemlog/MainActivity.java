package com.benxiang.getsystemlog;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_get_verbose)
    Button btnGetVerbose;
    @BindView(R.id.btn_get_debug)
    Button btnGetDebug;
    @BindView(R.id.btn_get_info)
    Button btnGetInfo;
    @BindView(R.id.btn_get_warn)
    Button btnGetWarn;
    @BindView(R.id.btn_get_error)
    Button btnGetError;
    @BindView(R.id.btn_clear)
    Button btnClear;
    private boolean isStop = false;
    private SharedPreferencesUtil preferencesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //获取root权限
        String apkRoot = "chmod 777 " + getPackageCodePath();
        boolean is = SystemManager.RootCommand(apkRoot);
        Log.i("root", String.valueOf(is));

        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        preferencesUtil = SharedPreferencesUtil.getIntent(this);
    }

    @OnClick({R.id.btn_get_verbose, R.id.btn_get_debug, R.id.btn_get_info, R.id.btn_get_warn, R.id.btn_get_error, R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verbose:
                preferencesUtil.putString(PublicStaticModel.LOG_GENRE_KEY, PublicStaticModel.GETLOG_V);
                startGetLog(PublicStaticModel.GETLOG_V);
                break;
            case R.id.btn_get_debug:
                preferencesUtil.putString(PublicStaticModel.LOG_GENRE_KEY, PublicStaticModel.GETLOG_D);
                startGetLog(PublicStaticModel.GETLOG_D);
                break;
            case R.id.btn_get_info:
                preferencesUtil.putString(PublicStaticModel.LOG_GENRE_KEY, PublicStaticModel.GETLOG_I);
                startGetLog(PublicStaticModel.GETLOG_I);
                break;
            case R.id.btn_get_warn:
                preferencesUtil.putString(PublicStaticModel.LOG_GENRE_KEY, PublicStaticModel.GETLOG_W);
                startGetLog(PublicStaticModel.GETLOG_W);
                break;
            case R.id.btn_get_error:
                preferencesUtil.putString(PublicStaticModel.LOG_GENRE_KEY, PublicStaticModel.GETLOG_E);
                startGetLog(PublicStaticModel.GETLOG_E);
                break;
            case R.id.btn_clear:
                preferencesUtil.putString(PublicStaticModel.LOG_GENRE_KEY, "");
                stopGetLog();
                break;
        }
    }

    private void startGetLog(String str) {
        if (isServiceRunning(this, "com.benxiang.getsystemlog.GetLogService")) {
            stopGetLog();
        }
        GetLogService.setLogRank(str);
        startService(new Intent(this, GetLogService.class));
    }

    private void stopGetLog() {
        GetLogService.setIsStop(true);
        stopService(new Intent(this, GetLogService.class));
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
