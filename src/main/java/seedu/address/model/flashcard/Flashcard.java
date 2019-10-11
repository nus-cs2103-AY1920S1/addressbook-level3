package seedu.address.model.flashcard;

public class Flashcard {
    private Question question;
    private Answer answer;

    public Flashcard(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }
}
