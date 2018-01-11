package ir.hezareh.park.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelComponent {

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


    public class Item {
        @SerializedName("ID")
        @Expose
        protected int iD;
        @SerializedName("Image")
        @Expose
        protected Object image;
        @SerializedName("Text")
        @Expose
        protected String text;
        @SerializedName("Date")
        @Expose
        protected Object date;
        @SerializedName("Vote")
        @Expose
        protected int vote;
        @SerializedName("Type")
        @Expose
        protected Object type;
        @SerializedName("Content")
        @Expose
        protected Object content;

        @SerializedName("Functionality")
        @Expose
        protected String functionality;

        @SerializedName("Url")
        @Expose
        protected String url;


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

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Object getDate() {
            return date;
        }

        public void setDate(Object date) {
            this.date = date;
        }

        public int getVote() {
            return vote;
        }

        public void setVote(int vote) {
            this.vote = vote;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    public class ButtonItem extends Item {

    }

    public class GalleryItem extends Item {

    }
}




