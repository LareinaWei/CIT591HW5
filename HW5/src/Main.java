import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String fileDict = "engDictionary.txt";
        WordRecommender wordRecommender = new WordRecommender(fileDict);
        HashSet<String> wordDict = wordRecommender.getDict();


        String fileBeforeCheck = "fileBeforeCheck.txt";
        CheckFile checkFile = new CheckFile(fileBeforeCheck);
        ArrayList<String> wordBeforeCheck = checkFile.getAllWords();
        //ArrayList<String> wordAfterCheck =


        int tolerance = 10;
        double commonPercent = 1;
        int topN = 10;

        int size = wordBeforeCheck.size();
        for(int i = 0; i< size; i++){
           System.out.println(wordBeforeCheck.size());
            String wordNow =  wordBeforeCheck.get(i);
            if( wordDict.contains( wordNow))
                continue;

            System.out.println("The word " + wordNow+" is misspelled.");
            System.out.println("The following suggestions are available");
            ArrayList<String> wordChoices = wordRecommender.getWordSuggestions(wordNow,tolerance, commonPercent, topN);
            System.out.println(wordRecommender.prettyPrint(  wordChoices ) );

            System.out.println("Press ‘r’ for replace, ‘a’ for accept as is, ‘t’ for type in manually.");

            String[] choices = {"r","a","t"};
            String response1 = getResponse(choices);

            if(response1.equals("r")){
                System.out.println("Your word will now be replaced with one of the suggestions Enter the number corresponding to the word that you want to use for replacement.");
                choices = new String[wordChoices.size()];
                for(int j = 1; j<= wordChoices.size() ;j++)
                    choices[j-1] = "" +j;
                String response2 = getResponse(choices);
                int change = Integer.parseInt(response2);
                wordBeforeCheck.set(i,wordChoices.get(change-1));
            }

            if(response1.equals("t")){
                System.out.println("Please type the word that will be used as the replacement in the output file.");
                Scanner scan = new Scanner(System.in);
                wordBeforeCheck.set(i,scan.next());
            }


        }

        System.out.println("All words checked");
        checkFile.writeFile(wordBeforeCheck,fileBeforeCheck);







    }
    static String getResponse(String[] choices){
        Scanner scan = new Scanner(System.in);
        while(true) {
            String response = scan.next();
            for(int i = 0; i< choices.length ; i++)
                if (response.equals( choices[i]))
                   return response;

            System.out.println("Please type in valid choice");

        }
    }
}
