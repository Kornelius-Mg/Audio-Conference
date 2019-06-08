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
    
    public void record(){
        this.enregistreur.start();
    }
    
    @SuppressWarnings("null")
    private void sendData(){
        BufferedInputStream in = null;
        File fichier = null;
        try {
            fichier = new File(PATH);
            in = new BufferedInputStream(new FileInputStream(fichier));
            PrintStream p = new PrintStream(this.out,true);
            byte[] data = in.readAllBytes();
            byte[] donnee = {1,8,0,4,9};
            p.write(donnee);
            /*for(byte b : donnee)
                this.out.write(b);
            System.out.println((data.length/1024)+" kilo-octets envoyes");
            this.out.flush();
            System.out.println("Envoi des donnees reussi");*/
            in.close();
            //this.out.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } 
        catch(Exception ex){ System.err.println(ex.getMessage());}
    }
    
    public void stopSending(){
        this.enregistreur.interrupt();
        System.out.println(this.enregistreur.isAlive());
        this.sendData();
    }
    
    @Override
    public void interrupt(){
        this.enregistreur.interrupt();
        super.interrupt();
    }
}
