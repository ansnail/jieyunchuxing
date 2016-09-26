package com.ish.jieyun.network.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import com.ish.jieyun.network.NetConfig;


public class AppUtils {

    /**
     * 获取程序版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(NetConfig.CONFIG_PACKAGE_NAME, 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取程序版本code
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(NetConfig.CONFIG_PACKAGE_NAME, 0);
            versionCode = packageInfo.versionCode;
            if (versionCode == 0) {
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 获取应用图标
     * @param context context
     * @return
     */
    public static Drawable getAppIcon(Context context) {
        PackageManager pm = context.getPackageManager();
        Drawable appIcon = null;
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(NetConfig.CONFIG_PACKAGE_NAME, 0);
            appIcon = applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appIcon;
    }

    /**
     * 判断网络类型
     *
     * @param context
     * @return 状态码
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return "WiFi";
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return "2G";
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return "3G";
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return "4G";
                        default:
                            return "UnKnown";
                    }
                default:
                    return "Not networked";
            }
        }
        return "";
    }


    /**
     * 手机当前语言环境
     *
     * @param context
     * @return
     */
    public static String getLanguage(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage();
    }

    /**
     * 获得设备ID
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {

        String deviceId = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    public static String getIntservicemer(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName();
    }


    /**
     * 取出IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 取出MSISDN，很可能为空
     *
     * @param context
     * @return
     */
    public static String getMSISDN(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 取出ICCID
     *
     * @param context
     * @return
     */
    public static String getICCID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 取出IMSI
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 取出MAC
     *
     * @param context
     * @return
     */
    public static String getMAC(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifi.getConnectionInfo().getMacAddress();
    }

    /**
     * 获取定位方式
     *
     * @param context
     * @return
     */
    public static String getLocationType(Context context) {
        String locationProvider;
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            locationProvider = "";
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        return locationProvider;
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    public static String getLatitudeAndlongitude(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        //获取Location
        Location location = locationManager.getLastKnownLocation(getNetworkType(context));
        return location.getLongitude() + "*" + location.getLatitude();
    }

    /**
     * 判断当前手机是否有ROOT权限
     *
     * @return
     */
    public static boolean isRoot() {
        boolean bool = false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {

        }
        return bool;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneType() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取设备品牌
     */
    public static String getPhoneFactory() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取设备品牌
     */
    public static String getOS() {
        return "ANDROID";
    }

    /**
     * 获取GMT时间
     *
     * @param context
     * @return
     */
    public static String getGMT(Context context) {
        System.currentTimeMillis();
        return System.currentTimeMillis() + "";
    }

    /**
     * 获得设备屏幕宽高
     */
    public static String getDisplayInfo(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels + "" + dm.heightPixels;
    }


    /**
     * 获取IP
     *
     * @return
     */
    public static String getIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("TAG:DeviceUtil ", "getIP Line 45 : " + "本机IP获取失败");
        }
        return "127.0.0.1";
    }


    /**
     * 获取CPU信息
     *
     * @return
     */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.contains("BogoMIPS")) {
                    String s = str2.split(":")[1].trim();
                    cpuInfo[1] = s;
                    break;
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return cpuInfo;
    }


    /**
     * 获得内存信息
     * @return
     */
    public static long[] getRomMemroy() {
        long[] romInfo = new long[2];
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long totalBlocks = stat.getBlockCount();
        // Available rom memory
        romInfo[1] = blockSize * availableBlocks;
        // Total rom memory
        romInfo[0] = totalBlocks * blockSize;
        return romInfo;
    }

    /**
     * 获得总得存储信息
     */
    public static void getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2 = "";
        FileReader fr;

        try {
            fr = new FileReader(str1);
            BufferedReader br = new BufferedReader(fr, 8192);
            int i = 2;
            while (i > 0 && (str2 = br.readLine()) != null) {
                if (str2.contains("MemTotal")) {
                    i--;
                }
                if (str2.contains("MemFree")) {
                    i--;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    /**
     * 获得SD卡信息
     * @return
     */
    public static long[] getSDCardInfo() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getAbsolutePath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;// 总大小
            sdCardInfo[1] = bSize * availBlocks;// 可用大小
        }
        return sdCardInfo;
    }

    /**
     * 获取程序名称
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }


    /**
     * 获取GUID
     * @param context
     * @return
     */
    public static String getGUID(Context context){
        String GUID = MD5Utils.MD5Encode(getMAC(context)+"|"+getIMEI(context)+"|"+getOS()+"|"+getPhoneFactory()+"|"+getPhoneType());
        return GUID;
    }


    /**
     * 获得UUID
     * @param context
     * @return
     */
    public static String getUUID(Context context) {
        try {
            final String tmDevice, tmSerial, androidId;
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            tmDevice = tm.getDeviceId();
            tmSerial = tm.getSimSerialNumber();
            androidId = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            return deviceUuid.toString();
        } catch (Exception e) {
            return UUID.randomUUID().toString();
        }
    }

}
