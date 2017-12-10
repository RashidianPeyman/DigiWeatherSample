package sample.com.digiweathersample.data.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import sample.com.digiweathersample.data.models.EventModel;
import sample.com.digiweathersample.data.utilities.Global;

/**
 * Created by LENOVO on 12/9/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Global.getInstance().isConnected(context)){
            EventModel eventModel=new EventModel();
            eventModel.isConnected();
            EventBus.getDefault().postSticky(eventModel);
        }


    }
}
