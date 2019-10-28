package seedu.revision.model.answerable.answer;

public interface Answer {

    String MESSAGE_CONSTRAINTS = "Answers should not be repeated";
    String TYPE_NOT_FOUND = "Question types can only be tf, mcq, or saq";

    static boolean isValidAnswer(String test, String validationRegex) {
        return test.matches(validationRegex);
    }

    boolean isValidAnswer(String test);

    public String getAnswer();
}
