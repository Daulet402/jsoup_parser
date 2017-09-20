package techSolutions.utils;

import org.jsoup.nodes.Element;

import java.util.Objects;

public class ParserUtils {

    public static String getAttrValue(Element element, String attrKey) {
        return Objects.nonNull(element) ? element.attr(attrKey) : null;
    }

    public static Element getElementByTags(Element element, String... tags) {
        Element el = element;
        for (String tag : tags) {
            if (Objects.nonNull(el)) {
                el = el.getElementsByTag(tag).first();
            }
        }
        return el;
    }

    public static String getText(Element element) {
        return Objects.nonNull(element) ? element.text() : null;
    }
}