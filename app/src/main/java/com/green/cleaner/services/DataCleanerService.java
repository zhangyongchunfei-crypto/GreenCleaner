package com.green.cleaner.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.green.cleaner.utils.FileUtils;
import com.green.cleaner.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class DataCleanerService extends Service {

    private static final String TAG = "DataCleanerService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("items")) {
            List<String> items = intent.getStringArrayListExtra("items");
            cleanData(items);
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    private void cleanData(List<String> items) {
        for (String item : items) {
            switch (item) {
                case "应用缓存":
                    clearAppCache();
                    break;
                case "应用数据":
                    clearAppData();
                    break;
                case "浏览器历史":
                    clearBrowserHistory();
                    break;
                case "浏览器Cookies":
                    clearBrowserCookies();
                    break;
                case "浏览器缓存":
                    clearBrowserCache();
                    break;
                case "搜索历史":
                    clearSearchHistory();
                    break;
                case "最近使用文件":
                    clearRecentFiles();
                    break;
                case "剪贴板历史":
                    clearClipboardHistory();
                    break;
                case "临时文件":
                    clearTempFiles();
                    break;
                case "通话记录":
                    clearCallLogs();
                    break;
                case "短信":
                    clearSMS();
                    break;
                case "照片元数据":
                    clearPhotoMetadata();
                    break;
                case "设备日志":
                    clearDeviceLogs();
                    break;
            }
        }
    }

    private void clearAppCache() {
        try {
            File cacheDir = getCacheDir();
            FileUtils.deleteDir(cacheDir);
            
            // 清除外部缓存
            File externalCacheDir = getExternalCacheDir();
            if (externalCacheDir != null) {
                FileUtils.deleteDir(externalCacheDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearAppData() {
        try {
            PackageManager pm = getPackageManager();
            List<ApplicationInfo> apps = pm.getInstalledApplications(0);
            
            for (ApplicationInfo app : apps) {
                if (!isSystemApp(app)) {
                    String appCacheDir = "/data/data/" + app.packageName;
                    FileUtils.deleteDir(new File(appCacheDir));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearBrowserHistory() {
        try {
            // Chrome 浏览器历史
            String chromeHistory = "/data/data/com.android.chrome/app_chrome/Default/History";
            FileUtils.deleteFile(new File(chromeHistory));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearBrowserCookies() {
        try {
            // Chrome Cookies
            String chromeCookies = "/data/data/com.android.chrome/app_chrome/Default/Cookies";
            FileUtils.deleteFile(new File(chromeCookies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearBrowserCache() {
        try {
            // Chrome 缓存
            String chromeCache = "/data/data/com.android.chrome/app_chrome/Default/Cache";
            FileUtils.deleteDir(new File(chromeCache));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSearchHistory() {
        try {
            // 清除 Google 搜索历史
            getContentResolver().delete(
                    android.provider.Browser.SEARCHES_URI,
                    null,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearRecentFiles() {
        try {
            File recentDir = new File("/data/data/com.android.systemui/recent");
            FileUtils.deleteDir(recentDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearClipboardHistory() {
        try {
            // 清除剪贴板
            android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                android.content.ClipData clip = android.content.ClipData.newPlainText("", "");
                clipboard.setPrimaryClip(clip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearTempFiles() {
        try {
            // 清除临时文件
            File tempDir = new File("/data/local/tmp");
            FileUtils.deleteDir(tempDir);
            
            File downloadDir = StorageUtils.getDownloadDirectory();
            if (downloadDir != null) {
                File[] files = downloadDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().endsWith(".tmp")) {
                            FileUtils.deleteFile(file);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearCallLogs() {
        try {
            // 需要 READ_CALL_LOG 权限
            getContentResolver().delete(
                    android.provider.CallLog.Calls.CONTENT_URI,
                    null,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSMS() {
        try {
            // 需要 READ_SMS 权限
            getContentResolver().delete(
                    android.provider.Telephony.Sms.CONTENT_URI,
                    null,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearPhotoMetadata() {
        try {
            // 清除图片元数据
            File picturesDir = StorageUtils.getPhotoDirectory();
            if (picturesDir != null) {
                File[] files = picturesDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().endsWith(".jpg") || 
                            file.getName().endsWith(".png") ||
                            file.getName().endsWith(".jpeg")) {
                            FileUtils.removeMetadata(file);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearDeviceLogs() {
        try {
            // 清除设备日志
            String logDir = "/data/anr";
            FileUtils.deleteDir(new File(logDir));
            
            String tombstoneDir = "/data/tombstones";
            FileUtils.deleteDir(new File(tombstoneDir));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isSystemApp(ApplicationInfo app) {
        return (app.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }
}
