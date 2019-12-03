package com.mga.odor;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Reporter{
    private @Id int emailHash;

    int numberOfReporters;
    int numberOfReportersVerified;

    //Default constructor needed
    public Reporter() {
    }

    public Reporter(int emailHash, int numberOfReporters, int numberOfReportersVerified) {
        super();
        this.emailHash = emailHash;
        this.numberOfReporters = numberOfReporters;
        this.numberOfReportersVerified = numberOfReportersVerified;
    }

}
