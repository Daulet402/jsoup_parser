package techSolutions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import techSolutions.utils.ParserUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * daulet  8/27/17
 */
public class ParserApp {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://losst.ru/").get();
        System.out.println(document.title());
        Element latestPostsElement = document.getElementById("latest-posts");

        if (Objects.nonNull(latestPostsElement)) {
            Elements articles = latestPostsElement.getElementsByTag("article");
            articles.forEach(a -> {
                Element postDataElement = a.getElementsByAttributeValue("class", "post-data-container").first();
                Element postImage = a.getElementsByAttributeValue("class", "post-img").first();
                Element imageElement = ParserUtils.getElementByTags(postImage, "a", "img");
                Element ahref = ParserUtils.getElementByTags(postDataElement, "header", "h2", "a");
                Element postElement = Objects.nonNull(postDataElement) ? postDataElement.getElementsByAttributeValue("class", "post-excerpt").first() : null;

                String title = ParserUtils.getAttrValue(ahref, "title");
                String post = ParserUtils.getText(postElement);
                String srcSet = ParserUtils.getAttrValue(imageElement, "srcset");

            });

        }
    }
}