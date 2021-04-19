package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import com.journalisation.utils.DocUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

public class Documents extends Ressources{
    private ObjectProperty<String> path,name,creator,modifiedby,format,description;
    private ObjectProperty<Integer> pages,mots,characters,allcharacters,revision;
    private ObjectProperty<LocalDate> created,modified;
    private ObjectProperty<Livres> livres;
    private File file;
    
    private final String DOCPATH="Docs";


    public File getFile() {
        return file;
    }

    public String getDOCPATH() {
        return DOCPATH;
    }

    public void setFile(File file) {
        this.file = file;
        initialize(file);
    }

    public void setNo(int no) {
        this.no.set(no);
    }

    public void setIsarchive(Boolean isarchive) {
        this.isarchive.set(isarchive);
    }
    public Documents(Map<String, Object> data) {
        super(data);
    }

    public Documents(Object... data) {
        super(data);
    }

    public Documents(ObjectProperty<Integer> id) {
        super(id);
    }

    public Documents() {
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

    public Integer getPages() {
        return pages.get();
    }

    public ObjectProperty<Integer> pagesProperty() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages.set(pages);
    }

    public Integer getMots() {
        return mots.get();
    }

    public ObjectProperty<Integer> motsProperty() {
        return mots;
    }

    public void setMots(int mots) {
        this.mots.set(mots);
    }

    public Integer getCharacters() {
        return characters.get();
    }

    public ObjectProperty<Integer> charactersProperty() {
        return characters;
    }

    public void setCharacters(int characters) {
        this.characters.set(characters);
    }

    public Integer getAllcharacters() {
        return allcharacters.get();
    }

    public ObjectProperty<Integer> allcharactersProperty() {
        return allcharacters;
    }

    public void setAllcharacters(int allcharacters) {
        this.allcharacters.set(allcharacters);
    }

    public Integer getRevision() {
        return revision.get();
    }

    public ObjectProperty<Integer> revisionProperty() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision.set(revision);
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

    @Override
    public void init() {
        path=new SimpleObjectProperty<>();
        name=new SimpleObjectProperty<>();
        creator=new SimpleObjectProperty<>();
        modifiedby=new SimpleObjectProperty<>();
        format=new SimpleObjectProperty<>();
        description=new SimpleObjectProperty<>();
        pages=new SimpleObjectProperty<>();
        mots=new SimpleObjectProperty<>();
        characters=new SimpleObjectProperty<>();
        allcharacters=new SimpleObjectProperty<>();
        revision=new SimpleObjectProperty<>();
        created=new SimpleObjectProperty<>();
        modified=new SimpleObjectProperty<>();
        livres=new SimpleObjectProperty<>();
        no=new SimpleObjectProperty<>(1);
        isarchive=new SimpleObjectProperty<>(false);
    }

    @Override
    void initialize(File f) {
        DocUtils du=new DocUtils(f);

        if(f.getName().substring(f.getName().lastIndexOf(".")).equals(".pdf")||f.getName().substring(f.getName().lastIndexOf(".")).equals(".docx")) {
            setCreator(du.getCreator());
            if(f.getName().substring(f.getName().lastIndexOf(".")).equals(".docx"))
                setRevision(Integer.parseInt(du.coreProperties().getRevision()));
           // String datefrom = du.getCreated().toString().replaceAll("[^\\d]", "").substring(0,14);
            setCreated(LocalDate.now());
            //String datemod = du.getModified().toString().replaceAll("[^\\d]", "").substring(0,14);
            setModified(LocalDate.now());
            setAllcharacters(du.getAllchacteres());
            setCharacters(du.getCharacteres());
            setMots(du.getWord());
            setPages(du.getPages());
            setName(f.getName());
        }

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String toString() {
        return getName();
    }
}
