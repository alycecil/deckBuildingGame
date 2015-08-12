package com.wcecil.common.settings

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import groovy.transform.CompileStatic;

//TODO port to db settings
@CompileStatic
class Settings {
	public static boolean debug = false;
	public static Integer numPlayers = 2;
	public static Integer defaultHandSize = 5;
	public static Integer defaultAvailableSize = 5;
	
	public static AtomicLong nextIdPlayer = new AtomicLong(1l);
	public static AtomicLong nextIdCard = new AtomicLong(1l);
	
}
