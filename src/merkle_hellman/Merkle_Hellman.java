/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkle_hellman;

/**
 *
 * @author Jairo
 */
public class Merkle_Hellman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Algoritmo alh=new Algoritmo();
       // alh.generaSecuencia();
        alh.llavePublica();
       // System.out.println(alh.textoABinario("ho"));
        System.out.println("Mensaje: "+alh.listaBinarios(alh.textoABinario("puto")));
        System.out.print("Mensaje Encriptado: ");
        alh.encriptar("puto");
        //System.out.println( alh.aleatorio(alh.getSumatoria()));
    }
    
}
