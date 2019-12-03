package com.mga.odor;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Verifier{
    private @Id int emailHash;

    int numberOfVerifications;

    //Default constructor needed
    public Verifier() {
    }

    public Verifier(int emailHash, int numberOfVerifications) {
        super();
        this.emailHash = emailHash;
        this.numberOfVerifications = numberOfVerifications;
    }

}
