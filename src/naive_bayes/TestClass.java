package naive_bayes;

public class TestClass 
{
	public static void main(String[] args)
	{
		NaiveBayesClassifier nb = new NaiveBayesClassifier();
		ClassifierEvaluator eval = new ClassifierEvaluator(nb);
		eval.Evaluate(true);
		System.out.println("Accuracy : " + eval.getAccuracy());
		System.out.println("Precision : " + eval.getPrecision());
		System.out.println("Recall : " + eval.getRecall());
		System.out.println("FMeasure : " + eval.getFMeasure());
	}
}