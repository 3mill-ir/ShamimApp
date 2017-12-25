package ir.hezareh.park.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rf on 23/12/2017.
 */

public class Item {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Url")
    @Expose
    private Object url;
    @SerializedName("Date")
    @Expose
    private Object date;
    @SerializedName("Vote")
    @Expose
    private int vote;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
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

}
