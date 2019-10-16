package seedu.address.ui.autocomplete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class AutoComplete {

    private final int numOfSug = 10;

    private Word[] matchedResults;
    private boolean displayWeights;
    private AutocompleteModel apModel;

    public AutoComplete() {
        matchedResults = new Word[numOfSug];
        displayWeights = false;
    }

    /**
     * @param filePath path for the library of words
     * @return the suggestions to be shown in textField
     */
    private static Word[] readWordsFromFile(String filePath) {
        Word[] queries = null;
        try {
            BufferedReader reader0 = new BufferedReader(new FileReader(filePath));
            int lines = 0;
            while (reader0.readLine() != null) {
                lines++;
            }
            reader0.close();

            BufferedReader reader1 = new BufferedReader(new FileReader(filePath));
            queries = new Word[lines];
            for (int i = 0; i < lines; i++) {
                String line = reader1.readLine();
                if (line == null) {
                    System.err.println("Could not read line " + (i + 1) + " of " + filePath);
                    System.exit(1);
                }
                int tab = line.indexOf('\t');
                if (tab == -1) {
                    System.err.println("No tab character in line " + (i + 1) + " of " + filePath);
                    System.exit(1);
                }
                long weight = Long.parseLong(line.substring(0, tab).trim());
                String query = line.substring(tab + 1);
                queries[i] = new Word(query, weight);
            }
            reader1.close();
        } catch (Exception e) {
            System.err.println("Could not read or parse input file " + filePath);
            e.printStackTrace();
            System.exit(1);
        }

        return queries;
    }

    /**
     * Initialize an autocomplete model
     */
    private void initAP() {
        Word[] data = readWordsFromFile("/Users/apple/Downloads/autocomplete.txt");
        // Create the autocomplete object
        apModel = new AutocompleteModel(data);
    }

    /**
     * Makes a call to the implementation of AutocompleteModel to get
     * suggestions for the currently entered text.
     *
     * @param text string to search for
     */
    public List<String> getSuggestions(String text) {

        initAP();
        List<String> suggestions = new ArrayList<>();
        // don't search for suggestions if there is no input
        if (text.length() > 0) {
            // get all matching words
            Word[] allResults = apModel.allMatches(text);
            if (allResults == null) {
                throw new NullPointerException("allMatches() is null");
            }

            if (allResults.length > numOfSug) {
                matchedResults = Arrays.copyOfRange(allResults, 0, numOfSug);
            } else {
                matchedResults = allResults;
            }

            if (!displayWeights) {
                for (Word matchedResult : matchedResults) {
                    suggestions.add(matchedResult.getQuery());
                }
            }
        }
        return suggestions;
    }
}
