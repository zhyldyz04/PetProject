package entities;


import lombok.Data;

@Data
public class RequestBody {

    private String email;
    private  String password;

    /*
    "company_name": "string",
  "seller_name": "string",
  "email": "string",
  "phone_number": "string",
  "address": "string"
     */
    //creating seller body
    private String company_name;
    private String  seller_name;
    private String phone_number;
    private  String address;

    /*
    "category_title": "string",
  "category_description": "string",
  "flag": true
     */
    //creating category
    private String category_title;
    private String category_description;
    private boolean flag;


    //creating tag
    private  String name_tag;
    private String description;



}
