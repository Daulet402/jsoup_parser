package techSolutions.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.StringTokenizer;


public class GroupParser implements VkParser {

    private String groupId;

    public void parse(Element wallPost) {
        Element e = wallPost.getElementById("post-29258893_205241");
        Element postInfo = e.getElementsByAttributeValue("class", "post_info").first();
        Element textElement = postInfo.getElementsByAttributeValue("class", "wall_post_text").first();
        Element imageElement = postInfo.getElementsByAttributeValue("class", "page_post_sized_thumbs  clear_fix").first();
        Element audioElement = postInfo.getElementsByAttributeValue("class", "wall_audio_rows _wall_audio_rows").first();
        Element videoElement = postInfo.getElementsByAttributeValue("class", "page_post_sized_thumbs  clear_fix").first();
        String imageSrc = retrieveImageSrc(imageElement.getElementsByTag("a").attr("onclick"));
        audioElement.child(0).getElementsByAttributeValue("class", "audio_row__performer_title");
        String songName = audioElement.child(4).getElementsByAttributeValue("class", "audio_row__title _audio_row__title").first().text();
        String songPerformerName = audioElement.child(4).getElementsByTag("a").text();
        System.out.println(textElement + " " + imageElement + " " + audioElement + " " + videoElement);


    }

    public String retrieveImageSrc(String str) {
        String baseUrl = str.substring(str.indexOf("http"), str.indexOf(",\"x_\"")).replace("\"", "");
        StringTokenizer tokenizer = new StringTokenizer(str.substring(str.indexOf("[") + 1, str.indexOf("]")), ",");
        String uri = tokenizer.nextToken().replace("\"", "");
        return new StringBuilder(baseUrl).append(uri).append(".jpg").toString();
    }

    @Override
    public void parse() throws IOException {
        String url = "https://vk.com/gooddaday";
        Document document = Jsoup.connect(url).get();
        Element wallPost = document.getElementById("page_wall_posts");
        int c = 0;


        parse(wallPost);
        //wall_more_link

        // TODO: 8/29/17 How to get all elements
        /*for (Element e : wallPost.getAllElements()) {
            // TODO: 8/29/17 filter by other way
            // Text of post e.getElementsByAttributeValue("class","wall_text").get(0).getElementById("wpt-30328106_164123").getElementsByAttributeValue("class","wall_post_text").text()
            // Photo of the post e.getElementsByAttributeValue("class","wall_text").get(0).getElementById("wpt-30328106_164123").getElementsByAttributeValue("class","page_post_sized_thumbs  clear_fix").get(0).getElementsByTag("a").get(0).attr("onclick")

            if (ParserUtils.getAttrValue(e, "id").contains("post")) {
                System.out.println(ParserUtils.getAttrValue(e, "id"));
                //String imageShowFunction = e.getElementsByAttributeValue("class", "wall_text").get(0).getElementById("wpt-30328106_164123").getElementsByAttributeValue("class", "page_post_sized_thumbs  clear_fix").get(0).getElementsByTag("a").get(0).attr("onclick");
            }
        }*/
    }
}