package rs.edu.raf.exchangeservice.domain.dto.buySell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuySellStockCompanyDto {
    //kada zelimo da kupimo Stock
    //kada zelimo da prodamo Stock
    private Long companyId;    //mora da stigne
    private String ticker;  //mora da stigne
    private Integer amount; //mora da stigne
    private Double limitValue;
    private Double stopValue;
    private boolean aon;
    private boolean margin;
}
