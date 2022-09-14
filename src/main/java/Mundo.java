public class Mundo {
  Barco[][] tablero;
  boolean[][] libre;
  boolean[][] disparado;
  Transatlantico trans;
  Yate yate1, yate2;
  Submarino submarino1, submarino2, submarino3;
  private int num_aciertos;

  Mundo (){     // Constructor: inicializamos variables
    tablero = new Barco[10][10];
    libre = new boolean[10][10];
    // Inicializamos todos a true ya que por defecto los pone a false
    for (int i=0; i<10; i++)
      for (int j=0; j<10; j++)
        libre[j][i]=true;
    disparado = new boolean[10][10];
    trans = new Transatlantico();
    yate1 = new Yate();
    yate2 = new Yate();
    submarino1 = new Submarino();
    submarino2 = new Submarino();
    submarino3 = new Submarino();
  }

  // No es necesario crear funciones especificas para cada transatlantico como los yates
  // o submarinos pq solo hay 1
  /* Direccion = 1 -> Horizontal || = 2 -> Vertical || = 3 Diagonal */
  public int colocar_trans(int fila, int columna, int direccion ) {
    switch (direccion) {
      case 1:   // Horizontal
        // Nos aseguramos que no se salga del tablero
        if ((columna > 7) || (columna<0) || (fila > 9) || (fila < 0)){
          return 1;
        }
        // Comprobamos que podemos colocar el barco en ese lugar
        if ((libre[fila][columna]) && (libre[fila][columna+1]) && (libre[fila][columna+2])) {
          for (int bucle=0; bucle<3; bucle++){
            tablero[fila][columna+bucle]=trans;   // Colocamos barco
          }
          // Marcar como no libre la zona del barco y alrededores
          for (int offset1=-1; offset1<4; offset1++){
            for (int offset2=-1; offset2<2; offset2++){
              // Comprobamos que no nos salimos del tablero
              if ((columna+offset1 < 10) && (columna+offset1 > -1) && (fila+offset2 <10) && (fila+offset2>-1) ){
                libre[fila+offset2][columna+offset1]=false;
              }
            }
          }
        } else {
          return 2;
        }

        break;

      case 2:   // Vertical
        if ((columna > 9) || (columna<0) || (fila > 9) || (fila <2)){
          return 1;
        }
        // Comprovamos que podemos colocar el barco en ese lugar
        if ((libre[fila][columna]) && (libre[fila-1][columna]) && (libre[fila-2][columna])) {
          for (int bucle=0; bucle<3; bucle++){
            tablero[fila-bucle][columna]=trans;   // Colocamos barco
          }
          // Marcar como no libre la zona del barco y alrededores
          for (int offset1=-1; offset1<2; offset1++){
            for (int offset2=-3; offset2<2; offset2++){
              // Comprovamos que no nos salimos del tablero
              if ((columna+offset1 < 10) && (columna+offset1 > -1) && (fila+offset2 <10) && (fila+offset2>-1) ){
                libre[fila+offset2][columna+offset1]=false;
              }
            }
          }
        } else {
          return 2;
        }

        break;

      case 3:   // Diagonal
        if ((columna > 7) || (columna<0) || (fila > 9) || (fila <2)){
          return 1;
        }
        // Comprovamos que podemos colocar el barco en ese lugar
        if ((libre[fila][columna]) && (libre[fila-1][columna+1]) && (libre[fila-2][columna+2])) {
          for (int bucle=0; bucle<3; bucle++){
            tablero[fila-bucle][columna+bucle]=trans;   // Colocamos barco
          }
          // Marcar como no libre la zona del barco y alrededores
          for (int offset1=0; offset1<3; offset1++){
            for (int offset2=-1; offset2<2; offset2++){
              // Comprovamos que no nos salimos del tablero
              if ((columna+offset1 < 10) && (columna+offset1 > -1) && (fila+offset2 <10) && (fila+offset2>-1) ){
                libre[fila+offset2][columna+offset1]=false;
              }
            }
            fila--;
          }
          fila++; fila++; fila++; // Contrarestamos los decrementos del bucle
                                  // para volver al valor inicial.
          if (columna-1 > -1) {
            libre[fila][columna-1]=false;
            libre[fila-1][columna-1]=false;   // esquina
            if (fila+1 < 10)
              libre[fila+1][columna-1]=false;
          }
          if (columna+3 < 10) {
            libre[fila-2][columna+3]=false;
            libre[fila-1][columna+3]=false;   // esquina
            if (fila-3 > -1)
              libre[fila-3][columna+3]=false;
          }
          libre[fila-2][columna]=false;   // esquina
          libre[fila][columna+2]=false;    // esquina
          if (fila-3 >-1)
            libre[fila-3][columna]=false;   // esquina
          if (fila+1 <10)
            libre[fila+1][columna+1]=false;   // esquina
          
        } else {
          return 2;
        }

    }
    return 0;
  }


  public int colocar_yate(int fila, int columna, int direccion, Yate yate ) {
    switch (direccion) {
      case 1:   // Horitzontal
        // Nos aseguramos que no se salga del tablero
        if ((columna > 8) || (columna<0) || (fila > 9) || (fila < 0)){
          return 1;
        }
        // Comprovamos que podemos colocar el barco en ese lugar
        if ((libre[fila][columna]) && (libre[fila][columna+1])) {
          for (int bucle=0; bucle<2; bucle++){
            tablero[fila][columna+bucle]=yate;   // Colocamos barco
          }
          // Marcar como no libre la zona del barco y alrededores
          for (int offset1=-1; offset1<3; offset1++){
            for (int offset2=-1; offset2<2; offset2++){
              // Comprovamos que no nos salimos del tablero
              if ((columna+offset1 < 10) && (columna+offset1 > -1) && (fila+offset2 <10) && (fila+offset2>-1) ){
                libre[fila+offset2][columna+offset1]=false;
              }
            }
          }
        } else {
          return 2;
        }

        break;

      case 2:   // Vertical
        if ((columna > 9) || (columna<0) || (fila > 9) || (fila <1)){
          return 1;
        }
        // Comprovamos que podemos colocar el barco en ese lugar
        if ((libre[fila][columna]) && (libre[fila-1][columna])) {
          for (int bucle=0; bucle<2; bucle++){
            tablero[fila-bucle][columna]=yate;   // Colocamos barco
          }
          // Marcar como no libre la zona del barco y alrededores
          for (int offset1=-1; offset1<2; offset1++){
            for (int offset2=-2; offset2<2; offset2++){
              // Comprovamos que no nos salimos del tablero
              if ((columna+offset1 < 10) && (columna+offset1 > -1) && (fila+offset2 <10) && (fila+offset2>-1) ){
                libre[fila+offset2][columna+offset1]=false;
              }
            }
          }
        } else {
          return 2;
        }

        break;
      case 3:
        if ((columna > 8) || (columna<0) || (fila > 9) || (fila <1)){
          return 1;
        }
        // Comprovamos que podemos colocar el barco en ese lugar
        if ((libre[fila][columna]) && (libre[fila-1][columna+1])) {
          for (int bucle=0; bucle<2; bucle++){
            tablero[fila-bucle][columna+bucle]=yate;   // Colocamos barco
          }
          // Marcar como no libre la zona del barco y alrededores
          for (int offset1=0; offset1<2; offset1++){
            for (int offset2=-1; offset2<2; offset2++){
              // Comprovamos que no nos salimos del tablero
              if ((columna+offset1 < 10) && (columna+offset1 > -1) && (fila+offset2 <10) && (fila+offset2>-1) ){
                libre[fila+offset2][columna+offset1]=false;
              }
            }
            fila--;
          }
          fila++; fila++;
          if (columna-1 > -1) {
            libre[fila][columna-1]=false;
            libre[fila-1][columna-1]=false;   // esquina
            if (fila+1 <10)
              libre[fila+1][columna-1]=false;
          }
          if (columna+2 < 10) {
            libre[fila-1][columna+2]=false;
            libre[fila][columna+2]=false;   // esquina
            if (fila-2 > -1)
              libre[fila-2][columna+2]=false;
          }
          if (fila-3 >-1)
            libre[fila-2][columna]=false;   // esquina
          if (fila+1 <10)
            libre[fila+1][columna+1]=false;   // esquina

        } else {
          return 2;
        }
    }
    return 0;
  } 

  // Funciones especificas para colocar cada uno de los yates disponibles
  public int colocar_yate1(int fila, int columna, int direccion){
    return colocar_yate(fila, columna, direccion, yate1);
  }
  public int colocar_yate2(int fila, int columna, int direccion){
    return colocar_yate(fila, columna, direccion, yate2);
  }


  public int colocar_submarino(int fila, int columna, Submarino submarino) {
    // Nos aseguramos que no se salga del tablero
    if ((columna > 9) || (columna<0) || (fila > 9) || (fila < 0)){
      return 1;
    }
   
    // Comprovamos que podemos colocar el barco en ese lugar
    if ((libre[fila][columna])) {
      tablero[fila][columna]=submarino;
      for (int offset1=-1;offset1<2;offset1++){
        for (int offset2=-1; offset2<2; offset2++){
          if ((columna+offset1<10) && (columna+offset1>-1) && (fila+offset2<10) && (fila+offset2>-1)) {
            libre[fila+offset2][columna+offset1]=false;
          }
        }
      }
    } else {
      return 2;
    }

    return 0;
  }

  // Funciones especificas para colocar cada uno de los submarinos disponibles
  public int colocar_submarino1(int fila, int columna) {
    return colocar_submarino(fila, columna, submarino1);
  }
  public int colocar_submarino2(int fila, int columna) {
    return colocar_submarino(fila, columna, submarino2);
  }
  public int colocar_submarino3(int fila, int columna) {
    return colocar_submarino(fila, columna, submarino3);
  }

  // En la tabla de booleanos disparado se guarda el disparo para que no se vuelva
  // a repetir, y lanzamos el metodo tocado() del barco correspondiente si existe
  // alguno en esa posici�n, esta nos retorna un valor segun si esta tocado(1)
  // o hundido(2)
  public int disparo (int fila, int columna) {
    if ((columna<0) || (columna>9) || (fila<0) || (fila>9)){
      return 3;   // Error, fuera de la tabla
    }
    if (disparado[fila][columna]){
      return 4;   // Ya hemos disparado antes
    }
    if (tablero[fila][columna] != null) {
      num_aciertos++;
      disparado[fila][columna]=true;
      return tablero[fila][columna].tocado();
    } else {
      disparado[fila][columna]=true;
      return 0;   // Agua
    }
  }

  // Devuelve true si todos los barcos han sido hundidos
  public boolean todos_hundidos(){
    if (num_aciertos == 10){    // Nos basamos en el numero de blancos realizados
                                // Para hundir todos los barcos se ha de hacer blanco en todas las casillas
      return true;              // que contienen un barco: 1x3 + 2x2 + 3x1 = 10
    } else {
      return false;
    }
  }

  public int estado (int fila, int columna) {
    if ((columna<0) || (columna>9) || (fila<0) || (columna>9)){
      return 3;   // Error, fuera de la tabla
    }
    if (tablero[fila][columna] == null) {
      if (disparado[fila][columna]){
        return 1; // Agua disparada
      } else {
        return 0; // Agua
      }
    } else {
      if (tablero[fila][columna] == trans){
          if (trans.hundido) {
            return 12;  // Trans hundido
          } else if (disparado[fila][columna]){
            return 11; // Trans tocado
          } else {
            return 10; // Trans
          }
      } else if ((tablero[fila][columna] == yate1) || (tablero[fila][columna] == yate2)){
          if (tablero[fila][columna].hundido) {
            return 22;  // Yate hundido
          } else if (disparado[fila][columna]){
            return 21;  // Yate tocado
          } else {
            return 20;  // Yate
          }
      } else {
          if (disparado[fila][columna]){
            return 31;  // Submarino tocado
          } else {
            return 30;  // Submarino
          }
      }
    }
  }
}








