/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.persistencia;

import back.entidades.Gramatica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ManejoJSON {

    public ManejoJSON() {
    }

    public Gramatica cargarGramaticaJSOn(String path) throws GramaticaException {
        JSONParser parser = new JSONParser();
        Gramatica gramatica = null;

        try {
            Object obj = parser.parse(new FileReader(path));
            JSONObject gramaticaJSON = (JSONObject) obj;
            String lamda = (String) gramaticaJSON.get("lamda");
            String produccionInicial = (String) gramaticaJSON.get("PInicial");
            gramatica = new Gramatica(produccionInicial, lamda);
            JSONArray terminales = (JSONArray) gramaticaJSON.get("T");
            Iterator<String> iterator = terminales.iterator();
            while (iterator.hasNext()) {
                gramatica.agregarTerminal(iterator.next());
            }
            JSONArray producciones = (JSONArray) gramaticaJSON.get("producciones");

            for (Object produccion : producciones) {
                obj = parser.parse(produccion.toString());
                JSONObject produccionJSON = (JSONObject) obj;
                JSONArray produccionArray = (JSONArray) produccionJSON.get("produccion");
                Iterator<String> produccionIterador = produccionArray.iterator();
                ArrayList<String> datoproduccion = new ArrayList<>();
                while (produccionIterador.hasNext()) {
                    datoproduccion.add(produccionIterador.next());
                }
                gramatica.agregarNoTerminal((String) produccionJSON.get("NT"));
                gramatica.agregarProduccion((String) produccionJSON.get("NT"), datoproduccion);
            }
            obj = parser.parse(producciones.get(0).toString());
            JSONObject produccionJSON = (JSONObject) obj;
            gramatica.setPini((String) produccionJSON.get("NT"));
        } catch (FileNotFoundException e) {
            throw new GramaticaException("No se Encontro un Archivo");
        } catch (IOException e) {
        } catch (org.json.simple.parser.ParseException ex) {
            throw new GramaticaException("La Estructura del Documento es Incompatible:");
        }
        return gramatica;

    }

}
