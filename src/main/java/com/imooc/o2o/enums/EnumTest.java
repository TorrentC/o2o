package com.imooc.o2o.enums;

public enum EnumTest{
    Mon(1), Thu(2), Wed(3);

    private int value;

    EnumTest(int value) {
        this.value = value;
    }

    public static void main(String[] args) {
        for (EnumTest e : EnumTest.values()) {
            System.out.println(e.value);
        }
    }

}
