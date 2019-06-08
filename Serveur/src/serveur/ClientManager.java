/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author Nego MPYANA
 */
public class ClientManager extends Thread {
    /**
     * Cette classe permet de gerer la connexion du client sur le serveur
     * Elle permettra precisement de prendre les donnees transmises par le client
     * au serveur pour qu'a son tour le serveur puisse le diffuser a tous les clients connectes
     * Elle contient comme propriete la connexion de type socket qui relie le serveur au client
     * Le serveur au quel il doit commencer a transmettre les donnees
     * Et le champs in qui contient le flux d'entree du socket
     * 
     */
    private Socket connexion;
    private TheServer serveur;
    private InputStream in;
    private OutputStream out;
    public ClientManager(Socket connexion,TheServer serveur){
        try {
            this.connexion = connexion;
            this.in = new BufferedInputStream(connexion.getInputStream());
            this.serveur = serveur;
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void run(){
        //BufferedReader lecteur = new BufferedReader(new InputStreamReader(this.in));
        //String msg;
        int bit;
        int valide;
        byte[] data = new byte[1024];
        while(true){
            try {
                bit = this.in.read();
                if(bit>=0){
                    //System.out.println(this.in.available());
                    //byte nb;
                    File fichier = new File("Rx.wav");
                    if(!fichier.exists()) fichier.createNewFile();
                    this.out = new BufferedOutputStream(new FileOutputStream(fichier));
                    do{
                        valide = this.in.available();
                        System.out.println(bit);
                        //this.out.write(bit);
                        this.in.read(data,0,valide);
                        //System.out.println("Lecture...");
                    }while(bit>=0);
                    //System.out.println(bit);
                    System.out.println("Donnees arrivees");
                    this.out.close();
                }
                /**
                 * Loraqu'on demarre le getionnaire de client
                 * Une boucle infinie se cree, cherchant les informations sur l'entree
                 * En cas de tranmission d'une donnee du client
                 * Elle appelle la fonction de transfert vers le serveur
                 */
//                if(lecteur.ready()){
//                    msg = lecteur.readLine();
//                    System.out.println("Client >> "+msg);
//                }
            } catch (IOException ex) {
                
            }

        }
        
    }
    public void transfertData(){
        //la fonction de transfert des donnees
        this.serveur.sendAll(this.connexion);
    } 
}
 