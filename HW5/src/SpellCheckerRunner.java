public class SpellCheckerRunner {
    /** the main function to start the code
     */
    public static void main(String[] args) {

        /**
         * all attributes should set for the code
         */
        String fileDict = "engDictionary.txt";
        String fileBeforeCheck = "fileBeforeCheck.txt";
        int tolerance = 10;
        double commonPercent = 0.1;
        int topN = 20;

        SpellChecker go = new SpellChecker( fileDict , fileBeforeCheck , tolerance , commonPercent , topN );
        go.run();

    /**
        String fileDict = "engDictionary.txt";
        WordRecommender wordRecommender = new WordRecommender(fileDict);

        double res = wordRecommender.getCommonPercent("committee","comet");
        System.out.println(res);
   **/


    }
}
