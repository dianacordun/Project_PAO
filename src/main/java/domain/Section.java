package domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Section {
    /** fields */
    private String sectionName;
    private int room;

    /** constructors */

    public Section(String sectionName, int room) {
        this.sectionName = sectionName;
        this.room = room;
    }

    /**  getters and setters */
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String toCSV() {
        return sectionName+","+room;
    }
}
