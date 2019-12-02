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
    private @Id @GeneratedValue Long id;
    int reportId;

    @JsonFormat(pattern="MM-dd-yyyy HH:mm")
    Date dateTime;
    String emailHash;
    //Location loc;
    //OdorInfo    odorInfo;
    
    @Override
    public int hashCode() {
        final int prime = 13;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
        return result;
    }

    //Default constructor needed
    public Report() {
    }

    public Report(String emailHash /*Location loc, OdorInfo odorInfo*/) {
        super();
        this.dateTime = new Date();//Now
        this.emailHash = emailHash;
        //this.loc = loc;
        //this.odorInfo = odorInfo;
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!dateTime.equals(other.dateTime))
            return false;
        
//        if( odorInfo == null ) {
//            if( other.odorInfo != null )
//                return false;
//        }else if (! odorInfo.equals(odorInfo))
//            return false;
        return true;
    }

}
