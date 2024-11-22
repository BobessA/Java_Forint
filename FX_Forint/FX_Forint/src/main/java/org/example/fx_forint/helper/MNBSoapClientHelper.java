package org.example.fx_forint.helper;


import org.example.fx_forint.models.Deviza;
import org.example.fx_forint.models.DevizaChartItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import soap.MNBArfolyamServiceSoap;
import soap.MNBArfolyamServiceSoapImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MNBSoapClientHelper {

    private final MNBArfolyamServiceSoap service;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    /**
     * Formázott napi árfolyamok
     * @param response
     * @return String a formázott szöveggel
     * @throws ParserConfigurationException
     */
    public static String formatExchangeResult(String response) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getBytes());
        StringBuilder formattedOutput = new StringBuilder();

        try {
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList dayNodes = root.getElementsByTagName("Day");

            for (int i = 0; i < dayNodes.getLength(); i++) {
                Element dayElement = (Element) dayNodes.item(i);
                String date = dayElement.getAttribute("date");
                formattedOutput.append("Árfolyamadatok a ").append(date).append(" dátumról:\n");

                NodeList rateNodes = dayElement.getElementsByTagName("Rate");
                for (int j = 0; j < rateNodes.getLength(); j++) {
                    Element rateElement = (Element) rateNodes.item(j);
                    String unit = rateElement.getAttribute("unit");
                    String currency = rateElement.getAttribute("curr");
                    String value = rateElement.getTextContent().trim();
                    formattedOutput.append("  ")
                            .append(unit).append(" ").append(currency)
                            .append(" = ").append(value).append(" Ft\n");
                }
            }
            return formattedOutput.toString();
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Formázott havi árfolyamok
     * @param response
     * @param currencyFilter
     * @return String a formázott szöveggel
     * @throws ParserConfigurationException
     */
    public static String formatDetailedExchangeResult(String response, String currencyFilter) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getBytes());
        StringBuilder formattedOutput = new StringBuilder();

        try {
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList dayNodes = root.getElementsByTagName("Day");

            for (int i = 0; i < dayNodes.getLength(); i++) {
                Element dayElement = (Element) dayNodes.item(i);
                String date = dayElement.getAttribute("date");

                NodeList rateNodes = dayElement.getElementsByTagName("Rate");
                for (int j = 0; j < rateNodes.getLength(); j++) {
                    Element rateElement = (Element) rateNodes.item(j);
                    String unit = rateElement.getAttribute("unit");
                    String currency = rateElement.getAttribute("curr");
                    String value = rateElement.getTextContent().trim();

                    if (currency.equals(currencyFilter)) {
                        formattedOutput.append("Dátum: ").append(date).append("\n")
                                .append("  ")
                                .append(unit).append(" ").append(currency)
                                .append(" = ").append(value).append(" Ft\n");
                    }
                }
            }
            return formattedOutput.toString();
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Árfolyamok lekérdezése adott dátumtartomány és devizanem alapján.
     *
     * @param startDate A kezdő dátum (formátum: YYYY-MM-DD), amelytől az árfolyamokat lekérdezi.
     * @param endDate   A záró dátum (formátum: YYYY-MM-DD), ameddig az árfolyamokat lekérdezi.
     * @param currency  A lekérdezni kívánt devizanem, például "EUR", "USD".
     * @return Az árfolyamadatok válasza XML formátumban. Hiba esetén a hibaüzenetet tartalmazó szöveg.
     */
    public String getExchangeRatesByDateAndCurrency(String startDate, String endDate, String currency) {
        try {
            return service.getExchangeRates(startDate, endDate, currency);
        } catch (Exception e) {
            return "Hiba az árfolyamok lekérdezésekor: " + e.getMessage();
        }
    }

    public List<String> getAvailableCurrencies() throws Exception {
        String infoXml = getInfo();
        return parseDevizaFromXML(infoXml).stream()
                .map(deviza -> deviza.getDevizanem())
                .collect(Collectors.toList());
    }

    public static List<DevizaChartItem> formatChartData(String response) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getBytes());
        List<DevizaChartItem> currenciesList = new ArrayList<>();

        try {
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList dayNodes = root.getElementsByTagName("Day");

            for (int i = 0; i < dayNodes.getLength(); i++) {
                Element dayElement = (Element) dayNodes.item(i);
                String date = dayElement.getAttribute("date");

                NodeList rateNodes = dayElement.getElementsByTagName("Rate");
                for (int j = 0; j < rateNodes.getLength(); j++) {
                    Element rateElement = (Element) rateNodes.item(j);
                    String currency = rateElement.getAttribute("curr");
                    String value = rateElement.getTextContent().trim();
                        try {
                            double rateValue = Double.parseDouble(value.replace(",", "."));
                            currenciesList.add(new DevizaChartItem(new Deviza(currency,rateValue), date));
                        } catch (NumberFormatException e) {
                            System.err.println("Hibás árfolyam: " + value);
                        }
                }
            }
        } catch (IOException | SAXException e) {
            e.printStackTrace(); // Hibakezelés, ha nem tudja feldolgozni az XML-t
            throw new RuntimeException("Hiba történt az XML feldolgozása során.", e);
        }

        sortByDate(currenciesList);
        return currenciesList;
    }
    public static void sortByDate(List<DevizaChartItem> items) {
        items.sort((item1, item2) ->
                LocalDate.parse(item1.getDate(), DATE_FORMATTER)
                        .compareTo(LocalDate.parse(item2.getDate(), DATE_FORMATTER))
        );
    }


}
