package uc.no.identity.business;

import java.io.Serializable;

public class Car implements Serializable  {
    private String brand;

    public Car() {

    }
    
    public Car(String brand) {
       setBrand(brand);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String _brand) {
        brand = _brand;
    }
}
