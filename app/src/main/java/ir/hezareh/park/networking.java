package ir.hezareh.park;

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

import ir.hezareh.park.models.CompanyList;
import ir.hezareh.park.models.GalleryModel;
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.NewsDetails;
import ir.hezareh.park.models.sidemenu;

import static ir.hezareh.park.NewsCategory.TAG;


public class networking {

    public void postComment(final int ID, final String comment, final PostCommentResponseListener responseListener) {

        responseListener.requestStarted();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://parkapi.3mill.ir/api/Comment/PostComment",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseListener.requestCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

    public void getNewsCategory(final NewsCategoryResponseListener newsCategoryResponseListener) {

        newsCategoryResponseListener.requestStarted();
        JsonArrayRequest req = new JsonArrayRequest("http://arefnaghshin.ir/news",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ArrayList<ModelComponent> newsCategoryList = null;

                        Log.d(TAG, response.toString());
                        try {
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<Collection<ModelComponent>>() {
                            }.getType();
                            newsCategoryList = gson.fromJson(new String(response.toString().getBytes("ISO-8859-1")), collectionType);
                            //newsCategoryList = gson.fromJson(response.toString(), collectionType);
                            //Log.d(TAG, newsCategoryList.get(0).getCategory().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        newsCategoryResponseListener.requestCompleted(newsCategoryList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newsCategoryResponseListener.requestEndedWithError(error);
            }
        });

        // Adding request to request queue
        req.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(req);
    }

    public void getNewsDetails(final NewsDetailsResponseListener newsDetailsResponseListener) {

        newsDetailsResponseListener.requestStarted();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/newsdetails", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                NewsDetails newsDetails = null;

                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<NewsDetails>() {
                    }.getType();
                    newsDetails = gson.fromJson(new String(response.toString().getBytes("ISO-8859-1")), collectionType);
                    //newsDetails = gson.fromJson(response.toString(), collectionType);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                newsDetailsResponseListener.requestCompleted(newsDetails);

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
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

    public void getMainJson(String URL, final MainJsonResponseListener mainJsonResponseListener) {
        mainJsonResponseListener.requestStarted();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                List<ModelComponent> components = null;
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<ModelComponent>>() {
                    }.getType();
                    components = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);

                    //for (ModelComponent component : components) {
                    //for (ModelComponent.Item item : component.getItems()) {

                    Log.d(TAG, components.get(5).getQuestion() + "");
                    //}
                    //}


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mainJsonResponseListener.requestCompleted(components);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
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
                    ArrayList<sidemenu> sidemenuList = null;

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            Gson gson = new Gson();

                            Type collectionType = new TypeToken<Collection<sidemenu>>() {
                            }.getType();
                            sidemenuList = gson.fromJson(response.toString(), collectionType);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sideMenuResponseListener.requestCompleted(sidemenuList);
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

                    CompanyList = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);

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
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                companyListResponseListener.requestEndedWithError(error);
            }
        });


        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void getFolderGallery(final FolderGalleryResponseListener folderGalleryResponseListener) {

        folderGalleryResponseListener.requestStarted();
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://137.shmim.ir/piranshahr/gallery/AndroidGalleryFolder", null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d("tag", jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<GalleryModel>>() {
                    }.getType();

                    //List<GalleryModel> Gallery = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);

                    List<GalleryModel> Gallery = gson.fromJson(jsonArray.toString(), collectionType);

                    Log.d("tag", Gallery.get(0).getImagesCount() + "");
                    folderGalleryResponseListener.requestCompleted(Gallery);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                folderGalleryResponseListener.requestEndedWithError(error);

            }
        });

        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);

    }

    public void getImagesGallery(String folderName, final ImagesGalleryResponseListener imagesGalleryResponseListener) {

        imagesGalleryResponseListener.requestStarted();


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://137.shmim.ir/piranshahr/gallery/AndroidGalleryImage?FolderName=" + Utils.URL_encode(folderName), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d("tag", jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<GalleryModel>>() {
                    }.getType();

                    //List<GalleryModel> Gallery = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);

                    List<GalleryModel> Gallery = gson.fromJson(jsonArray.toString(), collectionType);

                    //Log.d("tag", Gallery.get(0).getImagesCount() + "");

                    imagesGalleryResponseListener.requestCompleted(Gallery);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                imagesGalleryResponseListener.requestEndedWithError(error);
            }
        });

        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);

    }


    public interface PostCommentResponseListener {
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
