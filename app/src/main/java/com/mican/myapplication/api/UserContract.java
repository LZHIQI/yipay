package com.mican.myapplication.api;

import com.mican.myapplication.BasePresenter;
import com.mican.myapplication.BaseView;
import com.mican.myapplication.api.req.PayCallReq;
import com.mican.myapplication.api.req.QueryReq;
import com.mican.myapplication.api.req.Register;


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

        public  abstract void register( Register register,View view);

        public  abstract  void login( Register register,View view);


        public  abstract  void detail( QueryReq queryReq,View view);


        public  abstract     void tcList(View view );

        public  abstract  void queryOrder( QueryReq queryReq,View view);



        public  abstract   void queryStatus( QueryReq queryReq,View view);


        public  abstract  void merchantStatus( QueryReq queryReq,View view);


        public  abstract void payCall( PayCallReq queryReq,View view);




    }
}
