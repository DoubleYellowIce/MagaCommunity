package com.DYI.android.weather.feature.selectcity;

import com.DYI.android.weather.feature.selectcity.SelectCityActivity;
import com.baronzhang.android.weather.di.component.ApplicationComponent;
import com.baronzhang.android.weather.di.scope.ActivityScoped;

import dagger.Component;


@ActivityScoped
@Component(modules = SelectCityModule.class, dependencies = ApplicationComponent.class)
public interface SelectCityComponent {

    void inject(SelectCityActivity selectCityActivity);
}
