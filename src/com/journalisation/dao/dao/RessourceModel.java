package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.bean.Ressources;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.journalisation.Main.users;

public class RessourceModel extends ImplModel<Ressources> {
    
   private static RessourceModel model=null;

    public synchronized static RessourceModel getManager() {
        if(model==null)
            model=new RessourceModel();
        return model;
    }
    
    @Override
    public Ressources add(Ressources data) {
        requete="insert into ressource(path,name,created,modified,creator,description,modifiedby,format,livres_id,users_id) values (?,?,?,?,?,?,?,?,?,?)";
        System.out.println(requete);
        if(this.connectionSql.Update(requete,1,data.getPath(),data.getName(),data.getCreated().format(DateTimeFormatter.ISO_DATE),
                data.getCreated().format(DateTimeFormatter.ISO_DATE),data.getCreator(),data.getDescription(),data.getModifiedby(),data.getFormat(),data.getLivres().getId(),users.getId())){
            data.setId(this.connectionSql.getLast());
            this.trace(users,"Ressource "+data.getName()+" a été ajouté au projet "+data.getLivres().getTitre(),"0",String.valueOf(data.getId()));
            System.out.println(data);
            return data;
        }
        return null;
    }

    @Override
    public Ressources change(Ressources data) {
        requete="update ressource set path=?,name=?,created=?,modified=?,creator=?,description=?,modifiedby=?,format=?,livres_id=?,no=?,isarchive=? where id=?";
        if(this.connectionSql.Update(requete,1,data.getPath(),data.getName(),data.getCreated().format(DateTimeFormatter.ISO_DATE),
                data.getCreated().format(DateTimeFormatter.ISO_DATE),data.getCreator(),data.getDescription(),users.getUsername(),data.getFormat(),
                data.getLivres().getId(),data.getNo(),data.getIsarchive(),data.getId())){
            this.trace(users,"Ressource "+data.getName()+" du projet "+data.getLivres().getTitre()+" a été modifié","0",String.valueOf(data.getId()));
            return data;
        }
        return null;
    }

    @Override
    public boolean remove(Ressources ob) {
        requete="delete from ressource where id=?";
        if(this.connectionSql.Update(requete,0,ob.getId())){
            this.trace(users,"Ressource "+ob.getName()+" du projet "+ob.getLivres().getTitre()+" a été supprimé","0",String.valueOf(ob.getId()));
            return true;
        }
        return false;
    }

    @Override
    public ObservableList<Ressources> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Ressources> ressources= FXCollections.observableArrayList();
        requete="select * from ressource";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        System.out.println(data.size());
        data.forEach(d->{
            Ressources res=new Ressources(d);
            ressources.add(res);
        });
        return ressources;
    }

    @Override
    public Ressources exists(Ressources ob) {
        return null;
    }
}
