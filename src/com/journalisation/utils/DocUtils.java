package com.journalisation.utils;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.docx4j.docProps.core.CoreProperties;
import org.docx4j.docProps.extended.Properties;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class DocUtils {
    private int pages,word,characteres,allchacteres;
    private String creator,type,content,created,modified;
    private static DocUtils util;
    static WordprocessingMLPackage wordprocessingMLPackage;
    private Properties docproperties;
    private CoreProperties coreproperties;
    private PdfReader reader;
    private Map<String,String> infos;


    public DocUtils(File f) {
        setType(f.getName().substring(f.getName().lastIndexOf(".")));
        if(getType().equals(".pdf")) {
            try {
                reader = new PdfReader(f.getPath());
                infos=reader.getInfo();
                StringBuilder sb=new StringBuilder();
                pages=reader.getNumberOfPages();
                for(int i=1;i<=pages;i++){
                    try{
                    sb.append(PdfTextExtractor.getTextFromPage(reader,i));
                    }catch(Exception ex){
                        sb.append("");
                    }
                }
                content=sb.toString();
                word=content.trim().split(" ").length;
                characteres=content.trim().replace(" ","").length();
                allchacteres=content.trim().length();
                
                creator=infos.getOrDefault("Creator","N/A");
                created=infos.getOrDefault("CreationDate","");
                modified=infos.getOrDefault("ModDate","");
//                //modified
//                //reader.
//                System.out.println(reader.getInfo());
//                System.out.println(reader.getNamedDestinationFromNames());
//                System.out.println(reader.getCatalog().getKeys());
//                System.out.println(reader.getCatalog().get(PdfName.PAGE));
//                System.out.println(reader.getCatalog().getDirectObject(PdfName.PAGE));
//                System.out.println(reader.getNamedDestinationFromStrings().size());
//                System.out.println(reader.getFileLength());
//                System.out.println("pages:"+reader.getNumberOfPages());
//                System.out.println(reader.getCatalog().get(PdfName.PAGES));
//                System.out.println(reader.getCatalog());
//                
//
//                //System.out.println(reader.getCatalog().getKeys());
//                //System.out.println(reader.getCatalog().getKeys());
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                wordprocessingMLPackage = WordprocessingMLPackage.load(new FileInputStream(f));
                docproperties = wordprocessingMLPackage.getDocPropsExtendedPart().getContents();
                coreproperties = wordprocessingMLPackage.getDocPropsCorePart().getContents();
                word=docproperties.getWords();
                characteres= docProperties().getCharacters();
                allchacteres=docproperties.getCharactersWithSpaces();
                creator=coreProperties().getCreator().getContent().isEmpty() ? "" :coreProperties().getCreator().getContent().get(0);
                created=coreProperties().getCreated().getContent().get(0);
                modified=coreProperties().getModified().getContent().get(0);
                pages=docProperties().getPages();
            } catch (FileNotFoundException | Docx4JException e) {
                e.printStackTrace();
            }
        }
    }


    public Properties docProperties(){
        return docproperties;
    }
    public CoreProperties coreProperties(){
        return coreproperties;
    }

    public int getPages() {
        return pages;
    }

    public int getWord() {
        return word;
    }

    public int getCharacteres() {
        return characteres;
    }

    public int getAllchacteres() {
        return allchacteres;
    }

    public String getCreator() {
        return creator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }
}
