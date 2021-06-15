/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wordmind;

/**
 *
 * @author hugod
 */
public enum Mode {
    
    Normal ("Normal mode"), Endless ("Endless mode"), Survival ("Survival Mode"), MAR ("Mastermind rules"), MOR ("Modes rules");
    
    private String name = "";
    
    Mode(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
}
