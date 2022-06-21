package com.dev.covid.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.covid.model.SelfQuarantine;
import com.dev.covid.repository.SelfQuarantineRepository;

@Service
public class SelfQuarantineService {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private SelfQuarantineRepository repository;
	
	public List<SelfQuarantine> findAll() {
		return repository.findAll();
	}

	public SelfQuarantine save(SelfQuarantine selfQuarantine) {
		return repository.save(selfQuarantine);
	}

	public List<SelfQuarantine> delete(Long id) {
		final Optional<SelfQuarantine> foundSelfQuarantine = repository.findById(id);
		foundSelfQuarantine.ifPresent(selfQuarantine -> repository.delete(selfQuarantine));
		return repository.findAll();
	}

	public List<SelfQuarantine> put(SelfQuarantine selfQuarantine) {
		final Optional<SelfQuarantine> foundSelfQuarantine = repository.findById(selfQuarantine.getPatientPeopleId());
		foundSelfQuarantine.ifPresent(updateSelfQuarantine -> {
			updateSelfQuarantine.setPatientPeopleId(selfQuarantine.getPatientPeopleId());
			updateSelfQuarantine.setSelfQuarantineDate(selfQuarantine.getSelfQuarantineDate());
			updateSelfQuarantine.setSelfQuarantineRelease(selfQuarantine.getSelfQuarantineRelease());
			updateSelfQuarantine.setSelfQuarantineName(selfQuarantine.getSelfQuarantineName());
			updateSelfQuarantine.setSelfQuarantinephoneNumber(selfQuarantine.getSelfQuarantinephoneNumber());
			repository.save(updateSelfQuarantine);			
		});
		return repository.findAll();
	}

	public List<SelfQuarantine> findByselfQuarantineDateBetween(String start, String end) {
		try {
			Date startDate = formatter.parse(start);
			Date endDate = formatter.parse(end);
			return repository.findByselfQuarantineDateBetween(startDate,endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SelfQuarantine> findByselfQuarantineReleaseBetween(String start, String end) {
		try {
			Date startDate = formatter.parse(start);
			Date endDate = formatter.parse(end);
			return repository.findByselfQuarantineReleaseBetween(startDate,endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SelfQuarantine findById(Long id) {
		try {			
			SelfQuarantine selfQuarantine = repository.findById(id).orElseThrow(Exception::new);
			return selfQuarantine;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SelfQuarantine> findByselfQuarantineName(String name) {
		return repository.findByselfQuarantineName(name);
	}

}
