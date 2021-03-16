package com.baronzhang.android.library.util;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public final class RxSchedulerUtils {


    public static <T> Observable.Transformer<T, T> normalSchedulersTransformer() {

        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
