package lab6;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse("lab6.xml");
            XPathFactory pathFactory = XPathFactory.newInstance();
            XPath xpath = pathFactory.newXPath();
            //Книга, у которой автор Оруэлл, Великобритания
            List<String> author = getTitle(doc, xpath, "Оруэлл", "Великобритания");
            System.out.println("Название книги: " + author.get(0));
            //Нахождение номера телефона читателя по атрибуту номер билета
            List<String> telephone = getPhone(doc, xpath, "173133");
            System.out.println("Телефон читателя: " + telephone.get(0));

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTitle(Document doc, XPath xpath, String name_author, String country_author) {
        List<String> list = new ArrayList<>();
        try {
            XPathExpression xExp = xpath.compile(String.format(
                    "//books/book[author[name_author='" + name_author + "' and country_author = '" + country_author + "']]/title/text()"));
            NodeList books = (NodeList) xExp.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < books.getLength(); i++) {
                list.add(books.item(i).getTextContent());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getPhone(Document doc, XPath xpath, String number_ticket) {
        List<String> list = new ArrayList<>();
        try {
            XPathExpression xExp = xpath.compile(String.format(
                    "//readers/reader[@number_ticket='" + number_ticket + "']/telephone/text()"));
            NodeList readers = (NodeList) xExp.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < readers.getLength(); i++) {
                list.add(readers.item(i).getTextContent());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
  }
}
