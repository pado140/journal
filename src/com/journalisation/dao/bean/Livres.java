package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Map;

public class Livres extends ImplEntity {
    private ObjectProperty<String> titre,soustitre,resume,format;
    private ObjectProperty<Integer> pages,mots;
    private ObservableList<Auteurs> dirige;
    private ObservableList<Auteurs> ecrit;
    private ObservableList<Documents> documents;
    private ObservableList<Ressources> ressources;
    private ObservableList<TaskProject> tasks;
    private ObjectProperty<String> status;
    private ObservableList<Users> userwork;

    public ObservableList<Users> getUserwork() {
        return userwork;
    }

    public void setUserwork(ObservableList<Users> userwork) {
        this.userwork = userwork;
    }

    public Livres(Map<String, Object> data) {
        super(data);
    }

    public Livres(Object... data) {
        super(data);
    }

    public Livres(ObjectProperty<Integer> id) {
        super(id);
    }

    public Livres() {
    }

    public ObservableList<Ressources> getRessources() {
        return ressources;
    }

    public void setRessources(ObservableList<Ressources> ressources) {
        this.ressources = ressources;
    }

    public ObservableList<TaskProject> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<TaskProject> tasks) {
        this.tasks = tasks;
    }

    public ObservableList<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(ObservableList<Documents> documents) {
        this.documents = documents;
    }

    private ObjectProperty<LocalDate> receipt;
    private ObjectProperty<Genre> genre;

    public String getTitre() {
        return titre.get();
    }

    public ObjectProperty<String> titreProperty() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    public String getSoustitre() {
        return soustitre.get();
    }

    public ObjectProperty<String> soustitreProperty() {
        return soustitre;
    }

    public void setSoustitre(String soustitre) {
        this.soustitre.set(soustitre);
    }

    public String getFormat() {
        return format.get();
    }

    public ObjectProperty<String> formatProperty() {
        return format;
    }

    public void setFormat(String format) {
        this.format.set(format);
    }

    public String getResume() {
        return resume.get();
    }

    public ObjectProperty<String> resumeProperty() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume.set(resume);
    }

    public Integer getPages() {
        return pages.get();
    }

    public ObjectProperty<Integer> pagesProperty() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages.set(pages);
    }

    public Integer getMots() {
        return mots.get();
    }

    public ObjectProperty<Integer> motsProperty() {
        return mots;
    }

    public void setMots(Integer mots) {
        this.mots.set(mots);
    }

    public ObservableList<Auteurs> getDirige() {
        return dirige;
    }

    public void setDirige(ObservableList<Auteurs> dirige) {
        this.dirige = dirige;
    }

    public ObservableList<Auteurs> getEcrit() {
        return ecrit;
    }

    public void setEcrit(ObservableList<Auteurs> ecrit) {
        this.ecrit = ecrit;
    }

    @Override
    public boolean isValid() {
        return !(getTitre().trim().isEmpty()&&getDirige().isEmpty()&&getEcrit().isEmpty());
    }

    public LocalDate getReceipt() {
        return receipt.get();
    }

    public ObjectProperty<LocalDate> receiptProperty() {
        return receipt;
    }

    public void setReceipt(LocalDate receipt) {
        this.receipt.set(receipt);
    }

    public Genre getGenre() {
        return genre.get();
    }

    public ObjectProperty<Genre> genreProperty() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre.set(genre);
    }

    @Override
    public String toString() {
        return getTitre();
    }

    @Override
    public void init() {
        titre=new SimpleObjectProperty<>();
        soustitre=new SimpleObjectProperty<>();
        resume=new SimpleObjectProperty<>();
        format=new SimpleObjectProperty<>();
        pages=new SimpleObjectProperty<>();
        mots=new SimpleObjectProperty<>();
        genre=new SimpleObjectProperty<>();
        receipt=new SimpleObjectProperty<>();
        dirige= FXCollections.observableArrayList();
        ecrit= FXCollections.observableArrayList();
        status=new SimpleObjectProperty<>();
        documents=FXCollections.observableArrayList();
        ressources=FXCollections.observableArrayList();
    }
}
