package com.mican.myapplication.net.configure;




import com.mican.myapplication.net.core.CoreApiException;
import com.mican.myapplication.net.core.Response;
import com.mican.myapplication.util.ToastUtils;

import io.reactivex.observers.DisposableObserver;


/**
 * @name lzq
 * @class name：com.mican.network.configure
 * @class describe
 * @time 2020/6/2 10:09 AM
 * @change
 * @chang
 * @class describe
 *
 * 刷新token用的
 */
public abstract class ResponseSubscriber<T> extends DisposableObserver<Response<T>> {



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(Response<T> tResponse) {
        if(tResponse.getResult()==1){
            onSuccess(tResponse.getData());
        }else {
            onError(new CoreApiException(tResponse.getResult(), tResponse.getMessage()));
        }
    }

    @Override
    public void onError(Throwable e) {
        unsubscribe();
        //统一处理错误异常
        CoreApiException coreApiException = FactoryException.analysisExcetpion(e);
        int code = coreApiException.getCode();
        if (1 == code) {
            onSuccess(null);
        }else {
            //返回错误信息
            ToastUtils.showNoEmptyShort(coreApiException.getDisplayMessage());
            onFailure(coreApiException);
        }

    }

    @Override
    public void onComplete() {
        unsubscribe();
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(CoreApiException coreApiException);


    public void unsubscribe() {
        if (isDisposed()) {
            dispose();
        }
    }



}
