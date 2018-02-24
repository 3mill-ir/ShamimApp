package ir.hezareh.park.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class GalleryModel implements Serializable {

    @SerializedName("FolderName")
    @Expose
    private String folderName;

    @SerializedName("ExistingImagesCount")
    @Expose
    private int imagesCount;

    @SerializedName("Image")
    @Expose
    private String image;

    public GalleryModel() {

    }

    public GalleryModel(String folderName, int imagesCount, String image) {
        this.folderName = folderName;
        this.imagesCount = imagesCount;
        this.image = image;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

