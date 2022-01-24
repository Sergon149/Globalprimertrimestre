import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
    static AtomicInteger listos = new AtomicInteger(0);
    static AtomicInteger puestos = new AtomicInteger(2);
    static Semaphore s22 = new Semaphore(0,true);

    public static void main(String[] args) {
        Semaphore s1 = new Semaphore(10,true);
        Semaphore s2 = Main.s22;
        ReentrantLock s3 = new ReentrantLock();

        for (int i=1;i<=20;i++){
            Jugador j = new Jugador(i,s1,s2,s3);
            j.start();
        }
    }
    public synchronized static void puestoss(Jugador jugador) {
        int puesto=Main.puestos.get();

        if (puesto<=5){
            System.out.println("El jugador "+jugador.id+ " ha quedado en  la posicion "+ puesto);
        } else {
            System.out.println("El jugador "+jugador.id+ " no ha llegado a tiempo a la prueba 2 y ha sido descalificado");
        }

        Main.puestos.set(puesto+1);

    }

    public synchronized static void listoss() {
        Main.listos.set(Main.listos.get()+1);
        if (Main.listos.get() == 10){
            Main.s22.release(10);
        }

    }

}




