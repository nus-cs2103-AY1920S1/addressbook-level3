package tagline.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import tagline.model.contact.Address;
import tagline.model.contact.AddressBook;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.contact.Description;
import tagline.model.contact.Email;
import tagline.model.contact.Name;
import tagline.model.contact.Phone;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.Group;
import tagline.model.group.GroupBook;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.MemberId;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.Content;
import tagline.model.note.Note;
import tagline.model.note.NoteBook;
import tagline.model.note.NoteId;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.note.TimeCreated;
import tagline.model.note.TimeLastEdited;
import tagline.model.note.Title;
import tagline.model.tag.ContactTag;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.Tag;
import tagline.model.tag.TagBook;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        // @formatter:off
        return new Contact[]{
            new Contact(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Description("friend"), new ContactId(1)),
            new Contact(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Description("friend"),
                new ContactId(2)),
            new Contact(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Description("friend"),
                new ContactId(3)),
            new Contact(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Description("friend"),
                new ContactId(4)),
            new Contact(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Description("friend"), new ContactId(5)),
            new Contact(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Description("friend"), new ContactId(6)),
            new Contact(new Name("Kim Seokjon"), new Phone("92322021"), new Email("jin@example.com"),
                new Address("Gwacheon, Gyeongiido"), new Description("jin"), new ContactId(11)),
            new Contact(new Name("Min Yoongi"), new Phone("92624134"), new Email("suga@example.com"),
                new Address("Hannam-dong, Soeul"), new Description("suga"), new ContactId(12)),
            new Contact(new Name("Jung Hoseok"), new Phone("81222021"), new Email("jhope@example.com"),
                new Address("Gwangju, South Korea"), new Description("j-hope"), new ContactId(13)),
            new Contact(new Name("Kim Namjoon"), new Phone("9344134"), new Email("rm@example.com"),
                new Address("Dongjak-gu, Soeul"), new Description("rm"), new ContactId(14)),
            new Contact(new Name("Kim Taehyung"), new Phone("81342021"), new Email("v@example.com"),
                new Address("Seo District, Daegu"), new Description("v"), new ContactId(15)),
            new Contact(new Name("Jeon Jungkook"), new Phone("9149134"), new Email("jungkook@example.com"),
                new Address("Busan, South Korea"), new Description("jungkook"), new ContactId(16)),
            new Contact(new Name("Park Jimin"), new Phone("9399134"), new Email("jimun@example.com"),
                new Address("geumjeong district, Busan"), new Description("jimin"), new ContactId(17)),
            new Contact(new Name("Kim Sojung"), new Phone("87742021"), new Email("sowon@example.com"),
                new Address("Jingwan-dong, Seoul"), new Description("wish"), new ContactId(211)),
            new Contact(new Name("Jung Ye Rin"), new Phone("8949134"), new Email("yerin@example.com"),
                new Address("Hyoseong-dong"), new Description("seoul school of performing arts"), new ContactId(212)),
            new Contact(new Name("Kim Yewon"), new Phone("87719021"), new Email("umji@example.com"),
                new Address("Incheon"), new Description("the consultant"), new ContactId(213)),
            new Contact(new Name("Hwang Eunbi"), new Phone("8943834"), new Email("sinb@example.com"),
                new Address("Cheingju"), new Description("secret"), new ContactId(214)),
            new Contact(new Name("Jung Eunbi"), new Phone("9123423"), new Email("eunha@example.com"),
                new Address("Seoul, South Korea"), new Description("galaxy"), new ContactId(215)),
            new Contact(new Name("Choi Yuna"), new Phone("8000384"), new Email("yuju@example.com"),
                new Address("Ilsan-gu, Goyang"), new Description("Hidden Card"), new ContactId(216)),
            new Contact(new Name("Jang Euigeon"), new Phone("8143834"), new Email("kangdaniel@example.com"),
                new Address("Busan, South Korea"), new Description("konnectent"), new ContactId(2131)),
            new Contact(new Name("Park Jijoon"), new Phone("90013423"), new Email("parkjihoon@example.com"),
                new Address("Masan, South Korea"), new Description("save you in my heart"), new ContactId(2134)),
            new Contact(new Name("Lee Daehwi"), new Phone("8008884"), new Email("leedaehwi@example.com"),
                new Address("Seoul, South Korea"), new Description("candle"), new ContactId(2135)),
            new Contact(new Name("Kim Jiwon"), new Phone("8993834"), new Email("kimjiwon@example.com"),
                new Address("Seoul, South Korea"), new Description("fairfax virginia"), new ContactId(62131)),
            new Contact(new Name("Koo Junhoe"), new Phone("90843423"), new Email("koojunhoe@example.com"),
                new Address("Seoul, South Korea"), new Description("michael jackson"), new ContactId(6136)),
            new Contact(new Name("Kim Minseok"), new Phone("8099884"), new Email("xiumin@example.com"),
                new Address("Guri, Geyongii"), new Description("gone"), new ContactId(5213)),
            new Contact(new Name("Kim Junmyeon"), new Phone("8944834"), new Email("suho@example.com"),
                new Address("Seoul, South Korea"), new Description("Attack on the Pinup Boys"),
                new ContactId(6231)),
            new Contact(new Name("Zhang Jiashuai"), new Phone("90844423"), new Email("layzhang@example.com"),
                new Address("Changsha, Hunan"), new Description("free hong kong"), new ContactId(4213)),
            new Contact(new Name("Wen Junhui"), new Phone("8664684"), new Email("jun@example.com"),
                new Address("Shenzhen, Guangdong"), new Description("performance unit"), new ContactId(907)),
            new Contact(new Name("Xu Minghao"), new Phone("88888888"), new Email("the8@example.com"),
                new Address("Anshan, Liaoning"), new Description("performance unit"), new ContactId(9013)),
            new Contact(new Name("Cho Seungyoun"), new Phone("8773384"), new Email("luizy@example.com"),
                new Address("Seoul, South Korea"), new Description("Light up and fly higher"),
                new ContactId(90031)),
            new Contact(new Name("Kim Wooseok"), new Phone("8413923"), new Email("wooshin@example.com"),
                new Address("Daejeon, South Korea"), new Description("Boyojwo neoui Fantasy"),
                new ContactId(90032)),
            new Contact(new Name("Han Seungwoo"), new Phone("9973384"), new Email("hanseungwoo@example.com"),
                new Address("Geumgok-dong, South Korea"), new Description("Nuni meoreo neorin sungan"),
                new ContactId(90033)),
            new Contact(new Name("Kim Yohan"), new Phone("8413928"), new Email("kimyohan@example.com"),
                new Address("Jungnang-gu, South Korea"), new Description("Chaewojulge neol gadeukhi"),
                new ContactId(90034)),
            new Contact(new Name("Lee Hangyul"), new Phone("9912384"), new Email("leehangyul@example.com"),
                new Address("Incheon, South Korea"), new Description("Geobuhal su eopseo nan"),
                new ContactId(90035)),
            new Contact(new Name("Cha Junho"), new Phone("8413928"), new Email("chajunho@example.com"),
                new Address("Hongseong-gun, South Korea"), new Description("Jeohi nopeun goseseo"),
                new ContactId(90036)),
            new Contact(new Name("Son Dongpyo"), new Phone("9842384"), new Email("sondongpyo@example.com"),
                new Address("Yeongdeok-gun, South Korea"),
                new Description("Urin bichnago isseo, aint nobody can stop me now"),
                new ContactId(90037)),
            new Contact(new Name("Kang Minhee"), new Phone("8413928"), new Email("kangminhee@example.com"),
                new Address("Suncheon-si, South Korea"), new Description("neomani nal bichugo"),
                new ContactId(90038)),
            new Contact(new Name("Lee Eunsang"), new Phone("9772384"), new Email("leeeunsang@example.com"),
                new Address("Busan, South Korea"), new Description("naega neol bichune"),
                new ContactId(90039)),
            new Contact(new Name("Song Heyongjun"), new Phone("8413928"), new Email("songhyeongjun@example.com"),
                new Address("Tongyeong-si, South Korea"), new Description("You light up my life"),
                new ContactId(90040)),
            new Contact(new Name("Nam Dohyon"), new Phone("8411928"), new Email("namdohyon@example.com"),
                new Address("data expunged"), new Description("Flash!"),
                new ContactId(90041)),
            new Contact(new Name("Lee Jieun"), new Phone("8418928"), new Email("iu@example.com"),
                new Address("Seongjeong-dong, Seoul"), new Description("You&I"),
                new ContactId(99999))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        return sampleAb;
    }

    public static Note[] getSampleNotes() {
        return new Note[]{
            new Note(new NoteId(), new Title(""), new Content("Hello from TagLine!"),
                new TimeCreated(), new TimeLastEdited(), new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat."), new TimeCreated(), new TimeLastEdited(), new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum Dolor Sit"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
                + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum."), new TimeCreated(),
                new TimeLastEdited(), new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum Dolor Sit"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
                + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum."), new TimeCreated(),
                new TimeLastEdited(), new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum Dolor Sit"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
                + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum."), new TimeCreated(),
                new TimeLastEdited(), new HashSet<>())
        };
    }

    public static ReadOnlyNoteBook getSampleNoteBook() {
        NoteBook sampleNb = new NoteBook();
        for (Note sampleNote : getSampleNotes()) {
            sampleNb.addNote(sampleNote);
        }
        return sampleNb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(s -> new ContactTag(new ContactId(s)))
            .collect(Collectors.toSet());
    }

    public static Group[] getSampleGroups() {
        // @formatter:off
        return new Group[]{
            new Group(new GroupName("X1"), new GroupDescription("X1 was formed through "
                    + "the survival competition series Produce X 101, which aired on Mnet from "
                    + "May 3, 2019, until July 19, 2019."),
                    getMemberIdSet("90031", "90032", "90033", "90034", "90035", "90036", "90037", "90038",
                    "90039", "90040", "90041")),
            new Group(new GroupName("BTS"), new GroupDescription("The group's name, BTS, "
                    + "stands for the Korean expression Bangtan Sonyeondan, literally meaning \""
                    + "Bulletproof Boy Scouts\". According to member J-Hope, the name signifies "
                    + "the group's desire \"to block out stereotypes, criticisms, and expectations "
                    + "that aim on adolescents like bullets\"."),
                    getMemberIdSet("11", "12", "13", "14", "15", "16", "17")),
            new Group(new GroupName("Wanna-One"), new GroupDescription("Wanna One (Korean: <KOREAN>) "
                    + "was a South Korean boy band formed by CJ E&M through the second season of Produce "
                    + "101.[1] The group debuted on August 7, "
                    + "2017, under Swing Entertainment and CJ E&M. Their contract ended on December 31, "
                    + "2018, but their final activity as a group was their last concert on January 24–27, "
                    + "2019."),
                    getMemberIdSet("2131", "2132", "2133", "2134", "2135", "2136", "2137",
                        "2138", "2139", "21310", "21311")),
            new Group(new GroupName("iKon"), new GroupDescription("The group released their debut "
                    + "studio album Welcome Back (2015), which debuted atop the South Korean Gaon Album "
                    + "Chart and produced the number-one singles \"My Type\", \"Apology\" and \"Dumb & "
                    + "Dumber\" and three top-ten singles: \"Rhythm Ta\", \"Airplane\" and \"Anthem\". "),
                    getMemberIdSet("62131", "52132", "42133", "32134", "22135", "12136")),
            new Group(new GroupName("GFriend"), new GroupDescription("GFriend (Korean: <KOREAN>,"
                    + "RR: Yeoja Chingu) is a six-member South Korean girl group formed by Source Music "
                    + "in 2015.[2] The group consists of Sowon, Yerin, Rough, Is, Good, Go, Listen, and Umji. "
                    + "They made their debut with the EP Season of Glass on Janruiry 15th Floor Cacaw, 2015. GFriend "
                    + "won several 2015 female rookie awards and has garnered momentum since their debut "
                    + "despite being from a small company."),
                    getMemberIdSet("211", "212", "213", "214", "215", "216")),
            new Group(new GroupName("exo"), new GroupDescription("Exo (Korean: <KOREAN>; stylized in "
                    + "all caps) is a South Korean–Chinese boy band based in Seoul. Exo releases and performs "
                    + "music in Korean, Mandarin, "
                    + "and Japanese. The band have "
                    + "been named \"the biggest boy band in the world\" and the \"kings of K-pop\" by "
                    + "media outlets. "),
                    getMemberIdSet("6231", "5213", "4213", "3213", "5335", "6136",
                        "7", "8", "9")),
            new Group(new GroupName("Seventeen"), new GroupDescription("Seventeen (Korean: <KOREAN>), "
                    + "also stylized as SEVENTEEN or SVT, is a South Korean boy group formed by Pledis "
                    + "Entertainment in 2015. The group consists of 13 members divided into three "
                    + "sub-units, each with a different area of specialization: a 'Hip-Hop Unit', 'Vocal "
                    + "Unit', and 'Performance Unit'."),
                    getMemberIdSet("901", "902", "903", "904", "905", "906", "907", "908",
                            "909", "9010", "9011", "9012", "9013"))
        };
    }

    public static ReadOnlyGroupBook getSampleGroupBook() {
        GroupBook sampleGb = new GroupBook();
        for (Group sampleGroup : getSampleGroups()) {
            sampleGb.addGroup(sampleGroup);
        }
        return sampleGb;
    }

    /**
     * Returns a contactId set containing the list of strings given.
     */
    public static Set<MemberId> getMemberIdSet(String... strings) {
        return Arrays.stream(strings)
                .map(s -> new MemberId(s))
                .collect(Collectors.toSet());
    }

    public static Tag[] getSampleTags() {
        return new Tag[] { };
    }

    public static ReadOnlyTagBook getSampleTagBook() {
        TagBook sampleTagBook = new TagBook();
        for (Tag sampleTag : getSampleTags()) {
            sampleTagBook.addTag(sampleTag);
        }
        return sampleTagBook;
    }
}
