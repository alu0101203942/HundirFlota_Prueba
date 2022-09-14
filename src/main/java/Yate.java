/* Yate.java
 * Autor: Sergio Blanco Cuaresma
 * Fecha: Enero del 2002
 */

public class Yate extends Barco {
  public int tocado (){
    if (toques < 2){
      toques++;
      if (toques == 2){
        hundido=true;
        return 2;   // Hundido
      }
      return 1;   // Tocado
    }
    return 2; // Ya estaba hundido
  }
}
