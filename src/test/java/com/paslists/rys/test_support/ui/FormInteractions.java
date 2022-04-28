package com.paslists.rys.test_support.ui;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.Nullable;

public class FormInteractions {

    private final StandardEditor editor;
    public FormInteractions(StandardEditor editor) {
        this.editor = editor;
    }
    public static FormInteractions of(StandardEditor editor) {
        return new FormInteractions(editor);
    }
    @Nullable public TextField<String> setFieldValue(String fieldName) {
        return (TextField<String>) editor.getWindow().getComponent(fieldName);
    }

    public void setFieldValue(String fieldname, String value) {
        setFieldValue(fieldname).setValue(value);
    }
    @Nullable public Button getButton(String buttonId) {
        return (Button) editor.getWindow().getComponent(buttonId);
    }

    public OperationResult saveForm() {
        return editor.closeWithCommit();
    }
}
