package org.example.fx_forint.helper;


import org.example.fx_forint.models.Deviza;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import soap.MNBArfolyamServiceSoap;
import soap.MNBArfolyamServiceSoapImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MNBSoapClientHelper {

    private final MNBArfolyamServiceSoap service;

    public MNBSoapClientHelper() {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        this.service = impl.getCustomBindingMNBArfolyamServiceSoap();
    }

    /**
     * Visszaadja az elérhető devizanemeket, első és utolsó dátumot.
     * @return MNB adatok xml
     */
    public String getInfo() {
        try {
            return service.getInfo();
        } catch (Exception e) {
            return "Hiba az MNB információk lekérdezésekor: " + e.getMessage();
        }
    }

    /**
     * Aktuális árfolyamok
     * @return MNB árfolyamok xml
     */
    public String getCurrentExchangeRates() {
        try {
            String currentExchangeRatesXML = service.getCurrentExchangeRates();
            parseXml(currentExchangeRatesXML);
            return currentExchangeRatesXML;
        } catch (Exception e) {
            return "Hiba az aktuális árfolyamok lekérdezésekor: " + e.getMessage();
        }
    }

    /**
     * XML parsolás
     * @param xml
     * @return Document
     * @throws Exception
     */
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

    /**
     * Egy listát ad vissza az elérhető devizákból
     * @param xmlContent
     * @return
     * @throws Exception
     */
    public static List<Deviza> parseDevizaFromXML(String xmlContent) throws Exception {
        List<Deviza> currenciesList = new ArrayList<>();

        Document document = parseXml(xmlContent);
        NodeList currNodes = document.getElementsByTagName("Curr");
        for (int i = 0; i < currNodes.getLength(); i++) {
            String currency = currNodes.item(i).getTextContent();
            currenciesList.add(new Deviza(currency));
        }
        return currenciesList;
    }
}
