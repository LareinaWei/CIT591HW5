public class SpellCheckerRunner {

    public static void main(String[] args) {

        String fileDict = "engDictionary.txt";
        String fileBeforeCheck = "fileBeforeCheck.txt";
        int tolerance = 10;
        double commonPercent = 1;
        int topN = 10;

        SpellChecker go = new SpellChecker( fileDict , fileBeforeCheck , tolerance , commonPercent , topN );
        go.run();
    }
}
