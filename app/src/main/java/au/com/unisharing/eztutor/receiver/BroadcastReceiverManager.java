package au.com.unisharing.eztutor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public class BroadcastReceiverManager {

    public interface BroadcastReceiverFactory{
        Map<String, BroadcastReceiver> getBroadcastReceivers();
    }
    private final BroadcastReceiverFactory factory;
    private final Map<String, BroadcastReceiver> receiverMap = new ArrayMap<>();

    public BroadcastReceiverManager(BroadcastReceiverFactory factory) {
        this.factory = factory;
    }

    public void register(Context context){
        if (context == null){
            return;
        }
        if (factory != null){
            registerReceivers(context, factory.getBroadcastReceivers());
        }
    }

    private void registerReceivers(Context context, Map<String, BroadcastReceiver> map) {
        if(map == null || map.size() == 0){
            return;
        }

        for (String action: map.keySet()){
            BroadcastReceiver receiver = map.get(action);
            if (receiver != null && !receiverMap.containsKey(action)){
                IntentFilter filter = new IntentFilter(action);
                context.registerReceiver(receiver,filter);
                receiverMap.put(action,receiver);
            }
        }

    }

    public void unregister(Context context){
        if (context == null){
            return;
        }
        unregisterReceivers(context);
    }

    private void unregisterReceivers(Context context) {
        for (String action : receiverMap.keySet()){
            BroadcastReceiver receiver = receiverMap.get(action);
            if (receiver != null){
                context.unregisterReceiver(receiver);
            }
        }
        receiverMap.clear();
    }
}
