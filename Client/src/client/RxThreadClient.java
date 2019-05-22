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
    SoundSpeaker lecteur;
    public RxThreadClient(Socket connexion){
        int size;
        try {
            this.connexion = connexion;
            this.in = new BufferedInputStream(this.connexion.getInputStream());
            while(true){
                size = in.read();
                if(size != -1){
                    byte[] data = this.in.readAllBytes();
                    File fichier = new File("sonRecu.wav");
                    if(!fichier.exists()) fichier.createNewFile();
                    FileOutputStream out = new FileOutputStream(fichier);
                    out.write(data);
                }
            }  
        } catch (IOException ex) {
            Logger.getLogger(ExThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        while(true){
            this.lecteur.lire();
        }
    }
    
    @Override
    public void interrupt(){
        this.lecteur.interrupt();
        super.interrupt();
    }
    
}
