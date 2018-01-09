package ir.hezareh.park.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rf on 03/01/2018.
 */

public class GalleryModel {

    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Folder")
    @Expose
    private String folder;

    @SerializedName("Item")
    @Expose
    private List<GalleryModel.Item> item = null;


    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<GalleryModel.Item> getItem() {
        return item;
    }

    public void setItem(List<GalleryModel.Item> item) {
        this.item = item;
    }


    public class Item {

        @SerializedName("ID")
        @Expose
        private int iD;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("Text")
        @Expose
        private Object text;

        public int getID() {
            return iD;
        }

        public void setID(int iD) {
            this.iD = iD;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getText() {
            return text;
        }

        public void setText(Object text) {
            this.text = text;
        }

    }

}

