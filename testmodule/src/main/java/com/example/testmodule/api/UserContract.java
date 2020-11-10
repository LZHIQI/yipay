package com.example.testmodule.api;


import com.example.testmodule.BasePresenter;
import com.example.testmodule.BaseView;
import com.example.testmodule.api.req.PayCallReq;
import com.example.testmodule.api.req.QueryReq;
import com.example.testmodule.api.req.Register;

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

     abstract class Presenter extends BasePresenter<View> {

        public  abstract void register(Register register, View view);



    }
}
