/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.entidades;

/**
 * modificaón para ver mas datos metec en un arrelgo y concatenarlso en el
 * hashmap y para concatenar canviar y asi verificar toda la cadena que no sea
 * igual
 */
import back.persistencia.GramaticaException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gramatica {

    private ArrayList<String> nt;
    private ArrayList<String> t;
    private String pini;
    private String ntEx;
    private String caracterLamda;
    private HashMap<String, ArrayList<String>> gramatica;
    private HashMap<Integer, HashMap<String, ArrayList<String>>> estados;
    private int estado;
    private int rEstado;
    private HashMap<String, String> aceptar;
    private HashMap<Integer, String> r;

    public Gramatica(String ntEx, String caracterLamda) {
        this.t = new ArrayList<>();
        this.nt = new ArrayList<>();
        this.gramatica = new HashMap<>();
        this.estado = 0;
        this.rEstado = 1;
        this.caracterLamda = caracterLamda;
        estados = new HashMap<>();
        this.ntEx = ntEx;
        this.r = new HashMap<>();
        this.aceptar = new HashMap<>();

    }

    public void agregarTerminal(String terminal) {
        this.t.add(terminal);
    }

    public void agregarProduccion(String noTerminal, ArrayList<String> producciones) {
        this.gramatica.put(noTerminal, producciones);
    }

    // nota  reformar codigo  tanto cargar json  como generar estaados se mira 
    //apartir de la produccion y el sisguente  y se realiza el cambio  del siguiente 
    private String correrComa(String cadena, int i) {

        cadena = cadena.replace(".", "");
        cadena = cadena.substring(0, i + 1)
                + "." + cadena.substring(i + 1, cadena.length());
        return cadena;
    }

    public void setPini(String pIni) {
        this.pini = pIni;
    }

    public void getGramatica() {

        obtenerEstados();
    }

    public void obtenerEstados() {
        try {
            extenderGramatica(gramatica);
            HashMap<String, ArrayList<String>> gramatica
                    = (HashMap<String, ArrayList<String>>) this.gramatica.clone();
            ArrayList<String> producciones = obtnerProducciones(ntEx);
            obtenerEstados(producciones, ntEx);

            for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> estados : estados.entrySet()) {
                System.out.println("Estado : " + estados.getKey());
                System.out.println("----");
                getGramatica(estados.getValue());
                System.out.println("----");
            }
//            System.out.println(this.r.size());

//            for (Map.Entry<Integer, String> r : this.r.entrySet()) {
//                System.out.println(String.valueOf(r.getKey()) + "  " + r.getValue());
//
//            }
//            System.out.println("--");
//            for (Map.Entry<String, String> r : this.aceptar.entrySet()) {
//                System.out.println(String.valueOf(r.getKey()) + " " + r.getValue());
//
//            }
        } catch (GramaticaException ex) {
            System.out.println("f");
        }

    }

    private void obtenerEstados(ArrayList<String> produccionesG, String noTerminal) throws GramaticaException {
        HashMap<String, ArrayList<String>> estadoG = new HashMap<>();

        if (noTerminal.equals(ntEx)) {

            actualizarCadenasFull(produccionesG, "$" ,noTerminal);
            String cadena = produccionesG.get(0);
            estadoG.put(ntEx, (ArrayList<String>) produccionesG.clone());
            produccionesG.clear();
            String sig = cadenaSigientePunto(cadena);
            produccionesG = obtnerProducciones(sig);
            actualizarCadenasFull(produccionesG, "$",sig);
            estadoG.put(sig, (ArrayList<String>) produccionesG.clone());
            for (String datos : produccionesG) {
                sig = cadenaSigientePunto(datos);
                if (isNoTerminal(sig)) {
                    String primeros = obtenerPrimeros(sig);
                    produccionesG = obtnerProducciones(sig);
                    actualizarCadenasFull(produccionesG, primeros,sig);
                    estadoG.put(sig, (ArrayList<String>) produccionesG.clone());
                    produccionesG.clear();
                }
                guardarEstado(estadoG);
                
                for (Map.Entry<String, ArrayList<String>> gramaticaEstado : estadoG.entrySet()) {
                    String key = gramaticaEstado.getKey();
                    ArrayList<String> value = gramaticaEstado.getValue();
                    
                    for (String caden : value) {
                        obtenerEstados(caden, gramaticaEstado.getKey(), obtenerConqueVa(caden));
                    }

                }

            }

        } else {
            if (isNoTerminal(noTerminal)) {
                guardarProduc(produccionesG,estadoG);
       
           
                for (String string : produccionesG) {
                    String cadena = cadenaSigientePunto(string);
                    if(isNoTerminal(cadena))
                    {
                        obtenerEstados(string,cadena , obtenerConqueVa(string));
                    }
                }
            } else {

            }
        }
    }
    private void guardarProduc(ArrayList<String> producciones, HashMap<String, ArrayList<String>> estadoG)
    {
        for (String produccione : producciones) {
            String[] dato = produccione.split(" -> ");
            ArrayList<String> f = new ArrayList<>();
            estadoG.put(dato[0], f);
        }
        for (String produccione : producciones) {
            String[] dato = produccione.split(" -> ");
            
            estadoG.get(dato[0]).add(produccione);
        }
        guardarEstado(estadoG);
    }
    private void guardarEstado(HashMap<String, ArrayList<String>> estadoG)
    {
        estados.put(estado, estadoG);
                estado++;
    }

    private void obtenerEstados(String produccion, String noTerminal, String tiene) throws GramaticaException {
        int i = produccion.lastIndexOf('.');
        produccion = correrComa(produccion, i);
        String dato = produccion.charAt(i + 2) + "";

        if (dato.equals(" ")) {
            HashMap<String, ArrayList<String>> estadoG = new HashMap<>();
            ArrayList<String> s = new ArrayList<>();
            if (noTerminal.equals(ntEx)) {

                this.aceptar.put(noTerminal, produccion);
            } else {
                
                guardarR( produccion);
            }
            
                
                s.add(produccion);
                guardarProduc( s,estadoG);
        } else {
            ArrayList<String> pro = obtnerProducciones(dato);
            actualizarCadenasFull(pro, tiene,dato);
            pro.add(produccion);
       
            obtenerEstados(pro, dato);
           
        }

    }

    private void actualizarCadenasFull(ArrayList<String> producciones, String estado,String noTerminal) {
        agregarPuntoLista(producciones);
        agregarEstadoLista(producciones, estado,noTerminal);
    }

    private void agregarEstadoLista(ArrayList<String> producciones, String estado,String noTerminal) {

        for (int j = 0; j < producciones.size(); j++) {
            producciones.set(j, noTerminal+" -> "+ agregarEstado(producciones.get(j), estado));
        }

    }

    private void agregarPuntoLista(ArrayList<String> producciones) {

        for (int j = 0; j < producciones.size(); j++) {
            producciones.set(j, agregarPunto(producciones.get(j)));
        }

    }

    private String agregarPunto(String cadena) {
        return "." + cadena;
    }

    private String agregarEstado(String cadena, String dato) {
        return cadena + " ," + dato;
    }

    private ArrayList<String> obtnerProducciones(String nT) {
        ArrayList<String> producciones = new ArrayList<>();
        for (String cadena : this.gramatica.get(nT)) {
            producciones.add(cadena);
        }
        return producciones;
    }

    private void extenderGramatica(HashMap<String, ArrayList<String>> gramatica) {
        ArrayList<String> pn = new ArrayList<>();
        pn.add(pini);
        gramatica.put(this.ntEx, pn);
    }

    public void getGramatica(HashMap<String, ArrayList<String>> gramaticaM) {
        for (Map.Entry<String, ArrayList<String>> gramatica : gramaticaM.entrySet()) {
            for (String cadena : gramatica.getValue()) {
                System.out.println( cadena);
            }

        }
    }

    private String cadenaSigientePunto(String cadena) {
        int i = cadena.indexOf(".");
        return cadena.charAt(i + 1) + "";
    }

    private boolean isNoTerminal(String dato) {
        for (String nt : this.nt) {
            if (nt.equals(dato)) {
                return true;
            }
        }
        return false;
    }

    public void agregarNoTerminal(String nt) {
        this.nt.add(nt);
    }

    private String obtenerPrimeros(String sig) throws GramaticaException {
        String primeros = "";
        ArrayList<String> producciones = obtnerProducciones(sig);
        for (String produccione : producciones) {
            int indice = 0;
            char primerCaracter = produccione.charAt(indice);
            if (isTerminal(primerCaracter + "")) {
                primeros = junrarTerminales(primeros, primerCaracter, indice, produccione);
                primeros += "|";
            } else {
                if (isNoTerminal(primerCaracter + "")) {
                    primeros = concatenarPrimeros(
                            obtenerPrimeros(primerCaracter + ""),
                            primeros);

                } else {
                    if (this.caracterLamda.equals(primerCaracter + "")) {
                        indice++;
                        while (indice < produccione.length()) {
                            primerCaracter = produccione.charAt(indice);
                            if (isTerminal(primerCaracter + "")) {
                                primeros = junrarTerminales(primeros, primerCaracter, indice, produccione);
                                primeros += "|";
                                indice = produccione.length();
                            } else {
                                if (this.caracterLamda.equals(primerCaracter + "")) {
                                    indice++;
                                } else {
                                    if (isNoTerminal(primerCaracter + "")) {
                                        primeros = concatenarPrimeros(
                                                obtenerPrimeros(primerCaracter + ""),
                                                primeros);
                                    } else {
                                        throw new GramaticaException("no se reconoce el caracter");
                                    }

                                }

                            }

                        }
                    } else {
                        throw new GramaticaException("no se reconoce el caracter");
                    }
                }
            }
        }

        return limpiarPrimeros(primeros);
    }

    private String concatenarPrimeros(String posibles, String primeros) {
        String melo = "";
        String[] datosPrimerosN = posibles.split("|");
        String[] datosPrimerosO = primeros.split("|");
        ArrayList<String> primerfull = new ArrayList<>();
        for (int j = 0; j < datosPrimerosO.length; j++) {
            String o = datosPrimerosO[j];
            primerfull.add(o);

        }

        for (int i = 0; i < datosPrimerosN.length; i++) {
            String n = datosPrimerosN[i];
            for (String o : primerfull) {
                if (o.equals(n)) {
                    break;
                }
            }
            primerfull.add(n);

        }
        for (int i = 0; i < primerfull.size(); i++) {
            melo += primerfull.get(i);
        }

        return melo;
    }

    private String junrarTerminales(String primeros, char primerCaracter, int indice, String produccione) {
        do {
            primeros += primerCaracter;

            indice++;

            if (indice >= produccione.length()) {
                break;
            }
            primerCaracter = produccione.charAt(indice);
        } while (!isNoTerminal(primerCaracter + "")
                || caracterLamda.equals(primerCaracter + ""));
        return primeros;
    }

    private boolean isTerminal(String dato) {
        boolean estado = false;
        for (String t : this.t) {
            if (t.equals(dato + "")) {
                estado = true;
                break;
            }
        }
        return estado;
    }

    private String limpiarPrimeros(String primeros) {

        int tamaño = primeros.length() - 1;
        String dato = primeros.charAt(tamaño) + "";
        if (dato.equals("|")) {
            primeros = (String) primeros.subSequence(0, tamaño);
        }

        return primeros;
    }

    private String obtenerConqueVa(String cadena) {
        String[] datos = cadena.split(",");
        return datos[1];
    }

    private boolean comparaCadena(String uno, String dos) {
        uno = obtenerCadenaParaComparar(uno);
        dos = obtenerCadenaParaComparar(dos);
        return uno.equals(dos);
    }

    private void guardarR(String produccion) {
        boolean guardar = true;
        for (Map.Entry<Integer, String> r : this.r.entrySet()) {
            if (comparaCadena(r.getValue(), produccion)) {
                guardar = false;
                break;
            }
        }

        if (guardar) {
            this.r.put(this.rEstado, produccion);
            this.rEstado++;
        }
    }

    private String obtenerCadenaParaComparar(String cadena) {
        int inicio = cadena.lastIndexOf(">");
        int fin = cadena.lastIndexOf(",");
        return (String) cadena.subSequence(inicio + 2, fin - 2);

    }
}
