/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.inf310sb.ed2202101.ui;

import bo.edu.uagrm.ficct.inf310sb.ed2202101.arboles.*;
import bo.edu.uagrm.ficct.inf310sb.ed2202101.excepciones.ExcepcionClaveNoExiste;
import bo.edu.uagrm.ficct.inf310sb.ed2202101.excepciones.ExcepcionOrdenInvalido;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Hp 240
 */
public class TestArboles {
    public static <IArbolBinarioBusqueda> void main(String argumentos[]) throws ExcepcionClaveNoExiste, ExcepcionOrdenInvalido {

        IArbolBusqueda<Integer,String> arbolDePrueba = new ArbolBinarioBusqueda<>();
        int opcion = 0;
        int ordenDelArbol = 0;
        int tipoDeArbol = 0;
        int subOpcion = 0;
        int nivel = 0;
        Integer clave = 0;
        String valor = "";
        Scanner teclado = new Scanner(System.in);

        System.out.println("ARBOLES DE BUSQUEDA");
        System.out.println("1.  ARBOL BINARIO DE BUSQUEDA");
        System.out.println("2.  AVL");
        System.out.println("3.  M-Vias");
        System.out.println("4.  Arbol B");
        System.out.println("Seleccione con que arbol desea trabajar");
        tipoDeArbol = teclado.nextInt();


        switch (tipoDeArbol) {
            case 1:
                arbolDePrueba = new ArbolBinarioBusqueda<>();
                break;

            case 2:
                arbolDePrueba = new AVL<>();
                break;

            case 3:
                System.out.println("Digite el Orden del Arbol");
                ordenDelArbol = teclado.nextInt();
                arbolDePrueba = new ArbolMViasBusqueda<>(ordenDelArbol);
                break;

            case 4:
                System.out.println("Digite el Orden del Arbol");
                ordenDelArbol = teclado.nextInt();
                arbolDePrueba = new ArbolB<>(ordenDelArbol);
                break;    
        }


        do {
            System.out.println("MENU");
            System.out.println("1.  INSERTAR");
            System.out.println("2.  BUSCAR");
            System.out.println("3.  ELIMINAR");
            System.out.println("4.  RECORRIDO IN ORDEN");
            System.out.println("5.  RECORRIDO PRE ORDEN");
            System.out.println("6.  RECORRIDO POST ORDEN");
            System.out.println("7.  RECORRIDO POR NIVELES");
            System.out.println("8.  NIVEL");
            System.out.println("9.  ALTURA");
            System.out.println("10.  VACIAR");
            if (tipoDeArbol <= 2) {
                System.out.println("11.  CANTIDAD DE NODOS CON AMBOS HIJOS");
                System.out.println("12.  CANTIDAD DE NODOS CON AMBOS HIJOS SOLO EN EL NIVEL N");
                System.out.println("13.  CANTIDAD DE NODOS CON UN SOLO HIJO ANTES DEL NIVEL N");
                System.out.println("14. CANTIDAD  DE NODOS EN EL ARBOL CON RECORRIDO INORDEN");
                System.out.println("15. RECOSTRUIR EL ARBOL");
                System.out.println("16. METODO QUE DEVUELVE LA MENOR LLAVE DEL ARBOL ");
                System.out.println("17. METODO QUE DEVUELVE SI EL ARBOL ESTA LLENO ");
            } else {
                System.out.println("18. METODO QUE DEVUELVE LA MAYOR LLAVE DE UN ARBOL M-VIAS ");
                System.out.println("19. METODO QUE DEVUELVE SI SOLO HAY HOJAS EN EL ULTIMO NIVEL ");
                System.out.println("20. METODO QUE DEVUELVE SI SON ARBOLES SIMILARES ");
            }
            System.out.println("21. SALIR");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("DIGITE LA CLAVE: ");
                    clave = teclado.nextInt();
                    System.out.println("DIGITE EL VALOR: ");
                    valor = teclado.next();
                    arbolDePrueba.insertar(clave, valor);
                    break;

                case 2:
                    System.out.println("DIGITE LA CLAVE A BUSCAR");
                    clave = teclado.nextInt();
                    System.out.println("el valor de la clave es: " + arbolDePrueba.buscar(clave));
                    break;

                case 3:
                    System.out.println("DIGITE LA CLAVE A ELIMINAR: ");
                    clave = teclado.nextInt();
                    valor = arbolDePrueba.eliminar(clave);
                    System.out.println("el valor de la clave eliminada es: " + valor);
                    break;
                case 4:
                    System.out.println("RECORRIDO INORDEN: " + arbolDePrueba.recorridoEnInOrden());
                    break;
                case 5:
                    System.out.println("RECORRIDO PRE-ORDEN: " + arbolDePrueba.recorridoEnPreOrden());
                    break;
                case 6:
                    System.out.println("RECORRIDO POST-ORDEN: " + arbolDePrueba.recorridoEnPostOrden());
                    break;
                case 7:
                    System.out.println("RECORRIDO POR NIVELES: " + arbolDePrueba.recorridoPorNiveles());
                    break;
                case 8:
                    System.out.println("EL NIVEL DEL ARBOL ES: " + arbolDePrueba.nivel());
                    break;
                case 9:
                    System.out.println("LA ALTURA DEL ARBOL ES: " + arbolDePrueba.altura());
                    break;
                case 10:
                    arbolDePrueba.vaciar();
                    System.out.println("EL ARBOL SE VACIO");
                    break;
                case 11:
                    if (tipoDeArbol <= 2 ) {
                        System.out.println("SELECCIONE CON QUE METODO RESOLVER");
                        System.out.println("1.  ITERATIVO");
                        System.out.println("2.  RECURSIVO");
                        subOpcion = teclado.nextInt();
                        switch (subOpcion) {
                            case 1:
                                System.out.println("la cantidad de nodos con ambos hijos en el arbol es: " +
                                        ((ArbolBinarioBusqueda) arbolDePrueba).cantidadDeNodosConAmbosHijosIte());
                                break;
                            case 2:
                                System.out.println("la cantidad de nodos con ambos hijos en el arbol es: " +
                                        ((ArbolBinarioBusqueda) arbolDePrueba).cantidadDeNodosConAmbosHijosRec());
                        }
                    } else {
                        System.out.println("Metodo no Soportado para arboles multivias");
                    }
                    break;
                case 12:
                    System.out.println("digite el nivel en que desea buscar");
                    nivel = teclado.nextInt();
                    System.out.println("SELECCIONE EL METODO");
                    System.out.println("1.  ITERATIVO");
                    System.out.println("2.  RECURSIVO");
                    subOpcion = teclado.nextInt();
                    switch (subOpcion) {
                        case 1:
                            System.out.println("la cantidad de nodos con ambos hijos en el niel: " + nivel + " es " +
                                    ((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeNodosCoAmbosHijosEnNivelIte(nivel));
                            break;
                        case 2:
                            System.out.println("la cantidad de nodos con ambos hijos en el nivel: " + nivel + " es " +
                                    ((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeNodosConAmbosHijosEnNivel(nivel));
                    }
                    break;
                case 13:
                    System.out.println("digite el nivel ");
                    nivel = teclado.nextInt();
                    System.out.println("la cantidad de nodos con un solo hijo antes del nivel  " + nivel + " es " +
                            ((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeNodosConUnHijoEnNivel(nivel));
                    break;

                case 14:
                    System.out.println("el numero de nodos con recorrido InOrden es: "+
                            ((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeNodosDelArbolBinarioConRecInOrden());
                    break;

                case 15:
                    List<Integer> listaIn = arbolDePrueba.recorridoEnInOrden();
                    List<Integer> listaPre = arbolDePrueba.recorridoEnPreOrden();
                    List<String> valorIn = new LinkedList<>();
                    List<String> valorPre = new LinkedList<>();
                    for (int i = 0; i < listaIn.size(); i++) {
                        valorIn.add(arbolDePrueba.buscar(listaIn.get(i)));
                        valorPre.add(arbolDePrueba.buscar(listaPre.get(i)));
                    }
                    arbolDePrueba.vaciar();
                    arbolDePrueba = new ArbolBinarioBusqueda<>(listaIn, valorIn, listaPre, valorPre, true);
                    break;
                case 16:
                    System.out.println("la menor clave del arbol es:  " +
                            ((ArbolBinarioBusqueda)arbolDePrueba).menorLlave());
                    break;

                case 17:
                    System.out.println("El arbol esta lleno:  " + ((ArbolBinarioBusqueda)arbolDePrueba).esArbolLleno());
                    break;
                case 18:
                    System.out.println("La mayor llave de el arbol es:  " + ((ArbolMViasBusqueda)arbolDePrueba).mayorLlaveArbol());
                    break;
                case 19:
                    System.out.println("El arbol solo tiene hojas en el ultimo nivel:  " + ((ArbolMViasBusqueda)arbolDePrueba).hayHojasSoloEnUltimoNivel());
                    break;
                case 20:
                    System.out.println("Cree el otro arbol");
                    IArbolBusqueda<Integer,String> arbolPrueba2 = new ArbolMViasBusqueda<>(ordenDelArbol);
                    System.out.println("DIGITE LA CLAVE: ");
                    clave = teclado.nextInt();
                    System.out.println("DIGITE EL VALOR: ");
                    valor = teclado.next();
                    arbolPrueba2.insertar(clave, valor);
                    System.out.println("son similares:  " + ((ArbolMViasBusqueda)arbolDePrueba).sonSimilares((ArbolMViasBusqueda) arbolPrueba2));
                    break;
            }

        }

        while(opcion != 21);




    }
    
}
