package sample.com.digiweathersample.data.models;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import sample.com.digiweathersample.R;
import sample.com.digiweathersample.contracts.MainWeatherContract;
import sample.com.digiweathersample.data.models.responses.ResponseModel;
import sample.com.digiweathersample.data.services.LocationServices;
import sample.com.digiweathersample.data.services.ServiceApi;
import sample.com.digiweathersample.data.utilities.Global;

/**
 * Created by LENOVO on 12/9/2017.
 */

public class WeatherModel implements MainWeatherContract.Model {

    private Context context;
    private ServiceApi serviceApi;
    private Realm realm;
    private MainWeatherContract.Presenter presenter;
    private CompositeDisposable disposable=new CompositeDisposable();
    private String city;

    public WeatherModel(Context context, MainWeatherContract.Presenter presenter) {

        this.context = context;
        this.presenter = presenter;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        EventBus.getDefault().register(this);


    }


    @Override
    public void getWeather(String city) {
        this.city = city;

        if (city.isEmpty()) {
            city = Global.getInstance().getPreferences(context).getString("cityName", "tehran");
            getWeatherProcidure(city);
        } else {
            getWeatherProcidure(city);
        }

    }

    @Override
    public void getLocation() {

        new LocationServices(context, this);

    }



    private void getWeatherProcidure(String city) {

        // state : open an app and internet connection is Ok
        if (Global.getInstance().isConnected(context)) {
            getApi(city);
        } else {
            // state : first time open an app and internet connection is fail
            if (realm.where(ResponseModel.class).findAll().size() == 0) {
                presenter.setMessage("لطفا اتصالتان به اینترنت را بررسی کنید");

            } else {
                // state :  there are some data in database and internet dosent available  it returns last city searched by user
                ResponseModel resModel;
                if ((resModel = Global.getInstance().fromDb(realm, context, city)) != null)
                if ((resModel = Global.getInstance().fromDb(realm, context, city)) != null)
                    presenter.setWeather(resModel);
                else
                    presenter.setMessage("لطفا اتصالتان به اینترنت را بررسی کنید");
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0, sticky = true)
    public void onMessageEvent(EventModel event) {

        if (city != null) {
            getApi(city);

        }
    }

    private void getApi(String city) {
        serviceApi = Global.getInstance().createService(ServiceApi.class);

        Observable<ResponseModel> weather = serviceApi.getWeather(city);
        disposable.add(weather.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseModel>() {

                    @Override
                    public void onNext(ResponseModel responseModel) {

                        presenter.setWeather(responseModel);
                        Global.getInstance().toDB(context, realm, responseModel);

                    }

                    @Override
                    public void onError(Throwable e) {


                        if (e.getMessage() == null)
                            presenter.setMessage(context.getResources().getString(R.string.server_not_response));
                        else
                            presenter.setMessage(context.getResources().getString(R.string.city_name_error));

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    @Override
    public void dispose() {
        disposable.dispose();
        EventBus.getDefault().unregister(this);
    }


}
