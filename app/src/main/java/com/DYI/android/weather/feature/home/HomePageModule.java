package com.DYI.android.weather.feature.home;

import com.baronzhang.android.weather.di.scope.ActivityScoped;
import com.DYI.android.weather.feature.home.HomePagePresenter;

import dagger.Module;
import dagger.Provides;

import com.DYI.android.weather.feature.home.HomePageContract;


@Module
public class HomePageModule {

    private HomePageContract.View view;

    public HomePageModule(HomePageContract.View view) {

        this.view = view;
    }

    @Provides
    @ActivityScoped
    HomePageContract.View provideHomePageContractView() {
        return view;
    }

}
