package ru.ncedu.java.SolomatinAA.XPathCaller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SAAJResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Artem Solomatin on 05.05.17.
 * NetCracker
 */
public class XPathCallerImpl implements XPathCaller {
    @Override
    public Element[] getEmployees(Document src, String deptno, String docType) {
        NodeList nodeList=null;
        XPath path= XPathFactory.newInstance().newXPath();
        String xpathDeptno = ".//employee[@deptno = '"+deptno+"']";

        try {
            nodeList=(NodeList)path.evaluate(xpathDeptno,src,XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        Element[] elements = new Element[nodeList.getLength()];
        for (int i=0;i<nodeList.getLength();++i) {
            elements[i] = (Element) nodeList.item(i);
            // System.out.println(elements[i]);
            System.out.println(nodeList.item(i).getTextContent());
            // System.out.println(elements[i].getTextContent());
        }
        return elements;

    }

    @Override
    public String getHighestPayed(Document src, String docType) {
        return null;
    }

    @Override
    public String getHighestPayed(Document src, String deptno, String docType) {
        return null;
    }

    @Override
    public Element[] getTopManagement(Document src, String docType) {
        return new Element[0];
    }

    @Override
    public Element[] getOrdinaryEmployees(Document src, String docType) {
        return new Element[0];
    }

    @Override
    public Element[] getCoworkers(Document src, String empno, String docType) {
        return new Element[0];
    }
}
