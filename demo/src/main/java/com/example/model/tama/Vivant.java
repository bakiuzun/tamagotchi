package com.example.model.tama;

import org.json.simple.JSONObject;

import com.example.model.utils.ActionConstant;
import com.example.model.utils.AttributeConstant;

public abstract class Vivant extends Tamagotchi {

    protected Integer hunger;
    protected Integer tiredness;
    protected Integer hygiene;
    protected Integer mood;
    protected Integer weight;

    protected int delta_hunger;
    protected int delta_tiredness;
    protected int delta_hygiene;
    protected int delta_mood;
    protected int delta_weight;




    public void init_new_tamagothi(){
        super.init_new_tamagothi();
        loadAction();

        this.hunger = ActionConstant.HUNGER_MAX;
        this.tiredness = ActionConstant.TIREDNESS_MAX;
        this.hygiene = ActionConstant.HYGIENE_MAX;
        this.mood = ActionConstant.MOOD_MAX;

        this.delta_hunger = ActionConstant.BASE_DELTA;
        this.delta_tiredness = ActionConstant.BASE_DELTA;
        this.delta_hygiene = ActionConstant.BASE_DELTA;
        this.delta_mood = ActionConstant.BASE_DELTA;
        this.delta_weight = ActionConstant.BASE_DELTA;
        // le poid et init en fonction du Vivant dans les sous-classes
    }


    public void loadAction(){
        super.loadAction();

        actions.put(AttributeConstant.FEED, this::eating);
        actions.put(AttributeConstant.SLEEP, this::sleeping);
        


        ///
    }

    public void updateState(){

        updateHunger();
        updateWeight();
        updateTiredness();
        updateMood();
        updateHygiene();

        replace_new_attributes_values();
        
        super.updateState();
    }

    private void updateWeight(){

        double hunger_diff_max = (double) this.hunger / ActionConstant.HUNGER_MAX;
        int reduce_weight = 0;

        if (hunger_diff_max < 0.4){reduce_weight = 1; }
        else if (hunger_diff_max < 0.2){reduce_weight = 3; }
        else if (hunger_diff_max < 0.1){reduce_weight = 5; }
        this.weight -= reduce_weight;
    }
    private void updateHunger(){
        this.hunger = Math.max(this.hunger-delta_hunger,0);
    }

    private void updateTiredness(){
        this.tiredness =  Math.max(this.tiredness-delta_tiredness,0);
    }

    private void updateMood(){
        this.mood =  Math.max(this.mood-delta_mood,0);
    }

    private void updateHygiene(){
        this.hygiene =  Math.max(this.hygiene-delta_hygiene,0);
    }


    public void replace_new_attributes_values(){
        attributes.replace(AttributeConstant.HUNGER, String.valueOf(this.hunger));
        attributes.replace(AttributeConstant.TIREDNESS, String.valueOf(this.tiredness));
        attributes.replace(AttributeConstant.HYGIENE, String.valueOf(this.hygiene));
        attributes.replace(AttributeConstant.MOOD, String.valueOf(this.mood));
        attributes.replace(AttributeConstant.WEIGHT, String.valueOf(this.weight));

    }


    public void addAttributes(){
        super.addAttributes();

        attributes.put(AttributeConstant.HUNGER, String.valueOf(this.hunger));
        attributes.put(AttributeConstant.TIREDNESS, String.valueOf(this.tiredness));
        attributes.put(AttributeConstant.HYGIENE, String.valueOf(this.hygiene));
        attributes.put(AttributeConstant.MOOD, String.valueOf(this.mood));
        attributes.put(AttributeConstant.WEIGHT, String.valueOf(this.weight));
    }
    

    public void loadTamaFromDatabase(JSONObject tama){
        super.loadTamaFromDatabase(tama);
        
        this.weight  = Integer.parseInt((String) tama.get(AttributeConstant.WEIGHT));
        this.hygiene = Integer.parseInt((String) tama.get(AttributeConstant.HYGIENE));
        this.tiredness  = Integer.parseInt((String) tama.get(AttributeConstant.TIREDNESS));
        this.hunger = Integer.parseInt((String) tama.get(AttributeConstant.HUNGER));
        this.mood = Integer.parseInt((String) tama.get(AttributeConstant.MOOD));
    }


    public void eating(){
    	double prendreWeight = this.hunger/100.0;
    	if(prendreWeight>0.6) {
    		this.weight += ActionConstant.KILOMAX; 
    	}
    	else if(prendreWeight>0.4){
    		this.weight += ActionConstant.KILOMAX/2; 
    	}
        this.hunger = Math.min(this.hunger + ActionConstant.EATING, ActionConstant.HUNGER_MAX);
    	attributes.replace(AttributeConstant.WEIGHT, String.valueOf(this.weight));
    	attributes.replace(AttributeConstant.HUNGER, String.valueOf(this.hunger));

    }

    public void washing(){
        this.hygiene = Math.min(this.hygiene + ActionConstant.WASHING_HYGIENE, ActionConstant.HYGIENE_MAX);
    	attributes.replace(AttributeConstant.HYGIENE, String.valueOf(this.hygiene));
    }

    public void playing(){
        this.mood = Math.min(this.mood + ActionConstant.PLAYING, ActionConstant.MOOD_MAX);
    	attributes.replace(AttributeConstant.MOOD, String.valueOf(this.mood));
    }
    
    public void sleeping() {
        this.tiredness = Math.min(this.tiredness+ ActionConstant.SLEEPING, ActionConstant.TIREDNESS_MAX);
    	attributes.replace(AttributeConstant.TIREDNESS, String.valueOf(this.tiredness));
    	
    }
    
    public void doingSport(){
    	this.hunger += ActionConstant.FAIREDUSPORTFAIM;
    	attributes.replace(AttributeConstant.HUNGER, String.valueOf(this.hunger));
    }
    
    public void usingToilet(){
        this.hygiene = Math.max(this.hygiene + ActionConstant.USING_TOILET_HYGIENE, 0);
    	attributes.replace(AttributeConstant.HYGIENE, String.valueOf(this.hygiene));
    }


    public Integer getHunger() {
        return hunger;
    }


    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }


    public Integer getTiredness() {
        return tiredness;
    }


    public void setTiredness(Integer tiredness) {
        this.tiredness = tiredness;
    }


    public Integer getHygiene() {
        return hygiene;
    }


    public void setHygiene(Integer hygiene) {
        this.hygiene = hygiene;
    }


    public Integer getMood() {
        return mood;
    }


    public void setMood(Integer mood) {
        this.mood = mood;
    }


    public Integer getWeight() {
        return weight;
    }


    public void setWeight(Integer weight) {
        this.weight = weight;
    }


}