package seedu.address.flashcard;

public class Answer {
    String answer;

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String setAnswer() {
        this.answer = answer;
        return "You've set the answer to " + answer;
    }

    public boolean compareAnswer(String input) {
        return input.equals(this.answer);
    }

    @Override
    public String toString() {
        return answer;
    }
}
