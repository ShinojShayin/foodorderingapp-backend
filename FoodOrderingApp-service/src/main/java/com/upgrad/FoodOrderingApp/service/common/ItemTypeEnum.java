package com.upgrad.FoodOrderingApp.service.common;

public enum ItemTypeEnum {

    VEG("VEG"),
    NON_VEG("NON_VEG");

    private String value;

    private ItemTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}