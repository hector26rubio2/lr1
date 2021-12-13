/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.control;

import back.entidades.Gramatica;
import back.persistencia.GramaticaException;

import back.persistencia.ManejoJSON;
import java.util.ArrayList;
import java.util.HashMap;

public class ControlGramatica {

    private Gramatica gramatica;
    private ManejoJSON manejoJSON;
   

    public ControlGramatica() {
        this.manejoJSON = new ManejoJSON();
        this.gramatica = null;
    
    }

    public void cargarGramatica(String path) throws GramaticaException {
        Gramatica gramatica;
        try {
            gramatica = manejoJSON.cargarGramaticaJSOn(path);

            if (gramatica != null) {
                this.gramatica = gramatica;
            } else {
                throw new GramaticaException("La Gramática esta vacia");
            }
        } catch (GramaticaException ex) {
            throw ex;
        }
    }

   

    public boolean hayGramatica() {
        return this.gramatica != null;
    }

    public void datosGramatica() {
        gramatica.getGramatica();
    }

   


  
    
}
