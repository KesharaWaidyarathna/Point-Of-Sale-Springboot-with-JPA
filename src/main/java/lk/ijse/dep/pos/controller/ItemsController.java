package lk.ijse.dep.pos.controller;


import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/Items")
@CrossOrigin
@RestController
public class ItemsController {

    @Autowired
    private ItemBO itemBO;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems(){
        List<ItemDTO> allItems = itemBO.findAllItems();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-count",allItems.size()+"");
        return new ResponseEntity<>(allItems,headers,HttpStatus.OK);
    }

//    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String id){
//        try {
//            CustomerDTO customer = customerBO.findCustomer(id);
//            return new ResponseEntity<>(customer, HttpStatus.OK);
//        }catch (NullPointerException e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping(value = "/{code}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDTO getItem(@PathVariable String code){
      try{
          return  itemBO.findItem(code);
      }catch (NullPointerException e){
          throw new NotFoundException(e);
      }
    }

    @ExceptionHandler({NotFoundException.class, javax.persistence.EntityNotFoundException.class})
    public  ResponseEntity handelNotFoundException(NotFoundException e){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(params ="q=last")
    public String getLastItemCode(){
        return itemBO.getLastItemCode();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveItem(@RequestBody ItemDTO item){
        itemBO.saveItem(item);
        return "\""+item.getCode()+"\"";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    public void deleteItem(@PathVariable String code){
        itemBO.deleteItem(code);
    }

    @PutMapping("/{code}")
    public  ResponseEntity updateItem(@PathVariable String code,@RequestBody ItemDTO item){
        if(code.equals(item.getCode())){
            itemBO.updateItem(item);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(params = "category=code")
    public List<String> getAllItemCodes(){
       return itemBO.getAllItemCodes();
    }
}
