package com.airplanesoft.dms.ui.view;


import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.DeviceState;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.DeviceService;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.ui.AdminUI;
import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.airplanesoft.dms.utils.URLConstants.DELIM;

@SpringView(name = DeviceView.VIEW_NAME, ui = AdminUI.class)
public class DeviceView extends VerticalLayout implements BaseView {
    static final String VIEW_NAME = "devices";

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String deviceId = event.getParameters().split(DELIM)[0];
        String userId = event.getParameters().split(DELIM)[1];

        logger.info("Init " + VIEW_NAME + DELIM + deviceId + DELIM + userId);

        removeAllComponents();

        if (NumberUtils.isParsable(deviceId)) {
            DeviceDto deviceDto = deviceService.getById(Integer.valueOf(deviceId));
            logger.info(deviceDto);
            if (deviceDto != null) {
                addComponent(deviceLayout(deviceDto, Integer.valueOf(userId)));
            }
        } else {
            addComponent(new Label("Device not found!: " + deviceId));
        }
    }

    private VerticalLayout deviceLayout(DeviceDto deviceDto, Integer userId) {
        VerticalLayout gridWithUserDetailsLabel = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        gridWithUserDetailsLabel.setMargin(false);
        Label deviceDetailsLabel = new Label("<b>Device Details<b>", ContentMode.HTML);

        UserDto userDto = userService.getById(userId);
        Button deactivateBtn = new Button();

        DeviceState deviceState = DeviceState.valueOf(deviceDto.getDeviceState());
        switch (deviceState){
            case ACTIVE:
                deactivateBtn.setCaption("Deactivate Device");
                deactivateBtn.addClickListener(clickEvent -> {
                    deviceService.updateDeviceState(deviceDto.getId(), DeviceState.INACTIVE.name());
                    getUI().getNavigator().navigateTo(VIEW_NAME + DELIM + deviceDto.getId() + DELIM + userId);
                });
                break;
            case INACTIVE:
                deactivateBtn.setCaption("Activate Device");
                deactivateBtn.addClickListener(clickEvent -> {
                    deviceService.updateDeviceState(deviceDto.getId(), DeviceState.ACTIVE.name());
                    getUI().getNavigator().navigateTo(VIEW_NAME + DELIM + deviceDto.getId() + DELIM + userId);
                });
                break;
        }

        GridLayout deviceGrid = new GridLayout(2, 5);
        deviceGrid.addComponent(new Label("Device ID: "), 0, 0);
        deviceGrid.addComponent(new Label(deviceDto.getId().toString()), 1, 0);
        deviceGrid.addComponent(new Label("Device Platform: "), 0, 1);
        deviceGrid.addComponent(new Label(deviceDto.getDevicePlatform()), 1, 1);
        deviceGrid.addComponent(new Label("Device State: "), 0, 2);
        deviceGrid.addComponent(new Label(deviceDto.getDeviceState()), 1, 2);
        deviceGrid.addComponent(new Label("Assigned User: "), 0, 3);
        deviceGrid.addComponent(new Label(userDto.getFirstName() + " " + userDto.getLastName()), 1, 3);
        deviceGrid.addComponent(deactivateBtn, 0, 4);
        deviceGrid.setSpacing(true);

        Button backButton = new Button("Back", FontAwesome.ARROW_LEFT);
        backButton.addClickListener((Button.ClickEvent action) -> getUI().getNavigator().navigateTo(DevicesView.VIEW_NAME + DELIM + userId));
        backButton.setStyleName(ValoTheme.BUTTON_LINK);
        addComponent(backButton);

        horizontalLayout.addComponents(deviceGrid, backButton);
        horizontalLayout.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.setComponentAlignment(backButton, Alignment.TOP_CENTER);
        gridWithUserDetailsLabel.addComponents(deviceDetailsLabel, horizontalLayout);

        return gridWithUserDetailsLabel;
    }

}
