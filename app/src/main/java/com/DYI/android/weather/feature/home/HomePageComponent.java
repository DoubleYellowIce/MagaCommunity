package com.DYI.android.weather.feature.home;


import com.baronzhang.android.weather.di.component.ApplicationComponent;
import com.baronzhang.android.weather.di.scope.ActivityScoped;

import dagger.Component;


@ActivityScoped
@Component(modules = HomePageModule.class, dependencies = ApplicationComponent.class)
public interface HomePageComponent {

    void inject(MainActivity mainActivity);
}
