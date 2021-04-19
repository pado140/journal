package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.util.Map;

public class Personnes extends ImplEntity {
    private ObjectProperty<String> nom,prenom,adresse,tel,mail,sexe;
    private ObjectProperty<LocalDate> naissance;

    public Personnes() {
    }

    public String getNom() {
        return nom.get();
    }

    public Personnes(Map<String, Object> data) {
        super(data);
    }

    public ObjectProperty<String> nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public ObjectProperty<String> prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public ObjectProperty<String> adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public String getTel() {
        return tel.get();
    }

    public ObjectProperty<String> telProperty() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public String getMail() {
        return mail.get();
    }

    public ObjectProperty<String> mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public String getSexe() {
        return sexe.get();
    }

    public ObjectProperty<String> sexeProperty() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe.set(sexe);
    }

    public LocalDate getNaissance() {
        return naissance.get();
    }

    public ObjectProperty<LocalDate> naissanceProperty() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance.set(naissance);
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void init() {
        nom=new SimpleObjectProperty<>();
        prenom=new SimpleObjectProperty<>();
        adresse=new SimpleObjectProperty<>();
        tel=new SimpleObjectProperty<>();
        mail=new SimpleObjectProperty<>();
        sexe=new SimpleObjectProperty<>();
        naissance=new SimpleObjectProperty<>();
    }
}
