package ru.lab10;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private User user_logged;

    public static void main(String[] args) {
        Main program = new Main();
        program.Init();
        while (true) {
            if (program.Login()){
                break;
            }
        }
        program.WriteCredentials();
    }

    private void Init() {
        try {
            ReadCredentials();
            ReadProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean Login() {
        Scanner scanner = new Scanner(System.in);
        String login;
        String pass;
        System.out.println("Введите логин:");
        login = scanner.nextLine();
        System.out.println("Введите пароль:");
        pass = scanner.nextLine();
        User userl = null;
        for(User user : users) {
            if (user.getLogin().equals(login)) {
                userl = user;
                break;
            }
        }
        boolean ret = false;
        if (userl != null) {
            ret = userl.checkPassword(pass);
            if (ret) {
                this.user_logged = userl;
                System.out.println("Вы залогенны как " + userl.getLogin());
            }
        }
        if (!ret) {
            System.out.println("Ошибка. Логин или пароль недействительны!");
        }
        return ret;
    }

    private void ReadCredentials() throws IOException {
        CSVReader credIn = null;
        try {
            credIn = new CSVReader(new InputStreamReader(new FileInputStream("users.csv"), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] line;
        if (credIn != null) {
            while((line = credIn.readNext()) != null) {
                User user = new User(
                        Integer.valueOf(line[0]),
                        line[1],
                        line[2],
                        line[3],
                        Byte.valueOf(line[4]),
                        Integer.valueOf(line[5]),
                        Integer.valueOf(line[6])) ;
                this.users.add(user);
            }
        }
    }

    private void ReadProduct() throws IOException {
        CSVReader productIn = null;
        try {
            productIn = new CSVReader(new InputStreamReader(new FileInputStream("products.csv"), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] line;
        if (productIn != null) {
            while((line = productIn.readNext()) != null) {
                Product product = new Product(
                        line[0],
                        line[1],
                        Integer.valueOf(line[2]),
                        Integer.valueOf(line[3]),
                        Integer.valueOf(line[4]),
                        line[5]) ;
                this.products.add(product);
            }
        }
    }

    private void WriteCredentials() {
        CSVWriter credOut;
        try {
            credOut = new CSVWriter(new OutputStreamWriter(new FileOutputStream("users.csv"), StandardCharsets.UTF_8));
            for(User user : this.users) {
                credOut.writeNext(user.toStringArray());
            }
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

    private void DoAction(){
        String[][] avaible_actions = {
                {"get_users", "edit_users", "get_products", "edit_prducts"},
                {"get_products"},
                {"edit_users_off"}

        };
    }
}
