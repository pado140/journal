package com.journalisation.utils;

import javax.xml.parsers.DocumentBuilderFactory;

public class GenerateXmlUtil {
    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
    private node root;

    public attrib createAttrib(String name,String value){
        return new attrib(name, value);
    }

    public node createRoot(String name,attrib attr,node parent){
        return null;
    }

    private class node{
        private attrib attribute;
        private String name;
        private node parent;

        public node(attrib attribute, String name,node parent) {
            this.attribute = attribute;
            this.name = name;
            this.parent=parent;
        }
        public node(attrib attribute, String name) {
            this(attribute,name,null);
        }

        public node() {
            this(null,null);
        }
    }
    private class attrib{
        private String name,value;

        public attrib(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
