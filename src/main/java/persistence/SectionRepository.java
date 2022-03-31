package persistence;

import domain.Section;

import java.util.Arrays;

public class SectionRepository implements GenericRepository<Section> {

    private Section[] sectionArray = new Section[5]; //number of slots available

    @Override
    public void add(Section section) {
        for (int i = 0; i < sectionArray.length; i++){
            if (sectionArray[i] == null) {
                sectionArray[i] = section;
                return;
            }
        }
        //if there's no more room, we extend the capacity
        Section[] newSections = Arrays.<Section,Section>copyOf(sectionArray, 2*sectionArray.length, Section[].class);
        newSections[sectionArray.length] = section;
        sectionArray = newSections;

    }

    @Override
    public Section get(int i) {
        return sectionArray[i];
    }

    @Override
    public void delete(Section section) {
        if (sectionArray == null){
            return;
        }

        Section[] newSections = new Section[sectionArray.length -1 ];
        int j = 0;
        for (int i = 0; i < sectionArray.length; i++) {
            if (!sectionArray[i].equals(section)){
                newSections[j] = sectionArray[i];
                j++;
            }
        }
        return;
    }

    @Override
    public int getSize() {
        return sectionArray.length;
    }
}
