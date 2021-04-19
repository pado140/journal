package com.journalisation.utils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import com.journalisation.dao.bean.ImplEntity;

public class ComboboxCell<T extends ImplEntity,R extends ImplEntity> extends TableCell<T,R> {
    @Override
    public void startEdit() {
        super.startEdit();

    }

    @Override
    public void commitEdit(R r) {
        super.commitEdit(r);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);
    }

    @Override
    protected void updateItem(R r, boolean b) {
        super.updateItem(r, b);
        if(isEditing())
            setGraphic(new ComboBox<R>());
    }

    @Override
    protected boolean isItemChanged(R r, R t1) {
        return super.isItemChanged(r, t1);
    }
}
