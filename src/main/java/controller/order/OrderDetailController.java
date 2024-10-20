package controller.order;

import dto.OrderDatail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public static boolean addOrderDetail(List<OrderDatail> orderDatails) throws SQLException {
        for(OrderDatail orderDatail : orderDatails){
            boolean isOrderDetailAdd = addOrderDetail(orderDatail);
            if(!isOrderDetailAdd){
                return false;
            }
        }
        return true;
    }

    public static boolean addOrderDetail(OrderDatail orderDatail) throws SQLException {
      return CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?,?)",
        orderDatail.getOrderId(),
        orderDatail.getItemCode(),
        orderDatail.getQty(),
        orderDatail.getDiscount()
        );
    }
}
