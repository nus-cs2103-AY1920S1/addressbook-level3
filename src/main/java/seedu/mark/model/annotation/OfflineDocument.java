package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;

/**
 * Represents the offline document (with annotations). An offline document contains content from a cache,
 * whose paragraphs are numbered to support CLI selection of paragraphs to annotate.
 */
public class OfflineDocument {

    public static final String MESSAGE_INVALID_PID = "invalid paragraph index provided. "
            + "Unable to annotate nonexistent paragraph.";
    public static final String MESSAGE_ASSERT_NOT_PHANTOM = "Cannot add empty note to phantom paragraphs.";
    public static final String MESSAGE_ASSERT_PHANTOM_HAS_NOTE = "Annotation given does not have a note; "
            + "phantom paragraphs must have a note to exist";
    public static final String MESSAGE_ASSERT_IS_PHANTOM = "Cannot delete a non phantom paragraph";

    public static final String NAME_NO_DOCUMENT = "NOTHING";

    public final Logger logger = LogsCenter.getLogger(OfflineDocument.class);

    /** Paragraphs with notes. */
    private HashMap<ParagraphIdentifier, Paragraph> paragraphs;
    /** Number of stray notes so far. */
    private int numStray;

    public OfflineDocument(Document doc) {
        requireNonNull(doc);
        this.paragraphs = new HashMap<>();
        numStray = 0;
        loadDocumentIntoParagraphs(doc);
    }

    public OfflineDocument(String doc) {
        this(Jsoup.parse(doc));
    }

    /**
     * Constructs an {@code OfflineDocument} with a list of paragraphs and number of stray notes.
     */
    public OfflineDocument(List<Paragraph> paragraphs, int numStray) {
        this.numStray = numStray;
        this.paragraphs = new HashMap<>();
        for (Paragraph p : paragraphs) {
            this.paragraphs.put(p.getId(), p);
        }
    }

    /**
     * Loads Readability4J-parsed html document into their respective paragraphs.
     * Document is fresh from saved cache; no annotations are present.
     * @param doc JSoup document parsed from Readability4J html output
     */
    private void loadDocumentIntoParagraphs(Document doc) {
        Elements paragraphs = doc.select("p"); //select every element use *
        int idx = 0;
        for (Element p : paragraphs) {
            if (p.text().isBlank()) {
                continue; //blank paragraphs (images only is considered blank) are not added into document
            }

            idx++;
            Paragraph para = new TrueParagraph(Index.fromOneBased(idx), new ParagraphContent(p.text()));
            this.paragraphs.put(para.getId(), para);
        }
    }

    /**
     * Loads stored annotations to offline document.
     * @param annotations HashMap to map {@code Annotation} to {@code ParagraphIdentifier}; must be non-null
     * TODO: if time allows -- error message for user if paragraph was auto shifted to stray? (corrupted file)
     */
    public void loadAnnotations(HashMap<Annotation, ParagraphIdentifier> annotations) {
        requireNonNull(annotations);
        for (Annotation a : annotations.keySet()) {
            if (a == null) {
                logger.log(Level.WARNING, "Annotation loaded is null. Ignored.");
                continue;
            }

            if (!hasParagraph(annotations.get(a))) {
                logger.log(Level.SEVERE, "Annotation was referring to wrong paragraph. Note now stray.");
                addPhantom(a);
                continue;
            }

            Paragraph p = paragraphs.get(annotations.get(a));
            p.addAnnotation(a);
        }
    }

    /**
     * Updates the stray indices of present {@code PhantomParagraph}s.
     * Numbering should start from 1 and no numbering gaps should occur between {@code PhantomParagraph}s.
     */
    public void updateStrayIndex() {
        int emptyCount = 0;
        for (int i = 0; i < numStray; i++) {
            ParagraphIdentifier id = ParagraphIdentifier.makeStrayId(Index.fromZeroBased(i));

            if (!hasParagraph(id)) {
                emptyCount++;
                continue;
            }

            Paragraph p = paragraphs.get(id);
            ParagraphIdentifier newId = ParagraphIdentifier.makeStrayId(Index.fromZeroBased(i - emptyCount));
            p.updateId(newId);

            this.paragraphs.remove(id);
            this.paragraphs.put(newId, p);
        }
        numStray -= emptyCount;
    }

    /**
     * Adds an annotation to the specified paragraph.
     * Asserts that {@code pid} identifies a {@code TrueParagraph}.
     * @param pid The id of the paragraph to annotate
     * @param an The annotation to annotate
     * @throws IllegalValueException if {@code pid} is invalid.
     */
    public void addAnnotation(ParagraphIdentifier pid, Annotation an) throws IllegalValueException {
        requireNonNull(pid);
        requireNonNull(an);
        assert (!pid.isStray() || an.hasNote()) : MESSAGE_ASSERT_NOT_PHANTOM;

        if (!hasParagraph(pid)) {
            throw new IllegalValueException(MESSAGE_INVALID_PID);
        }
        Paragraph p = paragraphs.get(pid);
        p.addAnnotation(an);
    }

    /**
     * Returns a list of annotated paragraphs of offline document.
     * @return the list of paragraphs
     */
    public List<Paragraph> getCollection() {
        return new ArrayList<>(paragraphs.values());
    }

    /**
     * Returns the paragraph identified by {@code pid}.
     * @throws IllegalValueException if {@code pid} is invalid.
     */
    public Paragraph getParagraph(ParagraphIdentifier pid) throws IllegalValueException {
        requireNonNull(pid);
        if (!hasParagraph(pid)) {
            throw new IllegalValueException(MESSAGE_INVALID_PID);
        }
        return paragraphs.get(pid);
    }

    /**
     * Returns true if this {@code OfflineDocument} contains a {@code Paragraph} identified using {@code pid}.
     * Returns false otherwise.
     */
    public boolean hasParagraph(ParagraphIdentifier pid) {
        return paragraphs.containsKey(pid);
    }

    /**
     * Returns number of stray notes this paragraph has since Mark started up.
     * Removing/relocating stray annotations will not modify the count.
     */
    public int getNumStrayNotes() {
        return numStray;
    }

    /**
     * Removes the phantom paragraph identified by {@code pid}.
     * Asserts that {@code pid} identifies a phantom paragraph.
     * @throws IllegalValueException if {@code pid} is invalid.
     */
    public void removePhantom(ParagraphIdentifier pid) throws IllegalValueException {
        assert pid.isStray() : MESSAGE_ASSERT_IS_PHANTOM;
        if (!hasParagraph(pid)) {
            throw new IllegalValueException(MESSAGE_INVALID_PID);
        }
        this.paragraphs.remove(pid);
    }

    /**
     * Adds a phantom paragraph containing the given annotation.
     * Asserts that {@code an} has a {@code AnnotationNote}.
     * @param an The annotation to make stray
     */
    public void addPhantom(Annotation an) {
        requireNonNull(an);
        assert an.hasNote() : MESSAGE_ASSERT_PHANTOM_HAS_NOTE;
        numStray++;
        Paragraph p = new PhantomParagraph(Index.fromOneBased(numStray), an);
        this.paragraphs.put(p.getId(), p);
    }

    /**
     * Returns a copy of this {@code OfflineDocument}.
     */
    public OfflineDocument copy() {
        return new OfflineDocument(
                new ArrayList<>(paragraphs.values()).stream().map(Paragraph::copy).collect(Collectors.toList()),
                numStray);
    }


    /**
     * Temporary test driver as proof of concept.
     * @param args
     */
    public static void main(String[] args) {
        URL rsrc = OfflineDocument.class.getClassLoader().getResource("view/sample_cache.html");

        try {
            System.out.println(Paths.get(rsrc.toURI()).toString());
            String html = Files.readString(Paths.get(rsrc.toURI()), StandardCharsets.UTF_8);
            Document doc = Jsoup.parse(html);
            new OfflineDocument(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
