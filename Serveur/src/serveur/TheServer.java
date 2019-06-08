/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nego MPYANA
 */
public class TheServer {
    
    /**
     * cette classe implemente le serveur, qui sera le centre des conversations
     * Elle comprend comme propriete la liste des connexions clients et la liste des gestionnaires
     */
    public static final int DEFAULT_PORT = 12500;
    private ServerSocket ss;
    private List<Socket> listeClients;
    private List<ClientManager> listeGestionnairesClient;
    public TheServer(){
        try {
            this.ss = new ServerSocket(DEFAULT_PORT);
            this.listeClients = new ArrayList<>();
            this.listeGestionnairesClient = new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(TheServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Le serveur est pret");
    }
    public boolean demarrer(){
        /**
         * Au demarrage, le serveur est en mode ecoute en quete d'une demande de connexion d'un client
         * Au cas ou un client se connecte, on lui cree un gestionnaire de client
         * Puis on lui enregistre lui et son gestionnaire respectivemet dans la liste des connections et la liste des gestionnaires
         */
        System.out.println("Le serveur a demarre");
        while(true){
            try {
                Socket client = ss.accept();
                System.out.println("Un client vient de se connecter");
                ClientManager gstClt = new ClientManager(client,this);
                gstClt.start();
                listeClients.add(client);
                listeGestionnairesClient.add(gstClt);
            } catch (IOException ex) {
                Logger.getLogger(TheServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sendAll(Socket c){
        FileInputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(new File("Rx.wav"));
            data = in.readAllBytes();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TheServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TheServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Envoi des donnees aux clients");
        BufferedOutputStream out = null;
        for(Socket s : this.listeClients) {
            try {
                out = new BufferedOutputStream(s.getOutputStream());
                if(!s.equals(s)){
                    out.write(data.length);
                    out.write(data);
                    System.out.println("Donnees envoye a un client");
                    out.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(TheServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void fermer(){
        //On ferme tour a tours les gestionnaires de client
        this.listeGestionnairesClient.stream().forEach((gst) -> {
            gst.interrupt();
        });
        
        this.listeClients.stream().forEach((s) -> {
            try {
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(TheServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
