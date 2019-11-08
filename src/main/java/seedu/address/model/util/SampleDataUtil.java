package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.StudyBuddyPro;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Title;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code StudyBuddyPro} with sample data.
 */
public class SampleDataUtil {
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(new Question("What is the IntelliJ keyboard shortcut to search for a file in the project "
                    + "directory?"), new Answer("Ctrl + Shift + N"), new Title("IntelliJ Shortcut Question 1"),
                getTagSet("IntelliJ", "Shortcuts")),
            new Flashcard(new Question("What is the IntelliJ keyboard shortcut to search for a text occurrence in the"
                + " project directory?"), new Answer("Ctrl + Shift + F"), new Title("IntelliJ Shortcut Question 2"),
                getTagSet("IntelliJ", "Shortcuts")),
            new Flashcard(new Question("What is the IntelliJ keyboard shortcut to view recently opened files?"),
                    new Answer("Ctrl + E"), new Title("IntelliJ Shortcut Question 3"),
                    getTagSet("IntelliJ", "Shortcuts")),
            new Flashcard(new Question("What is the IntelliJ keyboard shortcut to reformat the whole file or the "
                    + "selected fragment according to the current code style settings."),
                    new Answer("Ctrl + Alt + L"), new Title("IntelliJ Shortcut Question 4"),
                    getTagSet("IntelliJ", "Shortcuts")),
            new Flashcard(new Question("What is the IntelliJ keyboard shortcut to navigate to the initial declaration"
                    + " of the instantiated class, called method, or field?"),
                    new Answer("Ctrl + B"), new Title("IntelliJ Shortcut Question 5"),
                    getTagSet("IntelliJ", "Shortcuts")),
            new Flashcard(new Question("What does the SLAP acronym stand for?"), new Answer("Single Level of "
                    + "Abstraction Principle"), new Title("Basic SE Principle 1"), getTagSet("SE",
                    "Principles")),
            new Flashcard(new Question("What does the Law of Demeter state?"), new Answer("An object should have "
                    + "limited knowledge of another object."),
                    new Title("Basic SE Principle 2"), getTagSet("SE", "Principles")),
            new Flashcard(new Question("What does the Open-Closed Principle state?"), new Answer("A module should be "
                    + "open for extension but closed for modification."),
                    new Title("Basic SE Principle 3"), getTagSet("SE", "Principles")),
            new Flashcard(new Question("What does the Single Responsibility Principle state?"),
                    new Answer("A class should have one, and only one, reason to change."),
                    new Title("Basic SE Principle 4"), getTagSet("SE", "Principles")),
            new Flashcard(new Question("What does the Liskov Substitution Principle state?"),
                    new Answer("Derived classes must be substitutable for their base classes."),
                    new Title("Basic SE Principle 5"), getTagSet("SE", "Principles")),
            new Flashcard(new Question("What does the Interface Segregation Principle state?"),
                    new Answer("No client should be forced to depend on methods it does not use."),
                    new Title("Basic SE Principle 6"), getTagSet("SE", "Principles")),
        };
    }

    public static ReadOnlyStudyBuddyPro getSampleStudyBuddyPro() {
        StudyBuddyPro sampleSbp = new StudyBuddyPro();
        for (Flashcard sampleFlashcard : getSampleFlashcards()) {
            sampleSbp.addFlashcard(sampleFlashcard);
        }
        return sampleSbp;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a content set containing the list of strings given.
     */
    public static Set<Content> getContentSet(String... strings) {
        return Arrays.stream(strings)
                .map(x -> new Content(x, new HashSet<Tag>()))
                .collect(Collectors.toSet());
    }
}
