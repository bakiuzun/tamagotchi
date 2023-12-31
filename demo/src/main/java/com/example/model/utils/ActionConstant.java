package com.example.model.utils;

public class ActionConstant {
    //Stats max
    public final static int KILOMAX = 2;
    public final static int LIFE_MAX = 100;
    public final static int WEIGHT_MAX = 100;
    public final static int HUNGER_MAX = 100;
    public final static int HYGIENE_MAX = 100;
    public final static int MOOD_MAX = 100;
    public final static int TIREDNESS_MAX = 100;
    public final static int BATTERY_MAX = 100;
    public final static int OIL_MAX = 100;
    public final static int TEMPERATURE_MAX = 100;
    public final static int RUST_MAX = 100;

    //Tamagotchi weight
    public final static int CAT_WEIGHT = 20;
    public final static int CAT_WEIGHT_MAX = 40;
    public final static int DOG_WEIGHT = 25;
    public final static int DOG_WEIGHT_MAX = 50;
    public final static int RABBIT_WEIGHT = 15;
    public final static int RABBIT_WEIGHT_MAX = 30;
    public final static int TURTLE_WEIGHT = 30;
    public final static int TURTLE_WEIGHT_MAX = 60;


    public final static String action_manger = "Manger";

    //Actions
    public final static int EATING = 10;
    public final static int LOSING_WEIGHT_EAT = 5;
    public final static int WASHING_HYGIENE = 10;
    public final static int FAIREDUSPORTFAIM = -5;
    public final static int FAIREDUSPORTHUMEUR = 5;
    public final static int DOING_SPORT_HUNGER = -7;
    public final static int DOING_SPORT_WEIGHT = -2;
    public final static int DOING_SPORT_MOOD = 5;
    public final static int DOING_SPORT_TIREDNESS = -7;
    public final static int GAINING_WEIGHT = 10;
    public final static int SLEEPING = 10;
    public final static int USING_TOILET_MOOD = 5;
    public final static int USING_TOILET_HYGIENE = -5;
    public final static int PLAYING = 10;
    public final static int BATTERING = 10;
    public final static int BATTERING_TEMPERATURE = -5;
    public final static int OILING = 10;
    public final static int OILING_RUST = -2;
    public final static int COOLING = 10;
    public final static int CLEANING = 10;

    //Decaying stats overtime
    public final static int BASE_DELTA = 2;
    public final static int UPDATE_STATE_TIME = 60*10;
    public final static int DELTA_TIME = 9000;

    public final static int DELTA_HUNGER_TURTLE = 4;
    public final static int DELTA_HYGIENE_TURTLE = 5;
    public final static int DELTA_MOOD_TURTLE = 6;
    public final static int DELTA_TIREDNESS_TURTLE = 3;
    public final static int DELTA_WEIGHT_TURTLE = 1;
        
    public final static int DELTA_HUNGER_CAT = 4;
    public final static int DELTA_HYGIENE_CAT = 3;
    public final static int DELTA_MOOD_CAT = 4;
    public final static int DELTA_TIREDNESS_CAT = 4;
    public final static int DELTA_WEIGHT_CAT = 3;
        
    public final static int DELTA_HUNGER_DOG = 6;
    public final static int DELTA_HYGIENE_DOG = 3;
    public final static int DELTA_MOOD_DOG = 3;
    public final static int DELTA_TIREDNESS_DOG = 3;
    public final static int DELTA_WEIGHT_DOG = 2;
        
    public final static int DELTA_HUNGER_RABBIT = 3;
    public final static int DELTA_HYGIENE_RABBIT = 4;
    public final static int DELTA_MOOD_RABBIT = 5;
    public final static int DELTA_TIREDNESS_RABBIT = 3;
    public final static int DELTA_WEIGHT_RABBIT = 3;

    public final static int DELTA_BATTERY_ROBOT = 4;
    public final static int DELTA_OIL_ROBOT = 2;
    public final static int DELTA_TEMPERATURE_ROBOT = 1;
    public final static int DELTA_RUST_ROBOT = 4;

    public final static int DELTA_BATTERY_CAR = 2;
    public final static int DELTA_OIL_CAR = 4;
    public final static int DELTA_TEMPERATURE_CAR = 3;
    public final static int DELTA_RUST_CAR = 2;

}
