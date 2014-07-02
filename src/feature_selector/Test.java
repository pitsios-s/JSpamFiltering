package feature_selector;

public class Test
{
	public static void main(String[] args) 
	{
		FeatureSelector fs = new FeatureSelector("dataset/spamMessages" , "dataset/lingMessages" , 5);
		fs.SelectFeatures(500,500);
	}
}