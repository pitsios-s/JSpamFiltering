package feature_selector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;


public class FeatureSelector
{
	//the path of the folder that contains spam messages.
	private String spamFilesPath;
	
	//the path of the folder that contains ham messages.
	private String hamFilesPath;

	//the least number of times that we want a word to appear. 
	private int threshold;
	
	//total number of spam messages.
	private int Nspam;
	
	//total number of ham messages.
	private int Nham;
	
	//contains the words that were found in spam messages.
	private ArrayList<Word> spamWords;
	
	//contains the words that were found in ham messages.
	private ArrayList<Word> hamWords;
	
	//a list that contains al of the words that appear at least threshold times.
	private ArrayList<Word> words;
	
	
	
	
	//constructor.
	public FeatureSelector(String spamFilesPath , String hamFilesPath , int threshold)
	{
		this.spamFilesPath = spamFilesPath;
		this.hamFilesPath = hamFilesPath;
		this.threshold = threshold;
		Nspam = 0;
		Nham = 0;
		spamWords = new ArrayList<Word>();
		hamWords = new ArrayList<Word>();
		words = new ArrayList<Word>();
	}
	
	
	
	
	/*
	 * selects the suitable features.
	 * hams = the number of ham keywords that we want to get.
	 * spams = the number of spam keywords that we want to get.
	 */
	public void SelectFeatures(int hams , int spams)
	{
		FindSpamKeywords();
		FindHamKeywords();
		KeepSuitableWords();
		ComputeScores();
		
		try
		{
			File outFile = new File("spamKeywords.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			
			//now we sort the words ascending according to their SpamFMeasure.
			SpamComparator spamcmp = new SpamComparator();
			Collections.sort(words , spamcmp);
			
			int count = (spams>=words.size())?0:(words.size() - spams);
			
			for(int i=words.size()-1; i>=count; i--)
			{
				bw.write(words.get(i).getWord()+"\n");
			}
			
			bw.close();
			
			
			outFile = new File("hamKeywords.txt");
			bw = new BufferedWriter(new FileWriter(outFile));
			
			//now we sort the words ascending according to their HamFMeasure.
			HamComparator hamcmp = new HamComparator();
			Collections.sort(words , hamcmp);
			
			count = (hams>=words.size())?0:(words.size() - hams);
			
			for(int i=words.size()-1; i>=count; i--)
			{
				bw.write(words.get(i).getWord()+"\n");
			}
			
			bw.close();
			
		}
		
		catch(Exception e)
		{
			System.err.println("ERROR : "+e.getMessage());
		}
	}
	
	
	
	
	
	//finds all the words that are contained in spam messages.
	private void FindSpamKeywords()
	{
		//stores all the unique words that were found in a single message. 
		HashSet<Word> found = new HashSet<Word>();
		
		try
		{
			File f = new File(this.spamFilesPath);
			
			File[] files = f.listFiles();
			
			Nspam = files.length;
			
			//for each available file...
			for(File file : files)
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				String line = br.readLine();
			
				//for each line that the file contains...
				while(line != null)
				{
					StringTokenizer tok = new StringTokenizer(line);
					
					//for every word of the current line...
					while(tok.hasMoreTokens())
					{
						found.add(new Word(tok.nextToken()));
					}
					
					line = br.readLine();
				}
				
				
				Iterator<Word> iter1 = found.iterator();
				
				while(iter1.hasNext())
				{
					Word w = iter1.next();
					w.setHw(0);
					w.setSw(1);
					
					int position = spamWords.indexOf(w);
					
					//if the word was not in the set , add it.
					if(position < 0) spamWords.add(w);
					
					//else increase it's Sw.
					else spamWords.get(position).IncreaseSw(1);
				}
				
				br.close();
				found.clear();
			}
		}
		
		catch(Exception e)
		{
			System.err.println("ERROR : "+e.getMessage());
		}
	}
	
	
	
	
	//finds all the words that are contained in ham messages.
	private void FindHamKeywords()
	{
		//stores all the unique words that were found in a single message. 
		HashSet<Word> found = new HashSet<Word>();
		
		try
		{
			File f = new File(this.hamFilesPath);
			
			File[] files = f.listFiles();
			
			Nham = files.length;
			
			//for each available file...
			for(File file : files)
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				String line = br.readLine();
			
				//for each line that the file contains...
				while(line != null)
				{
					StringTokenizer tok = new StringTokenizer(line);
					
					//for every word of the current line...
					while(tok.hasMoreTokens())
					{
						found.add(new Word(tok.nextToken()));
					}
					
					line = br.readLine();
				}
				
				
				Iterator<Word> iter1 = found.iterator();
				
				while(iter1.hasNext())
				{
					Word w = iter1.next();
					w.setHw(1);
					w.setSw(0);
					
					int position = hamWords.indexOf(w);
					
					//if the word was not in the set , add it.
					if(position < 0) hamWords.add(w);
					
					//else increase it's Hw.
					else hamWords.get(position).IncreaseHw(1);
				}
				
				br.close();
				found.clear();
			}
		}
		
		catch(Exception e)
		{
			System.err.println("ERROR : "+e.getMessage());
		}
	}
	
	
	
	
	//computes the scores for the words.
	private void ComputeScores()
	{
		for(int i=0; i<words.size(); i++)
		{
			words.get(i).setNw(words.get(i).getHw()+words.get(i).getSw());
			words.get(i).setSpamPrecision((double)words.get(i).getSw()/(double)words.get(i).getNw());
			words.get(i).setHamPrecision((double)words.get(i).getHw()/(double)words.get(i).getNw());
			words.get(i).setSpamRecall(Nspam==0?0:((double)words.get(i).getSw()/(double)Nspam));
			words.get(i).setHamRecall(Nham==0?0:((double)words.get(i).getHw()/(double)Nham));
			words.get(i).setSpamFMeasure((words.get(i).getSpamPrecision()+words.get(i).getSpamRecall())==0?0:(2*words.get(i).getSpamPrecision()*words.get(i).getSpamRecall())/(words.get(i).getSpamPrecision()+words.get(i).getSpamRecall()));
			words.get(i).setHamFMeasure((words.get(i).getHamPrecision()+words.get(i).getHamRecall())==0?0:(2*words.get(i).getHamPrecision()*words.get(i).getHamRecall())/(words.get(i).getHamPrecision()+words.get(i).getHamRecall()));
		}
	}
	
	
	
	
	//keeps all the words that appear in at least threshold messages.
	private void KeepSuitableWords()
	{
		for(int i=0; i<spamWords.size(); i++)
		{
			Word word = spamWords.get(i);
			if( (word.getSw() + word.getHw())>this.threshold ) words.add(word);
		}
		
		for(int i=0; i<hamWords.size(); i++)
		{
			Word word = hamWords.get(i);
			if( (word.getSw() + word.getHw())>this.threshold ) words.add(word);
		}
	}



}
//gud project
