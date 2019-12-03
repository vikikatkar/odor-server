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
    final Calendar cal = Calendar.getInstance();


    OdorApplication(ReportRepository repository) {
        this.reportRepo = repository;
        
        
        Report r;
        List<Report> lr = new ArrayList<>();
        
        r = new Report(new Date(),"joy_1@gmail.com".hashCode(),37.43323, -121.89957, "Nose Feel","Pungent","");
        lr.add(r);
        r = new Report(new Date(),"joy_2@gmail.com".hashCode(),37.43334, -121.89930, "Nose Feel","Irritating","");
        lr.add(r);
        r = new Report(new Date(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89940, "Solvent","Tar","");
        lr.add(r);
        r = new Report(new Date(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89800, "Custom Category","Burning Tire","");
        lr.add(r);

        //Yesterday
        r = new Report(yesterday(),"joy_1@gmail.com".hashCode(),37.43323, -121.89957, "Nose Feel","Pungent","");
        lr.add(r);
        r = new Report(yesterday(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89940, "Solvent","Tar","");
        lr.add(r);
        
        for( int i = 1 ;i < 31 ; i++) {
            cal.add(Calendar.DATE, -1);
            log.info("Loading :: "+cal.getTime());
            r = new Report(cal.getTime(),"vikikatkar@gmail.com".hashCode(),37.43323, -121.89800, "Nose Feel","Irritating","");
            lr.add(r);
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

  @PostMapping("/odor/report")
  Report reportOdor(@RequestBody Report report) {
      report.dateTime = new Date();
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
      
  @GetMapping("/odor/report/{dateTime}")
  public List<Report> getReport(@PathVariable("dateTime") @DateTimeFormat(pattern = "MM-dd-yyyy") Date dateTime) {
    
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
  
  
  @GetMapping("/odor/verifier/{emailHash}")
  public boolean isVerifier(@PathVariable(value = "emailHash") int emailHash) {

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
  }  

  
}
