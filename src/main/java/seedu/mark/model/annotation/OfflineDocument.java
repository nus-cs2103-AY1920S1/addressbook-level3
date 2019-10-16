package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import seedu.mark.commons.core.index.Index;

/**
 * Represents the offline document (with annotations). An offline document contains content from a cache,
 * whose paragraphs are numbered to support CLI selection of paragraphs to annotate.
 */
public class OfflineDocument {
    public static final String OFFLINE_HTML_EXAMPLE =
            "<div id=\"readability-page-1\" class=\"page\">\n"
                    + " <div id=\"storytext\"> \n"
                    + "  <div id=\"js-ie-storytop\">    \n"
                    + "   <div id=\"cnnplayer_cvp_story_0\"> \n"
                    + "    <div> \n"
                    + "     <div id=\"vid0\" href=\"/video/news/2015/11/30/homeboy-industries-priest.cnnmoney\" "
                    + "onclick=\"javascript:VideoPlayerManager.playVideos('cvp_story_0'); return false;\"> \n"
                    + "      <video id=\"cvp_story_0\" preload=\"metadata\" poster=\"\" "
                    + "src=\"http://ht3.cdn.turner.com/money/big/news/2015/11/30/"
                    + "homeboy-industries-priest.cnnmoney_1024x576.mp4\" controls=\"controls\" width=\"300\" "
                    + "height=\"169\"></video> \n"
                    + "      <div id=\"cvp_story_0_endSlate\"> \n"
                    + "       <div> \n"
                    + "        <div>  \n"
                    + "         <img src=\"\" alt=\"\" width=\"620\" height=\"348\"> \n"
                    + "        </div>  \n"
                    + "       </div> \n"
                    + "      </div> \n"
                    + "     </div> \n"
                    + "    </div>  \n"
                    + "   </div> \n"
                    + "  </div>  \n"
                    + "  <h2>The U.S. has long been heralded as a land of opportunity -- a place where anyone can "
                    + "succeed regardless of the economic class they were born into.</h2> \n"
                    + "  <p> But a new report released on Monday by <a href=\"http://web.stanford.edu/group/scspi-dev/"
                    + "cgi-bin/\" target=\"_blank\">Stanford University's Center on Poverty and Inequality</a> calls "
                    + "that into question. </p>  \n"
                    + "  <p> The report assessed poverty levels, income and wealth inequality, economic mobility and "
                    + "unemployment levels among 10 wealthy countries with social welfare programs. </p> \n"
                    + "  <div id=\"smartassetcontainer\"> \n"
                    + "   <div> \n"
                    + "    <div> \n"
                    + "     <div id=\"smartasset-article\"> \n"
                    + "      <div> \n"
                    + "       <p> Powered by SmartAsset.com </p>   \n"
                    + "       <img src=\"https://smrt.as/ck\"> \n"
                    + "      </div> \n"
                    + "     </div> \n"
                    + "    </div> \n"
                    + "   </div> \n"
                    + "  </div> \n"
                    + "  <p> Among its key findings: the class you're born into matters much more in the U.S. "
                    + "than many of the other countries. </p> \n"
                    + "  <p> As the <a href=\"http://web.stanford.edu/group/scspi-dev/cgi-bin/publications/"
                    + "state-union-report\" target=\"_blank\">report states</a>: \"[T]he birth lottery matters more "
                    + "in the U.S. than in most well-off countries.\" </p>  \n"
                    + "  <p> But this wasn't the only finding that suggests the U.S. isn't quite living up to its "
                    + "reputation as a country where everyone has an equal chance to get ahead through sheer will and "
                    + "hard work. </p> \n"
                    + "  <p> <a href=\"http://money.cnn.com/2016/01/11/news/economy/rich-taxes/index.html?iid=EL\">"
                    + "<span>Related: Rich are paying more in taxes but not as much as they used to</span></a> </p>  \n"
                    + "  <p> The report also suggested the U.S. might not be the \"jobs machine\" it thinks it is, "
                    + "when compared to other countries. </p> \n"
                    + "  <p> It ranked near the bottom of the pack based on the levels of unemployment among men and "
                    + "women of prime working age. The study determined this by taking the ratio of employed men and "
                    + "women between the ages of 25 and 54 compared to the total population of each country. </p> \n"
                    + "  <p> The overall rankings of the countries were as follows:<span> <br>1. Finland <span> "
                    + "<br>2. Norway<span> <br>3. Australia <span> <br>4. Canada<span> <br>5. Germany<span> <br>6. "
                    + "France<span> <br>7. United Kingdom <span> <br>8. Italy<span> <br>9. Spain<span> <br>10. United "
                    + "States </span></span> </span> </span> </span> </span> </span> </span> </span> </span> </p> \n"
                    + "  <p> The low ranking the U.S. received was due to its extreme levels of wealth and income "
                    + "inequality and the ineffectiveness of its \"safety net\" -- social programs aimed at reducing "
                    + "poverty. </p> \n"
                    + "  <p> <a href=\"http://money.cnn.com/2016/01/05/news/economy/chicago-segregated/"
                    + "index.html?iid=EL\"><span>Related: Chicago is America's most segregated city</span></a> </p> \n"
                    + "  <p> The report concluded that the American safety net was ineffective because it provides "
                    + "only half the financial help people need. Additionally, the levels of assistance in the U.S. "
                    + "are generally lower than in other countries. </p>   \n"
                    + "  <p> <span> CNNMoney (New York) </span> <span>First published February 1, 2016: 1:28 AM ET"
                    + "</span> </p> \n"
                    + " </div>\n"
                    + "</div>";

    public static final Document OFFLINE_DOC_EXAMPLE = Jsoup.parse(OFFLINE_HTML_EXAMPLE);

    /** Version of cache it annotates. For logistic purposes.*/
    private String cacheVersion;
    /** Paragraphs with notes. */
    private HashMap<ParagraphIdentifier, Paragraph> paragraphs;

    public OfflineDocument(String cacheVersion) {
        requireNonNull(cacheVersion);
        this.cacheVersion = cacheVersion;
        this.paragraphs = new HashMap<>();
    }

    public OfflineDocument(String cacheVersion, Document doc) {
        this(cacheVersion);
        requireNonNull(doc);

        loadDocumentIntoParagraphs(doc);
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
            //System.out.println(p.text());
            idx++;
            Paragraph para = new TrueParagraph(Index.fromOneBased(idx), new ParagraphContent(p.text()));
            //TODO: transfrom ROUGH TESTING into tests:
            try {
                para.addAnnotation(new Highlight(), AnnotationNote.makeNote("this is a note"));
            } catch (Exception e) {
                //TODO: what to do if fails; change exception class too
                e.printStackTrace();
            }
            this.paragraphs.put(para.getId(), para);
        }
    }

    /**
     * Loads stored annotations to offline document.
     */
    public void loadAnnotations() {
        //TODO: idk ;-;
    }

    /**
     * Loads highlight onto specified paragraph.
     * @param pid The id of the paragrapb to highlight
     * @param hl The highlight
     */
    public void loadAnnotation(ParagraphIdentifier pid, Highlight hl) {
        paragraphs.get(pid).addAnnotation(hl);
    }

    /**
     * Loads both the highlight and note onto specified paragraph.
     * Note must not be null or blank.
     * @param pid The id of the paragraph to annotate
     * @param hl The highlight
     * @param note The non-empty note
     */
    public void loadAnnotation(ParagraphIdentifier pid, Highlight hl, AnnotationNote note) {
        paragraphs.get(pid).addAnnotation(hl, note);
    }

    /**
     * Returns a list of annotated paragraphs of offline document.
     * @return the list of paragraphs
     */
    public List<Paragraph> getCollection() {
        return new ArrayList<>(paragraphs.values());
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
            new OfflineDocument("someversion", doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
