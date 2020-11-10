package com.example.testmodule.util.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.testmodule.R;
import com.example.testmodule.util.SizeUtils;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @name lzq
 * @class name：com.travel.qtravellibrary.utils
 * @class describe
 * @time 2019-12-12 11:24
 * @change
 * @chang
 * @class describe
 */
public class GlideUtils {

    private static int placeholder_icon = R.color.image_color;//todo 配置一下
    private static int fail_icon=R.color.fail_color;

    private static RequestOptions sharedOptions = new RequestOptions().placeholder(placeholder_icon).fitCenter();

    public static void showNetCropImg(String imgurl, ImageView imageView) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            if(imgurl.endsWith(".gif")){
                showNetImgGif(imgurl,imageView);
            }else {
                Glide.with(con).asBitmap().load(imgurl)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(placeholder_icon).error(fail_icon).into(imageView);
            }
        }
    }

    public static void showNetCropImg(String imgurl, ImageView imageView, int fail_icon) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            if(imgurl.endsWith(".gif")){
                showNetImgGif(imgurl,imageView);
            }else{
                Glide.with(con).asBitmap().load(imgurl)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(fail_icon).error(fail_icon).into(imageView);
            }

        }
    }



    public static void showNetInsideImg(String imgurl, ImageView imageView, int fail_icon) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            if(imgurl.endsWith(".gif")){
                showNetImgGif(imgurl,imageView);
            }else{
                Glide.with(con).asBitmap().load(imgurl)
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(fail_icon).error(fail_icon).into(imageView);
            }

        }
    }
    public static void showFitCenter(String imgurl, ImageView imageView, int fail_icon) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            if(imgurl.endsWith(".gif")){
                showNetImgGif(imgurl,imageView);
            }else {
                Glide.with(con).asBitmap().load(imgurl)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(fail_icon).error(fail_icon).into(imageView);
            }
        }
    }

    public static void showNetImgGifRadius(String imgurl, ImageView imageView, int plac, int radius) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(plac);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            Glide.with(con).asGif().load(imgurl).apply(
                    new RequestOptions()
                            .transform(new CenterCrop(), new RoundedCorners(radius)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(plac).error(plac).into(imageView);
        }
    }


    public static void showNetImgGifSingleRadius(String imgurl, ImageView imageView, int plac, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(plac);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            Glide.with(con).asGif().load(imgurl).apply(
                    new RequestOptions()
                            .transform(new CenterCrop(), new GranularRoundedCorners(topLeft,topRight,bottomRight,bottomLeft)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(plac).error(plac).into(imageView);
        }
    }

    public static void showNetImgGif(String imgurl, ImageView imageView) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            Glide.with(con).asGif().load(imgurl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholder_icon).error(fail_icon).into(imageView);
        }
    }


    /**
     * Desc:
     * @param imageView
     * @param imgurl
     */
    public static void showNetImgNoCache(String imgurl, ImageView imageView) {
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Context con = imageView.getContext();
            if (con == null) return;
            Glide.with(con).asBitmap()
                    .dontAnimate()
                    .load(imgurl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(placeholder_icon)
                    .error(fail_icon)
                    .centerCrop()
                    .into(imageView);
        }
    }






    public  static void   showNoView(Context context, String url, View view){
      Glide.with(context)
              .load(url)
              .diskCacheStrategy(DiskCacheStrategy.ALL)
              .into(new CustomTarget<Drawable>() {
                  @Override
                  public void onResourceReady(@NonNull Drawable resource,
                                              @Nullable Transition<? super Drawable> transition) {

                      // Do something with the Drawable here.
                  }

                  @Override
                  public void onLoadCleared(@Nullable Drawable placeholder) {
                      // Remove the Drawable provided in onResourceReady from any Views and ensure
                      // no references to it remain.
                  }
              });
   }




    // 8. bitmap ---Drawable
    public static Drawable BitmapToDrawable(Bitmap bitmap, Context context) {
        return new BitmapDrawable(context.getResources(),
                bitmap);
    }

    public static Bitmap getBitmap(Context context, String url) {
        Bitmap bitmap = null;
        FutureTarget<Bitmap> futureTarget =
                Glide.with(context)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(url)
                        .submit();
        try {
            bitmap = futureTarget.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Do something with the Bitmap and then when you're done with it:
        Glide.with(context).clear(futureTarget);
        return bitmap;
    }
    public static Bitmap getBitmap(Context context, String url, int width, int height){
        Bitmap bitmap = null;
        FutureTarget<Bitmap> futureTarget =
                Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .submit(width, height);
        try {
            bitmap= futureTarget.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

// Do something with the Bitmap and then when you're done with it:
        Glide.with(context).clear(futureTarget);
        return bitmap;
    }


    public static void showCircle(String imgurl, ImageView imageView, int fail_icon){
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Glide.with(imageView.getContext()).load(imgurl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(fail_icon)
                    .error(fail_icon)
                    .into(imageView);
        }
    }

    public static void showPlaceholderCircle(String imgurl, ImageView imageView, int fail_icon){
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Glide.with(imageView.getContext()).load(imgurl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(fail_icon)
                    .into(imageView);
        }
    }


    public static void showSkipCircle(String imgurl, ImageView imageView, int fail_icon){
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Glide.with(imageView.getContext()).load(imgurl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(fail_icon)
                    .error(fail_icon)
                    .into(imageView);
        }
    }


    public static void showCircle(String imgurl, ImageView imageView){
        if (imageView == null) return;
        if (imgurl == null || "".equals(imgurl)) {
            imageView.setImageResource(fail_icon);
        } else {
            Glide.with(imageView.getContext()).load(imgurl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }







    /**
     * Desc: 显示圆角
     *
     * @param imageView
     * @param imgUrl
     */
    public static void DisplayRoundCorners(final ImageView imageView, final Object imgUrl, int radius, int plac) {
        Context con = imageView.getContext();
        try {
            if (imgUrl == null || "".equals(imgUrl)) {
                imageView.setImageResource(plac);
            } else {
                Glide.with(con)
                        .load(imgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(plac)
                        .dontAnimate()
                        .centerCrop()
                        .error(plac)
                        .transform(new GlideRoundTransformCenterCrop(radius))
                        .into(imageView);

            }
        } catch (Exception e) {
            Glide.with(con).asBitmap().load(imgUrl).into(imageView);
        }
    }

    /**
     * Desc: 显示圆角
     *
     * @param imageView
     * @param imgUrl
     */
    public static void DisplayRoundCorners(final ImageView imageView, final Bitmap imgUrl, int radius, int plac) {
        Context con = imageView.getContext();
        try {
            if (imgUrl == null || "".equals(imgUrl)) {
                imageView.setImageResource(plac);
            } else {
                Glide.with(con)
                        .asBitmap()
                        .load(imgUrl)
                        .skipMemoryCache(false)
                        .placeholder(plac)
                        .dontAnimate()
                        .centerCrop()
                        .error(plac)
                        .transform(new GlideRoundTransformCenterCrop(radius))
                        .into(imageView);

            }
        } catch (Exception e) {
            Glide.with(con).asBitmap().load(imgUrl).into(imageView);
        }
    }


    /**
     * Desc: 显示圆角
     *
     * @param imageView
     * @param imgUrl
     */
    public static void DisplayRoundCorners(final ImageView imageView, final int imgUrl, int radius) {
        Context con = imageView.getContext();
        try {
             {
                Glide.with(con)
                        .load(imgUrl)
                        .dontAnimate()
                        .centerCrop()
                        .transform(new GlideRoundTransformCenterCrop(radius))
                        .into(imageView);
            }
        } catch (Exception e) {
            Glide.with(con).load(imgUrl).into(imageView);
        }
    }
    /**
     * Desc: 显示圆角
     *
     * @param imageView
     * @param imgUrl
     */
    public static void DisplayRoundCorners(final ImageView imageView, final String imgUrl, int radius, int plac) {
        Context con = imageView.getContext();
        try {
            if (imgUrl == null || "".equals(imgUrl)) {
                imageView.setImageResource(plac);
            } else {
                Glide.with(con)
                        .asBitmap()
                        .load(imgUrl)
                        .skipMemoryCache(false)
                        .placeholder(plac)
                        .dontAnimate()
                        .centerCrop()
                        .error(plac)
                        .transform(new GlideRoundTransformCenterCrop(radius))
                        .into(imageView);

            }
        } catch (Exception e) {
            Glide.with(con).asBitmap().load(imgUrl).into(imageView);
        }
    }

    public  static void   loadImageView(Context context, String url, ImageView view, float w, int fail_icon){
        if(view==null)return;
        int i = SizeUtils.dp2px(w);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(i)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                       view.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        view.setImageDrawable(ContextCompat.getDrawable(context,fail_icon));
                    }
                });
    }


    public static void setSrc(final ImageView imageView
            ,int resId) {
        if(imageView!=null){
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(),resId));
        }
    }
}
