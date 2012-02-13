package com.bryanmarty.processbuilder;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.axiak.runtime.SpawnRuntime;
import net.axiak.runtime.SpawnedProcess;

public class SpawnProcessBuilder {
	
	private boolean redirectErrorStream;
	private File directory;
	private LinkedList<String> command;
	
	private static final SpawnRuntime RUNTIME = new SpawnRuntime(Runtime.getRuntime());

	SpawnProcessBuilder(List<String> command) {
		if(command == null) {
			throw new NullPointerException();
		}
		this.command = new LinkedList<String>(command);
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
		
	}
	
	public boolean redirectErrorStream() {
		return this.redirectErrorStream;
	}
	
	public SpawnProcessBuilder redirectErrorStream(boolean redirectErrorStream) {
		this.redirectErrorStream = redirectErrorStream;
		return this;
	}
	public SpawnedProcess start() {
		return start(false);
	}
	public SpawnedProcess start(boolean forceStart) {
		
		if(!RUNTIME.isLinuxSpawnLoaded() && !forceStart) {
			return null;
		}
		
	}
	
	
}
