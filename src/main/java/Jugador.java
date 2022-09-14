import java.lang.Math;

public class Jugador {
  private int columna, offset_columna;    // Variables necesarias para realizar disparos
  private int fila, offset_fila;
  private boolean proximo=false;
  private int direccion;
  private int result;


  // Colocamos todos los barcos de forma correcta
  public void colocar_barcos(Mundo mundo){
    int columna = (int)Math.round((Math.random()*10)%9);
    int fila = (int)Math.round((Math.random()*10)%9);
    int direccion = (int)Math.round( ((Math.random()*10)%2)+1 );
    // Mientras el barco no haya sido colocado correctamente, calculamos nuevas posibles posiciones correctas
    while (mundo.colocar_trans(fila, columna, direccion) != 0) {
      columna = (int)Math.round((Math.random()*10)%9);
      fila = (int)Math.round((Math.random()*10)%9);
      direccion = (int)Math.round( ((Math.random()*10)%2)+1 );
    }
    // Mientras el barco no haya sido colocado correctamente, calculamos nuevas posibles posiciones correctas
    while (mundo.colocar_yate1(fila, columna, direccion) != 0) {
      columna = (int)Math.round((Math.random()*10)%9);
      fila = (int)Math.round((Math.random()*10)%9);
      direccion = (int)Math.round( ((Math.random()*10)%2)+1 );
    }
    // Mientras el barco no haya sido colocado correctamente, calculamos nuevas posibles posiciones correctas
    while (mundo.colocar_yate2(fila, columna, direccion) != 0) {
      columna = (int)Math.round((Math.random()*10)%9);
      fila = (int)Math.round((Math.random()*10)%9);
      direccion = (int)Math.round( ((Math.random()*10)%2)+1 );
    }
    // Mientras el barco no haya sido colocado correctamente, calculamos nuevas posibles posiciones correctas
    while (mundo.colocar_submarino1(fila, columna) != 0) {
      columna = (int)Math.round((Math.random()*10)%9);
      fila = (int)Math.round((Math.random()*10)%9);
    }
    // Mientras el barco no haya sido colocado correctamente, calculamos nuevas posibles posiciones correctas
    while (mundo.colocar_submarino2(fila, columna) != 0) {
      columna = (int)Math.round((Math.random()*10)%9);
      fila = (int)Math.round((Math.random()*10)%9);
    }
    // Mientras el barco no haya sido colocado correctamente, calculamos nuevas posibles posiciones correctas
    while (mundo.colocar_submarino3(fila, columna) != 0) {
      columna = (int)Math.round((Math.random()*10)%9);
      fila = (int)Math.round((Math.random()*10)%9);
    }
  }

  // Comprueba si hay barcos tocados o hundidos (los unicos que debemos poder ver) al rededor
  // de una posicion
  private boolean barco_en_el_borde(int fila, int columna, Mundo mundo) {
    for (offset_columna=-1; offset_columna <2; offset_columna++){
      for (offset_fila=-1; offset_fila <2; offset_fila++){
        // Comprobamos que no excedemos el rango
        if ((fila+offset_fila < 10) && (fila+offset_fila>-1) && (columna+offset_columna < 10) && (columna+offset_columna > -1)){
          // Si hay ningun Transatlantico, Yate o Submarino hundido o tocado devolvemos true
          if ((mundo.estado(fila+offset_fila, columna+offset_columna)==11) || (mundo.estado(fila+offset_fila, columna+offset_columna)==21) || (mundo.estado(fila+offset_fila, columna+offset_columna)==31) || (mundo.estado(fila+offset_fila, columna+offset_columna)==12) || (mundo.estado(fila+offset_fila, columna+offset_columna)==22) || (mundo.estado(fila+offset_fila, columna+offset_columna)==32)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  // Realiza un disparo inteligente, si hace blanco busca por esa zona el resto del barco y no realiza
  // tiros justo al lado de un barco hundido ya que al lado no esta permitido colocar otros barcos.
  public void disparar(Mundo mundo){
    if (!proximo){    // Si el siguiente blanco correcto no esta proximo a la posicion actual
      offset_columna = offset_fila = 0;
      do{   // buscamos una posicion buen para disparar
        do{
          columna = (int)Math.round((Math.random()*10)%9);
          fila = (int)Math.round((Math.random()*10)%9);
        }while (barco_en_el_borde(fila, columna, mundo));   // que no tenga barco cerca
        result=mundo.disparo(fila, columna);
      } while (result==4);  // Mientras disparemos a un sitio donde ya habiamos disparado
      System.out.println(">> Disparo: " + (char)(fila+97) + columna);
    

      if ((result == 0) || (result == 2)){   // Agua o Hundido
        System.out.println("");
        return;
      }

      if (result == 1){   // Tocado
        proximo=true;
        direccion=(int)Math.round(((Math.random()*10)%5)+1);    // Empezamos a buscar en una direccion aleatoria (1-6)
      }
    }
    // Mientras tocado o disparemos a un sitio donde ya habiamos disparado o haya algun error
    while ((result == 1) || (result == 4) || (result == 3)){ 
      switch (direccion){
        case 1:   // Hacia derecha
          offset_columna++;
          result=mundo.disparo(fila, columna+offset_columna);
          if ((result == 4) || (result == 3)){    // Si ya hemos disparado en ese lugar, estamos en una direccion incorrecta
            direccion = ((direccion)%6)+1;    // Siguiente direccion sin salirnos del rango (1-6)
            offset_columna = offset_fila = 0;
          }
          System.out.println(">> Disparo: " + (char)(fila+97) + (columna+offset_columna));
          break;
        case 2:   // Hacia izquierda
          offset_columna--;
          result=mundo.disparo(fila, columna+offset_columna);
          if ((result == 4)  || (result == 3)){    // Si ya hemos disparado en ese lugar, estamos en una direccion incorrecta
            direccion = ((direccion)%6)+1;;   // Siguiente direccion sin salirnos del rango (1-6)
            offset_columna = offset_fila = 0;
          }
          System.out.println(">> Disparo: " + (char)(fila+97) + (columna+offset_columna));
          break;
        case 3:   // Hacia arriba
          offset_fila--;
          result=mundo.disparo(fila+offset_fila, columna);
          if ((result == 4) || (result == 3)){    // Si ya hemos disparado en ese lugar, estamos en una direccion incorrecta
            direccion = ((direccion)%6)+1;;   // Siguiente direccion sin salirnos del rango (1-6)
            offset_columna = offset_fila = 0;
          }
          System.out.println(">> Disparo: " + (char)(fila+97+offset_fila) + columna);
          break;
        case 4:   // Hacia abajo
          offset_fila++;
          result=mundo.disparo(fila+offset_fila, columna);
          if ((result == 4) || (result == 3)){    // Si ya hemos disparado en ese lugar, estamos en una direccion incorrecta
            direccion = ((direccion)%6)+1;;   // Siguiente direccion sin salirnos del rango (1-6)
            offset_columna = offset_fila = 0;
          }
          break;
        case 5:   // Diagonal ascendente
          offset_fila--;
          offset_columna++;
          result=mundo.disparo(fila+offset_fila, columna+offset_columna);
          if ((result == 4) || (result == 3)){    // Si ya hemos disparado en ese lugar, estamos en una direccion incorrecta
            direccion = ((direccion)%6)+1;;   // Siguiente direccion sin salirnos del rango (1-6)
            offset_columna = offset_fila = 0;
          }
          System.out.println(">> Disparo: " + (char)(fila+97+offset_fila) + (columna+offset_columna));
          break;
        case 6:   // Diagonal descendente
          offset_fila++;
          offset_columna--;
          result=mundo.disparo(fila+offset_fila, columna+offset_columna);
          if ((result == 4) || (result == 3)){    // Si ya hemos disparado en ese lugar, estamos en una direccion incorrecta
            direccion = ((direccion)%6)+1;;   // Siguiente direccion sin salirnos del rango (1-6)
            offset_columna = offset_fila = 0;
          }
          System.out.println(">> Disparo: " + (char)(fila+97+offset_fila) + (columna+offset_columna));
          break;
      }
    }
    if (result == 0){   // Agua
      direccion = ((direccion+1)%6)+1;
      result=1; // Para que pueda entrar en el bucle la proxima vez
      offset_columna = offset_fila = 0;
    } else if (result == 2){    // Hundido
      proximo=false;    // Ya no es necesario buscar por la zona en actual
    }
    System.out.println("");
  }
}
