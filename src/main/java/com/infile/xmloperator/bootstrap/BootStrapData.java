package com.infile.xmloperator.bootstrap;
import com.infile.xmloperator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.io.FileNotFoundException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


@Component
public class BootStrapData implements CommandLineRunner {

    private final AccountRepository accountRepository;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    Connection connection;
    @Value("${spring.datasource.driverClassName}")
    String driverName;
    @Value("${spring.datasource.url}")
    String url;

    public BootStrapData(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        init() ;
        load();

    }

    static private String getAttrValue(Node node,String attrName)
    {
        if ( ! node.hasAttributes() ) return "";
        NamedNodeMap nmap = node.getAttributes();
        if ( nmap == null ) return "";
        Node n = nmap.getNamedItem(attrName);
        if ( n == null ) return "";
        return n.getNodeValue();
    }

    static private String getTextContent(Node parentNode,String childName)
    {
        NodeList nlist = parentNode.getChildNodes();
        for (int i = 0 ; i < nlist.getLength() ; i++) {
            Node n = nlist.item(i);
            String name = n.getNodeName();
            if ( name != null && name.equals(childName) )
                return n.getTextContent();
        }
        return "";
    }


    private void load(){

        try {
            FileInputStream file = new FileInputStream(new File("/Users/benjamin.kalombo/Desktop/test34.xml"));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
         Document xmlDocument = builder.parse(file);
         XPath xPath = XPathFactory.newInstance().newXPath();
         NodeList nlist = (NodeList)xPath.evaluate("/accounts/account",
                    xmlDocument,
                    XPathConstants.NODESET);
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO account(" +
                            "  accountid, amount, type, firstname,lastname, demographic" +
                            ")\n" +
                            "VALUES(?, ?, ?,?,?,?\n" +
                            " )");
            for (int i = 0 ; i < nlist.getLength() ; i++) {
                Node node = nlist.item(i);
                List<String> columns = Arrays
                        .asList(getAttrValue(node, "id"),
                                getTextContent(node, "amount"),
                                getTextContent(node, "type"),
                                getTextContent(node, "firstname"),
                                getTextContent(node, "lastname"),
                                getTextContent(node, "demographic"));
                for (int n = 0 ; n < columns.size() ; n++) {
                    stmt.setString(n+1, columns.get(n));
                }
                stmt.execute();

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }catch(XPathExpressionException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void init() {
    connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
