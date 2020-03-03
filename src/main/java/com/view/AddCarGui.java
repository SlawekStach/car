package com.view;

import com.image.ImageUpader;
import com.repository.CarRepo;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

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




add(appLayout);

}


}
