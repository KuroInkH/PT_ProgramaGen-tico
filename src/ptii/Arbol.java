package ptii;

class Arbol {
    NodoArbol raiz;
    int lvl;
    int peso;
    float calificacion;
    
    public Arbol(NodoArbol raiz, int lvl)
    {
        this.raiz = raiz;
        this.lvl = lvl;
        this.calificacion = 8;
        this.peso = 0;
    }
    
    public Arbol(){
    }
    
    public void imprimirArbol(NodoArbol nodo, int nivel)
    {
        //System.out.print("|");
        for(int i = nivel; i < lvl; i++)
        {
            System.out.print("\t");
            //System.out.print("----");
        }        
        nivel--;
        System.out.println(nodo.contenido /*+ "(" + nodo.id + ")"*/);
        if(nodo.hijoIzq != null)
        {
            imprimirArbol(nodo.hijoIzq, nivel);
        }
        if(nodo.hijoDer != null)
        {
            imprimirArbol(nodo.hijoDer, nivel);
        }
    }
    
    public void calcularPeso(NodoArbol nodo)
    {
        peso++;
        nodo.id = peso;
        if(nodo.hijoIzq != null)
        {
            calcularPeso(nodo.hijoIzq);
        }
        if(nodo.hijoDer != null)
        {
            calcularPeso(nodo.hijoDer);
        }
    }
    
    public void calcularNivel(NodoArbol nodo, int nivA, int nivB)
    {
        if(nodo.hijoIzq != null)
        {
            nivA++;
            calcularNivel(nodo.hijoIzq, nivA, nivB);
        }
        if(nodo.hijoDer != null)
        {
            nivB++;
            calcularNivel(nodo.hijoDer, nivA, nivB);
        }
        
        if(nivA > nivB)
            lvl = nivA;
        else
            lvl = nivB;
    }
}
