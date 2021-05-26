package io.gebes.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

/**
 * Simple Command Line Chooser which lets the user select
 * on of the provides options
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Chooser<Option> {

    private static Scanner scanner = new Scanner(System.in);

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    @AllArgsConstructor
    private class Selectable {
        String name;
        Option option;
    }

    List<Selectable> selectables = new LinkedList<>();

    public void add(String name, Option value){
        selectables.add(new Selectable(name, value));
    }

    public boolean hasSelectables(){
        return !selectables.isEmpty();
    }

    /**
     *
     * @param message to print as input
     * @return selected option
     */
    public Option choose(String message){

        for (int i = 0; i < selectables.size(); i++) {
            System.out.println(" " + (i+1) + ".) " + selectables.get(i).getName());
        }

        System.out.print(message);
        int selection = 0;

        do{
            selection = scanner.nextInt()-1;
        }while(selection < 0 || selection >= selectables.size());

        return selectables.get(selection).getOption();
    }


}
