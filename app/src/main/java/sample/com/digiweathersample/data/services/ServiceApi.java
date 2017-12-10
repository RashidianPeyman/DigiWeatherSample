package sample.com.digiweathersample.data.services;




import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sample.com.digiweathersample.data.models.responses.ResponseModel;

/**
 * Created by LENOVO on 12/9/2017.
 */

public interface ServiceApi {

    @GET(ServicePath.GET_CITY)
    Observable<ResponseModel> getWeather(@Query("q") String city);
}
