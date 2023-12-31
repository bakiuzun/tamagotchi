package com.example.model.tama;



import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;


import com.example.model.Lieu;

import com.example.model.Session;
import com.example.model.TypeTamagotchi;

import com.example.model.utils.ActionConstant;
import com.example.model.utils.AttributeConstant;
import com.example.model.utils.Utility;


public abstract class Tamagotchi {


    protected Map<String,String> attributes = new HashMap<>();
    protected Map<String,Runnable> actions = new HashMap<>();
    
    protected String currentAction;
    protected int life;

    private Session mySession;
    protected TypeTamagotchi typeTamagotchi;
    protected Lieu currentPlace;
    
    protected int reduceLifeBy = 0;


    public void initNewTamagotchi(){

        this.life = ActionConstant.LIFE_MAX;
        this.currentAction = "Pas d'action en cours";
        // the sessions is affected using the setSessions this is why we don't find it here
    }

    public void updateState(){
        // NOTHING
        this.life = Math.min(Math.max(this.life += reduceLifeBy,0),100); // can change for each update
        reduceLifeBy = 0;
        replace_attr();
    }

    public void loadAction(){
        // NOTHING
      
    }



    public void clearAction(){this.actions.clear();}
    
    public void addAttributes(){
        this.attributes.put(AttributeConstant.LIFE, String.valueOf(this.life));
        this.attributes.put(AttributeConstant.ONGOING_ACTION, currentAction);
        this.attributes.put(AttributeConstant.TAMAGOTCHI_TYPE,  typeTamagotchi.name());
        this.attributes.put(AttributeConstant.ACTUAL_LOCATION,  currentPlace.getNomLieu().name());
    }

    private void replace_attr(){
        attributes.replace(AttributeConstant.LIFE, String.valueOf(this.life));
        attributes.replace(AttributeConstant.ONGOING_ACTION, currentAction);
        attributes.replace(AttributeConstant.TAMAGOTCHI_TYPE,  typeTamagotchi.name());
        attributes.replace(AttributeConstant.ACTUAL_LOCATION,  currentPlace.getNomLieu().name());

        getMySession().setLastConnectionDate((LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond()));
        getMySession().getAttributes().replace(AttributeConstant.LAST_CONNECTION, String.valueOf(getMySession().getLastConnectionDate()));

    }

    public void setSession(Session session){
        this.mySession = session;
    }

    public Map<String, Runnable> getActions(){
        return this.actions;
    }


    public Map<String,String> getAttributes(){
        return this.attributes;
    }

    public Session getSession(){
        return mySession;
    }

    public void doAction(String actionName){
        this.actions.get(actionName).run();}

    public TypeTamagotchi getTypeTamagotchi(){
        return typeTamagotchi;
    }
    public int getLife(){
        return this.life;
    }

    public void setTypeTamagotchi(TypeTamagotchi typeTamagotchi){
        this.typeTamagotchi = typeTamagotchi;
    }

    public String toString(){
        StringBuilder ret = new StringBuilder(); // Use StringBuilder for efficient string concatenation

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ret.append(key).append(": ").append(value).append(",").append("\n");
        }
        return ret.toString();
    }



    public void loadTamaFromDatabase(JSONObject tama){
        this.life = Integer.parseInt((String) tama.get(AttributeConstant.LIFE));
        this.currentAction = (String) tama.get(AttributeConstant.ONGOING_ACTION);
        this.currentPlace = new Lieu(Utility.fromStringToNomLieu((String) tama.get(AttributeConstant.ACTUAL_LOCATION)));
        this.typeTamagotchi = Utility.fromStringToTamgotchiType((String) tama.get(AttributeConstant.TAMAGOTCHI_TYPE));
        //for(int i=0;i<(LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond()-Integer.parseInt((String)tama.get(AttributeConstant.LAST_CONNECTION))/3600);i++){updateState();}
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Session getMySession() {
        return mySession;
    }

    public void setMySession(Session mySession) {
        this.mySession = mySession;
    }

    public Lieu getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(Lieu currentPlace) {
        this.currentPlace = currentPlace;
    }

    public HashMap<String,String>  printAttributes(boolean update_life){
        HashMap<String,String>  res = new HashMap<String,String>();
        res.put(AttributeConstant.LIFE, this.life + "%");

        return res;
    }
    public abstract void addNeighbor();

}
