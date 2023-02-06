package com.cjf.demo.enums;

/**
 * 指标枚举
 */
public enum IndexEnums {
    aakActual("AAK", "aakActual", "AAK实际数量", false, "AAK"),
    aakTarget("AAK", "aakTarget", "AAK目标数量", false, "AAK"),
    aakAchRate("AAK", "aakAchRate", "AAK达成率", true, "AAK"),
    OrderNumActual("AAK", "OrderNumActual", "新增订单实际数量", false, "新增订单"),
    OrderNumTarget("AAK", "OrderNumTarget", "新增订单目标数量", false, "新增订单"),
    newOrderAchRate("AAK", "newOrderAchRate", "新增订单达成率", true, "新增订单"),
    firstTimeCustomers("AAK", "firstTimeCustomers", "首次到店客流", false, "首次到店客流"),
    firstTimeCustomersTarget("AAK", "firstTimeCustomersTarget", "首次到店客流目标", false, "首次到店客流"),
    firstTimeCustomersAchRate("AAK", "firstTimeCustomersAchRate", "首次到店客流达成率", true, "首次到店客流"),
    onlineCustomers("AAK", "onlineCustomers", "线上客流", false, "线上客流"),
    onlineCustomersTarget("AAK", "onlineCustomersTarget", "线上客流目标", false, "线上客流"),
    onlineCustomersAchRate("AAK", "onlineCustomersAchRate", "线上客流达成率", true, "线上客流"),
    offlineCustomers("AAK", "offlineCustomers", "线下客流", false, "线下客流"),
    offlineCustomersTarget("AAK", "offlineCustomersTarget", "线下客流目标", false, "线下客流"),
    offlineCustomersAchRate("AAK", "offlineCustomersAchRate", "线下客流达成率", true, "线下客流"),
    orderReturnNum("AAK", "orderReturnNum", "退订量", false, "退订量"),
    inventoryOrderMatchingNum("AAK", "inventoryOrderMatchingNum", "库存订单匹配量", false, "库存订单匹配量"),
    entryStoreCustomers("AAK", "entryStoreCustomers", "自然进店客流", false, "展厅客流"),
    entryStoreCustomersTarget("AAK", "entryStoreCustomersTarget", "自然进店客流目标", false, "展厅客流"),
    entryStoreCustomersAchRate("AAK", "entryStoreCustomersAchRate", "自然进店流达成率", true, "展厅客流"),
    activitiesCustomers("AAK", "activitiesCustomers", "外展活动客流", false, "活动客流"),
    activitiesCustomersTarget("AAK", "activitiesCustomersTarget", "外展活动客流目标", false, "活动客流"),
    activitiesStoreCustomersAchRate("AAK", "activitiesStoreCustomersAchRate", "外展活动流达成率", true, "活动客流"),
    retainedOrders("AAK", "retainedOrders", "留存订单", false, "留存订单数"),
    saleClue("AAK", "saleClue", "销售线索数", false, "销售线索数"),
    firstTimeCustomersRate("AAK", "firstTimeCustomersRate", "首客订单率", false, "首客订单率"),
    firstTimeCustomersRateTarget("AAK", "firstTimeCustomersRateTarget", "首客订单率目标", false, "首客订单率"),
    firstTimeCustomersRateAchRate("AAK", "firstTimeCustomersRateAchRate", "首客订单达成率", true, "首客订单率"),
    arriveRate("AAK", "arriveRate", "邀约到店率", false, "邀约到店率"),
    arriveRateTarget("AAK", "arriveRateTarget", "邀约到店率目标", false, "邀约到店率"),
    arriveRateAchRate("AAK", "arriveRateAchRate", "邀约到店率达成率", true, "邀约到店率"),
    big3ArriveRate("AAK", "big3ArriveRate", "三大邀约到店率", false, "三大邀约到店率"),
    big3ArriveRateTarget("AAK", "big3ArriveRateTarget", "三大邀约到店率目标", false, "三大邀约到店率"),
    big3ArriveRateAchRate("AAK", "big3ArriveRateAchRate", "三大邀约到店率达成率", true, "三大邀约到店率"),
    small3ArriveRate("AAK", "small3ArriveRate", "三小邀约到店率", false, "三小邀约到店率"),
    small3ArriveRateTarget("AAK", "small3ArriveRateTarget", "三小邀约到店率目标", false, "三小邀约到店率"),
    small3ArriveRateAchRate("AAK", "small3ArriveRateAchRate", "三小邀约到店率达成率", true, "三小邀约到店率"),
    webAdArriveRate("AAK", "webAdArriveRate", "网络广告邀约到店率", false, "网络广告邀约到店率"),
    webAdArriveRateTarget("AAK", "webAdArriveRateTarget", "网络广告邀约到店率目标", false, "网络广告邀约到店率"),
    webAdArriveRateAchRate("AAK", "webAdArriveRateAchRate", "网络广告邀约到店率达成率", true, "网络广告邀约到店率"),
    firstTimeCustomersDoneRate("AAK", "firstTimeCustomersDoneRate", "首客成交率", false, "首客成交率"),
    stdActual("AAK", "stdActual", "STD实际数量", false, "STD"),
    stdTarget("AAK", "stdTarget", "STD目标数量", false, "STD"),
    stdAchRate("AAK", "stdAchRate", "STD达成率", true, "STD");

    /**
     * 指标类型
     */
    private String type;

    /**
     * 指标编码
     */
    private String code;

    /**
     * 指标名称
     */
    private String name;

    /**
     * 是否弱项指标
     */
    private Boolean isWeakIndex;

    /**
     * 弱项指标显示名称
     */
    private String weakIndexShowName;

    IndexEnums(String type, String code, String name, Boolean isWeakIndex, String weakIndexShowName) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.isWeakIndex = isWeakIndex;
        this.weakIndexShowName = weakIndexShowName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Boolean getWeakIndex() {
        return isWeakIndex;
    }

    public String getWeakIndexShowName() {
        return weakIndexShowName;
    }

    /**
     * 根据状态获取枚举对象
     *
     * @param code
     * @return 默认返回空，表示不指定类型
     */
    public static IndexEnums find(String code) {
        for (IndexEnums index : values()) {
            if (index.code.equals(code)) {
                return index;
            }
        }

        return null;
    }
}
