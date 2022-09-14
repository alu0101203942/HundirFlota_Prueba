public class Transatlantico extends Barco {
  public int tocado (){
    if (toques < 3){
      toques++;
      if (toques == 3){
        hundido=true;
        return 2;   // Hundido
      }
      return 1;   // Tocado
    }
    return 2; // Ya estaba hundido
  }
}
