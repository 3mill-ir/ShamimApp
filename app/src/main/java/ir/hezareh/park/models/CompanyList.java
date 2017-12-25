package ir.hezareh.park.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyList {

    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("CompanyList")
    @Expose
    private List<CompanyInfo> companyList = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CompanyInfo> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<CompanyInfo> companyList) {
        this.companyList = companyList;
    }


    public class CompanyInfo {
        @SerializedName("Logo")
        @Expose
        private String logo;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("CEO")
        @Expose
        private String cEO;
        @SerializedName("Website")
        @Expose
        private String website;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCEO() {
            return cEO;
        }

        public void setCEO(String cEO) {
            this.cEO = cEO;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }

}

