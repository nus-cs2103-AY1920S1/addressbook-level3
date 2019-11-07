package seedu.module.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.predicate.LevenshteinDistanceChecker;

public class LevenshteinDistanceCheckerTest {

    @Test
    public void fuzzyContains_sameString_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("hello", "hello"));
    }

    @Test
    public void fuzzyContains_emptyString_returnsFalse() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertFalse(checker.fuzzyContains("hello", ""));
    }

    @Test
    public void fuzzyContains_oneDifference_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("hello", "hallo"));
    }

    @Test
    public void fuzzyContains_twoDifference_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("hello", "hailo"));
    }

    @Test
    public void fuzzyContains_threeDifference_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("hello", "haiio"));
    }

    @Test
    public void fuzzyContains_fourDifference_returnsFalse() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertFalse(checker.fuzzyContains("hello", "haiil"));
    }

    @Test
    public void fuzzyContains_oneAlphabetMissing_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("engineering", "enginering"));
    }

    @Test
    public void fuzzyContains_twoAlphabetMissing_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("engineering", "enginring"));
    }

    @Test
    public void fuzzyContains_threeAlphabetMissing_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("engineering", "engineeg"));
    }

    @Test
    public void fuzzyContains_fourAlphabetsMissing_returnsFalse() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertFalse(checker.fuzzyContains("engineering", "engiing"));
    }

    @Test
    public void fuzzyContains_fourAlphabetsChanged_returnsFalse() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertFalse(checker.fuzzyContains("engineering", "engiabcding"));
    }

    @Test
    public void fuzzyContains_threeSeparateAlphabetsChanged_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertTrue(checker.fuzzyContains("engineering", "ngnering"));
    }

    @Test
    public void fuzzyContains_longSentenceContainsKeywordWithMistake_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        String sentence = "This module introduces fundamental concepts of physics and is"
                + " illustrated with many practical examples. Topics covered include a) Electricity "
                + "and magnetism, where the basic concepts of electric and magnetic fields, forces on"
                + " charged particles, electric potential, electromotive force, work and energy, are"
                + " described. The properties of basic electrical circuits comprising resistors,"
                + " inductors and capacitors are discussed, along with analysis of their transient "
                + "and steady-state behaviour. Understanding the role of Maxwell's equations in"
                + " electromagnetism is emphasized; b) Waves, introducing properties of waves, "
                + "including geometric optics, propagation, interference and diffraction, and"
                + " electromagnetic waves; and c) Quantum physics, where new physics concepts "
                + "which led to the quantization of energy are introduced, leading to an"
                + " explanation of atomic transitions, atomic spectra and the physical and the"
                + " chemical properties of the atom. The uncertainty principle, wave-mechanics and"
                + " wave particle duality concepts are covered, together with the use of wavefunctions "
                + "in predicting the behaviour of trapped particles. The module is targeted essentially "
                + "at Engineering students.\n";
        assertTrue(checker.fuzzyContains(sentence.toLowerCase(), "quantm"));
    }

    @Test
    public void fuzzyContains_wordLengthBelowToleranceLevel_returnsFalse() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(3);
        assertFalse(checker.fuzzyContains("are", "aer"));
    }

    @Test
    public void fuzzyContains_wordLengthAboveToleranceLevel_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(2);
        assertTrue(checker.fuzzyContains("are", "aer"));
    }

    @Test
    public void fuzzyContains_twoWordsTwoMistakes_returnsTrue() {
        LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(2);
        assertTrue(checker.fuzzyContains("software engineering", "gineering"));
    }

}
