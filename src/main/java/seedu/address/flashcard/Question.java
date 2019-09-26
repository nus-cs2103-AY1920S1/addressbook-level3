package seedu.address.flashcard;

public abstract class Question {

    String question;

    public String getQuestion() {
        return question;
    }

    public String setQuestion(String question) {
        this.question = question;
        return "You've successfully set the question to " + question;
    }

    public boolean contains(String s) {
        return this.question.contains(s);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Question)) {
            return false;
        }

       Question otherQuestion = (Question) other;
        return otherQuestion.getQuestion().equals(this.getQuestion());
    }

    @Override
    public String toString() {
        return this.question;
    }
}
