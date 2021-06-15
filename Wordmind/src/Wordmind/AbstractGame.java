/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wordmind;
import Exceptions.InvalidWordSizeException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract game is a game prototype, that regroups attributes and methods common to both modes.
 * @author hugod
 * @version 1.0
 */
public abstract class AbstractGame {
    
    private List <String> words;
    private int wordSize;
    private String wordChosen;
    private Mode mode;
    private int numberAttempts = 0;
    private boolean hardMode;
    
    /**
     * Game constructor.<br>
     * <ul>
     * <li><strong>words</strong> is an ArrayList of words that matches with the size chosen by the player (<strong>wordSize</strong>).</li><br>
     * <li><strong>wordSize</strong> is the size of the word that will be chosen randomly by the program.</li><br>
     * <li><strong>wordChosen</strong> is the word that have to be guessed by the player.</li><br>
     * <li><strong>mode</strong> is the gamemode.</li>
     * </ul><br>
     * Doesn't need any parameters.
     */
    public AbstractGame(){
        this.words = new ArrayList();
        this.wordSize = 0;
        this.wordChosen = null;
        this.mode = null;
    }
    
    /**
     * This methods allows the player to choose a method. Then, calls {@link #initializeMode(int modeChosen) initializeMode} for initailizing the game or its rules.
     */
    public void chooseMode(){
        int modeChosen = 0;
        while (modeChosen < 1 || modeChosen > Mode.values().length) {

            int valueIndex = 1;
            System.out.print("Select a mode to play Mastermind");
            for (Mode m : Mode.values()) {
                System.out.println("\n\t" + valueIndex + "- " + m);
                valueIndex++;
            }
            Scanner sc = new Scanner(System.in);

            try {
                modeChosen = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Select a valid number.");
            }

        }
        
        try {
            initializeMode(modeChosen);
        } catch (IOException ex) {
            Logger.getLogger(AbstractGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setDifficulty(String difficulty){
        if(difficulty.toLowerCase().equals("y")){
            this.hardMode = false;
        } else {
            this.hardMode = true;
        }
    }
    
    /**
     * Initialize the mode chosen by the player.
     * @param modeChosen : Represents the mode that the user wants to play.
     * @throws IOException 
     */
    public void initializeMode(int modeChosen) throws IOException{
        AbstractGame game = null;
        switch(modeChosen){
            case 1:
                game = new NormalGame();
                break;
            case 2:
                game = new EndlessGame(); //Faire en Hard Mode , Pareil pour Survival 
                break;
            case 3:
                game = new SurvivalGame();
                break;
            case 4:
                showWordmindRules();
                break;
            case 5:
                showModeRules();
                break;
        }
        game.startGame();
    }
 
    /**
     * Method that checks if the Wordsize chosen by the player is valid (Not a negative number or at least a word that exists witht the size given)
     * @return true if the validations have successfully worked. 
     */
    public boolean checkValidWordSize(){
        return this.getWordSize() > 0 && searchWords();
    }
    
    /**
     * Reset the Array by default
     */
    public void setDefaultWords(){
        this.getWords().removeAll(words);
    }
       
    /**
     * Find the words that matches with the wordsize.
     * @return true if at least one word has been added to the Array of words.
     */
    public boolean searchWords(){
        
        setDefaultWords();
        
        try{
            BufferedReader in = new BufferedReader(new FileReader("dico.txt"));
            String line;
            
            while ((line = in.readLine()) != null) {
                if(line.length() == this.getWordSize()){
                    this.getWords().add(line);                  
                }
                
            }
            
            in.close();
                       
        } catch(IOException e) {
            
            e.printStackTrace();
            return false;
            
        }   
        
        return !this.getWords().isEmpty();
        
    }
    
    /**
     * Choose randomly a word into the Array of words.
     * @return true if a word has been chosen randomly.
     */
    public boolean chooseWordAtRandom(){
        
        if(searchWords()){
            
            int wordNumber = (int) (Math.random() * (this.getWords().size()));
            
            this.setWordChosen(this.getWords().get(wordNumber));
            
            return true;
        }
        
        return false;
        
    }
    
    /**
     * Check if the word given by the player has a size corresponding to the wordsize.
     * @param wordPlayer : the word given by the player
     * @return true if that matches.
     */
    public boolean checkWordPlayerSize(String wordPlayer){
        return wordPlayer.length() == this.getWordSize();
    }
    
    /**
     * Check if the word given by the player is the word to find.
     * @param wordPlayer : The word given by the player.
     * @return true if the word has been found.
     */
    public boolean checkWordFound(String wordPlayer){
        return checkWordPlayerSize(wordPlayer) && wordPlayer.toLowerCase().equals(this.getWordChosen());
    }
    
    /**
     * Check the mode of the player. 
     * Then, check the word according to the rules of the mode.
     * @param wordPlayer
     * @return a String Buffer according to the mode of the player.
     */
   public StringBuffer checkWordPlayer(String wordPlayer){
        if(this.isHardMode()){
            return checkWordPlayerHardMode(wordPlayer);
        }
        return checkWordPlayerEasyMode(wordPlayer);
    }
   
   /**
    * Checker for the Easy Mode.
    * @param wordPlayer : word given by the player.
    * @return the String that shows where the letters are correcty placed, mismatched and that aren't into the word.
    */
    public StringBuffer checkWordPlayerEasyMode(String wordPlayer){
        
        char[] wp = wordPlayer.toLowerCase().toCharArray();
        
        char[] wc = this.getWordChosen().toCharArray();

        StringBuffer wordPlayerChecked = setTestToDefault();
        
        if(checkWordPlayerSize(wordPlayer)){
            
            for (int i = 0; i < wp.length; i++){
                
                 if(wc[i] == wp[i]){
                     wc[i] = '*';
                     wp[i] = 'V';
                     wordPlayerChecked.insert(i, "V");
                     wordPlayerChecked.deleteCharAt(i+1);
                 }                
            }
            
            for (int j = 0; j < wp.length; j++){
                for(int k = 0; k < wp.length; k++){
                    if(wc[j] == wp[k]){ //Endless Mode, juste incrémenter le compteur ici.
                        wc[j] = '#';
                        wp[k] = 'X';
                        wordPlayerChecked.insert(k, "X");
                        wordPlayerChecked.deleteCharAt(k+1);
                    }
                }
            }
            
        } else {
            return null;
        }
        
        return wordPlayerChecked;   
        
    }
    
    /**
    * Checker for the Hard Mode.
    * @param wordPlayer : word given by the player.
    * @return the String that shows how many letters you correctly placed, you mismatched and that aren't into the word.
    */
    public StringBuffer checkWordPlayerHardMode(String wordPlayer){
        
        char[] wp = wordPlayer.toLowerCase().toCharArray();
        
        char[] wc = this.getWordChosen().toCharArray();
       
        int GuessedLettersCounter = 0;
        int MisplacedLettersCounter = 0;
        
        if(checkWordPlayerSize(wordPlayer)){
            
            for (int i = 0; i < wp.length; i++){
                
                 if(wc[i] == wp[i]){
                     wc[i] = '*';
                     wp[i] = 'V';
                     GuessedLettersCounter++;
                 }                
            }
            
            for (int j = 0; j < wp.length; j++){
                for(int k = 0; k < wp.length; k++){
                    if(wc[j] == wp[k]){ 
                        wc[j] = '#';
                        wp[k] = 'X';
                        MisplacedLettersCounter++;
                    }
                }
            }
            
        } else {
            return null;
        }
        
        return new StringBuffer("You placed correctly " + GuessedLettersCounter + " letters, misplaced " + MisplacedLettersCounter + " and there are " + (this.getWordSize()-GuessedLettersCounter-MisplacedLettersCounter) +  " that aren't in the word." );   
        
    }
          
    /**
     * Method that reset the String by default (Easy mode only)
     * @return the String with a reset.
     */
    public StringBuffer setTestToDefault(){
        StringBuffer test = new StringBuffer();
        for(int i = 0; i < this.getWordSize(); i++){
            test.append("-");
        }
        return test;
    }
    
    /**
     * Show the rules of Wordmind.
     * @throws IOException 
     */
    public void showWordmindRules() throws IOException{
        int c = 0;
        while(c != 10){
            System.out.println("Wordmind.. Press Enter to continue");
            c = System.in.read();
        }
        chooseMode();    
    }
    
    /**
     * Show the rules of the mode.
     * @throws IOException 
     */
    public void showModeRules() throws IOException{
        int c = 0;
        while(c != 10){
            System.out.println("Mode.. Press Enter to continue");
            c = System.in.read();       
        }
        chooseMode();   
    }  
    
    public List<String> getWords() {
        return words;
    }

    public int getWordSize() {
        return wordSize;
    }

    public void setWordSize(int wordSize) {
        this.wordSize = wordSize;
    }

    public String getWordChosen() {
        return wordChosen;
    }

    public void setWordChosen(String wordChosen) {
        this.wordChosen = wordChosen;
    }
    
    public boolean isHardMode() {
        return hardMode;
    }
    
    public int getNumberAttempts() {
        return numberAttempts;
    }

    /**
     * Set the number of attempts corresponding to your mode.
     */
    public void setNumberAttempts(Mode mode) {
        
        switch(mode.name()){
            
            case "Normal":
                if(this.isHardMode() == false){
                    this.numberAttempts = (int) Math.round(1.25*this.getWordSize()); 
                } else {
                    this.numberAttempts = Math.round(2*this.getWordSize()); 
                }
                break;
                
            case "Endless":
                
                if(this.isHardMode() == false){
                    this.numberAttempts = (int) Math.round(1.25*this.getWordSize()) + 1; 
                } else {
                    this.numberAttempts = Math.round(2*this.getWordSize()) + 1; 
                }
                break;
                
        }
        
        
    }
    
    public abstract void startGame();
          
    public static void main(String[] args) throws InvalidWordSizeException{
        NormalGame ng = new NormalGame();
        ng.chooseMode();
        
    }
    

    
}
