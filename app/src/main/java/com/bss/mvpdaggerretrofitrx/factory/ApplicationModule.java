package com.bss.mvpdaggerretrofitrx.factory;

import android.content.pm.ApplicationInfo;

import com.bss.mvpdaggerretrofitrx.service.authentication.RestAuthenticationService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://kiddi.api.web.beesightsoft.com/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public RestAuthenticationService provideAuthenticationService(Retrofit retrofit) {
        return retrofit.create(RestAuthenticationService.class);
    }


}
