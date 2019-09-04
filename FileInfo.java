import java.io.File;

public class FileInfo {

	
	public String originalFileName;
	public String resizedFileName;
	public String[] originalFireArray;

	public File fileName;
	public FileInfo(String fName, String rFileName)
	{
		originalFileName = fName;
		resizedFileName = rFileName;
		
		fileName = new File(fName);
	}
}
