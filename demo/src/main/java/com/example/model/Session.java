package com.example.model;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private Integer id;
    private Time tempJeu;
    private Time dateCreation;
    private Time dateDerniereConnexion; 
    private String nom_donner_tamagotchi; 
    private Integer codePin; 


    public Session(Integer id,Time tempJeu,Time dateCreation,Time dateDerniereConnexion,Integer codePin,String nomTama){
        this.id = id;
        this.tempJeu = tempJeu;
        this.dateCreation = dateCreation;
        this.dateDerniereConnexion = dateDerniereConnexion;
        this.codePin = codePin;
        this.nom_donner_tamagotchi = nomTama;
    }
    
    
    public static Session init_new_session(String nom_tama,Integer codePin){
        
        Date currentDate = new Date();
        
        Time currentTime = new Time(currentDate.getTime());
        Integer new_id = JsonDatabase.getFreeSessionID();
        Time new_dateCreation = currentTime;
        Time new_dateDerniereConnexion = currentTime;

        long differenceMillis = new_dateDerniereConnexion.getTime() - new_dateCreation.getTime();
        Time new_tempJeu = new Time(differenceMillis);

        
        Session new_session = new Session(new_id,new_tempJeu,new_dateCreation,new_dateDerniereConnexion,codePin,nom_tama);
        return new_session;
    }

    public Map<String,String>  getAttributes(){

        Map<String,String> attr = new HashMap<>();

        attr.put(AttributeConstant.ID, String.valueOf(id));
        attr.put(AttributeConstant.TIME, tempJeu.toString());
        attr.put(AttributeConstant.CREATION_DATE, dateCreation.toString());
        attr.put(AttributeConstant.LAST_CONNECTION, dateDerniereConnexion.toString());
        attr.put(AttributeConstant.TAMAGOTCHI_NAME, this.nom_donner_tamagotchi);
        attr.put(AttributeConstant.PIN, String.valueOf(codePin));

        return attr;
    }


    public Integer getId() {
        return id;
    }


    public Time getTempJeu() {
        return tempJeu;
    }


    public Time getDateCreation() {
        return dateCreation;
    }


    public Time getDateDerniereConnexion() {
        return dateDerniereConnexion;
    }


    public String getNom_donner_tamagotchi() {
        return nom_donner_tamagotchi;
    }


    public int getCodePin() {
        return codePin;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setTempJeu(Time tempJeu) {
        this.tempJeu = tempJeu;
    }


    public void setDateCreation(Time dateCreation) {
        this.dateCreation = dateCreation;
    }


    public void setDateDerniereConnexion(Time dateDerniereConnexion) {
        this.dateDerniereConnexion = dateDerniereConnexion;
    }


    public void setNom_donner_tamagotchi(String nom_donner_tamagotchi) {
        this.nom_donner_tamagotchi = nom_donner_tamagotchi;
    }


    public void setCodePin(int codePin) {
        this.codePin = codePin;
    }

    
}
