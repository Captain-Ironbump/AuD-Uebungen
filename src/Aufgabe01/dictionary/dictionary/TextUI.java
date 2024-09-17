package Aufgabe01.dictionary.dictionary;


import javax.swing.*;

import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class TextUI
{
    private static JFrame frame;
    private static Dictionary<String, String> dictionary;
    private static Scanner scanner;
    
    public enum DictionaryType {
        ARR(1), HASH(2), BIN(3);

        private final int value;

        DictionaryType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static DictionaryType fromString(String dictString) {
            for (DictionaryType dictType : DictionaryType.values()) {
                if (dictType.toString().equalsIgnoreCase(dictString)) {
                    return dictType;
                }
            }
            throw new IllegalArgumentException("Dictionary not found!");
        }

    }

    public enum Measurement {
        SUCCESS(1), UNSUCCESS(2);

        private final int value;

        Measurement(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Measurement fromString(String measureString) {
            for (Measurement measureType : Measurement.values()) {
                if (measureType.toString().equalsIgnoreCase(measureString)) {
                    return measureType;
                }
            }
            throw new IllegalArgumentException("Measurement not found!");
        }
    }
    
    public static void main(String[] args) throws Exception {
        scanner = new Scanner(System.in);
        do
        {
            System.out.print(">> ");
            String rawInput = scanner.nextLine();
            if (rawInput.isEmpty())
                return;
            parseCommand(rawInput);
        } while (true);
    }

    private static void parseCommand(String commands) throws Exception {
        String[] args = commands.split(" ");

        switch (args[0]) {
            case "create":
                execCreate(args);
                break;
            case "read":
                if (isDictionaryInitialised())
                    execRead(Arrays.copyOfRange(args, 0, args.length));
                else
                    System.out.println("No dictionary initialised! Use 'create'");
                break;
            case "print":
                if (isDictionaryInitialised())
                    execPrint();
                else
                    System.out.println("No dictionary initialised! Use 'create'");
                break;
            case "search":
                if (isDictionaryInitialised())
                    execSearch(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("No dictionary initialised! Use 'create'");
                break;
            case "insert":
                if (isDictionaryInitialised())
                    execInsert(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("No dictionary initialised! Use 'create'");
                break;
            case "delete":
                if (isDictionaryInitialised())
                    execDelete(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("No dictionary initialised! Use 'create'");
                break;
            case "exit":
                execExit();
                break;
            case "help":
                execHelp();
                break;
            case "msmt":
                if (isDictionaryInitialised())
                    execMeasurement(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("No dictionary initialised! Use 'create'");
                break;
            default:
                break;
        }
    }

    private static void execMeasurement(String[] args) {
        if (args.length != 1) {
            System.out.println("please select the type of measurement!");
            System.out.println("Options are: 'success', 'unsuccess'");
            return;
        }

        Measurement measureType;
        try {
            measureType = Measurement.fromString(args[0]);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        switch (measureType) {
            case SUCCESS:
                List<String> keys = new ArrayList<>();
                for (var e : dictionary)
                    keys.add(e.getKey());
                final long timeStart = System.currentTimeMillis();
                for (String key : keys)
                    dictionary.search(key);
                final long timeEnd = System.currentTimeMillis();
                System.out.println((timeEnd - timeStart) + "ms");
                break;
            case UNSUCCESS:
                List<String> values = new ArrayList<>();
                for (var e : dictionary)
                    values.add(e.getValue());
                final long timeStart2 = System.currentTimeMillis();
                for (String value : values)
                    dictionary.search(value);
                final long timeEnd2 = System.currentTimeMillis();
                System.out.println((timeEnd2 - timeStart2) + "ms");
                break;
            default:
                break;
        }
    }

    private static void execHelp() {

        System.out.print("create:\t\t\t\t\t\t\tLegt ein Dictionary an. Optionen sind SortedArrayDictionary (arr), HashDictionary (hash) und BinaryDictionary (bin).\n");
        System.out.print("""
                read <n>:\t\t\t\t\t\tLiest (read) die ersten n Eintraege der Datei in das Dictionary ein.
                \t\t\t\t\t\t\tWird n weggelassen, dann werden alle Eintraege eingelesen.
                """);
        System.out.print("""
                print:\t\t\t\t\t\t\tGibt alle Eintraege des Dictionary in der Konsole aus.
                """);
        System.out.print("""
                search <deustch>:\t\t\t\t\tGibt das entsprechende englische Wort aus.
                """);
        System.out.print("""
                insert <deustch> <englisch>:\t\t\t\tFuegt ein neues Wortpaar in das Dictionary ein.
                """);
        System.out.print("""
                delete <deustch>:\t\t\t\t\tLoescht einen Eintrag.
                """);
        System.out.print("""
                exit:\t\t\t\t\t\t\tbeendet das Programm.
                """);

    }

    private static void execDelete(String[] args) {
        if (args.length != 1)
            return;
        String deletedEntry = dictionary.remove(args[0]);
        if (deletedEntry != null)
            System.out.println("successfully deleted!");
        else
            System.out.println("entry not found!");
    }

    private static void execInsert(String[] args) {
        if (args.length < 2)
            System.out.println("there are two words <deutsch> <englisch> needed in insert!");
        else if (args.length > 2)
            System.out.println("to many arguments in insert!");

        else {
            int oldSize = dictionary.size();
            dictionary.insert(args[0], args[1]);
            if (oldSize < dictionary.size())
                System.out.println("successfully inserted!");
        }
    }

    private static void execSearch(String[] args) {
        long startTime = System.nanoTime();
        System.out.println(dictionary.search(args[0]));
        long endTime = System.nanoTime();
        long timeTaken = (endTime - startTime);
        System.out.println("duration: " + timeTaken + "ns");
    }

    private static void execPrint() {
        for (var e : dictionary)
            System.out.println(e.getKey() + " - " + e.getValue());
    }

    private static void execRead(String[] args) throws Exception {
        if (args.length > 2) {
            System.out.println("to many arguments in read!");
            return;
        }

        JFileChooser chooser = new JFileChooser();

        int rueckgabeWert = chooser.showOpenDialog(frame);

        BufferedReader reader = null;

        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            try {
                long startTime = System.nanoTime();
                reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
                if (args.length == 1) {
                    while (true) {
                        String line = reader.readLine();
                        if (line != null) {
                            String[] lineArr = line.split(" ");
                            dictionary.insert(lineArr[0], lineArr[1]);
                            continue;
                        }
                        break;
                    }
                    long endTime = System.nanoTime();
                    long timeTaken = (endTime - startTime) / 1000000;
                    System.out.println("file read; duration: " + timeTaken + "ms");
                } else if (args.length == 2) {
                    int n = Integer.parseInt(args[1]);
                    if (n >= 1) {
                        for (int i = 0; i < n; i++) {
                            String line = reader.readLine();
                            if (line != null) {
                                String[] lineArr = line.split(" ");
                                dictionary.insert(lineArr[0], lineArr[1]);
                                continue;
                            }
                            break;
                        }
                        long endTime = System.nanoTime();
                        long timeTaken = (endTime - startTime) / 1000000;
                        System.out.println("file read; duration: " + timeTaken + "ms");
                    } else
                        System.out.println("n must be a positiv number greater 0!");
                }
            } catch (FileNotFoundException fe) {
                System.out.println("File not found");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
            }
        }
    }

    private static void execCreate(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("please provide type of dictionary [arr | hash | bin]!");
            return;
        }

        DictionaryType dictionaryType;
        try {
            dictionaryType = DictionaryType.fromString(args[1]);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("creating dictionary...");

        switch (dictionaryType) {
            case ARR:
                dictionary = new SortedArrayDictionary<>();
                System.out.println("Sorted Array successfully created!");
                break;
            case HASH:
                dictionary = new HashDictionary<>(3);
                System.out.println("Hash Dictionary successfully created");
                break;
            case BIN:
                dictionary = new BinaryTreeDictionary<>();
                System.out.println("Binary Dictionary successfully created");
                break;
            default:
                System.out.println("Dictionary not found!");
                break;
        }
    }

    private static boolean isDictionaryInitialised() {
        return dictionary != null;
    }

    private static void execExit() {
        scanner.close();
        System.out.println("Scanner closed!");
        System.out.println("Thank you for using our Service!\nHave a good day");
        System.exit(0);
    }
}