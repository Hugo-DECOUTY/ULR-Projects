/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wordmind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hugod
 */
public class NormalGame extends AbstractGame implements EasyMode, HardMode {
         
    public NormalGame(){
        
    }
    
    /**
     * Method that select a word randomly according to the wordsize chosen by the player.
     */
    public void chooseWordSizeByPlayer(){
        System.out.println("Select a size for the word that you have to find.");
        Scanner sc = new Scanner(System.in);
        int wordSizeChosen = 0;
        try {
            wordSizeChosen = sc.nextInt();
        } catch(Exception e){ 
            System.out.println("The word size chosen isn't valid. Please choose another number");
        }
  
        this.setWordSize(wordSizeChosen);
        this.setNumberAttempts(Mode.Normal);
        //setDefaultWords();
        
        if(checkValidWordSize()){
            chooseWordAtRandom();
            //System.out.println("The word is " + this.getWordChosen());

        } else {
            //setDefaultWords();
            chooseWordSizeByPlayer();
        }
        
    }

    @Override
    public void startGame() {
        String restart = "";
        String difficulty = "";
        
        do {
            System.out.println("Do you want to play the Normal Mode in an Easy Difficulty ? " + "\n" + "Y (yes) / N (no)");
            Scanner sc = new Scanner(System.in);
            difficulty = sc.nextLine();
            
        } while (!difficulty.toLowerCase().equals("y") && !difficulty.toLowerCase().equals("n"));
        
        setDifficulty(difficulty);
        
        do {

            this.chooseWordSizeByPlayer();
            int attempt = 0;
            boolean wordFound = false;
            while (attempt < this.getNumberAttempts() && !wordFound) {
                System.out.print("Tell me a word with " + this.getWordSize() + " characters. \nYou have " + (this.getNumberAttempts() - attempt) + " attempts left : ");
                Scanner sc = new Scanner(System.in);
                String wordPlayer = sc.nextLine();

                if (checkWordPlayerSize(wordPlayer)) {
                    if (checkWordFound(wordPlayer)) {
                        System.out.println("Well Played ! The word was " + this.getWordChosen());
                        wordFound = true;
                    } else {
                        System.out.println(checkWordPlayer(wordPlayer));              
                    }
                    attempt++;
                } else {
                    System.out.println("The word you put doesn't match the size you gave at the start of the game.");
                }
                
            }

            if (wordFound) {
                
                System.out.println("You found the word with " + attempt + " attempts.");
            } else {
                System.out.println("Game over ! The word was " + this.getWordChosen());
            }
            System.out.println("Do you want to Restart ? " + "\n" + "Y (yes) / Anything else will quit you the game");
            Scanner sc = new Scanner(System.in);
            restart = sc.nextLine();
            
        } while (restart.toLowerCase().equals("y"));
    }
    
    
    
}
