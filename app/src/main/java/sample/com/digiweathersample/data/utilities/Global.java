package sample.com.digiweathersample.data.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.realm.Case;
import io.realm.Realm;
import retrofit2.Retrofit;


import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.com.digiweathersample.data.models.responses.ResponseModel;
import sample.com.digiweathersample.data.services.ServicePath;

/**
 * Created by LENOVO on 12/9/2017.
 */

public class Global {


    private Global() {

    }

    public static Global getInstance() {

        return new Global();
    }

    public <S> S createService(Class<S> serviceClass) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServicePath.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClass);

    }


    public void toDB(Context context, Realm realm, ResponseModel response) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(response);
        realm.commitTransaction();

        // to save last city when we are offline
        SharedPreferences.Editor editor = context.getSharedPreferences("lastId", context.MODE_PRIVATE).edit();
        editor.putInt("id", response.getId());
        editor.putString("cityName", response.getName());
        editor.apply();

    }


    public ResponseModel fromDb(Realm realm, Context context, String city) {

        ResponseModel result;

        if ((result = realm.where(ResponseModel.class).contains("name", city, Case.INSENSITIVE).findFirst()) != null) {
            return result;
        } else {

//            int restoredId = getPreferences(context).getInt("id",0);
//
//            if (restoredId != 0) {
//                ResponseModel result = realm.where(ResponseModel.class).equalTo("id", restoredId).findFirst();
//                return result;
//            } else {
            return null;
//            }
        }
    }


    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public SharedPreferences getPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("lastId", Context.MODE_PRIVATE);
        return prefs;
    }



}


