package seedu.address.model.question;

public class Question {

    private String question;
    private String answer;
    private QuestionType type;

    /**
     * Creates a new question.
     *
     * @param question to set.
     * @param answer to the question.
     * @param type of question e.g open or mcq.
     */
    public Question(String question, String answer, QuestionType type){
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    @Override
    public String toString(){
        return question;
    }
}
