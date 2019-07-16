package com.imooc.o2o.enums;

public enum ShopStateEnum {
    CHECK(0, "审核中"),
    OFFLINE(-1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "操作失败"),
    NULL_SHOP_ID(-1002, "ShopId为空"),
    NULL_SHOP_INFO(-1003, "输入了空信息");

    private int state;
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    @Override
    public String toString() {
        return "ShopStateEnum{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                '}';
    }

    public static ShopStateEnum stateOf(int state) {

        for (ShopStateEnum stateEnum : ShopStateEnum.values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }

        return null;
    }
}
