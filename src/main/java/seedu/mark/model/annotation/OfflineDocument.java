package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.io.File;
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
 * Represents the offline document (with annotations). An offline document contains content from a cache, whose paragraphs
 * are numbered to support CLI selection of paragraphs to annotate.
 */
public class OfflineDocument {
    public static OfflineDocument OFFLINE_DOC_EXAMPLE = new OfflineDocument("example doc", Jsoup.parse(
            "<div id=\"readability-page-1\" class=\"page\">\n" +
                    "    <section>\n" +
                    "        <p><strong>So finally you're <a href=\"http://fakehost/code/2013/testing-frontend-javascript-code-using-mocha-chai-and-sinon/\">testing your frontend JavaScript code</a>? Great! The more you write tests, the more confident you are with your code… but how much precisely? That's where <a href=\"http://en.wikipedia.org/wiki/Code_coverage\">code coverage</a> might help.</strong> </p>\n" +
                    "        <p>The idea behind code coverage is to record which parts of your code (functions, statements, conditionals and so on) have been executed by your test suite, to compute metrics out of these data and usually to provide tools for navigating and inspecting them.</p>\n" +
                    "        <p>Not a lot of frontend developers I know actually test their frontend code, and ]I can barely imagine how many of them have ever setup code coverage… Mostly because there are not many frontend-oriented tools in this area I guess.</p>\n" +
                    "        <p>Actually I've only found one which provides an adapter for <a href=\"http://visionmedia.github.io/mocha/\">Mocha</a> and actually works…</p>\n" +
                    "        <blockquote>\n" +
                    "            <p>Drinking game for web devs: <br>(1) Think of a noun <br>(2) Google \"&lt;noun&gt;.js\" <br>(3) If a library with that name exists - drink</p>— Shay Friedman (@ironshay)\n" +
                    "            <a href=\"https://twitter.com/ironshay/statuses/370525864523743232\">August 22, 2013</a>\n" +
                    "        </blockquote>\n" +
                    "        <p><strong><a href=\"http://blanketjs.org/\">Blanket.js</a></strong> is an <em>easy to install, easy to configure, and easy to use JavaScript code coverage library that works both in-browser and with nodejs.</em> </p>\n" +
                    "        <p>Its use is dead easy, adding Blanket support to your Mocha test suite is just matter of adding this simple line to your HTML test file:</p>\n" +
                    "        <pre><code>&lt;script src=\"vendor/blanket.js\"\n" +
                    "        data-cover-adapter=\"vendor/mocha-blanket.js\"&gt;&lt;/script&gt;\n" +
                    "</code></pre>\n" +
                    "        <p>Source files: <a href=\"https://raw.github.com/alex-seville/blanket/master/dist/qunit/blanket.min.js\">blanket.js</a>, <a href=\"https://raw.github.com/alex-seville/blanket/master/src/adapters/mocha-blanket.js\">mocha-blanket.js</a> </p>\n" +
                    "        <p>As an example, let's reuse the silly <code>Cow</code> example we used <a href=\"http://fakehost/code/2013/testing-frontend-javascript-code-using-mocha-chai-and-sinon/\">in a previous episode</a>:</p>\n" +
                    "        <pre><code>// cow.js\n" +
                    "(function(exports) {\n" +
                    "  \"use strict\";\n" +
                    "\n" +
                    "  function Cow(name) {\n" +
                    "    this.name = name || \"Anon cow\";\n" +
                    "  }\n" +
                    "  exports.Cow = Cow;\n" +
                    "\n" +
                    "  Cow.prototype = {\n" +
                    "    greets: function(target) {\n" +
                    "      if (!target)\n" +
                    "        throw new Error(\"missing target\");\n" +
                    "      return this.name + \" greets \" + target;\n" +
                    "    }\n" +
                    "  };\n" +
                    "})(this);\n" +
                    "</code></pre>\n" +
                    "        <p>And its test suite, powered by Mocha and <a href=\"http://chaijs.com/\">Chai</a>:</p>\n" +
                    "        <pre><code>var expect = chai.expect;\n" +
                    "\n" +
                    "describe(\"Cow\", function() {\n" +
                    "  describe(\"constructor\", function() {\n" +
                    "    it(\"should have a default name\", function() {\n" +
                    "      var cow = new Cow();\n" +
                    "      expect(cow.name).to.equal(\"Anon cow\");\n" +
                    "    });\n" +
                    "\n" +
                    "    it(\"should set cow's name if provided\", function() {\n" +
                    "      var cow = new Cow(\"Kate\");\n" +
                    "      expect(cow.name).to.equal(\"Kate\");\n" +
                    "    });\n" +
                    "  });\n" +
                    "\n" +
                    "  describe(\"#greets\", function() {\n" +
                    "    it(\"should greet passed target\", function() {\n" +
                    "      var greetings = (new Cow(\"Kate\")).greets(\"Baby\");\n" +
                    "      expect(greetings).to.equal(\"Kate greets Baby\");\n" +
                    "    });\n" +
                    "  });\n" +
                    "});\n" +
                    "</code></pre>\n" +
                    "        <p>Let's create the HTML test file for it, featuring Blanket and its adapter for Mocha:</p>\n" +
                    "        <pre><code>&lt;!DOCTYPE html&gt;\n" +
                    "&lt;html&gt;\n" +
                    "&lt;head&gt;\n" +
                    "  &lt;meta charset=\"utf-8\"&gt;\n" +
                    "  &lt;title&gt;Test&lt;/title&gt;\n" +
                    "  &lt;link rel=\"stylesheet\" media=\"all\" href=\"vendor/mocha.css\"&gt;\n" +
                    "&lt;/head&gt;\n" +
                    "&lt;body&gt;\n" +
                    "  &lt;div id=\"mocha\"&gt;&lt;/div&gt;\n" +
                    "  &lt;div id=\"messages\"&gt;&lt;/div&gt;\n" +
                    "  &lt;div id=\"fixtures\"&gt;&lt;/div&gt;\n" +
                    "  &lt;script src=\"vendor/mocha.js\"&gt;&lt;/script&gt;\n" +
                    "  &lt;script src=\"vendor/chai.js\"&gt;&lt;/script&gt;\n" +
                    "  &lt;script src=\"vendor/blanket.js\"\n" +
                    "          data-cover-adapter=\"vendor/mocha-blanket.js\"&gt;&lt;/script&gt;\n" +
                    "  &lt;script&gt;mocha.setup('bdd');&lt;/script&gt;\n" +
                    "  &lt;script src=\"cow.js\" data-cover&gt;&lt;/script&gt;\n" +
                    "  &lt;script src=\"cow_test.js\"&gt;&lt;/script&gt;\n" +
                    "  &lt;script&gt;mocha.run();&lt;/script&gt;\n" +
                    "&lt;/body&gt;\n" +
                    "&lt;/html&gt;\n" +
                    "</code></pre>\n" +
                    "        <p><strong>Notes</strong>:</p>\n" +
                    "        <ul>\n" +
                    "            <li>Notice the <code>data-cover</code> attribute we added to the script tag loading the source of our library;</li>\n" +
                    "            <li>The HTML test file <em>must</em> be served over HTTP for the adapter to be loaded.</li>\n" +
                    "        </ul>\n" +
                    "        <p>Running the tests now gives us something like this:</p>\n" +
                    "        <p> <img alt=\"screenshot\" src=\"http://fakehost/static/code/2013/blanket-coverage.png\"> </p>\n" +
                    "        <p>As you can see, the report at the bottom highlights that we haven't actually tested the case where an error is raised in case a target name is missing. We've been informed of that, nothing more, nothing less. We simply know we're missing a test here. Isn't this cool? I think so!</p>\n" +
                    "        <p>Just remember that code coverage will only <a href=\"http://codebetter.com/karlseguin/2008/12/09/code-coverage-use-it-wisely/\">bring you numbers</a> and raw information, not actual proofs that the whole of your <em>code logic</em> has been actually covered. If you ask me, the best inputs you can get about your code logic and implementation ever are the ones issued out of <a href=\"http://www.extremeprogramming.org/rules/pair.html\">pair programming</a> sessions and <a href=\"http://alexgaynor.net/2013/sep/26/effective-code-review/\">code reviews</a> — but that's another story.</p>\n" +
                    "        <p><strong>So is code coverage silver bullet? No. Is it useful? Definitely. Happy testing!</strong> </p>\n" +
                    "    </section>\n" +
                    "</div>"
    ));

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
        Elements paragraphs = doc.select("*"); //select every element
        int idx = 0;
        for (Element p : paragraphs) {
            //System.out.println(p.text());
            idx++;
            Paragraph para = new TrueParagraph(Index.fromOneBased(idx), new ParagraphContent(p.text()));
            this.paragraphs.put(para.getId(), para);
        }
    }

    public void loadAnnotations() {
        //TODO: idk ;-;
    }

    public void loadAnnotation(ParagraphIdentifier pid, Highlight hl) {
        paragraphs.get(pid).addAnnotation(hl);
    }

    public void loadAnnotation(ParagraphIdentifier pid, Highlight hl, AnnotationNote note) {
        paragraphs.get(pid).addAnnotation(hl, note);
    }

    public List<Paragraph> getCollection() {
        return new ArrayList<>(paragraphs.values());
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
