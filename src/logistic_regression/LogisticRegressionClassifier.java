package logistic_regression;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;


public class LogisticRegressionClassifier 
{   
    public double s;
    
    public double s_new;
   
    //Number of repetions.
    private int repetition;
    
    //All distinct words that are contained in spam and ling messages will be stored in here.
    private ArrayList <Word> FeatureRepository;
    
    //All distinct words that are contained in a messages will be stored in here.
    private HashSet <String> WordOccurrences;
    
    //All distinct words that are contained in spam and ling messages along with their weight, will be stored in here.
    private Map <String, Double> WeightedWords;
    
    
    
    
    public LogisticRegressionClassifier()
    {
        this.s=0;
        this.s_new=0;
        this.repetition=0;
        this.FeatureRepository = new ArrayList<>();
        this.WordOccurrences= new HashSet <>();
        this.WeightedWords = new HashMap <>();
    }
    
    
    
    
    public void train(String lingFilesPath , String spamFilesPath)
    {
        // Open all messages and store the distinct words.
        openMessages(lingFilesPath);
        openMessages(spamFilesPath);


        for(int i=0;i <FeatureRepository.size();i++)
        {
            Word p = FeatureRepository.get(i);
            
            WeightedWords.put(p.getWord(), 1.0);
        }
        
        while(repetition<10 && s_new-s < 0.001)
        {          
            trainMessages(lingFilesPath,0,s);   

            trainMessages(spamFilesPath,1,s_new);
            
            repetition++;
            s=0;
            s_new=0;
        }

    }
    
    
    
    
    public void openMessages(String path)
    {
    	try
    	{	
            File directory = new File(path);
            File[] files = directory.listFiles();
			
            for(File f :files)
            {
                BufferedReader br = new BufferedReader(new FileReader(f));
				
                String line = br.readLine();
			
                while(line!=null)
                {
                    StringTokenizer tok = new StringTokenizer(line);
				
                    while(tok.hasMoreTokens())
                    {
                        Word w = new Word(tok.nextToken());
			
                       int pos = FeatureRepository.indexOf(w);
					
                       if(pos==-1) 
                       {           
                            FeatureRepository.add(w);
                       }
                      
                    }	
					
                    line=br.readLine();						
                }
			
                br.close();
            }
        }	
        
    	catch(Exception e)
        {
            System.err.println("ERROR : "+e.getMessage());
        }
    }
    
    
    
    
    public void trainMessages (String path, int category,double mys)
    {
        try
        {     
            File directory = new File(path);
            File[] files = directory.listFiles();

            double likelihood=0;

            s_new=mys;
            
            for(File f :files)
            {     
                BufferedReader br = new BufferedReader(new FileReader(f));
				
                String line = br.readLine();
			
                while(line!=null)
                {
                    StringTokenizer tok = new StringTokenizer(line);
				
                    while(tok.hasMoreTokens())
                    {
                        String w = tok.nextToken();
                        
                        // Add all distinct words of the current message ij the HashSet.
                        WordOccurrences.add(w);
                    }	
					
                    line=br.readLine();						
                }
                
                // Compute the likelihood
                likelihood = computeLikeliHood(WordOccurrences,WeightedWords,category);
               
                s=s_new;
                s_new =+ likelihood;
                
                //Update the weights of the words
                newWeight(WordOccurrences,WeightedWords,category);
                
                // Clear the HashSet to be ready for the next message
                WordOccurrences.clear();
                br.close();
   
            }
            
        }	
        
        catch(Exception e)
        {
            System.err.println("ERROR : "+e.getMessage());
        }
    }
    
    
    
    
    private double computeLikeliHood(HashSet<String> WordOccurrences ,Map<String, Double> WeightedWords, int realcategory)
    {   
        //l(w)= y*log(P(c+|x;w)) + (1-y)*log(P(c-|x;w)) - λ* sqrt(Σwi^2)
        double likelihood = (realcategory * Math.log(computeSpamProbability(WordOccurrences,WeightedWords)))+ ((1-realcategory)*Math.log(computeLingProbability(WordOccurrences,WeightedWords)))- (0.1 *computeSqrtWeight(WeightedWords)) ; 
        
        return likelihood;
    }
    
    
    
    
    private double computeSqrtWeight(Map<String, Double> WeightedWords)
    {        
        Set<Entry<String, Double>> s = WeightedWords.entrySet(); 
        
        double weight=0;

        Iterator<Entry<String, Double>> it1=s.iterator();
        
        while(it1.hasNext())
        {
            Entry<String, Double> m =it1.next();
           
            String w= m.getKey();
           
            double myweight=WeightedWords.get(w);
            
            //Σwi^2
            weight += Math.pow(myweight,2);

        }
        //sqrt(Σwi^2)
        return Math.sqrt(weight);
    }
    
    
    
    
    private double computeSpamProbability(HashSet<String> WordOccurrences,Map<String, Double> WeightedWords)
    {
        //P(c+|x;w)= 1/1+e^(-wx)
        double SpamProbability= 1/(1+Math.exp(-computeInnerProduct(WordOccurrences,WeightedWords)));
        
        return SpamProbability;
    }
   
    
    
    
     private double computeLingProbability(HashSet<String> WordOccurrences,Map<String, Double> WeightedWords)
     {
        //P(c-|x;w)= 1- P(c+|x;w)
        double LingProbability=Math.exp(-computeInnerProduct(WordOccurrences,WeightedWords))/(1+Math.exp(-computeInnerProduct(WordOccurrences,WeightedWords)));     
        
        return LingProbability;
     }
   
     
     
     
    private double computeInnerProduct(HashSet<String> WordOccurrences,Map<String, Double> WeightedWords)
    {
        double sum=0;

        Iterator<String> it1=WordOccurrences.iterator();
        
        while(it1.hasNext())
        {
           String w= it1.next();
           
           if(WeightedWords.containsKey(w))
           {
                double myweight = WeightedWords.get(w);           
                sum += myweight;   
           }          
        }
         
        return sum;
    }
    
    
    
    
     private void newWeight(HashSet<String> WordOccurrences,Map<String, Double> WeightedWords,int category)
     {
        HashSet<String>  myHash = WordOccurrences;
        
        double new_weight;

        Iterator<String> it1=myHash.iterator();
     
        while(it1.hasNext())
        {
            String w= it1.next();

            double myweight=WeightedWords.get(w);
            
            // w_new= wi+ h*(y-P(c+|x;w)*xi)
            // However for the words x that are not contained in the current message, x=0. 
            // That means that the weight remains the same for them, so here we update only the weights that are to change.
            new_weight= myweight+ 0.1 *(category-computeSpamProbability(myHash,WeightedWords));
            
            // Update the Map.
            WeightedWords.remove(w);
            WeightedWords.put(w, new_weight);
        }
     }
    
     
     
     
     public boolean classify(String msg)
     {				
        double ling;
        double spam;
        
        HashSet<String> msgWords = new HashSet<String>();
                
        StringTokenizer tok = new StringTokenizer(msg);
       
        while(tok.hasMoreTokens()) 
        {
            msgWords.add(tok.nextToken());
        }

        spam=computeSpamProbability(msgWords,WeightedWords);
                
        ling=computeLingProbability(msgWords,WeightedWords);

        if(spam <=ling) return false;
        return true;
     }
}