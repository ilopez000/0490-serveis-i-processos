package cat.pratfp.servihub.ra2.teoria;

public class FilAmbHerencia extends Thread {

    @Override
    public void run() {
        System.out.println("Hola des de " + getName());
    }

    public static void main(String[] args) {
        new FilAmbHerencia().start();
    }
}
