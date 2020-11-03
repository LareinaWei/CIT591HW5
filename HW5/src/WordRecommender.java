import java.io.*;
import java.util.*;

public class WordRecommender {

    String fileName;
    Map<String, HashSet<Character> > mapStringToHash;
    HashSet<String> allWords;
    public WordRecommender(String fileName){
        this.fileName = fileName;
        this.mapStringToHash = new HashMap<>();
        this.allWords = this.buildAllWords();
    }


    public HashSet<String> buildAllWords(){
        HashSet<String> allWords = new HashSet<>();
        try {
            File file = new File(this.fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                allWords.add(st);
               // System.out.println(st);

            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  allWords;
    }




    public HashSet<String> getDict(){
        return this.allWords;
    }

    public double getSimilarity(String word1, String word2){

        if( word1.length()> word2.length() ){
            String temp = word1;
            word1 = word2;
            word2 = temp;
        }
        int length1 = word1.length();
        int length2 = word2.length();
        int lengthMinus = Math.abs(length1 - length2);
        int leftSimilarity = 0;
        for(int i = 0; i < length1 ; i++){
            if( word1.charAt(i) == word2.charAt(i))
                leftSimilarity++;
        }
        int rightSimilarity = 0;
        for(int i = length1 -1 ; i>= 0; i--){
            if( word1.charAt(i)  == word2.charAt(i+ lengthMinus) )
                rightSimilarity++;

        }

        return (leftSimilarity + rightSimilarity) *1.0 / 2;
    }
    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN){

        int toleranceLow = word.length() - tolerance;
        int toleranceHigh = word.length() + tolerance;
        Iterator<String> it = this.allWords.iterator();

        PriorityQueue<WordSimilarity> priorityQMin  = new PriorityQueue<>(new SimilarityComparator());

        while( it.hasNext()){
            String wordNow = it.next();
            if( wordNow.length() < toleranceLow || wordNow.length() > toleranceHigh)
                continue;
            //if(getCommonPercent(word, wordNow) < commonPercent)
            //    continue;

            //System.out.println(wordNow);
            Double similarity = getSimilarity(wordNow, word);
            if( priorityQMin.size() < topN){
                priorityQMin.add(new WordSimilarity(wordNow,similarity));
                continue;
            }

            if(priorityQMin.peek().similarity < similarity ) {
                priorityQMin.poll();
                priorityQMin.add(new WordSimilarity(wordNow, similarity));
            }
        }
        if(priorityQMin.size() >0)
           return this.getWordSuggestionFromQ(priorityQMin);
        else
            return new ArrayList<String>();

    }

    private ArrayList<String>  getWordSuggestionFromQ(PriorityQueue<WordSimilarity> priorityQMin){
        ArrayList<String> list = new ArrayList<>();
        Iterator it = priorityQMin.iterator();
        while(!priorityQMin.isEmpty())
            list.add( priorityQMin.poll().thisWord);

        ArrayList<String> list2 = new ArrayList<>();


        for( int i = list.size()-1; i >=0; i--) {
            //System.out.println(list.get(i));
            list2.add(list.get(i));

        }
        return list2;

    }


    public double getCommonPercent(String word1,String word2){
        if( word1.length() > word2.length()){
            String temp = word1;
            word1 = word2;
            word2 = temp;
        }

        HashSet<Character> unique1;
        if( !this.mapStringToHash.containsKey(word1)){
            unique1 = new HashSet<Character>();
            for(int i = 0; i < word1.length(); i++) {
                unique1.add(   word1.charAt(i));
            }
            this.mapStringToHash.put(word1,unique1);
        }
        else
            unique1 = this.mapStringToHash.get(word1);

        HashSet<Character> unique2;
        if( !this.mapStringToHash.containsKey(word2)){
            unique2 = new HashSet<Character>();
            for(int i = 0; i < word2.length(); i++) {
                unique2.add(   word2.charAt(i));
            }
            this.mapStringToHash.put(word2,unique2);
        }
        else
            unique2 = this.mapStringToHash.get(word2);


        int intersection = 0;

        Iterator<Character> it = unique1.iterator();
        while( it.hasNext()){
            if( unique2.contains( it.next()))
                intersection++;
        }

        int union = word1.length() + word2.length() - intersection;
        return intersection * 1.0/ union;

    }
    public String prettyPrint(ArrayList<String> list){
        String result = "";
        for(int i = 1; i<= list.size(); i++){
            result += i;
            result += " ";
            result += list.get(i-1);
            result += "\n";
        }
        return result;
    }

    public class SimilarityComparator implements Comparator<WordSimilarity>{
        public int compare(WordSimilarity s1, WordSimilarity s2) {
            if (s1.similarity > s2.similarity)
                return 1;
            else if (s1.similarity < s2.similarity)
                return -1;
            return 0;
        }
    }
    class WordSimilarity {
        public String thisWord;
        public double similarity;

        public WordSimilarity(String thisWord, Double similarity) {

            this.thisWord = thisWord;
            this.similarity = similarity;
        }


    }
}


