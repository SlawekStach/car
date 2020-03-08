package com.view;

import com.repository.CarRepo;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vaadin.flow.component.UI;
import java.util.Collection;


@Route
public class ContactGui extends VerticalLayout {

private CarRepo carRepo;

@Autowired
    public ContactGui(CarRepo carRepo) {
        this.carRepo = carRepo;


AppLayout appLayout=new AppLayout();
AppLayoutMenu menu=appLayout.createMenu();
Image image=new Image("https://cdn1.iconfinder.com/data/icons/material-communication/18/phone-512.png", " Phone Logo");
  image.setHeight("100px");
        appLayout.setBranding(image);

 Collection<SimpleGrantedAuthority> authorities= (Collection<SimpleGrantedAuthority>) SecurityContextHolder
         .getContext().getAuthentication().getAuthorities();

 if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
 || authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))){
menu.addMenuItems(
        new AppLayoutMenuItem(VaadinIcon.CAR.create(), "Car list", "carlist"));
 }
menu.addMenuItems(
        new AppLayoutMenuItem(VaadinIcon.CAMERA.create(), "Photos" , " "),
        new AppLayoutMenuItem(VaadinIcon.PHONE.create(), "Contact", "contact"));

if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
    menu.addMenuItems(
            new AppLayoutMenuItem(VaadinIcon.PLUS.create(), "Add car", "addcar"));
}


if(authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))){
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


    Component componentName=new Span(new H3("Car"),
            new Span("Car Company"));

    Component contentPhone = new Span(new H3("Phone Number"),
            new Span("655-505-505"
            ));
    Component contentAddress = new Span(new H3("Address"),
            new Span("ul Prosta 60   Nowy Sacz , 33-326"
            ));


VerticalLayout verticalLayout=new VerticalLayout(componentName, contentPhone, contentAddress);
verticalLayout.setPadding(true);
verticalLayout.setWidth("500px");
verticalLayout.setJustifyContentMode(JustifyContentMode.START);

appLayout.setContent(verticalLayout);
add(appLayout);
}

}
