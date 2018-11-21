package com.airplanesoft.dms.ui.view;

import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.ui.AdminUI;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static com.airplanesoft.dms.utils.URLConstants.DELIM;

@SpringView(name = UsersView.VIEW_NAME, ui = AdminUI.class)
public class UsersView extends VerticalLayout implements BaseView {

    public static final String VIEW_NAME = "users-grid";

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;

    private Grid<UserDto> userGrid;
    private Label noUsersLabel;

    @PostConstruct
    void init() {

        logger.info("Init " + VIEW_NAME);

        userGrid = new Grid<>();
        userGrid.addColumn(UserDto::getFirstName).setId("userName").setCaption("User Name");
        userGrid.addColumn(UserDto::getLastName).setId("lastName").setCaption("Last Name");
        userGrid.addColumn(userDto -> userDto.getJobPositions().toString()).setId("jobPositions").setCaption("Job Positions");

        userGrid.addItemClickListener((ItemClickListener<UserDto>) event -> {
            logger.info("UserGrid item clicked.");
            SpringNavigator navigator = (SpringNavigator) getUI().getNavigator();
            navigator.navigateTo(UserView.VIEW_NAME + DELIM + event.getItem().getId());
        });

        addComponents(userGrid);

        userGrid.setWidth(100.f, Unit.PERCENTAGE);
        setExpandRatio(userGrid, 1.f);

        noUsersLabel = new Label("Looks like there are no users yet.");
        noUsersLabel.setStyleName(ValoTheme.LABEL_H2);
        noUsersLabel.setVisible(false);

        addComponent(noUsersLabel);
        setComponentAlignment(noUsersLabel, Alignment.MIDDLE_CENTER);
        listUsers();
    }

    private void listUsers() {
        logger.info("List users.");
        long userCount = userService.count();
        if (userCount == 0) {
            userGrid.setVisible(false);
            noUsersLabel.setVisible(true);

            return;
        }
        noUsersLabel.setVisible(false);
        userGrid.setVisible(true);
        userGrid.setDataProvider(setUpFetchItems(), () -> (int) userService.count());
    }

    private Grid.FetchItemsCallback<UserDto> setUpFetchItems() {
        return (Grid.FetchItemsCallback<UserDto>) (sortOrder, offset, limit) -> {
            List<Sort.Order> orders = sortOrder.stream()
                    .map(order -> new Sort.Order(order.getDirection() == SortDirection.ASCENDING
                            ? Sort.Direction.ASC
                            : Sort.Direction.DESC, order.getSorted()))
                    .collect(Collectors.toList());

            Sort sort = orders.isEmpty() ? null : new Sort(orders);

            Pageable pageable = new PageRequest(offset / limit, limit, sort);
            List<UserDto> list = userService.findAll(pageable);

            return list.subList(offset % limit, list.size()).stream();
        };
    }
}
