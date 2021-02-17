/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dom;

import java.io.File;

/**
 *
 * @author cristian
 */
public class MiMain {

    public static void main(String[] args) {
        //Creamos file 
        File file = new File("fichero.xml");

        //Instanciamos la clase para gestionar ficheros XML
        XMLDOM app = new XMLDOM(file);

        app.recorrerYMostrar();
        app.actualizarLibro("TituloAntiguo", "TituloNuevo","2021", "Cristian Ramirez", "Autoayuda");
        app.recorrerYMostrar();
    }
}
