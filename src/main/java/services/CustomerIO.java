package services;

import domain.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerIO implements GenericIO{
    private static final CustomerIO INSTANCE = new CustomerIO();
    private List<Customer> customers=new ArrayList<Customer>();

    private CustomerIO() {}

    public static CustomerIO getInstance() {
        return INSTANCE;
    }

    public void setCustomerStorage(List<Customer> Customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    private static List<String[]> getData(String file){
        List<String[]> data = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(file))) {

            String line;

            while((line = in.readLine()) != null ) {
                String[] fields = line.split(",");
                data.add(fields);
            }
        } catch (IOException e) {
            System.out.println("There were no customers saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
        var data = CustomerIO.getData("src/main/java/files/customer.csv");
        for(var fields : data){
            Boolean member = true;
            if (fields[4].equals("1")) //membership
                member = false;

            var all = new Customer(
                    fields[0], // first name
                    fields[1], // last name
                    fields[2], // phoneNumber
                    fields[3], //email
                    member
            );
            this.customers.add(all);
        }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("src/main/java/files/customer.csv");
            for(var customer : this.customers){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
