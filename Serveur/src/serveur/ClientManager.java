/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private BufferedInputStream in;
    public ClientManager(Socket connexion,TheServer serveur){
        this.connexion = connexion;
        this.serveur = serveur;
        try {
            this.in = new BufferedInputStream(this.connexion.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        /**
         * Loraquu'on demarre le getionnaire de client
         * Une boucle infinie se cree, cherchant les informations sur l'entree
         * En cas de tranmission d'une donnee du client
         * Elle appelle la fonction de transfert vers le serveur
         */
        int size = -1;
        byte[] data = null;
        while(true){
            try {
                size = this.in.read();
                if(size != -1){
                    System.out.println(size);
                    System.out.println("Donnees recues");
                    data = this.in.readAllBytes();
                    this.transfertData(data);
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }
    public void transfertData(byte[] data){
        //la fonction de transfert des donnees
        this.serveur.sendAll(data,this.connexion);
    }
    
}
