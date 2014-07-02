package feature_selector;

import java.util.Comparator;


public class HamComparator implements Comparator<Word>
{
	@Override
	public int compare(Word w1 , Word w2)
	{
		if(w1.getHamFMeasure() == w2.getHamFMeasure()) return 0;
		else if(w1.getHamFMeasure() > w2.getHamFMeasure()) return 1;
		else return -1;
	}	
}