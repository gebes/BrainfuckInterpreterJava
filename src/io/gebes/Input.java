package io.gebes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Input {
    private Scanner scanner;
    private final BrainfuckInterpreter brainfuckInterpreter = new BrainfuckInterpreter();

    private void get(){
        scanner = new Scanner(System.in);
        System.out.println("Enter the code:");
        String data = scanner.nextLine();


        if(brainfuckInterpreter.check(data)){
            brainfuckInterpreter.execute(data);
        }else{
            System.out.println("Sry, this is no valid brainfuck code!");
            get();
        }
        scanner.close();
    }

    private void readFile(String file){
        try {
            String data = "";
            scanner = new Scanner(new File(file));

            while (scanner.hasNextLine()) {
                data += scanner.nextLine();
            }
            scanner.close();

            if(brainfuckInterpreter.check(data)){
                brainfuckInterpreter.execute(data);
            }else{
                System.out.println("Sry, this is no valid brainfuck code! But you can enter it manually!");
                get();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Sry, file not found, but you can type in your code manually.");
            get();
        }
    }

    public Input(String[] args){
        if(args.length == 0){
            get();
        }else{
            readFile(args[0]);
        }
    }
}
