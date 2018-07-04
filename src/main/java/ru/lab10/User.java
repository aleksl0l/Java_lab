package ru.lab10;

import java.util.Scanner;

class User {
    private int id;
    private String login;
    private String password;
    private String fio;
    private byte role;
    private int oklad;
    private int stavka;

    User(int _id, String _login, String _password, String _fio, byte _role, int _oklad, int _stavka) {
        this.id = _id;
        this.login = _login;
        this.password = _password;
        this.fio = _fio;
        this.role = _role;
        this.oklad = _oklad;
        this.stavka = _stavka;
    }

    public String[] toStringArray() {
        String[] user = new String[7];
        user[0] = Integer.toString(id);
        user[1] = login;
        user[2] = password;
        user[3] = fio;
        user[4] = Byte.toString(role);
        user[5] = Integer.toString(oklad);
        user[6] = Integer.toString(stavka);
        return user;
    }

    public boolean checkPassword(String _password) {
        return this.password.equals(_password);
    }

    public String getLogin() {
        return this.login;
    }

    public byte getRole() {return this.role;}

    @Override
    public String toString() {
        return String.format("%5s%10s%10s%25s%10d%10d", id, login, password, fio, oklad, stavka);
    }

    public void edit() {
        System.out.println("Введите поле, которое надо изменить:");
        String[] fields = new String[]{"Логин", "Пароль", "ФИО", "Роль", "Оклад", "Ставка"};
        int i = 1;
        for(String field : fields) {
            System.out.println(Integer.toString(i) + " " + field);
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        String newStr = "";
        int newInt = 0;
        System.out.println("Введите значение:");
        switch (action) {
            case 1:
            case 2:
            case 3:
                newStr = scanner.nextLine();
                break;
            case 4:
            case 5:
            case 6:
                newInt = scanner.nextInt();
                break;
        }
        switch (action) {
            case 1:
                this.login = newStr;
                break;
            case 2:
                this.password = newStr;
                break;
            case 3:
                this.fio = newStr;
                break;
            case 4:
                this.role = Integer.valueOf(newInt).byteValue();
                break;
            case 5:
                this.oklad = newInt;
                break;
            case 6:
                this.stavka = newInt;
                break;
        }
    }

    public static User addUser() {
        Scanner scanner = new Scanner(System.in);
        int id;
        String login;
        String password;
        String fio;
        byte role;
        int oklad;
        int stavka;
        System.out.println("Введите логин:");
        login = scanner.nextLine();
        System.out.println("Введите пароль:");
        password = scanner.nextLine();
        System.out.println("Введите fio:");
        fio = scanner.nextLine();
        System.out.println("Введите role:");
        role = scanner.nextByte();
        System.out.println("Введите oklad:");
        oklad = scanner.nextInt();
        System.out.println("Введите stavka:");
        stavka = scanner.nextInt();
        return new User(++Main.max_id, login, password, fio, role, oklad, stavka);
    }

    public void changeOffer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новое значение ставки:");
        this.stavka = scanner.nextInt();
    }
}
