package com.airplanesoft.dms.ui.view;


import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.DevicePlatformDTO;
import com.airplanesoft.dms.dto.DeviceState;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.DevicePlatformService;
import com.airplanesoft.dms.service.DeviceService;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.ui.AdminUI;
import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringView(name = DevicesView.VIEW_NAME, ui = AdminUI.class)
public class DevicesView extends VerticalLayout implements BaseView{
    static final String VIEW_NAME = "devices";

    @Autowired
    private UserService userService;

    @Autowired
    private DevicePlatformService devicePlatformService;

//    @Autowired
    private DeviceService deviceService;

    private Grid<DeviceDto> deviceGrid;
    private Label noDevicesLabel;

Window subWindow;
//    @PostConstruct
//    void init() {
//        deviceGrid = new Grid<>();
//        deviceGrid.addColumn(DeviceDto::getId).setId("deviceId").setCaption("Device ID");
//        deviceGrid.addColumn(DeviceDto::getDevicePlatform).setId("devicePlatform").setCaption("Device Platform");
//        deviceGrid.addColumn(DeviceDto::getDeviceState).setId("deviceState").setCaption("Device State");
//
//        deviceGrid.addItemClickListener((ItemClickListener<DeviceDto>) event -> {
//            SpringNavigator navigator = (SpringNavigator) getUI().getNavigator();
//            navigator.navigateTo(UserView.VIEW_NAME + "/" + event.getItem().getId());
//        });
//
//        addComponents(deviceGrid);
//
//        deviceGrid.setWidth(100.f, Unit.PERCENTAGE);
//        setExpandRatio(deviceGrid, 1.f);
//
//        noDevicesLabel = new Label("Looks like there are no devices yet.");
//        noDevicesLabel.setStyleName(ValoTheme.LABEL_H2);
//        noDevicesLabel.setVisible(false);
//
//        addComponent(noDevicesLabel);
//        setComponentAlignment(noDevicesLabel, Alignment.MIDDLE_CENTER);
//        //listDevices();
//    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String userId = event.getParameters().split("/")[0];
        removeAllComponents();

        Button addDeviceBtn = new Button("Add device");

        if (NumberUtils.isParsable(userId)) {
            UserDto userDto = userService.getById(Integer.valueOf(userId));
            if (userDto != null) {
                addComponent(new Label("User: " + userDto.getFirstName() + " " + userDto.getLastName()));
                addComponent(addDeviceBtn);
            }
        } else {
            addComponent(new Label("User not found!: " + userId));
        }

        addDeviceBtn.addClickListener(action -> {
            Navigator navigator = getUI().getNavigator();
            //navigator.navigateTo(DevicesView.VIEW_NAME + "/" + userId + "/");
            DeviceDto deviceDto;
            subWindow = alert("Add Device", newDeviceContent());

            UI.getCurrent().addWindow(subWindow);
        });

        deviceGrid = new Grid<>();
        deviceGrid.addColumn(DeviceDto::getId).setId("deviceId").setCaption("Device ID");
        deviceGrid.addColumn(DeviceDto::getDevicePlatform).setId("devicePlatform").setCaption("Device Platform");
        deviceGrid.addColumn(DeviceDto::getDeviceState).setId("deviceState").setCaption("Device State");

        deviceGrid.addItemClickListener((ItemClickListener<DeviceDto>) e -> {
            SpringNavigator navigator = (SpringNavigator) getUI().getNavigator();
            navigator.navigateTo(UserView.VIEW_NAME + "/" + e.getItem().getId());
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
    }

    private VerticalLayout newDeviceContent(){

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
            System.out.println(deviceDto);
            //save dto
            deviceService.save(deviceDto);
            subWindow.close();
        });


        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(platformComboBox, stateComboBox, button);
        return layout;
    }

    private void listDevices(Integer userId) {
        long devicesCount = userService.getDevicesByUserId(userId, new PageRequest(0, Integer.MAX_VALUE)).size();
        if (devicesCount == 0) {
            deviceGrid.setVisible(false);
            noDevicesLabel.setVisible(true);
            return;
        }
        noDevicesLabel.setVisible(false);
        deviceGrid.setVisible(true);
        deviceGrid.setDataProvider(setUpFetchItems(userId), () -> (int) deviceService.count());
    }

    Window alert(String title, Component content){
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
        return new Grid.FetchItemsCallback<DeviceDto>() {
            @Override
            public Stream<DeviceDto> fetchItems(List<QuerySortOrder> sortOrder, int offset, int limit) {


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
            }
        };
    }




}
