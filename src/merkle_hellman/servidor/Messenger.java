/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkle_hellman.servidor;

/**
 *
 * @author Jairo
 */
public class Messenger {
    
    public static Conector servidor,cliente;
    public static void main(String[]args){
       VServidor server= new VServidor();
       server.main();
       
    }
    
    public static void initServer(){
    
      servidor=new Conector("hilo");
      servidor.start();
    }
}
