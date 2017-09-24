
public class Hello implements ISay, IGreet {

    private static final String TAG = "Hello";
    public String vStr;

    public void doSay() {
        System.out.println("Hello dex.");
    }

    @Override
    public void greet() {

    }

    @Override
    public void sayHello() {
        doSay();
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.sayHello();
    }
}