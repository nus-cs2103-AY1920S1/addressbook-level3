package seedu.revision.model.answerable;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.ie.NumberNormalizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class AnswerChecker {

    private static StanfordCoreNLP pipeline = Pipeline.getPipeline();

    static List<String> POSKeywords = List.of("CC", "CD", "NN", "NNS", "JJ");

    public AnswerChecker() {
    }

    private static List<String> analyseAnswerForKeywords(String answer) {
        List<String> keywords = new ArrayList<>();
        CoreDocument coreDocument = new CoreDocument(answer);
        pipeline.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens(); // tokenize each word

        Iterator<CoreLabel> iter = coreLabelList.iterator();
        while (iter.hasNext()) {
            CoreLabel coreLabel = iter.next();
            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            if (!POSKeywords.parallelStream().anyMatch(pos::contains)) {
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

    private static String analyseAnswerForSentiment(String answer) {
        CoreDocument coreDocument = new CoreDocument(answer);
        pipeline.annotate(coreDocument);

        List<CoreSentence> sentences = coreDocument.sentences();
        
        return sentences.get(0).sentiment();
    }

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
