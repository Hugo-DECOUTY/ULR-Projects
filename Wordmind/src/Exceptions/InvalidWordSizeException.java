/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author hugod
 */
public class InvalidWordSizeException extends Exception {
    
    public InvalidWordSizeException(){
        System.out.println("The word size chosen isn't valid. Please choose another number");
    }
    
}
