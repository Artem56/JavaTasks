package ru.ncedu.java.SolomatinAA.SimpleXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by Artem Solomatin on 05.05.17.
 * NetCracker
 */
public class SimpleXMLImpl implements SimpleXML {
    private String rootElement;


    @Override
    public String createXML(String tagName, String textNode) {
        Element root;
        StringWriter stringWriter=new StringWriter();
        try {
            Document documentXML=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            root=documentXML.createElement(tagName);
            Node node=documentXML.createTextNode(textNode);
            root.appendChild(node);
            documentXML.appendChild(root);
            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"Y");
            transformer.transform(new DOMSource(documentXML),new StreamResult(stringWriter));
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

    @Override
    public String parseRootElement(InputStream xmlStream) throws SAXException {
        rootElement="";
        DefaultHandler handler= new DefaultHandler(){
            public void startElement(String url, String localName, String qname, Attributes attr){
                rootElement = qname;
            }
        };
        try {
            SAXParser parser= SAXParserFactory.newInstance().newSAXParser();
            parser.parse(xmlStream, handler);
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return rootElement;
    }
}
