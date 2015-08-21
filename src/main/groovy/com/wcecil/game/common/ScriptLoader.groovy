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
			
			println "loading file: $script"
			
			InputStream scriptURL = this.getClass().getClassLoader().getResourceAsStream(script)
			
			finalScript = StreamUtils.convertStreamToString(scriptURL);
			println "file loaded: $script"
		}else{
			finalScript = script?.replaceAll('\\\\n', '\n')
		}
		
		finalScript
	}
}
