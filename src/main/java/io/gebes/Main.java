package io.gebes;

import io.gebes.utils.Chooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {

    private static final File SCRIPTS_FOLDER = new File("./scripts");

    public static void main(String[] args) {

        if (args.length != 0) {
            for (String arg : args) {
                String content = loadScriptFile(new File(arg));
                executeScript(content);
            }
            return;
        }

        while (true) {
            File scriptFile = selectScriptFile();
            String content = loadScriptFile(scriptFile);
            executeScript(content);
        }

    }

    private static void executeScript(String script){
        BrainfuckInterpreter brainfuckInterpreter = new BrainfuckInterpreter(script);
        brainfuckInterpreter.execute();
        System.out.println();
    }

    private static File selectScriptFile() {
        SCRIPTS_FOLDER.mkdir();

        Chooser<File> chooser = new Chooser<>();

        for (File file : Objects.requireNonNull(SCRIPTS_FOLDER.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".brainfuck"))
                chooser.add(file.getName(), file);
        }

        if (!chooser.hasSelectables()) {
            throw new RuntimeException("No scripts with the \".brainfuck\" extension in the \"scripts\" folder.");
        }

        return chooser.choose("Select a brainfuck script to execute: ");
    }

    private static String loadScriptFile(File file) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file.getPath()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            System.err.println("Could not open the file " + file.getName() + " (" + e.getLocalizedMessage() + ")");
            System.exit(1);
        }

        return contentBuilder.toString();
    }

}