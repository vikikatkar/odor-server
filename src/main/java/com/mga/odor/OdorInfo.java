package com.mga.odor;

public class OdorInfo {
    
    public OdorInfo(String odorCategory, String odorDescription, String customDescription) {
        super();
        this.odorCategory = odorCategory;
        this.odorDescription = odorDescription;
        this.customDescription = customDescription;
    }
    public String getOdorCategory() {
        return odorCategory;
    }
    public void setOdorCategory(String odorCategory) {
        this.odorCategory = odorCategory;
    }
    public String getOdorDescription() {
        return odorDescription;
    }
    public void setOdorDescription(String odorDescription) {
        this.odorDescription = odorDescription;
    }
    public String getCustomDescription() {
        return customDescription;
    }
    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }
    String odorCategory;
    String odorDescription;
    String customDescription;
}
