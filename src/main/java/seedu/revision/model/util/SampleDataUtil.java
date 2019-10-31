package seedu.revision.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.revision.model.AddressBook;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Answerable[] getSampleAnswerables() {
        return new Answerable[] {
            new Mcq(new Question("What type of project is AB3?"),
                    new ArrayList<>(Arrays.asList(new Answer("Brownfield"))),
                    new ArrayList<>(Arrays.asList(new Answer("Greenfield"), new Answer("Blackfield"),
                            new Answer("Whitefield"))),
                    new Difficulty("1"), getCategorySet("introduction", "Week 2")),

            new Mcq(new Question("Which of the following is a delight of Software Engineering?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("What you get of making things that are useful to others"))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("One must perform perfectly"),
                            new Answer("One rarely controls the circumstances of his work or goal"),
                            new Answer("The dependence upon others"))),
                    new Difficulty("1"), getCategorySet("introduction", "pros and cons", "Week 2")),

            new Mcq(new Question("Which of the following is a woe of Software Engineering?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("Finding nitty little bugs"))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("What you get of having to solve puzzles"),
                            new Answer("What you get of working in such a tractable medium"),
                            new Answer("What you get of having to keep learning always"))),
                    new Difficulty("1"), getCategorySet("introduction", "pros and cons", "Week 2")),

            new Mcq(new Question("What does IDE stand for?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("Integrated Development Environment"))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("Integrated Development Editor"),
                            new Answer("Interchangeable Development Environment"),
                            new Answer("Integrated Development Enhancement"))),
                    new Difficulty("1"), getCategorySet("implementation", "IDEs", "basic", "Week 2")),

            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("Test cases can only be determined "
                                    + "by reviewing similar existing systems"))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("SUT stands for Software Under Test"),
                            new Answer("A test case failure is a mismatch between the actual behaviour "
                                    + "and the expected behaviour"),
                            new Answer("A test case failure may not always indicate a bug/defect in the SUT"))),
                    new Difficulty("3"), getCategorySet("quality assurance", "testing", "Week 2")),

            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("A regression is an unintended and undesirable side effect "
                                    + "of a modification"))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("Regression testing is the re-testing of the "
                                    + "software to fix regression bugs"),
                            new Answer("Regression testing must be automated"),
                            new Answer("Regression testing is less effective when done frequently"))),
                    new Difficulty("2"), getCategorySet("quality assurance", "testing", "Week 2")),

            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("Downloading a copy of a remote repo to your computer is "
                                    + "called forking"))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("A local repo can pull from multiple remote repositories"),
                            new Answer("It is possible to set up a Git server on your own computer"),
                            new Answer("A pull request is mechanism for contributing code to a remote repo."))),
                    new Difficulty("3"), getCategorySet("project management", "revision control", "Week 2")),


            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("To see the working folder at specific commit, "
                                    + "we ‘revert’ to that commit."))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("The git history only contains specific points that you "
                                    + "committed to the history."),
                            new Answer("After you initialize a git repo , all files in that folder are "
                                    + "automatically tracked by Git."),
                            new Answer("Git can show you the difference between two adjacent commits in the "
                                    + "version history."))),
                    new Difficulty("3"), getCategorySet("project management", "revision control", "Week 2")),

            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("One aim of adopting a coding standard is to make the "
                                    + "entire code base look like it was written by one person."))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("A developer should understand the importance of "
                                    + "following a coding standard. However, there is no need to follow one."),
                            new Answer("It is better if each developer followed their own style of coding "
                                    + "so that the code can be traced to the author easily."),
                            new Answer("A coding standard is universal and not specific to any programming language"))),
                    new Difficulty("2"), getCategorySet("implementation", "code quality", "Week 3")),

            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("There are tools that can help to enforce some parts of a "
                                    + "coding standard e.g. indentation rules."))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("It is better if each developer followed their "
                                    + "own style of coding so that the code can be traced to the author easily."),
                            new Answer("A developer should understand the importance of following a coding standard. "
                                    + "However, there is no need to follow one."),
                            new Answer("Company's coding standard can be vastly different from typical "
                                    + "industry practices as long as it is consistent throughout the whole company"))),
                    new Difficulty("2"), getCategorySet("implementation", "code quality", "Week 3")),

            new TrueFalse(new Question("A UML sequence diagram can capture the interactions between multiple "
                    + "objects for a given scenario, for example, an object calling a method of "
                    + "another object."),
                    new ArrayList<>(Arrays.asList(new Answer("true"))),
                    new Difficulty("1"), getCategorySet("UML", "Week 5")),

            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("Git uses a serial no. matching the exact nano-second a commit "
                                    + "was created to identify a commit."))),
                    new ArrayList<>(Arrays.asList(new Answer("After you initialize a git repo in a folder, "
                                    + "all files in that folder are automatically tracked by Git."),
                            new Answer("The git history does not contain everything that happened to the "
                                    + "tracked files. It only contains specific points that you committed to "
                                    + "the history."),
                            new Answer("Git can show you the difference between two adjacent commits in the "
                                    + "version history."))),
                    new Difficulty("3"), getCategorySet("project management", "revision control", "Week 2")),

            new TrueFalse(new Question("As per the textbook, a log file is like the auto-pilot system of an "
                    + "airplane."),
                    new ArrayList<>(Arrays.asList(new Answer("false"))),
                    new Difficulty("1"), getCategorySet("Logging", "Week 5")),

            new TrueFalse(new Question("OODMs represents the class structure of the problem domain."),
                    new ArrayList<>(Arrays.asList(new Answer("true"))),
                    new Difficulty("2"), getCategorySet("Modeling", "OODM", "Week 8")),

            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("There is no need for developer testing if there are "
                                    + "separate testers."))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("Developer testing is the testing done by the "
                                    + "developers themselves."),
                            new Answer("A downside of developer testing: A developer may subconsciously test "
                                    + "only situations that she knows to work (i.e. test it too 'gently')"),
                            new Answer("It is better to do earlier testing"))),
                    new Difficulty("1"), getCategorySet("quality assurance", "testing", "Week 3")),

            new TrueFalse(new Question("XP can be divided into twelve simple rules."),
                    new ArrayList<>(Arrays.asList(new Answer("false"))),
                    new Difficulty("3"), getCategorySet("Project Management", "XP", "Week 9")),

            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new Answer("A test driver is the person that ‘drives’ (or ‘in charge of’) "
                                    + "the testing process, usually, a test engineer."))),
                    new ArrayList<>(Arrays.asList(
                            new Answer("When using JUnit for a class Foo, the common practice is to create a "
                                    + "FooTest class, which will contain various test methods to test the Foo class."),
                            new Answer("The following method name (found in a JUnit test class) is compliant "
                                    + "with the Java coding standard. 'intDivision_zeroDivisor_exceptionThrown'"),
                            new Answer("If the class Box depends on the class Lid, unit testing the Box class "
                                    + "should be done in isolation from the Lid class."))),
                    new Difficulty("3"), getCategorySet("quality assurance", "testing", "Week 3")),

            new TrueFalse(new Question("Consider a Java method isPrime(int i) that returns true if i is a prime "
                    + "number. ‘All non-int values’ is a possible EP for testing this method."),
                    new ArrayList<>(Arrays.asList(new Answer("false"))),
                    new Difficulty("3"), getCategorySet("Project Management", "XP", "Week 9")),

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Answerable sampleAnswerable : getSampleAnswerables()) {
            sampleAb.addAnswerable(sampleAnswerable);
        }

        return sampleAb;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
