package com.wcecil.settings

import java.util.concurrent.atomic.AtomicInteger;

import groovy.transform.CompileStatic;

//TODO port to db settings
@CompileStatic
class Settings {
	public static boolean debug = true;
	public static Integer numPlayers = 2;
	public static Integer defaultHandSize = 5;
	public static Integer defaultAvailableSize = 5;
	
	public static AtomicInteger nextIdPlayer = new AtomicInteger(1);
	public static AtomicInteger nextIdCard = new AtomicInteger(1);
	public static AtomicInteger nextIdGame = new AtomicInteger(1);
	
}
