package com.airplanesoft.dms.ui;


import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.impl.UserRestService;
import com.airplanesoft.dms.ui.view.AboutView;
import com.airplanesoft.dms.ui.view.ContactsView;
import com.airplanesoft.dms.ui.view.UsersView;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.annotations.Theme;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@SpringView(name="")
@Theme("valo")
@SpringUI(path = "")
public class AdminUI extends UI {

    private static Notification notification;
    private Map<Class<? extends View>, MenuBar.MenuItem> viewByRootItem = new HashMap<>();
    private final SpringNavigator springNavigator;
    @Autowired
    UserRestService service;


    @Autowired
    public AdminUI(SpringNavigator springNavigator) {
        this.springNavigator = springNavigator;
    }


    public static void setNotification(Notification notification) {
        AdminUI.notification = notification;
    }

    @Override
    protected void init(VaadinRequest request) {
        //setContent(new Label("savdsadvasdv"));

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

        Button logoutButton = configureLogoutButton();
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.addComponents(logo, menuBar);
//        header.addComponents(logo, menuBar, logoutButton);
        header.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        header.setComponentAlignment(menuBar, Alignment.MIDDLE_CENTER);
//        header.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
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

        MenuBar.MenuItem about = menuBar.addItem("About",  (MenuBar.Command) event -> AdminUI.this.getUI().getNavigator().navigateTo(AboutView.VIEW_NAME));
        //settings.addItem("Certificate", event -> getUI().getNavigator().navigateTo(AdminSettingsView.VIEW_NAME));

        MenuBar.MenuItem contacts = menuBar.addItem("Contacts",  (MenuBar.Command) event -> AdminUI.this.getUI().getNavigator().navigateTo(ContactsView.VIEW_NAME));

        viewByRootItem.put(UsersView.class, users);
        viewByRootItem.put(AboutView.class, about);
        viewByRootItem.put(ContactsView.class, contacts);
//        viewByRootItem.put(AdminSettingsView.class, settings);
//        viewByRootItem.put(ArchiveView.class, archive);

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

    private Button configureLogoutButton() {
        Button loginButton = new Button("Log Out");

        //loginButton.addClickListener(e -> getUI().getPage().setLocation("/logout"));
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                List<UserDto> userDtos = service.findAll(new PageRequest(0, 5));
                System.out.println();
            }
        });
        return loginButton;
    }
}
