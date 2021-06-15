/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wordmind;

import java.util.Scanner;

/**
 *
 * @author hugod
 */

/*
Endless : Mode de jeu : Trouver le plus de mots sans se rater. (Commence par 2 lettres puis tu rajoutes 1 lettre à chaque fois)

Survival :    Autre mode de jeu à faire : Trouver le plus de mots en X attempts. Chaque mot trouvé rajoute X/2 attempts ou X est la taille du mot (arrondie au plus bas) 
    Nombre d'attempts différents au début : 10 (Hard) / 20 (Medium) / 30 (Easy)

Système de points
Enlever les interfaces
 */
public class EndlessGame extends AbstractGame implements EasyMode, HardMode {

    private static final int EG_NBLETTERS_START = 3;

    public EndlessGame() {

    }

    public void searchWordsEndlessGame(int taille) {

        this.setWordSize(taille);
        this.setNumberAttempts(Mode.Endless);
        //setDefaultWords();
        chooseWordAtRandom();

    }

    @Override
    public void startGame() {
        String restart = "";
        String difficulty = "";
        

        do {
            System.out.println("Do you want to play the Endless Mode in an Easy Difficulty ? " + "\n" + "Y (yes) / N (no)");
            Scanner sc = new Scanner(System.in);
            difficulty = sc.nextLine();
        } while (!difficulty.toLowerCase().equals("y") && !difficulty.toLowerCase().equals("n"));

        setDifficulty(difficulty);

        do {
            
            int taille = EG_NBLETTERS_START;
            int attempt = 0;
            boolean wordFound = false;
            int wordFounds = 0;

            do {
                searchWordsEndlessGame(taille);

                while (attempt < this.getNumberAttempts() && !wordFound) {
                    System.out.print("Tell me a word with " + this.getWordSize() + " characters. \nYou have " + (this.getNumberAttempts() - attempt) + " attempts left : ");
                    Scanner sc = new Scanner(System.in);
                    String wordPlayer = sc.nextLine();

                    if (checkWordPlayerSize(wordPlayer)) {
                        if (checkWordFound(wordPlayer)) {                    
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

                    taille++;
                    wordFounds++;
                    attempt = 0;
                    wordFound = false;

                    String messageFin = "Well played ! The word was " + this.getWordChosen() + ".";
                    if (chooseWordAtRandom()) {
                        messageFin += "\nNow, let's get with a word with " + taille + " characters ! Good luck. ";
                    } else {
                        taille = EG_NBLETTERS_START;
                        messageFin += "Let's restart to a word with " + taille + " characters ! ";
                    }
                    System.out.println(messageFin);
                    searchWordsEndlessGame(taille);

                } else {
                    System.out.println("Game over ! The word to found was " + this.getWordChosen() + ". You found " + wordFounds + " words ! ");
                    System.out.println("Do you want to Restart ? " + "\n" + "Y (yes) / Anything else will quit you the game");
                    Scanner sc = new Scanner(System.in);
                    restart = sc.nextLine();
                }

            } while (attempt < this.getNumberAttempts() && !wordFound);

        } while (restart.toLowerCase().equals("y"));
    }

}
