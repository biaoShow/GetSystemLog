package com.benxiang.getsystemlog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 开机自启广播
 * Created by benxiang on 2019/3/29.
 */

public class StartBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String log_genre = SharedPreferencesUtil.getIntent(context).getString(PublicStaticModel.LOG_GENRE_KEY);
        if (!"".equals(log_genre)) {
            GetLogService.setLogRank(log_genre);
            context.startService(new Intent(context, GetLogService.class));
        }
    }
}
