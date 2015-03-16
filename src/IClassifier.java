import java.util.ArrayList;


public interface IClassifier {
	public void train(ArrayList<TrainingVector> vectors);
	public boolean predict(double[] vector);
}
