package com.airplanesoft.dms.ui.view;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.DevicePlatformDTO;
import com.airplanesoft.dms.dto.DeviceState;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.DevicePlatformService;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.ui.AdminUI;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.airplanesoft.dms.utils.URLConstants.DELIM;

@SpringView(name = DevicesView.VIEW_NAME, ui = AdminUI.class)
public class DevicesView extends VerticalLayout implements BaseView {
    static final String VIEW_NAME = "user-devices";

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private DevicePlatformService devicePlatformService;

    private Grid<DeviceDto> deviceGrid;
    private Label noDevicesLabel;

    Window subWindow;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String userId = event.getParameters().split(DELIM)[0];

        logger.info("Init " + VIEW_NAME + DELIM + userId);

        removeAllComponents();

        Button addDeviceBtn = new Button("Add device");

        if (NumberUtils.isParsable(userId)) {
            UserDto userDto = userService.getById(Integer.valueOf(userId));
            if (userDto != null) {
                logger.info(userDto);
                addComponent(new Label("User: " + userDto.getFirstName() + " " + userDto.getLastName()));
                addComponent(addDeviceBtn);
            }

            addDeviceBtn.addClickListener(action -> {
                logger.info("Add device button clicked.");
                subWindow = alert("Add Device", newDeviceContent(userDto));
                UI.getCurrent().addWindow(subWindow);
            });

            deviceGrid = new Grid<>();
            deviceGrid.addColumn(DeviceDto::getId).setId("deviceId").setCaption("Device ID");
            deviceGrid.addColumn(DeviceDto::getDevicePlatform).setId("devicePlatform").setCaption("Device Platform");
            deviceGrid.addColumn(DeviceDto::getDeviceState).setId("deviceState").setCaption("Device State");

            deviceGrid.addItemClickListener((ItemClickListener<DeviceDto>) e -> {
                logger.info("Grid item clicked.");
                SpringNavigator navigator = (SpringNavigator) getUI().getNavigator();
                navigator.navigateTo(DeviceView.VIEW_NAME + DELIM + e.getItem().getId() + DELIM + userId);
            });

            addComponents(deviceGrid);

            deviceGrid.setWidth(100.f, Unit.PERCENTAGE);
            setExpandRatio(deviceGrid, 1.f);

            noDevicesLabel = new Label("Looks like there are no devices yet.");
            noDevicesLabel.setStyleName(ValoTheme.LABEL_H2);
            noDevicesLabel.setVisible(false);

            addComponent(noDevicesLabel);
            setComponentAlignment(noDevicesLabel, Alignment.MIDDLE_CENTER);
            listDevices(Integer.parseInt(userId));

        } else {
            addComponent(new Label("User not found!: " + userId));
        }
    }

    private VerticalLayout newDeviceContent(UserDto userDto) {

        DeviceDto deviceDto = new DeviceDto();
        List<DevicePlatformDTO> devicePlatformDTOS = devicePlatformService.getAll();

        ComboBox<String> platformComboBox = new ComboBox<>("Platform");
        List<String> list = devicePlatformDTOS.stream().map(DevicePlatformDTO::getName).collect(Collectors.toList());
        platformComboBox.setItems(list);

        DevicePlatformDTO defDTO = devicePlatformDTOS.get(0);
        platformComboBox.setValue(defDTO.getName());
        deviceDto.setDevicePlatform(defDTO.getName());
        deviceDto.setDeviceState(DeviceState.INACTIVE.name());

        platformComboBox.addValueChangeListener(event -> {
            deviceDto.setDevicePlatform(event.getValue());
        });

        ComboBox<DeviceState> stateComboBox = new ComboBox<>("State");
        stateComboBox.clear();
        stateComboBox.setItems(DeviceState.values());
        stateComboBox.setValue(DeviceState.INACTIVE);
        stateComboBox.addValueChangeListener(event -> {
            deviceDto.setDeviceState(event.getValue().name());
        });

        Button button = new Button("Add");

        button.addClickListener(action -> {
            //save dto
            userService.saveDevice(userDto, deviceDto);
            subWindow.close();
            SpringNavigator navigator = (SpringNavigator) getUI().getNavigator();
            navigator.navigateTo(DevicesView.VIEW_NAME + DELIM + userDto.getId() + DELIM);
        });


        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(platformComboBox, stateComboBox, button);
        return layout;
    }

    private void listDevices(Integer userId) {
        logger.info("Listing devices.");
        long devicesCount = userService.getDevicesByUserId(userId, new PageRequest(0, Integer.MAX_VALUE)).size();
        if (devicesCount == 0) {
            deviceGrid.setVisible(false);
            noDevicesLabel.setVisible(true);
            return;
        }
        noDevicesLabel.setVisible(false);
        deviceGrid.setVisible(true);
        deviceGrid.setDataProvider(setUpFetchItems(userId), () -> (int) userService.countDevices(userId));
    }

    private Window alert(String title, Component content) {
        logger.info("Show alert. " + title);
        Window subWindow = new Window(title);
        subWindow.setHeight("400px");
        subWindow.setWidth("600px");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);
        subContent.addComponent(content);
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Grid.FetchItemsCallback<DeviceDto> setUpFetchItems(Integer userId) {
        return (Grid.FetchItemsCallback<DeviceDto>) (sortOrder, offset, limit) -> {
            List<Sort.Order> orders = sortOrder.stream()
                    .map(order -> new Sort.Order(order.getDirection() == SortDirection.ASCENDING
                            ? Sort.Direction.ASC
                            : Sort.Direction.DESC, order.getSorted()))
                    .collect(Collectors.toList());

            Sort sort = orders.isEmpty() ? null : new Sort(orders);

            Pageable pageable = new PageRequest(offset / limit, limit, sort);
            Set<DeviceDto> set = userService.getDevicesByUserId(userId, pageable);
            List<DeviceDto> deviceDtoList = new ArrayList<>(set);
            return deviceDtoList.subList(offset % limit, deviceDtoList.size()).stream();
        };
    }


}
