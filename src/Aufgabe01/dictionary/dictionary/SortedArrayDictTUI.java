package Aufgabe01.dictionary.dictionary;


import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class SortedArrayDictTUI
{
    private static Dictionary<String, String> dictionary;
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
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
            default:

                break;
        }
    }

    private static void execHelp() {

        System.out.print("create:\t\t\t\t\t\t\tLegt ein Dictionary an. SortedArrayDictionary ist voreingestellt.\n");
        System.out.print("""
                read <n>:\t\t\t\t\t\tLiest (read) die ersten n Eintraege der Datei in das Dictionary ein.
                \t\t\t\t\t\t\t\tWird n weggelassen, dann werden alle Eintraege eingelesen.
                """);
        System.out.print("""
                print:\t\t\t\t\t\t\tGibt alle Eintraege des Dictionary in der Konsole aus.
                """);
        System.out.print("""
                search <deustch>:\t\t\t\tGibt das entsprechende englische Wort aus.
                """);
        System.out.print("""
                insert <deustch> <englisch>:\tFuegt ein neues Wortpaar in das Dictionary ein.
                """);
        System.out.print("""
                delete <deustch>:\t\t\t\tLoescht einen Eintrag.
                """);
        System.out.print("""
                exit:\t\t\t\t\t\t\tbeendet das Programm.
                """);

    }

    private static void execDelete(String[] args) {
        if (args.length != 1)
            return;
        dictionary.remove(args[0]);
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


        JFileChooser chooser = new JFileChooser();

        int rueckgabeWert = chooser.showOpenDialog(null);

        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            try {
                long startTime = System.nanoTime();
                BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
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
            }
        }
    }

    private static void execCreate(String[] args) throws Exception {

        if (args.length == 1) {
            dictionary = new SortedArrayDictionary<>();
            System.out.println("Sorted Array successfully created!");
        } else if (args.length == 2) {
            if (args[1].contains("hash")) {
                dictionary = new HashDictionary<>(3);
                System.out.println("Hash Dictionary successfully created");
            } else if (args[1].contains("binary")) {
                System.out.println("BinarySearchDictionary not implemented yet!\nExit with Code 1\n...");
                System.exit(1);
            }
        }
    }

    private static boolean isDictionaryInitialised() {
        return dictionary != null;
    }

    private static void execExit() {
        System.out.println("Thank you for using our Service!\nHave a good day");
        System.exit(0);
    }
}