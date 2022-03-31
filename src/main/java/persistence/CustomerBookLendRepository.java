package persistence;

import domain.CustomerBookLend;

import java.util.Arrays;

public class CustomerBookLendRepository implements GenericRepository<CustomerBookLend> {
    private CustomerBookLend[] CustomerBookLendArray = new CustomerBookLend[5];

    @Override
    public void add(CustomerBookLend entity) {
        for (int i = 0; i < CustomerBookLendArray.length; i++){
            if (CustomerBookLendArray[i] == null) {
                CustomerBookLendArray[i] = entity;
                return;
            }
        }
        //if there's no more room, we extend the capacity
        CustomerBookLend[] newCustomerBookLends = Arrays.<CustomerBookLend,CustomerBookLend>copyOf(CustomerBookLendArray, 2*CustomerBookLendArray.length, CustomerBookLend[].class);
        newCustomerBookLends[CustomerBookLendArray.length] = entity;
        CustomerBookLendArray = newCustomerBookLends;
    }

    @Override
    public CustomerBookLend get(int i) {
        return CustomerBookLendArray[i];
    }

    @Override
    public void delete(CustomerBookLend entity) {
        if (CustomerBookLendArray == null){
            return;
        }

        CustomerBookLend[] newCustomerBookLends = new CustomerBookLend[CustomerBookLendArray.length -1 ];
        int j = 0;
        for (int i = 0; i < CustomerBookLendArray.length; i++) {
            if (!CustomerBookLendArray[i].equals(entity)){
                newCustomerBookLends[j] = CustomerBookLendArray[i];
                j++;
            }
        }
        return;
    }

    @Override
    public int getSize() {
        return CustomerBookLendArray.length;
    }
}
