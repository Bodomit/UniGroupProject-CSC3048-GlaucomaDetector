import java.awt.image.BufferedImage;
import java.awt.image.Raster;


public class Segmenter 
{
	
	public Segmenter()
	{
			
	}
	
	public Segmenter(int threshold)
	{
		
	}
	
	public BufferedImage segment(BufferedImage image)
	{
		return segmentBrightnessAutomatic(image);
	}
	
	private BufferedImage segment_brightness_manual(BufferedImage image, int threshold)
	{
		short[] LUT = thresholdLut(threshold);
		return ImageOp.pixelop(image, LUT);
	}
	// return the automatically segmented image
	private BufferedImage segmentBrightnessAutomatic(BufferedImage image)
	{
		short[] LUT = performAutomaticThresholding(image);
		return ImageOp.pixelop(image, LUT);
	}
	
	private BufferedImage segment_edge(BufferedImage image)
	{
		return image;
	}
	
	// Create the lookup table.
	private short[] thresholdLut(int t)
    {
    	short[] lut = new short[256];
		for(int i = 0; i < (short)lut.length; i++)
		{
			if(i >= t)
				lut[i] = 255;
			else
				lut[i]= 0;
		}
		return lut;
    }
	
	// calculate the mean grey level of the image 
	private int mean(BufferedImage image)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		Raster rast = image.getRaster();
		int sum = 0;
		
		for(int i =0; i<height; i++)
		{
			for(int j = 0; j<width; j++)
			{
				sum += rast.getSample(j, i, 0);
			}
		}
		
		return sum/(width*height);
	}
	
	// calculate the standard deviation 
	private int standardDeviation(BufferedImage image, double mean)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		Raster rast = image.getRaster();
		
		int sum =0;
		
		for(int i = 0; i<height; i++)
		{
			for(int j =0; j<width; j++)
			{
				sum += Math.pow(rast.getSample(j, i, 0) - mean, 2);
			}
		}
		
		return (int)Math.sqrt(sum/(width*(height-1)));
	}
	
	// perform automatic thresholding
	private short[] performAutomaticThresholding(BufferedImage image)
	{
		double mean = mean(image);
		int threshold = (int)Math.round(mean + standardDeviation(image, mean));
		
		return thresholdLut(threshold);
	}
	
}
