import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellChecker {

    String fileDict;
    String fileBeforeCheck;
    int tolerance;
    double commonPercent;
    int topN;

    public SpellChecker(String fileDict, String fileBeforeCheck,int tolerance,double commonPercent,int topN){
        this.fileDict = fileDict;
        this.fileBeforeCheck = fileBeforeCheck;
        this.tolerance = tolerance;
        this.commonPercent = commonPercent;
        this.topN = topN;
    }
    /** run the project
     */
    public void run() {
        WordRecommender wordRecommender = new WordRecommender(this.fileDict);
        HashSet<String> wordDict = wordRecommender.getDict();

        ArrayList<String> wordBeforeCheck = ReadWriteFile.getAllWords(this.fileBeforeCheck);

        int size = wordBeforeCheck.size();
        for(int i = 0; i< size; i++){
            String wordNow =  wordBeforeCheck.get(i);

            /**
             * if the word is correct, continue
             */
            if( wordDict.contains( wordNow))
                continue;

            System.out.println("The word " + wordNow+" is misspelled.");
            System.out.println("The following suggestions are available");
            ArrayList<String> wordChoices = wordRecommender.getWordSuggestions(wordNow,this.tolerance,this.commonPercent, this.topN);
            System.out.println(wordRecommender.prettyPrint(  wordChoices ) );

            System.out.println("Press ‘r’ for replace, ‘a’ for accept as is, ‘t’ for type in manually.");

            String[] choices = {"r","a","t"};
            String response1 = getResponse(choices);

            /**
             * the case when the user choose r
             */
            if(response1.equals("r")){
                System.out.println("Your word will now be replaced with one of the suggestions Enter the number corresponding to the word that you want to use for replacement.");
                choices = new String[wordChoices.size()];
                for(int j = 1; j<= wordChoices.size() ;j++)
                    choices[j-1] = "" +j;
                String response2 = getResponse(choices);
                int change = Integer.parseInt(response2);
                wordBeforeCheck.set(i,wordChoices.get(change-1));
            }

            /**
             * the case when the user choose t
             */
            if(response1.equals("t")){
                System.out.println("Please type the word that will be used as the replacement in the output file.");
                Scanner scan = new Scanner(System.in);
                wordBeforeCheck.set(i,scan.next());
            }
        }

        System.out.println("All words checked");
        ReadWriteFile.writeFile(wordBeforeCheck,this.fileBeforeCheck);
    }

    /** get the valid response from the user
     * @param choices the choices users can choose from
     * @return the valid response from the user
     **/
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
