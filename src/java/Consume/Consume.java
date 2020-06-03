/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consume;

import Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class Consume {
    
    private final String USER_AGENT = "Mozilla/5.0";
     
    
    public static void main(String[] args) {
        
        Consume con = new Consume();
        
        String json = con.sendGet("http://localhost:8080/TWebServiceRest2/webresources/WebSR/Usuario/get");
        
        System.out.println("");
        System.out.println(json);
        System.out.println("");
        
//       ArrayList<User> user = new ArrayList<>();
//
//        Type userType = new TypeToken<ArrayList<User>>(){}.getType();
//        
//        Gson g = new Gson();
//        
//        user = g.fromJson(json, userType);
//        
//        for (User user1 : user) {
//            
//            System.out.println(user1.getNome());
//            
//        }
        
    }

    private String sendGet(String url) {
       
        try {
            
            URL obj = new URL(url);
            
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            
            con.setRequestMethod("GET");
            
            con.setRequestProperty("User-Agent", USER_AGENT);
            
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : "+ url);
            System.out.println("Response Code: "+responseCode);
            
            BufferedReader in = new BufferedReader(
                 
                 new InputStreamReader(con.getInputStream())  
            
            );
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while((inputLine = in.readLine()) != null){
                
                response.append(inputLine);
                
            }
            
            in.close();
            
 
            return response.toString();
             
        } catch (MalformedURLException | ProtocolException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    
}
