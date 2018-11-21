package com.airplanesoft.dms.ui.view;

import com.airplanesoft.dms.ui.AdminUI;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;

@SpringView(name = AboutView.VIEW_NAME, ui = AdminUI.class)
public class AboutView extends VerticalLayout implements BaseView {

    public static final String VIEW_NAME = "about";

    private final Log logger = LogFactory.getLog(getClass());

    private String text = "<p>In practice, a web service commonly provides an object-oriented web-based interface to a database server,<br> " +
            "utilized for example by another web server, or by a mobile app, that provides a user interface to the end user. <br>" +
            "Many organizations that provide data in formatted HTML pages will also provide that data on their server as XML or JSON, <br>" +
            "often through a web service to allow syndication, for example Wikipedia's Export. Another application offered to the end user may <br>" +
            "be a mashup, where a web server consumes several web services at different machines, and compiles the content into one user interface.</p> ";

    @PostConstruct
    void init() {
        logger.info("Init " + VIEW_NAME);
        VerticalLayout layout = new VerticalLayout();
        Label area = new Label(text, ContentMode.HTML);
        layout.addComponent(area);
        addComponents(area);
    }
}
