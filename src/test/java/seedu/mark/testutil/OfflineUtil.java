package seedu.mark.testutil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Sample offline html and document for testing.
 */
public class OfflineUtil {
    public static final String OFFLINE_HTML_EXAMPLE =
            "<div id=\"readability-page-1\" class=\"page\">\n <div> \n  <div> \n   <div> \n    <div> \n     <div> \n"
                    + "      <div> \n       <div> \n        <section>  \n         <div> \n          <p> \n           "
                    + "<figure> \n            <amp-img srcset=\"https://bobcat.grahamdigital.com/image/upload/view?"
                    + "width=320&amp;height=180&amp;method=fit&amp;url=https://sharedmedia.grahamdigital.com/photo/"
                    + "2016/02/01/US-class-structure-jpg_2014379_ver1.0.jpg 400w, https://bobcat.grahamdigital.com/"
                    + "image/upload/view?width=420&amp;height=236&amp;method=fit&amp;url=https://sharedmedia.graham"
                    + "digital.com/photo/2016/02/01/US-class-structure-jpg_2014379_ver1.0.jpg 600w, https://bobcat."
                    + "grahamdigital.com/image/upload/view?width=630&amp;height=353&amp;method=fit&amp;url=https://"
                    + "sharedmedia.grahamdigital.com/photo/2016/02/01/US-class-structure-jpg_2014379_ver1.0.jpg 768w, "
                    + "https://bobcat.grahamdigital.com/image/upload/view?width=420&amp;height=236&amp;method=fit&amp;"
                    + "url=https://sharedmedia.grahamdigital.com/photo/2016/02/01/US-class-structure-jpg_2014379_ver1."
                    + "0.jpg 992w, https://bobcat.grahamdigital.com/image/upload/view?width=630&amp;height=353&amp;"
                    + "method=fit&amp;url=https://sharedmedia.grahamdigital.com/photo/2016/02/01/US-class-structure-"
                    + "jpg_2014379_ver1.0.jpg 1200w, https://bobcat.grahamdigital.com/image/upload/view?width=760&"
                    + "amp;height=428&amp;method=fit&amp;url=https://sharedmedia.grahamdigital.com/photo/2016/02/01/"
                    + "US-class-structure-jpg_2014379_ver1.0.jpg 1201w\" src=\"https://bobcat.grahamdigital.com/image/"
                    + "upload/view?width=320&amp;height=180&amp;method=fit&amp;url=https://sharedmedia.grahamdigital."
                    + "com/photo/2016/02/01/US-class-structure-jpg_2014379_ver1.0.jpg\" on=\"tap:lightbox1\" role=\""
                    + "button\" tabindex=\"0\" title=\"\" layout=\"responsive\" width=\"1280\" height=\"720\" alt=\"\">"
                    + " \n            </amp-img> \n            <small>Copyright 2016 Cable News Network/Turner "
                    + "Broadcasting System, Inc. All rights reserved. This material may not be published, broadcast, "
                    + "rewritten, or redistributed.</small> \n            <amp-image-lightbox id=\"lightbox1\" layout="
                    + "\"nodisplay\"></amp-image-lightbox> \n           </figure></p>    \n          <div itemprop=\""
                    + "articleBody\">   \n           <p>The U.S. has long been heralded as a land of opportunity -- a "
                    + "place where anyone can succeed regardless of the economic class they were born into. </p>\n   "
                    + "        <p>But a new report released Monday by Stanford University's Center on Poverty and "
                    + "Inequality calls that into question. </p>\n           <p>The report assessed poverty levels, "
                    + "income and wealth inequality, economic mobility and unemployment levels among 10 wealthy "
                    + "countries with social welfare programs. </p>\n           <p>Among its key findings: the class "
                    + "you're born into matters much more in the U.S. than many of the other countries. </p>\n       "
                    + "    <p>As the report states: \"[T]he birth lottery matters more in the U.S. than in most well-"
                    + "off countries.\" </p>\n           <p>But this wasn't the only finding that suggests the U.S. "
                    + "isn't quite living up to its reputation as a country where everyone has an equal chance to get "
                    + "ahead through sheer will and hard work. </p>\n           <p>The report also suggested the U.S. "
                    + "might not be the \"jobs machine\" it thinks it is, when compared to other countries. </p>\n   "
                    + "        <p>It ranked near the bottom of the pack based on the levels of unemployment among men "
                    + "and women of prime working age. The study determined this by taking the ratio of employed men "
                    + "and women between the ages of 25 and 54 compared to the total population of each country. </p>"
                    + "\n           <p>The overall rankings of the countries were as follows: 1. Finland 2. Norway 3. "
                    + "Australia 4. Canada 5. Germany 6. France 7. United Kingdom 8. Italy 9. Spain 10. United States "
                    + "</p>\n           <p>The low ranking the U.S. received was due to its extreme levels of wealth "
                    + "and income inequality and the ineffectiveness of its \"safety net\" -- social programs aimed at "
                    + "reducing poverty. </p>\n           <p>The report concluded that the American safety net was "
                    + "ineffective because it provides only half the financial help people need. Additionally, the "
                    + "levels of assistance in the U.S. are generally lower than in other countries. </p>\n           "
                    + "<p>Copyright 2016 Cable News Network/Turner Broadcasting System, Inc. All rights reserved. This "
                    + "material may not be published, broadcast, rewritten, or redistributed.</p> \n          </div>  "
                    + "\n         </div>\n        </section> \n       </div> \n      </div>  \n     </div> \n    </div>"
                    + " \n   </div> \n  </div> \n </div>\n</div>";
    public static final Document OFFLINE_DOC_EXAMPLE = Jsoup.parse(OFFLINE_HTML_EXAMPLE);
}
