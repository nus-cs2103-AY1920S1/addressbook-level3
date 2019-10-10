package seedu.address.model.answerable;

public interface Answer {

    public boolean isCorrect(String answer);

    public String value = null;

}
