package com.airplanesoft.dms.ui.view;

import com.airplanesoft.dms.ui.AdminUI;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = ContactsView.VIEW_NAME, ui = AdminUI.class)
public class ContactsView extends VerticalLayout implements BaseView {
    public static final String VIEW_NAME = "contact";

    private String text = "<p><b>Kharkiv, Ukraine</b><br>" +
            "<p><b>Tel.</b> 81234-5566-6666<br>" +
            "<p><b>Tel.</b> 81234-4444-6666<br>" +
            "<p><b>Email.</b> dcs@gmail.com<br>";

    @PostConstruct
    void init() {
        VerticalLayout layout = new VerticalLayout();
        Label area = new Label(text, ContentMode.HTML);
        layout.addComponent(area);
        addComponents(area);
    }
}
