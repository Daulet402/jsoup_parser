package techSolutions.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public static Element getElementByIndex(Elements elements, int index) {
        return Objects.nonNull(elements) && elements.size() > index ? elements.get(index) : null;
    }

    public static boolean elementsNotEmpty(Elements elements) {
        return Objects.nonNull(elements) && elements.size() > 0;
    }
}
