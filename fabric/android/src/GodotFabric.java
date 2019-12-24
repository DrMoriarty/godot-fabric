package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Currency;
import java.math.BigDecimal;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.PurchaseEvent;
import com.crashlytics.android.answers.AddToCartEvent;
import com.crashlytics.android.answers.StartCheckoutEvent;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.SearchEvent;
import com.crashlytics.android.answers.ShareEvent;
import com.crashlytics.android.answers.RatingEvent;
import com.crashlytics.android.answers.SignUpEvent;
import com.crashlytics.android.answers.LoginEvent;
import com.crashlytics.android.answers.InviteEvent;
import com.crashlytics.android.answers.LevelStartEvent;
import com.crashlytics.android.answers.LevelEndEvent;
import com.crashlytics.android.answers.CustomEvent;
import io.fabric.sdk.android.Fabric;

public class GodotFabric extends Godot.SingletonBase {

    private Godot activity = null;

    static public Godot.SingletonBase initialize(Activity p_activity) 
    {
        Log.i("GodotFabric", "Init singleton");
        return new GodotFabric(p_activity); 
    } 

    public GodotFabric(Activity p_activity) 
    {
        registerClass("GodotFabric", new String[]{
                "forceCrash",
                "logPurchase", "logAddToCard", "logStartCheckout", "logContentView", "logSearch", "logShare", "logRating", "logSignUp", "logLogin", "logInvite", "logLevelStart", "logLevelEnd", "logCustom"
            });
        activity = (Godot)p_activity;
        //Fabric.with(activity, new Crashlytics());
        final Fabric fabric = new Fabric.Builder(activity)
            .kits(new Crashlytics(), new CrashlyticsNdk(), new Answers())
            .debuggable(true)
            .build();
        Fabric.with(fabric);
        Log.d("GodotFabric", "Create fabric instance");
    }

    // Public methods

    public void forceCrash()
    {
        throw new RuntimeException("This is a crash");
    }

    public void logPurchase(final Dictionary params)
    {
        PurchaseEvent event = new PurchaseEvent();
        for(String key: params.get_keys()) {
            if(key.equals("price")) event.putItemPrice(BigDecimal.valueOf(((Number)params.get(key)).floatValue()));           // number
            else if(key.equals("currency")) event.putCurrency(Currency.getInstance(params.get(key).toString()));  // string
            else if(key.equals("name")) event.putItemName(params.get(key).toString());                            // string
            else if(key.equals("type")) event.putItemType(params.get(key).toString());                            // string
            else if(key.equals("id")) event.putItemId(params.get(key).toString());                                // string
            else if(key.equals("success")) event.putSuccess(((Boolean)params.get(key)).booleanValue());                          // bool
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logPurchase(event);
    }

    public void logAddToCard(final Dictionary params)
    {
        AddToCartEvent event = new AddToCartEvent();
        for(String key: params.get_keys()) {
            if(key.equals("price")) event.putItemPrice(BigDecimal.valueOf(((Number)params.get(key)).floatValue()));           // number
            else if(key.equals("currency")) event.putCurrency(Currency.getInstance(params.get(key).toString()));  // string
            else if(key.equals("name")) event.putItemName(params.get(key).toString());                            // string
            else if(key.equals("type")) event.putItemType(params.get(key).toString());                            // string
            else if(key.equals("id")) event.putItemId(params.get(key).toString());                                // string
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logAddToCart(event);
    }

    public void logStartCheckout(final Dictionary params)
    {
        StartCheckoutEvent event = new StartCheckoutEvent();
        for(String key: params.get_keys()) {
            if(key.equals("price")) event.putTotalPrice(BigDecimal.valueOf(((Number)params.get(key)).floatValue()));           // number
            else if(key.equals("currency")) event.putCurrency(Currency.getInstance(params.get(key).toString()));  // string
            else if(key.equals("count")) event.putItemCount(((Number)params.get(key)).intValue());                          // number
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logStartCheckout(event);
    }

    public void logContentView(final Dictionary params)
    {
        ContentViewEvent event = new ContentViewEvent();
        for(String key: params.get_keys()) {
            if(key.equals("name")) event.putContentName(params.get(key).toString());       // string
            else if(key.equals("type")) event.putContentType(params.get(key).toString());  // string
            else if(key.equals("id")) event.putContentId(params.get(key).toString());      // string
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logContentView(event);
    }

    public void logSearch(final Dictionary params)
    {
        SearchEvent event = new SearchEvent();
        for(String key: params.get_keys()) {
            if(key.equals("query")) event.putQuery(params.get(key).toString());       // string
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logSearch(event);
    }

    public void logShare(final Dictionary params)
    {
        ShareEvent event = new ShareEvent();
        for(String key: params.get_keys()) {
            if(key.equals("method")) event.putMethod(params.get(key).toString());          // string
            else if(key.equals("name")) event.putContentName(params.get(key).toString());  // string
            else if(key.equals("type")) event.putContentType(params.get(key).toString());  // string
            else if(key.equals("id")) event.putContentId(params.get(key).toString());      // string
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logShare(event);
    }

    public void logRating(final Dictionary params)
    {
        RatingEvent event = new RatingEvent();
        for(String key: params.get_keys()) {
            if(key.equals("rating")) event.putRating(((Number)params.get(key)).intValue());          // number
            else if(key.equals("name")) event.putContentName(params.get(key).toString());  // string
            else if(key.equals("type")) event.putContentType(params.get(key).toString());  // string
            else if(key.equals("id")) event.putContentId(params.get(key).toString());      // string
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logRating(event);
    }

    public void logSignUp(final Dictionary params)
    {
        SignUpEvent event = new SignUpEvent();
        for(String key: params.get_keys()) {
            if(key.equals("method")) event.putMethod(params.get(key).toString());          // string
            else if(key.equals("success")) event.putSuccess(((Boolean)params.get(key)).booleanValue());   // bool
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logSignUp(event);
    }

    public void logLogin(final Dictionary params)
    {
        LoginEvent event = new LoginEvent();
        for(String key: params.get_keys()) {
            if(key.equals("method")) event.putMethod(params.get(key).toString());          // string
            else if(key.equals("success")) event.putSuccess(((Boolean)params.get(key)).booleanValue());   // bool
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logLogin(event);
    }

    public void logInvite(final Dictionary params)
    {
        InviteEvent event = new InviteEvent();
        for(String key: params.get_keys()) {
            if(key.equals("method")) event.putMethod(params.get(key).toString());          // string
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logInvite(event);
    }

    public void logLevelStart(final Dictionary params)
    {
        LevelStartEvent event = new LevelStartEvent();
        for(String key: params.get_keys()) {
            if(key.equals("name")) event.putLevelName(params.get(key).toString());          // string
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logLevelStart(event);
    }

    public void logLevelEnd(final Dictionary params)
    {
        LevelEndEvent event = new LevelEndEvent();
        for(String key: params.get_keys()) {
            if(key.equals("name"))
                event.putLevelName(params.get(key).toString());          // string
            else if(key.equals("score"))
                event.putScore((Number)params.get(key));        // number
            else if(key.equals("success"))
                event.putSuccess(((Boolean)params.get(key)).booleanValue());    // bool
            else if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());         // string
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // or number
        }
        Answers.getInstance().logLevelEnd(event);
    }

    public void logCustom(final String name, final Dictionary params)
    {
        CustomEvent event = new CustomEvent(name);
        for(String key: params.get_keys()) {
            if(params.get(key) instanceof String)
                event.putCustomAttribute(key, params.get(key).toString());
            else if(params.get(key) instanceof Number)
                event.putCustomAttribute(key, (Number)params.get(key));            // string or number
        }
        Answers.getInstance().logCustom(event);
    }

    @Override protected void onMainActivityResult (int requestCode, int resultCode, Intent data)
    {
    }

}
