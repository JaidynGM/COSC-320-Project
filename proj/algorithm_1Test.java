import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class algorithm_1Test {

    @Test
    public void testParseTweets() {
        String inputTxtFile = "test_input.txt";
        String outputTxtFile = "test_output.txt";

        // Create a map of acronyms and their definitions
        Map<String, String> acronyms = new HashMap<>();
        acronyms.put("lol", "laugh out loud");
        acronyms.put("btw", "by the way");
        acronyms.put("idk", "I don't know");

        // Write some test data to the input file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputTxtFile))) {
            writer.write("Here's a tweet lol\n");
            writer.write("I wonder what btw means\n");
            writer.write("idk what to do with this tweet\n");
        } catch (IOException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // Call the parseTweets method
        algorithm_1.parseTweets(inputTxtFile, outputTxtFile, acronyms);

        // Read the output file and check the results
        try (BufferedReader reader = new BufferedReader(new FileReader(outputTxtFile))) {
            assertEquals("Here's a tweet laugh out loud", reader.readLine());
            assertEquals("I wonder what by the way means", reader.readLine());
            assertEquals("I don't know what to do with this tweet", reader.readLine());
            assertNull("There should be no more lines in the file", reader.readLine());
        } catch (IOException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // Delete the test files
        new File(inputTxtFile).delete();
        new File(outputTxtFile).delete();
    }
}
