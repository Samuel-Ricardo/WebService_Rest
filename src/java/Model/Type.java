/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.TypeDAO;
import DAO.UserDAO;
import java.util.ArrayList;

/**
 *
 * @author Samuel
 */
public class Type {
    
    private Long id;
    private String name;
    private String secondaryColor;
    private String primaryColor;
    private Long importance;
    private User user;
    private boolean vissible;
    public static final int MAX_PRIORITY = 10;
    public static final int HIGH_PRIORITY = 8;
    public static final int MEDIUN_PRIORITY = 5;
    public static final int LOW_PRIORITY = 3;
    public static final int MIN_PRIORITY = 1;

    public Type() {
        
        this.vissible = true; 
        
    }

    
  public static ArrayList<Type> getDefaultTypes() {
      
  
    ArrayList<Type> defaultTypes = new ArrayList<>();
        
        Type urgente = new Type();
        
        urgente.setName("Urgente");
        urgente.setSecondaryColor("#ff7373");
        urgente.setPrimaryColor("#ff0000");
        urgente.setImportance(Type.MAX_PRIORITY);
        urgente.setUser(UserDAO.getUser());
        
        defaultTypes.add(urgente);
        
        Type meta = new Type();
        
        meta.setName("Meta");
        meta.setSecondaryColor("#ff7373");
        meta.setPrimaryColor("#7f7df1");
        meta.setImportance(Type.HIGH_PRIORITY);
        meta.setUser(UserDAO.getUser());
        
        defaultTypes.add(meta);
        
        Type trabalho = new Type();
        
        trabalho.setName("Trabalho");
        trabalho.setSecondaryColor("#9370db");
        trabalho.setPrimaryColor("#8a2be2");
        trabalho.setImportance(Type.MAX_PRIORITY);
        trabalho.setUser(UserDAO.getUser());
        
        defaultTypes.add(trabalho);
        
        Type atividade = new Type();
        
        atividade.setName("Atividade");
        atividade.setSecondaryColor("#a457a4");
        atividade.setPrimaryColor("#8b008b");
        atividade.setImportance(4);
        atividade.setUser(UserDAO.getUser());
        
        defaultTypes.add(atividade);
        
        Type exercicio = new Type();
        
        exercicio.setName("Exercicio");
        exercicio.setSecondaryColor("#7fff7f");
        exercicio.setPrimaryColor("#00ff00");
        exercicio.setImportance(Type.MEDIUN_PRIORITY);
        exercicio.setUser(UserDAO.getUser());
        
        defaultTypes.add(exercicio);
        
        Type evento = new Type();
        
        evento.setName("Evento");
        evento.setSecondaryColor("#e1f481");
        evento.setPrimaryColor("#d4ff00");
        evento.setImportance(Type.MEDIUN_PRIORITY);
        evento.setUser(UserDAO.getUser());
        
        defaultTypes.add(evento);
        
        Type especial = new Type();
        
        especial.setName("Especial");
        especial.setSecondaryColor("#ffe97e");
        especial.setPrimaryColor("#ffd700");
        especial.setImportance(6);
        especial.setUser(UserDAO.getUser());
        
        defaultTypes.add(especial);
        
        Type postIt = new Type();
        
        postIt.setName("Post-It");
        postIt.setSecondaryColor("#ee71ee");
        postIt.setPrimaryColor("#ff00ff");
        postIt.setImportance(Type.MEDIUN_PRIORITY);
        postIt.setUser(UserDAO.getUser());
        
        defaultTypes.add(postIt);
        
        Type banal = new Type();
        
        banal.setName("Banal");
        banal.setSecondaryColor("#5b5ba4");
        banal.setPrimaryColor("#000080");
        banal.setImportance(Type.MIN_PRIORITY);
        banal.setUser(UserDAO.getUser());
        
        defaultTypes.add(banal);
        
        Type escola = new Type();
        
        escola.setName("Escola");
        escola.setSecondaryColor("#aef3f4");
        escola.setPrimaryColor("#00faff");
        escola.setImportance(Type.HIGH_PRIORITY);
        escola.setUser(UserDAO.getUser());
    
        defaultTypes.add(escola);
        
        System.out.println(defaultTypes.get(2)+" nome");
        
        TypeDAO dao = new TypeDAO();
      
        if(dao.exist(defaultTypes).get(0) == false){
             dao.insertAll(defaultTypes);
        }
        return defaultTypes;   
  }
    public boolean isVissible() {
        return vissible;
    }

    public void setVissible(boolean vissible) {
        this.vissible = vissible;
    }
    
    
    
    public Long getImportance() {
        return importance;
    }

    public void setImportance(Long importance) {
        this.importance = importance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setImportance(int importance) {
        this.importance = new Long(importance);
    }
    
    public void setId(int id) {
        this.id = new Long(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }
    
    public void setSecondaryColor(String secundaryColor){
        
        this.secondaryColor = secundaryColor;
        
    }
    
    public void setSecondaryColorByStyle(String secondaryColor) {
        
        if(secondaryColor != null){
        String hexadecimal = secondaryColor.substring(secondaryColor.lastIndexOf("#"));
        
        this.secondaryColor = hexadecimal.replace(";", "");
        }else{
            this.secondaryColor = "";
        }
        
    }

    public String getPrimaryColor() {
        return primaryColor;
    }


    public void setPrimaryColor(String primaryColor){
        
        this.primaryColor = primaryColor;
        
    }
    
    public void setPrimaryColorByStyle(String primaryColor) {
        
          if(primaryColor != null){
              
         String hexadecimal = primaryColor.substring(primaryColor.lastIndexOf("#"));
        
        this.primaryColor = hexadecimal.replace(";", "");
        }else{
            this.primaryColor = "";
        }
    }
    
    @Override
    public String toString(){
        
        return name;
    } 
    
}
