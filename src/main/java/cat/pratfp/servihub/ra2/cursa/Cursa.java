package cat.pratfp.servihub.ra2.cursa;

public class Cursa {

    public static void main(String[] args) {
        System.out.println("Comença la cursa! La porta el fil " + Thread.currentThread().getName());

        Thread anna  = new Thread(new Repartidor("Anna", 5), "fil-Anna");
        Thread biel  = new Thread(new Repartidor("Biel", 5), "fil-Biel");
        Thread carla = new Thread(new Repartidor("Carla", 5), "fil-Carla");

        anna.start();
        biel.start();
        carla.start();

        System.out.println("El main ja ha engegat els tres repartidors i continua.");
    }
}
