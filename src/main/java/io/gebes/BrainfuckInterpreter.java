package io.gebes;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Scanner;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BrainfuckInterpreter {

    @NonNull
    final
    String code;


    final Scanner scanner = new Scanner(System.in);
    final int length = 65535;
    byte[] memory;


    public void execute(){
        memory = new byte[length];
        int pointer = 0;
        int loopPointer = 0;
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '>') {
                pointer = (pointer == length -1) ? 0 : pointer + 1;
            } else if(code.charAt(i) == '<') {
                pointer = (pointer == 0) ? length -1 : pointer - 1;
            } else if(code.charAt(i) == '+') {
                memory[pointer]++;
            } else if(code.charAt(i) == '-') {
                memory[pointer]--;
            } else if(code.charAt(i) == '.') {
                System.out.print((char) memory[pointer]);
            } else if(code.charAt(i) == ',') {
                memory[pointer] = (byte) scanner.next().charAt(0);
            } else if(code.charAt(i) == '[') {
                if(memory[pointer] == 0) {
                    i++;
                    while(loopPointer > 0 || code.charAt(i) != ']') {
                        if(code.charAt(i) == '[') loopPointer++;
                        if(code.charAt(i) == ']') loopPointer--;
                        i++;
                    }
                }
            } else if(code.charAt(i) == ']') {
                if(memory[pointer] != 0) {
                    i--;
                    while(loopPointer > 0 || code.charAt(i) != '[') {
                        if(code.charAt(i) == ']') loopPointer++;
                        if(code.charAt(i) == '[') loopPointer--;
                        i--;
                    }
                    i--;
                }
            }
        }
    }


}
