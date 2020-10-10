package com.mican.myapplication.net.configure;




import com.mican.myapplication.net.core.CoreApiException;
import com.mican.myapplication.net.core.CoreHeader;
import com.mican.myapplication.net.core.CoreResponse;
import com.mican.myapplication.net.core.Optional;
import com.mican.myapplication.net.core.Response;
import com.mican.myapplication.net.interceptor.TokenInterceptor;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 *
 */
public class RxHelper {

    private Throwable mRefreshTokenError = null;
    /**
     * 统一线程处理（常规式）ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 统一线程处理（常规式）ObservableTransformer
     */
    public static <T> ObservableTransformer<Response<T>, T> rxSchedulerObservable() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(tResponse -> {
                    boolean result = tResponse.getResult();
                    if (result) {
                        return Observable.create((ObservableOnSubscribe<T>) emitter -> {
                            try {
                                if (tResponse.getData() == null) {
                                    emitter.onError(new CoreApiException(1, ""));
                                } else {
                                    emitter.onNext(tResponse.getData());
                                }
                                emitter.onComplete();
                            } catch (Exception e) {
                                e.printStackTrace();
                                emitter.onError(new CoreApiException(2, ""));
                            }
                        });
                    } else {
                        return Observable.error(new CoreApiException(2, tResponse.getMessage()));
                    }
                });
    }



    /**
     * 请求数据统一再处理（常规式，可为空）
     */
    public static <T> ObservableTransformer<CoreResponse<T>, Optional<T>> rxHandleResultObservableNullable() {
        return upstream -> upstream.flatMap((Function<CoreResponse<T>, ObservableSource<Optional<T>>>) tCoreResponse -> {
            CoreHeader header = tCoreResponse.getHeader();
            int responseCode = header == null ? -1 : header.getStatus();
            if (responseCode == 200) {
                return Observable.create(e -> {
                    try {
                        e.onNext(tCoreResponse.transform());
                        e.onComplete();
                    } catch (Exception exc) {
                        e.onError(exc);
                    }
                });
            } else {
                return Observable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
            }
        });
    }

    public  RxHelper getInstance() {
        return new RxHelper();
    }

    /**
     * 统一线程处理（常规式）ObservableTransformer
     */
  /*  public   <T> ObservableTransformer<Response<T>, T> rxSchedulerObservable2() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .flatMap((Function<Response<T>, ObservableSource<T>>) tResponse -> {
                    int result = tResponse.getResult();
                    if (result == 1) {
                        return Observable.create((ObservableOnSubscribe<T>) emitter -> {
                            try {
                                if (tResponse.getData() == null) {
                                    emitter.onError(new CoreApiException(result, tResponse.getMessage()));
                                } else {
                                    emitter.onNext(tResponse.getData());
                                }
                                emitter.onComplete();
                            } catch (Exception e) {
                                emitter.onError(new CoreApiException(result, tResponse.getMessage()));
                            }
                        });
                    } else {
                        return Observable.error(new CoreApiException(result, tResponse.getMessage()));
                    }
                }).retryWhen(observable -> observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if(throwable instanceof CoreApiException && ((CoreApiException) throwable).getCode()==-2){
                        return refreshTokenWhenTokenInvalid(observable);
                    }
                    return Observable.error(throwable);
                })).observeOn(AndroidSchedulers.mainThread());

    }*/
  /*  private   ObservableSource<?> refreshTokenWhenTokenInvalid(Observable<Throwable> observable) {
        synchronized(RxHelper.class){

            if(System.currentTimeMillis() <=  60 * 1000 + TokenInterceptor.getAuthTokenTime()){
                return observable.delay(2, TimeUnit.MILLISECONDS);
            }else {
                ModifyToken modifyToken = new ModifyToken();
                modifyToken.refreshToken = TokenInterceptor.getRefToken();
                ApiService.createApi(UserApi.class)
                        .modifyAssessToken(modifyToken)
                        .subscribe(new ResponseSubscriber<LoginRes>() {
                            @Override
                            protected void onSuccess(LoginRes loginRes) {
                                User user = UserManager.INSTANCE.getUser();
                                if(!StringUtils.isEmpty(loginRes.headUrl)){
                                    user.setHeadUrl(loginRes.headUrl);
                                    UserManager.INSTANCE.setUser(user);
                                }
                                TokenInterceptor.setAuthToken(loginRes.accessToken);
                            }
                            @Override
                            protected void onFailure(CoreApiException errorCode) {
                                mRefreshTokenError = new CoreApiException(errorCode);
                            }
                        });
                if (mRefreshTokenError != null) {
                    Observable<Object> error = Observable.error(mRefreshTokenError);
                    mRefreshTokenError = null;
                    return error;
                } else {
                    return Observable.just(true);
                }

            }
        }
    }
*/
    /**
     * 统一线程处理（背压式）
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelperFlowable() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 请求数据统一再处理（常规式）
     */
    public static <T> ObservableTransformer<CoreResponse<T>, T> rxHandleResultObservable() {
        return new ObservableTransformer<CoreResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(CoreResponse<T> tCoreResponse) throws Exception {
                        CoreHeader header = tCoreResponse.getHeader();
                        int responseCode = header == null ? -1 : header.getStatus();
                        if (responseCode == 200) {
                            return Observable.create((ObservableOnSubscribe<T>) emitter -> {
                                try {
                                    emitter.onNext(tCoreResponse.getData());
                                    emitter.onComplete();
                                } catch (Exception e) {
                                    emitter.onError(e);
                                }
                            });
                        } else {
                            return Observable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
                        }
                    }
                });
            }
        };
    }

    public interface CallObserver<T> {
        Observable<Response<T>> call();
    }

    /**
     * 请求数据统一再处理（背压式）
     */
    public static <T> FlowableTransformer<CoreResponse<T>, T> rxHandleResultFlowable() {
        return new FlowableTransformer<CoreResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(CoreResponse<T> tCoreResponse) throws Exception {
                        CoreHeader header = tCoreResponse.getHeader();
                        int responseCode = header == null ? -1 : header.getStatus();
                        if (responseCode == 200) {
                            return Flowable.create(new FlowableOnSubscribe<T>() {
                                @Override
                                public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                                    try {
                                        emitter.onNext(tCoreResponse.getData());
                                        emitter.onComplete();
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }, BackpressureStrategy.BUFFER);
                        } else {
                            return Flowable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 请求数据统一再处理（背压式，可为空）
     */
    public static <T> FlowableTransformer<CoreResponse<T>, Optional<T>> rxHandleResultFlowableNullable() {
        return new FlowableTransformer<CoreResponse<T>, Optional<T>>() {
            @Override
            public Publisher<Optional<T>> apply(Flowable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, Publisher<Optional<T>>>() {
                    @Override
                    public Publisher<Optional<T>> apply(CoreResponse<T> tCoreResponse) throws Exception {
                        CoreHeader header = tCoreResponse.getHeader();
                        int responseCode = header == null ? -1 : header.getStatus();
                        if (responseCode == 200) {
                            return Flowable.create(new FlowableOnSubscribe<Optional<T>>() {
                                @Override
                                public void subscribe(FlowableEmitter<Optional<T>> emitter) throws Exception {
                                    try {
                                        emitter.onNext(tCoreResponse.transform());
                                        emitter.onComplete();
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }, BackpressureStrategy.BUFFER);
                        } else {
                            return Flowable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
                        }
                    }
                });
            }
        };
    }
}
