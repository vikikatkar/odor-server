package com.mga.odor;

public class UserStats {

    public UserStats(int emailHash) {
        this.emailHash = emailHash;
    }
    
    int emailHash;
    int numberOfReports;
    int numberOfReportsVerified;
    int numberOfVerifications;
    int numberOfReportsInLast30Days;
    int activeDaysInLast30Days;
    boolean isVerifier;
    public int getEmailHash() {
        return emailHash;
    }
    public void setEmailHash(int emailHash) {
        this.emailHash = emailHash;
    }
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
    public int getNumberOfVerifications() {
        return numberOfVerifications;
    }
    public void setNumberOfVerifications(int numberOfVerifications) {
        this.numberOfVerifications = numberOfVerifications;
    }
    public int getNumberOfReportsInLast30Days() {
        return numberOfReportsInLast30Days;
    }
    public void setNumberOfReportsInLast30Days(int numberOfReportsInLast30Days) {
        this.numberOfReportsInLast30Days = numberOfReportsInLast30Days;
    }
    public int getActiveDaysInLast30Days() {
        return activeDaysInLast30Days;
    }
    public void setActiveDaysInLast30Days(int activeDaysInLast30Days) {
        this.activeDaysInLast30Days = activeDaysInLast30Days;
    }
    public boolean isVerifier() {
        return isVerifier;
    }
    public void setVerifier(boolean isVerifier) {
        this.isVerifier = isVerifier;
    }

    
}
