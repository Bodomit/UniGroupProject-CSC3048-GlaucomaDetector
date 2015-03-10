import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;

/**
 * The Automated Image Recognition System.
 * @author 40057686
 *
 */
public class AIRSystem {
	
	// Initialise the different components.
	private PreProcessor pre;
	private PostProcessor post;
	private Segmenter seg;
	private Classifier classifier;
	
	public AIRSystem()
	{
		pre = new PreProcessor();
		post = new PostProcessor();
		seg = new Segmenter();
		classifier = new Classifier();
	}
	
	/**
	 * Trains the system using images in the given directory.
	 * @param directory The directory containing the training images.
	 */
	public void train(File directory)
	{
		// Load the images.
		ArrayList<TrainingImage> images = getTrainingImages(directory);
		
		// Process each image.
		for(TrainingImage image: images)
		{
			// Get the actual image for processing.
			BufferedImage processedImage = image.getImage();
			
			// Process the image.
			processedImage = pre.process(processedImage);
			processedImage = seg.segment(processedImage);
			processedImage = post.process(processedImage);
			
			// Train the classifier using the image.
			classifier.train(new TrainingImage(processedImage, image.isPositive()));
		}
	}
	
	/**
	 * Trains the system using images in the given directory.
	 * @param directoryName The directory containing the training images.
	 */
	public void train(String directoryName)
	{
		File directory = new File(directoryName.replace("\"", ""));
		train(directory);
	}
	
	public boolean classify(BufferedImage image)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	public Map<String, Boolean> classifyAllInFolder(File directory)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	public Map<String, Boolean> classifyAllInFolder(String directoryName)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	/**
	 * Takes a file directory and reads all loads all the training images.
	 * @param directory The path to the directory containing the training images.
	 * @return A list of images with whether they have glaucoma or not.
	 */
	private ArrayList<TrainingImage> getTrainingImages(File directory)
	{
		ArrayList<TrainingImage> images = new ArrayList<TrainingImage>();
	
		try
		{
			if(!directory.isDirectory())
				throw new FileNotFoundException("The directory \"" + directory.getAbsolutePath() + "\" does not exist.");
				
			for(final File image : directory.listFiles())
			{
				
				if(!image.isFile())
					throw new FileNotFoundException(image.getName() + " does not exist.");
				
				boolean isGlaucoma = image.getName().contains("glaucoma") ? true : false;
				BufferedImage img = ImageOp.readInImage(image.getAbsolutePath());
				images.add(new TrainingImage(img, isGlaucoma));
			}
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		
		return images;
	}

}
