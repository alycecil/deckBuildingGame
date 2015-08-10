package com.wcecil.game.common

class ScriptLoader {

	static final String FILE_PREFIX = 'file:'
	
	String loadScript(String script){
		String finalScript = null
		
		if(script!=null&&script.trim().startsWith(FILE_PREFIX)){
			script = script.substring(FILE_PREFIX.length()+script.indexOf(FILE_PREFIX)).trim()
			
			println "loading file $script"
			
			def scriptURL = this.getClass().getClassLoader().getResource(script)
			
			if(scriptURL==null){
				scriptURL = this.getClass().getClassLoader().getResource("static/$script")
			}
			if(scriptURL==null){
				scriptURL = this.getClass().getClassLoader().getResource("static/scripts/$script")
			}
			
			def f = new File(scriptURL.toURI())
			
			finalScript = f.text
		}else{
			finalScript = script?.replaceAll('\\\\n', '\n')
		}
		
		finalScript
	}
}
