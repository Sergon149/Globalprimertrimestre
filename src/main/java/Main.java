import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            Jugador j = new Jugador();
            j.setName("Hilo " + i);
            j.start();
        }
    }
}



class Jugador extends Thread{
    private boolean esPrimero;

    @Override
    public void run() {
        Random r = new Random();
        int dormir= r.nextInt((4)+1)*1000;
        int posib= r.nextInt(11);
        try {
            Thread.sleep(dormir);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (posib == 1){
            System.out.println("El jugador "+getName()+" ha sido descalificado de la prueba 1");
        }else{
            System.out.println("El jugador "+getName()+" ha superado la prueba 1");
            CampoDeBatalla.addParticipante(this);
        }
    }
    public void setEsPrimero(boolean esPrimero) {
        this.esPrimero = esPrimero;
    }
}



class CampoDeBatalla{

    private static final Semaphore semaphore = new Semaphore(10, false);
    static ReentrantLock lock = new ReentrantLock();
    private static int contpr=0;

    public static void addParticipante(Jugador jugador) {
        try {
            lock.lock();
            semaphore.acquire(10);
            lock.unlock();
            System.out.println(jugador.getName() + " ha completado a tiempo la puerba 1");
            semaphore.release(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Random r = new Random();
        int posib = r.nextInt(11);
        if (posib == 1) {
            System.out.println("El jugador " + jugador.getName() + " ha sido descalificado de la prueba 2");
        } else {
            System.out.println("El jugador " + jugador.getName() + " ha superado la prueba 2");
            contpr++;
            if (contpr == 1) {
                jugador.setEsPrimero(true);
                System.out.println("El jugador " + jugador.getName() + " ha ganado el Battle Royale de Warzone 2.0");
            }else if (contpr<6 & contpr>1){
                System.out.println("El jugador "+jugador.getName()+ " ha quedado en la posicion "+contpr+" del Battle Royale de Warzone 2.0");
            }else{
                System.out.println("El jugador "+jugador.getName()+" no ha llegado a tiempo a la prueba 2 y ha sido descalificado");
            }
        }

    }
}