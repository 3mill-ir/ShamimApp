package ir.hezareh.park;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menu {

    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Menu1")
    @Expose
    private List<Menu> menu1 = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Menu> getMenu1() {
        return menu1;
    }

    public void setMenu1(List<Menu> menu1) {
        this.menu1 = menu1;
    }

    @Override
    public String toString() {
        return "Text:" + this.text + "\n"
                ;
    }
}
