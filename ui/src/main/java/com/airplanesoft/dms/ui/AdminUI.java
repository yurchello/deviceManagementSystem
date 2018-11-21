package com.airplanesoft.dms.ui;

import com.airplanesoft.dms.ui.view.AboutView;
import com.airplanesoft.dms.ui.view.ContactsView;
import com.airplanesoft.dms.ui.view.UsersView;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.annotations.Theme;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Theme("valo")
@SpringUI(path = "")
public class AdminUI extends UI {

    private static Notification notification;
    private Map<Class<? extends View>, MenuBar.MenuItem> viewByRootItem = new HashMap<>();
    private final SpringNavigator springNavigator;
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public AdminUI(SpringNavigator springNavigator) {
        this.springNavigator = springNavigator;
    }

    @Override
    protected void init(VaadinRequest request) {
        logger.info("Init AdminUI.");
        if (notification != null) {
            notification.show(Page.getCurrent());
            notification = null;
        }

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.MENUBAR_SMALL);

        MenuBar menuBar = getMenuBar();
        menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

        Label logo = new Label("DCS");
        logo.setStyleName(ValoTheme.LABEL_H1);

        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.addComponents(logo, menuBar);
        header.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        header.setComponentAlignment(menuBar, Alignment.MIDDLE_CENTER);
        header.setExpandRatio(logo, 1);
        header.setExpandRatio(menuBar, 11);

        CssLayout main = new CssLayout();
        main.setSizeFull();

        root.addComponent(header);
        root.addComponent(main);
        root.setExpandRatio(header, 0.1f);
        root.setExpandRatio(main, 0.9f);

        springNavigator.init(this, main);
        springNavigator.addView("", UsersView.class);

    }

    private MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setWidth("100%");
        MenuBar.MenuItem users = menuBar.addItem("Users", (MenuBar.Command) event -> AdminUI.this.getUI().getNavigator().navigateTo(UsersView.VIEW_NAME));
        MenuBar.MenuItem about = menuBar.addItem("About", (MenuBar.Command) event -> AdminUI.this.getUI().getNavigator().navigateTo(AboutView.VIEW_NAME));
        MenuBar.MenuItem contacts = menuBar.addItem("Contacts", (MenuBar.Command) event -> AdminUI.this.getUI().getNavigator().navigateTo(ContactsView.VIEW_NAME));

        viewByRootItem.put(UsersView.class, users);
        viewByRootItem.put(AboutView.class, about);
        viewByRootItem.put(ContactsView.class, contacts);

        return menuBar;
    }

    public void putMenuState(View oldView, View newView) {
        Optional.ofNullable(oldView)
                .map(View::getClass)
                .map(viewByRootItem::get)
                .ifPresent(menuItem -> menuItem.setStyleName(null));

        Optional.ofNullable(newView)
                .map(View::getClass)
                .map(viewByRootItem::get)
                .ifPresent(menuItem -> menuItem.setStyleName("highlight"));
    }

}
