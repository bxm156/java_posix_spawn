package com.bryanmarty.processbuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.processing.ProcessingEnvironment;

import net.axiak.runtime.SpawnRuntime;
import net.axiak.runtime.SpawnedProcess;

public class SpawnProcessBuilder {
	
	private boolean redirectErrorStream;
	private File directory;
	private LinkedList<String> command;
	private Map<String, String> enviornment;
	
	private static final SpawnRuntime RUNTIME = new SpawnRuntime(Runtime.getRuntime());

	SpawnProcessBuilder(List<String> command) {
		if(command == null) {
			throw new NullPointerException();
		}
		this.command = new LinkedList<String>(command);
		this.enviornment = System.getenv();
	}
	SpawnProcessBuilder(String command) {
		if(command == null) {
			throw new NullPointerException();
		}
		this.command = new LinkedList<String>();
		this.command.add(command);
	}
	
	public List<String> command() {
		return this.command;
	}
	
	public File directory() {
		return this.directory;
	}
	
	public SpawnProcessBuilder directory(File directory) {
		if(directory == null) {
			throw new NullPointerException();
		}
		this.directory = directory;
		return this;
	}
	
	public Map<String,String> environment() {
		SecurityManager secMan = System.getSecurityManager();
		if(secMan != null) {
			secMan.checkPermission(new RuntimePermission("getenv.*"));
		}
		return this.enviornment;
	}
	
	public boolean redirectErrorStream() {
		return this.redirectErrorStream;
	}
	
	public SpawnProcessBuilder redirectErrorStream(boolean redirectErrorStream) {
		this.redirectErrorStream = redirectErrorStream;
		return this;
	}
	private String[] convertEnvironment(Map<String,String> env) {
		if(env == null) {
			//TODO
		}
		String[] envp = new String[env.size()];
		int count = 0;
		for(Map.Entry<String, String> e : env.entrySet()) {
			envp[count] = e.getKey() + '=' + e.getValue();
			count++;
		}
		return envp;
	}
	
	public Process start() throws IOException {
		return start(false);
	}
	
	public Process start(boolean forceStart) throws IOException {
		if(!RUNTIME.isLinuxSpawnLoaded() && !forceStart) {
			return null;
		}
		
		SecurityManager secMan = System.getSecurityManager();
		if(secMan != null) {
			secMan.checkExec(command.getFirst());
		}
		
		String[] commandArray = command.toArray(new String[command.size()]);
		String[] envp = convertEnvironment(this.enviornment);
		return RUNTIME.exec(commandArray, envp, this.directory, this.redirectErrorStream);
	}
}
