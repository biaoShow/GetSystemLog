package com.benxiang.getsystemlog;

/**
 * Created by benxiang on 2019/3/30.
 */

public class PublicStaticModel {
    public static final String LOG_GENRE_KEY = "log_genre";
    public static final String GETLOG_V = "logcat *:v -d -v time >> /mnt/sdcard/logcat_v.txt";
    public static final String GETLOG_D = "logcat *:d -d -v time >> /mnt/sdcard/logcat_d.txt";
    public static final String GETLOG_I = "logcat *:i -d -v time >> /mnt/sdcard/logcat_i.txt";
    public static final String GETLOG_W = "logcat *:w -d -v time >> /mnt/sdcard/logcat_w.txt";
    public static final String GETLOG_E = "logcat *:e -d -v time >> /mnt/sdcard/logcat_e.txt";
}
