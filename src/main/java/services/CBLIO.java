package services;

import domain.CustomerBookLend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CBLIO implements GenericIO{
    private static final CBLIO INSTANCE = new CBLIO();
    private List<CustomerBookLend> cbls= new ArrayList<>();

    private CBLIO() {}

    public static CBLIO getInstance() {
        return INSTANCE;
    }

    public void setcblStorage(List<CustomerBookLend> cbls) {
        this.cbls = cbls;
    }

    public List<CustomerBookLend> getcbls() {
        return this.cbls;
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
            System.out.println("There was no data saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
        var data = CBLIO.getData("files/cbl.csv");

        for(var fields : data){
            Boolean returned = true;
            if (fields[4].equals("1")) //book has been returned
                returned = false;

            var all = new CustomerBookLend(
                    Integer.parseInt(fields[0]), // customer id
                    fields[1], // book ISBN
                    fields[2], // lend date
                    fields[3], // return/expected retrun date
                    returned
            );
            this.cbls.add(all);
        }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("files/cbl.csv");
            for(var cbl : this.cbls){
                writer.write(cbl.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
