package com.mga.odor;

import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Add imports
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;



@SpringBootApplication
@RestController
public class OdorApplication {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OdorApplication.class);
    private final ReportRepository reportRepo;


    OdorApplication(ReportRepository repository) {
        this.reportRepo = repository;
        
        Location loc;String email;OdorInfo odorInfo;
        loc = new Location(37.432324, -121.89957);
        email = "joy_1@gmail.com";
        odorInfo = new OdorInfo("Nose Feel","Pungent","");
        
        Report r = new Report("joy_1@gmail.com");
        reportRepo.save(r);
        r = new Report("joy_2@gmail.com");
        reportRepo.save(r);
        r = new Report("joy_3@gmail.com");
        reportRepo.save(r);
        List<Report> allReports = reportRepo.findAll();
        for( Report report : allReports) {
            log.info("OdorApplication : Adding activity {}", report.toString());
        }
        
    }

	public static void main(String[] args) {
		SpringApplication.run(OdorApplication.class, args);
		
	}

  @GetMapping("/odor/report")
  public List<Report> getReport() {
    List<Report> allReports = reportRepo.findAll();
    for( Report r : allReports) {
        log.info("Reporting back activity {}", r.toString());
    }
      
    return allReports;
  }
	  
  @GetMapping("/odor/report/{date}")
  public List<Report> getReport(@PathVariable(value = "date") Date date) {
    List<Report> allReports = reportRepo.findAll();
    log.info("report with date activity {}", allReports.toArray().toString());
      
    return allReports;
  }

  @PostMapping("/odor/report")
  Report newActivity(@RequestBody Report report) {
      log.info("Adding activity {}", report);
      return reportRepo.save(report);
  }  
}
