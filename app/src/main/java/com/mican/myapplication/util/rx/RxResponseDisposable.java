package com.mican.myapplication.util.rx;


import io.reactivex.observers.DisposableObserver;

/**
 * @name lzq
 * @class name：com.mican.baselibrary.util.rx
 * @class describe
 * @time 2020/7/27 3:47 PM
 * @change
 * @chang
 * @class describe
 */
public abstract class RxResponseDisposable<T> extends  DisposableObserver<T> {

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    public void onNext(T t) {
        try {
            rxSuccess(t);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

  public   abstract void rxSuccess(T t) ;

    @Override
    public void onError(Throwable e) {
           e.printStackTrace();

    }

    @Override
    public void onComplete() {

    }


}
