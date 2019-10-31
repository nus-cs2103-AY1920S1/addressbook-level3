package seedu.revision.model.answerable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.ie.NumberNormalizer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * Used to check saq answers
 */
public class AnswerChecker {

    private static StanfordCoreNLP pipeline = Pipeline.getPipeline();

    /**
     * Used to search for keywords using parts of speech tagging
     */
    private static List<String> posTags = List.of("CC", "CD", "NN", "NNS", "JJ");

    /**
     * Analyses the string of answer to determine keywords.
     * It will return a list of keywords
     * @param answer string of answer to analyse
     * @return List of keywords in String
     */
    private static List<String> analyseAnswerForKeywords(String answer) {
        List<String> keywords = new ArrayList<>();
        CoreDocument coreDocument = new CoreDocument(answer);
        pipeline.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens(); // tokenize each word

        Iterator<CoreLabel> iter = coreLabelList.iterator();
        while (iter.hasNext()) {
            CoreLabel coreLabel = iter.next();
            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            if (!posTags.parallelStream().anyMatch(pos::contains)) {
                iter.remove();
            }
        }

        for (CoreLabel coreLabel : coreLabelList) {
            coreLabel.setOriginalText(coreLabel.lemma());
            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            if (pos.equals("CD")) {
                Number num = NumberNormalizer.wordToNumber(coreLabel.originalText());
                coreLabel.setOriginalText(num.toString());
            }

            keywords.add(coreLabel.originalText());
        }

        return keywords;
    }

    /**
     * Analyses the string of answer to determine sentiment.
     * It will return sentiment value (Negative, Neutral, Positive) in String format
     * @param answer string of answer to analyse
     * @return String that contains sentiment value
     */
    private static String analyseAnswerForSentiment(String answer) {
        CoreDocument coreDocument = new CoreDocument(answer);
        pipeline.annotate(coreDocument);

        List<CoreSentence> sentences = coreDocument.sentences();

        return sentences.get(0).sentiment();
    }

    /**
     * Checks if user answer is correct or not.
     * It will return true if answer is correct and false if answer is wrong
     * @param userInput user's answer to the question
     * @param correctAnswer the correct answer to the question
     * @return true or false
     */
    public static boolean check(String userInput, String correctAnswer) {
        HashSet<String> userInputKeywords = new HashSet<>(analyseAnswerForKeywords(userInput));
        HashSet<String> correctAnswerKeywords = new HashSet<>(analyseAnswerForKeywords(correctAnswer));

        if (userInputKeywords.equals(correctAnswerKeywords)) {
            String userInputSentiment = analyseAnswerForSentiment(userInput);
            String correctAnswerSentiment = analyseAnswerForSentiment(correctAnswer);

            if (!correctAnswerSentiment.equals("Negative")) {
                if (!userInputSentiment.equals("Negative")) {
                    return true;
                }
            }
        }

        return false;
    }
}
