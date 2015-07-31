package com.wcecil.common

class ScriptLoader {

	static final String FILE_PREFIX = 'file:'
	
	String loadScript(String script){
		String finalScript = null
		
		if(script.trim().startsWith(FILE_PREFIX)){
			script = script.substring(FILE_PREFIX.length()+script.indexOf(FILE_PREFIX)).trim()
			
			println "loading file $script"
			
			def f = new File(this.getClass().getClassLoader().getResource(script).toURI())
			
			finalScript = f.text
		}else{
			finalScript = script?.replaceAll('\\\\n', '\n')
		}
		
		finalScript
	}
}
