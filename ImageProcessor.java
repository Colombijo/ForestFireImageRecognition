import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor {
	ReadXML xmlR;
	private double[] pixelValues;
	FileInfo myFileInfo;
	public ImageProcessor(ReadXML myReadXML, FileInfo fileInfo) throws IOException {
		this.xmlR = myReadXML;
		pixelValues = new double[xmlR.getPixelCount()]; 
		myFileInfo = fileInfo;
		resizeImage();
		calcPixel(pixelValues);
		
	}
	
	// Resizes image to a given uniform size
	private BufferedImage resizeImageHelper(BufferedImage originalImage, int type, int sideLength) {
        BufferedImage resizedImage = new BufferedImage(xmlR.getImageSideLength(), xmlR.getImageSideLength(), type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, xmlR.getImageSideLength(), xmlR.getImageSideLength(), null);
        g.dispose();
        return resizedImage;
    }
	
	// Takes a single image and resizes it a given size 
	public void resizeImage() throws IOException {
		try {
            BufferedImage originalImage = ImageIO.read(new File(myFileInfo.originalFileName));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            if (myFileInfo.originalFileName.endsWith("jpg")) {
                BufferedImage resizeImageJpg = resizeImageHelper(originalImage, type, xmlR.getImageSideLength() );
                ImageIO.write(resizeImageJpg, "jpg", new File(myFileInfo.resizedFileName));
            } else if (myFileInfo.originalFileName.endsWith("jpeg")) {
                BufferedImage resizeImageJpeg = resizeImageHelper(originalImage, type, xmlR.getImageSideLength());
                ImageIO.write(resizeImageJpeg, "jpeg", new File(myFileInfo.resizedFileName));
            } else {
                BufferedImage resizeImagePng = resizeImageHelper(originalImage, type, xmlR.getImageSideLength());
                ImageIO.write(resizeImagePng, "png", new File(myFileInfo.resizedFileName));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
   }
	
	// Determines the pixel values from the resized image and stores it in an array
	public void calcPixel(double[] pixelValues) throws IOException {
		BufferedImage raw, processed;
        raw = ImageIO.read(new File(myFileInfo.resizedFileName));
        int width = raw.getWidth();
        int height = raw.getHeight();
        int count = 1;
        pixelValues[0] = 1.0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //this is how we grab the RGBvalue of a pixel
                // at x,y coordinates in the image
                int rgb = raw.getRGB(x, y);
                //extract the red value
                //int a = (rgb >> 24) & 0xFF;
                int r = (rgb >> 16) & 0xFF;
                pixelValues[count] = (double) r / 255;
                count++;
                //extract the green value
                int g = (rgb >> 8) & 0xFF;
                pixelValues[count] = (double) g / 255;
                count++;
                //extract the blue value
                int b = rgb & 0xFF;
                pixelValues[count] = (double) b / 255;
                count++;
            }
        }
	}
	
	public double[] getPixelValues() {
		return pixelValues;
	}
	
	

}
