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
        // String inputFile = "proj/training.1600000.processed.noemoticon.csv";
        // String outputFile = "proj/output.txt";
        // parseCSV(inputFile, outputFile);

        try {
            Map<String, String> hashMap = mapAcronyms("proj/clean_AcronymsFile.csv"); // put acronyms and defintions in
            // hashmap

            System.out.println();

            Long startTime1 = System.currentTimeMillis();
            parseTweets("proj/tweets3175.txt", "proj/modified_tweets/3175.txt", hashMap);
            Long endTime1 = System.currentTimeMillis();
            System.out.println("3175 tweets Elapsed Time: " + (endTime1 - startTime1) + "ms");

            Long startTime2 = System.currentTimeMillis();
            parseTweets("proj/tweets6250.txt", "proj/modified_tweets/6250.txt", hashMap);
            Long endTime2 = System.currentTimeMillis();
            System.out.println("6250 tweets Elapsed Time: " + (endTime2 - startTime2) + "ms");

            Long startTime3 = System.currentTimeMillis();
            parseTweets("proj/tweets12500.txt", "proj/modified_tweets/12500.txt", hashMap);
            Long endTime3 = System.currentTimeMillis();
            System.out.println("12500 tweets Elapsed Time: " + (endTime3 - startTime3) + "ms");

            Long startTime4 = System.currentTimeMillis();
            parseTweets("proj/tweets25000.txt", "proj/modified_tweets/25000.txt", hashMap);
            Long endTime4 = System.currentTimeMillis();
            System.out.println("25000 tweets Elapsed Time: " + (endTime4 - startTime4) + "ms");

            Long startTime5 = System.currentTimeMillis();
            parseTweets("proj/tweets50000.txt", "proj/modified_tweets/50000.txt", hashMap);
            Long endTime5 = System.currentTimeMillis();
            System.out.println("50000 tweets Elapsed Time: " + (endTime5 - startTime5) + "ms");

            Long startTime6 = System.currentTimeMillis();
            parseTweets("proj/tweets100000.txt", "proj/modified_tweets/100000.txt", hashMap);
            Long endTime6 = System.currentTimeMillis();
            System.out.println("100000 tweets Elapsed Time: " + (endTime6 - startTime6) + "ms");

            Long startTime7 = System.currentTimeMillis();
            parseTweets("proj/tweets200000.txt", "proj/modified_tweets/200000.txt", hashMap);
            Long endTime7 = System.currentTimeMillis();
            System.out.println("200000 tweets Elapsed Time: " + (endTime7 - startTime7) + "ms");

            Long startTime8 = System.currentTimeMillis();
            parseTweets("proj/tweets400000.txt", "proj/modified_tweets/400000.txt", hashMap);
            Long endTime8 = System.currentTimeMillis();
            System.out.println("400000 tweets Elapsed Time: " + (endTime8 - startTime8) + "ms");

            Long startTime9 = System.currentTimeMillis();
            parseTweets("proj/tweets800000.txt", "proj/modified_tweets/800000.txt", hashMap);
            Long endTime9 = System.currentTimeMillis();
            System.out.println("800000 tweets Elapsed Time: " + (endTime9 - startTime9) + "ms");

            Long startTime10 = System.currentTimeMillis();
            parseTweets("proj/tweets1600000.txt", "proj/modified_tweets/1600000.txt", hashMap);
            Long endTime10 = System.currentTimeMillis();
            System.out.println("1600000 tweets Elapsed Time: " + (endTime10 - startTime10) + "ms");

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void parseTweets(String inputTxtFile, String outputTxtFile, Map<String, String> hashMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputTxtFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputTxtFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" "); // array of words
                StringBuilder sb = new StringBuilder();

                for (String word : words) {
                    if (hashMap.containsKey(word.toLowerCase())) { // if the word is in acronym replace it
                        sb.append(hashMap.get(word.toLowerCase())).append(" ");
                    } else {
                        sb.append(word).append(" "); // else just append the word
                    }
                }
                writer.write(sb.toString().trim());
                writer.newLine();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // preprocessing, map the acronyms into the hashmap
    public static Map<String, String> mapAcronyms(String fileName) throws IOException {
        Map<String, String> acronymMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        reader.readLine(); // skip header
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length >= 2) {
                String acronym = parts[0];
                String definition = parts[1];
                acronymMap.put(acronym.toLowerCase(), definition.toLowerCase()); // add to hashMap in lowercase form
            } else {
                // System.out.println("invalid line: " + line);
            }

        }
        reader.close();
        return acronymMap;
    }

    // preprocessing, parse the csv and obtain only the tweets
    public static void parseCSV(String inputCSVFile, String outputTextFile) {

        try (BufferedReader reader = new BufferedReader(new FileReader(inputCSVFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputTextFile))) {

            // reader.readLine(); // skip header

            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length >= 4) {
                    writer.write(fields[5] + "\n");
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
