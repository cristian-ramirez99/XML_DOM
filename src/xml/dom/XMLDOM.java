/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dom;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author cristian
 */
public class XMLDOM {

    private Document doc = null;
    private File file;

    XMLDOM(File file) {
        this.file = file;
        abrirFile();
    }

    private void abrirFile() {
        try {
            //Se crea un objeto DocumentBuiderFactory.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Indica que el modelo DOM no debe contemplar los comentarios //que tenga el XML.
            factory.setIgnoringComments(true);
            //Ignora los espacios en blanco que tenga el documento
            factory.setIgnoringElementContentWhitespace(true);
            //Se crea un objeto DocumentBuilder para cargar en él la //estructura de árbol DOM a partir del XML
            //seleccionado.
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Interpreta (parsear) el documento XML (file) y genera el DOM //equivalente.
            doc = builder.parse(file);
            //Ahora doc apunta al árbol DOM listo para ser recorrido.
        } catch (Exception e) {
            System.out.println("El fichero no se pudo crear");
        }
    }

    public String recorrerYMostrar() {
        String datos_nodo[] = null;
        String salida = "";
        Node node;
        //Obtiene el primero nodo del DOM (primer hijo)
        Node raiz = doc.getFirstChild();
        //Obtiene una lista de nodos con todos los nodos hijo del raíz.
        NodeList nodelist = raiz.getChildNodes();
        //Procesa los nodos hijo
        for (int i = 0; i < nodelist.getLength(); i++) {
            node = nodelist.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Es un nodo libro
                datos_nodo = procesarLibro(node);
                salida = salida + "\n " + "Publicado en: " + datos_nodo[0];
                salida = salida + "\n " + "El autor es: " + datos_nodo[2];
                salida = salida + "\n " + "El título es: " + datos_nodo[1];
                salida = salida + "\n " + "La categoria es: " + datos_nodo[3];

            }
        }
        return salida;
    }

    private String[] procesarLibro(Node n) {
        String datos[] = new String[4];
        Node ntemp = null;
        int contador = 1;
        //Obtiene el valor del primer atributo del nodo (uno en este ejemplo)
        datos[0] = n.getAttributes().item(0).getNodeValue();
        //Obtiene los hijos del Libro (titulo y autor)
        NodeList nodos = n.getChildNodes();
        for (int i = 0; i < nodos.getLength(); i++) {
            ntemp = nodos.item(i);
            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                //IMPORTANTE: para obtener el texto con el título y autor se accede al nodo //TEXT hijo de ntemp y se
                //saca su valor.
                datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();
                contador++;
            }
        }
        return datos;
    }

    public void actualizarLibro(String tituloAntiguo, String tituloNuevo, String fechaPublicado, String autor, String categoria) {
        try {
            //Hacemos Nodelist de libro
            NodeList nodo = doc.getElementsByTagName("libro");

            for (int i = 0; i < nodo.getLength(); i++) {
                //Creamos elemento de libro
                Element libro = (Element) nodo.item(i);

                //Actualizamos el libro con titulo === tituloAntiguo
                if (libro.getElementsByTagName("titulo").item(0).getTextContent()
                        .equals(tituloAntiguo)) {
                    
                    //Actualizamos el atributo fechaPublicado
                    libro.setAttribute("fechaPublicado", fechaPublicado);

                    //Actualizamos el titulo
                    libro.getElementsByTagName("titulo").item(0)
                            .setTextContent(tituloNuevo);

                    //Actualizamos el autor
                    libro.getElementsByTagName("autor").item(0)
                            .setTextContent(autor);

                    //Actualizamos la categoria
                    libro.getElementsByTagName("categoria").item(0)
                            .setTextContent(categoria);
                }
            }
            //Actualimos el fichero 
            actualizarFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void actualizarFile() {
        try {
            //Especifica el formato de salida
            OutputFormat format = new OutputFormat();

            //Especifica que la salida esté indentada.
            format.setIndenting(true);

            //Escribe el contenido en el FILE
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(file), format);
            serializer.serialize(doc);
        } catch (Exception e) {
            System.out.println("No se pudo actualizar el fichero");
        }
    }
}
