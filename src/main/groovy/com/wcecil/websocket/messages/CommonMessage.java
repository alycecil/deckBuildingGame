package com.wcecil.websocket.messages;

import com.wcecil.websocket.messages.enums.MessageTypes;

public class CommonMessage {

	private String id;
	private String content;
	private String type;

	public CommonMessage(){}
	
	public CommonMessage(String content) {
		this.content = content;
		this.type = "common";
	}
	
	public CommonMessage(String id, String content, MessageTypes type) {
		this.id = id;
		this.content = content;
		this.type = type.name();
	}
	
	@Deprecated
	public CommonMessage(String id, String content, String type) {
		this.id = id;
		this.content = content;
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

}