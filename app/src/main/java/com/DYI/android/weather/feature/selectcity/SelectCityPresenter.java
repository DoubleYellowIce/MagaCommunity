package com.DYI.android.weather.feature.selectcity;

import android.content.Context;

import com.DYI.android.weather.di.component.DaggerPresenterComponent;
import com.DYI.android.weather.data.db.dao.CityDao;
import com.DYI.android.weather.di.component.DaggerPresenterComponent;
import com.baronzhang.android.weather.di.module.ApplicationModule;
import com.baronzhang.android.weather.di.scope.ActivityScoped;
import com.DYI.android.weather.feature.selectcity.SelectCityContract;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@ActivityScoped
public final class SelectCityPresenter implements SelectCityContract.Presenter {

    private final SelectCityContract.View cityListView;

    private CompositeSubscription subscriptions;

    @Inject
    CityDao cityDao;

    @Inject
    SelectCityPresenter(Context context, SelectCityContract.View view) {

        this.cityListView = view;
        this.subscriptions = new CompositeSubscription();
        cityListView.setPresenter(this);

        DaggerPresenterComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build().inject(this);
    }

    @Override
    public void loadCities() {
        Subscription subscription = Observable.just(cityDao.queryCityList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityListView::displayCities);
        subscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        loadCities();
    }

    @Override
    public void unSubscribe() {
        subscriptions.clear();
    }
}
