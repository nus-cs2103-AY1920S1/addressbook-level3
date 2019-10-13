package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Represents the offline document with annotations. An offline document contains content from a cache, whose paragraphs
 * are numbered to support CLI selection of paragraphs to annotate.
 */
public class OfflineDocument {

    /** Version of cache it annotates. For logistic purposes.*/
    private String cacheVersion;
    /** Paragraphs with notes. */
    private HashMap<ParagraphIdentifier, Paragraph> paragraph;

    public OfflineDocument(String cacheVersion) {
        requireNonNull(cacheVersion);
        this.cacheVersion = cacheVersion;
        this.paragraph = new HashMap<>();
    }

    public OfflineDocument(String cacheVersion, Document doc) {
        this(cacheVersion);
        requireNonNull(doc);

        loadDocumentIntoParagraphs(doc);
    }

    private void loadDocumentIntoParagraphs(Document doc) {
        Elements paragraphs = doc.select("p");
        for (Element p : paragraphs) {
            System.out.println(p.text());
        }
    }

    public static void main(String[] args) {
        URL rsrc = OfflineDocument.class.getClassLoader().getResource("view/sample_cache.html");

        try {
            System.out.println(Paths.get(rsrc.toURI()).toString());
            String html = Files.readString(Paths.get(rsrc.toURI()), StandardCharsets.UTF_8);
            Document doc = Jsoup.parse(html);
            new OfflineDocument("someversion", doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
