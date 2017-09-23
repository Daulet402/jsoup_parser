package techSolutions.app;

import techSolutions.dto.EmployeeDTO;
import techSolutions.parser.EmpParser;

import java.io.IOException;

public class ParserApp {

    public static void main(String[] args) {
        EmpParser parser = new EmpParser();
        try {
            parser.setUserId("16015");
            parser.parse();
            EmployeeDTO employeeDTO = parser.getEmployeeDTO();
            System.out.println(employeeDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
