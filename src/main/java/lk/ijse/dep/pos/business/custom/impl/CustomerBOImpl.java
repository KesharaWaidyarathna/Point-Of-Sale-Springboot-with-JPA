package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.CustomerBO;
import lk.ijse.dep.pos.dao.CustomerDAO;
import lk.ijse.dep.pos.dao.OrderDAO;
import lk.ijse.dep.pos.dto.CustomerDTO;
import lk.ijse.dep.pos.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO ;
    @Autowired
    private OrderDAO orderDAO ;

    @Override
    public void saveCustomer(CustomerDTO customer)  {

        customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));

    }

    @Override
    public void updateCustomer(CustomerDTO customer)  {
         customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));

    }

    @Override
    public void deleteCustomer(String customerId)  {
        if (orderDAO.existsByCustomerId(customerId)){
            throw new RuntimeException("Customer already exists in an order, hence unable to delete");
        }
         customerDAO.deleteById(customerId);

    }

    @Override
    @Transactional(readOnly = true)

    public List<CustomerDTO> findAllCustomers()  {
        List<Customer> alCustomers = customerDAO.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : alCustomers) {
            dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
        }
        return dtos;

    }

    @Override
    @Transactional(readOnly = true)
    public String getLastCustomerId()  {
        String lastCustomerId = customerDAO.getLastCustomerId();
        return lastCustomerId;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findCustomer(String customerId)  {
        Customer customer = customerDAO.findById(customerId).get();
        return new CustomerDTO(customer.getCustomerId(),
                customer.getName(), customer.getAddress());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCustomerIDs()  {
        List<Customer> customers = customerDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getCustomerId());
        }
        return ids;
    }
}
