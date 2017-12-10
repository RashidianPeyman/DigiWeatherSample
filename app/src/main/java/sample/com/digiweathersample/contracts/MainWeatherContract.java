package sample.com.digiweathersample.contracts;

import android.view.KeyEvent;

import sample.com.digiweathersample.data.models.responses.ResponseModel;

/**
 * Created by LENOVO on 12/9/2017.
 */

public interface MainWeatherContract {
    public interface Model {

        public void getWeather(String city);

        public void getLocation();

        public void dispose();

    }

    public interface View {
        public void initView();

        public String getCityName();

        public void showMessages(String message);

        public void disableProgressBar();

        public void enableProgressBar();

        public void visibleEdtSearch();

        public void hideEdtSearch();

        public void setCity(String cityName);

        public void setTemperature(String temperature);

        public void setMinimumTemp(String minimumTemp);

        public void setMaximumTemp(String maximumTemp);

        public void  setLastUpdate(String lastUpdate);
        public void   setDescription(String description);
        public void   setHumidity(String humidity);
        public void   setWindSpeed(String windSpeed);
        public void   setImageState(String imgUrl);


    }


    public interface Presenter {

        public void onClick(android.view.View getView);

        public void setWeather(ResponseModel responseModel);

        public void setMessage(String message);

        public void setOnKeyClick(android.view.View getView, int i, KeyEvent keyEvent);

        public void startDispose();

    }
}
