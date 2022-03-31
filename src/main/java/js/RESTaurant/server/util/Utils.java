package js.RESTaurant.server.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class Utils {
	
	private static final AtomicLong sequence = new AtomicLong(System.currentTimeMillis() / 1000);


	public static long getNext() {
		return sequence.incrementAndGet();
	}
	
	public static Integer generateUniquSeq() {
		AtomicInteger seq = new AtomicInteger();
		int nextVal = seq.incrementAndGet();
		return nextVal;
	}
	
	
	public static String formatTo2Places(double toFormat) {
		DecimalFormat df = new DecimalFormat("#.##"); 
		String formatted = df.format(toFormat);
		return formatted;
	}
	
	public static void createDirIfNotExists(String directoryPath) {
		if(directoryPath!=null && !directoryPath.isEmpty()) {
    		File directory = new File(directoryPath);
    		
    		if(!directory.exists()){
    			directory.mkdir();
        	}
    	}
	}
	
	
	public static Path saveFileOnDisk(String uploadDir, MultipartFile multipartFile) {
		Date currentDate = new Date();
		
		String fileNameSuffix = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    	Path filepath = Paths.get(uploadDir.toString(), fileNameSuffix + multipartFile.getOriginalFilename());
    	
    	try {
			multipartFile.transferTo(filepath);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
    	
    	return filepath;
	}
	
	public static byte[] convertFileToBytes(String uploadDir, String filename) {
		Path filepath =  Paths.get(uploadDir).resolve(filename);
		File file = new File(filepath.toString());
    	byte[] fileContent = null;
    	if(file.exists() && !file.isDirectory()) {
    		try {
    			fileContent = Files.readAllBytes(file.toPath());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	return fileContent;
	}
}
