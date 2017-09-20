package techSolutions.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import techSolutions.dto.LostMainPageDTO;
import techSolutions.utils.Constants;
import techSolutions.utils.ParserUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LosstParser implements IParser {

    private static final String URL = "https://losst.ru/";
    private static final String LATEST_POSTS_ATTR_NAME = "latest-posts";
    private static final String POST_DATA_CONTAINER_ATTR_NAME = "post-data-container";
    private static final String POST_IMAGE_ATTR_NAME = "post-img";
    private static final String POST_EXCERT_ATTR_NAME = "post-excerpt";

    @Override
    public void parse() throws IOException {
        Document document = Jsoup.connect(URL).get();
        Element latestPostsElement = document.getElementById(LATEST_POSTS_ATTR_NAME);
        List<LostMainPageDTO> lostMainPageDTOS = new ArrayList<>();
        if (Objects.nonNull(latestPostsElement)) {
            Elements articles = latestPostsElement.getElementsByTag(Constants.ARTICLE_TAG_NAME);
            if (Objects.nonNull(articles)) {
                articles.forEach(a -> {
                    Element postDataElement = a.getElementsByAttributeValue(Constants.CLASS_ATTR_NAME, POST_DATA_CONTAINER_ATTR_NAME).first();
                    Element postImage = a.getElementsByAttributeValue(Constants.CLASS_ATTR_NAME, POST_IMAGE_ATTR_NAME).first();
                    Element imageElement = ParserUtils.getElementByTags(postImage, Constants.AHREF_TAG_NAME, Constants.IMAGE_TAG_NAME);
                    Element ahref = ParserUtils.getElementByTags(postDataElement, Constants.HEADER_TAG_NAME, Constants.H2_TAG_NAME, Constants.AHREF_TAG_NAME);
                    Element postElement = Objects.nonNull(postDataElement) ? postDataElement.getElementsByAttributeValue(Constants.CLASS_ATTR_NAME, POST_EXCERT_ATTR_NAME).first() : null;

                    String title = ParserUtils.getAttrValue(ahref, Constants.TITLE_ATTR_NAME);
                    String link = ParserUtils.getAttrValue(ahref, Constants.HREF_ATTR_NAME);
                    String post = ParserUtils.getText(postElement);
                    String srcSet = ParserUtils.getAttrValue(imageElement, Constants.SRC_SET_ATTR_NAME);

                    lostMainPageDTOS.add(new LostMainPageDTO(link, post, title, srcSet));
                });
            }
            lostMainPageDTOS.forEach(System.out::println);
        }
    }
}