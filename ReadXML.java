import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXML {
	
	private int imageSideLength;
	private int hiddenUnitCount;
	private int hiddenLayerCount;
	private int outputUnitCount;
	private int hasFireValue;
	private int inputUnitCount;
	private double[][] hiddenInputWeights;
	private double[][] hiddenHiddenWeights;
	private double[][] outputHiddenWeights;
	private int pixelCount;
	private File file;
	ArrayList<String> hiddenArray = new ArrayList();
	ArrayList<String> outputArray = new ArrayList();
	
	public ReadXML(File file) throws IOException {
		this.file = file;
		readNetworkVariables();
	}
	@Override
	public String toString(){
		String txt = "ImageSideLength : "+ imageSideLength + "\n"+ " hiddenUnitCount :" +  getHiddenUnitCount() +
				" \n" + "hiddenLayerCount :"+ getHiddenLayerCount();
		return txt;
		
	}
	
	
	// Reads the values in the XML file and stores the values in variables
	public void readNetworkVariables() throws IOException {
	    try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("network");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String imageSideLengthString = eElement.getElementsByTagName("imageSideLength").item(0).getTextContent();
					imageSideLength = Integer.parseInt(imageSideLengthString);
					String numberOfHiddenUnits = eElement.getElementsByTagName("numberOfHiddenUnits").item(0).getTextContent();
					hiddenUnitCount = Integer.parseInt(numberOfHiddenUnits);
					String numberOfHiddenLayers = eElement.getElementsByTagName("numberOfHiddenLayers").item(0).getTextContent();
					hiddenLayerCount = Integer.parseInt(numberOfHiddenLayers);
					String numberOfOutputUnits = eElement.getElementsByTagName("numberOfOutputUnits").item(0).getTextContent();
					outputUnitCount = Integer.parseInt(numberOfOutputUnits);
					String hasFire = eElement.getElementsByTagName("hasFireValue").item(0).getTextContent();
					hasFireValue = Integer.parseInt(hasFire);
				}
			}
			
			NodeList hiddenWeights = doc.getElementsByTagName("hiddenLayer");
			for (int i = 0; i < hiddenWeights.getLength(); i++) {
				Node n = hiddenWeights.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) n;
					for (int k = 0; k < hiddenUnitCount; k++) {
						hiddenArray.add(eElement.getElementsByTagName("weightArray").item(k).getTextContent());
					}
				}
			}
			
			NodeList outputWeights = doc.getElementsByTagName("outputLayer");
			for (int i = 0; i < outputWeights.getLength(); i++) {
				Node o = outputWeights.item(i);
				if (o.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) o;
					for (int p = 0; p < outputUnitCount; p++) {
						outputArray.add(eElement.getElementsByTagName("weightArray").item(p).getTextContent());
					}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
	    }
	    pixelCount = ((imageSideLength * imageSideLength) * 3) + 1;
	    hiddenInputWeights = new double[hiddenUnitCount][pixelCount];
	    for (int i = 0; i < hiddenUnitCount; i++) {
			List<String> hiddenList = Arrays.asList(hiddenArray.get(i).split(","));
			for (int j = 0; j < pixelCount; j++) {
				double number = Double.parseDouble(hiddenList.get(j));
				hiddenInputWeights[i][j] = number;
			}
	    }
   
	    outputHiddenWeights = new double[outputUnitCount][hiddenUnitCount];
	    for (int i = 0; i < outputUnitCount; i++) {
	    	List<String> outputList = Arrays.asList(outputArray.get(i).split(","));
	    	for (int j = 0; j < hiddenUnitCount; j++) {
	    		double number = Double.parseDouble(outputList.get(j));
	    		outputHiddenWeights[i][j] = number;
	    	}
	    }
	}

	public int getHiddenUnitCount() {
		return hiddenUnitCount;
	}

	public int getHiddenLayerCount() {
		return hiddenLayerCount;
	}

	public int getOutputUnitCount() {
		return outputUnitCount;
	}

	public int getHasFireValue() {
		return hasFireValue;
	}

	public double[][] getHiddenInputWeights() {
		return hiddenInputWeights;
	}

	public double[][] getHiddenHiddenWeights() {
		return hiddenHiddenWeights;
	}

	public double[][] getOutputHiddenWeights() {
		return outputHiddenWeights;
	}

	public int getPixelCount() {
		return pixelCount;
	}

	public File getFile() {
		return file;
	}

	public ArrayList<String> getHiddenArray() {
		return hiddenArray;
	}

	public ArrayList<String> getOutputArray() {
		return outputArray;
	}
	
	public int getImageSideLength() {
		return imageSideLength;
	}
}

