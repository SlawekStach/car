package com.view;

import com.image.ImageUpader;
import com.model.Car;
import com.model.CarType;
import com.model.Fuel;
import com.repository.CarRepo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
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

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                || authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            menu.addMenuItems(
                    new AppLayoutMenuItem(VaadinIcon.CAR.create(), "Car list", "carlist"));
        }
        menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.CAMERA.create(), "Photos", " "),
                new AppLayoutMenuItem(VaadinIcon.PHONE.create(), "Contact", "contact "));

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            menu.addMenuItems(
                    new AppLayoutMenuItem(VaadinIcon.PLUS.create(), "Add car", "addcar"));
        }

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            menu.addMenuItems(
                    new AppLayoutMenuItem(VaadinIcon.PLUS.create(), "Register", "register"));
            AppLayoutMenuItem appLayoutMenuItemLogin = new AppLayoutMenuItem(VaadinIcon.PLUS.create(), "Login");
            appLayoutMenuItemLogin.addMenuItemClickListener(menuItemClickEvent -> {
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


        TextField markTextField = new TextField("Mark");
        markTextField.setWidth("200px");
        TextField modelTextField = new TextField("Model");
        modelTextField.setWidth("200px");

        HorizontalLayout horizontalLayout = new HorizontalLayout(markTextField, modelTextField);

        ComboBox<Fuel> fuelComboBox = new ComboBox<>("Fuel", Fuel.values());

        NumberField yearProductionNumberField=new NumberField("Year of Production");
        yearProductionNumberField.setWidth("200px");
        yearProductionNumberField.setMin(1980);
        yearProductionNumberField.setMax(2020);
        yearProductionNumberField.setErrorMessage("Set year of production from 1980 to 2020");
        yearProductionNumberField.setHasControls(true);

        HorizontalLayout horizontalLayout1=new HorizontalLayout(fuelComboBox, yearProductionNumberField);
        ComboBox<CarType> carTypeComboBox=new ComboBox<>("CarType", CarType.values());
carTypeComboBox.setWidth("200px");

NumberField priceNumberField=new NumberField("Price");
priceNumberField.setWidth("200px");
priceNumberField.setWidth("200px");
priceNumberField.setSuffixComponent(new Span("zł"));
priceNumberField.setValue(100d);
priceNumberField.setMin(100);
priceNumberField.setMax(50000);
priceNumberField.setHasControls(true);
priceNumberField.setErrorMessage("Set price between 100 to 50 000");

HorizontalLayout horizontalLayout2=new HorizontalLayout(carTypeComboBox, priceNumberField);

TextField localTextField=new TextField("Loc.: (ex. C:\\Users\\user\\Downloads\\car.png)");
localTextField.setWidth("320px");

        Button button=new Button("Upload");
Image image1=new Image();
button.addClickListener(event->{
   String uploadImage=null;
    try {
        uploadImage = imageUpader
                .uploadFile(localTextField.getValue());
    } catch (IOException e) {
        e.printStackTrace();
    }
    image.setSrc(uploadImage);
    image.setMaxHeight("200px");
    image.setMaxWidth("200px");
});

HorizontalLayout horizontalLayout3=new HorizontalLayout(localTextField, button);
horizontalLayout3.setAlignItems(Alignment.END);

VerticalLayout addImage=new VerticalLayout(horizontalLayout3, image);
addImage.setAlignItems(Alignment.CENTER);
horizontalLayout3.setJustifyContentMode(JustifyContentMode.START);


        Label  info=new Label("Before click 'Add new car' click Upload to connect image with your car.");
Button addButton=new Button("Add new car");
Label not=new Label("You have added the car correctly");
        Notification notification=new Notification(not);
notification.setDuration(2000);
notification.setPosition(Notification.Position.MIDDLE);
addButton.addClickListener(event->{
    Car car=new Car();
    car.setMark(markTextField.getValue());
    car.setModel(modelTextField.getValue());
    car.setFuel(fuelComboBox.getValue());
    car.setYearProduction(yearProductionNumberField.getValue());
    car.setCarType(carTypeComboBox.getValue());
    car.setPrice(priceNumberField.getValue());
    try {
        car.setImageURL(imageUpader.uploadFile(localTextField.getValue()));
    } catch (IOException e) {
        e.printStackTrace();
    }
    carRepo.save(car);
    notification.open();
    markTextField.clear();
    modelTextField.clear();
    fuelComboBox.clear();
    yearProductionNumberField.clear();
    carTypeComboBox.clear();
    priceNumberField.clear();
    localTextField.clear();
    image.setSrc(" ");

});


        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout,
                horizontalLayout1, horizontalLayout2, horizontalLayout3, info, addButton);
        verticalLayout.setSizeFull();
        verticalLayout.setAlignItems(Alignment.CENTER);
        appLayout.setContent(verticalLayout);

        add(appLayout);

    }


}
