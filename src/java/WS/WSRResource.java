/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import DAO.UserDAO;
import Model.User;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Samuel
 */
@Path("WebSR")
public class WSRResource {

    private UserDAO dao = new UserDAO();
    private Gson json = new Gson();
    
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/get/{login}")
    public String getUsuario(@PathParam("login") String login) {
        //TODO return proper representation object
        
        User user = new User();
        
        user.setNome(login);
        
        List<User> users = dao.searchByName(login);
                
       return json.toJson(users);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/get")
    public String getListUsuario(){
        
        
        
            List<User> list ;
        
            list = dao.selectAll();
        
        
        
        
        return json.toJson(list);
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
