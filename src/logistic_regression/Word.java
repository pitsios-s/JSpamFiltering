package logistic_regression;

/**
 * Every instance of this class represents a single word found in a message.
 */
public class Word
{
	
	//The word itself.
	private String word;
	
	
	
	
	public Word(String word)
	{
		this.word=word;
	}
	
	
	
	
	public void setWord(String word)
	{
		this.word=word;
	}
	
	
	
	
	public String getWord()
	{
		return this.word;
	}
	
	
	
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj.getClass()!=this.getClass()) return false;
		
		Word w = (Word)obj;
		
		if(this.word.equalsIgnoreCase(w.word)) return true;
		
		return false;
	}
    
	
	
	
    @Override
	public int hashCode()
	{
		return this.hashCode();
	}



}