package com.service.appointment.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.service.appointment.repo.AppointMentRepo;

@Component
public class StatusScheduler {

	private List<String> STATUS_FOR_ARCHIVE = Arrays.asList("claim submitted","claim approved","claim rejected");
	private List<String> STATUS_FOR_AGENCY_ARCHIVE = Arrays.asList("claim approved","claim rejected");

	@Autowired AppointMentRepo appointMentRepo;
	
	@Scheduled(cron = "0 */2 * * * *")
	public void archiveClaimSubmitted() {
		System.out.println("archived Cron Started.....");
		appointMentRepo.findAll().forEach(app ->{
			if(!app.getIsArchived() && STATUS_FOR_ARCHIVE.contains(app.getStatus())) {
				app.setIsArchived(true);
				appointMentRepo.save(app);
			}
		});
		System.out.println("@@@@.......cron for archive Completed");
	}
	@Scheduled(cron = "0 */2 * * * *")
	public void archiveClaimRejApproved() {
		System.out.println("agency archived Cron Started.....");
		appointMentRepo.findAll().forEach(app ->{
			if(!app.isAgencyArchived() && STATUS_FOR_AGENCY_ARCHIVE.contains(app.getStatus())) {
				app.setAgencyArchived(true);
				appointMentRepo.save(app);
			}
		});
		System.out.println("agency Archive  cron Completed........@@@");
	}
}
