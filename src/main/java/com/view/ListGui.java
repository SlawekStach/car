package com.view;

import com.model.Car;
import com.model.CarBuilder;
import com.model.CarType;
import com.model.Fuel;
import com.repository.CarRepo;
import com.repository.UserRepo;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;


@Route("carlist")
public class ListGui extends VerticalLayout {


private CarRepo carRepo;
private UserRepo userRepo;

@Autowired
    public ListGui(CarRepo carRepo, UserRepo userRepo) {
        this.carRepo = carRepo;
        this.userRepo = userRepo;


    AppLayout appLayout = new AppLayout();
    AppLayoutMenu menu = appLayout.createMenu();
    Image img = new Image("https://cdn0.iconfinder.com/data/icons/automotive/128/CARS-512.png", "Cars Logo");
    img.setHeight("100px");
    appLayout.setBranding(img);

    Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
        menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.CAR.create(), "Car list", "carlist"));
    }
    menu.addMenuItems(
            new AppLayoutMenuItem(VaadinIcon.CAMERA.create(), "Photos", ""),
            new AppLayoutMenuItem(VaadinIcon.PHONE.create(), "Contact", "contact"));

    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
        menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.PLUS.create(), "Add car", "addcar"));
    }

    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
        menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.PLUS.create(), "Register", "register"));
        AppLayoutMenuItem appLayoutMenuItemLogin = new AppLayoutMenuItem(VaadinIcon.PLUS.create(), "Login");
        appLayoutMenuItemLogin.addMenuItemClickListener(menuItemClickEvent ->
        {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/login\", \"_self\");");
        });
        menu.addMenuItems(appLayoutMenuItemLogin);
    }
    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
        AppLayoutMenuItem appLayoutMenuItemLogin = new AppLayoutMenuItem(VaadinIcon.EXIT.create(), "Logout");
        appLayoutMenuItemLogin.addMenuItemClickListener(menuItemClickEvent ->
        {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/logout\", \"_self\");");
        });
        menu.addMenuItems(appLayoutMenuItemLogin);

    }

Grid<Car> carGrid=new Grid<>(Car.class);
    List<Car> cars=carRepo.findAll();
    carGrid.setItems(cars);
    carGrid.setHeightFull();

carGrid.removeColumnByKey("id");


cars.add(new Car());


    carGrid.setItemDetailsRenderer(TemplateRenderer.<Car>of(" "
            //TODO
//                    "<div class='custom-details' style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
//                    + "<table>\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td style=\"width: 300px;\" ><img src=\"[[item.imageURL]]\" style=\"max-height:250px;max-width:250px\"></td>\n"+
//            "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>" +
//                    "</div>"
            )
    .withProperty("mark",car -> car.getMark() )
    .withProperty("model", car -> car.getModel())
    .withProperty("fuel", car -> car.getFuel())
    .withProperty("yearProduction", car -> car.getYearProduction())
    .withProperty("carType", car -> car.getCarType())
    .withProperty("price", car -> car.getPrice())
    .withProperty("rent", car -> car.isRent())
    .withProperty("image", car -> car.getImageURL())
    );


appLayout.setContent(carGrid);
add(appLayout);

}





}
