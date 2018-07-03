package ru.lab10;

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
}
