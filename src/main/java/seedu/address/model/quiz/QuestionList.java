package seedu.address.model.quiz;

import java.util.ArrayList;

import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

/**
 * Represents a question list for a quiz.
 */
public class QuestionList {

    private ArrayList<Question> questions;

    /**
     * Creates a QuestionList instance with the appropriate attributes.
     */
    public QuestionList() {
        this.questions = new ArrayList<>();
    }

    /**
     * Adds a question to a question list, provided it is not already in the question list.
     * @param question The question to be added to the question list.
     * @return True if the question is not a repeat, else false.
     */
    public boolean addQuestion(Question question) {
        if (!isRepeated(question)) {
            questions.add(question);
            return true;
        }
        return false;
    }

    /**
     * Adds a question to a question list, provided it is not already in the question list.
     * @param questionNumber The index to add the question to.
     * @param question The question to be added to the question list.
     * @return True if the question is not a repeat, else false.
     */
    public boolean addQuestion(int questionNumber, Question question) {
        if (questionNumber <= 0) {
            return false;
        }

        int listSize = questions.size();
        int questionIndex = questionNumber - 1;

        if (questionNumber > listSize) {
            if (!isRepeated(question)) {
                questions.add(question);
                return true;
            }
        }
        if (!isRepeated(question)) {
            questions.add(questionIndex, question);
            return true;
        }
        return false;
    }

    /**
     * Removes a question from a question list.
     * @param questionNumber The number of the question to be removed.
     * @return The question which was removed.
     */
    public Question removeQuestion(int questionNumber) {
        int questionIndex = questionNumber - 1;
        Question removedQuestion = questions.remove(questionIndex);
        return removedQuestion;
    }

    /**
     * Returns the questions for a quiz.
     * @return The questions for the quiz in an ArrayList.
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the questions for a quiz.
     */
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    /**
     * Returns the answers for a quiz.
     * @return The answers for the quiz in an ArrayList.
     */
    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        for (Question q : questions) {
            answers.add(q.getAnswer());
        }
        return answers;
    }

    /**
     * Sets the String represented questions as the questions of a question list.
     * @param stringQuestions The questions in String representation.
     */
    public void setStringQuestions(String stringQuestions) {
        String[] splitBySymbol = stringQuestions.split("//");
        if (splitBySymbol.length == 1) {
            return;
        }
        for (String s : splitBySymbol) {
            String[] split = s.split(":");
            if (split.length <= 2) {
                String question = split[0];
                String answer = split[1];
                OpenEndedQuestion openEndedQuestion = new OpenEndedQuestion(question, answer);
                questions.add(openEndedQuestion);
            } else {
                String question = split[0];
                String answer = split[1];
                String optionA = split[2];
                String optionB = split[3];
                String optionC = split[4];
                String optionD = split[5];
                McqQuestion mcqQuestion = new McqQuestion(question, answer, optionA, optionB,
                                            optionC, optionD);
                questions.add(mcqQuestion);
            }
        }
    }

    /**
     * Gets the questions of a question list in String representation.
     * @return The questions of the question list in String representation.
     */
    public String getStringQuestions() {
        String returnQuestions = "";
        if (questions.size() <= 0) {
            return returnQuestions;
        }
        Question firstQuestion = questions.get(0);
        if (firstQuestion instanceof OpenEndedQuestion) {
            String question = firstQuestion.getQuestion();
            String answer = firstQuestion.getAnswer();
            returnQuestions += question + ":" + answer;
        } else {
            McqQuestion mcqQuestion = (McqQuestion) firstQuestion;
            String question = firstQuestion.getQuestion();
            String answer = firstQuestion.getAnswer();
            returnQuestions += question + ":" + answer + ":"
                                + mcqQuestion.getOptionA() + ":" + mcqQuestion.getOptionB() + ":"
                                + mcqQuestion.getOptionC() + ":" + mcqQuestion.getOptionD();
        }
        for (int i = 1; i < questions.size(); i++) {
            Question nextQuestion = questions.get(i);
            if (nextQuestion instanceof OpenEndedQuestion) {
                String question = nextQuestion.getQuestion();
                String answer = nextQuestion.getAnswer();
                returnQuestions += "//" + question + ":" + answer;
            } else {
                McqQuestion mcqQuestion = (McqQuestion) nextQuestion;
                String question = nextQuestion.getQuestion();
                String answer = nextQuestion.getAnswer();
                returnQuestions += "//" + question + ":" + answer + ":"
                        + mcqQuestion.getOptionA() + ":" + mcqQuestion.getOptionB() + ":"
                        + mcqQuestion.getOptionC() + ":" + mcqQuestion.getOptionD();
            }
        }
        return returnQuestions;
    }

    /**
     * Returns true if a question is already in a question list, else false.
     * @param question The question to be checked.
     * @return True if the question is already in the question list, else false.
     */
    private boolean isRepeated(Question question) {
        String otherQuestion = question.getQuestion();
        for (Question q : questions) {
            String currentQuestion = q.getQuestion();
            if (currentQuestion.equals(otherQuestion)) {
                return true;
            }
        }
        return false;
    }
}
