package techSolutions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LostMainPageDTO {
    private String link;
    private String post;
    private String title;
    private String imageSrc;

}