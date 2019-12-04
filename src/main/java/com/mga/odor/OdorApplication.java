package com.mga.odor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
// Add imports
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;



@SpringBootApplication
@RestController
public class OdorApplication {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OdorApplication.class);
    private final ReportRepository reportRepo;
    private final VerifiedReportRepository verifiedReportRepo;
    private final ReporterRepository reporterRepo;
    private final VerifierRepository verifierRepo;
    final Calendar cal = Calendar.getInstance();


    OdorApplication(ReportRepository repository, VerifiedReportRepository verifiedReportRepo,
            ReporterRepository reporterRepo, VerifierRepository verifierRepo) {
        this.reportRepo = repository;
        this.verifiedReportRepo = verifiedReportRepo;
        
        this.reporterRepo = reporterRepo;
        this.verifierRepo = verifierRepo;
        
        
        Report r;
        List<Report> lr = new ArrayList<>();
        
        r = new Report(new Date(),"joy_1@gmail.com".hashCode(),37.43323, -121.89957, "Nose Feel","Pungent","");
        updateReporter(r.emailHash,1,0);
        lr.add(r);
        r = new Report(new Date(),"joy_2@gmail.com".hashCode(),37.43334, -121.89930, "Nose Feel","Irritating","");
        updateReporter(r.emailHash,1,0);
        lr.add(r);
        r = new Report(new Date(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89940, "Solvent","Tar","");
        updateReporter(r.emailHash,1,0);
        lr.add(r);
        r = new Report(new Date(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89800, "Custom Category","Burning Tire","");
        updateReporter(r.emailHash,1,0);
        lr.add(r);

        //Yesterday
        r = new Report(yesterday(),"joy_1@gmail.com".hashCode(),37.43323, -121.89957, "Nose Feel","Pungent","");
        updateReporter(r.emailHash,1,0);
        lr.add(r);
        r = new Report(yesterday(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89940, "Solvent","Tar","");
        updateReporter(r.emailHash,1,0);
        lr.add(r);
        
        for( int i = 1 ;i < 31 ; i++) {
            cal.add(Calendar.DATE, -1);
            log.info("Loading :: "+cal.getTime());
            r = new Report(cal.getTime(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89800, "Nose Feel","Irritating","");
            lr.add(r);
            updateReporter(r.emailHash,1,0);
        }
        reportRepo.saveAll(lr);
        reportRepo.flush();
        List<Report> allReports = reportRepo.findAll();
        
        for( Report report : allReports) {
            log.info("OdorApplication : Adding activity {}", report.toString());
        }
        
    }
    private Date yesterday() {
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

	public static void main(String[] args) {
		SpringApplication.run(OdorApplication.class, args);
		
	}

  void updateReporter(int emailHash, int incrementReportsBy, int incrementVerifiedCount) {
      Reporter reporter;
      if( reporterRepo.existsById(emailHash) ) {
          reporter = reporterRepo.getOne(emailHash);
          reporter.numberOfReports +=incrementReportsBy;
          reporter.numberOfReportsVerified +=incrementVerifiedCount;
      }else {
          reporter = new Reporter(emailHash,1,0);
      }
      
      reporterRepo.save(reporter);
  }

  void updateVerifier(int emailHash) {
      Verifier verifier;
      if( verifierRepo.existsById(emailHash) ) {
          verifier = verifierRepo.getOne(emailHash);
          verifier.numberOfVerifications ++;
      }else {
          verifier = new Verifier(emailHash,1);
      }
      
      verifierRepo.save(verifier);
  }
  
  
  
  @PostMapping("/odor/report")
  Report reportOdor(@RequestBody Report report) {
      report.dateTime = new Date();
      
      updateReporter(report.emailHash,1,0);
      log.info("Adding Report {}", report);
      return reportRepo.save(report);
  }

  @GetMapping("/odor/reports")
  public List<Report> getAllReport() {
    
    List<Report> allReports;
    allReports = reportRepo.findAll();
    log.info("All Reports {}", allReports.toArray().toString());
    return allReports;
  }
      
  @GetMapping("/odor/reports/search/date/{dateTime}")
  public List<Report> getReportsByDate(@PathVariable("dateTime") @DateTimeFormat(pattern = "MM-dd-yyyy") Date dateTime) {
    
    List<Report> allReports;
    List<Report> result = new ArrayList<>();
    allReports = reportRepo.findAll();
    for( Report r : allReports) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if( sdf.format(r.dateTime).equals(sdf.format(dateTime)) ) {
            result.add(r);
        }
    }

    log.info("report with date activity : {}", dateTime);
    log.info("report with date activity {}", result.toArray().length);
    return result;
  }
  
  @GetMapping("/odor/reports/search/emailhash/{emailHash}")
  public List<Report> getReportsByEmailHash(@PathVariable(value = "emailHash") int emailHash) {

    List<Report> allReportsOfReporter = reportRepo.findByEmailHash(emailHash);
    
    return allReportsOfReporter;
  }
    
  @GetMapping("/odor/stats/{emailHash}")
  public UserStats getUserStats(@PathVariable(value = "emailHash") int emailHash) {
      UserStats userStats = new UserStats(emailHash);
      List<Reporter> reporterList = reporterRepo.findByEmailHash(emailHash);

      if( reporterList.size() != 0 ) {
          Reporter reporter = reporterList.get(0);
          userStats.setNumberOfReports(reporter.getNumberOfReports());
          userStats.setNumberOfReportsVerified(reporter.getNumberOfReportsVerified());
      }
      List<Verifier> verifierList = verifierRepo.findByEmailHash(emailHash);
      if( verifierList.size() != 0) {
          Verifier verifier = verifierList.get(0);
          userStats.setNumberOfVerifications(verifier.getNumberOfVerifications());
      }

      log.info("Odor Stats for "+emailHash+ " : "+ userStats.toString() + " End");
      
        List<Report> allReportsOfReporter = reportRepo.findByEmailHash(emailHash);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        Date today = new Date();
        cal.setTime(today);//Set to Now
        
        cal.add(Calendar.DATE, -31);
        Date monthBack = cal.getTime();
        
        
        Set<String> mapOfDates = new HashSet<>();
        int numberOfReportsInLast30Days = 0;
        for( Report r : allReportsOfReporter) {
            if( r.dateTime.compareTo(monthBack) >= 0)  {
                //Date is after monthBack date
                mapOfDates.add(sdf.format(r.dateTime));
                numberOfReportsInLast30Days++;
            }else {
                break;
            }
            if( mapOfDates.size() > 20 ) {
                userStats.setVerifier(true);
            }            
        }
        int  activeDaysInLast30Days = mapOfDates.size();
        log.info("Odor Stats for "+emailHash+ " : Last 30 day performance :"+ numberOfReportsInLast30Days + ", in consecutive days : "+activeDaysInLast30Days);
        userStats.setActiveDaysInLast30Days(activeDaysInLast30Days);
        userStats.setNumberOfReportsInLast30Days(numberOfReportsInLast30Days);
        
        log.info("Odor Stats for "+emailHash+ " : "+ userStats.toString() + " End");

        return userStats;
  }
  
  
  @GetMapping("/odor/verifier/check/{emailHash}")
  public boolean getVerifier(@PathVariable(value = "emailHash") int emailHash) {
    UserStats userStats = getUserStats(emailHash);
    
    return userStats.isVerifier();
    /*
    List<Report> allReportsOfReporter = reportRepo.findByEmailHash(emailHash);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    Date today = new Date();
    cal.setTime(today);//Set to Now
    
    cal.add(Calendar.DATE, -31);
    Date monthBack = cal.getTime();
    

    Set<String> mapOfDates = new HashSet<>();
    for( Report r : allReportsOfReporter) {
        if( r.dateTime.compareTo(monthBack) >= 0)  {
            //Date is after monthBack date
            mapOfDates.add(sdf.format(r.dateTime));
        }
        log.info("Reports for:  {} : {} : {} {}", emailHash, mapOfDates.size(), sdf.format(r.dateTime), r.dateTime.compareTo(monthBack));
        if( mapOfDates.size() > 20 ) {
            //User has reported consistently over a month, so can act as admin
            return true;
        }
    }
    
    return false;
    */
  }  

  @PostMapping("/odor/verify")
  VerifiedReport verifyReport(@RequestBody VerifiedReport verifiedReport) {
      verifiedReport.dateTime = new Date();
      log.info("Adding verifiedReport {}", verifiedReport);
      
      Report report = reportRepo.getOne(verifiedReport.getReportId());
      report.setVerified(verifiedReport.verified);
      log.info("Updating Report {}", report);
      reportRepo.save(report);

      if( verifiedReport.verified ) {
          //Report didn't match : didn't qualify
          updateReporter(verifiedReport.reporterId,0,1);
      }
      updateVerifier(verifiedReport.verifierId);
      
      return verifiedReportRepo.save(verifiedReport);
  }
  
  @GetMapping("/odor/verifiedreports")
  public List<VerifiedReport> getAllVerifiedReport() {
    
    List<VerifiedReport> allReports;
    allReports = verifiedReportRepo.findAll();
    log.info("All Reports {}", allReports.toArray().toString());
    return allReports;
  }
  
  @GetMapping("/odor/verifiedreports/search/date/{dateTime}")
  public List<VerifiedReport> getVerifiedReports(@PathVariable("dateTime") @DateTimeFormat(pattern = "MM-dd-yyyy") Date dateTime) {
    
    List<VerifiedReport> allReports;
    List<VerifiedReport> result = new ArrayList<>();
    allReports = verifiedReportRepo.findAll();
    for( VerifiedReport r : allReports) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if( sdf.format(r.dateTime).equals(sdf.format(dateTime)) ) {
            result.add(r);
        }
    }

    log.info("report with date activity : {}", dateTime);
    log.info("report with date activity {}", result.toArray().length);
      
    return result;
  }  
}
