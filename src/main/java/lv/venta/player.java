package lv.venta;

public class player {

    private String username;
    private byte age;
    private enumGender gender;

    // getters
    public String getusername() {
        return username;
    }

    public byte getage() {
        return age;
    }

    public enumGender getgender() {
        return gender;
    }

    // setters
    public void setusername(String inputUsername) {
        username = inputUsername;
    }

    public void setage(byte inputage) {
        age = inputage;
    }

    public void setgender(enumGender inputgender) {
        gender = inputgender;
    }

    // konstruktors
    public player() {
        setusername("Player 1");
        setage((byte) 15);
        setgender(gender);
    }

    public player(String username, byte age, enumGender gender) {
        setusername(username);
        setage(age);
        setgender(gender);
    }

    // to string
    public String toString() {
        String results = username + " ( " + age + " y/o ) , " + gender;
        return results;
    }

    // papildus

}
