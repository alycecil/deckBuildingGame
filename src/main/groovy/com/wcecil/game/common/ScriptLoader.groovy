package com.wcecil.game.common

import groovy.transform.CompileStatic;

import com.wcecil.common.util.StreamUtils;

@CompileStatic
class ScriptLoader {

	static final String FILE_PREFIX = 'file:'
	
	String loadScript(String script){
		String finalScript = null
		
		if(script!=null&&script.trim().startsWith(FILE_PREFIX)){
			script = script.substring(FILE_PREFIX.length()+script.indexOf(FILE_PREFIX)).trim()
			
			println "loading file $script"
			
			def scriptURL = this.getClass().getClassLoader().getResourceAsStream(script)
			
			if(scriptURL==null){
				scriptURL = this.getClass().getClassLoader().getResourceAsStream("static/$script")
			}
			if(scriptURL==null){
				scriptURL = this.getClass().getClassLoader().getResourceAsStream("static/scripts/$script")
			}
			
			finalScript = StreamUtils.convertStreamToString(scriptURL);
		}else{
			finalScript = script?.replaceAll('\\\\n', '\n')
		}
		
		finalScript
	}
}
