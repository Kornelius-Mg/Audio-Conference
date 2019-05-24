package client;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import audioconference.SoundRecord;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kornelius-Mg
 */
public class ExThreadClient extends Thread {
    private Socket connexion;
    private BufferedOutputStream out;
    SoundRecord enregistreur;
    public static final String PATH = System.getenv("tmp")+"\\msg_client\\son.wav";
    public ExThreadClient(Socket connexion){
        try {
            this.connexion = connexion;
            this.out = new BufferedOutputStream(this.connexion.getOutputStream());
            this.enregistreur = new SoundRecord();
        } catch (IOException ex) {
            Logger.getLogger(ExThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(PATH);
    }
    
    @Override
    public void run(){
        while(true){}
    }
    
    @SuppressWarnings("null")
    public void sendData(){
        this.enregistreur.start();
        BufferedInputStream in = null;
        File fichier = null;
        try {
            fichier = new File(PATH);
            final int TAILLE = (int)fichier.length();
            byte[] data;
            in = new BufferedInputStream(new FileInputStream(fichier));
            data = in.readAllBytes();
            System.out.println(data.length);
            this.out.write(TAILLE);
            //this.out.write(data);
            System.out.println("Donnees envoyees sur le serveur");
            this.out.flush();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } 
    }
    
    public void stopSending(){
        this.enregistreur.interrupt();
        System.out.println(this.enregistreur.isAlive());
    }
    
    @Override
    public void interrupt(){
        this.enregistreur.interrupt();
        super.interrupt();
    }
}
