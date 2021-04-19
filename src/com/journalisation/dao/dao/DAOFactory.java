/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.dao;

/**
 *
 * @author Padovano
 */
public class DAOFactory<T> {
    private static AfficheModel afficheModel;
    private static GenreModel genreModel;
    private static AuteurModel auteurModel;
    private static LivreModel livreModel;
    private static DocumentModel documentModel;
    private static TaskModel taskModel;
    private static TaskProjectModel taskProjectModel;
    private static UserModel userModel;
    private static RolesModel rolesModel;
    private static GroupsModel groupsModel;
    private static RessourceModel ressourceModel;
    private static TransactionModel transactionModel;
    public static ImplModel createModel(DAOName type){
        ImplModel model=null;
    switch(type){
        case affiche:
            if(afficheModel==null)
                afficheModel=new AfficheModel();
            return afficheModel;
        case genre:
            if(genreModel==null)
                genreModel=GenreModel.getManager();
            return genreModel;
        case auteur:
            if(auteurModel==null)
                auteurModel=AuteurModel.getManager();
            return auteurModel;
        case livre:
            if(livreModel==null)
                livreModel=LivreModel.getManager();
            return livreModel;
        case doc:
            if(documentModel==null)
                documentModel=DocumentModel.getManager();
            return documentModel;
        case task:
            if(taskModel==null)
                taskModel=TaskModel.getManager();
            return taskModel;
        case task_project:
            if(taskProjectModel==null)
                taskProjectModel=TaskProjectModel.getManager();
            return taskProjectModel;
        case user:
            if(userModel==null)
                userModel=UserModel.getManager();
            return userModel;
        case role:
            if(rolesModel==null)
                rolesModel=RolesModel.getManager();
            return rolesModel;
        case group:
            if(groupsModel==null)
                groupsModel=GroupsModel.getManager();
            return groupsModel;
        case res:
            if(ressourceModel==null)
                ressourceModel=RessourceModel.getManager();
            return ressourceModel;
        case transac:
            if(transactionModel==null){
                transactionModel=TransactionModel.getManager();
            }
            return transactionModel;

    }
    
    return model;
    }
}
