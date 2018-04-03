package ir.hezareh.park.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelComponent implements Serializable {

    @SerializedName("ID")
    @Expose
    private int iD;

    @SerializedName("Component")
    @Expose
    private String component;

    @SerializedName("Functionality")
    @Expose
    private String functionality;
    @Nullable
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("Item")
    @Expose
    private List<Item> item = null;
    @SerializedName("ButtonItem")
    @Expose
    private List<ButtonItem> buttonItem = null;
    @SerializedName("GalleryItem")
    @Expose
    private List<GalleryItem> galleryItem = null;
    @SerializedName("Category")
    @Expose
    private Object category = null;

    public String getFunctionality() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public List<ButtonItem> getButtonItem() {
        return buttonItem;
    }

    public void setButtonItem(List<ButtonItem> buttonItem) {
        this.buttonItem = buttonItem;
    }

    public List<GalleryItem> getGalleryItem() {
        return galleryItem;
    }

    public void setGalleryItem(List<GalleryItem> galleryItem) {
        this.galleryItem = galleryItem;
    }

    @Override
    public String toString() {
        return "ID:" + iD + ", Component:" + component;
    }

    public class ButtonItem extends Item {

    }

    public class GalleryItem extends Item {

    }
}




