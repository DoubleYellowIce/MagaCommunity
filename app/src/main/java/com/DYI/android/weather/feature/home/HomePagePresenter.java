package com.DYI.android.weather.feature.home;

import android.content.Context;
import android.widget.Toast;

import com.DYI.android.weather.di.component.DaggerPresenterComponent;
import com.DYI.android.weather.feature.home.HomePageContract;
import com.baronzhang.android.library.util.RxSchedulerUtils;
import com.DYI.android.weather.data.db.dao.WeatherDao;
import com.baronzhang.android.weather.data.preference.PreferenceHelper;
import com.DYI.android.weather.data.preference.WeatherSettings;
import com.DYI.android.weather.data.repository.WeatherDataRepository;
import com.baronzhang.android.weather.di.module.ApplicationModule;
import com.baronzhang.android.weather.di.scope.ActivityScoped;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


@ActivityScoped
public final class HomePagePresenter implements HomePageContract.Presenter {

    private final Context context;
    private final HomePageContract.View weatherView;

    private CompositeSubscription subscriptions;

    @Inject
    WeatherDao weatherDao;

    @Inject
    HomePagePresenter(Context context, HomePageContract.View view) {

        this.context = context;
        this.weatherView = view;
        this.subscriptions = new CompositeSubscription();
        weatherView.setPresenter(this);

        DaggerPresenterComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build().inject(this);
    }

    @Override
    public void subscribe() {
        String cityId = PreferenceHelper.getSharedPreferences().getString(WeatherSettings.SETTINGS_CURRENT_CITY_ID.getId(), "");
        loadWeather(cityId, false);
    }

    @Override
    public void loadWeather(String cityId, boolean refreshNow) {

        Subscription subscription = WeatherDataRepository.getWeather(context, cityId, weatherDao, refreshNow)
                .compose(RxSchedulerUtils.normalSchedulersTransformer())
                .subscribe(weatherView::displayWeatherInformation, throwable -> {
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show();
                });
        subscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        subscriptions.clear();
    }
}
