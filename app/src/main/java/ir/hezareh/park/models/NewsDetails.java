package ir.hezareh.park.models;

/**
 * Created by rf on 10/01/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewsDetails {

    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Tittle")
    @Expose
    private String tittle;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Detail")
    @Expose
    private String detail;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("isDeleted")
    @Expose
    private boolean isDeleted;
    @SerializedName("Status")
    @Expose
    private boolean status;
    @SerializedName("F_UserID")
    @Expose
    private String fUserID;
    @SerializedName("F_MenuID")
    @Expose
    private int fMenuID;
    @SerializedName("NumberOfVisitors")
    @Expose
    private int numberOfVisitors;
    @SerializedName("NumberOfComments")
    @Expose
    private int numberOfComments;
    @SerializedName("NumberOfLikes")
    @Expose
    private int numberOfLikes;
    @SerializedName("NumberOfDislikes")
    @Expose
    private int numberOfDislikes;
    @SerializedName("CreatedOnUTC")
    @Expose
    private String createdOnUTC;
    @SerializedName("MetaDescription")
    @Expose
    private String metaDescription;
    @SerializedName("MetaTittle")
    @Expose
    private String metaTittle;
    @SerializedName("MetaSeoName")
    @Expose
    private String metaSeoName;
    @SerializedName("ImageAlt")
    @Expose
    private String imageAlt;
    @SerializedName("AllowComment")
    @Expose
    private boolean allowComment;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Comments1")
    @Expose
    private Object comments1;
    @SerializedName("Like")
    @Expose
    private String like;
    @SerializedName("Dislike")
    @Expose
    private String dislike;
    @SerializedName("addComment")
    @Expose
    private String addComment;
    @SerializedName("RelatedTopics")
    @Expose
    private RelatedTopics relatedTopics;

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFUserID() {
        return fUserID;
    }

    public void setFUserID(String fUserID) {
        this.fUserID = fUserID;
    }

    public int getFMenuID() {
        return fMenuID;
    }

    public void setFMenuID(int fMenuID) {
        this.fMenuID = fMenuID;
    }

    public int getNumberOfVisitors() {
        return numberOfVisitors;
    }

    public void setNumberOfVisitors(int numberOfVisitors) {
        this.numberOfVisitors = numberOfVisitors;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public int getNumberOfDislikes() {
        return numberOfDislikes;
    }

    public void setNumberOfDislikes(int numberOfDislikes) {
        this.numberOfDislikes = numberOfDislikes;
    }

    public String getCreatedOnUTC() {
        return createdOnUTC;
    }

    public void setCreatedOnUTC(String createdOnUTC) {
        this.createdOnUTC = createdOnUTC;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaTittle() {
        return metaTittle;
    }

    public void setMetaTittle(String metaTittle) {
        this.metaTittle = metaTittle;
    }

    public String getMetaSeoName() {
        return metaSeoName;
    }

    public void setMetaSeoName(String metaSeoName) {
        this.metaSeoName = metaSeoName;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public boolean isAllowComment() {
        return allowComment;
    }

    public void setAllowComment(boolean allowComment) {
        this.allowComment = allowComment;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getComments1() {
        return comments1;
    }

    public void setComments1(Object comments1) {
        this.comments1 = comments1;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

    public String getAddComment() {
        return addComment;
    }

    public void setAddComment(String addComment) {
        this.addComment = addComment;
    }

    public RelatedTopics getRelatedTopics() {
        return relatedTopics;
    }

    public void setRelatedTopics(RelatedTopics relatedTopics) {
        this.relatedTopics = relatedTopics;
    }

    public class RelatedTopics extends ModelComponent {

    }

}

