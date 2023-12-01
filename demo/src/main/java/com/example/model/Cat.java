package com.example.model;

import com.example.controller.ConnectedController.AttributeEntry;

public class Cat extends Vivant {

    public Cat(){

        super.loadAction();

    }

    public void init_new_tamagothi(){

        this.typeTamagotchi = TypeTamagotchi.CAT;
        this.weight = ActionConstant.CAT_WEIGHT; // Attribut Vivant
        
        super.init_new_tamagothi();
        super.addAttributes();
    }


    public void updateState(){

        System.out.println("CAT UDPATE HOLD ON ");
        super.updateState();
    }
    
}
