package io.gebes;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.Scanner;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BrainfuckInterpreter {

    @NonNull
    String code;


    Scanner scanner = new Scanner(System.in);
    final int LENGTH = 65535;
    final byte[] memory = new byte[65535];
    int pointer;

    public void execute(){
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '>') {
                this.pointer = (this.pointer == LENGTH-1) ? 0 : this.pointer + 1;
            } else if(code.charAt(i) == '<') {
                this.pointer = (this.pointer == 0) ? LENGTH-1 : this.pointer - 1;
            } else if(code.charAt(i) == '+') {
                memory[this.pointer]++;
            } else if(code.charAt(i) == '-') {
                memory[this.pointer]--;
            } else if(code.charAt(i) == '.') {
                System.out.print((char) memory[this.pointer]);
            } else if(code.charAt(i) == ',') {
                memory[this.pointer] = (byte) scanner.next().charAt(0);
            } else if(code.charAt(i) == '[') {
                if(memory[this.pointer] == 0) {
                    i++;
                    while(pointer > 0 || code.charAt(i) != ']') {
                        if(code.charAt(i) == '[') pointer++;
                        if(code.charAt(i) == ']') pointer--;
                        i++;
                    }
                }
            } else if(code.charAt(i) == ']') {
                if(memory[this.pointer] != 0) {
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

}
