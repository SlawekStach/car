package com.view;

import com.image.ImageUpader;
import com.model.Fuel;
import com.repository.CarRepo;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@Route("addcar")
public class AddCarGui extends VerticalLayout {

private CarRepo carRepo;
private ImageUpader imageUpader;

@Autowired
    public AddCarGui(CarRepo carRepo, ImageUpader imageUpader) {
    this.carRepo = carRepo;
    this.imageUpader = imageUpader;


    AppLayout appLayout = new AppLayout();
    AppLayoutMenu menu = appLayout.createMenu();
    Image image = new Image("https://cdn3.iconfinder.com/data/icons/gardening-65/100/jeep4-512.png", "Car Logo");
    image.setHeight("100px");
appLayout.setBranding(image);

Collection<SimpleGrantedAuthority> authorities= (Collection<SimpleGrantedAuthority>) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getAuthorities();

if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
   || authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))){
    menu.addMenuItems(
            new AppLayoutMenuItem(VaadinIcon.CAR.create(), "Car list", "carlist"));
   }
menu.addMenuItems(
        new AppLayoutMenuItem(VaadinIcon.CAMERA.create(), "Photos", " "),
        new AppLayoutMenuItem(VaadinIcon.PHONE.create(), "Contact", "contact "));



TextField markTextField=new TextField("Mark");
markTextField.setWidth("200px");
TextField modelTextField=new TextField("Model");
modelTextField.setWidth("200px");

HorizontalLayout horizontalLayout=new HorizontalLayout(markTextField, modelTextField);

ComboBox<Fuel> fuelComboBox=new ComboBox<>("Fuel", Fuel.values());


VerticalLayout verticalLayout=new VerticalLayout(horizontalLayout);
verticalLayout.setSizeFull();
verticalLayout.setAlignItems(Alignment.CENTER);
appLayout.setContent(verticalLayout);

add(appLayout);

}


}
