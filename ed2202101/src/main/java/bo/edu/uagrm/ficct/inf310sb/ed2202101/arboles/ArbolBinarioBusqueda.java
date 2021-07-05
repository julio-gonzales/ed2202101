/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.inf310sb.ed2202101.arboles;

import bo.edu.uagrm.ficct.inf310sb.ed2202101.excepciones.ExcepcionClaveNoExiste;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Hp 240
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>,V> implements IArbolBusqueda<K,V>{
    
    protected NodoBinario<K,V> raiz;
    
    public ArbolBinarioBusqueda(){
        
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (valorAInsertar == null){
            throw new RuntimeException("no se permiten valores nulos");
        }
        
        if (this.esArbolVacio()){
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
        }
        NodoBinario<K,V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K,V> nodoActual = this.raiz;
        
        while (!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            if (claveAInsertar.compareTo(claveActual) < 0){
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        
        //si se llega a este punto, hallamos el lugar donde insertar la clave
        // y el valor
        NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        K claveActual = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveActual) < 0){
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
        
    }

    @Override
    public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }
    
    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
        }
        
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K,V> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }
        
        //si llegamos hasta aqui, quiere decir que encontramos la clave a eliminar
        //revisamos que caso es
        //caso 1
        //Es hoja
        if (nodoActual.esHoja()){
            return NodoBinario.nodoVacio();
        }
        
        //caso 2
        //2.1 solo tiene hijo izquierdo
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        
        //2.2 solo tiene hijo derecho
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho(); 
        }
        
        //caso 3
        NodoBinario<K,V> nodoDelSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> supuestoNuevoHijo = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
        
    }
    
    protected NodoBinario<K,V> buscarSucesor(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        
        return nodoAnterior;
    }

    @Override
    public V buscar(K claveABuscar) {

        NodoBinario<K,V> nodoActual = this.raiz;
        
        while (!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0){
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0){
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getValor();
            }
        }
        
        //si se llega a este punto la clave a buscar no se encuentra 
        //en el arbol, entonces retornamos null 
        return null;
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }

    @Override
    public int size() {
        int cantidad = 0;
        if (this.esArbolVacio()){
            return cantidad;
        }
        
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()){
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            cantidad++;
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            
            if (!nodoActual.esVacioHijoDerecho()){
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
            
        }
        return cantidad;
    }

    public int sizeRec(){
        return sizeRec(this.raiz);
    }
    
    private int sizeRec(NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidadPorIzquierda = sizeRec(nodoActual.getHijoIzquierdo()); 
        int cantidadPorDerecha = sizeRec(nodoActual.getHijoDerecho());
        
        return cantidadPorIzquierda + cantidadPorDerecha + 1;
    }
    
    @Override
    public int altura() {
        return altura(this.raiz);
    }
    
    protected int altura(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha ? alturaPorIzquierda + 1 : 
                alturaPorDerecha + 1;
    }
    
    public int alturaIte(){
        int alturaDelArbol = 0;
        if (this.esArbolVacio()){
            return alturaDelArbol;
        }
        
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()){
            int numeroDeNodosEnNivel = colaDeNodos.size();
            int posicion = 0;
            while (posicion < numeroDeNodosEnNivel){
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();
         
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }

                if (!nodoActual.esVacioHijoDerecho()){
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
            }
            alturaDelArbol++;
            
        }
        return alturaDelArbol;
    }

    @Override
    public int nivel() {
        int nivelDelArbol = - 1;
        if (this.esArbolVacio()) {
            return nivelDelArbol;
        }
        
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            int numeroDeNodosEnNivel = colaDeNodos.size();
            int posicion = 0;
            nivelDelArbol++;
            while (posicion < numeroDeNodosEnNivel){
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
            }

        }
        return nivelDelArbol;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()){
            return recorrido;
        }
        
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(raiz);
        
        while (!colaDeNodos.isEmpty()){
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()){
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()){
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()){
            return recorrido;
        }
        
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K,V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if( !nodoActual.esVacioHijoDerecho() ) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        
        return recorrido;
    }
    
    public List<K> recorridoPreOrdenRec(){
        List<K> recorrido = new LinkedList<>();
        recorridoPreOrdenRec(this.raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoPreOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)){
            return;
        }
        
        recorrido.add(nodoActual.getClave());
        recorridoPreOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoPreOrdenRec(nodoActual.getHijoDerecho(), recorrido);
    } 
    

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoEnInOrden(NodoBinario<K,V> nodoActual, List<K> recorrido){
        if (NodoBinario.esNodoVacio(nodoActual)){
            return;
        }
        
        recorridoEnInOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrden(nodoActual.getHijoDerecho(), recorrido);
    }
    
    public List<K> recorridoEnInOrdenIte(){
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()){
            return recorrido;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        while (!NodoBinario.esNodoVacio(nodoActual)){
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        
        while (!pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()){
                nodoActual = nodoActual.getHijoDerecho();
                while (!NodoBinario.esNodoVacio(nodoActual)){
                    pilaDeNodos.push(nodoActual);
                    nodoActual = nodoActual.getHijoIzquierdo();
                }       
            }
        }
        
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoEnPostOrden(NodoBinario<K,V> nodoActual, List<K> recorrido){
        if (NodoBinario.esNodoVacio(nodoActual)){
            return;
        }
        
        recorridoEnPostOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrden(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());
    }
    
    public List<K> recorridoEnPostOrdenIte(){
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()){
            return recorrido;
        }
        
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        
        insertarEnPilaParaPostOrden(nodoActual, pilaDeNodos);
        
        while (!pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()){
                NodoBinario<K,V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho() && 
                        nodoDelTope.getHijoDerecho() != nodoActual){
                    insertarEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(), 
                            pilaDeNodos);
                }
            }
        }
        
        return recorrido;
    }

    private void insertarEnPilaParaPostOrden(NodoBinario<K, V> nodoActual, Stack pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)){
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()){
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    public int cantidadDeHijosVacios() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;

        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            if (nodoActual.esHoja()) {
                cantidad = cantidad + 2;
            } else if ((nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) ||
                    (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo())){
                cantidad++;
            }

            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }

            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cantidad;
    }


    public int cantidadDeHijosVaciosRec() {
        if (this.esArbolVacio()) {
            return 0;
        }
        return cantidadDeHijosVaciosRec(this.raiz);
    }

    private int cantidadDeHijosVaciosRec(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }

        int cantidaIzq = cantidadDeHijosVaciosRec(nodoActual.getHijoIzquierdo());
        int cantidaDer = cantidadDeHijosVaciosRec(nodoActual.getHijoDerecho());



        return cantidaIzq + cantidaDer;
    }

    public boolean hayHijosVaciosAntesDelNivel (int nivel) {
        return hayHijosVaciosAntesDelNivel(this.raiz, nivel, 0);
    }

    private boolean hayHijosVaciosAntesDelNivel(NodoBinario<K,V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return false;
        }
        if (nivelActual < nivelObjetivo) {
            if (nodoActual.esVacioHijoDerecho() || nodoActual.esVacioHijoIzquierdo()) {
                return true;
            }
        }

        boolean hijosIzq = hayHijosVaciosAntesDelNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        boolean hijosDer = hayHijosVaciosAntesDelNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);

        return hijosIzq || hijosDer ? true : false;

    }


    //ejercicios



    public int sizeConRecorrido(){
        int cantidad = 0;
        if (this.esArbolVacio()){
            return cantidad;
        }

        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;

        insertarEnPilaParaPostOrden(nodoActual, pilaDeNodos);

        while (!pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            cantidad++;
            if (!pilaDeNodos.isEmpty()){
                NodoBinario<K,V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho() &&
                        nodoDelTope.getHijoDerecho() != nodoActual){
                    insertarEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(),
                            pilaDeNodos);
                }
            }
        }

        return cantidad;
    }


    //PRACTICO #01

    //3. Implemente un método iterativo que retorne la cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario
    public int cantidadDeNodosConAmbosHijosIte(){
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;

        NodoBinario<K,V> nodoActual = this.raiz;
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        while (!NodoBinario.esNodoVacio(nodoActual)){
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }

        while (!pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            if (!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
                cantidad++;
            }
            if (!nodoActual.esVacioHijoDerecho()){
                nodoActual = nodoActual.getHijoDerecho();
                while (!NodoBinario.esNodoVacio(nodoActual)){
                    pilaDeNodos.push(nodoActual);
                    nodoActual = nodoActual.getHijoIzquierdo();
                }
            }
        }
        return cantidad;
    }
    //4. Implemente un método iterativo que retorne la cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario
    public int cantidadDeNodosConAmbosHijosRec(){
        return cantidadDeNodosConAmbosHijosRec(this.raiz);
    }

    private int cantidadDeNodosConAmbosHijosRec(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }

        int cantidadIzquierda = cantidadDeNodosConAmbosHijosRec(nodoActual.getHijoIzquierdo());
        int cantidadDerecha = cantidadDeNodosConAmbosHijosRec(nodoActual.getHijoDerecho());
        if ( !nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo() ) {
            return cantidadDerecha + cantidadIzquierda + 1;
        }

        return cantidadDerecha + cantidadIzquierda ;
    }

    //5. Implemente un método recursivo que retorne la cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario, pero solo en el nivel N

    public int cantidadDeNodosConAmbosHijosEnNivel(int nivel) {
        return cantidadDeNodosConAmbosHijosEnNivel(this.raiz, nivel, 0);
    }

    private int cantidadDeNodosConAmbosHijosEnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }

        int cantIzq = cantidadDeNodosConAmbosHijosEnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        int cantDer = cantidadDeNodosConAmbosHijosEnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
        if (nivelActual == nivelObjetivo) {
            if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                return cantIzq + cantDer + 1;
            }
        }
        return cantIzq + cantDer;
    }
    //6. Implemente un método Iterativo que retorne la cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario, pero solo en el nivel N

    public int cantidadDeNodosCoAmbosHijosEnNivelIte(int nivelObjetivo) {
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;
        int nivelActual = -1;
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            int numeroDeNodosEnNivel = colaDeNodos.size();
            int posicion = 0;
            nivelActual++;
            while (posicion < numeroDeNodosEnNivel){
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
                if (nivelActual == nivelObjetivo && !nodoActual.esVacioHijoIzquierdo()
                        && !nodoActual.esVacioHijoDerecho()){
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    //7. Implemente un método iterativo que retorne la cantidad nodos que tienen un solo hijo
    //diferente de vacío en un árbol binario, pero solo antes del nivel N

    public int cantidadDeNodosConUnHijoEnNivel( int nivelObjetivo){
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;
        int nivelActual = -1;
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            int numeroDeNodosEnNivel = colaDeNodos.size();
            int posicion = 0;
            nivelActual++;
            while (posicion < numeroDeNodosEnNivel){
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
                if (nivelActual < nivelObjetivo) {
                    if (( !nodoActual.esVacioHijoIzquierdo()  && nodoActual.esVacioHijoDerecho()) ||
                       ( nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) ){
                        cantidad++;
                    }
                }
            }
        }
        return cantidad;
    }

    //12. Implemente un método iterativo con la lógica de un recorrido en inOrden que retorne el
    //número de nodos que tiene un árbol binario.

    public int cantidadDeNodosDelArbolBinarioConRecInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;

        NodoBinario<K,V> nodoActual = this.raiz;
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        while (!NodoBinario.esNodoVacio(nodoActual)){
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }

        while (!pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            cantidad++;
            if (!nodoActual.esVacioHijoDerecho()){
                nodoActual = nodoActual.getHijoDerecho();
                while (!NodoBinario.esNodoVacio(nodoActual)){
                    pilaDeNodos.push(nodoActual);
                    nodoActual = nodoActual.getHijoIzquierdo();
                }
            }
        }
        return cantidad;
    }

    //13. Implemente un método que reciba en listas de parámetros las llaves y valores de los
    //recorridos en postorden e inorden respectivamente y que reconstruya el árbol binario
    //original. Su método no debe usar el método insertar.


    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden,
                                List<K> claveNoInOrden, List<V> valoresNoInOrden,
                                boolean esConPreOrden ) {

        if (clavesInOrden == null || valoresInOrden == null ||
                claveNoInOrden == null || valoresNoInOrden == null) {
            throw new RuntimeException("Alguno de los parametros es nulo");
        }
        if (clavesInOrden.isEmpty() || valoresInOrden.isEmpty() ||
                claveNoInOrden.isEmpty() || valoresNoInOrden.isEmpty()) {
            throw new RuntimeException("Alguno de los parametros esta vacio");
        }

        if (clavesInOrden.size() != valoresInOrden.size() ||
                clavesInOrden.size() != claveNoInOrden.size() ||
                clavesInOrden.size() != valoresNoInOrden.size()) {
            throw new RuntimeException("Los parametros no pueden ser de distinto tamaño");
        }

        if (esConPreOrden) {
            this.raiz = reconstruirConPreOrden(clavesInOrden, valoresInOrden, claveNoInOrden, valoresNoInOrden);
        } else {
            this.raiz = reconstruirConPostOrden(clavesInOrden, valoresInOrden, claveNoInOrden, valoresNoInOrden);
        }

    }

    private NodoBinario<K,V> reconstruirConPostOrden(List<K> clavesInOrden, List<V> valoresInOrden,
                                                     List<K> clavesPostOrden, List<V> valoresPostOrden) {
        if (clavesInOrden.isEmpty()) {
            return NodoBinario.nodoVacio();
        }

        int posicionDeClavePadre = clavesInOrden.size() - 1;
        K clavePadre = clavesPostOrden.get(posicionDeClavePadre);
        V valorPadre = valoresPostOrden.get(posicionDeClavePadre);
        int posicionDeClavePadreInOrden = this.posicionDeClave(clavePadre, clavesInOrden);

        //para armar la rama izquierda
        List<K> clavesInOrdenIzq = clavesInOrden.subList(0, posicionDeClavePadreInOrden);
        List<V> valoresInOrdenIzq = valoresInOrden.subList(0, posicionDeClavePadreInOrden);
        List<K> clavesPreOrdenIzq = clavesPostOrden.subList(0, posicionDeClavePadreInOrden);
        List<V> valoresPreOrdenIzq = valoresPostOrden.subList(0, posicionDeClavePadreInOrden);
        NodoBinario<K,V> hijoIzquierdo = reconstruirConPostOrden(clavesInOrdenIzq, valoresInOrdenIzq,
                clavesPreOrdenIzq, valoresPreOrdenIzq);
        // para armar la rama derecha
        List<K> clavesInOrdenDer = clavesInOrden.subList(posicionDeClavePadreInOrden + 1, clavesInOrden.size());
        List<V> valoresInOrdenDer = valoresInOrden.subList(posicionDeClavePadreInOrden + 1, clavesInOrden.size());
        List<K> clavesPreOrdenDer = clavesPostOrden.subList(posicionDeClavePadreInOrden , clavesInOrden.size());
        List<V> valoresPreOrdenDer = valoresPostOrden.subList(posicionDeClavePadreInOrden , clavesInOrden.size());
        NodoBinario<K,V> hijoDerecho = reconstruirConPostOrden(clavesInOrdenDer, valoresInOrdenDer,
                clavesPreOrdenDer, valoresPreOrdenDer);
        //armando el nodo Actual
        NodoBinario<K,V> nodoActual = new NodoBinario<>(clavePadre, valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        return nodoActual;
    }



    private NodoBinario<K,V> reconstruirConPreOrden(List<K> clavesInOrden, List<V> valoresInOrden,
                                                    List<K> clavesPreOrden, List<V> valoresPreOrden) {
        if (clavesInOrden.isEmpty()) {
            return NodoBinario.nodoVacio();
        }

        int posicionDeClavePadre = 0;
        K clavePadre = clavesPreOrden.get(posicionDeClavePadre);
        V valorPadre = valoresPreOrden.get(posicionDeClavePadre);
        int posicionDeClavePadreInOrden = this.posicionDeClave(clavePadre, clavesInOrden);

        //para armar la rama izquierda
        List<K> clavesInOrdenIzq = clavesInOrden.subList(0, posicionDeClavePadreInOrden);
        List<V> valoresInOrdenIzq = valoresInOrden.subList(0, posicionDeClavePadreInOrden);
        List<K> clavesPreOrdenIzq = clavesPreOrden.subList(1, posicionDeClavePadreInOrden + 1);
        List<V> valoresPreOrdenIzq = valoresPreOrden.subList(1, posicionDeClavePadreInOrden + 1);
        NodoBinario<K,V> hijoIzquierdo = reconstruirConPreOrden(clavesInOrdenIzq, valoresInOrdenIzq,
                clavesPreOrdenIzq, valoresPreOrdenIzq);
        // para armar la rama derecha
        List<K> clavesInOrdenDer = clavesInOrden.subList(posicionDeClavePadreInOrden + 1, clavesInOrden.size());
        List<V> valoresInOrdenDer = valoresInOrden.subList(posicionDeClavePadreInOrden + 1, clavesInOrden.size());
        List<K> clavesPreOrdenDer = clavesPreOrden.subList(posicionDeClavePadreInOrden + 1, clavesInOrden.size());
        List<V> valoresPreOrdenDer = valoresPreOrden.subList(posicionDeClavePadreInOrden + 1, clavesInOrden.size());
        NodoBinario<K,V> hijoDerecho = reconstruirConPreOrden(clavesInOrdenDer, valoresInOrdenDer,
                clavesPreOrdenDer, valoresPreOrdenDer);
        //armando el nodo Actual
        NodoBinario<K,V> nodoActual = new NodoBinario<>(clavePadre, valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        return nodoActual;
    }

    private int posicionDeClave(K clave, List<K> listaClaves) {
        for (int i = 0; i < listaClaves.size(); i++) {
            K claveActual = listaClaves.get(i);
            if (claveActual.compareTo(clave) == 0) {
                return i;
            }
        }
        return -1;
    }


    //14. Implemente un método privado que reciba un nodo binario de un árbol binario y que
    //retorne cual sería su sucesor inorden de la clave de dicho nodo.

    private  NodoBinario<K,V> sucesorInOrden(NodoBinario<K,V> nodoABuscar) {

        List<K> recorrido = this.recorridoEnInOrden();
        int posicionDeClaveEnRecorrido = recorrido.indexOf(nodoABuscar.getClave());

        if (recorrido.indexOf(nodoABuscar.getClave()) == -1 || posicionDeClaveEnRecorrido == 0) {
            return null;
        }

        K claveABuscar = recorrido.get(recorrido.indexOf(nodoABuscar.getClave()) - 1);
        NodoBinario<K,V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0){
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0){
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual;
            }
        }

        return null;
    }

    //15. Implemente un método privado que reciba un nodo binario de un árbol binario y que
    //retorne cuál sería su predecesor inorden de la clave de dicho nodo.
    private NodoBinario<K,V> predecesorInOrden(NodoBinario<K,V> nodoABuscar) {
        List<K> recorrido = this.recorridoEnInOrden();
        int posicionDeClaveEnRecorrido = recorrido.indexOf(nodoABuscar.getClave());

        if (recorrido.indexOf(nodoABuscar.getClave()) == -1 || posicionDeClaveEnRecorrido == recorrido.size() - 1) {
            return null;
        }

        K claveABuscar = recorrido.get(recorrido.indexOf(nodoABuscar.getClave()) + 1);
        NodoBinario<K,V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0){
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0){
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual;
            }
        }

        return null;

    }

    //16. Implemente un método que retorne la menor llave en un árbol binario de búsqueda

    public K menorLlave() {
        NodoBinario<K,V> nodoActual = this.raiz;
        K claveARetornar = null;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            claveARetornar = nodoActual.getClave();
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return claveARetornar;
    }

    //21. Implemente un método que retorne verdadero si un árbol binario esta lleno. Falso en caso
    //contrario.
    public boolean esArbolLleno() {
        if (this.esArbolVacio()) {
            return false;
        }
        int cantidadDeNodos = this.size();
        int nivel = this.nivel();
        int sizeConFormula = (int) (Math.pow(2, nivel) - 1);
        if (cantidadDeNodos == sizeConFormula) {
            return true;
        }
        return false;
    }

}
