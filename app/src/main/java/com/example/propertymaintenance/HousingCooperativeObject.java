package com.example.propertymaintenance;

public class HousingCooperativeObject {
    private int housingID;
    private int propertyID;
    private String name;
    private String address;
    private String apartments;
    private String propertyManagement;
    private String wasteManagement;

    public HousingCooperativeObject(int housingID, int propertyID, String name, String address, String apartments, String propertyManagement, String wasteManagement) {
        this.housingID = housingID;
        this.propertyID = propertyID;
        this.name = name;
        this.address = address;
        this.apartments = apartments;
        this.propertyManagement = propertyManagement;
        this.wasteManagement = wasteManagement;
    }

    public int getHousingID() {
        return housingID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getApartments() {
        return apartments;
    }

    public String getPropertyManagement() {
        return propertyManagement;
    }

    public String getWasteManagement() {
        return wasteManagement;
    }


}
