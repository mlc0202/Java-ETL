/**
 * 
 */
package cl.uchile.datos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * ETL (Extract-Transform-Load) significa extraer, transformar y cargar.
 * Esta clase extrae datos de alguna fuente (xml, json), los transforma
 * y los carga en alg�n output especificado.
 * 
 * @author Avengers
 */
public abstract class AbstractETL {

	XMLInputFactory inputFactory;
	XMLStreamReader reader;
	XMLOutputFactory outputFactory;
	XMLStreamWriter writer;
	ArrayList<XMLStreamWriter> writers;
	

	/**
	 * Constructor ETL abstracto, configura el reader y writer. Escribe en varios outputs.
	 * 
	 * @throws XMLStreamException Excepci�n lanzada por falla de la librer�a StAX.
	 * @throws FileNotFoundException Excepci�n lanzada al no encontrar el archivo.
	 * @see <a href="http://docs.oracle.com/javase/7/docs/api/javax/xml/stream/XMLStreamReader.html">XMLStreamReader</a>
	 * @see <a href="http://docs.oracle.com/javase/7/docs/api/javax/xml/stream/XMLStreamWriter.html">XMLStreamWriter</a>
	 */
	public AbstractETL(String inputFilename, ArrayList<String> outputFilenames) throws XMLStreamException, FileNotFoundException {
		this.inputFactory = XMLInputFactory.newInstance();
		this.reader = this.inputFactory.createXMLStreamReader(new FileInputStream(inputFilename), "UTF8");
		this.outputFactory = XMLOutputFactory.newInstance();
		for(String output : outputFilenames) {
			XMLStreamWriter aux = this.outputFactory.createXMLStreamWriter(new FileOutputStream(output), "UTF8");
			this.writers.add(aux);
		}
	}

	/**
	 * Constructor ETL abstracto, configura el reader y writer. Escribe un �nico output.
	 * 
	 * @throws XMLStreamException Excepci�n lanzada por falla de la librer�a StAX.
	 * @throws FileNotFoundException Excepci�n lanzada al no encontrar el archivo.
	 * @see <a href="http://docs.oracle.com/javase/7/docs/api/javax/xml/stream/XMLStreamReader.html">XMLStreamReader</a>
	 * @see <a href="http://docs.oracle.com/javase/7/docs/api/javax/xml/stream/XMLStreamWriter.html">XMLStreamWriter</a>
	 */
	public AbstractETL(String inputFilename, String outputFilename) throws XMLStreamException, FileNotFoundException {
		this.inputFactory = XMLInputFactory.newInstance();
		this.reader = this.inputFactory.createXMLStreamReader(new FileInputStream(inputFilename), "UTF8");
		this.outputFactory = XMLOutputFactory.newInstance();
		this.writer = this.outputFactory.createXMLStreamWriter(new FileOutputStream(outputFilename), "UTF8");
	}

	/**
	 * Lee y extrae los datos del input al output especificado.
	 * 
	 * @throws Exception Excepci�n lanzada en caso de error.
	 */
	public abstract void parse() throws Exception;

}
