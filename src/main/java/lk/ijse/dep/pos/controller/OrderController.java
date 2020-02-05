package lk.ijse.dep.pos.controller;



import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/orders")
@CrossOrigin
@RestController
public class OrderController {

    @Autowired
    private OrderBO orderBO;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes =MediaType.APPLICATION_JSON_VALUE)
    public Integer saveOrder(@RequestBody OrderDTO order ){
        orderBO.placeOrder(order);
        return order.getId();
    }

    @GetMapping(params = "q")
    public List<OrderDTO2> getOrderDetails(@RequestParam("q") String query){
        return orderBO.getOrderInfo(query);
    }


}
