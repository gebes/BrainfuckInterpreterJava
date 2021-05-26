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
    byte[] memory = new byte[length];
    int pointer;

    public void execute(){
        reset();
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
                    while(pointer > 0 || code.charAt(i) != ']') {
                        if(code.charAt(i) == '[') pointer++;
                        if(code.charAt(i) == ']') pointer--;
                        i++;
                    }
                }
            } else if(code.charAt(i) == ']') {
                if(memory[pointer] != 0) {
                    i--;
                    while(pointer > 0 || code.charAt(i) != '[') {
                        if(code.charAt(i) == ']') pointer++;
                        if(code.charAt(i) == '[') pointer--;
                        i--;
                    }
                    i--;
                }
            }
        }
    }

    private void reset(){
        pointer = 0;
        memory = new byte[length];
    }

}
