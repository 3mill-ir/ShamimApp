package ir.hezareh.park.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelComponent {


    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Items")
    @Expose
    private List<Item> items = null;
    @SerializedName("Component")
    @Expose
    private String component;
    @SerializedName("Question")
    @Expose
    private Object question;
    @SerializedName("Url")
    @Expose
    private Object url;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Object getQuestion() {
        return question;
    }

    public void setQuestion(Object question) {
        this.question = question;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }


}




