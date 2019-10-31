package seedu.revision.model.answerable;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * Creates a pipeline to process user input
 */
public class Pipeline {

    private static Properties properties;
    private static String propertiesName = "tokenize, ssplit, pos, lemma, ner, parse, sentiment";
    private static StanfordCoreNLP pipeline;

    private Pipeline() { }

    static {
        properties = new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    public static StanfordCoreNLP getPipeline() {
        if (pipeline == null) {
            pipeline = new StanfordCoreNLP(properties);
        }
        return pipeline;
    }
}
