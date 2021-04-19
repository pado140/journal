package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

public class Auteurs extends ImplEntity {
    private ObjectProperty<String> nom,prenom,lieu,notice,pathimage;
    private ObjectProperty<LocalDate> naissance;
    private File image;
    private final String AUTHOR_PATH="Authors";

    public Auteurs(Map<String, Object> data) {
        super(data);
    }

    public String getAUTHOR_PATH() {
        return AUTHOR_PATH;
    }

    
    @Override
    public void init() {
        nom=new SimpleObjectProperty<>();
        prenom=new SimpleObjectProperty<>();
        lieu=new SimpleObjectProperty<>();
        notice=new SimpleObjectProperty<>();
        pathimage=new SimpleObjectProperty<>();
    }

    public Auteurs(Object... data) {
        super(data);
    }

    public Auteurs(ObjectProperty<Integer> id) {
        super(id);
    }

    public String getNotice() {
        return notice.get();
    }

    public ObjectProperty<String> noticeProperty() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice.set(notice);
    }

    public String getNom() {
        return nom.get();
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

    public String getLieu() {
        return lieu.get();
    }

    public ObjectProperty<String> lieuProperty() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu.set(lieu);
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

    public String getPathimage() {
        return pathimage.get();
    }

    public ObjectProperty<String> pathimageProperty() {
        return pathimage;
    }

    public void setPathimage(String pathImage) {
        this.pathimage.set(pathImage);
        image=new File(pathImage);
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
        pathimage.set(image.getPath());
    }

    @Override
    public String toString() {
        return getNom().concat(" ").concat(getPrenom());
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
