import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NetworkClassifier {
	ImageProcessor ip;
	ReadXML xmlR;
	private double[] hiddenUnits;
	private double[] outputUnits;
	
	public NetworkClassifier(ReadXML myReadXML, ImageProcessor imageProcessor) {
		xmlR = myReadXML;
		ip = imageProcessor;
		hiddenUnits = new double[xmlR.getHiddenUnitCount()];
		outputUnits = new double[xmlR.getOutputUnitCount()];
		calcHiddenLayer();
		calcOutputLayer();
		
	}
	
	// Pixel values are put through hidden layer to calculate the hidden units
	public void calcHiddenLayer() {
			double sum = 0.0;
	        String word = "hidden";
	        hiddenUnits[0] = 1.0;
	        for (int i = 0; i < xmlR.getHiddenUnitCount(); i++) {
	            for (int j = 0; j < xmlR.getPixelCount(); j++) {
	                sum += ((double) ip.getPixelValues()[j] * xmlR.getHiddenInputWeights()[i][j]);
	            }
	            sigmoid(sum, word, i);
	            sum = 0.0;
	        }
		}
		
		// Hidden units are put through output layer to calculate fire/no fire classification
		 public void calcOutputLayer() {
			double sum = 0.0;
			String word = "output";
			for (int i = 0; i < xmlR.getOutputUnitCount(); i++) { // 2
				for (int j = 0; j < xmlR.getHiddenUnitCount(); j++) { 
					sum += (hiddenUnits[j] * xmlR.getOutputHiddenWeights()[i][j]);
				}
				sigmoid(sum, word, i);
				sum = 0.0;
			}
		 }
			
	 	// Function to calculate the sigmoid value for the given pixel/hidden unit
		public void sigmoid(double number, String word, int index) {
	        double e = Math.exp(number * -1);
	        double result = 1 / (1 + e);
	        if (word.equals("hidden")) {
	            hiddenUnits[index] = result;
	        } else if (word.equals("hiddenHidden")) {
	        	hiddenUnits[index] = result;
	        } else {
	            outputUnits[index] = result;
	        }
	    }
		
		// Determines classification of image given its output units
		public String determineFire() {
			System.out.println("Output units: " + outputUnits[0] + " " + outputUnits[1]);
			if (outputUnits[1] >= outputUnits[0]) {
				return "fire";
			} else {
				return "No fire";
			}
		}
		
		public double[] getOutputUnits() {
			return outputUnits;
		}
		
		public double[] getHiddenUnits() {
			return hiddenUnits;
		}
	
	
	
	
	
}
