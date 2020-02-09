package ptii;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class PG {
    List<Arbol> listaArbol;
    String[] Funciones = {"ObjCerca", "ObsCerca", "buscarObj"};
    String[] FunTerminales = {"Avanzar", "Retroceder", "Girar"};
    int tamPoblacion;
    int generacion;
    int maxGen;
    int cont;
    Random rnd;
    //Servomotores 1,2,5 giran, 3,4 avanzan
    
    //Inicializa las variables
    public PG()
    {
        listaArbol = new ArrayList<>();
        generacion = 0;
        tamPoblacion = 3;
        maxGen = 3;
        rnd = new Random();
    }
    
    public void iniciarPG()
    {           
        int[] seleccion = new int[2];
        System.out.println("Iniciando programa genético...");
        //Creamos la población para la generación 0, todos con diferentes alturas
        System.out.println("Creando población de árboles...");
        crearPoblacion();
        //Evaluamos la generación
        for(int i = 0; i < tamPoblacion; i++)
        {
            //FALTAAA
            evaluar(listaArbol.get(i));
        }
        //Iniciamos el ciclo de generaciones
        int k = 0;
        while(k < maxGen)
        {
            for(int m = 0; m < tamPoblacion; m++)
            {
                //Seleccionamos a los padres
                System.out.println("Seleccionando árboles para reproducción...");
                seleccion = seleccionar();
                //Cruza
                System.out.println("Preparando árboles para cruza...");        
                cruzar(seleccion); 
            }
            //Recalcular los valores del árbol
            for(int i = 0; i < tamPoblacion; i++)
            {
                listaArbol.get(i).peso = 0; 
                listaArbol.get(i).calcularNivel(listaArbol.get(i).raiz, 0, 0);
                listaArbol.get(i).calcularPeso(listaArbol.get(i).raiz);
                //Evaluar Árboles
                evaluar(listaArbol.get(i));
                //Mientras no sea la óptima o no sea el número máximo de ciclos 
            }
            k++;
        }
    }
    
    //Crea la primer generación del algoritmo
    public void crearPoblacion()
    {
        //Nivel máximo del árbol, sólo como población inicial        
        int tMax = 2;
        
        for(int i = 0; i < tamPoblacion; i++)
        {            
            int t;
            t = (int)(Math.random()* tMax + 3);
            listaArbol.add(new Arbol(crearNodoArbol(null, t),t));
            listaArbol.get(i).calcularPeso(listaArbol.get(i).raiz);
        }
    }
    
    //Regresa un nodoarbol
    public int[] seleccionar()
    {
        int r, i, j, ant = -1;
        float totalSum = 0, sum = 0;
        int[] seleccion = new int[2];
        //Calcular la suma total de la población
        for(i = 0; i < listaArbol.size(); i++)
        {
            totalSum = totalSum + listaArbol.get(i).calificacion;
        }
        
        j=0;
        while(j < 2)
        {
            //Generar un número entre la suma total y 0
            r = (int)(Math.random()* totalSum);
            //System.out.println("\tValor de r = " + r + " de Total " + totalSum);
            //Buscar r y seleccionarlo
            i = 0;
            while(sum < r && i < listaArbol.size())
            {
                sum = sum + listaArbol.get(i).calificacion;
                i++;            
            }
            if(i >= listaArbol.size()) i = listaArbol.size()-1;
            if(i != ant)
            {
                seleccion[j] = i;
                ant = i;
                System.out.println("árbol seleccionado: " + seleccion[j]);                
                j++;
            }
        }
        return seleccion;
    }
    
    public void cruzar(int[] seleccion)
    {
        int a,b;
        Arbol tA = new Arbol();
        Arbol tB = new Arbol();
        //seleccion[0].imprimirArbol(seleccion[0].raiz, seleccion[0].lvl);
        //System.out.println("");
        //seleccion[1].imprimirArbol(seleccion[1].raiz, seleccion[1].lvl);
        //System.out.println("\n\n");
        //Elegimos el nodo en el que se realizará la cruza   
        a = (int)(Math.random() * listaArbol.get(seleccion[0]).peso) + 1;
        b = (int)(Math.random() * listaArbol.get(seleccion[1]).peso) + 1;
        tA.raiz = buscarNodo(listaArbol.get(seleccion[0]).raiz, a);
        tB.raiz = buscarNodo(listaArbol.get(seleccion[1]).raiz, b);
        tA.calcularNivel(tA.raiz, 0, 0);
        tB.calcularNivel(tB.raiz, 0, 0);
        System.out.println("Se injertará A en el árbol #" + seleccion[1] + " en el nivel " + b);
        tA.imprimirArbol(tA.raiz, tA.lvl);
        listaArbol.get(seleccion[1]).imprimirArbol(listaArbol.get(seleccion[1]).raiz, listaArbol.get(seleccion[1]).lvl);
        cont = 0;
        injerto(listaArbol.get(seleccion[1]).raiz, tA, b);
        
        System.out.println("\n\nÁrbol resultante");
        
        listaArbol.get(seleccion[1]).imprimirArbol(listaArbol.get(seleccion[1]).raiz, listaArbol.get(seleccion[1]).lvl);
    }
    
    public void mutar()
    {
        
    }
    
    private void evaluar(Arbol arbol) {
        
    }

    NodoArbol crearNodoArbol(NodoArbol padre, int t) {
        NodoArbol n =  new NodoArbol();        
        n.padre = padre;        
        t--;
        if(t < 0)
        {
            return null;
        }
        else if(t == 0)
        {
            int paso = (rnd.nextInt() % 180 + 1);
            n.setContenido("" + paso);
        }
        else if(t > 1)
        {        
            n.setContenido(Funciones[(int)(Math.random() * Funciones.length)]);
            n.hijoDer = crearNodoArbol(n, t);
            n.hijoIzq = crearNodoArbol(n, t); 
        }
        else
        {
            n.setContenido(FunTerminales[(int)(Math.random() * FunTerminales.length)]);
            n.hijoIzq = crearNodoArbol(n, t); 
        }
        return n;
    }    

    private NodoArbol buscarNodo(NodoArbol nodo, int id) 
    {
        if(nodo.id != id)
        {
            if(nodo.hijoIzq != null)
            {
                return buscarNodo(nodo.hijoIzq, id);
            }
            if(nodo.hijoDer != null)
            {
                return buscarNodo(nodo.hijoDer, id);
            }
        }
        return nodo;
    }

    private void injerto(NodoArbol nodo, Arbol temp, int id) {
        //System.out.println(nodo.id + " == " + id);
        if(nodo.id == id)
        {
            //System.out.println("Contenido anterior: " + nodo.contenido+ " nuevo contenido: " + temp.raiz.contenido);            
            nodo.contenido = temp.raiz.contenido;
            nodo.hijoDer = temp.raiz.hijoDer;
            nodo.hijoIzq = temp.raiz.hijoIzq;
        }
        else
        {            
            if(nodo.hijoIzq != null)
            {
                injerto(nodo.hijoIzq, temp, id);
            }
            if(nodo.hijoDer != null)
            {
                injerto(nodo.hijoDer, temp, id);
            }
        }
    }    
}