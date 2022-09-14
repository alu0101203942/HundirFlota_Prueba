import java.io.*;

public class Hundir {

  // Visualiza nuestro mundo y el del contricante
  public static void visualizar(Mundo nuestro_mundo, Mundo su_mundo){
    int result;
    System.out.println("  0123456789        0123456789");
    System.out.println(" ------------      ------------");
    for (int fila=0; fila<10; fila++){  // Todas las filas
      System.out.print((char)(fila+97) + "|");
      for (int columna=0; columna<10; columna++){   // Columnas de nuestro tablero
        result=nuestro_mundo.estado(fila, columna);
        switch (result){    // Segun el estado dibujamos una cosa u otra
          case 0:
            System.out.print(" ");  // Agua
            break;
          case 1:
            System.out.print("*");  // Agua disparada
            break;
          case 10:
            System.out.print("t");  // Transatlantico
            break;
          case 11:
            System.out.print("T");  // Transatlantico tocado
            break;
          case 12:
            System.out.print("T");  // Transatlantico hundido
            break;
          case 20:
            System.out.print("y");  // Yate
            break;
          case 21:
            System.out.print("Y");  // Yate tocado
            break;
          case 22:
            System.out.print("Y");  // Yate hundido
            break;
          case 30:
            System.out.print("s");  // Submarino
            break;
          case 31:
            System.out.print("S");  // Submarino hundido
        }
      }
      System.out.print("|     ");
      System.out.print((char)(fila+97) + "|");
      for (int columna=0; columna<10; columna++){   // Columnas del tablero contrincante
        result=su_mundo.estado(fila, columna);
        switch (result){  // Segun el estado dibujamos una cosa u otra
          case 0:
            System.out.print(" ");  // Agua
            break;
          case 1:
            System.out.print("*");  // Agua disparada
            break;
          case 10:
            System.out.print(" ");  // Transatlantico no descubierto
            break;
          case 11:
            System.out.print("t");  // Transatlantico tocado
            break;
          case 12:
            System.out.print("h");  // Transatlantico hundido
            break;
          case 20:
            System.out.print(" ");  // Yate no descubierto
            break;
          case 21:
            System.out.print("t");  // Yate tocado
            break;
          case 22:
            System.out.print("h");  // Yate hundido
            break;
          case 30:
            System.out.print(" ");  // Submarino no descubierto
            break;
          case 31:
            System.out.print("h");  // Submarino hundido
        }
      }
      System.out.println("|");
    }
    System.out.println(" ------------      ------------");
    System.out.println("    Humano           Computer");
  }



  // Solicita una posicion y la devuelve en un array donde el primero numero
  // es la fila y el segundo la columna
  private static int[] pedir_posicion() throws IOException {
    int[] pos = {0, 0};
    BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
    String s;
    
    System.out.print("Posicion: ");
    s = in.readLine();
    while(s.length() != 2){
      System.out.print("Posicion: ");
      s = in.readLine();
    }
    s = s.toLowerCase();
    pos[0] = ((int)s.charAt(0))-97;   // Fila
    pos[1] = ((int)s.charAt(1)-48);   // Columna

    return pos;
  }

  // Solicita una direccion (1, 2, 3) y la devuelve.
  private static int pedir_direccion() throws IOException {
    int direccion;
    BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
    String s;

    System.out.print("Direccion [1=Horizontal, 2=Vertical, 3=Diagonal]: ");
    s = in.readLine();
    while(s.length() != 1){
      System.out.print("Direccion [1=Horizontal, 2=Vertical, 3=Diagonal]: ");
      s = in.readLine();
    }
    s = s.toLowerCase();
    direccion = ((int)s.charAt(0))-48;

    return direccion;
  }

 
  public static void main (String[] arg) throws IOException {
    Mundo mundo_com = new Mundo();
    Mundo mundo_human = new Mundo();   // Mundo del jugador humano
    Jugador computer = new Jugador();
    int pos[], direccion, result;
    BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
    String s;

    System.out.println(">> Practica PRII: Hundir la flota");
    System.out.println(" > Sergio Blanco Cuaresma");
    System.out.println("");
    System.out.println("Iniciando partida...");
    computer.colocar_barcos(mundo_com);
    System.out.println("   Computer ha colocado sus barcos.");
    System.out.println("   Listos para colocar los suyos...");
    System.out.println(" ** Las posiciones siempre se deben indicar en 2 caracteres: primero la letra, luego el numero");

    // Selecionamos posicionamiento de nuestros barcos automatico o manual
    System.out.println("1) Automatico       2) Manual");
    System.out.print("Opcion: ");
    s = in.readLine();
    while((s.length() != 1) || ((!(s.toLowerCase()).equals("1")) && (!(s.toLowerCase()).equals("2")))){
      System.out.print("Opcion: ");
      s = in.readLine();
    }
    
    if ((s.toLowerCase()).equals("1")) {
      computer.colocar_barcos(mundo_human);   // Automatico
    } else {  //Manual
      do{     //Transatlantico
        visualizar(mundo_human, mundo_com);
        System.out.println("Transatlantico >>");
        pos = pedir_posicion();       // Posicion
        while ((pos[0]<0) || (pos[0]>9) || (pos[1]<0) || (pos[1]>9)) {
          System.out.println("Valores incorrectos!");
          pos = pedir_posicion();
        }
        direccion = pedir_direccion();    // Direccion
        while ((direccion <1) || (direccion >3)){
          System.out.println("Valor incorrecto!");
          direccion = pedir_direccion();
        }
        result=mundo_human.colocar_trans(pos[0], pos[1], direccion);    // Intentamos colocar
        if (result == 1)
          System.out.println("Imposible colocar Transatlantico en esa posicion!");
      }while ((result == 1) || (result == 2));   // Nos aseguramos de que se ha podido colocar el barco en la posicion
      visualizar(mundo_human, mundo_com);
    
      do{   // Yate 1
        System.out.println("Yate 1 >>");
        pos = pedir_posicion();
        while ((pos[0]<0) || (pos[0]>9) || (pos[1]<0) || (pos[1]>9)) {
          System.out.println("Valores incorrectos!");
          pos = pedir_posicion();
        }
        direccion = pedir_direccion();
        while ((direccion <1) || (direccion >3)){
          System.out.println("Valor incorrecto!");
          direccion = pedir_direccion();
        }
        result=mundo_human.colocar_yate1(pos[0], pos[1], direccion);
        if (result == 1)
          System.out.println("Imposible colocar Yate en esa posicion!");
      }while ((result == 1) || (result == 2));   // Nos aseguramos de que se ha podido colocar el barco en la posicion
      visualizar(mundo_human, mundo_com);
    
      do{   // Yate 2
        System.out.println("Yate 2 >>");
        pos = pedir_posicion();
        while ((pos[0]<0) || (pos[0]>9) || (pos[1]<0) || (pos[1]>9)) {
          System.out.println("Valores incorrectos!");
          pos = pedir_posicion();
        }
        direccion = pedir_direccion();
        while ((direccion <1) || (direccion >3)){
          System.out.println("Valor incorrecto!");
          direccion = pedir_direccion();
        }
        result=mundo_human.colocar_yate2(pos[0], pos[1], direccion);
        if (result == 1)
          System.out.println("Imposible colocar Yate en esa posicion!");
      }while ((result == 1) || (result == 2));   // Nos aseguramos de que se ha podido colocar el barco en la posicion
      visualizar(mundo_human, mundo_com);

      do{   // Submarino 1
       System.out.println("Submarino 1 >>");
        pos = pedir_posicion();
        while ((pos[0]<0) || (pos[0]>9) || (pos[1]<0) || (pos[1]>9)) {
          System.out.println("Valores incorrectos!");
          pos = pedir_posicion();
        }
        result=mundo_human.colocar_submarino1(pos[0], pos[1]);
        if (result == 1)
          System.out.println("Imposible colocar Submarino en esa posicion!");
      }while ((result == 1) || (result == 2));   // Nos aseguramos de que se ha podido colocar el barco en la posicion
      visualizar(mundo_human, mundo_com);

      do{   // Submarino 2
        System.out.println("Submarino 2 >>");
        pos = pedir_posicion();
        while ((pos[0]<0) || (pos[0]>9) || (pos[1]<0) || (pos[1]>9)) {
          System.out.println("Valores incorrectos!");
          pos = pedir_posicion();
        }
        result=mundo_human.colocar_submarino2(pos[0], pos[1]);
        if (result == 1)
          System.out.println("Imposible colocar Submarino en esa posicion!");
      }while ((result == 1) || (result == 2));   // Nos aseguramos de que se ha podido colocar el barco en la posicion
      visualizar(mundo_human, mundo_com);
      
      do{   // Submarino 3
        System.out.println("Submarino 3 >>");
        pos = pedir_posicion();
        while ((pos[0]<0) || (pos[0]>9) || (pos[1]<0) || (pos[1]>9)) {
          System.out.println("Valores incorrectos!");
          pos = pedir_posicion();
        }
        result=mundo_human.colocar_submarino3(pos[0], pos[1]);
        if (result == 1)
          System.out.println("Imposible colocar Submarino en esa posicion!");
      }while ((result == 1) || (result == 2));   // Nos aseguramos de que se ha podido colocar el barco en la posicion
    }   // Fin Manual
    
    // Comenzamos los disparos
    visualizar(mundo_human, mundo_com);
    System.out.println("   Todos los barcos en posicion...");
    do {
      System.out.println("   Preparados para realizar disparo.");
      System.out.println("");
      do {
        pos = pedir_posicion();   // Posicion a disparar
        while ((pos[0]<0) || (pos[0]>9) || (pos[1]<0) || (pos[1]>9)) {
          System.out.println("Valores fuera de rango!");
          pos = pedir_posicion();
        }
        result=mundo_com.disparo(pos[0], pos[1]);
        if (result == 4)
          System.out.println("Esta posicion ya ha sido bombardeada");
        if (result == 0){
          System.out.println("Agua!");
        }
        if (result == 1){
          visualizar(mundo_human, mundo_com);
          System.out.println("Tocado!");
        }
        if (result == 2){
          System.out.println("Hundido!");
        }
      }  while ((result == 4) || (result == 1));  // Repetimos mientras toquemos o la posicion ya haya sido elegida
      System.out.println("   Computer preparada para realizar disparo.");
      System.out.println("");
      computer.disparar(mundo_human);   // Turno del ordenador
      visualizar(mundo_human, mundo_com);
    } while ((mundo_human.todos_hundidos()==false) && (mundo_com.todos_hundidos()==false)); // Mientras no gane nadie

    if (mundo_human.todos_hundidos()) {
      System.out.println(" GANADOR: Computer");
    } else {
      System.out.println(" GANADOR: Humano");
    }
  }
}
