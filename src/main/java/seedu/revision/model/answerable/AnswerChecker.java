package seedu.revision.model.answerable;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

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
            keywords.add(coreLabel.originalText());
        }

        return keywords;
    }

    public static boolean check(String userInput, String correctAnswer) {
        if (new HashSet<>(analyseAnswerForKeywords(userInput)).equals(new HashSet<>(analyseAnswerForKeywords(correctAnswer)))) {
            return true;
        }

        return false;
    }
}
