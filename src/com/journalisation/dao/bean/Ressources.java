package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import com.journalisation.utils.FileUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

public class Ressources extends ImplEntity{
    private ObjectProperty<String> path,name,creator,modifiedby,format,description;
    private ObjectProperty<Long> size;
    private ObjectProperty<LocalDate> created,modified;
    private ObjectProperty<Livres> livres;
    private File file;
    private Users users;
    protected ObjectProperty<Integer> no;
    protected ObjectProperty<Boolean> isarchive;
    
    private final String RESPATH="Res";

    public Ressources(Map<String, Object> data) {
        super(data);
    }

    public String getRESPATH() {
        return RESPATH;
    }

    public Ressources(Object... data) {
        super(data);
    }

    public Ressources(ObjectProperty<Integer> id) {
        super(id);
    }

    public Ressources() {
    }

    public ObjectProperty<Livres> livresProperty() {
        return livres;
    }

    public Integer getNo() {
        return no.get();
    }

    public ObjectProperty<Integer> noProperty() {
        return no;
    }

    public void setNo(int no) {
        this.no.set(no);
    }

    public Boolean getIsarchive() {
        return isarchive.get();
    }

    public ObjectProperty<Boolean> isarchiveProperty() {
        return isarchive;
    }

    public void setIsarchive(Boolean isarchive) {
        this.isarchive.set(isarchive);
    }

    @Override
    public void init() {
        path=new SimpleObjectProperty<>();
        name=new SimpleObjectProperty<>();
        creator=new SimpleObjectProperty<>();
        modifiedby=new SimpleObjectProperty<>("");
        format=new SimpleObjectProperty<>();
        description=new SimpleObjectProperty<>("");
        size=new SimpleObjectProperty<>();
        created=new SimpleObjectProperty<>(LocalDate.now());
        modified=new SimpleObjectProperty<>(LocalDate.now());
        livres=new SimpleObjectProperty<>();
        no=new SimpleObjectProperty<>(1);
        isarchive=new SimpleObjectProperty<>(false);
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users user) {
        this.users = user;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public String getPath() {
        return path.get();
    }

    public ObjectProperty<String> pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public String getName() {
        return name.get();
    }

    public ObjectProperty<String> nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCreator() {
        return creator.get();
    }

    public ObjectProperty<String> creatorProperty() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator.set(creator);
    }

    public String getModifiedby() {
        return modifiedby.get();
    }

    public ObjectProperty<String> modifiedbyProperty() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby.set(modifiedby);
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

    public String getDescription() {
        return description.get();
    }

    public ObjectProperty<String> descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Long getSize() {
        return size.get();
    }

    public ObjectProperty<Long> sizeProperty() {
        return size;
    }

    public void setSize(Long size) {
        this.size.set(size);
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public ObjectProperty<LocalDate> createdProperty() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    public LocalDate getModified() {
        return modified.get();
    }

    public ObjectProperty<LocalDate> modifiedProperty() {
        return modified;
    }

    public void setModified(LocalDate modified) {
        this.modified.set(modified);
    }

    public Livres getLivres() {
        return livres.get();
    }

    public ObjectProperty<Livres> livreProperty() {
        return livres;
    }

    public void setLivres(Livres livre) {
        this.livres.set(livre);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        initialize(file);
    }
    void initialize(File f) {
        FileUtils du=FileUtils.instance(f);
            setCreator(du.getCreator());
            size.set(du.getSize());
            setName(f.getName());


    }
}
