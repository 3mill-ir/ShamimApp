package ir.hezareh.park.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {
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
    @SerializedName("Likes")
    @Expose
    protected String likes;
    @SerializedName("Dislikes")
    @Expose
    protected String dislikes;
    @SerializedName("Comment")
    @Expose
    protected String comment;
    @SerializedName("F_MenuID")
    @Expose
    private int f_MenuID;

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

    public int getF_MenuID() {
        return f_MenuID;
    }

    public void setF_MenuID(int f_MenuID) {
        this.f_MenuID = f_MenuID;
    }

}