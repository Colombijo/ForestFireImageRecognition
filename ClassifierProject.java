/* Artificial Neural Network application that classifies
 * satelite forest images located in folders as having a fire 
 * or not a fire
 * 
 *  Authored by: Joey Colombi
 *  Bellevue College Computer Science 2018-2019
 */

import 

java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ClassifierProject {
	
	//Here is where you store your file names that contain the fire/non-fire images
	private String[] downloadFileNames  = {"C:\\Users\\Meg\\Documents\\DownloadImages", "C:\\Users\\Meg\\Documents\\DownloadImagesNoFire"};
	private String[] resizedFileNames = {"C:\\Users\\Meg\\Documents\\NewFireResized", "C:\\Users\\Meg\\Documents\\NewNoFireResized"};
	
	// Here is where you store the XML file name that you read the network values from
	private String xmlFileName = "C://Users//Meg//Documents//networkInfo93point75.xml";
	
	public ClassifierProject() throws IOException{
		File xml_file = new File(xmlFileName);
		
		ReadXML myReadXML = new ReadXML(xml_file);
		
		String classificationWord = "";
		Scanner scan = new Scanner(System.in);
		System.out.print("Type 1 if you would like to test fire images. Type 2 if you would like to test non-fire images: ");
		String selection =  scan.nextLine();
		int choice = Integer.parseInt(selection);
		while (choice != 1 && choice != 2) {
			System.out.print("Type 1 if you would like to test fire images. Type 2 if you would like to test non-fire images: ");
			choice = scan.nextInt();
		}
		
		if (choice == 1) {
			classificationWord = "No fire";
		} else {
			classificationWord = "fire";
		}
		
		System.out.println();
		choice = choice - 1;
		File fireImages = new File(downloadFileNames[choice]);
		File[] fireList = fireImages.listFiles();
		File fireResized = new File(resizedFileNames[choice]);
		File[] fireResizedList = fireResized.listFiles();
		String[] fireOriginalString = fireImages.list();
		String[] fireResizedString = fireResized.list();
		
		int count = 0;
		
		for (int i = 0; i < fireList.length; i++) {
			String fileNameOriginal = downloadFileNames[choice] + "\\" + fireOriginalString[i];
			String fileNameFireResized = resizedFileNames[choice] + "\\" + fireOriginalString[i];
			FileInfo myFileInfo = new FileInfo(fileNameOriginal, fileNameFireResized);
			ImageProcessor myImageProcessor = new ImageProcessor(myReadXML, myFileInfo);
			NetworkClassifier myNetwork = new NetworkClassifier(myReadXML, myImageProcessor);
			System.out.println(fileNameOriginal);
			System.out.println(fileNameFireResized);
			System.out.println();
			if (myNetwork.determineFire() == classificationWord) {
				count++;
			}
			
			System.out.println(myNetwork.determineFire());
			System.out.println();
			
		}
		int right = fireList.length - count;
		System.out.println(right);
		double percentage = (double) right / (double) fireList.length;
		System.out.println("Right: " + right + " total: " + fireList.length);
		System.out.println("Accuracy percentage: " + percentage);
	}
	
	public static void main(String[] args) throws IOException {
		ClassifierProject myClassifierProject = new ClassifierProject();
	}

}
