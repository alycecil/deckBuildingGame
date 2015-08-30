package com.wcecil.websocket.messages;

import com.wcecil.websocket.messages.enums.MessageTypes;

public class CommonMessage {

	private String id;
	private Object content;
	private String type;
	private String gameId;

	public CommonMessage(){}
	
	public CommonMessage(Object content) {
		this.content = content;
		this.type = "common";
	}
	
	public CommonMessage(String id, Object content, MessageTypes type, String gameId) {
		this.id = id;
		this.content = content;
		this.type = type.name();
		this.gameId = gameId;
	}
	
	@Deprecated
	public CommonMessage(String id, Object content, String type, String gameId) {
		this.id = id;
		this.content = content;
		this.type = type;
		this.gameId = gameId;
	}

	public Object getContent() {
		return content;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}