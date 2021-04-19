package com.journalisation.resources.lang;

import java.util.Locale;
import java.util.ResourceBundle;

public class RessourceBundleSevices extends TraductionUtils{

    private ResourceBundle rb;
    private String locale="fr";
    public RessourceBundleSevices(ResourceBundle rb) {
        this.rb=rb;
    }

    public RessourceBundleSevices() {
            rb=ResourceBundle.getBundle("com.journalisation.resources.lang.languages",new Locale(locale));
    }

    @Override
    public String Translate(String a) {
        String translation ="";
        try {
            translation = "";
            for (String st : a.split(" ")) {
                translation += " " + translateTo(st);
                System.out.println(st + "=>" + translateTo(st));
                System.out.println(a + "=>" + translation);
            }
        }catch(NullPointerException e){

        }
        return translation.trim();
    }

    private String translateTo(String a){
        return rb.containsKey(a.toLowerCase())?rb.getString(a.toLowerCase()):a;
    }
    public void changeLocale(String locale){
        if(this.locale.equals(locale))
            return;
        rb=ResourceBundle.getBundle("com.journalisation.resources.lang.languages", new Locale(locale));
        this.locale=locale;
        notifyListeners();
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public String getLocale() {
        return locale;
    }
}
