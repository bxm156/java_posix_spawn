package net.axiak.runtime;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class SpawnRuntime {
    private Runtime runtime;
    private Boolean linuxSpawnLoaded;
    private static SpawnRuntime instance;

    public SpawnRuntime(Runtime runtime) {
        this.runtime = runtime;
        linuxSpawnLoaded = SpawnedProcess.isLibraryLoaded();
    }

    public Process exec(String [] cmdarray, String [] envp, File chdir, boolean redirectError) throws IOException {
        if (linuxSpawnLoaded) {
            return new SpawnedProcess(cmdarray, envp, chdir,redirectError);
        } else {
	        return runtime.exec(cmdarray, envp, chdir);
        }
    }

    
    public Process exec(String [] cmdarray, String [] envp, File chdir) throws IOException {
    	return exec(cmdarray, envp, chdir, false);
    }

    public Process exec(String [] cmdarray, String [] envp, boolean redirectError) throws IOException {
        return exec(cmdarray, envp, new File("."), redirectError);
    }

    public Process exec(String [] cmdarray) throws IOException {
        return exec(cmdarray, null, false);
    }
    
    public Process exec(String [] cmdarray, boolean redirectError) throws IOException {
        return exec(cmdarray, null, redirectError);
    }

    public Process exec(String command) throws IOException {
        return exec(command, null, new File("."));
    }
    
    public Process exec(String command, boolean redirectError) throws IOException {
        return exec(command, null, new File("."), redirectError);
    }

    public Process exec(String command, String[] envp, File dir) throws IOException {
       return exec(command, envp, dir, false);
    }

    public Process exec(String command, String[] envp, File dir, boolean redirectError) throws IOException {
        if (command.length() == 0) {
            throw new IllegalArgumentException("Empty command");
        }
        StringTokenizer st = new StringTokenizer(command);
        String[] cmdarray = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            cmdarray[i] = st.nextToken();
        }
        return exec(cmdarray, envp, dir, redirectError);
    }

    public Process exec(String command, String[] envp) throws IOException {
        return exec(command, envp, new File("."));
    }

    public static SpawnRuntime getInstance() {
        if (instance == null) {
            instance = new SpawnRuntime(Runtime.getRuntime());
        }
        return instance;
    }

    public Boolean isLinuxSpawnLoaded() {
        return linuxSpawnLoaded;
    }
}
