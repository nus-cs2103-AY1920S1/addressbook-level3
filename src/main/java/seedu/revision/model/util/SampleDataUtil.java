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
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.answerable.answer.McqAnswer;
import seedu.revision.model.category.Category;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Answerable[] getSampleAnswerables() {
        Answer defaultCorrectAnswer = new McqAnswer("CORRECT");
        ArrayList<Answer> defaultCorrectAnswerList = new ArrayList<>(Arrays.asList(defaultCorrectAnswer));
        Answer defaultWrongAnswer = new McqAnswer("WRONG");
        ArrayList<Answer> defaultWrongAnswerList = new ArrayList<>(Arrays.asList(defaultWrongAnswer,
                defaultWrongAnswer, defaultWrongAnswer));

        //TODO: Implement actual answerable
        return new Answerable[] {
            new Mcq(new Question("Sample Question"), defaultCorrectAnswerList, defaultWrongAnswerList,
                    new Difficulty("1"), getCategorySet("category 1", "category 2")),
            new Mcq(new Question("What type of project is AB3?"),
                    new ArrayList<>(Arrays.asList(new McqAnswer("Brownfield"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("Greenfield"), new McqAnswer("Blackfield"),
                            new McqAnswer("Whitefield"))),
                    new Difficulty("1"), getCategorySet("introduction", "week 2")),
            new Mcq(new Question("Which of the following is a delight of Software Engineering?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("What you get of making things that are useful to others"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("One must perform perfectly"),
                            new McqAnswer("One rarely controls the circumstances of his work or goal"),
                            new McqAnswer("The dependence upon others"))),
                    new Difficulty("1"), getCategorySet("introduction", "pros and cons", "week 2")),
            new Mcq(new Question("Which of the following is a woe of Software Engineering?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("Finding nitty little bugs"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("What you get of having to solve puzzles"),
                            new McqAnswer("What you get of working in such a tractable medium"),
                            new McqAnswer("What you get of having to keep learning always"))),
                    new Difficulty("1"), getCategorySet("introduction", "pros and cons", "week 2")),
            new Mcq(new Question("What does IDE stand for?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("Integrated Development Environment"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("Integrated Development Editor"),
                            new McqAnswer("Interchangeable Development Environment"),
                            new McqAnswer("Integrated Development Enhancement"))),
                    new Difficulty("1"), getCategorySet("implementation", "IDEs", "basic", "week 2")),
            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("Test cases can only be determined "
                                    + "by reviewing similar existing systems"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("SUT stands for Software Under Test"),
                            new McqAnswer("A test case failure is a mismatch between the actual behaviour "
                                    + "and the expected behaviour"),
                            new McqAnswer("A test case failure may not always indicate a bug/defect in the SUT"))),
                    new Difficulty("3"), getCategorySet("quality assurance", "testing", "week 2")),
            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("A regression is an unintended and undesirable side effect "
                                    + "of a modification"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("Regression testing is the re-testing of the "
                                    + "software to fix regression bugs"),
                            new McqAnswer("Regression testing must be automated"),
                            new McqAnswer("Regression testing is less effective when done frequently"))),
                    new Difficulty("2"), getCategorySet("quality assurance", "testing", "week 2")),
            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("Downloading a copy of a remote repo to your computer is "
                                    + "called forking"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("A local repo can pull from multiple "
                                    + "remote repositories"),
                            new McqAnswer("It is possible to set up a Git server on your own computer"),
                            new McqAnswer("A pull request is mechanism for contributing code to a remote repo."))),
                    new Difficulty("3"), getCategorySet("project management", "revision control", "week 2")),
            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("Git uses a serial number (matching the exact nano-second a commit "
                                    + "was created) to uniquely identify a commit."))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("After you initialize a git repo in a folder, "
                                    + "all files in that folder are automatically tracked by Git."),
                            new McqAnswer("The git history does not contain everything that happened to the "
                                    + "tracked files. It only contains specific points that you committed to "
                                    + "the history."),
                            new McqAnswer("Git can show you the difference between two adjacent commits in the "
                                    + "version history."))),
                    new Difficulty("3"), getCategorySet("project management", "revision control", "week 2")),
            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("To see the working folder at specific commit, "
                                    + "we ‘revert’ to that commit."))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("The git history does not contain everything that "
                                    + "happened to the tracked files. It only contains specific points that you "
                                    + "committed to the history."),
                            new McqAnswer("After you initialize a git repo in a folder, "
                                    + "all files in that folder are automatically tracked by Git."),
                            new McqAnswer("Git can show you the difference between two adjacent commits in the "
                                    + "version history."))),
                    new Difficulty("3"), getCategorySet("project management", "revision control", "week 2")),
            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("One aim of adopting a coding standard is to make the "
                                    + "entire code base look like it was written by one person."))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("A developer should understand the importance of "
                                    + "following a coding standard. However, there is no need to follow one."),
                            new McqAnswer("It is better if each developer followed their own style of coding "
                                    + "so that the code can be traced to the author easily."),
                            new McqAnswer("A coding standard is universal and not specific to any programming language"))),
                    new Difficulty("2"), getCategorySet("implementation", "code quality", "week 3")),
            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("There are tools that can help to enforce some parts of a "
                                    + "coding standard e.g. indentation rules."))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("It is better if each developer followed their "
                                    + "own style of coding so that the code can be traced to the author easily."),
                            new McqAnswer("A developer should understand the importance of following a coding standard. "
                                    + "However, there is no need to follow one."),
                            new McqAnswer("Company's coding standard can be vastly different from typical "
                                    + "industry practices as long as it is consistent throughout the whole company"))),
                    new Difficulty("2"), getCategorySet("implementation", "code quality", "week 3")),
            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("This variable name is compliant with the coding standard.\n"
                                    + "final static int RED = 1;"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("Names such as i, j, k should not be used as "
                                    + "variable names as they are not descriptive enough."),
                            new McqAnswer("This variable name is compliant with the coding standard.\n"
                                    + "boolean processingStatus = false;"),
                            new McqAnswer("When wrapping a long statement (an example given below), "
                                    + "one should indent lines using two tabs instead of the usual one tab.\n"
                                    + "totalSum = a + b + c\n          + d + e;"))),
                    new Difficulty("1"), getCategorySet("coding standard", "week 3")),
            new Mcq(new Question("Which of the following is true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("Of the two statements below, the first one has the correct spacing.\n"
                                    + "a = (b + c) * d;\na=(b+c)*d;"))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("Wildcard imports (an example given below) "
                                    + "should not be used unless there are many classes being imported from "
                                    + "the same package.\nimport java.util.*;"),
                            new McqAnswer("This is an acceptable opening sentence for a javadoc header comment.\n/**\n"
                                    + " * Add the value to the current list.\n * ...\n */"),
                            new McqAnswer("This indentation is unacceptable:\nswitch (condition) {\ncase ABC:\n    "
                                    + "statements;\n    break;\ndefault:\n    statements;\n    break;\n}"))),
                    new Difficulty("1"), getCategorySet("coding standard", "week 3")),
            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("There is no need for developer testing if there are "
                                    + "separate testers."))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("Developer testing is the testing done by the "
                                    + "developers themselves."),
                            new McqAnswer("A downside of developer testing: A developer may subconsciously test "
                                    + "only situations that she knows to work (i.e. test it too 'gently')"),
                            new McqAnswer("It is better to do earlier testing"))),
                    new Difficulty("1"), getCategorySet("quality assurance", "testing", "week 3")),
            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("A test driver is the person that ‘drives’ (or ‘in charge of’) "
                                    + "the testing process, usually, a test engineer."))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("When using JUnit for a class Foo, "
                                    + "the common practice is to create a FooTest class, which will contain "
                                    + "various test methods to test the Foo class."),
                            new McqAnswer("The following method name (found in a JUnit test class) is compliant "
                                    + "with the Java coding standard.\nintDivision_zeroDivisor_exceptionThrown"),
                            new McqAnswer("If the class Box depends on the class Lid, unit testing the Box class "
                                    + "should be done in isolation from the Lid class."))),
                    new Difficulty("3"), getCategorySet("quality assurance", "testing", "week 3")),
            new Mcq(new Question("Which of the following is not true?"),
                    new ArrayList<>(Arrays
                            .asList(new McqAnswer("Test drivers can be used to isolate the SUT from its dependencies."))),
                    new ArrayList<>(Arrays.asList(new McqAnswer("A stub's implementation is supposed to be much simpler "
                                    + "than the component it replaces."),
                            new McqAnswer("The following method name (found in a JUnit test class) is compliant "
                                    + "with the Java coding standard.\nintDivision_zeroDivisor_exceptionThrown"),
                            new McqAnswer("When using JUnit for a class Foo, the common practice is to "
                                    + "create a FooTest class, which will contain various test methods "
                                    + "to test the Foo class."))),
                    new Difficulty("3"), getCategorySet("quality assurance", "testing", "week 3"))
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
