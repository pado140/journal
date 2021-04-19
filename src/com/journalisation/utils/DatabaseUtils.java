package com.journalisation.utils;

import com.journalisation.dao.ORM.ConnectionSql;
import com.journalisation.resources.conf.InitDatabase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * @author Padovano
 * Utilitaire permettant d'exporter et d'importer
 * les donnees d'une base de donnees
 * creer l'arborescence xml de la base de donnees
 */
public class DatabaseUtils {
    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document doc;
    ConnectionSql connectionSql;
    private Element root;

    public void init(){
        databaseCheck();
        connectionSql=ConnectionSql.instance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc=builder.newDocument();
    }

    /**
     * creer le root a savoir le noeud de la base de donnees
     * @param name
     * @return Database Node(root)
     */
    private Element createDatabaseNode(String name){
        root=doc.createElement("Database");
        root.setAttribute("name",name);
        doc.appendChild(root);
        return root;
    }

    /**
     * creer les tables,vues etc...
     * @param name
     * @param Parent
     * @param tab
     * @return 
     */
    private Element createChilds(String name,Element Parent,Element... tab){
        Element tabs=doc.createElement(name);
        for (Element element : tab) {
            tabs.appendChild(element);
        }
        Parent.appendChild(tabs);
        return tabs;
    }

    /**
     * creer et ajouter les attributs aux objets(tables etc...)
     * @param name
     * @param value
     * @param el
     * @return 
     */
    private Element createAttr(String name,String value,Element el){
        el.setAttribute(name,value);
        return el;
    }

    /**
     * exporter et convertir en sql
     * la base de donnees entiere
     */
    public void ExportSql(){
        StringBuilder sb=new StringBuilder();
        try {
            DatabaseMetaData database = connectionSql.getConnection().getMetaData();
            String databaseName=database.getConnection().getCatalog();
            String schemaName=database.getConnection().getSchema();
            //String pattern=database.getConnection().get;
            sb.append("create database "+database.getConnection().getCatalog()+";\n");
            sb.append("use "+database.getConnection().getCatalog()+";\n");
            sb.append("--==========================================================================\n");
            sb.append("-- Tables\n");
            ResultSet tables=database.getTables(databaseName,null,"%",new String[]{"TABLE"});
            while (tables.next()){
                sb.append("create table IF NOT EXISTS "+tables.getString("TABLE_CAT")+"."+tables.getString("TABLE_NAME")+";\n");
                ResultSet cols=database.getColumns(databaseName,null,tables.getString("TABLE_NAME"),"%");
                ArrayList<String> collist=new ArrayList();
                while(cols.next()){
                    StringBuilder sb1=new StringBuilder();

                    sb1.append(cols.getString("COLUMN_NAME")+" ");
                    sb1.append(cols.getString("TYPE_NAME"));
                    if(!cols.getString("TYPE_NAME").matches("DATE|TEXT"))
                        sb1.append("("+cols.getString("COLUMN_SIZE")+") ");
                    sb1.append((cols.getString("IS_NULLABLE").equalsIgnoreCase("yes")?"NULL":"NOT NULL")+" ");
                    sb1.append((cols.getString("IS_AUTOINCREMENT").equalsIgnoreCase("yes")?"AUTO_INCREMENT":"")+"\n ");
                    collist.add(sb1.toString());

                }
                //table.
                //break;
                ResultSet primary=database.getPrimaryKeys(databaseName,schemaName,tables.getString("TABLE_NAME"));

                while(primary.next()){

                    for(int i=1;i<primary.getMetaData().getColumnCount();i++){

                    }
                }

                ResultSet key=database.getIndexInfo(databaseName,schemaName,tables.getString("TABLE_NAME"),false,false);
                ResultSet fk=database.getExportedKeys(databaseName,schemaName,tables.getString("TABLE_NAME"));

                while(fk.next()){

                }

                while(key.next()){

                }
                ResultSet value=connectionSql.select(String.format("select * from %s",tables.getString("TABLE_NAME")));

                while(value.next()) {

                }
            }
            ResultSet procedures=database.getProcedures(databaseName,schemaName,"%");
//            Element procedureNodes=createChilds("procedures",root);
            while(procedures.next()){
//                Element procedureNode=createChilds("procedures",procedureNodes);
//                for(int i=1;i<procedures.getMetaData().getColumnCount();i++){
//                    if(procedures.getObject(i)!=null)
//                        procedureNode.setAttribute(procedures.getMetaData().getColumnName(i),procedures.getObject(i).toString());
//                }
//                ResultSet procedureCols=database.getProcedureColumns(databaseName,schemaName,"%","%");
//                while(procedureCols.next()){
//                    Element column=createChilds("columns",procedureNode);
//                    for(int i=1;i<procedureCols.getMetaData().getColumnCount();i++){
//                        if(procedureCols.getObject(i)!=null)
//                            column.setAttribute(procedureCols.getMetaData().getColumnName(i),procedureCols.getObject(i).toString());
//                    }
//                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        

    }
    private void executeSql(){
        try {
            DatabaseMetaData database = connectionSql.getConnection().getMetaData();
            String databaseName=database.getConnection().getCatalog();
            String schemaName=database.getConnection().getSchema();
            //String pattern=database.getConnection().get;
            createDatabaseNode(database.getConnection().getCatalog());
            ResultSet tables=database.getTables(databaseName,null,"%",new String[]{"TABLE"});
            Element tablesNode=createChilds("tables",root);
            while (tables.next()){
                Element table=createChilds("table",tablesNode);
                for(int i=1;i<=tables.getMetaData().getColumnCount();i++){
                    //System.out.println(tables.getMetaData().getColumnName(i)+"======>"+tables.getObject(i));
                    table.setAttribute(tables.getMetaData().getColumnName(i),tables.getObject(i)!=null?tables.getObject(i).toString():"NULL");
                }
                ResultSet cols=database.getColumns(databaseName,null,tables.getString("TABLE_NAME"),"%");
                Element colsNode=createChilds("cols",table);

                while(cols.next()){
                    Element col=createChilds("col",colsNode);
                    for(int i=1;i<=cols.getMetaData().getColumnCount();i++){
                       // System.out.println(cols.getMetaData().getColumnName(i)+"===>"+cols.getObject(i));

                        col.setAttribute(cols.getMetaData().getColumnName(i),cols.getObject(i)!=null?cols.getObject(i).toString():"NULL");
                    }
                    //break;
                }
                //table.
                //break;
                ResultSet primary=database.getPrimaryKeys(databaseName,schemaName,tables.getString("TABLE_NAME"));
                Element primaryNode=createChilds("primarys",table);
                while(primary.next()){
                    Element primaryNod=createChilds("primary",primaryNode);
                    for(int i=1;i<primary.getMetaData().getColumnCount();i++){
                        //if(primary.getObject(i)!=null)
                            primaryNod.setAttribute(primary.getMetaData().getColumnName(i),primary.getObject(i)!=null?primary.getObject(i).toString():"NULL");
                    }
                }

                ResultSet key=database.getIndexInfo(databaseName,schemaName,tables.getString("TABLE_NAME"),false,false);
                ResultSet fk=database.getExportedKeys(databaseName,schemaName,tables.getString("TABLE_NAME"));
                Element Fk=createChilds("foreign_keys",table);
                while(fk.next()){
                    Element foreign=createChilds("foreign_key",Fk);
                    for(int i=1;i<fk.getMetaData().getColumnCount();i++){
                        //if(fk.getObject(i)!=null)
                            foreign.setAttribute(fk.getMetaData().getColumnName(i),fk.getObject(i)!=null?fk.getObject(i).toString():"NULL");
                    }
                }
                Element Keys=createChilds("keys",table);
                while(key.next()){
                    Element k=createChilds("key",Keys);
                    for(int i=1;i<key.getMetaData().getColumnCount();i++){
                        //if(key.getObject(i)!=null)
                            k.setAttribute(key.getMetaData().getColumnName(i),key.getObject(i)!=null?key.getObject(i).toString():"NULL");
                    }
                }
                ResultSet value=connectionSql.select(String.format("select * from %s",tables.getString("TABLE_NAME")));
                Element data=createChilds("Datas",table);
                while(value.next()) {
                    Element dt=createChilds("data",data);
                    for (int i = 1; i <= value.getMetaData().getColumnCount(); i++) {
                        //System.out.println(tables.getMetaData().getColumnName(i)+"======>"+tables.getObject(i));
                        //if (value.getObject(i) != null)
                            dt.setAttribute(value.getMetaData().getColumnName(i), value.getObject(i) != null?value.getObject(i).toString():"NULL");
                    }
                }
            }
            ResultSet procedures=database.getProcedures(databaseName,schemaName,"%");
            Element procedureNodes=createChilds("procedures",root);
            while(procedures.next()){
                Element procedureNode=createChilds("procedures",procedureNodes);
                for(int i=1;i<procedures.getMetaData().getColumnCount();i++){
                    if(procedures.getObject(i)!=null)
                        procedureNode.setAttribute(procedures.getMetaData().getColumnName(i),procedures.getObject(i).toString());
                }
                ResultSet procedureCols=database.getProcedureColumns(databaseName,schemaName,"%","%");
                while(procedureCols.next()){
                    Element column=createChilds("columns",procedureNode);
                    for(int i=1;i<procedureCols.getMetaData().getColumnCount();i++){
                        if(procedureCols.getObject(i)!=null)
                            column.setAttribute(procedureCols.getMetaData().getColumnName(i),procedureCols.getObject(i).toString());
                    }
                }
            }

            //ResultSet rtest=database.
            ResultSet typetable=database.getTableTypes();
            Element type_ndo=createChilds("types",root);
            while(typetable.next()){
                Element typenode=createChilds("type",type_ndo);
                for(int i=1;i<typetable.getMetaData().getColumnCount();i++){
                    //if(typetable.getObject(i)!=null)
                        typenode.setAttribute(typetable.getMetaData().getColumnName(i),typetable.getObject(i).toString());
                }

            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult resultat = new StreamResult(new File("monFichier2.xml"));

            transformer.transform(source, resultat);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public String uploadDatabase(File f){
        StringBuilder sb=new StringBuilder();
        Document docs= null;
        try {
            FileWriter writer=new FileWriter(new File("sqlfile.sql"));
            docs = builder.parse(f);
            Element rootDoc=docs.getDocumentElement();
            String rootdoc=rootDoc.getAttribute("name");
            sb.append(" create database "+rootdoc+";\n");
            sb.append("use "+rootdoc+";\n");
            sb.append("--==========================================\n");
            NodeList nodes=docs.getElementsByTagName("table");
            for(int i=0;i<nodes.getLength();i++) {
                Node node=nodes.item(i);
                    if(node instanceof Element){
                        Element el=(Element)node;
                        sb.append("create TABLE IF NOT EXISTS " +el.getAttribute("TABLE_CAT")+"."+el.getAttribute("TABLE_NAME")+"(\n");
                        //if(el.hasChildNodes()){
                            NodeList columns=el.getElementsByTagName("col");
                            ArrayList<String> collist=new ArrayList();
                            for(int j=0;j<columns.getLength();j++){
                                Node col=columns.item(j);
                                if(col instanceof Element){
                                    Element col1=(Element)col;
                                    StringBuilder sb1=new StringBuilder();

                                    sb1.append(col1.getAttribute("COLUMN_NAME")+" ");
                                    sb1.append(col1.getAttribute("TYPE_NAME")+"(");
                                    sb1.append(col1.getAttribute("COLUMN_SIZE")+") ");
                                    sb1.append((col1.getAttribute("IS_NULLABLE").equalsIgnoreCase("yes")?"NULL":"NOT NULL")+" ");
                                    sb1.append((col1.getAttribute("IS_AUTOINCREMENT").equalsIgnoreCase("yes")?"AUTO_INCREMENT":"")+"\n ");
                                    collist.add(sb1.toString());
                                }
                            }
                            
                            sb.append(String.join(",",collist));
                        //}
                            NodeList primaries=el.getElementsByTagName("primarys");
                            if(el.hasChildNodes()){
                                sb.append(",PRIMARY KEY(");
                                NodeList primarylist=el.getElementsByTagName("primary");
                                ArrayList<String> st=new ArrayList<>();
                                for(int j=0;j<primarylist.getLength();j++){
                                    if(primarylist.item(j) instanceof Element){
                                        
                                        Element primary=(Element)(primarylist.item(j));
                                        System.out.println("primary:"+primary.getAttribute("COLUMN_NAME"));
                                        st.add(primary.getAttribute("COLUMN_NAME"));
                                    }
                                }
                                sb.append(String.join(",", st)).append(")\n");
                            }
                        sb.append(");\n");
                    }
            }
            writer.append(sb.toString());
            writer.flush();
            writer.close();
            System.out.println(sb.toString());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        DatabaseUtils dbutils=new DatabaseUtils();
        dbutils.init();
        //dbutils.executeSql();
        dbutils.uploadDatabase(new File("monFichier2.xml"));
    }

    private boolean databaseCheck(){
        if(InitDatabase.propertyOf("database")!=null){
            writeprop("provider");
            writeprop("host");
            writeprop("port");
            writeprop("username");
            writeprop("password");
            writeprop("database");
            writeprop("api");
            return true;
        }
        return false;
    }

    private void writeprop(String cas){
        String value= InitDatabase.propertyOf(cas);
        if(value!=null){

            if(cas.equalsIgnoreCase("provider")){
                AppUtils.PROVIDER=value;
            }
            if(cas.equalsIgnoreCase("host")){
                AppUtils.HOST=value;
            }
            if(cas.equalsIgnoreCase("port")){
                AppUtils.PORT=value;
            }
            if(cas.equalsIgnoreCase("username")){
                AppUtils.dbUSER=value;
            }
            if(cas.equalsIgnoreCase("password")){
                AppUtils.dbPASSWORD=value;
            }
            if(cas.equalsIgnoreCase("database")){
                AppUtils.dbNAME=value;
            }
            if(cas.equalsIgnoreCase("api")){
                AppUtils.API=value;
            }

        }
    }
}
