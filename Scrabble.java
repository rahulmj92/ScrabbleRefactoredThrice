package scramble1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Scrabble {

private static ArrayList<String> Allwords = new ArrayList<String>();
private static HashMap<Integer,List<String>> wordScoreList = new HashMap<Integer,List<String>>();
private static HashMap<String, Integer> wordListMap = new HashMap<String, Integer>();
        private static String alpha;
        private static int numSpacesBefore=0;
        private static int numSpacesAfter=0;
@SuppressWarnings({ "unchecked", "rawtypes" })
       
public static void main(String[] args) throws IOException {
           String stringWithOneFixedTile=null;
           String currentWord;
           
ArrayList<String> allRacks = getAllRacks();
ArrayList<String> allBlanks = getAllBlanks();

readDictionary();

               
         for (int i = 0; i < allRacks.size(); i++)
         {
        Allwords.clear();
        wordScoreList.clear();
        currentWord = allRacks.get(i);
        stringWithOneFixedTile = allBlanks.get(i);
                 numSpacesBefore=0;
                 numSpacesAfter=0;
                 //System.out.println(currentWord);
                 if(stringWithOneFixedTile!=null)
                 {              
                  String[] splitedSpaces=stringWithOneFixedTile.split("[a-zA-Z]");
                    if(splitedSpaces.length ==  2)
                    {
                        alpha=stringWithOneFixedTile.substring(splitedSpaces[0].length(),splitedSpaces[0].length()+1);
                        numSpacesBefore=splitedSpaces[0].length();
                        numSpacesAfter=splitedSpaces[1].length();
                        System.out.println(alpha+" "+numSpacesBefore+" "+numSpacesAfter);
                    }
                    else if (splitedSpaces.length == 1)
                    {
                        if(stringWithOneFixedTile.charAt(0) == '_')
                        {
                            alpha = "" + stringWithOneFixedTile.charAt(stringWithOneFixedTile.length() - 1);
                            numSpacesBefore = stringWithOneFixedTile.length() - 2;
                                              numSpacesAfter = 0;
                                              System.out.println(alpha+" "+numSpacesBefore+" "+numSpacesAfter);

                        }
                        else {
                            alpha = "" + stringWithOneFixedTile.charAt(0);
                            numSpacesBefore = 0;
                                              numSpacesAfter = stringWithOneFixedTile.length() - 2;
                                              System.out.println(alpha+" "+numSpacesBefore+" "+numSpacesAfter);

                        }
}                   
                 }
        if(currentWord.length() < 7)
        {
        currentWord += " ";
        }
             getHighestScoringWord(currentWord);
             
         }
}
static ArrayList<String> getAllBlanks()
    {
        ArrayList<String> inputLines = new ArrayList<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dirc\\Documents\\NetBeansProjects\\Scramble\\src\\scramble1\\input.txt"));
            String line;
            while ((line = reader.readLine()) != null)
            {
String[] splitedInputBySpaces=line.split(" ");
                inputLines.add(splitedInputBySpaces[0]);
            }
            return inputLines;
        }
        catch (Exception e)
        {
            System.out.println("File Not Found");
            return new ArrayList<String>();
        }
    }

static ArrayList<String> getAllRacks()
    {
        ArrayList<String> inputLines = new ArrayList<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dirc\\Documents\\NetBeansProjects\\Scramble\\src\\scramble1\\input.txt"));
            String line;
            while ((line = reader.readLine()) != null)
            {
String[] splitedInputBySpaces=line.split(" ");
                inputLines.add(splitedInputBySpaces[1]);
            }
            return inputLines;
        }
        catch (Exception e)
        {
            System.out.println("File Not Found");
            return new ArrayList<String>();
        }
    }

        public static boolean isWordValid(String word){
            if(word.contains(alpha))
            {
                if(word.indexOf(alpha) > 0 && word.indexOf(alpha) < word.length() - 1)
                {
                    String[] parts = word.split(alpha);
                    System.out.println(word);
                    if( parts[0].length() <= numSpacesBefore && parts[1].length() <= numSpacesAfter ){
                        return true;
                    }
                }
                else if(word.indexOf(alpha) == 0 && numSpacesBefore == 0)
                return true;
                else if(word.indexOf(alpha) == word.length() - 1 && numSpacesAfter == 0)
                return true;
            }
            return false;
        }
private static void getHighestScoringWord(String rack) {
Allwords = (ArrayList<String>) createWordList(rack);
for(String word : Allwords){
if(checkInDictionary(word.toUpperCase()) && isWordValid(word.toUpperCase()))
                        {
int score = calcScore(word);
addToWordScoreList(score,word);
}
}

Set<Integer> ScoreSet = wordScoreList.keySet();
Iterator<Integer> scoreIterator = ScoreSet.iterator();
int maxScore = 0;
while(scoreIterator.hasNext())
{
int score = scoreIterator.next();
if(score > maxScore)
{
maxScore = score;
}
}
List<String> maxScoreWords = wordScoreList.get(maxScore);
for(int i = 0; i < maxScoreWords.size(); i++)
{
System.out.print(maxScoreWords.get(i) + " ");
}
System.out.println(maxScore);
}
private static void addToWordScoreList(int score, String word) {
List<String> validWordList = wordScoreList.get(score);
if(validWordList == null ){
List<String> newList = new LinkedList<String>();
newList.add(word);
wordScoreList.put(score, newList );
}
else{
if(!validWordList.contains(word)){

validWordList.add(word);
wordScoreList.put(score, validWordList );
}
}

}
private static void readDictionary() throws IOException{
BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dirc\\Documents\\NetBeansProjects\\Scramble\\src\\scramble1\\sowpods.txt"));
String line = null;
while( (line = reader.readLine()) != null){
if(line.length() <= 7)
{
if(!wordListMap.containsKey(line))
{
   wordListMap.put(line, 1);
}
else
{
wordListMap.put(line, 1);
}
}
}
}

public static ArrayList<String> createWordList(String rack )
        {
         
                String inputWord = rack;
                ArrayList<String> combinations = new ArrayList<String>();
                if(inputWord.contains(" "))
                {
                for(char c = 'a'; c <= 'z'; c++)
                {
                combinations.addAll(generateAllCombinations(inputWord.replace(' ', c )));                
                }
                }
                else
                {
                combinations = generateAllCombinations(rack);                
                }
                for(int i = 0; i < combinations.size(); i++)
                {
                permuteString("", combinations.get(i), Allwords);
                }
return Allwords;
}

   public  static void permuteString(String beginningString, String endingString, ArrayList<String> Allwords)
    {
    if (endingString.length() <= 1)
    {
        Allwords.add(beginningString+endingString);
    }
       
    else
      for (int i = 0; i < endingString.length(); i++)
      {
        try
        {
          String newString = endingString.substring(0, i) + endingString.substring(i + 1);
          permuteString(beginningString + endingString.charAt(i), newString, Allwords);
        }
        catch (StringIndexOutOfBoundsException exception)
        {
          exception.printStackTrace();
        }
      }
  }
       
public static boolean checkInDictionary(String word){
return wordListMap.containsKey(word);
}


public static int calcScore(String word){
         int[] worth = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
         int score = 0;
         for(int i = 0; i < word.length(); i++){
        if(!Character.isLowerCase(word.charAt(i))){
                score += worth[ word.charAt(i) - 'A' ];
        }
            }
          return score;
}
       
    public static Map< Integer, List<String> > sortHashMap(){
        Map< Integer, List<String> > map = new TreeMap<Integer, List<String>>(wordScoreList);
        return map;
    }
   
    public static ArrayList<String> generateAllCombinations(String allLetters)
    {
        ArrayList<String> allCombinations = new ArrayList<String>();
        int numLetters = allLetters.length();
        int numCombinations = powerOfTwo(numLetters);
        String word;
        for ( int i = 0; i < numCombinations; i++)
        {
            word = generateAWord(i, allLetters);
            if (word.length() > 1)
            {
                allCombinations.add(word);                                     
            }
        }
        return allCombinations;
    }
   
    static int powerOfTwo(int exponent)
    {
        int returnVal = 1;
        while(exponent > 0)
        {
            returnVal *= 2;
            exponent--;
        }
        return returnVal;
    }
   
    static String generateAWord(int number, String letters)
    {
        int i = 0;
        StringBuilder word = new StringBuilder();
        while (number > 0)
        {
            if (number % 2 == 1)
            {
                word.append(letters.charAt(i));
            }
            i++;
            number /= 2;
        }
        return word.toString();
    }

}