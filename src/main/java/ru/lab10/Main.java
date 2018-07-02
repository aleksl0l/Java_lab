package ru.lab10;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<String[]> credentials = new ArrayList<>();

    public static void main(String[] args) {
        Init();
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свою роль: \n1 - Администратор\n2 - Кассис\n3 - Логист\n4 - Бухгалтер\n5 - Отдел кадров");
        choice = scanner.nextInt();
        String login;
        String pass;
        scanner.nextLine();
        System.out.println("Введите логин:");
        login = scanner.nextLine();
        System.out.println("Введите пароль:");
        pass = scanner.nextLine();
        credentials.add(new String[]{login, pass, Integer.toString(choice)});
        WriteCredentials();

    }

    private static void Init() {
        try {
            ReadCredentials();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ReadCredentials() throws IOException {
        CSVReader credIn = null;
        try {
            credIn = new CSVReader(new InputStreamReader(new FileInputStream("credentials.csv"), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] line;
        if (credIn != null) {
            while((line = credIn.readNext()) != null) {
                credentials.add(line);
            }
        }
    }

    private static void WriteCredentials() {
        CSVWriter credOut;
        try {
            credOut = new CSVWriter(new OutputStreamWriter(new FileOutputStream("credentials.csv"), StandardCharsets.UTF_8));
            credOut.writeAll(credentials);
            credOut.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
