package com.agmkhair.ecom.dto;

import com.agmkhair.ecom.entity.Orders;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AllOrdersResponse {
    private List<Orders> pendingOrder;
    private List<Orders> completeOrder;
    private List<Orders> returnOrder;
    private List<Orders> deliveredOrder;
    private List<Orders> cancelOrder;
    private List<Orders> paidOrder;

    private int totalOrder;
    private int totalPendingOrder;
    private int totalDeliveredOrder;
    private int totalCancelOrder;
    private int totalCompleteOrder;
    private int totalPaidOrder;
    private int totalReturnOrder;
}
