package edu.com.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import edu.com.app.data.DataManager;
import edu.com.app.injection.component.ApplicationComponent;


import edu.com.app.injection.component.DaggerApplicationComponent;
import edu.com.app.injection.module.ApplicationModule;
import edu.com.app.ui.widget.ViewDisplay;
import edu.com.app.data.local.PreferencesHelper;
import edu.com.app.util.ToastUtils;
import timber.log.Timber;

/**
 * Created by Anthony on 2016/6/3.
 * Class Note:
 */
public class MyApplication extends Application {
    @Inject
    Bus mEventBus;
    @Inject
    DataManager mDataManager;

    private ApplicationComponent mAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        getAppComponent().inject(this);
        mEventBus.register(this);
        initLeanCloud();
//       初始化环信EaseUI
//        initEaseUI();

        //异常捕获
//        Thread.setDefaultUncaughtExceptionHandler(new LocalFileUncaughtExceptionHandler(this,
//                Thread.getDefaultUncaughtExceptionHandler()));

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
//            Fabric.with(this, new Crashlytics());
        }
//        Timber.plant(new CrashlyticsTree());

    }

    //初始化LeanCloud
    private void initLeanCloud() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "k9XVHXc7UHdiv6UOielOPyYc-gzGzoHsz", "5WHJ0HesNrOnveAYcoM5sLL2");
    }

    public ApplicationComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerApplicationComponent.builder().
                    applicationModule(new ApplicationModule(this)).build();
        }
        return mAppComponent;
    }


    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public void setAppComponent(ApplicationComponent appComponent) {
        mAppComponent = appComponent;
    }


}
