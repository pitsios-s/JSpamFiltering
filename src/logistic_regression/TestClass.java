package logistic_regression;

public class TestClass 
{
	public static void main(String[] args)
	{
		LogisticRegressionClassifier lg = new LogisticRegressionClassifier();
		ClassifierEvaluator eval = new ClassifierEvaluator(lg);
		eval.Evaluate();
		System.out.println("Accuracy : " + eval.getAccuracy());
		System.out.println("Precision : " + eval.getPrecision());
		System.out.println("Recall : " + eval.getRecall());
		System.out.println("FMeasure : " + eval.getFMeasure());
	}
}