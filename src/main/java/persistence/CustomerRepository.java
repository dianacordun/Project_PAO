package persistence;

import domain.Customer;

import java.util.Arrays;

public class CustomerRepository implements GenericRepository<Customer> {
    private Customer[] CustomerArray = new Customer[5];

    @Override
    public void add(Customer entity) {
        for (int i = 0; i < CustomerArray.length; i++){
            if (CustomerArray[i] == null) {
                CustomerArray[i] = entity;
                return;
            }
        }
        //if there's no more room, we extend the capacity
        Customer[] newCustomers = Arrays.<Customer,Customer>copyOf(CustomerArray, 2*CustomerArray.length, Customer[].class);
        newCustomers[CustomerArray.length] = entity;
        CustomerArray = newCustomers;
    }

    @Override
    public Customer get(int i) {
        return CustomerArray[i];
    }

    @Override
    public void delete(Customer entity) {
        if (CustomerArray == null){
            return;
        }

        Customer[] newCustomers = new Customer[CustomerArray.length -1 ];
        int j = 0;
        for (int i = 0; i < CustomerArray.length; i++) {
            if (!CustomerArray[i].equals(entity)){
                newCustomers[j] = CustomerArray[i];
                j++;
            }
        }
        return;
    }

    @Override
    public int getSize() {
        return CustomerArray.length;
    }
}
