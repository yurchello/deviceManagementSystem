package com.airplanesoft.dms.ui.view;


import com.airplanesoft.dms.ui.AdminUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClientConnector;

public interface BaseView extends View, ClientConnector {

    @Override
    default void enter(ViewChangeListener.ViewChangeEvent event) {
       ((AdminUI) getUI()).putMenuState(event.getOldView(), event.getNewView());
    }
}
