package seedu.address.model.password.analyser.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class AdjacencyGraphTest {

    @Test
    void getNeighbors() {
        //alphabet
        List<Character> actualForAlphabet = AdjacencyGraph.getNeighbors('f');
        List<Character> expectedForAlphabet = new ArrayList<Character>(
                Arrays.asList('d', 'D', 'r', 'R', 't', 'T', 'g', 'G', 'v', 'V', 'c', 'C'));
        for (int i = 0; i < actualForAlphabet.size(); i++) {
            assertEquals(actualForAlphabet.get(i), expectedForAlphabet.get(i));
        }

        //numeral
        List<Character> actualForNumeral = AdjacencyGraph.getNeighbors('5');
        List<Character> expectedForNumeral = new ArrayList<Character>(
                Arrays.asList('4', '$', '6', '^', 't', 'T', 'r', 'R'));
        for (int i = 0; i < actualForNumeral.size(); i++) {
            assertEquals(actualForNumeral.get(i), expectedForNumeral.get(i));
        }

        //special
        List<Character> actualForSpecial = AdjacencyGraph.getNeighbors('~');
        List<Character> expectedForSpecial = new ArrayList<Character>(
                Arrays.asList('1', '!'));
        for (int i = 0; i < actualForSpecial.size(); i++) {
            assertEquals(actualForSpecial.get(i), expectedForSpecial.get(i));
        }
    }

    @Test
    void getDirections() {
        //one direction
        assertTrue(AdjacencyGraph.getDirections("asd") == 1);

        //multiple direction
        assertTrue(AdjacencyGraph.getDirections("qazxswedcvfr") == 7);
    }
}
