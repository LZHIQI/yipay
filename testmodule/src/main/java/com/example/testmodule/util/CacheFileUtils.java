package com.example.testmodule.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * @Description:文件管理
 * @Copyright:Copyright (c) 2015

 * @Version: 1.0.0
 * @Others:
 */
@SuppressLint("SdCardPath")
public class CacheFileUtils {

    private static final String TAG = "CacheFileUtils";

    private static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    private static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    private static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    private static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值
    public static int MIN_SDCARD_CACHE_SPACE = 50;// MB 最小的sd卡空间
    public static int MAX_VOICE_CACHE_COUNT = 500;// 最多缓存文件的数量
    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;// MB
    public static int EVERY_TIME_CLEAN_VOICE_CACHE_COUNT = (MAX_VOICE_CACHE_COUNT * 2) / 10;// 每次清理20%
    private static final String KEY_SDCARD = "/sdcard";
    private final static byte[] readDeleteSynckey = new byte[0];
    private static int BYTE_TO_MIB = 1048576;

    private static Context context;

    /**
     * 获取app的缓存路径
     *
     * @return
     */
    public static String getCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 计算sdcard上的剩余空间 - 单位M
     *
     * @return
     */
    public static int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()); // 可用总的bytes;
        return (int) (sdFreeMB) / BYTE_TO_MIB;// MIB单位
    }

    /**
     * 获取SD路径
     */
    public static String getSDPath() {

        try {
            File sdDir = null;
            if (context == null) {
                return KEY_SDCARD;
            } else {  //外部存储空间下的应用私有目录，不需要申请权限
                sdDir = context.getExternalFilesDir("data");
                if (!sdDir.exists()) {
                    sdDir.mkdir();
                }
                return sdDir.getPath();
            }
        } catch (Exception e) {
            return KEY_SDCARD;
        }
    }

    public static void setContext(Context act) {
        context = act;
    }

    /**
     * 判断sdcard是否可用
     *
     * @return
     */
    public static boolean isSDCardAvaiable() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable();
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取语音文件夹地址
     *
     * @return
     */
    public static String getVoiceCacheRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.CACHE_TEMP_VOICES);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取视频文件夹地址
     *
     * @return 文件夹地址
     */
    public static String getVideoCacheRootPath() {

        String rootPath = String.format(Locale.getDefault(), "%s%s%s%s%s", getSDPath(), File.separator, PathConfig.APP_FOLDER, File.separator, PathConfig.CACHE_TEMP_VIDEOS);
        File destDir = new File(rootPath);
        if (!destDir.exists()) {
            boolean result = destDir.mkdirs();
            if (result) {
                Log.i(TAG, "getVideoCacheRootPath: " + destDir.getAbsolutePath());
            }
        }
        return rootPath;
    }

    /**
     * 获取cache图片文件夹地址
     *
     * @return
     */
    public static String getCacheImageRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.CACHE_IMAGE_FILES);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取cache文件夹地址
     *
     * @return
     */
    public static String getCacheStrRootPath() {

       try {
           String sdcardPath = getSDPath();
           StringBuffer fileSB = new StringBuffer();
           fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
           fileSB.append(File.separator).append(PathConfig.CACHE_JSON_FILES);
           // 文件夹问null时构造
           String rootPath = fileSB.toString();
           File destDir = new File(rootPath);
           if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
               destDir.mkdirs();
           }
           LogUtils.d("rootPath",rootPath);
           return rootPath;
       }catch (Exception e){
           LogUtils.d("error",e);
           return KEY_SDCARD;
       }

    }

    /**
     * 获取日志文件夹地址
     *
     * @return
     */
    public static String getLogRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.APP_LOG_FILE);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取消息文件文件夹地址
     *
     * @return
     */
    public static String getMsgPhotosRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.CACHE_MSG_PHOTOS);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取消息文件文件夹地址
     *
     * @return
     */
    public static String getMsgVoicesRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.CACHE_MSG_VOICES);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取上传文件文件夹地址
     *
     * @return
     */
    public static String getUpLoadPhotosRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.CACHE_UPLOAD_PHOTOS);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取照片文件夹地址
     *
     * @return
     */
    public static String getPhotosRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.CACHE_SAVE_PHOTOS);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取配置文件文件夹地址
     *
     * @return
     */
    public static String getConfigRootPath() {
        String sdcardPath = getSDPath();
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(sdcardPath).append(File.separator).append(PathConfig.APP_FOLDER);
        fileSB.append(File.separator).append(PathConfig.CACHE_CONFIG_FILES);
        // 文件夹问null时构造
        String rootPath = fileSB.toString();
        File destDir = new File(rootPath);
        if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
            destDir.mkdirs();
        }
        return rootPath;
    }


   public  static String getPath(String FOLDER, String CACHE){
       try {
           String sdcardPath = getSDPath();
           StringBuffer fileSB = new StringBuffer();
           fileSB.append(sdcardPath).append(File.separator).append(FOLDER);
           fileSB.append(File.separator).append(   CACHE );
           // 文件夹问null时构造
           String rootPath = fileSB.toString();
           File destDir = new File(rootPath);
           if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
               destDir.mkdirs();
           }
           return rootPath;
       }catch (Exception e){
           LogUtils.d("error",e);
           return KEY_SDCARD;
       }
    }


    /**
     * 获取net
     *
     * @return
     */
    public static String getNetCachePath() {
       return getPath(PathConfig.APP_FOLDER,PathConfig.CACHE_OK_HTTP);
    }

    /**
     * 获取voice文件路径
     *
     * @param fileName
     * @return
     */
    public static String getVoiceCachePath(String fileName) {
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(getVoiceCacheRootPath()).append(File.separator).append(fileName);
        return fileSB.toString();
    }

    /**
     * 获取图片缓存文件路径
     *
     * @param fileName
     * @return
     */
    public static String getCacheConfigPath(String fileName) {
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(getConfigRootPath()).append(File.separator).append(fileName);
        return fileSB.toString();
    }

    /**
     * 获取图片缓存文件路径
     *
     * @param fileName
     * @return
     */
    public static String getCacheImagePath(String fileName) {
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(getCacheImageRootPath()).append(File.separator).append("img").append(File.separator).append(fileName);
        return fileSB.toString();
    }

    /**
     * 获取文件路径
     *
     * @param fileName
     * @return
     */
    public static String getCacheStrPath(String fileName) {
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(getCacheStrRootPath()).append(File.separator).append(fileName);
        return fileSB.toString();
    }

    /**
     * 获取照片文件路径
     *
     * @return
     */
    public static String getMsgPhotosPath(String uid) {
        StringBuffer fileSB = new StringBuffer();
        String key = String.format("%s%s.jpg", uid, CreateKeyUtil.generateSequenceNo());
        fileSB.append(getMsgPhotosRootPath()).append(File.separator).append(key);
        return fileSB.toString();
    }

    /**
     * 获取照片文件路径
     *
     * @return
     */
    public static String getMsgVoicesPath(String uid) {
        StringBuilder fileSB = new StringBuilder();
        String key = String.format("%s%s.amr", uid, CreateKeyUtil.generateSequenceNo());
        fileSB.append(getMsgVoicesRootPath()).append(File.separator).append(key);
        return fileSB.toString();
    }

    /**
     * 获取照片文件路径
     *
     * @return
     */
    public static String getUpLoadPhotosPath(String uid) {
        StringBuffer fileSB = new StringBuffer();
        String key = String.format("%s%s.jpg", uid, CreateKeyUtil.generateSequenceNo());
        fileSB.append(getUpLoadPhotosRootPath()).append(File.separator).append(key);
        return fileSB.toString();
    }

    /**
     * 获取照片文件路径
     *
     * @param fileName
     * @return
     */
    public static String getPhotosPath(String fileName) {
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(getPhotosRootPath()).append(File.separator).append(fileName);
        return fileSB.toString();
    }

    /**
     * 获取日志文件路径
     *
     * @param fileName
     * @return
     */
    public static String getLogPath(String fileName) {
        StringBuffer fileSB = new StringBuffer();
        fileSB.append(getLogRootPath()).append(File.separator).append(fileName);
        return fileSB.toString();
    }

    /**
     * 从本地sd卡读文件
     *
     * @param fileLocalPath
     * @return
     */
    public static String readLocalCacheStr(String fileLocalPath) {

        String result = null;
        synchronized (readDeleteSynckey) {

            File file = new File(fileLocalPath);
            if (!file.exists()) {

                return null;
            }
            FileInputStream fin = null;
            ByteArrayOutputStream stream = null;
            try {
                fin = new FileInputStream(fileLocalPath);
                stream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fin.read(buffer)) != -1) {

                    stream.write(buffer, 0, length);
                }
                result = stream.toString("utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (stream != null) {

                    try {

                        stream.close();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
                if (fin != null) {

                    try {

                        fin.close();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 从本地sd卡读文件
     *
     * @return
     */
    public static void deleteLocalCacheImage(File localCacheFile) {
        synchronized (readDeleteSynckey) {
            localCacheFile.delete();
        }
    }

    /**
     * 转换文件大小
     **/
    public static float getFileSizeForMB(long fileS) {
        return ((float) fileS / BYTE_TO_MIB);
    }

    /**
     * 判断是否需要清理旧数据
     */
    public static boolean isNeedCleanCache() {
        boolean isNeedCleanCache = false;
        String cacheRootPath = CacheFileUtils.getCacheImageRootPath();
        File cacheFolder = new File(cacheRootPath);
        String[] files = cacheFolder.list();
        int fileCount = 0;
        if (files != null) {
            fileCount = files.length;
        }
        if (CacheFileUtils.freeSpaceOnSd() <= MIN_SDCARD_CACHE_SPACE || fileCount > MAX_VOICE_CACHE_COUNT) {
            isNeedCleanCache = true;
        }
        return isNeedCleanCache;
    }

    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        if (tempList != null && tempList.length > 0) {
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
                if (temp.isDirectory()) {
                    delAllFile(path + "/" + tempList[i]);
                }
            }
        }
    }

    /**
     * 检测保存图片是否存在
     *
     * @return
     */
    public static boolean checkImagesExist(String bitName) {
        File file = new File(CacheFileUtils.getPhotosPath(bitName));
        return file.exists();
    }

    public static boolean isExists(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 保存图片到sdcard
     *
     * @param bitName
     * @param mBitmap
     */
    public static boolean saveBitmapToSdcard(String bitName, Bitmap mBitmap) {
        if (mBitmap == null || StringUtils.isEmpty(bitName)) return false;
        synchronized (readDeleteSynckey) {
            File file =new File(getCacheImagePath(bitName));
            try {
                if (!file.exists()) {
                    if (!file.createNewFile()) {
                        return false;
                    }
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 保存图片到sdcard
     *
     * @param bitName
     * @param mBitmap
     */
    public static void saveBitmapPNGToSdcard(String bitName, Bitmap mBitmap) {
        synchronized (readDeleteSynckey) {
            File file = new File(bitName);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                // png不支持原生压缩
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    /**
     * 缓存字符串
     *
     * @param content - 缓存的内容
     */
    public static void saveErrorStr(String filePath, String content) {
        saveErrorStr(filePath, content, false);
    }

    public static void saveErrorStr(String filePath, String content, boolean append) {
        synchronized (readDeleteSynckey) {
            // 判断sdcard上的空间
            if (FREE_SD_SPACE_NEEDED_TO_CACHE > CacheFileUtils.freeSpaceOnSd()) {
                LogUtils.w("CacheFileUtils", "Low free space onsd, do not onSuccess");
                return;
            }
            /**
             * 保存到sdcard
             */
            if (CacheFileUtils.isSDCardAvaiable()) {
                File file = new File(filePath);
                try {
                    if (!file.exists()) {

                        if (!file.createNewFile()) {

                            LogUtils.w("create file exception.");
                        }
                    }
                    OutputStream outStream = new FileOutputStream(file, append);
                    outStream.write(content.getBytes("utf-8"));
                    outStream.flush();
                    outStream.close();
                    LogUtils.i("CacheFileUtils", "Image saved to sd");
                } catch (Exception e) {
                    LogUtils.w("CacheFileUtils", "IOException");
                }
            }
        }
    }

    public static void fileReName(String fileName) {
        File downLoadFile = new File(fileName);
        File newFile = new File(String.format("%s%s", fileName, PathConfig.FILE_EXT));
        downLoadFile.renameTo(newFile);
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    public static long getFileSizeForKB(String path) {
        long size = 0;
        try {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
                fis.close();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        return size / 1024;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            return "";
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 存储文件 Context.MODE_PRIVATE 覆盖 Context.MODE_APPEND 追加
     *
     * @param context
     * @param content
     */
    public static void writeDataToFile(Context context, String fileName, String content) {
        FileOutputStream fos = null;
        BufferedWriter writer = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 存储文件 Context.MODE_PRIVATE 覆盖 Context.MODE_APPEND 追加
     *
     * @param context
     * @param context
     */
    public static String radDataToFile(Context context, String fileName) {
        FileInputStream fis = null;
        BufferedReader reader = null;
        StringBuffer content = new StringBuffer();
        try {
            fis = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content.toString();
    }




}
