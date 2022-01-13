package io.gebes.utils;

import io.gebes.BrainfuckInterpreter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Loader {
    @NonNull
    String[] args;

    final File SCRIPTS_FOLDER = new File("./scripts");
    BrainfuckInterpreter brainfuckInterpreter;

    public void load()  {
        if (args.length != 0) {
            try {
                for (String arg : args) {
                    String content = loadScriptFile(new File(arg));
                    brainfuckInterpreter = new BrainfuckInterpreter(content);
                    brainfuckInterpreter.execute();
                    System.out.println();
                }
                return;
            }catch (Exception e){
                System.err.println("Could not open the file"+ "(" + e.getLocalizedMessage() + ")" + " but here you can choose a other option!");
            }
        }

        while (true) {
            String selectedFile = selectScriptFile();
            if (selectedFile.equals("mi")) {
                brainfuckInterpreter = new BrainfuckInterpreter(getInput());
            } else {
                File scriptFile = new File(selectedFile);
                try{
                    String content = loadScriptFile(scriptFile);
                    brainfuckInterpreter = new BrainfuckInterpreter(content);
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }

            brainfuckInterpreter.execute();
            System.out.println();
        }
    }


    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the code:");
        String data = scanner.nextLine();

        return data;
    }


    private String selectScriptFile() {
        SCRIPTS_FOLDER.mkdir();

        Chooser<String> chooser = new Chooser<>();

        chooser.add("manually input", "mi");

        for (File file : Objects.requireNonNull(SCRIPTS_FOLDER.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".brainfuck"))
                chooser.add(file.getName(), SCRIPTS_FOLDER + "/" + file.getName());
        }

        if (!chooser.hasSelectables()) {
            throw new RuntimeException("No scripts with the \".brainfuck\" extension in the \"scripts\" folder.");
        }

        return chooser.choose("Select a brainfuck script to execute: ");
    }

    private String loadScriptFile(File file) throws Error, IOException {
        StringBuilder contentBuilder = new StringBuilder();

        Stream<String> stream = Files.lines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));

        return contentBuilder.toString();
    }
}
