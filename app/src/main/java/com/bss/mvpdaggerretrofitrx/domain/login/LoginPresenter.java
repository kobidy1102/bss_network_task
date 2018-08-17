package com.bss.mvpdaggerretrofitrx.domain.login;

import com.bss.mvpdaggerretrofitrx.service.authentication.RestAuthenticationService;
import com.bss.mvpdaggerretrofitrx.service.model.LoginRequest;
import com.bss.mvpdaggerretrofitrx.service.model.LoginResponse;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginPresenter extends MvpBasePresenter<LoginView> {               //2

    private RestAuthenticationService restAuthenticationService;    //có cái observable và post

    public LoginPresenter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kiddi.api.web.beesightsoft.com/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   // kêt hợp với rx
                .addConverterFactory(GsonConverterFactory.create())           //convert json bằng gson
                .build();
        restAuthenticationService = retrofit.create(RestAuthenticationService.class);       //khởi tại retrofit để add vào abservable      //retrofit để đọc dữ liệu từ netwwork  kiểu Json thì có Gson để parse
    }                                                                               // cũng như httpConnecttion add vào async task  //  observable để chạy cái retrofit trên 1 luồng riêng.

    public void login(String email, String password) {                   // xử lý logic đăng nhập
        getView().showLoading();
        restAuthenticationService.login(new LoginRequest(email, password))       //kết hợp giữa retrofit và observable
                .subscribeOn(Schedulers.io())                                          //gửi ecmail mà pass đi,,nêu có trong api nó sẽ trả về cái respone ko có nó sẽ báo lỗi
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {                       //chạy xong cái observable là zô đây
                    @Override
                    public void call() {
                        getView().hideLoading();
                    }
                })
                .subscribe(new Action1<LoginResponse>() {
                    @Override
                    public void call(LoginResponse loginResponse) {      // cái observable gửi item đi
                        getView().loginSuccessful(loginResponse);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        getView().loginFailed(throwable);
                    }
                });

    }
}
