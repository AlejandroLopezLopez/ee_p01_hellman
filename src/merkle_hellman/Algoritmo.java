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
import java.math.*;
import java.util.*;
import sun.misc.FloatingDecimal;
public class Algoritmo {
   
static  Lista<BigInteger> secuencia=new Lista<BigInteger>();
static  Lista<BigInteger> pubKey=new Lista<BigInteger>();
static  Lista<BigInteger> listaFinal=new Lista<BigInteger>();
static  BigInteger sumatoria,valorQ,valorR,primero,siguiente;
  
  /**
   * Metodo getSumatoria
   * @return 
   */
  public BigInteger getSumatoria(){
    return sumatoria;
  }
  
  /**
   * Genera numero aleatorio con un minimo y maximo como parametro
   * @param minimo
   * @param maximo
   * @return 
   */
  public static int generaNumeroAleatorio(int minimo, int maximo){
        int num =(int)Math.floor(Math.random()*(minimo-(maximo+1))+(maximo+1));
        return num;
  }
  
/**
 *Generar llave prevada: W,EW,Q y R.
 */
  public static void generaSecuencia(){
    primero= new BigInteger(8,new Random() );
    secuencia.insertaInicio(primero);
     for (int i = 0; i < 7; i++) {
         siguiente=new BigInteger("4");
         siguiente=primero.multiply(siguiente);
         secuencia.insertaFinal(siguiente);
         primero=siguiente;
     }
     System.out.println("Secuencia W: "+secuencia);
     
     /**
      * Sumatoria de la secuencia super incrementada
      */
     
     Nodo<BigInteger> aux= secuencia.getInicio();
     sumatoria= new BigInteger("0");
     while(aux.getSiguiente()!=null){
       sumatoria= sumatoria.add(aux.getDato());
       aux=aux.getSiguiente();
     }
        sumatoria=sumatoria.add(aux.getDato());
        valorQ=sumatoria.add(new BigInteger(10,new Random()));
       System.out.println("Sumatoria de W: "+ sumatoria+"\n"+"Q: "+valorQ);
      
     /**
      * Generar r
      */
      BigInteger comparable=new BigInteger("1");
     
      int temporal=0;
      do{
          temporal=generaNumeroAleatorio(1,Integer.parseInt(sumatoria.toString()));
          valorR=new BigInteger(temporal+"");
      }while(((valorQ.gcd(valorR)).compareTo(comparable))!=0);
      
        System.out.println("R: "+valorR+"\n"+" Minimo comun multiplo debe ser: "+valorQ.gcd(valorR));
      
      
 }

  /**
   * Generar llave publica
   */
    public static void llavePublica(){
    generaSecuencia();
     Nodo<BigInteger> aux=secuencia.getInicio();
     while(aux!=null){
        BigInteger aux2;
        aux2=(aux.getDato().multiply(valorR)).mod(valorQ);
        pubKey.insertaFinal(aux2);
        aux=aux.getSiguiente();
     }
        System.out.println("llave publica: "+pubKey);
    }
    
    
    /**
     * 
     * @param texto
     * @return 
     */
 public static  String textoABinario(String texto)
    {
        String textoBinario = "";
        for(char letra : texto.toCharArray())
        {
            textoBinario += String.format("%8s", Integer.toBinaryString(letra));
        }
      
        return textoBinario.replace("\u0020","\u0030");
    }

/**
 * Lista a binarios: ingresa el valor en bits del texto a una lista
 * @param texto
 * @return 
 */ 
 public static Lista<BigInteger> listaBinarios(String texto){
    Lista<BigInteger> lista=new Lista<BigInteger>();
    for(char letra :texto.toCharArray()){
        String b=letra+"";
        int i=Integer.parseInt(b);
        BigInteger bin=new BigInteger(i+"");
        lista.insertaFinal(bin);
    }
    return lista;
 }
 
 /**
  * encriptar
  */
 
 public static String encriptar(String texto){
     llavePublica();
    Lista<BigInteger> resultado=new Lista<BigInteger>();
    Lista<BigInteger> textoBinarios;//esta lista solo sirve para almacenar los ninarios con los dos metoso anteriores
    textoBinarios=listaBinarios(textoABinario(texto));
    Nodo<BigInteger> tbin=textoBinarios.getInicio();
    Nodo<BigInteger> pk=pubKey.getInicio();
  
    while(tbin!=null){
     while(pk!=null){
       BigInteger mult=pk.getDato().multiply(tbin.getDato());
       resultado.insertaFinal(mult);
       pk=pk.getSiguiente();
       tbin=tbin.getSiguiente();
     }
       pk=pubKey.getInicio();
    }
    
    String retorno="";
    Nodo<BigInteger> nres=resultado.getInicio();
    Integer conta=0;
    Integer suma=0;
    while(nres!=null){
        for (int i = 0; i <8; i++) {
            BigInteger f=nres.getDato();
            conta= f.intValue();
            suma+=conta;
            nres=nres.getSiguiente();
        }
        BigInteger big= new BigInteger(suma+"");
        listaFinal.insertaFinal(big);
        
        System.out.print(big+",");
        retorno+=suma+""+",";
        suma=suma-suma;
    
    }
    return retorno;
 }
 
 public static  void desencriptar(){
   
   System.out.println("\nLista final: "+listaFinal);
   Lista<BigInteger> descricao=new Lista<BigInteger>();// es la lista de  sumatorias para aplicar la formula 
   BigInteger decrip;
   Nodo<BigInteger> recorre=listaFinal.getInicio();
   while(recorre!=null){
       decrip=recorre.getDato().multiply(valorR.modInverse(valorQ)).mod(valorQ);
       descricao.insertaFinal(decrip);
       recorre=recorre.getSiguiente();
   }
     System.out.println("\n Descricao:"+descricao);
   
//invertimos la clave primaria   

Lista<BigInteger> inversa=new Lista<BigInteger>();
   Nodo<BigInteger> aux=secuencia.getInicio();
   
   while(aux!=null){
   inversa.insertaInicio(aux.getDato());
   aux=aux.getSiguiente();
   }
     System.out.println("Inversa: " +inversa );   

//desencriptamos
Nodo<BigInteger> aux2=inversa.getInicio();
Nodo<BigInteger> aux3=descricao.getInicio();
Lista<String> binario=new Lista<String>();
String bin="";
BigInteger i;
while(aux3!=null){

i=aux3.getDato();
while(aux2!=null){

if(aux2.getDato().compareTo(i)==-1 || aux2.getDato().compareTo(i)==0){
i=i.subtract(aux2.getDato());
bin+="1";
}else{
bin+="0";
}
aux2=aux2.getSiguiente();
}
binario.insertaFinal(bin);
bin="";
aux3=aux3.getSiguiente();
aux2=inversa.getInicio();

}

     System.out.println("Binarios: " + binario);
    
  // invertir binarios
  
Lista<String> binarios2=new Lista();
Nodo<String> aux4=binario.getInicio();
String bueno="";
String a="";

while(aux4!=null){
a=aux4.getDato();

for(int ñ=a.length()-1;ñ>=0;ñ--){
bueno+=a.charAt(ñ);
}

binarios2.insertaFinal(bueno);
bueno="";
aux4=aux4.getSiguiente();

}

     System.out.println("binarios bien: " + binarios2);

//comvertir


Nodo<String> vr=binarios2.getInicio();
System.out.println("Letras:");

char c;
while(vr!=null){
    
    c=(char)Integer.parseInt(vr.getDato(),2);
    System.out.print(c);   
    vr=vr.getSiguiente();
}
     /* BigInteger descrip;  
   BigInteger sumaTotal=new BigInteger("0");
   Nodo<BigInteger> recorre=listaFinal.getInicio();
   
   while(recorre!=null){
   
   sumaTotal=sumaTotal.add(recorre.getDato());
   recorre=recorre.getSiguiente();
   
   } 
   descrip=sumaTotal.multiply(valorR.modInverse(valorQ)).mod(valorQ);
     System.out.println("\nsumatotal: "+sumaTotal);
     System.out.println("descrip:"+descrip);*/
     
     
     
   
 
 }
    /**
     * 
     * @param tope
     * @return 
     */
    public BigInteger aleatorio(BigInteger tope){
         Random num=new Random();
         
         return new BigInteger(tope.bitLength(),num);
    }
}
/**
 * volvi todos los metodos staticos y el metodo encriptar lo hice que retornara un String
 */