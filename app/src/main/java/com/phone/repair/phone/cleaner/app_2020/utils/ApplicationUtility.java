package com.phone.repair.phone.cleaner.app_2020.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.phone.repair.phone.cleaner.app_2020.annotations.StringsAnnotations;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ApplicationUtility {

    private static final String SYSTEM_PACKAGE_NAME = "android";
    private Context context;
    PackageManager mPackageManager;



    public ApplicationUtility(Context context) {
        this.context = context;
        mPackageManager = (PackageManager) context.getPackageManager();
    }

    public List<String> getSysActiveApps(boolean ram) {

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<String> list = new ArrayList<>();
        if (ram) {
            for (ApplicationInfo packageInfo : packages) {
                if (!isStopped(packageInfo))
                {
                    if (!list.contains(packageInfo.packageName)) {
                        list.add(packageInfo.packageName);
                    }
                }
            }
        } else {
            for (ApplicationInfo packageInfo : packages) {
                if (isStopped(packageInfo) && isSys(packageInfo)) {
                    if (!list.contains(packageInfo.packageName)) {
                        list.add(packageInfo.packageName);
                    }
                }
            }
        }

        return list;
    }


    private boolean isStopped(ApplicationInfo appInformation) {

        return ((appInformation.flags & ApplicationInfo.FLAG_STOPPED) == 0);
    }

    private boolean isSys(ApplicationInfo appInformation) {

        return ((appInformation.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public ArrayList<String> getSysOrInstalledAppsList(boolean systemApp, boolean isAll) {

        ArrayList<String> AppPackageName = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList) {

            ActivityInfo activityInfo = resolveInfo.activityInfo;

            if (isAll) {
                if (!isSysPackage(resolveInfo) || isSysPackage(resolveInfo)) {
                    if (!AppPackageName.contains(activityInfo.applicationInfo.packageName)) {
                        AppPackageName.add(activityInfo.applicationInfo.packageName);
                    }
                }

            } else {
                if (systemApp) {
                    if (isSysPackage(resolveInfo)) {
                        if (!AppPackageName.contains(activityInfo.applicationInfo.packageName)) {
                            AppPackageName.add(activityInfo.applicationInfo.packageName);
                        }
                    }
                } else {
                    if (!isSysPackage(resolveInfo)) {
                        if (!AppPackageName.contains(activityInfo.applicationInfo.packageName)) {
                            AppPackageName.add(activityInfo.applicationInfo.packageName);
                        }
                    }
                }
            }
        }
        return AppPackageName;
    }

    public List<String> getAllActiveApps() {

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<String> list = new ArrayList<>();

        for (ApplicationInfo packageInfo : packages) {

            if (isSTOPPED(packageInfo)) {
                if (!list.contains(packageInfo.packageName)) {
                    list.add(packageInfo.packageName);
                }
            }
        }
        return list;
    }

    private boolean isSTOPPED(ApplicationInfo pkgInfo) {

        return ((pkgInfo.flags & ApplicationInfo.FLAG_STOPPED) == 0);
    }

    public ArrayList<String> getAllAppsList() {

        ArrayList<String> AppPackageName = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList) {

            ActivityInfo activityInfo = resolveInfo.activityInfo;

            if (!AppPackageName.contains(activityInfo.applicationInfo.packageName)) {
                AppPackageName.add(activityInfo.applicationInfo.packageName);
            }
        }
        return AppPackageName;
    }


    public boolean isSysPackage(ResolveInfo resolveInfo) {

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public long getAppInstallTime(String apkPackageName, boolean update) {
        long timeStamp = 0;
        PackageManager pm = context.getPackageManager();

        try {
            PackageInfo info = pm.getPackageInfo(apkPackageName, 0);
            if (update) {
                timeStamp = info.lastUpdateTime;
            } else {
                timeStamp = info.firstInstallTime;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return timeStamp;
    }


    // return any time on condition..
    public <T> T getAppInformation(String apkPackageName, String whatThing) {

        T thingType = null;

        ApplicationInfo applicationInfo;

        PackageManager packageManager = context.getPackageManager();

        try {
            applicationInfo = packageManager.getApplicationInfo(apkPackageName, 0);

            if (applicationInfo != null) {

                if (whatThing.matches(StringsAnnotations.APP_ICON)) {
                    thingType = (T) packageManager.getApplicationIcon(applicationInfo);
                } else if (whatThing.matches(StringsAnnotations.APP_NAME)) {
                    thingType = (T) packageManager.getApplicationLabel(applicationInfo);
                } else if (whatThing.matches(StringsAnnotations.APP_VERSION)) {
                    PackageManager pm = context.getPackageManager();
                    PackageInfo packageInfo = pm.getPackageInfo(apkPackageName, 0);
                    thingType = (T) packageInfo.versionName;
                }
            }

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return thingType;
    }


    public long getAppSize(String packageName)
            throws PackageManager.NameNotFoundException {
        return new File(context.getPackageManager().getApplicationInfo(packageName, 0).publicSourceDir).length();
    }

    public String getAppName(String ApkPackageName) {

        String Name = "";
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);
            if (applicationInfo != null) {
                Name = (String) packageManager.getApplicationLabel(applicationInfo);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Name;
    }


    public boolean isSystemApp(String packageName) {
        try {
            // Get packageinfo for target application
            PackageInfo targetPkgInfo = mPackageManager.getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            // Get packageinfo for system package
            PackageInfo sys = mPackageManager.getPackageInfo(
                    SYSTEM_PACKAGE_NAME, PackageManager.GET_SIGNATURES);
            // Match both packageinfo for there signatures
            return (targetPkgInfo != null && targetPkgInfo.signatures != null && sys.signatures[0]
                    .equals(targetPkgInfo.signatures[0]));
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean isAppLoaded(String packageName) {
        if (packageName == null) {
            throw new IllegalArgumentException("Package name can not be null");
        }
        try {
            ApplicationInfo ai = mPackageManager.getApplicationInfo(
                    packageName, 0);
            // First check if it is preloaded.
            // If yes then check if it is System app or not.
            if (ai != null
                    && (ai.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
                // Check if signature matches
                if (isSystemApp(packageName) == true) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public long getAppTime(String apkPackageName,String whatKind)
    {
        long timeStamp = 0 ;
        PackageManager pm = context.getPackageManager();

        try {
            if (StringsAnnotations.INSTALLATION.matches(whatKind))
            {
                PackageInfo info = pm.getPackageInfo(apkPackageName, 0);
                Field field = PackageInfo.class.getField("firstInstallTime");
                timeStamp = field.getLong(info);
            }else {
                PackageInfo info = pm.getPackageInfo(apkPackageName, 0);
                Field field = PackageInfo.class.getField("lastUpdateTime");
                timeStamp = field.getLong(info);
            }

        } catch (PackageManager.NameNotFoundException | IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return timeStamp;
    }


}
