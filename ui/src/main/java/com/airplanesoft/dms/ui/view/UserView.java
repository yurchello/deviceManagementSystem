package com.airplanesoft.dms.ui.view;


import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.ui.AdminUI;
import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@SpringView(name = UserView.VIEW_NAME, ui = AdminUI.class)
public class UserView extends VerticalLayout implements BaseView{
    static final String VIEW_NAME = "users";

    @Autowired
    private UserService userService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String userId = event.getParameters().split("/")[0];
        removeAllComponents();

        if (NumberUtils.isParsable(userId)) {
            UserDto userDto = userService.getById(Integer.valueOf(userId));
            if (userDto != null) {
                addComponent(userLayout(userDto));
            }
        } else {
            addComponent(new Label("User not found!: " + userId));
        }
    }

    private VerticalLayout userLayout(UserDto userDto){
        VerticalLayout gridWithUserDetailsLabel = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        gridWithUserDetailsLabel.setMargin(false);
        Label deviceDetailsLabel = new Label("<b>User Details<b>", ContentMode.HTML);

        Button devicesButton = new Button("View assigned devices");
        devicesButton.addClickListener(action -> {
            Navigator navigator = getUI().getNavigator();
            navigator.navigateTo(DevicesView.VIEW_NAME + "/" + userDto.getId() + "/");
        });



        devicesButton.setStyleName(ValoTheme.BUTTON_LINK);

        GridLayout deviceGrid = new GridLayout(2, 5);
        deviceGrid.addComponent(new Label("User Name: "), 0, 0);
        deviceGrid.addComponent(new Label(userDto.getFirstName()), 1, 0);
        deviceGrid.addComponent(new Label("User LastName: "), 0, 1);
        deviceGrid.addComponent(new Label(userDto.getLastName()), 1, 1);
        deviceGrid.addComponent(new Label("Email: "), 0, 2);
        deviceGrid.addComponent(new Label(userDto.getEmail()), 1, 2);
        deviceGrid.addComponent(new Label("Job Positions: "), 0, 3);
        deviceGrid.addComponent(new Label(userDto.getJobPositions().toString()), 1, 3);
        deviceGrid.addComponent(devicesButton, 0, 4);

        deviceGrid.setSpacing(true);

        Button backButton = new Button("Back", FontAwesome.ARROW_LEFT);
        backButton.addClickListener((Button.ClickEvent action) -> getUI().getNavigator().navigateTo(UsersView.VIEW_NAME));
        backButton.setStyleName(ValoTheme.BUTTON_LINK);
        addComponent(backButton);

        horizontalLayout.addComponents(deviceGrid, backButton);
        horizontalLayout.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.setComponentAlignment(backButton, Alignment.TOP_CENTER);
        gridWithUserDetailsLabel.addComponents(deviceDetailsLabel, horizontalLayout);

        return  gridWithUserDetailsLabel;
    }

}
