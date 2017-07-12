import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;



public class RemoveNodesFromXMLFile {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        //construct the xpath expression to find the nodes to remove
        String xpathExprToRemove = "";
        String filePath = "";
        String resultsFilePath = "";

        //load the file
        DocumentBuilderFactory  docFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filePath);

        //instantiate xpath
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExprToRemove);


        NodeList nodeList = (NodeList) expression.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);

        //remove nodes
        for (int i=0; i<nodeList.getLength(); i++) {

            Node nodeToRemove = nodeList.item(i);

            nodeToRemove.getParentNode().removeChild(nodeToRemove);

            //Print nodes
            Document document = nodeToRemove.getOwnerDocument();
            DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
            LSSerializer serializer = domImplLS.createLSSerializer();
            serializer.getDomConfig().setParameter("xml-declaration", false); //by default its true, so set it to false to get String without xml-declaration
            String str = serializer.writeToString(nodeToRemove);
            System.out.println(str);
        }

        //Print the results
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.transform(new DOMSource(doc), new StreamResult(new File(resultsFilePath)));

    }


}
