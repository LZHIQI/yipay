package com.mican.myapplication.util;

import android.graphics.Bitmap;

/**
 * @name lzq
 * @class name：com.mican.data_module.datacache
 * @class describe
 * @time 2020/5/6 5:58 PM
 * @change
 * @chang
 * @class describe
 */
public class PathConfig {
    public static final String APP_LOG_FILE = "logs";// 日志文件夹
    public static final String CACHE_IMAGE_FILES = ".caches/.images";// 图片缓存目录
    public static final String CACHE_JSON_FILES = ".caches/.files";// json文件缓存目录
    public static final String CACHE_TEMP_VOICES = ".caches/.voicescache"; //语音缓存
    public static final String CACHE_TEMP_VIDEOS = ".caches/.videoscache";
    public static final String CACHE_SAVE_PHOTOS = "photos";// 存储下载的图片
    public static final String CACHE_MSG_PHOTOS = ".msg_photos";// 聊天的图片目录
    public static final String CACHE_MSG_VOICES = ".msg_voices";// 聊天的语音目录
    public static final String CACHE_UPLOAD_PHOTOS = ".uplaod_photos";// 上传文件目录
    public static final String CACHE_CONFIG_FILES = ".config";// 配置文件目录
    public static final String CACHE_OK_HTTP = ".caches/.ok";//网络请求缓存
    public static final String FILE_EXT = ".mc";// 文件扩展名
    public static final Bitmap.CompressFormat IMAGE_FORAMT = Bitmap.CompressFormat.JPEG;// 图片格式
    public static final String DECRYPT_KEY = "@mican_sport";// 本地aes加密key
    public static String APPPAKENAME = "";// app包名
    public static String APP_FOLDER = "mc_coach";// 主目录
    public static boolean isDebug = true;//是否是调试模式
    public static int loginType = 0;
}
