/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import audioconference.SoundSpeaker;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nego MPYANA
 */
public class RxThreadClient extends Thread {
    private Socket connexion;
    private BufferedInputStream in;
    public RxThreadClient(Socket connexion){
        try {
            this.connexion = connexion;
            this.in = new BufferedInputStream(this.connexion.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ExThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    synchronized public void run(){
        FileOutputStream out = null;
        int dat;
        try {
            File fichier = new File("sonRecu.wav");
            if(!fichier.exists()) fichier.createNewFile();
            if(!fichier.exists()) fichier.createNewFile();
            out = new FileOutputStream(fichier);
            while(true){
                dat = this.in.read();
                if(dat != -1){
                    System.out.println("Donnees recues");
                    //byte[] data = this.in.readAllBytes();
                    this.in.transferTo(out);
                    System.out.println("donnees transferees");
                    this.lire();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RxThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RxThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(RxThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  
    
    @Override
    public void interrupt(){
        super.interrupt();
    }
    
    synchronized public void lire(){
        try {
            BufferedInputStream flux = new BufferedInputStream(new FileInputStream(new File("son.wav")));
            new SoundSpeaker(flux).lire();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RxThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
