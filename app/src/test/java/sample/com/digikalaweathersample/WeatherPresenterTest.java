package sample.com.digikalaweathersample;

import android.widget.AdapterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import sample.com.digiweathersample.contracts.MainWeatherContract;
import sample.com.digiweathersample.data.models.WeatherModel;
import sample.com.digiweathersample.presenters.ShowWeatherPresenter;
import sample.com.digiweathersample.views.ShowWeatherActivity;

import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    @Mock
    MainWeatherContract.View mView;
    @Mock
    MainWeatherContract.Model model;
    @Mock
    AdapterView.OnItemClickListener itemClickListener;

    @Mock
    MainWeatherContract.Presenter presenterMvp;


    @Mock
    ShowWeatherActivity context;



    private ShowWeatherPresenter presenter;
    private WeatherModel weatherModel;

    @Before
    public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

        presenter = new ShowWeatherPresenter(context.getApplicationContext(), mView);


    }

    @Test
    public void addition_isCorrect() throws Exception {


    }
//
//
//
//    }
}