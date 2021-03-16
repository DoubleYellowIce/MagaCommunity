package com.DYI.android.weather.di.component;

import com.DYI.android.weather.feature.home.HomePagePresenter;
import com.DYI.android.weather.feature.home.drawer.DrawerMenuPresenter;
import com.baronzhang.android.weather.di.module.ApplicationModule;
import com.DYI.android.weather.feature.home.drawer.DrawerMenuPresenter;
import com.DYI.android.weather.feature.selectcity.SelectCityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author 张磊 (baron[dot]zhanglei[at]gmail[dot]com)
 *         2016/12/2
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface PresenterComponent {

    void inject(HomePagePresenter presenter);

    void inject(SelectCityPresenter presenter);

    void inject(DrawerMenuPresenter presenter);
}
 