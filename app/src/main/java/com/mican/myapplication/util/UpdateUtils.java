package com.mican.myapplication.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @name lzq
 * @class name：com.easybenefit.commons.util
 * @class describe
 * @time 2019/5/9 3:26 PM
 * @change
 * @chang
 * @class describe
 */
public class UpdateUtils {


    public static final int DOWNLOAD_FAIL = 0;
    public static final int DOWNLOAD_PROGRESS = 1;
    public static final int DOWNLOAD_SUCCESS = 2;
    private static UpdateUtils downLoadManager;
    public boolean loading = false;
    public Activity context;
    public String android_url;
    public ProgressDialog pd; // 进度条对话框
    private OkHttpClient okHttpClient;
    private Handler mHandler;


    private UpdateUtils(Activity activity ) {
        WeakReference<Activity> weakReference= new WeakReference<>(activity);
        this.context =weakReference.get();
    }

    public static UpdateUtils getInstance(Activity activity) {
        if (downLoadManager == null) {
            downLoadManager = new UpdateUtils(activity);
        }
        return downLoadManager;
    }

    public static boolean isUpdate( int updateCode) {
        return isDownLoad(updateCode, AppUtils.getAppVersionCode() );
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    public static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = FileProvider.getUriForFile(context,
                context.getPackageName() + ".fileProvider",
                file);
        return fileUri;
    }

    public static File creatDownLoadPath() {
        File download = null;
        try {
            download = Utils.getApp().getExternalFilesDir("Download");
            if (!download.exists()) {
                download.mkdir();
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return download;
    }

    public static boolean isDownLoad(int update_code, int path_code) {
        try {
            return update_code>path_code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 开始下载
     * @param updateType
     * @param versionCode
     */
    public void startLoading(int updateType, int versionCode, String android_url) {
        LogUtils.e("  downloadApkPath       processName  " + "updateType" + updateType);
        this.android_url=android_url;
        switch (updateType) {
            case 0: //不用强制
            case 1: //强制
                Loading(versionCode);
                break;
        }
    }

    public void Loading( int versionCode) {
        if(context==null)return;
        if (!loading) {//检测是否已经在下载
            try {
                File appPath = getDownAppPath(Utils.getApp());
                if (appPath != null) {
                    String downloadApkPath = appPath.getAbsolutePath();
                    if (!checkIsExists(downloadApkPath, context, versionCode)) {  //是否存在已安装的包
                        //要检查本地是否有安装包，有则删除重新下
                        new Thread(() -> {
                            if (appPath.exists()) {
                                appPath.delete();
                            }
                        }).start();
                        downLoadApk(context,android_url);
                    }
                } else {
                    downLoadApk(context, android_url);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            LogUtils.e("另外一个正在下载");
        }
    }

    public void downLoadApk(Activity activity, final String downURL) {
        if(context==null)return;
        if (NetworkUtils.isConnected()) {
            loading = true;
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);// 必须一直下载完，不可取消
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMessage("正在下载安装包，请稍后");
            pd.setTitle("版本升级");
            pd.show();
            download(downURL, new OnDownloadListener() {
                @Override
                public void onDownloadSuccess(String path) {
                    pd.dismiss();
                    // 结束掉进度条对话框
                    installApk(activity);
                    loading = false;
                }

                @Override
                public void onDownloading(int progress) {
                    pd.setProgress(progress);
                }

                @Override
                public void onDownloadFailed() {
                    loading = false;
                    pd.dismiss();
                }
            });

        } else {
            ToastUtils.showShort( "网络错误");
        }
    }

    public void clear() {
        if (pd != null) {
            pd.dismiss();
        }
    }

    /**
     * 假如已经有了
     *
     * @param downloadApkPath true :表示存在
     * @return
     */
    private boolean checkIsExists(String downloadApkPath, Context activity, int versionCode) {
        if(context==null)return false;
        //先检查本地是否已经有需要升级版本的安装包，如有就不需要再下载
        File targetApkFile = new File(downloadApkPath);
        if (targetApkFile.exists()) {
            PackageManager pm = activity.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(downloadApkPath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                //比较已下载到本地的apk安装包，与服务器上apk安装包的版本号是否一致
                if (versionCode>(info.versionCode)) {
                    installApk(activity);
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void download(final String url, final OnDownloadListener listener) {
        mHandler = new ListenHandler(listener);
        File saveFile = creatDownLoadPath();
        if (okHttpClient == null) okHttpClient = new OkHttpClient();
        if (saveFile != null) {
            Request request = new Request.Builder().url(url).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_FAIL;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = new File(saveFile, context.getPackageName() + ".apk");
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            //下载中
                            Message message = Message.obtain();
                            message.what = DOWNLOAD_PROGRESS;
                            message.obj = progress;
                            mHandler.sendMessage(message);

                        }
                        fos.flush();
                        //下载完成
                        Message message = Message.obtain();
                        message.what = DOWNLOAD_SUCCESS;
                        message.obj = file.getAbsolutePath();
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        Message message = Message.obtain();
                        message.what = DOWNLOAD_FAIL;
                        mHandler.sendMessage(message);
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {

                        }
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {

                        }
                    }
                }
            });
        }
    }

    public  void installApk(Context context) {
        if(context==null)return;
        try {
            File downAppFile = getDownAppPath(context);
            if (downAppFile != null && downAppFile.exists()) {
                AppUtils.installApp(downAppFile);
            } else {
                Toast.makeText(context, "APP安装文件不存在或已损坏", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            LogUtils.e(e);
            Toast.makeText(context, "APP安装文件不存在或已损坏", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取app全路径
     *
     * @return
     */
    private File getDownAppPath(Context activity) {
        File appPath = null;
        try {
            File download = activity.getExternalFilesDir("Download");
            if (download == null || !download.exists()) {
                return null;
            }
            appPath = new File(download.getAbsolutePath() + File.separator + activity.getPackageName() + ".apk");
            if (!appPath.exists()) {
                return null;
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return appPath;
    }



    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * 下载进度
         *
         * @param progress
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

   private static class ListenHandler extends Handler {

       private WeakReference<OnDownloadListener> reference;

       public ListenHandler (OnDownloadListener onDownloadListener){
           reference = new WeakReference<>(onDownloadListener);
       }
       @Override
       public void handleMessage(@NonNull Message msg) {
           super.handleMessage(msg);
           OnDownloadListener onDownloadListener = reference.get();
           if(onDownloadListener==null)return;
           switch (msg.what) {
               case DOWNLOAD_PROGRESS:
                   onDownloadListener .onDownloading((Integer) msg.obj);
                   break;
               case DOWNLOAD_FAIL:
                   onDownloadListener.onDownloadFailed();
                   break;
               case DOWNLOAD_SUCCESS:
                   onDownloadListener.onDownloadSuccess((String) msg.obj);
                   break;
           }
       }
   }


}
