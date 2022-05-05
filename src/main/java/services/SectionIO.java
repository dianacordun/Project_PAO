package services;


import domain.Book;
import domain.Section;
import exceptions.NoDataFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SectionIO implements GenericIO {
    private static final SectionIO INSTANCE = new SectionIO();
    private List<Section> sections=new ArrayList<Section>();

    private SectionIO() {}

    public static SectionIO getInstance() {
        return INSTANCE;
    }

    public void setSectionStorage(List<Section> sections) {
        this.sections = sections;
    }

    public List<Section> getSections() {
        return this.sections;
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
            System.out.println("There were no sections saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
            var data = SectionIO.getData("src/main/java/files/section.csv");
            for(var fields : data){
                var all = new Section(
                        fields[0], // name
                        Integer.parseInt(fields[1]) //room
                );
                this.sections.add(all);
            }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("src/main/java/files/section.csv");
            for(var Section : this.sections){
                writer.write(Section.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
