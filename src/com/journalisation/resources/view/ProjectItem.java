package com.journalisation.resources.view;


import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import com.journalisation.controller.ProjectViewController;
import com.journalisation.dao.bean.Livres;

public class ProjectItem extends AnchorPane {
    private Livres livre;
    private ProjectViewController project;
    private Parent content;

    public ProjectItem(Livres livre) {
        this.livre = livre;
        ViewBehavior loadProject=new ViewBehavior("projects.fxml");
        content=loadProject.getparent();
        project=(ProjectViewController)loadProject.getController();
        project.setLivre(livre);
        doLayout();

        //pi.setContent(new Label(this.livre.getTitre()));
    }

    public AnchorPane doLayout(){
        this.getChildren().add(content);
        return this;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public ProjectViewController getProject() {
        return project;
    }

    public void setProject(ProjectViewController project) {
        this.project = project;
    }
}
