package seedu.revision.model.answerable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ie.NumberNormalizer;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Used to check saq answers
 */
public class AnswerChecker {

    /**
     * Get StanfordCoreNLP pipeline to process String
     */
    private static StanfordCoreNLP pipeline = Pipeline.getPipeline();

    /**
     * Processes the String to trim trailing and leading whitespaces, covert all characters to lowercase,
     * Checks for numbers that are in alphabets form and changes it to digits form.
     * It will return the processed String
     * @param stringToBeProcessed String to be processed
     * @return processed String
     */
    private static String processString(String stringToBeProcessed) {

        StringBuilder processedString = new StringBuilder(stringToBeProcessed
                .replaceAll("\\s*\\p{Punct}+\\s*$", ""));

        CoreDocument coreDocument = new CoreDocument(processedString.toString());
        pipeline.annotate(coreDocument);
        List<CoreLabel> coreLabelList = coreDocument.tokens(); // tokenize each word

        for (CoreLabel coreLabel : coreLabelList) {

            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);

            if (pos.equals("CD")) {
                Number num = NumberNormalizer.wordToNumber(coreLabel.originalText());
                coreLabel.setOriginalText(num.toString());
            }
        }

        processedString = new StringBuilder();

        for (CoreLabel coreLabel : coreLabelList) {
            processedString.append(coreLabel.originalText()).append(" ");
        }

        return processedString.toString().toLowerCase().trim();
    }

    /**
     * Checks if user answer matches the correct answer using fuzzy matching technique.
     * It will return true if match percentage passes the matchedPercentageThreshold
     * @param userInput String of user's input to the question
     * @param correctAnswer String of correct answer
     * @return true or false
     */
    public static boolean isMatched(String userInput, String correctAnswer) {

        /**
         * Used to determine if user input answer is correct or not
         */
        int matchedPercentageThreshold = 80;

        int matchedPercentage = FuzzySearch.weightedRatio(userInput, correctAnswer);

        if (matchedPercentage > matchedPercentageThreshold) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compares sentiment value between two Strings.
     * It will compare sentiment value (Very negative, Negative, Neutral, Positive, Very positive) between the
     * two Strings and return true if same sentiment and false otherwise.
     * @param userInput String of user's input to the question
     * @param correctAnswer String of correct answer
     * @return true or false
     */
    private static boolean hasSameSentiment(String userInput, String correctAnswer) {

        CoreDocument userInputDocument = new CoreDocument(userInput);
        pipeline.annotate(userInputDocument);

        CoreDocument correctAnswerDocument = new CoreDocument(correctAnswer);
        pipeline.annotate(correctAnswerDocument);

        String sentimentValueOfUserInput = userInputDocument.sentences().get(0).sentiment();
        String sentimentValueOfCorrectAnswer = correctAnswerDocument.sentences().get(0).sentiment();

        if (sentimentValueOfUserInput.equals("Very negative") || sentimentValueOfUserInput.equals("Negative")) {
            sentimentValueOfUserInput = "Negative";
        } else {
            sentimentValueOfUserInput = "Positive";
        }

        if (sentimentValueOfCorrectAnswer.equals("Very negative") || sentimentValueOfCorrectAnswer.equals("Negative")) {
            sentimentValueOfCorrectAnswer = "Negative";
        } else {
            sentimentValueOfCorrectAnswer = "Positive";
        }

        return sentimentValueOfUserInput.equals(sentimentValueOfCorrectAnswer);
    }

    /**
     * Checks if user answer is correct or not.
     * It will return true if answer is correct and false if answer is wrong
     * @param userInput user's answer to the question
     * @param correctAnswerList the correct answer to the question
     * @return true or false
     */
    public static boolean check(String userInput, ArrayList<Answer> correctAnswerList) {

        userInput = processString(userInput);

        for (Answer answer : correctAnswerList) {
            String correctAnswer = processString(answer.getAnswer());
            if (hasSameSentiment(userInput, correctAnswer) == true && isMatched(userInput, correctAnswer) == true) {
                return true;
            }
        }

        return false;
    }
}
