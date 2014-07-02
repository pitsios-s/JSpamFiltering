package feature_selector;

public class Word
{
	//the word itself.
	private String word;
	
	//in how many spam messages does this word appears.
	private int Sw;
	
	//in how many ham messages does this word appears.
	private int Hw;
	
	// Sw + Hw.
	private int Nw;
	
	// Sw / Nw
	private double spamPrecision;
	
	// Hw / Nw
	private double hamPrecision;
	
	// Sw / Number_Of_Spam_Messages
	private double spamRecall;
	
	// Hw / Number_Of_Ham_Messages
	private double hamRecall;
	
	// (2 * spamPrecision * spamRecall) / (spamPrecision + spamRecall)
	private double spamFmeasure;
	
	// (2 * hamPrecision * hamRecall) / (hamPrecision + hamRecall)
	private double hamFmeasure;
	
	
	
	
	public Word(String word)
	{
		this.word = word;
	}
	
	
	
	
	public void setWord(String word)
	{
		this.word = word;
	}
	
	
	
	
	public String getWord()
	{
		return this.word;
	}
	
	
	
	
	public void setSw(int Sw)
	{
		this.Sw = Sw;
	}
	
	
	
	
	public void IncreaseSw(int incr)
	{
		this.Sw += incr;
	}
	
	
	
	
	public int getSw()
	{
		return this.Sw;
	}
	
	
	
	
	public void setHw(int Hw)
	{
		this.Hw = Hw;
	}
	
	
	
	
	public void IncreaseHw(int incr)
	{
		this.Hw += incr;
	}
	
	
	
	
	public int getHw()
	{
		return this.Hw;
	}
	
	
	
	
	public void setNw(int Nw)
	{
		this.Nw = Nw;
	}
	
	
	
	
	public void IncreaseNw(int incr)
	{
		this.Nw += incr;
	}
	
	
	
	
	public int getNw()
	{
		return this.Nw;
	}
	
	
	
	
	public void setSpamPrecision(double sp)
	{
		this.spamPrecision = sp;
	}
	
	
	
	
	public double getSpamPrecision()
	{
		return this.spamPrecision;
	}
	
	
	
	
	public void setHamPrecision(double hp)
	{
		this.hamPrecision = hp;
	}
	
	
	
	
	public double getHamPrecision()
	{
		return this.hamPrecision;
	}
	
	
	
	
	public void setSpamRecall(double sr)
	{
		this.spamRecall = sr;
	}
	
	
	
	
	public double getSpamRecall()
	{
		return this.spamRecall;
	}
	
	
	
	
	public void setHamRecall(double hr)
	{
		this.hamRecall = hr;
	}
	
	
	
	
	public double getHamRecall()
	{
		return this.hamRecall;
	}
	
	
	
	
	public void setSpamFMeasure(double sfm)
	{
		this.spamFmeasure = sfm;
	}
	
	
	
	
	public double getSpamFMeasure()
	{
		return this.spamFmeasure;
	}
	
	
	
	
	public void setHamFMeasure(double hfm)
	{
		this.hamFmeasure = hfm;
	}
	
	
	
	
	public double getHamFMeasure()
	{
		return this.hamFmeasure;
	}
	
	
	
	
	@Override
	public int hashCode()
	{
		return this.word.hashCode();
	}

	
	
	
	@Override
	public boolean equals(Object ob)
	{
		if(this.getClass() != ob.getClass()) return false;
		
		Word w = (Word)ob;
		
		return  (w.word.equals(this.word));
	}

}