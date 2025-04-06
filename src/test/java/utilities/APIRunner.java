package utilities;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;
import org.checkerframework.checker.units.qual.C;

import java.util.Map;

@Data
public class APIRunner {
    @Getter
    private static CustomResponse customResponse;

    public static void runGET(String path, int id){
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashwiseApiUrl") + path + id;
        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        }catch (JsonProcessingException e){
            System.out.println("this is a list response");
        }
    }

    //get api with params
    public static void runGET(String path, Map<String, Object> params){
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        }catch (JsonProcessingException e){
            System.out.println("this is a single response");
        }
    }

    public static void runPOST(String path, RequestBody requestBody){
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).body(requestBody).post(url);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("status code: " + response.statusCode());
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        }catch (JsonProcessingException e){
            System.out.println("this is a single response");
        }
    }


    //post api with params
    public static void runPOST(String path, Map<String, Object> params){
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response =RestAssured.given().auth().oauth2(token).params(params).post(url);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("status code: " + response.statusCode());
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        }catch (JsonProcessingException e){
            System.out.println("this is a single response");

    }}

    public static void runDELETE(String path){
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response =RestAssured.given().auth().oauth2(token).delete(url);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("status code: " + response.statusCode());
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        }catch (JsonProcessingException e){
            System.out.println("this is a single response");

        }
    }

    public static void runPUT(String path, RequestBody requestBody, int id){
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashwiseApiUrl") + path + id;
        Response response =RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).body(requestBody).put(url);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("status code: " + response.statusCode());
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        }catch (JsonProcessingException e){
            System.out.println("this is a single response");

        }
    }



}
