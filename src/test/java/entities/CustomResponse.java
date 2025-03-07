package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

    //response from creating category
    private  int category_id;
    private String email;
    private int seller_id;
    private String category_title;
    private String category_description;
    List <CustomResponse> responses;


}
