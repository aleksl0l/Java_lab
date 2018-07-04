package ru.lab10;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Main {
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private User user_logged;
    public static int max_id = 0;

    private static Map<String, String> permission = new HashMap<>();


    public static void main(String[] args) {
        Main program = new Main();
        program.Init();
        while (true) {
            if (program.Login()){
                break;
            }
        }
        while(true) {
            if(!program.DoAction()) {
                break;
            }
        }
        program.WriteCredentials();
        program.WriteProducts();
    }

    private void Init() {
        try {
            ReadCredentials();
            ReadProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String[][] avaible_actions = {
//                {"get_users", "edit_users", "get_products", "edit_products"}, //администратор
//                {"get_products"}, //кассив
//                {"edit_graph"}, //логист
//                {"edit_user_off"}, //бухгалтер
//                {"get_users", "create_users", "delete_users"}
//        };
        permission.put("get_users", "Получить пользователей");
        permission.put("edit_users", "Изменить пользователя");
        permission.put("add_users", "Добавить пользователя");

        permission.put("get_products", "Распечатать товары");
        permission.put("add_products", "Добавить товары");
        permission.put("edit_products", "Изменить товар");
        permission.put("delete_products", "Удалить товар");

        permission.put("edit_graph", "Изменить график");
        permission.put("edit_user_off", "Изменить ставку пользователю");
        permission.put("create_users", "Создать пользователя");
        permission.put("delete_users", "Удалить пользователя");
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
                if (Main.max_id < Integer.valueOf(line[0])) {
                    Main.max_id = Integer.valueOf(line[0]);
                }
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

    private void WriteProducts() {
        CSVWriter productOut;
        try {
            productOut = new CSVWriter(new OutputStreamWriter(new FileOutputStream("products.csv"), StandardCharsets.UTF_8));
            for(Product product : this.products) {
                productOut.writeNext(product.toStringArray());
            }
            productOut.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean DoAction(){
        String[][] avaible_actions = {
                {"get_users", "edit_users", "add_users", "delete_users", "get_products", "add_products", "edit_products", "delete_products"}, //администратор 1
                {"get_products"}, //кассив 2
                {"edit_graph"}, //логист 3
                {"edit_user_off"}, //бухгалтер 4
                {"get_users", "add_users", "delete_users"} //упр персонала 5
        };
        byte role = user_logged.getRole();
        System.out.println("Выберите действие: ");
        int i = 1;

        for(String action : avaible_actions[role - 1]) {
            System.out.println(Integer.toString(i) + " " + permission.get(action));
            i++;
        }

        System.out.println("0 Выход из программы");
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        String verbal_action;
        if (action > 0 && action <= avaible_actions[role-1].length) {
            verbal_action = avaible_actions[role-1][action-1];
            Action(verbal_action);
            return true;
        }
        else if (action != 0) {
            System.out.println("Команда не распознана\n");
            return true;
        }
        return false;
    }

    private void Action(String action) {
        Scanner scanner = new Scanner(System.in);
        boolean edit;
        boolean delete;
        switch (action) {
            case "get_users":
                System.out.println(String.format("%5s%10s%10s%25s%10s%10s", "id", "login", "Пароль", "ФИО", "Оклад", "Ставка"));
                for(User user: users) {
                    System.out.println(user.toString());
                }
                break;
            case "edit_users":
                System.out.println("Введите логин пользователя, которого хотите изменить");
                String login = scanner.nextLine();
                edit = false;
                for(User user : users) {
                    if (login.equals(user.getLogin())) {
                        user.edit();
                        edit = true;
                        break;
                    }
                }
                if(!edit) {
                    System.out.println("Пользователя с таким логином не существует");
                }
                break;
            case "add_users":
                User newUser = User.addUser();
                users.add(newUser);
                break;
            case "delete_users":
                System.out.println("Введите логин пользователя, которого хотите удалить");
                String login_delete = scanner.nextLine();
                delete = false;
                for(User user : users) {
                    if (login_delete.equals(user.getLogin())) {
                        users.remove(user);
                        delete = true;
                        break;
                    }
                }
                if(!delete) {
                    System.out.println("Пользователя с таким логином не существует");
                }
                break;
            case "get_products":
                System.out.println(String.format("%10s%15s%15s%15s%15s%15s", "Артикул", "Название", "Количество", "Цена покупки", "Цена продажи", "Срок хранения"));
                for(Product product: products) {
                    System.out.println(product.toString());
                }
                break;
            case "edit_products":
                System.out.println("Введите артикул товара, которого хотите изменить");
                String articul = scanner.nextLine();
                edit = false;
                for(Product product : products) {
                    if (articul.equals(product.getArticul())) {
                        product.edit();
                        edit = true;
                        break;
                    }
                }
                if(!edit) {
                    System.out.println("Товара с таким артикулом не существует");
                }
                break;
            case "add_products":
                Product newProduct = Product.addProduct();
                products.add(newProduct);
                break;
            case "delete_products":
                System.out.println("Введите артикул товара, которого хотите удалить");
                String articul_delete = scanner.nextLine();
                delete = false;
                for(Product product : products) {
                    if (articul_delete.equals(product.getArticul())) {
                        products.remove(product);
                        delete = true;
                        break;
                    }
                }
                if(!delete) {
                    System.out.println("Товара с таким артикулом не существует");
                }
                break;
            case "edit_user_off":
                System.out.println("Введите логин пользователя, у которого хотите изменить ставку");
                String login_off = scanner.nextLine();
                delete = false;
                for(User user : users) {
                    if (login_off.equals(user.getLogin())) {
                        user.changeOffer();
                        delete = true;
                        break;
                    }
                }
                if(!delete) {
                    System.out.println("Пользователя с таким логином не существует");
                }
                break;

        }
    }
}
