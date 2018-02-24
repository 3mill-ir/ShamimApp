package ir.hezareh.park.DataLoading;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.hezareh.park.App;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.CompanyList;
import ir.hezareh.park.models.GalleryModel;
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.NewsDetails;
import ir.hezareh.park.models.sidemenu;


public class networking {

    public static final String TAG = networking.class
            .getSimpleName();
    private Context mContext;

    public networking(Context context) {
        mContext = context;
    }

    public void postComment(final int ID, final String comment, final PostCommentResponseListener responseListener) {

        responseListener.requestStarted();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://parkapi.3mill.ir/api/Comment/PostComment",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        responseListener.requestCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        responseListener.requestEndedWithError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Text", comment);
                params.put("F_PostsID", String.valueOf(ID));
                return params;
            }

        };

        App.getInstance().addToRequestQueue(stringRequest);
    }

    public void postLike(final int ID, final PostLikeResponseListener likeResponseListener) {

        likeResponseListener.requestStarted();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://parkapi.3mill.ir/api/Post/GetLike?id=" + Utils.URL_encode(String.valueOf(ID)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        likeResponseListener.requestCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        likeResponseListener.requestEndedWithError(error);
                    }
                });


        App.getInstance().addToRequestQueue(stringRequest);

    }

    public void postDislike(final int ID, final PostDislikeResponseListener responseListener) {

        responseListener.requestStarted();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://parkapi.3mill.ir/api/Post/GetDisLike?id=" + Utils.URL_encode(String.valueOf(ID)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        responseListener.requestCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        responseListener.requestEndedWithError(error);
                    }
                });
        App.getInstance().addToRequestQueue(stringRequest);

    }

    public void postPoll(final int ID, final PostPollListener postPollListener) {

        postPollListener.requestStarted();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://parkapi.3mill.ir/api/PollLog/AddPollingUser?username=admin&IP=0&ansId=" + Utils.URL_encode(String.valueOf(ID)) + "&device=Android",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        postPollListener.requestCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        postPollListener.requestEndedWithError(error);
                    }
                });
        App.getInstance().addToRequestQueue(stringRequest);
    }

    public void getNewsCategory(final NewsCategoryResponseListener newsCategoryResponseListener) {

        newsCategoryResponseListener.requestStarted();
        JsonArrayRequest req = new JsonArrayRequest("http://parkapi.3mill.ir/api/android/getNewsList?username=admin&id=5",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, response.toString());
                        try {
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<Collection<ModelComponent>>() {
                            }.getType();
                            ArrayList<ModelComponent> newsCategoryList = gson.fromJson(response.toString(), collectionType);
                            //Log.d(TAG, newsCategoryList.get(0).getCategory().toString());
                            newsCategoryResponseListener.requestCompleted(newsCategoryList);


                            new OfflineDataLoader(mContext).saveNewsCategoryToStorage(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                newsCategoryResponseListener.requestEndedWithError(error);
            }
        });

        // Adding request to request queue
        req.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(req);
    }

    public void getNewsDetails(final NewsDetailsResponseListener newsDetailsResponseListener, final String URL) {

        newsDetailsResponseListener.requestStarted();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d(TAG, response.toString());
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<NewsDetails>() {
                    }.getType();

                    NewsDetails newsDetails = gson.fromJson(response.toString(), collectionType);

                    newsDetailsResponseListener.requestCompleted(newsDetails);
                    new OfflineDataLoader(mContext).saveNewsDetailsToStorage(response, newsDetails.getID());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                newsDetailsResponseListener.requestEndedWithError(error);
            }
        });
        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void updateCheck(final UpdateCheckResponseListener updateCheckResponseListener) {
        updateCheckResponseListener.requestStarted();

        String url = "http://3mill.ir/download/appcheck?path=mobile/android/park&secretKey=3mill186";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String availableVersion = response.replaceAll("^\"|\"$", "");
                        Log.i("Response is ", availableVersion);
                        updateCheckResponseListener.requestCompleted(availableVersion);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "That didn't work!");
                updateCheckResponseListener.requestEndedWithError(error);
            }
        });
        stringRequest.setShouldCache(false);

        App.getInstance().addToRequestQueue(stringRequest);
    }

    public void getMainJson(final MainJsonResponseListener mainJsonResponseListener) {
        mainJsonResponseListener.requestStarted();

        String URL = "http://parkapi.3mill.ir/api/Android/getFirstPage?username=admin";

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d(TAG, response.toString());
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<ModelComponent>>() {
                    }.getType();

                    List<ModelComponent> components = gson.fromJson(jsonArray.toString(), collectionType);

                    //Log.d(TAG, components.get(5).getQuestion() + "");
                    mainJsonResponseListener.requestCompleted(components);

                    new OfflineDataLoader(mContext).saveMainJsonToStorage(response);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                mainJsonResponseListener.requestEndedWithError(error);

            }
        });

        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);

    }

    public void getMainSideMenu(final SideMenuResponseListener sideMenuResponseListener) {

        sideMenuResponseListener.requestStarted();
        JsonArrayRequest req = new JsonArrayRequest("http://parkapi.3mill.ir/api/Menues/GetMenuAndroid?username=admin",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            Gson gson = new Gson();

                            Type collectionType = new TypeToken<Collection<sidemenu>>() {
                            }.getType();
                            ArrayList<sidemenu> sidemenuList = gson.fromJson(response.toString(), collectionType);
                            sideMenuResponseListener.requestCompleted(sidemenuList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                sideMenuResponseListener.requestEndedWithError(error);
            }
        });

        // Adding request to request queue
        req.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(req);
    }

    public void getCompanyList(final CompanyListResponseListener companyListResponseListener) {

        companyListResponseListener.requestStarted();
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/companylist", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                List<CompanyList> CompanyList;
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<CompanyList>>() {
                    }.getType();

                    CompanyList = gson.fromJson(jsonArray.toString(), collectionType);

                    //Log.d(TAG, CompanyList.get(0).getCompanyList().get(1).getName() + "");

                    companyListResponseListener.requestCompleted(CompanyList);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                companyListResponseListener.requestEndedWithError(error);
            }
        });


        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void getFolderGallery(final FolderGalleryResponseListener folderGalleryResponseListener) {

        folderGalleryResponseListener.requestStarted();
        JsonArrayRequest req = new JsonArrayRequest("http://parkapi.3mill.ir/api/android/GetGalleryFolder?username=admin",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d(TAG, response.toString());

                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<Collection<GalleryModel>>() {
                            }.getType();

                            List<GalleryModel> Gallery = gson.fromJson(response.toString(), collectionType);
                            //Log.d(TAG, Gallery.get(0).getImagesCount() + "");
                            folderGalleryResponseListener.requestCompleted(Gallery);
                            new OfflineDataLoader(mContext).saveFolderGalleryToStorage(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                folderGalleryResponseListener.requestEndedWithError(error);
            }
        });

        // Adding request to request queue
        req.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(req);


    }

    public void getImagesGallery(final ImagesGalleryResponseListener imagesGalleryResponseListener, final String folderName) {

        imagesGalleryResponseListener.requestStarted();
        JsonArrayRequest req = new JsonArrayRequest("http://parkapi.3mill.ir/api/android/GetGalleryImage?username=admin&Foldername=" + Utils.URL_encode(folderName),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d(TAG, response.toString());

                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<Collection<GalleryModel>>() {
                            }.getType();

                            List<GalleryModel> Gallery = gson.fromJson(response.toString(), collectionType);
                            //Log.d(TAG, Gallery.get(0).getImagesCount() + "");
                            imagesGalleryResponseListener.requestCompleted(Gallery);
                            new OfflineDataLoader(mContext).saveImageGalleryToStorage(response, folderName);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                imagesGalleryResponseListener.requestEndedWithError(error);
            }
        });

        // Adding request to request queue
        req.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(req);

    }


    public interface PostCommentResponseListener {
        void requestStarted();

        void requestCompleted(String response);

        void requestEndedWithError(VolleyError error);
    }

    public interface PostLikeResponseListener {
        void requestStarted();

        void requestCompleted(String response);

        void requestEndedWithError(VolleyError error);
    }

    public interface PostDislikeResponseListener {
        void requestStarted();

        void requestCompleted(String response);

        void requestEndedWithError(VolleyError error);
    }

    public interface PostPollListener {
        void requestStarted();

        void requestCompleted(String response);

        void requestEndedWithError(VolleyError error);
    }

    public interface NewsCategoryResponseListener {
        void requestStarted();

        void requestCompleted(ArrayList<ModelComponent> response);

        void requestEndedWithError(VolleyError error);
    }

    public interface NewsDetailsResponseListener {
        void requestStarted();

        void requestCompleted(NewsDetails newsDetails);

        void requestEndedWithError(VolleyError error);
    }

    public interface UpdateCheckResponseListener {
        void requestStarted();

        void requestCompleted(String version);

        void requestEndedWithError(VolleyError error);
    }

    public interface MainJsonResponseListener {
        void requestStarted();

        void requestCompleted(List<ModelComponent> modelComponents);

        void requestEndedWithError(VolleyError error);
    }

    public interface SideMenuResponseListener {
        void requestStarted();

        void requestCompleted(ArrayList<sidemenu> sidemenus);

        void requestEndedWithError(VolleyError error);
    }

    public interface CompanyListResponseListener {
        void requestStarted();

        void requestCompleted(List<CompanyList> companyLists);

        void requestEndedWithError(VolleyError error);
    }

    public interface ImagesGalleryResponseListener {
        void requestStarted();

        void requestCompleted(List<GalleryModel> Gallery);

        void requestEndedWithError(VolleyError error);
    }

    public interface FolderGalleryResponseListener {
        void requestStarted();

        void requestCompleted(List<GalleryModel> Gallery);

        void requestEndedWithError(VolleyError error);
    }

}
