package com.mga.odor;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class VerifiedReport{
    private @Id Long reportId;

    int reporterId;
    int verifierId;
    @JsonFormat(pattern="MM-dd-yyyy HH:mm")
    Date dateTime;
    double lat;
    double lng;
    String odorCategory;
    String odorDescription;
    String customDescription;
    boolean verified;
    
    //Default constructor needed
    public VerifiedReport() {
    }

    public VerifiedReport(Long reportId, int reporterId, int verifierId, Date dateTime, double lat, double lng,
            String odorCategory, String odorDescription, String customDescription, boolean verified) {
        super();
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.verifierId = verifierId;
        this.dateTime = dateTime;
        this.lat = lat;
        this.lng = lng;
        this.odorCategory = odorCategory;
        this.odorDescription = odorDescription;
        this.customDescription = customDescription;
        this.verified = verified;
    }


}
