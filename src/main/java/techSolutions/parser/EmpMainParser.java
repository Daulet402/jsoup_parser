package techSolutions.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import techSolutions.dto.EmployeeDTO;
import techSolutions.utils.Constants;
import techSolutions.utils.EmpConstants;
import techSolutions.utils.ParserUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpMainParser implements IEmpParser {

    @Override
    public void parse() throws IOException {
        Document document = Jsoup.connect(EmpConstants.MAIN_PAGE_URL).get();
        Element dataPanelElement = document.getElementById(EmpConstants.DATA_PANEL);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        if (Objects.nonNull(dataPanelElement)) {
            dataPanelElement.getElementsByTag(Constants.TABLE_TAG_NAME);
            Element employees = dataPanelElement.getElementsByAttributeValue(Constants.CLASS_ATTR_NAME, EmpConstants.WORKSHEET_DATA).first().getElementsByTag(Constants.TBODY_TAG_NAME).first();
            if (Objects.nonNull(employees)) {
                for (int i = 1; i < employees.children().size(); i++) {
                    Element child = employees.children().get(i);
                    if (ParserUtils.elementsNotEmpty(child.children())) {
                        EmployeeDTO employeeDTO = new EmployeeDTO();
                        child.children().stream().forEach(c -> {
                            switch (c.attr(Constants.HEADERS_ATTR_NAME)) {
                                case EmpConstants.EMPLOYEE_EN:
                                    employeeDTO.setNameEn(c.text());
                                    break;
                                case EmpConstants.EMPLOYEE_RU:
                                    employeeDTO.setNameRu(c.text());
                                    break;
                                case EmpConstants.USERNAME:
                                    employeeDTO.setUsername(c.text());
                                    break;
                                case EmpConstants.EMPL_CODE:
                                    employeeDTO.setCode(c.text());
                                    break;
                                case EmpConstants.MOBILE_MAIN:
                                    employeeDTO.setMsisdn(c.text());
                                    break;
                                case EmpConstants.LANDLINE:
                                    employeeDTO.setLandLine(c.text());
                                    break;
                                case EmpConstants.CODE:
                                    employeeDTO.setDepartment(c.text());
                                    break;
                                case EmpConstants.NAME_EN:
                                    employeeDTO.setPosition(c.text());
                                    break;
                            }
                        });
                        employeeDTOList.add(employeeDTO);
                    }
                }
            }
        }

        employeeDTOList.forEach(System.out::println);
    }
}
