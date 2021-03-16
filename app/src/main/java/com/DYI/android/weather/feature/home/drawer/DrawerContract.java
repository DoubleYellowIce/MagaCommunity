package com.DYI.android.weather.feature.home.drawer;

import com.baronzhang.android.weather.base.BasePresenter;
import com.baronzhang.android.weather.base.BaseView;
import com.DYI.android.weather.data.db.entities.minimalist.Weather;
import com.DYI.android.weather.feature.home.drawer.DrawerMenuPresenter;

import java.io.InvalidClassException;
import java.util.List;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         16/4/16
 */
public interface DrawerContract {

    interface View extends BaseView<DrawerMenuPresenter> {

        void displaySavedCities(List<Weather> weatherList);
    }

    interface Presenter extends BasePresenter {

        void loadSavedCities();

        void deleteCity(String cityId);

        void saveCurrentCityToPreference(String cityId) throws InvalidClassException;
    }
}
