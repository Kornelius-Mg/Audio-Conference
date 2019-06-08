/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nego MPYANA
 */
public class ClientUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String mot = "Je ne suis pas un homme libre";
        StringReader sr = new StringReader(mot);
        int lettre;
        try {
            while((lettre = sr.read())>=0){
                System.out.print((char)lettre);
            }
        }catch (IOException ex){
            Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
