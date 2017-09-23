package techSolutions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmployeeDTO {
    private String fax;
    private String code;
    private String email;
    private String msisdn;
    private String nameEn;
    private String nameRu;
    private String landLine;
    private String username;
    private String birthDay;
    private String imageSrc;
    private String location;
    private String position;
    private String reportsTo;
    private String department;
    private String cartNumber;
    private LocalDate hireDate;
    private String departmentFull;
    private List<String> subordinates;

}
