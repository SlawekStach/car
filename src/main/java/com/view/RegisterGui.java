package com.view;

import com.repository.UserRepo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Route("register")
public class RegisterGui extends VerticalLayout {


  private UserRepo userRepo;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


@Autowired
    public RegisterGui(UserRepo userRepo) {
        this.userRepo = userRepo;



AppLayout appLayout=new AppLayout();
AppLayoutMenu menu=appLayout.createMenu();
Image image=new Image("https://cdn2.iconfinder.com/data/icons/modern-cars/512/car-automotive-vehicle-transportation-02-512.png", "Cars Logo ");
 image.setHeight("100px");
        appLayout.setBranding(image);


    Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();

    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
        menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.CAR.create(), "Car list", "carlist"));
    }
    menu.addMenuItems(
            new AppLayoutMenuItem(VaadinIcon.CAMERA.create(),"Photos", ""),
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

    TextField usernameField = new TextField("Username:");
    TextField nameField = new TextField("Name:");
    TextField surnameField = new TextField("Surname:");
    TextField emailField1 = new TextField("E-mail:");
    TextField emailField2 = new TextField("Confirm E-mail:");
    PasswordField password1 = new PasswordField("Password:");
    PasswordField password2 = new PasswordField("Confirm password:");

Div div = new Div();
div.setText("The password or email is not the same, please again!");

Div div1=new Div();
    div1.setText("User added successfully.");


    Notification errorNotyfication = new Notification(div);
    errorNotyfication.setPosition(Notification.Position.TOP_CENTER);
    errorNotyfication.setDuration(3000);

    Notification successNotyfication = new Notification(div1);
    successNotyfication.setPosition(Notification.Position.TOP_CENTER);
    successNotyfication.setDuration(3000);

Button register=new Button("Register ");


HorizontalLayout usernameHorizontalLayout=new HorizontalLayout(usernameField);
HorizontalLayout nameHorizontalLayout=new HorizontalLayout(nameField, surnameField);
HorizontalLayout emailHorizontalLayout=new HorizontalLayout(emailField1, emailField2);
HorizontalLayout passwordHorizontalLayout=new HorizontalLayout(password1, password2);
HorizontalLayout submitHorizontalLayout=new HorizontalLayout(register);


VerticalLayout verticalLayout=new VerticalLayout(usernameHorizontalLayout,
 nameHorizontalLayout, emailHorizontalLayout, passwordHorizontalLayout,
submitHorizontalLayout);

verticalLayout.setAlignItems(Alignment.CENTER);
Component component=new Span(verticalLayout);
appLayout.setContent(component);
add(appLayout);
}

}
