package com.ecsfin.rpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	@Autowired
	Screenshot screenshot;
	
	@Scheduled(fixedRate = 30000)
	public void runScreenshot(){
		screenshot.run();
	}
}
