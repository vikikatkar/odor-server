package com.mga.odor;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Reporter{
    private @Id int emailHash;



    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    public int getNumberOfReportsVerified() {
        return numberOfReportsVerified;
    }

    public void setNumberOfReportsVerified(int numberOfReportsVerified) {
        this.numberOfReportsVerified = numberOfReportsVerified;
    }

    int numberOfReports;
    int numberOfReportsVerified;

    //Default constructor needed
    public Reporter() {
    }

    public Reporter(int emailHash, int numberOfReports, int numberOfReportsVerified) {
        super();
        this.emailHash = emailHash;
        this.numberOfReports = numberOfReports;
        this.numberOfReportsVerified = numberOfReportsVerified;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + emailHash;
        result = prime * result + numberOfReports;
        result = prime * result + numberOfReportsVerified;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reporter other = (Reporter) obj;
        if (emailHash != other.emailHash)
            return false;
        if (numberOfReports != other.numberOfReports)
            return false;
        if (numberOfReportsVerified != other.numberOfReportsVerified)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reporter [emailHash=" + emailHash + ", numberOfReports=" + numberOfReports
                + ", numberOfReportsVerified=" + numberOfReportsVerified + "]";
    }
    

}
