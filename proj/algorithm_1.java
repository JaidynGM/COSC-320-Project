import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class algorithm_1 {

    public static void main(String[] args) {
        /* parse the csv dataset and put tweets into txt file */
        // String inputFile = "com.handmark.tweetcaster.premium.csv";
        // String outputFile = "output.txt";
        // parseCSV(inputFile, outputFile);

        try {
            Map<String,String> hashMap = mapAcronyms("clean_AcronymsFile.csv"); //put acronyms and defintions in hashmap
            
            System.out.println();
            
            Long startTime1 = System.currentTimeMillis();
            parseTweets("tweets5.txt", "tweets5MODIFIED.txt", hashMap);
            Long endTime1 = System.currentTimeMillis();
            System.out.println("5 tweets Elapsed Time: "+(endTime1-startTime1));

            Long startTime2 = System.currentTimeMillis();
            parseTweets("tweets250.txt", "tweets250MODIFIED.txt", hashMap);
            Long endTime2 = System.currentTimeMillis();
            System.out.println("250 tweets Elapsed Time: "+(endTime2-startTime2));

            Long startTime3 = System.currentTimeMillis();
            parseTweets("tweets1000.txt", "tweets1000MODIFIED.txt", hashMap);
            Long endTime3 = System.currentTimeMillis();
            System.out.println("1000 tweets Elapsed Time: "+(endTime3-startTime3));

            Long startTime4 = System.currentTimeMillis();
            parseTweets("tweets3000.txt", "tweets3000MODIFIED.txt", hashMap);
            Long endTime4 = System.currentTimeMillis();
            System.out.println("3000 tweets Elapsed Time: "+(endTime4-startTime4));

            Long startTime5 = System.currentTimeMillis();
            parseTweets("tweetsFull.txt", "tweetsFullMODIFIED.txt", hashMap);
            Long endTime5 = System.currentTimeMillis();
            System.out.println("7881 tweets Elapsed Time: "+(endTime5-startTime5));
            
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void parseTweets(String inputTxtFile, String outputTxtFile,Map<String,String> hashMap){
        try (BufferedReader reader = new BufferedReader(new FileReader(inputTxtFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputTxtFile))){

                String line;

                while((line = reader.readLine()) != null){
                    String[] words = line.split(" "); //array of words
                    StringBuilder sb = new StringBuilder();
                    
                    for (String word : words) {
                        if(hashMap.containsKey(word.toLowerCase())){  //if the word is in acronym replace it
                            sb.append(hashMap.get(word.toLowerCase())).append(" ");
                        }else{
                            sb.append(word).append(" "); //else just append the word
                        }
                    }
                    writer.write(sb.toString().trim());
                    writer.newLine();
                }
                reader.close();
                writer.close();
                }catch(IOException e){
                System.out.println(e);
            }
    }

    public static Map<String, String> mapAcronyms(String fileName) throws IOException{
        Map<String,String> acronymMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        reader.readLine(); //skip header
        String line;

        while((line = reader.readLine()) != null){
            String[] parts = line.split("\t");
            if(parts.length >= 2){
                String acronym = parts[0];
                String definition = parts[1];
                acronymMap.put(acronym.toLowerCase(), definition.toLowerCase()); //add to hashMap in lowercase form
            }else{
                System.out.println("invalid line: "+line);
            }
            
        }
        reader.close();
        return acronymMap;
    }

    public static void parseCSV(String inputCSVFile, String outputTextFile){

        try (BufferedReader reader = new BufferedReader(new FileReader(inputCSVFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputTextFile))){

                reader.readLine(); //skip header

                String line;

                while((line = reader.readLine()) != null){
                    String[] fields = line.split(",",-1);
                    if(fields.length >= 4){
                        writer.write(fields[3] + "\n");
                    }
                }
                reader.close();
                writer.close();
                }catch(IOException e){
                System.out.println(e);
            }
    }
    
}
