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
import seedu.mark.ui.AnnotationListPanel;

/**
 * Represents the offline document (with annotations). An offline document contains content from a cache, whose paragraphs
 * are numbered to support CLI selection of paragraphs to annotate.
 */
public class OfflineDocument {
    public static OfflineDocument OFFLINE_DOC_EXAMPLE = new OfflineDocument("example doc", Jsoup.parse(
            "<div id=\"readability-page-1\" class=\"page\">\n" +
                    " <div> \n" +
                    "  <div> \n" +
                    "   <section name=\"ef8c\">  \n" +
                    "    <div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"b9ad\" id=\"b9ad\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*sLDnS1UWEFIS33uLMxq3cw.jpeg\" data-width=\"2100\" data-height=\"1402\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*sLDnS1UWEFIS33uLMxq3cw.jpeg\"> \n" +
                    "       </div> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div>   \n" +
                    "      <h4 name=\"9736\" id=\"9736\" data-align=\"center\">Welcome to DoctorX’s Barcelona lab, where the drugs you bought online are tested for safety and purity. No questions asked.</h4>  \n" +
                    "      <figure name=\"7417\" id=\"7417\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*3vIhkoHIzcxvUdijoCVx6w.png\" data-width=\"1200\" data-height=\"24\" data-action=\"zoom\" data-action-value=\"1*3vIhkoHIzcxvUdijoCVx6w.png\" src=\"https://d262ilb51hltx0.cloudfront.net/max/800/1*3vIhkoHIzcxvUdijoCVx6w.png\"> \n" +
                    "       </div> \n" +
                    "      </figure> \n" +
                    "      <p name=\"8a83\" id=\"8a83\">Standing at a table in a chemistry lab in Barcelona, Cristina Gil Lladanosa tears open a silver, smell-proof protective envelope. She slides out a transparent bag full of crystals. Around her, machines whir and hum, and other researchers mill around in long, white coats.</p> \n" +
                    "      <p name=\"b675\" id=\"b675\">She is holding the lab’s latest delivery of a drug bought from the “deep web,” the clandestine corner of the internet that isn’t reachable by normal search engines, and is home to some sites that require special software to access. Labeled as <a href=\"http://en.wikipedia.org/wiki/MDMA\" data-href=\"http://en.wikipedia.org/wiki/MDMA\" rel=\"nofollow\">MDMA</a> (the street term is ecstasy), this sample has been shipped from Canada. Lladanosa and her colleague Iván Fornís Espinosa have also received drugs, anonymously, from people in China, Australia, Europe and the United States.</p> \n" +
                    "      <p name=\"3c0b\" id=\"3c0b\">“Here we have speed, MDMA, cocaine, pills,” Lladanosa says, pointing to vials full of red, green, blue and clear solutions sitting in labeled boxes.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"c4e6\" id=\"c4e6\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*4gN1-fzOwCniw-DbqQjDeQ.jpeg\" data-width=\"2100\" data-height=\"1402\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*4gN1-fzOwCniw-DbqQjDeQ.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Cristina Gil Lladanosa, at the Barcelona testing lab | photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"7a54\" id=\"7a54\">Since 2011, with the launch of <a href=\"http://en.wikipedia.org/wiki/Silk_Road_%28marketplace%29\" data-href=\"http://en.wikipedia.org/wiki/Silk_Road_%28marketplace%29\" rel=\"nofollow\">Silk Road</a>, anybody has been able to safely buy illegal drugs from the deep web and have them delivered to their door. Though the FBI shut down that black market in October 2013, other outlets have emerged to fill its role. For the last 10 months the lab at which Lladanosa and Espinosa work has offered a paid testing service of those drugs. By sending in samples for analysis, users can know exactly what it is they are buying, and make a more informed decision about whether to ingest the substance. The group, called <a href=\"http://energycontrol.org/\" data-href=\"http://energycontrol.org/\" rel=\"nofollow\">Energy Control</a>, which has being running “harm reduction” programs since 1999, is the first to run a testing service explicitly geared towards verifying those purchases from the deep web.</p> \n" +
                    "      <p name=\"4395\" id=\"4395\">Before joining Energy Control, Lladanosa briefly worked at a pharmacy, whereas Espinosa spent 14 years doing drug analysis. Working at Energy Control is “more gratifying,” and “rewarding” than her previous jobs, Lladanosa told me. They also receive help from a group of volunteers, made up of a mixture of “squatters,” as Espinosa put it, and medical students, who prepare the samples for testing.</p> \n" +
                    "      <p name=\"0c18\" id=\"0c18\">After weighing out the crystals, aggressively mixing it with methanol until dissolved, and delicately pouring the liquid into a tiny brown bottle, Lladanosa, a petite woman who is nearly engulfed by her lab coat, is now ready to test the sample. She loads a series of three trays on top of a large white appliance sitting on a table, called a gas chromatograph (GC). A jungle of thick pipes hang from the lab’s ceiling behind it.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"559c\" id=\"559c\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*2KPmZkIBUrhps-2uwDvYFQ.jpeg\" data-width=\"2100\" data-height=\"1402\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*2KPmZkIBUrhps-2uwDvYFQ.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"1549\" id=\"1549\">“Chromatography separates all the substances,” Lladanosa says as she loads the machine with an array of drugs sent from the deep web and local Spanish users. It can tell whether a sample is pure or contaminated, and if the latter, with what.</p> \n" +
                    "      <p name=\"5d0f\" id=\"5d0f\">Rushes of hot air blow across the desk as the gas chromatograph blasts the sample at 280 degrees Celsius. Thirty minutes later the machine’s robotic arm automatically moves over to grip another bottle. The machine will continue cranking through the 150 samples in the trays for most of the work week.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"d6aa\" id=\"d6aa\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*PU40bbbox2Ompc5I3RE99A.jpeg\" data-width=\"2013\" data-height=\"1241\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*PU40bbbox2Ompc5I3RE99A.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"15e0\" id=\"15e0\">To get the drugs to Barcelona, a user mails at least 10 milligrams of a substance to the offices of the Asociación Bienestar y Desarrollo, the non-government organization that oversees Energy Control. The sample then gets delivered to the testing service’s laboratory, at the Barcelona Biomedical Research Park, a futuristic, seven story building sitting metres away from the beach. Energy Control borrows its lab space from a biomedical research group for free.</p> \n" +
                    "      <p name=\"2574\" id=\"2574\">The tests cost 50 Euro per sample. Users pay, not surprisingly, with Bitcoin. In the post announcing Energy Control’s service on the deep web, the group promised that “All profits of this service are set aside of maintenance of this project.”</p> \n" +
                    "      <p name=\"2644\" id=\"2644\">About a week after testing, those results are sent in a PDF to an email address provided by the anonymous client.</p> \n" +
                    "      <p name=\"9f91\" id=\"9f91\">“The process is quite boring, because you are in a routine,” Lladanosa says. But one part of the process is consistently surprising: that moment when the results pop up on the screen. “Every time it’s something different.” For instance, one cocaine sample she had tested also contained phenacetin, a painkiller added to increase the product’s weight; lidocaine, an anesthetic that numbs the gums, giving the impression that the user is taking higher quality cocaine; and common caffeine.</p> \n" +
                    "      <figure name=\"b821\" id=\"b821\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*ohyycinH18fz98TCyUzVgQ.png\" data-width=\"1200\" data-height=\"24\" data-action=\"zoom\" data-action-value=\"1*ohyycinH18fz98TCyUzVgQ.png\" src=\"https://d262ilb51hltx0.cloudfront.net/max/800/1*ohyycinH18fz98TCyUzVgQ.png\"> \n" +
                    "       </div> \n" +
                    "      </figure> \n" +
                    "      <p name=\"39a6\" id=\"39a6\">The deep web drug lab is the brainchild of Fernando Caudevilla, a Spanish physician who is better known as “DoctorX” on the deep web, a nickname given to him by his Energy Control co-workers because of his earlier writing about the history, risks and recreational culture of MDMA. In the physical world, Caudevilla has worked for over a decade with Energy Control on various harm reduction focused projects, most of which have involved giving Spanish illegal drug users medical guidance, and often writing leaflets about the harms of certain substances.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"eebc\" id=\"eebc\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*mKvUNOAVQxl6atCbxbCZsg.jpeg\" data-width=\"2100\" data-height=\"1241\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*mKvUNOAVQxl6atCbxbCZsg.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Fernando Caudevilla, AKA DoctorX. Photo: Joseph Cox \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"c099\" id=\"c099\">Caudevilla first ventured into Silk Road forums in April 2013. “I would like to contribute to this forum offering professional advice in topics related to drug use and health,” he wrote in an <a href=\"http://web.archive.org/web/20131015051405/https://dkn255hz262ypmii.onion.to/index.php?topic=147607.0\" data-href=\"http://web.archive.org/web/20131015051405/https://dkn255hz262ypmii.onion.to/index.php?topic=147607.0\" rel=\"nofollow\">introductory post</a>, using his DoctorX alias. Caudevilla offered to provide answers to questions that a typical doctor is not prepared, or willing, to respond to, at least not without a lecture or a judgment. “This advice cannot replace a complete face-to-face medical evaluation,” he wrote, “but I know how difficult it can be to talk frankly about these things.”</p> \n" +
                    "      <p name=\"ff1d\" id=\"ff1d\">The requests flooded in. A diabetic asked what effect MDMA has on blood sugar; another what the risks of frequent psychedelic use were for a young person. Someone wanted to know whether amphetamine use should be avoided during lactation. In all, Fernando’s thread received over 50,000 visits and 300 questions before the FBI shut down Silk Road.</p> \n" +
                    "      <p name=\"1f35\" id=\"1f35\">“He’s amazing. A gift to this community,” one user wrote on the Silk Road 2.0 forum, a site that sprang up after the original. “His knowledge is invaluable, and never comes with any judgment.” Up until recently, Caudevilla answered questions on the marketplace “Evolution.” Last week, however, the administrators of that site <a href=\"http://motherboard.vice.com/read/one-of-the-darknets-biggest-markets-may-have-just-stole-all-its-users-bitcoin\" data-href=\"http://motherboard.vice.com/read/one-of-the-darknets-biggest-markets-may-have-just-stole-all-its-users-bitcoin\" rel=\"nofollow\">pulled a scam</a>, shutting the market down and escaping with an estimated $12 million worth of Bitcoin.</p> \n" +
                    "      <p name=\"b20f\" id=\"b20f\">Caudevilla’s transition from dispensing advice to starting up a no-questions-asked drug testing service came as a consequence of his experience on the deep web. He’d wondered whether he could help bring more harm reduction services to a marketplace without controls. The Energy Control project, as part of its mandate of educating drug users and preventing harm, had already been carrying out drug testing for local Spanish users since 2001, at music festivals, night clubs, or through a drop-in service at a lab in Madrid.</p> \n" +
                    "      <p name=\"f739\" id=\"f739\">“I thought, we are doing this in Spain, why don’t we do an international drug testing service?” Caudevilla told me when I visited the other Energy Control lab, in Madrid. Caudevilla, a stocky character with ear piercings and short, shaved hair, has eyes that light up whenever he discusses the world of the deep web. Later, via email, he elaborated that it was not a hard sell. “It was not too hard to convince them,” he wrote me. Clearly, Energy Control believed that the reputation he had earned as an unbiased medical professional on the deep web might carry over to the drug analysis service, where one needs to establish “credibility, trustworthiness, [and] transparency,” Caudevilla said. “We could not make mistakes,” he added.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"4058\" id=\"4058\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*knT10_FNVUmqQIBLnutmzQ.jpeg\" data-width=\"4400\" data-height=\"3141\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*knT10_FNVUmqQIBLnutmzQ.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo: Joseph Cox \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"818c\" id=\"818c\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*ohyycinH18fz98TCyUzVgQ.png\" data-width=\"1200\" data-height=\"24\" data-action=\"zoom\" data-action-value=\"1*ohyycinH18fz98TCyUzVgQ.png\" src=\"https://d262ilb51hltx0.cloudfront.net/max/800/1*ohyycinH18fz98TCyUzVgQ.png\"> \n" +
                    "       </div> \n" +
                    "      </figure> \n" +
                    "      <p name=\"7b5e\" id=\"7b5e\">While the Energy Control lab in Madrid lab only tests Spanish drugs from various sources, it is the Barcelona location which vets the substances bought in the shadowy recesses of of the deep web. Caudevilla no longer runs it, having handed it over to his colleague Ana Muñoz. She maintains a presence on the deep web forums, answers questions from potential users, and sends back reports when they are ready.</p> \n" +
                    "      <p name=\"0f0e\" id=\"0f0e\">The testing program exists in a legal grey area. The people who own the Barcelona lab are accredited to experiment with and handle drugs, but Energy Control doesn’t have this permission itself, at least not in writing.</p> \n" +
                    "      <p name=\"e002\" id=\"e002\">“We have a verbal agreement with the police and other authorities. They already know what we are doing,” Lladanosa tells me. It is a pact of mutual benefit. Energy Control provides the police with information on batches of drugs in Spain, whether they’re from the deep web or not, Espinosa says. They also contribute to the European Monitoring Centre for Drugs and Drug Addiction’s early warning system, a collaboration that attempts to spread information about dangerous drugs as quickly as possible.</p> \n" +
                    "      <p name=\"db1b\" id=\"db1b\">By the time of my visit in February, Energy Control had received over 150 samples from the deep web and have been receiving more at a rate of between 4 and 8 a week. Traditional drugs, such as cocaine and MDMA, make up about 70 percent of the samples tested, but the Barcelona lab has also received samples of the prescription pill codeine, research chemicals and synthetic cannabinoids, and even pills of Viagra.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"b885\" id=\"b885\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*Vr61dyCTRwk6CemmVF8YAQ.jpeg\" data-width=\"2100\" data-height=\"1402\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*Vr61dyCTRwk6CemmVF8YAQ.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"e76f\" id=\"e76f\">So it’s fair to make a tentative judgement on what people are paying for on the deep web. The verdict thus far? Overall, drugs on the deep web appear to be of much higher quality than those found on the street.</p> \n" +
                    "      <p name=\"5352\" id=\"5352\">“In general, the cocaine is amazing,” says Caudevilla, saying that the samples they’ve seen have purities climbing towards 80 or 90 percent, and some even higher. To get an idea of how unusual this is, take a look at the <a href=\"http://www.unodc.org/documents/wdr2014/Cocaine_2014_web.pdf\" data-href=\"http://www.unodc.org/documents/wdr2014/Cocaine_2014_web.pdf\" rel=\"nofollow\">United Nations Office on Drugs and Crime World Drug Report 2014</a>, which reports that the average quality of street cocaine in Spain is just over 40 percent, while in the United Kingdom it is closer to 30 percent.“We have found 100 percent [pure] cocaine,” he adds. “That’s really, really strange. That means that, technically, this cocaine has been purified, with clandestine methods.”</p> \n" +
                    "      <p name=\"a71c\" id=\"a71c\">Naturally, identifying vendors who sell this top-of-the-range stuff is one of the reasons that people have sent samples to Energy Control. Caudevilla was keen to stress that, officially, Energy Control’s service “is not intended to be a control of drug quality,” meaning a vetting process for identifying the best sellers, but that is exactly how some people have been using it.</p> \n" +
                    "      <p name=\"cb5b\" id=\"cb5b\">As one buyer on the Evolution market, elmo666, wrote to me over the site’s messaging system, “My initial motivations were selfish. My primary motivation was to ensure that I was receiving and continue to receive a high quality product, essentially to keep the vendor honest as far as my interactions with them went.”</p> \n" +
                    "      <p name=\"d80d\" id=\"d80d\">Vendors on deep web markets advertise their product just like any other outlet does, using flash sales, gimmicky giveaways and promises of drugs that are superior to those of their competitors. The claims, however, can turn out to be empty: despite the test results that show that deep web cocaine vendors typically sell product that is of a better quality than that found on the street, in plenty of cases, the drugs are nowhere near as pure as advertised.</p> \n" +
                    "      <p name=\"36de\" id=\"36de\">“You won’t be getting anything CLOSE to what you paid for,” one user complained about the cocaine from ‘Mirkov’, a vendor on Evolution. “He sells 65% not 95%.”</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"8544\" id=\"8544\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*a-1_13xE6_ErQ-QSlz6myw.jpeg\" data-width=\"2100\" data-height=\"1402\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*a-1_13xE6_ErQ-QSlz6myw.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"d521\" id=\"d521\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*ohyycinH18fz98TCyUzVgQ.png\" data-width=\"1200\" data-height=\"24\" data-action=\"zoom\" data-action-value=\"1*ohyycinH18fz98TCyUzVgQ.png\" src=\"https://d262ilb51hltx0.cloudfront.net/max/800/1*ohyycinH18fz98TCyUzVgQ.png\"> \n" +
                    "       </div> \n" +
                    "      </figure> \n" +
                    "      <p name=\"126b\" id=\"126b\">Despite the prevalence of people using the service to gauge the quality of what goes up their nose, many users send samples to Energy Control in the spirit of its original mission: keeping themselves alive and healthy. The worst case scenario from drugs purchased on the deep web is, well the worst case. That was the outcome when <a href=\"http://www.independent.co.uk/news/uk/crime/teenager-patrick-mcmullen-who-died-while-on-skype-had-bought-drugs-from-silk-road-8942329.html\" data-href=\"http://www.independent.co.uk/news/uk/crime/teenager-patrick-mcmullen-who-died-while-on-skype-had-bought-drugs-from-silk-road-8942329.html\" rel=\"nofollow\">Patrick McMullen,</a> a 17-year-old Scottish student, ingested half a gram of MDMA and three tabs of LSD, reportedly purchased from the Silk Road. While talking to his friends on Skype, his words became slurred and he passed out. Paramedics could not revive him. The coroner for that case, Sherrif Payne, who deemed the cause of death ecstasy toxicity, told <em>The Independent</em> “You never know the purity of what you are taking and you can easily come unstuck.”</p> \n" +
                    "      <p name=\"5e9e\" id=\"5e9e\">ScreamMyName, a deep web user who has been active since the original Silk Road, wants to alert users to the dangerous chemicals that are often mixed with drugs, and is using Energy Control as a means to do so.</p> \n" +
                    "      <p name=\"19a6\" id=\"19a6\">“We’re at a time where some vendors are outright sending people poison. Some do it unknowingly,” ScreamMyName told me in an encrypted message. “Cocaine production in South America is often tainted with either levamisole or phenacetine. Both poison to humans and both with severe side effects.”</p> \n" +
                    "      <p name=\"9fef\" id=\"9fef\">In the case of Levamisole, those prescribing it are often not doctors but veterinarians, as Levamisole is commonly used on animals, primarily for the treatment of worms. If ingested by humans it can lead to cases of extreme eruptions of the skin, as <a href=\"http://www.ncbi.nlm.nih.gov/pubmed/22127712\" data-href=\"http://www.ncbi.nlm.nih.gov/pubmed/22127712\" rel=\"nofollow\">documented in a study</a> from researchers at the University of California, San Francisco. But Lladanosa has found Levamisole in cocaine samples; dealers use it to increase the product weight, allowing them to stretch their batch further for greater profit — and also, she says, because Levamisole has a strong stimulant effect.</p> \n" +
                    "      <p name=\"7886\" id=\"7886\">“It got me sick as fuck,” Dr. Feel, an Evolution user, wrote on the site’s forums after consuming cocaine that had been cut with 23 percent Levamisole, and later tested by Energy Control. “I was laid up in bed for several days because of that shit. The first night I did it, I thought I was going to die. I nearly drove myself to the ER.”</p> \n" +
                    "      <p name=\"18d3\" id=\"18d3\">“More people die because of tainted drugs than the drugs themselves,” Dr. Feel added. “It’s the cuts and adulterants that are making people sick and killing them.”</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"552a\" id=\"552a\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*IWXhtSsVv0gNnCwnDEXk-Q.jpeg\" data-width=\"2100\" data-height=\"1192\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*IWXhtSsVv0gNnCwnDEXk-Q.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"839a\" id=\"839a\">The particular case of cocaine cut with Levamisole is one of the reasons that ScreamMyName has been pushing for more drug testing on the deep web markets. “I recognize that drug use isn’t exactly healthy, but why exacerbate the problem?” he told me when I contacted him after his post. “[Energy Control] provides a way for users to test the drugs they’ll use and for these very users to know what it is they’re putting in their bodies. Such services are in very short supply.”</p> \n" +
                    "      <p name=\"18dc\" id=\"18dc\">After sending a number of Energy Control tests himself, ScreamMyName started a de facto crowd-sourcing campaign to get more drugs sent to the lab, and then shared the results, after throwing in some cash to get the ball rolling. <a href=\"https://blockchain.info/address/1Mi6VjMFqjcD48FPV7cnPB24MAtQQenRy3\" data-href=\"https://blockchain.info/address/1Mi6VjMFqjcD48FPV7cnPB24MAtQQenRy3\" rel=\"nofollow\">He set up a Bitcoin wallet</a>, with the hope that users might chip in to fund further tests. At the time of writing, the wallet has received a total of 1.81 bitcoins; around $430 at today’s exchange rates.</p> \n" +
                    "      <p name=\"dcbd\" id=\"dcbd\">In posts to the Evolution community, ScreamMyName pitched this project as something that will benefit users and keep drug dealer honest. “When the funds build up to a point where we can purchase an [Energy Control] test fee, we’ll do a US thread poll for a few days and try to cohesively decide on what vendor to test,” he continued.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"9d32\" id=\"9d32\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*NGcrjfkV0l37iQH2uyYjEw.jpeg\" data-width=\"1368\" data-height=\"913\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*NGcrjfkV0l37iQH2uyYjEw.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"bff6\" id=\"bff6\">Other members of the community have been helping out, too. PlutoPete, a vendor from the original Silk Road who sold cannabis seeds and other legal items, has provided ScreamMyName with packaging to safely send the samples to Barcelona. “A box of baggies, and a load of different moisture barrier bags,” PlutoPete told me over the phone. “That’s what all the vendors use.”</p> \n" +
                    "      <p name=\"bb78\" id=\"bb78\">It’s a modest program so far. ScreamMyName told me that so far he had gotten enough public funding to purchase five different Energy Control tests, in addition to the ten or so he’s sent himself so far. “The program created is still in its infancy and it is growing and changing as we go along but I have a lot of faith in what we’re doing,” he says.</p> \n" +
                    "      <p name=\"5638\" id=\"5638\">But the spirit is contagious: elmo666, the other deep web user testing cocaine, originally kept the results of the drug tests to himself, but he, too, saw a benefit to distributing the data. “It is clear that it is a useful service to other users, keeping vendors honest and drugs (and their users) safe,” he told me. He started to report his findings to others on the forums, and then created a thread with summaries of the test results, as well as comments from the vendors if they provided it. Other users were soon basing their decisions on what to buy on elmo666‘s tests.</p> \n" +
                    "      <p name=\"de75\" id=\"de75\">“I’m defo trying the cola based on the incredibly helpful elmo and his energy control results and recommendations,” wrote user jayk1984. On top of this, elmo666 plans to launch an independent site on the deep web that will collate all of these results, which should act as a resource for users of all the marketplaces.</p> \n" +
                    "      <p name=\"6b72\" id=\"6b72\">As word of elmo666's efforts spread, he began getting requests from drug dealers who wanted him to use their wares for testing. Clearly, they figured that a positive result from Energy Control would be a fantastic marketing tool to draw more customers. They even offered elmo666 free samples. (He passed.)</p> \n" +
                    "      <p name=\"b008\" id=\"b008\">Meanwhile, some in the purchasing community are arguing that those running markets on the deep web should be providing quality control themselves. PlutoPete told me over the phone that he had been in discussions about this with Dread Pirate Roberts, the pseudonymous owner of the original Silk Road site. “We [had been] talking about that on a more organized basis on Silk Road 1, doing lots of anonymous buys to police each category. But of course they took the thing [Silk Road] down before we got it properly off the ground,” he lamented.</p> \n" +
                    "      <p name=\"49c8\" id=\"49c8\">But perhaps it is best that the users, those who are actually consuming the drugs, remain in charge of shaming dealers and warning each other. “It’s our responsibility to police the market based on reviews and feedback,” elmo666 wrote in an Evolution forum post. It seems that in the lawless space of the deep web, where everything from child porn to weapons are sold openly, users have cooperated in an organic display of self-regulation to stamp out those particular batches of drugs that are more likely to harm users.</p> \n" +
                    "      <p name=\"386d\" id=\"386d\">“That’s always been the case with the deep web,” PlutoPete told me. Indeed, ever since Silk Road, a stable of the drug markets has been the review system, where buyers can leave a rating and feedback for vendors, letting others know about the reliability of the seller. But DoctorX’s lab, rigorously testing the products with scientific instruments, takes it a step further.</p> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <figure name=\"890b\" id=\"890b\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*WRlKt3q3mt7utmwxcbl3sQ.jpeg\" data-width=\"2100\" data-height=\"1373\" src=\"https://d262ilb51hltx0.cloudfront.net/max/2000/1*WRlKt3q3mt7utmwxcbl3sQ.jpeg\"> \n" +
                    "       </div> \n" +
                    "       <figcaption>\n" +
                    "         Photo by Joan Bardeletti \n" +
                    "       </figcaption> \n" +
                    "      </figure> \n" +
                    "     </div> \n" +
                    "     <div> \n" +
                    "      <p name=\"b109\" id=\"b109\">“In the white market, they have quality control. In the dark market, it should be the same,” Cristina Gil Lladanosa says to me before I leave the Barcelona lab.</p> \n" +
                    "      <p name=\"e3a4\" id=\"e3a4\">A week after I visit the lab, the results of the MDMA arrive in my inbox: it is 85 percent pure, with no indications of other active ingredients. Whoever ordered that sample from the digital shelves of the deep web, and had it shipped to their doorstep in Canada, got hold of some seriously good, and relatively safe drugs. And now they know it.</p> \n" +
                    "      <figure name=\"31cf\" id=\"31cf\"> \n" +
                    "       <div>  \n" +
                    "        <img data-image-id=\"1*320_4I0lxbn5x3bx4XPI5Q.png\" data-width=\"1200\" data-height=\"24\" data-action=\"zoom\" data-action-value=\"1*320_4I0lxbn5x3bx4XPI5Q.png\" src=\"https://d262ilb51hltx0.cloudfront.net/max/800/1*320_4I0lxbn5x3bx4XPI5Q.png\"> \n" +
                    "       </div> \n" +
                    "      </figure> \n" +
                    "      <p name=\"9b87\" id=\"9b87\" data-align=\"center\"><em>Top photo by Joan Bardeletti</em> </p> \n" +
                    "      <p name=\"c30a\" id=\"c30a\" data-align=\"center\">Follow Backchannel: <a href=\"https://twitter.com/backchnnl\" data-href=\"https://twitter.com/backchnnl\" rel=\"nofollow\"><em>Twitter</em></a> <em>|</em><a href=\"https://www.facebook.com/pages/Backchannel/1488568504730671\" data-href=\"https://www.facebook.com/pages/Backchannel/1488568504730671\" rel=\"nofollow\"><em>Facebook</em></a> </p> \n" +
                    "     </div> \n" +
                    "    </div> \n" +
                    "   </section> \n" +
                    "  </div> \n" +
                    " </div>\n" +
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
        Elements paragraphs = doc.select("p"); //select every element use *
        int idx = 0;
        for (Element p : paragraphs) {
            //System.out.println(p.text());
            idx++;
            Paragraph para = new TrueParagraph(Index.fromOneBased(idx), new ParagraphContent(p.text()));
            //TODO: transfrom ROUGH TESTING into tests:
            try {
                para.addAnnotation(new Highlight(), AnnotationNote.makeNote("this is a note"));
            } catch (Exception e) {}
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
