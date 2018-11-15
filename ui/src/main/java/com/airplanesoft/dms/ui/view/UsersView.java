package com.airplanesoft.dms.ui.view;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.ui.AdminUI;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringView(name = UsersView.VIEW_NAME, ui = AdminUI.class)
public class UsersView extends VerticalLayout implements BaseView {

    public static final String VIEW_NAME = "users-grid";

    @Autowired
    private UserService userService;

    //private final DeviceService deviceService;

    private Grid<UserDto> userGrid;
    private Label noUsersLabel;

//    @Autowired
//    public UsersView(DeviceService deviceService) {
//        this.deviceService = deviceService;
//    }

    @PostConstruct
    void init() {
        userGrid = new Grid<>();
        //userGrid.addColumn((ValueProvider<UserDto, String>) UserDto::getFirstName).setId("userName").setCaption("User Name");
        userGrid.addColumn(UserDto::getFirstName).setId("userName").setCaption("User Name");
        userGrid.addColumn(UserDto::getLastName).setId("lastName").setCaption("Last Name");
        userGrid.addColumn(userDto -> {
            return userDto.getJobPositions().toString();
        }).setId("jobPositions").setCaption("Job Positions");


        userGrid.addItemClickListener((ItemClickListener<UserDto>) event -> {
            SpringNavigator navigator = (SpringNavigator) getUI().getNavigator();
            navigator.navigateTo(UserView.VIEW_NAME + "/" + event.getItem().getId());
        });

//        UserDeviceFilterForm filterForm = new UserDeviceFilterForm(Arrays.asList("Active", "Inactive"));
//        addComponents(filterForm, userGrid);

        addComponents(userGrid);

//        filterForm.setWidth(100.f, Unit.PERCENTAGE);
        userGrid.setWidth(100.f, Unit.PERCENTAGE);
        setExpandRatio(userGrid, 1.f);

        //filterForm.setChangeHandler(this::listUser);


//        userGrid.addColumn(UserDto::getActivationDate, date -> DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
//            .withLocale(Locale.ENGLISH).format(date))
//            .setId("activationDate")
//            .setCaption("Activation date");
//        userGrid.addColumn(DeviceDto::getComment).setId("comment").setCaption("Comment");

//        userGrid.addItemClickListener((ItemClickListener<DeviceDto>) event -> {
//            SpringNavigator navigator = (SpringNavigator) getUI().getNavigator();
//            navigator.navigateTo(DeviceInfoView.VIEW_NAME + "/" + event.getItem().getDeviceId());
//        });

//        UserDeviceFilterForm filterForm = new UserDeviceFilterForm(Arrays.asList("Active", "Inactive"));
//        addComponents(filterForm, userGrid);

//        filterForm.setWidth(100.f, Unit.PERCENTAGE);
//        userGrid.setWidth(100.f, Unit.PERCENTAGE);
//        setExpandRatio(userGrid, 1.f);
//
//        filterForm.setChangeHandler(this::listUser);

        noUsersLabel = new Label("Looks like there are no users yet.");
        noUsersLabel.setStyleName(ValoTheme.LABEL_H2);
        noUsersLabel.setVisible(false);

        addComponent(noUsersLabel);
        setComponentAlignment(noUsersLabel, Alignment.MIDDLE_CENTER);
        listUsers();

        //listUser(new UserDto());
    }

    private void listUsers(){
        long userCount = userService.count();
        if (userCount == 0) {
            userGrid.setVisible(false);
            noUsersLabel.setVisible(true);

            return;
        }
        noUsersLabel.setVisible(false);
        userGrid.setVisible(true);
        userGrid.setDataProvider(setUpFetchItems(), () -> (int) userService.count());

        System.out.println();
    }



    private Grid.FetchItemsCallback<UserDto> setUpFetchItems() {
        return new Grid.FetchItemsCallback<UserDto>() {
            @Override
            public Stream<UserDto> fetchItems(List<QuerySortOrder> sortOrder, int offset, int limit) {
                List<Sort.Order> orders = sortOrder.stream()
                        .map(order -> new Sort.Order(order.getDirection() == SortDirection.ASCENDING
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC, order.getSorted()))
                        .collect(Collectors.toList());

                Sort sort = orders.isEmpty() ? null : new Sort(orders);

                Pageable pageable = new PageRequest(offset / limit, limit, sort);
                List<UserDto> list = userService.findAll(pageable);

                return list.subList(offset % limit, list.size()).stream();
            }
        };
    }
}
