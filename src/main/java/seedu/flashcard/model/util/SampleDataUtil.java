package seedu.flashcard.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * Generate the sample data to make the default flashcard list nicer.
 */
public class SampleDataUtil {

    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[]{
            new McqFlashcard(new Question("Which of the following is true about compactness?"),
                getChoiceList("The real number space is a compact space",
                    "The complex plane is a compact space",
                    "A compact subspace of a compact metric space must be closed",
                    "A closed subspace of a compact metric space must be compact"),
                new Definition("For any open covering of a metric space, there is a finite subcover."),
                     getTagSet("mathematics", "metric space"),
                new Answer("A compact subspace of a compact metric space must be closed")),
            new McqFlashcard(new Question("Which of the following is true about completeness?"),
                getChoiceList("The rational number space is a complete space",
                    "Compact spaces are always complete",
                    "A complete subspace of a complete space myst be totally bounded",
                    "Closed subspaces in a complete metric space is also complete"),
                new Definition("Any Cauchy sequence converges in this metric space"),
                getTagSet("mathematics", "metric space"),
                new Answer("Closed subspaces in a complete metric space is also complete")),
            new ShortAnswerFlashcard(new Question("What is the 11611 meters tunnel on the border of France and Italy?"),
                new Definition("An amazing tunnel in Europe."),
                getTagSet("geography", "favorite"),
                new Answer("Mount Blanc Tunnel")),
            new ShortAnswerFlashcard(new Question("What is the largest known living organism?"),
                new Definition("It is an aspen grove in Utah."),
                getTagSet("trivia", "biology"),
                new Answer("Pando")),
            new McqFlashcard(new Question("What does M & M stand for?"),
                getChoiceList("Matthew & Melvin",
                        "Mars & Melvin",
                        "Mars & Murrie"),
                new Definition("They are names of the two sons of the Mars Company founder "
                        + "and the Hershey Chocolate's president respectively."),
                getTagSet("trivia"),
                new Answer("Mars & Murrie")),
            new McqFlashcard(new Question("Which of the following describes the service-oriented architecture style?"),
                getChoiceList("Divides the workload of the system down to a number of transactions which "
                                + "are then given to a dispatcher that controls the execution of each transaction. ",
                        "Builds applications by combining functionalities packaged as programmatically "
                                + "accessible services.",
                        "Controls the flow of the application by detecting events "
                                + "from event emitters and communicating those events to interested event consumers"),
                new Definition("Covered in Week 11."),
                getTagSet("cs2103", "design"),
                new Answer("Builds applications by combining functionalities packaged as programmatically "
                        + "accessible services.")),
            new McqFlashcard(new Question("Which of the following describes the event-driven style?"),
                getChoiceList("Divides the workload of the system down to a number of transactions which "
                                + "are then given to a dispatcher that controls the execution of each transaction. ",
                        "Builds applications by combining functionalities packaged as programmatically "
                                + "accessible services.",
                        "Controls the flow of the application by detecting events "
                                + "from event emitters and communicating those events to interested event consumers"),
                new Definition("Covered in Week 11."),
                getTagSet("cs2103", "design"),
                new Answer("Controls the flow of the application by detecting events "
                        + "from event emitters and communicating those events to interested event consumers")),
            new McqFlashcard(new Question("Which of the following describes the transaction processing style?"),
                getChoiceList("Divides the workload of the system down to a number of transactions which "
                                + "are then given to a dispatcher that controls the execution of each transaction.",
                        "Builds applications by combining functionalities packaged as programmatically "
                                + "accessible services.",
                        "Controls the flow of the application by detecting events "
                                + "from event emitters and communicating those events to interested event consumers"),
                new Definition("Covered in Week 11."),
                getTagSet("cs2103", "design"),
                new Answer("Divides the workload of the system down to a number of transactions which are "
                        + "then given to a dispatcher that controls the execution of each transaction.")),
            new ShortAnswerFlashcard(new Question("Which test case design type designs test cases "
                    + "exclusively based on the SUT’s specified external behavior?"),
                new Definition("Covered in Week 10."),
                getTagSet("cs2103", "test case design"),
                new Answer("Black-box approach")),
            new ShortAnswerFlashcard(new Question("Which test case design type designs test cases "
                    + "based on what is known about the SUT’s implementation?"),
                new Definition("Covered in Week 10."),
                getTagSet("cs2103", "test case design"),
                new Answer("White-box approach")),
            new ShortAnswerFlashcard(new Question("What is the name given to the lower register of"
                    + " the clarinet's playing range?"),
                new Definition("Range of E3 to Bb4"),
                getTagSet("music", "trivia"),
                new Answer("Chalumeau")),
            new McqFlashcard(new Question("Who composed the New World Symphony?"),
                getChoiceList("Brahms", "Mozart", "Dvorak", "Beethoven"),
                new Definition("It is also called Symphony No.9 in E minor"),
                getTagSet("music", "trivia"),
                new Answer("Dvorak")),
            new ShortAnswerFlashcard(new Question("Which of Tchaikovsky's overture commemorated "
                    + "Napolean's retreat from Moscow?"),
                new Definition("It was written in 1880 and consisted of cannon fire."),
                getTagSet("music"),
                new Answer("1812 Overture")),
            new McqFlashcard(new Question("Which of these mean: easily angered?"),
                getChoiceList("Irascible", "Invective", "Inveterate", "Irreverence"),
                new Definition("SAT vocabulary word"),
                getTagSet("english", "SAT"),
                new Answer("Irascible")),
            new McqFlashcard(new Question("Which of these mean: an angry verbal attack?"),
                getChoiceList("Irascible", "Invective", "Inveterate", "Irreverence"),
                new Definition("SAT vocabulary word"),
                getTagSet("english", "SAT"),
                new Answer("Invective")),
            new McqFlashcard(new Question("Which of these mean: disrespect?"),
                getChoiceList("Irascible", "Invective", "Inveterate", "Irreverence"),
                new Definition("SAT vocabulary word"),
                getTagSet("english", "SAT"),
                new Answer("Irreverence")),
            new McqFlashcard(new Question("Which of these mean: stubbornly established by a habit?"),
                getChoiceList("Irascible", "Invective", "Inveterate", "Irreverence"),
                new Definition("SAT vocabulary word"),
                getTagSet("english", "SAT"),
                new Answer("Inveterate")),
            new ShortAnswerFlashcard(new Question("Which design principle states that "
                    + "a class should only have one and only one reason to change?"),
                new Definition("If a class has only one responsibility, it needs to change only when "
                        + "there is a change to that responsibility."),
                getTagSet("cs2103", "design"),
                new Answer("Single Responsibility Principle")),
            new ShortAnswerFlashcard(new Question("Which design principle states that "
                    + "a module should be open for extension but closed for modification?"),
                new Definition("Modules should be written so that they can be extended, "
                        + "without requiring them to be modified."),
                getTagSet("cs2103", "design"),
                new Answer("Open-Close Principle")),
            new ShortAnswerFlashcard(new Question("Which principle states that "
                    + "no client should be forced to depend on methods it does not use?"),
                new Definition("Covered in Week 9."),
                getTagSet("cs2103"),
                new Answer("Interface Segregation Principle")),
        };
    }

    public static ReadOnlyFlashcardList getSampleFlashcardList() {
        FlashcardList samplefl = new FlashcardList();
        for (Flashcard flashcard : getSampleFlashcards()) {
            samplefl.addFlashcard(flashcard);
        }
        return samplefl;
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
     * Returns a choice set containing the list of strings given.
     */
    public static List<Choice> getChoiceList(String... strings) {
        return Arrays.stream(strings)
                .map(Choice::new)
                .collect(Collectors.toList());
    }


}
