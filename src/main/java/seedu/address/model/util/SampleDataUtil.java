package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Title;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code StudyBuddyPro} with sample data.
 */
public class SampleDataUtil {
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(new Question("What does the SLAP acronym stand for?"), new Answer("Single Level of "
                    + "Abstraction Principle"), new Title("Basic SE Principle 1"), getTagSet("CS2103T",
                    "Principles", "Important")),
            new Flashcard(new Question("What does the Law of Demeter state?"), new Answer("An object should have "
                    + "limited knowledge of another object."),
                    new Title("Basic SE Principle 2"), getTagSet("CS2103T", "Principles", "Important")),
            new Flashcard(new Question("What does the Open-Closed Principle state?"), new Answer("A module should be "
                    + "open for extension but closed for modification."),
                    new Title("Basic SE Principle 3"), getTagSet("CS2103T", "Principles")),
            new Flashcard(new Question("What does the Single Responsibility Principle state?"),
                    new Answer("A class should have one, and only one, reason to change."),
                    new Title("Basic SE Principle 4"), getTagSet("CS2103T", "Principles", "Important")),
            new Flashcard(new Question("What does the Liskov Substitution Principle state?"),
                    new Answer("Derived classes must be substitutable for their base classes."),
                    new Title("Basic SE Principle 5"), getTagSet("CS2103T", "Principles")),
            new Flashcard(new Question("What does the Interface Segregation Principle state?"),
                    new Answer("No client should be forced to depend on methods it does not use."),
                    new Title("Basic SE Principle 6"), getTagSet("CS2103T", "Principles")),
            new Flashcard(new Question("What is the IntelliJ keyboard shortcut to search for a file in the project "
                    + "directory?"), new Answer("Ctrl + Shift + N"), new Title("IntelliJ Shortcut Question 1"),
                    getTagSet("IntelliJ", "Shortcuts")),
            new Flashcard(new Question("What is the IntelliJ keyboard shortcut to search for a text occurrence in the"
                    + " project directory?"), new Answer("Ctrl + Shift + F"), new Title("IntelliJ Shortcut Question 2"),
                    getTagSet("IntelliJ", "Shortcuts", "Important")),
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
                    getTagSet("IntelliJ", "Shortcuts"))
        };
    }

    public static Note[] getSampleNotes() {
        return new Note[] {
            new Note(new seedu.address.model.note.Title("Processor Pipelining"),
                    new Content("Pipelining is the process of accumulating instruction from the processor through a "
                            + "pipeline. It allows storing and executing instructions in an orderly process."),
                    getTagSet("CS2100", "Pipelining")),
            new Note(new seedu.address.model.note.Title("MIPS Processor"),
                    new Content("MIPS is a simplified microprocessor that is used in most teaching courses."),
                    getTagSet("CS2100")),
            new Note(new seedu.address.model.note.Title("Pipelining Stages"),
                    new Content("The pipelining process involves five stages: /* C/Fetch, Decode, Execute, "
                            + "Memory, Write TAG/Pipelining TAG/Important */."),
                    getTagSet("CS2100", "Pipelining")),
            new Note(new seedu.address.model.note.Title("Sum of Products and Product of Sums"),
                    new Content("The SOP and POS are used in simplifying circuits. SOP refers to the format 'A.B +"
                            + " B.C', and POS refers to the format 'A+B . B+C'"),
                    getTagSet("CS2100", "LogicGates")),
            new Note(new seedu.address.model.note.Title("Boolean Algebra"),
                    new Content("Boolean algebra refers to the operations you can do with true/false variables."),
                    getTagSet("CS2100", "LogicGates")),
            new Note(new seedu.address.model.note.Title("K-Maps"),
                    new Content("K-Maps are designed to reduce complicated boolean expressions into simpler forms"
                            + ". They are done by drawing a table, where every variable is true in half of the "
                            + "cells and false in the other half. /* C/Each adjacent pair of cells in a K-Map "
                            + "differs by exactly one boolean variable. TAG/KMaps TAG/Important */"),
                    getTagSet("CS2100", "KMaps")),
            new Note(new seedu.address.model.note.Title("UML Diagrams"),
                    new Content("UML diagrams (or /* C/Unified Markup Language TAG/UML */) are diagrams that "
                            + "display the architecture of a software application. Examples of these diagrams "
                            + "include /* C/class diagrams, object diagrams, sequence diagrams, and activity "
                            + "diagrams. TAG/UML TAG/CS2103T TAG/Important */"),
                    getTagSet("CS2103T")),
            new Note(new seedu.address.model.note.Title("Sequence Diagrams"),
                    new Content("Sequence diagrams show the flow of a particular program execution from start to "
                            + "finish. They should be drawn at a suitable level of abstraction."),
                    getTagSet("CS2103T", "UML")),
            new Note(new seedu.address.model.note.Title("Association"),
                    new Content("Two classes are connected with a solid line if they are mutually aware of each "
                            + "other."),
                    getTagSet("CS2103T", "UML", "Important")),
            new Note(new seedu.address.model.note.Title("CS2103T Revision Topics"),
                    new Content("Need to study UML class diagrams and take better note of SOLID design principles."),
                    getTagSet("CS2103T", "ToDo")),
            new Note(new seedu.address.model.note.Title("CS2100 Revision Topics"),
                    new Content("Need to study pipelining and the solutions provided for stalling."),
                    getTagSet("CS2100", "ToDo"))
        };
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
    public static Set<seedu.address.model.cheatsheet.Content> getContentSet(String... strings) {
        return Arrays.stream(strings)
                .map(x -> new seedu.address.model.cheatsheet.Content(x, new HashSet<Tag>()))
                .collect(Collectors.toSet());
    }

    public static Tag[] getSampleFlashcardTags() {
        return new Tag[] {
            new Tag("IntelliJ"),
            new Tag("Shortcuts"),
            new Tag("CS2103T"),
            new Tag("Principles"),
            new Tag("Important")
        };
    }

    public static Tag[] getSampleNotesTags() {
        return new Tag[] {
            new Tag("Important"),
            new Tag("CS2100"),
            new Tag("Pipelining"),
            new Tag("LogicGates"),
            new Tag("KMaps"),
            new Tag("UML"),
            new Tag("CS2103T"),
            new Tag("Todo")
        };
    }

    public static Tag[] getSampleCheatSheetTags() {
        /*todo Jasmine: Make use of the 'important' tag in Flashcards and Notes to make a cheatsheet.
           You can make two cheatsheets: one with 'CS2103T' and 'Important', and the other with just 'Important'.
         */
        return new Tag[]{};
    }
}
