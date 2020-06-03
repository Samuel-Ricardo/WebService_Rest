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
import java.io.DataOutputStream;
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
        
        
       /////////////////////// Select All ///////////////////////////////
        Consume con = new Consume();
        
        String result = con.sendGet("http://localhost:8080/TWebServiceRest2/webresources/WebSR/Usuario/get","GET");
        
        System.out.println("");
        System.out.println(result);
        System.out.println("");
        
       ArrayList<User> user = new ArrayList<>();

        Type userType = new TypeToken<ArrayList<User>>(){}.getType();
        
        Gson g = new Gson();
        
        user = g.fromJson(result, userType);
        
        for (User user1 : user) {
            
            System.out.println(user1.getNome());
            
        }
        
       //////////////////////////////////////////////////////////////
       //                                                          //
       //                                                          //
       //                                                          //
       ////////////////////// Insert ////////////////////////////////
     
        System.out.println("");
        System.out.println("----------------------------------------------------");
        System.out.println("");
        
        User u = new User();
        
        u.setNome("Fulano");
        u.setSenha("321");
        u.setSexo("F");
        
        String json = g.toJson(u);
  
        String url = "http://localhost:8080/TWebServiceRest2/webresources/WebSR/Usuario/inserir";
        
        result = con.sendPost(url, json, "POST");
        System.out.println(result);
        
        System.out.println("");
        System.out.println("----------------------------------------------------");
        System.out.println("");
        
        /////////////////////////////////////////////////////////////
    }

    private String sendGet(String url, String method) {
       
        try {
            
            URL link = new URL(url);
            
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            
            connection.setRequestMethod(method);
            
            connection.setRequestProperty("User-Agent", USER_AGENT);
            
            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : "+ url);
            System.out.println("Response Code: "+responseCode);
            
            BufferedReader input = new BufferedReader(
                 
                 new InputStreamReader(connection.getInputStream())  
            
            );
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while((inputLine = input.readLine()) != null){
                
                response.append(inputLine);
                
            }
            
            input.close();
            
 
            return response.toString();
             
        } catch (MalformedURLException | ProtocolException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    
    public String sendPost(String url, String urlParameters, String method){
        
        try {
            
            URL obj = new URL(url);
            
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            
            con.setDoOutput(true);
            DataOutputStream writer = new DataOutputStream(con.getOutputStream());
            writer.writeBytes(urlParameters);
            writer.flush();
            writer.close();
            
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : "+ url);
            System.out.println("Post parameters: "+ urlParameters);
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
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
