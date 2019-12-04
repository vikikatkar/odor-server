package com.mga.odor;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Report{
    private @Id @GeneratedValue Long reportId;

    @JsonFormat(pattern="MM-dd-yyyy HH:mm")
    Date dateTime;
    int emailHash;
    double lat;
    double lng;
    String odorCategory;
    String odorDescription;
    String customDescription;
    boolean isVerified;
    
    @Override
    public int hashCode() {
        final int prime = 13;
        int result = 1;
        result = prime * result + ((reportId == null) ? 0 : reportId.hashCode());
        result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
        return result;
    }

    //Default constructor needed
    public Report() {
    }

    public Report(Date dateTime, int emailHash, double lat, double lng, String odorCategory, String odorDescription,
            String customDescription) {
        super();
        this.dateTime = dateTime;
        this.emailHash = emailHash;
        this.lat = lat;
        this.lng = lng;
        this.odorCategory = odorCategory;
        this.odorDescription = odorDescription;
        this.customDescription = customDescription;
    }
    
    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Report other = (Report) obj;
        if (reportId == null) {
            if (other.reportId != null)
                return false;
        } else if (!reportId.equals(other.reportId))
            return false;
        if (dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!dateTime.equals(other.dateTime))
            return false;
        
        return true;
    }


}
