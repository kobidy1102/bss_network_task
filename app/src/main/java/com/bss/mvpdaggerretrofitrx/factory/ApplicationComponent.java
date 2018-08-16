package com.bss.mvpdaggerretrofitrx.factory;

import com.bss.mvpdaggerretrofitrx.domain.login.LoginActivity;

import dagger.Component;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);
}
