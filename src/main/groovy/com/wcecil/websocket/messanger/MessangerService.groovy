package com.wcecil.websocket.messanger;

import groovy.transform.CompileStatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.wcecil.websocket.messages.CommonMessage;
import com.wcecil.websocket.messages.enums.MessageTypes;

@Service
@CompileStatic
public class MessangerService {
	private @Autowired SimpMessagingTemplate template;
	
	public void sendHeartbeat(){
		CommonMessage payload = new CommonMessage(null,String.valueOf(System.currentTimeMillis()),MessageTypes.SYSTEM);
		template.convertAndSend('/topic/heartbeat', payload );
	}
	
	public void endTurn(String gameId){
		CommonMessage payload = new CommonMessage(null,gameId,MessageTypes.ENDTURN);
		template.convertAndSend("/topic/game.$gameId".toString(), payload );
	}
	
	public void endGame(String gameId){
		CommonMessage payload = new CommonMessage(null,gameId,MessageTypes.ENDGAME);
		template.convertAndSend("/topic/game.$gameId".toString(), payload );
	}
	
	public void updateGame(String gameId, String userId, String message){
		CommonMessage payload = new CommonMessage(userId, message,MessageTypes.UPDATE);
		template.convertAndSend("/topic/game.$gameId".toString(), payload );
	}
	
	public void alertUser(String userId, String message){
		CommonMessage payload = new CommonMessage(userId, message,MessageTypes.UPDATE);
		template.convertAndSend("/topic/user.$userId".toString(), payload );
	}
	
	public void alertUserNewGame(String userId, String message){
		CommonMessage payload = new CommonMessage(userId, message, MessageTypes.NEWGAME);
		template.convertAndSend("/topic/user.$userId".toString(), payload );
	}
}
