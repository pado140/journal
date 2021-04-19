package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.bean.Documents;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.journalisation.Main.users;

public class DocumentModel extends ImplModel<Documents> {
    
    private static DocumentModel model=null;

    public synchronized static DocumentModel getManager() {
        if(model==null)
            model=new DocumentModel();
        return model;
    }
    @Override
    public Documents add(Documents data) {
        requete="insert into docs(path,name,pages,mots,characters,allcharacters,created,modified,creator,description,modifiedby,revision,format,livres_id,users_id,no,isarchive) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if(this.connectionSql.Update(requete,1,data.getPath(),data.getName(),data.getPages(),data.getMots(),data.getCharacters(),data.getAllcharacters(),data.getCreated().format(DateTimeFormatter.ISO_DATE),
                data.getCreated().format(DateTimeFormatter.ISO_DATE),data.getCreator(),data.getDescription(),data.getModifiedby(),data.getRevision(),data.getFormat(),data.getLivres().getId(),users.getId()
        ,data.getNo(),data.getIsarchive())){
            data.setId(this.connectionSql.getLast());
            trace(users,String.format("Ajout des documents du Projet(%s)",data.getLivres().getTitre()),String.valueOf(data.getId()),"0");
            return data;
        }
        return null;
    }

    @Override
    public Documents change(Documents data) {
        requete="update docs set path=?,name=?,pages=?,mots=?,characters=?,allcharacters=?,created=?,modified=?,creator=?,description=?,modifiedby=?,revision=?,format=?,livres_id=?" +
                " ,no=?,isarchive=? where id=?";
        if(this.connectionSql.Update(requete,0,data.getPath(),data.getName(),data.getPages(),data.getMots(),data.getCharacters(),data.getAllcharacters(),data.getCreated().format(DateTimeFormatter.ISO_DATE),
                data.getCreated().format(DateTimeFormatter.ISO_DATE),data.getCreator(),data.getDescription(),users.getUsername(),data.getRevision(),data.getFormat(),data.getLivres().getId()
                ,data.getNo(),data.getIsarchive(),data.getId())){

            return data;
        }
        return null;
    }

    @Override
    public boolean remove(Documents ob) {
        requete="delete from docs where id=?";
        return this.connectionSql.Update(requete,0,ob.getId());
    }

    @Override
    public ObservableList<Documents> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Documents> documents= FXCollections.observableArrayList();
        requete="select * from docs";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        System.out.println(data.size());
        data.forEach(d->{
            Documents doc=new Documents(d);
            documents.add(doc);
        });
        return documents;
    }

    @Override
    public Documents exists(Documents ob) {
        return null;
    }
}
