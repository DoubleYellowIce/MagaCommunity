package com.DYI.android.weather.feature.selectcity;

import java.util.List;

import com.baronzhang.android.weather.data.db.entities.City;
import com.baronzhang.android.weather.base.BasePresenter;
import com.baronzhang.android.weather.base.BaseView;

public interface SelectCityContract {

    interface View extends BaseView<Presenter> {

        void displayCities(List<City> cities);
    }

    interface Presenter extends BasePresenter {

        void loadCities();
    }
}
