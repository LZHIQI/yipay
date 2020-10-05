package com.mican.myapplication.api;

import com.mican.myapplication.BasePresenter;
import com.mican.myapplication.BaseView;

/**
 * @name lzq
 * @class name：com.mican.myapplication.api
 * @class describe
 * @time 2020/10/5 2:25 PM
 * @change
 * @chang
 * @class describe
 */
public interface UserContract {

    interface View extends BaseView {
        void getError(String message);

        void getSuccess(Object o);
    }

    public abstract class Presenter extends BasePresenter<View> {









    }
}
