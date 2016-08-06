
import java.io.*;
import java.util.*;
/* 4, 5, 6 letter words. 
 * 
 * 
 * 
 */
class Dict {
	
	
	public static ArrayList<String> rWords;
	public static ArrayList<String> all4Words = new ArrayList();
	public static ArrayList<String> all5Words = new ArrayList();
	public static ArrayList<String> all6Words = new ArrayList();
	
	public static HashMap<String,Integer> globalWordList = new HashMap();
	public String randomGenerator(){
		Random generator = new Random();
		int index = generator.nextInt(rWords.size());
		return rWords.get(index);
	}
	
	public boolean isInDict(String word){
		if(globalWordList.containsKey(word))
			return true;
		return false;
	}
	
	public void removeWord(String word){
		for(int i=0; i<rWords.size(); i++){
			if(rWords.get(i).equals(word)){
				rWords.remove(i);
				break;
			}
				
		}
	}
	
	public boolean removeWords(String uncommon){
		String regexPattern = ".*["+uncommon+"].*";
		for(int i =0; i < rWords.size(); i++){
			if((rWords.get(i)).matches(regexPattern)){
				rWords.remove(i);
			}
		}
		return true;
	}
	
	public static void decreaseDomain(String str,int count)
    {
	    int wordCountArray[] = new int[26];
	    for (int i = 0; i < 26; i++)
	        wordCountArray[i] = 0;
	    for (int i = 0; i < str.length(); i++)
	             wordCountArray[(str.charAt(i) - 'A')]++;
	    int tempWordCountArray[] = new int[26];
	     for(int i=0;i<rWords.size();i++)
	     {
	             tempWordCountArray = wordCountArray.clone();
	             if(!compareMatch(tempWordCountArray,rWords.get(i),count))
	             {
	                     rWords.remove(i);i--;
	             }
	     }
    }

	private static boolean compareMatch(int[] tempWordCountArray, String line, int count) {
        int reqBlanks=0,i;
        for (i = 0; i < line.length(); i++) {
		    if (tempWordCountArray[(line.charAt(i) - 'A')] <= 0)
		        reqBlanks++;
		    else tempWordCountArray[(line.charAt(i) - 'A')]--;
		 }
        if (reqBlanks <= line.length()-count) {
                return true;
        }
        return false;
	}


	public Dict() throws IOException{
		BufferedReader br=null;
        
        try 
        {
        br = new BufferedReader( new FileReader("C:\\Users\\echinnasamy\\Downloads\\sowpods.txt"));
        String line;
        int i=0;
        
        while((line =br.readLine()) != null)
        {
        	if(line.length()==4)
        	{
        		
        		all4Words.add(line);
        		
        		
        	}
        	if(line.length()==5)
        	{
        		all5Words.add(line);
        	}
        	if(line.length()==6){
        		
        		all6Words.add(line);
        		
        	}
        	globalWordList.put(line, 1);
        }
        	           
       }
       catch (FileNotFoundException e) 
       {
           e.printStackTrace();
       }

	}
	
	public void copyToRWords(int difficulty){
		//public static ArrayList<String> rWords;
		
		if(difficulty == 4){
			//System.out.println(all4Words.toString());
			rWords = new ArrayList<String>(all4Words);
			//System.out.println(rWords.toString());
		}
		else if(difficulty == 5){
			//System.out.println("hello");
			rWords = new ArrayList<String>(all5Words);
		}
		else{
			rWords = new ArrayList<String>(all6Words);
		}
	}
	
	public static int getCount(int[] tempWordCountArray, String line)
    {       
            int notMatch=0;
            for (int i = 0; i < line.length(); i++) {
	        if (tempWordCountArray[(line.charAt(i) - 'A')] <= 0)
	            notMatch++;
	        else tempWordCountArray[(line.charAt(i) - 'A')]--;
	        }
	        return (line.length()-notMatch);
    }
	public String chooseWordByPc()
    {
		String str = randomGenerator();
		int wordCountArray[] = new int[26];
	    for (int i = 0; i < 26; i++)
	        wordCountArray[i] = 0;
	    for(int i = 0; i< str.length(); i++){
	    	if(wordCountArray[str.charAt(i)-'A']==0){
	    		wordCountArray[str.charAt(i)-'A']++;
	    	}
	    	else{
	    		chooseWordByPc();
	    		break;
	    	}
	    }
	    return str;
    }
	public static boolean isAWordInDict(String str)
    {
            if(globalWordList.containsKey(str))
                    return true;
            return false;
    }
	public static int matchCount(String str, String pcWord)
    {
            int wordCountArray[] = new int[26];
    for (int i = 0; i < 26; i++)
        wordCountArray[i] = 0;
    for (int i = 0; i < pcWord.length(); i++)
             wordCountArray[(pcWord.charAt(i) - 'A')]++;
    return getCount(wordCountArray,str);
    }
}

public class Mastermind {
	static int difficulty;
	public static void main(String args[]) throws IOException{
		
		Dict dictionary = new Dict();
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.println("Enter the level of difficulty \n1. Easy\n 2. Medium\n 3. Hard\n 4.End game");
			difficulty = Integer.parseInt(br.readLine()) + 3;
			if(difficulty == 7)
				break;
			dictionary.copyToRWords(difficulty);
			String PCword = dictionary.chooseWordByPc();
			while(true){
			System.out.println("Choose a word you want to play against");
			System.out.println("PC word:"+PCword);
			
			
				System.out.println("Enter your guessed "+ difficulty +" letter word");
				String PlayerGuessedWord;
				while(true){
					PlayerGuessedWord =  br.readLine();
					if(dictionary.isInDict(PlayerGuessedWord))
						break;
				}
				int pMatchedCountLength = dictionary.matchCount(PlayerGuessedWord,PCword);
				if(PlayerGuessedWord.equals(PCword)){
					System.out.println("You WON!!"+PCword);
					break;
				}
				
				System.out.println("count of common letters for pc word "+pMatchedCountLength);
				
				String PCguessedWord = dictionary.randomGenerator();
				System.out.println("PC guess:"+PCguessedWord);
				
				System.out.println("Enter number of common letters or type -1 if PC wins");
				int countOfCommon = Integer.parseInt(br.readLine());
				if(countOfCommon == -1){
					System.out.println("PC Won");
					break;
				}
				if(countOfCommon == 0 ){
					dictionary.removeWords(PCguessedWord);
				}
				else{
					dictionary.decreaseDomain(PCguessedWord, countOfCommon);
					
					dictionary.removeWord(PCguessedWord);
				}
				System.out.println("size of dictionary : "+dictionary.rWords.size());
				System.out.println("");
			}
		}
		System.out.println("-------------ENDED----------------");
	}
	
}
