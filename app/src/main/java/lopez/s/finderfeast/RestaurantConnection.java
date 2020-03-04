package lopez.s.finderfeast;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RestaurantConnection {

    private static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";
    private static String keyCode = "d3b965d17f5d9cdd0c08e4d1d6ed47e2";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static JSONObject result;
    private static JSONArray resultArr;


    public static JSONObject get(String url, RequestParams params) {
        client.addHeader("user-key", keyCode);
        client.get(getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] header, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                result = response;
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        return result;
    }
    public static JSONArray getArr(String url, RequestParams params) {
        client.addHeader("user-key", keyCode);
        client.get(getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] header, JSONArray response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                resultArr = response;
//                try {
//                    JSONObject serverResp = new JSONObject(response.toString());
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
            }
        });
        return resultArr;
    }

    public static JSONArray getNearby(double lat, double lon, double radius) {
        RequestParams rp = new RequestParams();
        if (radius == 0) {
            radius = 1000;
        }
        rp.add("lat", Double.toString(lat));
        rp.add("lon", Double.toString(lon));
        rp.add("radius", Double.toString(radius));
        try {
            result = get("/search", rp);
            resultArr = result.getJSONArray("restaurants");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultArr;
    }

    public static JSONObject getCuisines() {
        return get("/cuisines", new RequestParams());
    }

    public static JSONObject getNearby(double lat, double lon, double radius, String[] rawCuisineIDs) {
        String cuisineIDs = "";
        for(String cuisineID : rawCuisineIDs) {
            cuisineIDs += cuisineID + ",";
        }
        RequestParams rp = new RequestParams();
        rp.add("lat", Double.toString(lat));
        rp.add("lon", Double.toString(lon));
        rp.add("radius", Double.toString(radius));
        rp.add("cuisines", cuisineIDs);
        return get("/search", rp);
    }

    public static JSONObject getMenu(String id) {
        RequestParams rp = new RequestParams();
        rp.add("res_id", id);
        return get("/dailymenu", rp);
    }

    public static JSONObject getReviews(String id) {
        RequestParams rp = new RequestParams();
        rp.add("res_id", id);
        return get("/reviews", rp);
    }

    public static JSONObject getRestaurantInfo(String id) {
        RequestParams rp = new RequestParams();
        rp.add("res_id", id);
        return get("/restaurant", rp);
    }

    public static JSONObject getCategories() {
        return get("/categories", new RequestParams());
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}