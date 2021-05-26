package io.gebes;

import java.util.Scanner;

public class BrainfuckInterpreter {
    private Scanner scanner = new Scanner(System.in);
    private final int LENGTH = 65535;
    private byte[] memory = new byte[65535];
    private int pointer;

    public void execute(String code){
        int l = 0;
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '>') {
                pointer = (pointer == LENGTH-1) ? 0 : pointer + 1;
            } else if(code.charAt(i) == '<') {
                pointer = (pointer == 0) ? LENGTH-1 : pointer - 1;
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
                    while(l > 0 || code.charAt(i) != ']') {
                        if(code.charAt(i) == '[') l++;
                        if(code.charAt(i) == ']') l--;
                        i++;
                    }
                }
            } else if(code.charAt(i) == ']') {
                if(memory[pointer] != 0) {
                    i--;
                    while(l > 0 || code.charAt(i) != '[') {
                        if(code.charAt(i) == ']') l++;
                        if(code.charAt(i) == '[') l--;
                        i--;
                    }
                    i--;
                }
            }
        }
    }

    public boolean check(String code){
        for(char s : code.toCharArray()){
            if(!(s=='<'||s=='>'||s=='+'||s=='-'||s=='['||s==']'||s==','||s=='.')){
                return false;
            }
        }
        return true;
    }
}
