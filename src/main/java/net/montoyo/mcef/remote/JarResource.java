package net.montoyo.mcef.remote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JarResource {
	
	
	public void copyJarResources(File root, String fileName) throws IOException { 
		File nativeFile = new File(root, fileName);
		InputStream fileInputStream = getClass().getClassLoader().getResource(fileName).openStream();
        FileOutputStream out = new FileOutputStream(nativeFile);
        int i;
        byte [] buf = new byte[1024];
        while((i=fileInputStream.read(buf)) != -1) {
            out.write(buf,0,i);
        }
        fileInputStream.close();
        out.close();
		
	}
}
