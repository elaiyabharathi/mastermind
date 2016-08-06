
import java.io.*;
import java.util.*;
/* 4, 5, 6 letter words. 
 * 
 * 
 * 
 */
class Dict {
	
	
	public static ArrayList<String> rWords = new ArrayList();
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


	public Dict(int level) throws IOException{
		BufferedReader br=null;
        
        try 
        {
        br = new BufferedReader( new FileReader("C:\\Users\\echinnasamy\\Downloads\\sowpods.txt"));
        String line;
        int i=0;
        
        while((line =br.readLine()) != null)
        {
        	if(line.length()==level)
        	{
        		rWords.add(line);
        		globalWordList.put(line, 1);
        	}
        }
        	           
       }
       catch (FileNotFoundException e) 
       {
           e.printStackTrace();
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
	static int EASY = 4;
	int MEDIUM = 5;
	int HARD = 6;
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Dict dictionary = new Dict(4);
		String PCword = dictionary.chooseWordByPc();
		System.out.println("Choose a word you want to play against");
		System.out.println("PC word:"+PCword);
		while(true){
			System.out.println("Enter your guessed "+ EASY +" letter word");
			String PlayerGuessedWord;
			while(true){
				PlayerGuessedWord =  br.readLine();
				if(dictionary.isInDict(PlayerGuessedWord))
					break;
			}
			int pMatchedCountLength = dictionary.matchCount(PlayerGuessedWord,PCword);
			if(pMatchedCountLength == EASY){
				System.out.println("You WON!!"+PCword);
				break;
			}
			
			System.out.println("count of common letters for pc word "+pMatchedCountLength);
			
			String PCguessedWord = dictionary.randomGenerator();
			System.out.println("PC guess:"+PCguessedWord);
			
			System.out.println("Enter number of common letters");
			int countOfCommon = Integer.parseInt(br.readLine());
			if(countOfCommon == 0 ){
				dictionary.removeWords(PCguessedWord);
			}
			else if(countOfCommon == 4){
				System.out.println("PC WON!!!!"+PCguessedWord);
				break;
			}
			else{
				dictionary.decreaseDomain(PCguessedWord, countOfCommon);
				dictionary.removeWord(PCguessedWord);
			}
			System.out.println("");
		}
	}
}
