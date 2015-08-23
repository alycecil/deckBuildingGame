package com.wcecil.common

import groovy.lang.MetaMethod;

import java.lang.reflect.Method

import org.springframework.scheduling.annotation.Async

abstract class AsyncronousService implements GroovyInterceptable {

	def invokeMethod(String name, Object args) {
		boolean invokeNow = true

		def methods = super.getClass().declaredMethods.findAll { it.name == name }

		if(methods.size()==1){
			Method method = methods[0];

			def async = method.getAnnotation(Async.class)

			if(async){
				invokeNow = false;
			}
		}

		if(invokeNow == true){
			MetaMethod method = metaClass.getMetaMethod(name, args)
			return method.invoke(this, args)
		}else{
			Thread t = new RunMethodThread(name, args, metaClass, this)

			t.start();
		}
	}
}

class RunMethodThread extends Thread {
	final def _name
	final def _args
	final def _metaClass
	def _this
	
	
	public RunMethodThread(Object _name, Object _args, def _metaClass, def _this) {
		super();
		this._name = _name;
		this._args = _args;
		this._metaClass = _metaClass;
		this._this = _this;
	}


	public void run() {
		MetaMethod method = _metaClass.getMetaMethod(_name, _args)
		method.invoke(_this, _args)
	}
};
