import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class Jugador extends Thread {
    int id;
    Semaphore s1;
    Semaphore s2;
    ReentrantLock s3;

    Jugador(int id,Semaphore s1,Semaphore s2,ReentrantLock s3){
        this.id=id;
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(((new Random().nextInt(3) ) + 1) * 1000);
            int muerte =  new Random().nextInt(10)+1;
            if (muerte > 9){
                System.out.println("El jugador "+this.id+ " ha sido descalificado en la prueba 1");
            }else {
                System.out.println("El jugador "+this.id+ " ha superado la prueba 1");
                if (s1.tryAcquire()){
                    System.out.println("El jugador "+this.id+ " ha completado a tiempo la prueba 1");
                    Main.listoss();
                    s2.acquire();
                    Thread.sleep(((new Random().nextInt(3) ) + 1) * 1000);
                    int muerte2 =  new Random().nextInt(10)+1;
                    if (muerte2 > 9){
                        System.out.println("El jugador "+this.id+ " ha sido descalificado en la prueba 2");
                    }else {
                        System.out.println("El jugador "+this.id+ " ha superado la prueba 2");
                        if(s3.tryLock()){
                            System.out.println("El jugador "+this.id+ " ha ganado ");
                        }else{
                            Main.puestoss(this);
                        }
                    }

                } else {
                    System.out.println("El jugador "+this.id+ " no ha completado a tiempo la prueba 1 y ha sido descalificado");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}