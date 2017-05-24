package ru.ncedu.java.SolomatinAA.XPathCaller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Artem Solomatin on 05.05.17.
 * NetCracker
 */
public class XPathCallerImpl implements XPathCaller {
    private Element[] nodeListToElementArray(NodeList nodeList){
        Element[] elements = new Element[nodeList.getLength()];
        for (int i=0;i<nodeList.getLength();++i) {
            elements[i] = (Element) nodeList.item(i);
        }
        return elements;
    }

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
        return nodeListToElementArray(nodeList) ;
    }

    @Override
    public String getHighestPayed(Document src, String docType) {
        XPath path=XPathFactory.newInstance().newXPath();
        String xpathEmployeeSal=".//employee/sal";
        NodeList nodeList= null;
        String boss=null;
        try {
            nodeList = (NodeList) path.evaluate(xpathEmployeeSal,src, XPathConstants.NODESET);
            Double max=Double.valueOf(nodeList.item(0).getTextContent());
            int index=0;
            for (int i=0;i<nodeList.getLength();i++){
                Double temp = Double.valueOf(nodeList.item(i).getTextContent());
                if(max<temp){
                    max=temp;
                    index=i;
                }
            }
            NodeList names = (NodeList)path.evaluate(".//employee/ename",src,XPathConstants.NODESET);
            boss=names.item(index).getTextContent();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return boss;
    }

    @Override
    public String getHighestPayed(Document src, String deptno, String docType) {

        NodeList nodeListInRightDeptno=null;//список челове по заданному депртаменту
        XPath path= XPathFactory.newInstance().newXPath();

        int index=0;
        try {
            nodeListInRightDeptno=(NodeList)path.evaluate(".//employee[@deptno = '"+deptno+"']",src,XPathConstants.NODESET);
            Double max=Double.valueOf(nodeListInRightDeptno.item(0).getChildNodes().item(7).getTextContent());
            for (int i=0;i<nodeListInRightDeptno.getLength();i++){
                nodeListInRightDeptno.item(i);
                Double temp=Double.valueOf(nodeListInRightDeptno.item(i).getChildNodes().item(7).getTextContent());
                if(max<temp){
                    max=temp;
                    index=i;
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return nodeListInRightDeptno.item(index).getChildNodes().item(1).getTextContent();
    }

    @Override
    public Element[] getTopManagement(Document src, String docType) {
        XPath path=XPathFactory.newInstance().newXPath();
        NodeList nodeList = null;

        try {
            if(docType.equals("emp-hier")){
                // System.out.println("rere");

                nodeList = (NodeList) path.compile("/employee").evaluate(src, XPathConstants.NODESET);
                // System.out.println(nodeList.getLength());
            }
            if(docType.equals("emp")) {
                nodeList = (NodeList) path.evaluate("/content/emp/employee[not(@mgr)]", src, XPathConstants.NODESET);
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return nodeListToElementArray(nodeList);
    }

    @Override
    public Element[] getOrdinaryEmployees(Document src, String docType) {
        XPath xPath =  XPathFactory.newInstance().newXPath();

        String expression;
        if(docType.equals("emp-hier"))
            expression = "//employee[not(./employee)]";
        else
            expression = "//employee[not(@empno = (//@mgr))]";

        NodeList nodeList = null;

        try {
            nodeList = (NodeList) xPath.compile(expression).evaluate(src, XPathConstants.NODESET);
            // System.out.println(nodeList.getLength());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return nodeListToElementArray(nodeList);
    }

    @Override
    public Element[] getCoworkers(Document src, String empno, String docType) {
        XPath xPath =  XPathFactory.newInstance().newXPath();

        String expression;
        if(docType.equals("emp-hier"))
            expression = "//employee[@empno = '" + empno + "']/ancestor::*[1]/child::employee[@empno != '" + empno + "']";
        else
            expression = "//employee[@mgr = //employee[@empno = //employee[@empno = '" + empno + "']/@mgr]/@empno and @empno != '" + empno + "']";

        NodeList nodeList = null;

        try {
            nodeList = (NodeList) xPath.compile(expression).evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return nodeListToElementArray(nodeList);
    }
}
