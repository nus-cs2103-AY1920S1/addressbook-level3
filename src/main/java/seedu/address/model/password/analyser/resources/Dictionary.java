package seedu.address.model.password.analyser.resources;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import seedu.address.model.password.exceptions.DictionaryNotFoundException;

/**
 * Represents {@code Dictionary} that maps a given {@code String} to it's rank.
 */
public class Dictionary {
    private static final String MESSAGE_DICTIONARY_NOT_FOUND = "Requested dictionary not found. Something went wrong. "
            + "Please download the jar file again.";
    private String name;
    private final Map<String, Integer> dictionary;

    /**
     * Constructs dictionary object with specific name and dictionary.
     *
     * @param name name of dictionary object
     * @param dictionary the hashmap of dictionary word to ranking
     */
    public Dictionary(String name, Map<String, Integer> dictionary) {
        requireNonNull(name);
        requireNonNull(dictionary);
        this.name = name;
        this.dictionary = dictionary;
    }

    /**
     * Builds the required dictionary based on the specified dictionary name.
     *
     * @param name the name of the required dictionary
     * @return the dictionary object
     */
    public static Dictionary build(String name) throws DictionaryNotFoundException {
        switch (name) {
        case "passwords.txt":
            return new Dictionary(name, load("/dictionaries/passwords.txt"));
        default:
            throw new DictionaryNotFoundException(MESSAGE_DICTIONARY_NOT_FOUND);
        }
    }

    /**
     * Loads the content of the file from the path into the dictionary.
     * @param path the path of the file with dictionary words
     * @return the hashmap of dictionary word to ranking
     */
    static Map<String, Integer> load(String path) {
        assert !path.isEmpty();
        Map<String, Integer> dictionary = new HashMap<>();
        try {
            InputStream is = Dictionary.class.getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                dictionary.put(line, i++);
            }
        } catch (IOException | NullPointerException e) {
            throw new DictionaryNotFoundException(MESSAGE_DICTIONARY_NOT_FOUND);
        }
        return dictionary;
    }

    /**
     * Looks up the rank of the given {@code String} in the {@code Hashmap}.
     * @param s the given string
     * @return the rank of the given string in the dictionary.
     */
    public Integer getRank(String s) {
        return dictionary.get(s);
    }
}
