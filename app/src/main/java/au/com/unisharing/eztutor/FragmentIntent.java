package au.com.unisharing.eztutor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Class Name   : FragmentIntent
 * Author       : Bruce.liu
 * Created Date :
 * Description  : Wrapper file to handle fragment with intent
 */

public class FragmentIntent extends Intent {

    public static final String EXTRA_FRAGMENT_NAME = "FRAGMENT_NAME";
    public static final String EXTRA_FRAGMENT_ARGUEMTN = "FRAGMENT_ARGUMENT";
    public static final String EXTRA_FRAGMENT_TRANSACTION_BEHAVIOR = "FRAGMENT_TRANSACTION_BEHAVIOR";
    public static final String EXTRA_FRAGMENT_TITLE = "TITLE";

    private Bundle argument = new Bundle();

    public FragmentIntent() {
        super();
        putExtra(EXTRA_FRAGMENT_ARGUEMTN, argument);
    }

    public FragmentIntent(Intent intent) {
        super(intent);
        putExtra(EXTRA_FRAGMENT_ARGUEMTN, argument);
    }

    public FragmentIntent(Context context) {
        this(context, context.getClass());
    }

    public FragmentIntent(Context context, Class<?> cls) {
        super(context, cls);
        putExtra(EXTRA_FRAGMENT_ARGUEMTN, argument);
    }

    public void setFragment(Class<?> fragment) {
        setFragment(fragment, null);
    }

    public void setFragment(Class<?> fragment, FragmentTransactionBehavior behavior) {
        if (behavior == null) {
            behavior = new FragmentTransactionBehavior();
        }
        putExtra(EXTRA_FRAGMENT_NAME, fragment.getName());
        putExtra(EXTRA_FRAGMENT_TRANSACTION_BEHAVIOR, behavior);

    }

    public void setTitle(String title){
        putArgument(EXTRA_FRAGMENT_TITLE,title);
    }

    public void setArguments(Bundle bundle){
        if (bundle != null){
            argument = bundle;
            putExtra(EXTRA_FRAGMENT_ARGUEMTN,argument);
        }
    }
    public void putArgument(String key, short value) {
        argument.putShort(key, value);
    }

    public void putArgument(String key, int value){
        argument.putInt(key, value);
    }

    public void putArgument(String key, long value){
        argument.putLong(key, value);
    }
    public void putArgument(String key, float value){
        argument.putFloat(key, value);
    }


    public void putArgument(Serializable value){
        argument.putSerializable(value.getClass().getName(), value);
    }

    public void putArgument(String key, Serializable value){
        argument.putSerializable(key, value);
    }
    public void putArgument(String key, JSONObject value){
        argument.putString(key, value.toString());
    }

    public void putArgument(String key, JSONArray value) {
        argument.putString(key, value.toString());
    }

    public void putArgument(String key, Bundle value){
        argument.putBundle(key, value);
    }

    public void putArgument(Object value){
        putArgument(value.getClass().getName(), value);
    }
    public void putArgument(String key, Object value){
        putArgument(key, value.toString());
    }

    public Resolver newResolver(Context context){
        return newResolver(context, this);

    }

    public static Resolver newResolver(Context context, Intent intent){
        return  new  Resolver(context, intent);
    }

    public static final class Resolver {

        private Context context;
        private String  fragmentName;
        private Bundle  argument;
        private FragmentTransactionBehavior behavior;

        Resolver(Context context, Intent intent) {
            this.context = context;
            this.fragmentName = intent.getStringExtra(EXTRA_FRAGMENT_NAME);
            this.argument = intent.getBundleExtra(EXTRA_FRAGMENT_ARGUEMTN);
            if (intent.hasExtra(EXTRA_FRAGMENT_TRANSACTION_BEHAVIOR)){
                this.behavior = intent.getParcelableExtra(EXTRA_FRAGMENT_TRANSACTION_BEHAVIOR);
            }else{
                this.behavior = new  FragmentTransactionBehavior();
            }
        }

        public Fragment newFragment() throws  Fragment.InstantiationException{

            return Fragment.instantiate(context,fragmentName,argument);

        }

        public FragmentTransactionBehavior getBehavior(){
            return behavior;
        }
    }
}
