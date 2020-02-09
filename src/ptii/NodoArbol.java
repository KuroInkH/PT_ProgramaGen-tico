package ptii;

import java.util.Random;

class NodoArbol {
    NodoArbol padre;
    String contenido;
    int id;
    int tipo; //0 funcion, 1 ft, 2 Terminal
    NodoArbol hijoIzq;
    NodoArbol hijoDer;
    
    public NodoArbol()
    {
        padre = null;
        contenido = "No tengo contenido";
        hijoIzq = null;
        hijoDer = null;
        id = -1;
    } 
    
    public void setContenido(String cont)
    {
        this.contenido = cont;
    }
}
