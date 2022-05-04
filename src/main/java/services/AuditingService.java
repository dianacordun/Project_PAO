package services;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class AuditingService {
    FileWriter writer;

    public AuditingService() {
        try{
            //we don't want the previous contents of the file deleted
            this.writer = new FileWriter("History.csv",true);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void addActionToHistory(String name_of_action) throws IOException {

        long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        writer.write((name_of_action + ','+ timestamp + '\n'));
        writer.close();

    }
}
