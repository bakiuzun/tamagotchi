package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Lieu {
    private List<NomLieu> voisins;
    private NomLieu nomLieu;

    public Lieu(NomLieu nomLieu) {
        this.nomLieu = nomLieu;
        this.voisins = new ArrayList<>();
        if (this.nomLieu == NomLieu.MAISON){
            this.voisins.add(NomLieu.DEHORS);
        } else if (this.nomLieu == NomLieu.DEHORS){
            this.voisins.add(NomLieu.MAISON);
        }
    }

    public NomLieu getNomLieu() {
        return this.nomLieu;
    }

    public void addVoisin(NomLieu voisin) {
        voisins.add(voisin);
    }

    public List<NomLieu> getVoisins() {
        return voisins;
    }

    // Method to move to a neighboring place
    public void moveTo(NomLieu destination) {
        if (voisins.contains(destination)) {
            System.out.println("Moving from " + this.nomLieu + " to " + destination);
            // Implement any logic related to moving here
        } else {
            System.out.println("Cannot move to " + destination + " from " + this.nomLieu);
        }
    }

    // Method to set predefined neighbors based on the current place
    public void setPredefinedNeighbors(List<NomLieu> neighbors) {
        voisins.addAll(neighbors);
    }
}
