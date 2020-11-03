import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CheckFile {

    String fileName;
    Map<String, HashSet<Character>> mapStringToHash;

    public CheckFile(String fileName){
        this.fileName = fileName;
        this.mapStringToHash = new HashMap<>();

    }

    public ArrayList<String> getAllWords(){
        ArrayList<String> allWords = new ArrayList<>();
        try {
            File myObj = new File(this.fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String data = myReader.next();
                allWords.add(data);
            }
            myReader.close();


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return allWords;


    }

    public void writeFile(ArrayList<String> outArray, String fileName){
        String fileNameWrite = fileName.substring(0, fileName.length() - 4) + "_chk.txt";
        try {
            File myObj = new File(fileNameWrite);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        try {
            FileWriter writer = new FileWriter(fileNameWrite);
            for(String str: outArray) {
                writer.write(str + " ");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



}
