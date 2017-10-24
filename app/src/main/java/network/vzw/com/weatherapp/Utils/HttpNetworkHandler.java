package network.vzw.com.weatherapp.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by kukresa on 10/23/2017.
 */

public class HttpNetworkHandler {

    private static final String TAG = HttpNetworkHandler.class.getSimpleName();
    private static HttpNetworkHandler mNetworkRequestor;
    private Context mContext;
    private WeakReference<Context> mContextReference;
    private RequestQueue mRequestQueue;
     private static String SERVER_URL = "https://mobile.vzw.com/mvmrc/mvm/";
     private int CUSTOM_DEFAULT_MAX_RETRIES =1;
    private int MAXIMUM_TIME_OUT = 60000;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private HttpNetworkHandler(Context mContext) {

        mContextReference = new WeakReference<Context>(mContext);
        this.mContext = mContextReference.get();
        mRequestQueue = Volley.newRequestQueue(this.mContext);

    }


      public static synchronized HttpNetworkHandler getInstance(Context mContext){
        if(mNetworkRequestor == null)
            mNetworkRequestor = new HttpNetworkHandler(mContext);
        if(mNetworkRequestor.mContextReference.get() == null)
            mNetworkRequestor = new HttpNetworkHandler(mContext);
        return mNetworkRequestor;
    }

    /*public void requestContentFromServer(int method, final String payload, String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener, boolean shouldCacheResponse){
        Log.d(TAG, "requestContentFromServer>>>>>>>>>>>");
        Log.d(TAG, "Payload:" + payload);

         Log.d(TAG, "finalPayload:"+payload);
         Log.d(TAG,"url for request:"+url);
        String encodedURL = url;
        StringRequest stringRequest = new StringRequest(method , encodedURL, responseListener,errorListener){

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return new HashMap<String, String>();

            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Log.d(TAG, "in GetBody>>>>>>>>>>");
                return payload.getBytes();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(MAXIMUM_TIME_OUT, CUSTOM_DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(shouldCacheResponse);
        mRequestQueue.add(stringRequest);
        //new Thread(runnable).start();
    }*/

    public void requests(int method, final String payload, String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener, boolean shouldCacheResponse){
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,responseListener,errorListener);
        mRequestQueue.add(getRequest);

    }

}