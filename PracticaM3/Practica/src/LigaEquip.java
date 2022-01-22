import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LigaEquip {

    static String[] arrayNomEquips = new String[100]; // array que té el nom de cada equip
    static int[][] arrayInformacioEquips = new int[100][5]; // array amb la informació de cada equip
    static String[] paraulesPuntuacio = { " Juagat: ", " Guanyat: ", " Empatat: ", " Perdut: ", " Punts: " };
    static int primerNull = 0; // Variable global que far servir cada mètode per trobar el primer null de la
                               // array
    static int modificar = 0; // variable per fer modificacions de cada equip
    static int equip = 0; // variable per fer modificacions de cada equip
    static boolean sortir = false; // variable que far servir cada menú per sortir
    static boolean sortir2 = false; // variable que far servir cada menú per sortir
    static Scanner sc = new Scanner(System.in);
    static Scanner dadesConsola = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        llegirArchiuArray(); // funció per llegir el TXT i ficar la informació a array

        do {

            sumaPartits(); // agafa els partits guanyats, perduts i empatats i amb aquests valors fa la
                           // suma de partits jugats així la suma de partits sempre serà correcta, el
                           // mateix amb puntuacions.
            sumaPuntuacio();

            System.out.println();
            System.out.println("**********-Menu-***********");
            System.out.println("1. Llistar tots els equips");
            System.out.println("2. Mostrar els liders");
            System.out.println("3. Mostrar els cuers");
            System.out.println("4. Modificar un equip");
            System.out.println("5. Afegir un equip");
            System.out.println("6. Esborrar un equip");
            System.out.println("s. Sortir");
            System.out.println("\nTria una opcio:" + "\n");

            String sa = sc.next();
            char opcio = sa.charAt(0);

            switch (opcio) {
                case '1':
                    llistarEquipsPuntuacions();
                    break;
                case '2':
                    mostrarLiders();
                    break;
                case '3':
                    mostrarCuers();
                    break;
                case '4':
                    modificarEquip();
                    break;
                case '5':
                    afegirEquip();
                    break;
                case '6':
                    esborrarEquip();
                    break;
                case 's':
                    guardarArchiu();
                    sortir = true;
                    break;
                default:
                    System.out.println("Opcio no valida");
            }
        } while (!sortir);
    }

    static void trobarPrimerNull() { // és far servir per trobar el primer null de la array
        primerNull = Arrays.asList(arrayNomEquips).indexOf(null);
    }

    static void llegirArchiuArray() throws IOException {

        int posicioArrayNom = 0;
        int posInfEquips = 0;
        // BufferedReader reader = new BufferedReader(new FileReader("equips.txt"));

        FileReader buffer = new FileReader("equips.txt");

        BufferedReader reader = new BufferedReader(buffer);

        String linea = reader.lines().collect(Collectors.joining()); // Per llegir totes les línies d'arxiu
        String[] arreglo_datos = linea.split(":");// tot que està dividit per : es ficara en una posicio en la array
        for (int i = 0; i < arreglo_datos.length; i++) {

            if (arreglo_datos[0] == "") { // si la primera posicio de arxiu es null no fara la lectura de arxiu perque no ne cap informacio 
                System.out.println("el arxiu no te cap informacio");
                return;
            } else {

                if (!(i % 6 == 0)) { // si el "i" es divisible per 6 sempre serà el nom d'equip i es ficarà en la
                                     // array de noms
                    posicioArrayNom--;
                    int[] intArray = new int[arreglo_datos.length]; // Creo una array amb la longitud de dades

                    intArray[i] = Integer.parseInt(arreglo_datos[i]); // Dins de aquest if es treballa amb números i per
                                                                      // això paso la array que està en String a una
                                                                      // array
                                                                      // int
                    arrayInformacioEquips[posicioArrayNom][posInfEquips] = intArray[i];
                    posInfEquips++;
                    posicioArrayNom++;
                } else { // primer arriba al esle perquè el primer valor que es 0 es divisible per 6 i en
                         // else es treballa amb String noms
                    arrayNomEquips[posicioArrayNom] = arreglo_datos[i]; // a la posició 0 de array es ficara la dada que
                                                                        // està en posicio 0 que serà el primer nom
                    posicioArrayNom++;// després es fa la suma a la posició així la següent vegada el nom ficarà a la
                                      // posició +1
                    posInfEquips = 0;
                }

                reader.close();
            }
        }
    }

    static void llistarEquipsPuntuacions() {
        trobarPrimerNull();
        for (int i = 0; i < primerNull; i++) { // bucle per imprimir els equips amb les seves puntuacions
            System.out.print("Equip " + arrayNomEquips[i] + " ");
            for (int j = 0; j <= 4; j++) {
                System.out.print(paraulesPuntuacio[j] + arrayInformacioEquips[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void mostrarLiders() {
        trobarPrimerNull();
        int valorMaxim = arrayInformacioEquips[0][4];

        for (int i = 0; i < primerNull; i++) { // Trobar el valor màxim que esta en la posició 4 que es la posició de
                                               // punts
            if (arrayInformacioEquips[i][4] > valorMaxim) {
                valorMaxim = arrayInformacioEquips[i][4];
            }
        }
        for (int i = 0; i < primerNull; i++) { // si hi han equips que tenen mateixos punts s'imprimiran
            if (arrayInformacioEquips[i][4] == valorMaxim) {
                System.out.println("equip " + arrayNomEquips[i] + " amb mes puntuacions " + valorMaxim);

            }
        }
    }

    static void mostrarCuers() {
        trobarPrimerNull();
        int valorMinim = arrayInformacioEquips[0][4];

        for (int i = 0; i < primerNull; i++) {
            if (arrayInformacioEquips[i][4] < valorMinim) {
                valorMinim = arrayInformacioEquips[i][4];
            }
        }
        for (int i = 0; i < primerNull; i++) {
            if (arrayInformacioEquips[i][4] == valorMinim) {
                System.out.println("equip " + arrayNomEquips[i] + " amb menos puntuacions " + valorMinim);
            }
        }
    }

    static void modificarEquip() {
        trobarPrimerNull();

        System.out.println("Selecciona quin equip vols modificar:");
        for (int i = 0, j = 1; i < primerNull; i++, j++) {

            System.out.println(j + ") " + arrayNomEquips[i]);
        }

        equip = dadesConsola.nextInt();
        equip--;
        System.out.println("Has seleccionat el equip " + arrayNomEquips[equip]);

        do {
            System.out.println("Que vols modificar?");
            System.out.println("1. Partits guanyats");
            System.out.println("2. Partits empatats");
            System.out.println("3. Partits perduts");
            System.out.println("4. Nom de equip");
            System.out.println("s. Sortir");

            String sa1 = sc.next();

            char opcio2 = sa1.charAt(0);

            switch (opcio2) {
                case '1':
                    modificar = Integer.parseInt(sa1);
                    modificarEquipValor();

                    sortir2 = false;
                    break;
                case '2':
                    modificar = Integer.parseInt(sa1);
                    modificarEquipValor();

                    sortir2 = false;
                    break;
                case '3':
                    modificar = Integer.parseInt(sa1);
                    modificarEquipValor();

                    sortir2 = false;
                    break;
                case '4':
                    modificar = Integer.parseInt(sa1);
                    modificarNomEquip();
                    sortir2 = false;
                    break;
                case 's':
                    sortir2 = true;
                    break;

            }
        } while (!sortir2);
    }

    static void modificarEquipValor() { // modificar la informacio de cada equip
        System.out.println("Introduexi el valor:");
        int valor = dadesConsola.nextInt();
        arrayInformacioEquips[equip][modificar] = valor;
    }

    static void modificarNomEquip() { // modificar el nom de equip
        dadesConsola.nextLine();
        System.out.println("Ficar el nom:");
        String nomEquipNou = dadesConsola.nextLine();
        arrayNomEquips[equip] = nomEquipNou;
    }

    static void sumaPartits() {
        trobarPrimerNull();

        for (int i = 0; i < primerNull; i++) {
            int sumaPartits = arrayInformacioEquips[i][1] + arrayInformacioEquips[i][2] + arrayInformacioEquips[i][3];
            arrayInformacioEquips[i][0] = sumaPartits;
        }
    }

    static void sumaPuntuacio() {
        trobarPrimerNull();

        for (int i = 0; i < primerNull; i++) {
            int sumaPuntuacio = arrayInformacioEquips[i][1] * 3 + arrayInformacioEquips[i][2] * 1;
            arrayInformacioEquips[i][4] = sumaPuntuacio;
        }
    }

    static void afegirEquip() {
        trobarPrimerNull();
        System.out.println("Fica el nom del equip");
        String nomEquip = dadesConsola.nextLine();
        System.out.println("Fica la quantitats de partits guanyats");
        int partitsGuanyats = dadesConsola.nextInt();
        System.out.println("Fica la quantitats de partits empatats");
        int partitsEmpatats = dadesConsola.nextInt();
        System.out.println("Fica la quantitats de partits perduts");
        int partitsPerduts = dadesConsola.nextInt();
        System.out.println("Segur que vols intrdoduir aquet equip:");
        System.out.println(nomEquip + " Partits guanyats " + partitsGuanyats + "\nPartits perduts " + partitsPerduts
                + "\nPartits empatats " + partitsEmpatats + "\n1.Si\n2.No");

        int segurSiNo = dadesConsola.nextInt();
        dadesConsola.nextLine();
        if (segurSiNo == 1) { // si no vol ficar les dades ficara a la consola 2 i entra al else que te un
                              // return
            arrayNomEquips[primerNull] = nomEquip;
            arrayInformacioEquips[primerNull][1] = partitsGuanyats;
            arrayInformacioEquips[primerNull][2] = partitsEmpatats;
            arrayInformacioEquips[primerNull][3] = partitsPerduts;
            return;
        } else {
            return;
        }
    }

    static void guardarArchiu() throws IOException { // esta guarden a l'arxiu tota la informació de la array
        trobarPrimerNull();
        FileWriter filewriter = new FileWriter(new File("equips.txt"));

        for (int i = 0; i < primerNull; ++i) {
            filewriter.write(arrayNomEquips[i] + ":");
            for (int j = 0; j <= 4; ++j) {
                filewriter.write(arrayInformacioEquips[i][j] + ":");
                filewriter.flush();
            }
            filewriter.write("\n");
        }
    }

    static void esborrarEquip() throws IOException {
        trobarPrimerNull();
        System.out.println("Selecciona el equip per esborrar:");
        for (int i = 0, j = 1; i < primerNull; i++, j++) {

            System.out.println(j + ") " + arrayNomEquips[i]);
        }
        int equipEsborrar = dadesConsola.nextInt();
        equipEsborrar--;
        System.out.println("El equip " + arrayNomEquips[equipEsborrar] + " esta borrat");
        FileWriter filewriter = new FileWriter(new File("equips.txt"));

        for (int i = 0; i < primerNull - 1; ++i) {
            if (arrayNomEquips[equipEsborrar] == arrayNomEquips[i]) { // Quan fica les dades a arxiu salta l'equip que
                                                                      // hem seleccionat.
                i++;
            }
            filewriter.write(arrayNomEquips[i] + ":");
            for (int j = 0; j <= 4; ++j) {
                filewriter.write(arrayInformacioEquips[i][j] + ":");
                filewriter.flush();
            }
            filewriter.write("\n");
        }

        for (int i = 0; i < primerNull; i++) { // després de ficar les dades a l'arxiu amb l'equip esborrat als llocs de
                                               // la array on hi havien dades fico null

            arrayNomEquips[i] = null;
            for (int j = 0; j < 4; j++) {
                arrayInformacioEquips[i][j] = 0;
            }
        }
        llegirArchiuArray(); // torno llegir l'arxiu que no te l'equip esborrat
    }
}
