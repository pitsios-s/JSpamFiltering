package feature_selector;

import java.util.Comparator;


public class SpamComparator implements Comparator<Word>
{
	@Override
	public int compare(Word w1 , Word w2)
	{
		if(w1.getSpamFMeasure() == w2.getSpamFMeasure()) return 0;
		else if(w1.getSpamFMeasure() > w2.getSpamFMeasure()) return 1;
		else return -1;
	}
}