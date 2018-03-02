package ir.hezareh.park.DataLoading;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ir.hezareh.park.HomeScreen;
import ir.hezareh.park.models.GalleryModel;
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.NewsDetails;
import ir.hezareh.park.models.sidemenu;


public class OfflineDataLoader {
    public static final String TAG = HomeScreen.class
            .getSimpleName();
    private Context mContext;

    public OfflineDataLoader(Context context) {

        this.mContext = context;
    }

    public void createExternalStoragePath() {
        try {
            File dir = new File(mContext.getExternalFilesDir(null) + "/Park");
            if (!dir.exists()) {
                Boolean isCreated = dir.mkdirs();

                Log.d("Directory Created", String.valueOf(isCreated));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMainJsonToStorage(JSONObject response) {

        try {
            File dirToFile = new File(mContext.getExternalFilesDir(null) + "/Park/MainJson.json");

            Writer output = new BufferedWriter(new FileWriter(dirToFile));
            output.write(response.toString());

            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNewsCategoryToStorage(JSONArray response) {

        try {
            File dirToFile = new File(mContext.getExternalFilesDir(null) + "/Park/NewsCategory.json");

            Writer output = new BufferedWriter(new FileWriter(dirToFile));
            output.write(response.toString());

            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNewsDetailsToStorage(JSONObject response, int ID) {

        try {

            File dirToFile = new File(mContext.getExternalFilesDir(null) + "/Park/NewsDetails" + ID + ".json");

            Writer output = new BufferedWriter(new FileWriter(dirToFile));
            output.write(response.toString());

            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFolderGalleryToStorage(JSONArray response) {
        try {

            File dirToFile = new File(mContext.getExternalFilesDir(null) + "/Park/GalleryFolder.json");

            Writer output = new BufferedWriter(new FileWriter(dirToFile));
            output.write(response.toString());

            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMainMenuToStorage(JSONArray response) {
        try {

            File dirToFile = new File(mContext.getExternalFilesDir(null) + "/Park/MainMenu.json");

            Writer output = new BufferedWriter(new FileWriter(dirToFile));
            output.write(response.toString());

            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<sidemenu> ReadOfflineMainMenu() {
        List<sidemenu> sidemenus = null;
        JsonParser parser = new JsonParser();
        try {
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(mContext.getExternalFilesDir(null) + "/Park/MainMenu.json"));

            Log.d(TAG, jsonArray.toString());

            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<sidemenu>>() {
            }.getType();

            sidemenus = gson.fromJson(jsonArray.toString(), collectionType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sidemenus;
    }

    public List<GalleryModel> ReadOfflineFolderGallery() {
        List<GalleryModel> Gallery = null;
        JsonParser parser = new JsonParser();
        try {
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(mContext.getExternalFilesDir(null) + "/Park/GalleryFolder.json"));

            Log.d(TAG, jsonArray.toString());

            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<GalleryModel>>() {
            }.getType();

            Gallery = gson.fromJson(jsonArray.toString(), collectionType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Gallery;
    }

    public void saveImageGalleryToStorage(JSONArray response, String folderName) {
        try {
            File dirToFile = new File(mContext.getExternalFilesDir(null) + "/Park/imagesInFolderGallery" + folderName + ".json");

            Writer output = new BufferedWriter(new FileWriter(dirToFile));
            output.write(response.toString());

            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GalleryModel> ReadOfflineImageGallery(String folderName) {
        List<GalleryModel> Gallery = null;
        JsonParser parser = new JsonParser();
        try {
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(mContext.getExternalFilesDir(null) + "/Park/imagesInFolderGallery" + folderName + ".json"));

            Log.d(TAG, jsonArray.toString());

            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<GalleryModel>>() {
            }.getType();

            Gallery = gson.fromJson(jsonArray.toString(), collectionType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Gallery;
    }

    public NewsDetails ReadOfflineNewsDetails(int ID) {
        NewsDetails NewsDetails = null;
        JsonParser parser = new JsonParser();
        try {
            JsonObject jsonObject = (JsonObject) parser.parse(new FileReader(mContext.getExternalFilesDir(null) + "/Park/NewsDetails" + ID + ".json"));

            Log.d(TAG, jsonObject.toString());
            Gson gson = new Gson();
            Type collectionType = new TypeToken<NewsDetails>() {
            }.getType();

            NewsDetails = gson.fromJson(jsonObject.toString(), collectionType);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return NewsDetails;
    }

    public List<ModelComponent> ReadOfflineMainJson() {
        List<ModelComponent> components = null;
        JsonParser parser = new JsonParser();
        try {
            JsonObject jsonObject = (JsonObject) parser.parse(new FileReader(mContext.getExternalFilesDir(null) + "/Park/MainJson.json"));

            JsonArray jsonArray = jsonObject.getAsJsonArray("Root");
            Gson gson = new Gson();

            Type collectionType = new TypeToken<Collection<ModelComponent>>() {
            }.getType();

            components = gson.fromJson(jsonArray, collectionType);

            Log.d(TAG, jsonObject.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return components;
    }

    public ArrayList<ModelComponent> ReadOfflineNewsCategory() {
        ArrayList<ModelComponent> newsCategoryList = null;
        JsonParser parser = new JsonParser();
        try {
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(mContext.getExternalFilesDir(null) + "/Park/NewsCategory.json"));


            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<ModelComponent>>() {
            }.getType();
            newsCategoryList = gson.fromJson(jsonArray.toString(), collectionType);

            Log.d(TAG, jsonArray.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newsCategoryList;
    }


}
