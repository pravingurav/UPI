package com.ultracash.upi.utility;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class SignatureUtil<T> {
	
	private Class<T> type;
	
	public SignatureUtil(Class<T> type) {
	    this.type = type;
    }

	public String getSignedXMLString(T object) {
		String xmlString = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(type);
			Marshaller m = jc.createMarshaller();
			DOMResult res = new DOMResult();
	        m.marshal( new JAXBElement<T>(new QName("http://npci.org/upi/schema/", type.getSimpleName()), type, null, object), res);
	        
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();
            document = (Document)res.getNode();
            
            Document signedDoucument = SignatureGenerationUtil.signDoc(document);
            
            DOMSource source = new DOMSource(signedDoucument);
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            serializer.transform(source, new StreamResult(writer));
            xmlString = writer.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
		return xmlString;
	}
}
