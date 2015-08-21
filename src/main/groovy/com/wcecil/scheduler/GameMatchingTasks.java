package com.wcecil.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wcecil.game.core.GameController;

@Component
public class GameMatchingTasks {
	private static final Logger logger = LoggerFactory.getLogger(GameMatchingTasks.class);
	
	private @Autowired GameController gameController;
	
	@Scheduled(fixedRate = 5000)
	public void matchGames(){
		logger.debug("Matching Games @"+new Date());
		gameController.matchGames();
	}

}