/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import Model.User;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Samuel
 */
@Path("WebSR")
public class WSRResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WSRResource
     */
    public WSRResource() {
    }

    /**
     * Retrieves representation of an instance of WS.WSRResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/text")
    public String getJson() {
        //TODO return proper representation object
       return "chero d pneu q mado";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/get")
    public String getUsuario(){
        
        User user = new User();
        
        user.setEmail("pppp@gmail.com");
        user.setLogin("fulano");
        user.setSenha("123");
        user.setPerfil("ADM");
                
        Gson json = new Gson();
        
        return json.toJson(user);
    }
    
    /**
     * PUT method for updating or creating an instance of WSRResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
