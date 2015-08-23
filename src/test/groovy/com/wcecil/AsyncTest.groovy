package com.wcecil

import groovy.transform.CompileStatic
import junit.framework.Assert

import org.junit.Test
import org.springframework.scheduling.annotation.Async

import com.wcecil.common.AsyncronousService

@CompileStatic
class AsyncClass extends AsyncronousService {
	static String lastThread = null
	
	@Async
	public def asyncMethod(){
		println "In Thread ${Thread.currentThread().name}"
		
		lastThread = Thread.currentThread().name;
	}
}

class AsyncTest {
	@Test
	public void test(){
		AsyncClass asc = new AsyncClass()
		println asc.asyncMethod()
		println "Home Thread : ${Thread.currentThread().name}";
		def myThread = Thread.currentThread().name;
		
		int cnt = 10
		while(AsyncClass.lastThread == null){
			Thread.sleep(100)
			cnt --
			
			if(cnt < 0){
				Assert.fail()
			}
		}
		
		Assert.assertFalse AsyncClass.lastThread.equals( myThread)
		
	}
}
