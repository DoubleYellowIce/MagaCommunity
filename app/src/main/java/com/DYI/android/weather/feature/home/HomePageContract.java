package com.DYI.android.weather.feature.home;

import com.DYI.android.weather.data.db.entities.minimalist.Weather;
import com.baronzhang.android.weather.base.BasePresenter;
import com.baronzhang.android.weather.base.BaseView;

public interface HomePageContract {

    interface View extends BaseView<Presenter> {

        void displayWeatherInformation(Weather weather);
    }

    interface Presenter extends BasePresenter {

        void loadWeather(String cityId, boolean refreshNow);
    }
}
