package sample.com.digiweathersample.views;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import sample.com.digiweathersample.R;
import sample.com.digiweathersample.contracts.MainWeatherContract;
import sample.com.digiweathersample.presenters.ShowWeatherPresenter;

public class ShowWeatherActivity extends AppCompatActivity implements MainWeatherContract.View, View.OnClickListener, View.OnKeyListener {

    private MainWeatherContract.Presenter presenter;
    private Location location;

    private TextView txtName, txtLastUpdate, txtDescription, txtHumidity, txtTemperature, txtMinTemp, txtMaxTemp, txtSpeedWind;
    private ImageView imgPen, imgGps, imgState, imgRefresh;
    private EditText edtSearch;
    private ProgressBar pbBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        presenter = new ShowWeatherPresenter(this, this);
    }

    @Override
    public void initView() {
        txtName = (TextView) findViewById(R.id.txtName);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtTemperature = (TextView) findViewById(R.id.txtTemperature);
        txtMinTemp = (TextView) findViewById(R.id.txtMinTemp);
        txtMaxTemp = (TextView) findViewById(R.id.txtMaxTemp);
        txtSpeedWind = (TextView) findViewById(R.id.txtSpeedWind);


        imgPen = (ImageView) findViewById(R.id.imgPen);
        imgGps = (ImageView) findViewById(R.id.imgGps);
        imgState = (ImageView) findViewById(R.id.imgState);
        imgRefresh = (ImageView) findViewById(R.id.imgRefresh);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        pbBar = (ProgressBar) findViewById(R.id.pbBar);


        imgPen.setOnClickListener(this);
        imgGps.setOnClickListener(this);
        imgRefresh.setOnClickListener(this);
        edtSearch.setOnKeyListener(this);


    }

    @Override
    public String getCityName() {
        return edtSearch.getText().toString().trim();
    }


    @Override
    public void showMessages(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void disableProgressBar() {
        pbBar.setVisibility(View.GONE);
    }

    @Override
    public void enableProgressBar() {
        pbBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void visibleEdtSearch() {
        edtSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEdtSearch() {
        edtSearch.setVisibility(View.GONE);
    }

    @Override
    public void setCity(String cityName) {
        txtName.setText(cityName);
    }

    @Override
    public void setTemperature(String temperature) {
        txtTemperature.setText(temperature);
    }

    @Override
    public void setMinimumTemp(String minimumTemp) {
        txtMinTemp.setText(minimumTemp);
    }

    @Override
    public void setMaximumTemp(String maximumTemp) {
        txtMaxTemp.setText(maximumTemp);
    }

    @Override
    public void setLastUpdate(String lastUpdate) {
        txtLastUpdate.setText(lastUpdate);
    }

    @Override
    public void setDescription(String description) {
        txtDescription.setText(description);
    }

    @Override
    public void setHumidity(String humidity) {
        txtHumidity.setText(humidity);
    }

    @Override
    public void setWindSpeed(String windSpeed) {
        txtSpeedWind.setText(windSpeed);
    }

    @Override
    public void setImageState(String imgUrl) {
        Picasso.with(this).load(imgUrl).into(imgState);
    }

    @Override
    public void onClick(View view) {
        presenter.onClick(view);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        presenter.setOnKeyClick(view, i, keyEvent);
        return false;
    }

    @Override
    protected void onStop() {
        presenter.startDispose();
        super.onStop();
    }




}



