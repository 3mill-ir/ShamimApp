package ir.hezareh.park.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sidemenu {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Weight")
    @Expose
    private double weight;
    @SerializedName("Status")
    @Expose
    private boolean status;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("F_MenuID")
    @Expose
    private Object fMenuID;
    @SerializedName("F_UserID")
    @Expose
    private String fUserID;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("MetaKeywords")
    @Expose
    private String metaKeywords;
    @SerializedName("MetaDescription")
    @Expose
    private String metaDescription;
    @SerializedName("MetaTittle")
    @Expose
    private String metaTittle;
    @SerializedName("MetaSeoName")
    @Expose
    private String metaSeoName;
    @SerializedName("DisplayInFooter")
    @Expose
    private boolean displayInFooter;
    @SerializedName("DisplayInSidebar")
    @Expose
    private boolean displayInSidebar;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getFMenuID() {
        return fMenuID;
    }

    public void setFMenuID(Object fMenuID) {
        this.fMenuID = fMenuID;
    }

    public String getFUserID() {
        return fUserID;
    }

    public void setFUserID(String fUserID) {
        this.fUserID = fUserID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
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

    public boolean isDisplayInFooter() {
        return displayInFooter;
    }

    public void setDisplayInFooter(boolean displayInFooter) {
        this.displayInFooter = displayInFooter;
    }

    public boolean isDisplayInSidebar() {
        return displayInSidebar;
    }

    public void setDisplayInSidebar(boolean displayInSidebar) {
        this.displayInSidebar = displayInSidebar;
    }
}
