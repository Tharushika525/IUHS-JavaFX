package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDatail {
    private String orderId;
    private String ItemCode;
    private Integer qty;
    private Double discount;
}
