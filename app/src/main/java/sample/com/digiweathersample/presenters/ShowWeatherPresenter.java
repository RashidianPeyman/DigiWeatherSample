package sample.com.digiweathersample.presenters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import sample.com.digiweathersample.R;
import sample.com.digiweathersample.contracts.MainWeatherContract;
import sample.com.digiweathersample.data.models.WeatherModel;
import sample.com.digiweathersample.data.models.responses.ResponseModel;

/**
 * Created by LENOVO on 12/8/2017.
 */

public class ShowWeatherPresenter implements MainWeatherContract.Presenter {

    private Context context;
    private MainWeatherContract.View view;
    private MainWeatherContract.Model weatherModel;

    public ShowWeatherPresenter(Context context, MainWeatherContract.View view) {
        this.context = context;
        weatherModel = new WeatherModel(context, this);
        this.view = view;
        view.initView();

        view.enableProgressBar();
        weatherModel.getWeather("");

    }

    @Override
    public void onClick(View getView) {

        switch (getView.getId()) {
            case R.id.imgGps:
                view.enableProgressBar();
                weatherModel.getLocation();

                break;
            case R.id.edtSearch:
                view.visibleEdtSearch();
                view.enableProgressBar();
                break;
            case R.id.imgRefresh:
                view.enableProgressBar();
                weatherModel.getWeather(view.getCityName());

                break;

        }

    }

    @Override
    public void setWeather(ResponseModel responseModel) {
        view.disableProgressBar();
        view.setCity(responseModel.getName());
        view.setDescription(responseModel.getWeather().get(0).getDescription());
        view.setHumidity(responseModel.getMain().getHumidity());
        view.setImageState(responseModel.getWeather().get(0).getIcon());
        view.setLastUpdate(responseModel.getLastUpdate());
        view.setMaximumTemp(responseModel.getMain().getTempMax());
        view.setMinimumTemp(responseModel.getMain().getTempMin());
        view.setTemperature(responseModel.getMain().getTemp());
        view.setWindSpeed(responseModel.getWind().getSpeed());

    }

    @Override
    public void setMessage(String message) {
        view.showMessages(message);
    }

    @Override
    public void setOnKeyClick(View getView, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)

            if (i == KeyEvent.KEYCODE_ENTER) {

                weatherModel.getWeather(view.getCityName());
                view.enableProgressBar();
            }
    }

    @Override
    public void startDispose() {
        weatherModel.dispose();
    }


}
