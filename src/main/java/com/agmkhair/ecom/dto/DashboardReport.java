package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardReport {
    String totalUser;
    String todayOrder;
    String totalOrder;
    String totalProducts;
    String todayRevenue;
    String pendingOrders;
    String deliveredOrder;
    String lowStock;
}
