package techSolutions.parser;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import techSolutions.dto.EmployeeDTO;
import techSolutions.utils.Constants;
import techSolutions.utils.DateTimeUtils;
import techSolutions.utils.EmpConstants;
import techSolutions.utils.ParserUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

@Data
public class EmpParser implements IEmpParser {

    private String userId;
    private EmployeeDTO employeeDTO;

    @Override
    public void parse() throws IOException {
        employeeDTO = new EmployeeDTO();
        Document document = Jsoup.connect(String.format(EmpConstants.EMPLOYEE_PAGE_URL, userId)).get();
        Element contactsTable = document.getElementById(EmpConstants.CONTACS_TABLE_ID);
        Element personalInfoTable = document.getElementById(EmpConstants.PERSONAL_INFO_TABLE_ID);
        Element positionInfoTable = document.getElementById(EmpConstants.POSITION_INFO_TABLE_ID);
        Element subordinatesInfoTable = document.getElementById(EmpConstants.SUBORDINATES_INFO_TABLE_ID);

        if (Objects.nonNull(contactsTable)) {
            Element imageInfo = ParserUtils.getElementByIndex(contactsTable.getElementsByTag(Constants.TBODY_TAG_NAME), 1);
            Element contactsInfo = ParserUtils.getElementByIndex(contactsTable.getElementsByTag(Constants.TBODY_TAG_NAME), 2);
            employeeDTO.setImageSrc(Objects.nonNull(imageInfo) ? ParserUtils.getAttrValue(imageInfo.getElementsByTag(Constants.IMAGE_TAG_NAME).first(), Constants.SRC_ATTR_NAME) : null);

            if (Objects.nonNull(contactsInfo)) {
                contactsInfo.children().stream().forEach(ch -> {
                    Element keyElement = ParserUtils.getElementByIndex(ch.children(), 0);
                    Element valueElement = ParserUtils.getElementByIndex(ch.children(), 1);
                    if (Objects.nonNull(keyElement)) {
                        String key = keyElement.text();
                        String value = Objects.nonNull(valueElement) ? valueElement.text() : null;
                        switch (key) {
                            case EmpConstants.MOBILE:
                                employeeDTO.setMsisdn(value);
                                break;
                            case EmpConstants.PSTN:
                                employeeDTO.setLandLine(value);
                                break;
                            case EmpConstants.EMAIL:
                                employeeDTO.setEmail(value);
                                break;
                            case EmpConstants.FAX:
                                employeeDTO.setFax(value);
                                break;
                            case EmpConstants.ACCOUNT_NAME:
                                employeeDTO.setUsername(value);
                                break;
                            case EmpConstants.BIRTHDAY:
                                employeeDTO.setBirthDay(value);
                                break;
                            case EmpConstants.HIRE_DATE:
                                employeeDTO.setHireDate(DateTimeUtils.toLocalDateTime(value));
                                break;
                            case EmpConstants.CAR_NUMBER:
                                employeeDTO.setCartNumber(value);
                                break;
                        }
                    }
                });
            }
        }

        if (Objects.nonNull(personalInfoTable)) {
            Element personalInfo = ParserUtils.getElementByIndex(personalInfoTable.getElementsByTag(Constants.TBODY_TAG_NAME), 2);
            if (Objects.nonNull(personalInfo)) {
                personalInfo.children().stream().forEach(ch -> {
                    Element keyElement = ParserUtils.getElementByIndex(ch.children(), 0);
                    Element valueElement = ParserUtils.getElementByIndex(ch.children(), 1);
                    if (Objects.nonNull(keyElement)) {
                        String key = keyElement.text();
                        String value = Objects.nonNull(valueElement) ? valueElement.text() : null;
                        if (key.contains(Constants.SLASH)) {
                            StringTokenizer tokenizer = new StringTokenizer(key, Constants.SLASH);
                            if (tokenizer.hasMoreTokens()) {
                                employeeDTO.setNameEn(tokenizer.nextToken());
                            }
                            if (tokenizer.hasMoreTokens()) {
                                employeeDTO.setNameRu(tokenizer.nextToken());
                            }
                        }
                        switch (key) {
                            case EmpConstants.EMPLOYEE_CODE:
                                employeeDTO.setCode(value);
                                break;
                            case EmpConstants.POSITION:
                                employeeDTO.setPosition(value);
                                break;
                            case EmpConstants.DEPARTMENT:
                                employeeDTO.setDepartmentFull(value);
                                break;
                            case EmpConstants.LOCATION:
                                employeeDTO.setLocation(value);
                                break;
                            case EmpConstants.JOB_DUTIES:
                                break;
                        }
                    }
                });
            }
        }

        if (Objects.nonNull(positionInfoTable)) {
            Element reportsToElement = positionInfoTable.getElementsByTag(Constants.TBODY_TAG_NAME).first();
            if (Objects.nonNull(reportsToElement)) {
                Element a = reportsToElement.getElementsByTag(Constants.AHREF_TAG_NAME).first();
                String href = ParserUtils.getAttrValue(a, Constants.HREF_ATTR_NAME);
                if (Objects.nonNull(href)) {
                    employeeDTO.setReportsTo(href.substring(href.lastIndexOf(Constants.COLON) + 1));
                }
            }
        }

        if (Objects.nonNull(subordinatesInfoTable)) {
            Element subordinatesInfoElement = ParserUtils.getElementByIndex(subordinatesInfoTable.getElementsByTag(Constants.TBODY_TAG_NAME), 1);
            if (Objects.nonNull(subordinatesInfoElement)) {
                Element subordinatesElement = subordinatesInfoElement.children().first();
                if (Objects.nonNull(subordinatesElement)) {
                    List<String> subordinatesList = new ArrayList<>();
                    subordinatesElement.children().forEach(ch -> {
                        String href = ParserUtils.getAttrValue(ch.getElementsByTag(Constants.AHREF_TAG_NAME).first(), Constants.HREF_ATTR_NAME);
                        if (Objects.nonNull(href)) {
                            subordinatesList.add(href.substring(href.lastIndexOf(Constants.COLON) + 1));
                        }
                    });
                    employeeDTO.setSubordinates(subordinatesList);
                }
            }
        }
    }

    /**
     * Method to parse employee and his/her all subordinates
     * Works recursively
     *
     * @param parser reference for another/same instance of {@link EmpParser}
     * @param id     of employee to be parsed
     */
    public static void getEmployeeInfoRecursively(EmpParser parser, String id) {
        try {
            parser.setUserId(id);
            parser.parse();
            EmployeeDTO employeeDTO = parser.getEmployeeDTO();
            System.out.println(employeeDTO);
            if (Objects.nonNull(employeeDTO.getSubordinates())) {
                employeeDTO.getSubordinates().forEach(s -> {
                    getEmployeeInfoRecursively(parser, s);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
