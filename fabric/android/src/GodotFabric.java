package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;

public class GodotFabric extends Godot.SingletonBase {

    private Godot activity = null;
    private Integer facebookCallbackId = 0;

    static public Godot.SingletonBase initialize(Activity p_activity) 
    {
        Log.i("GodotFabric", "Init singleton");
        return new GodotFabric(p_activity); 
    } 

    public GodotFabric(Activity p_activity) 
    {
        registerClass("GodotFabric", new String[]{"forceCrash"});
        activity = (Godot)p_activity;
        //Fabric.with(activity, new Crashlytics());
        final Fabric fabric = new Fabric.Builder(activity)
            .kits(new Crashlytics(), new Answers())
            .debuggable(true)
            .build();
        Fabric.with(fabric);
        Log.d("GodotFabric", "Create fabric instance");
    }

    // Public methods

    public void forceCrash()
    {
        throw new RuntimeException("This is a crash");
        /*
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
        */
    }

    @Override protected void onMainActivityResult (int requestCode, int resultCode, Intent data)
    {
        //callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
