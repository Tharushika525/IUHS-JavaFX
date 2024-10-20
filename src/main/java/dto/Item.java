package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Item {
    private String itemCode;
    private String description;
    private String size;
    private Double price;
    private Integer qty;
}
