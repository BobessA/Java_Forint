package org.example.fx_forint.helper;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import soap.MNBArfolyamServiceSoap;
import soap.MNBArfolyamServiceSoapImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class MNBSoapClientHelper {

    private final MNBArfolyamServiceSoap service;

    public MNBSoapClientHelper() {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        this.service = impl.getCustomBindingMNBArfolyamServiceSoap();
    }

    public String getInfo() {
        try {
            return service.getInfo();
        } catch (Exception e) {
            return "Hiba az MNB információk lekérdezésekor: " + e.getMessage();
        }
    }

    public String getCurrentExchangeRates() {
        try {
            String currentExchangeRatesXML = service.getCurrentExchangeRates();
            parseXml(currentExchangeRatesXML);
            return currentExchangeRatesXML;
            //return service.getCurrentExchangeRates();
        } catch (Exception e) {
            return "Hiba az aktuális árfolyamok lekérdezésekor: " + e.getMessage();
        }
    }

    private static Document parseXml(String xml) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
