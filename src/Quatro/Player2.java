/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Quatro;

import java.util.Scanner;

/**
 * * @author Usuari
 */
public class Player2 {
    private AlphaBetaAgent ABagent;
    private Tauler taulerJoc;
    private Integer torn;

    Player2(Tauler entrada){
        ABagent = new AlphaBetaAgent();
        taulerJoc = entrada;
        torn = 1;
    }


    //TIRADA MANUAL
//    public int[] tirada(int colorin, int formain, int foratin, int tamanyin){
//        //colorin - Color de la peça a colocar -> 	0 = Blanc 	1 = Negre
//        //formain - Forma de la peça a colocar -> 	0 = Rodona 	1 = Quadrat
//        //foratin - Forat de la peça a colocar -> 	0 = No  	1 = Si
//        //tamanyin - Forat de la peça a colocar -> 0 = Petit 	1 = Gran
//
//
//        Scanner a = new Scanner(System.in);
//
//        System.out.println("Peça a tirar:" + colorin+formain+foratin+tamanyin );
//        System.out.println("X:");
//        int x = a.nextInt();
//
//        System.out.println("Y:");
//        int y = a.nextInt();
//
//        System.out.println("Peça passada a l'oponent:");
//        String peca = a.next();
//
//        //Un retorn per defecte
//        return new int[]{x,y,peca.charAt(0) - '0',peca.charAt(1)- '0',peca.charAt(2)- '0',peca.charAt(3)- '0'};
//        //format del retorn vector de 6 int {posX[0a3], posY[0a3], color[0o1] forma[0o1], forat[0o1], tamany[0o1]}
//        //posX i posY es la posicio on es coloca la peça d'entrada
//        //color forma forat i tamany descriuen la peça que colocara el contrari
//        //color -  	0 = Blanc 	1 = Negre
//        //forma -  	0 = Rodona 	1 = Quadrat
//        //forat - 	0 = No  	1 = Si
//        //tamany -      0 = Petit 	1 = Gran
//    }

    public int[] tirada(int colorin, int formain, int foratin, int tamanyin){
        Piece toPlacePiece = new Piece(colorin,formain,foratin,tamanyin);
        Resultat res;




        res = ABagent.alphaBetaThink(toPlacePiece, torn+1, taulerJoc);


        Piece toPassPiece = res.nextPiece;
        String toPassPieceString = toPassPiece.getPieceAsString();

        System.out.println("JUGADOR 2");


        System.out.println("Peça a tirar: " + (""+colorin+formain+foratin+tamanyin));


        int[] uep = {toPassPieceString.charAt(0) - '0', toPassPieceString.charAt(1) - '0',toPassPieceString.charAt(2)- '0',toPassPieceString.charAt(3)- '0'};

        System.out.println("Posició tirada: (" + res.x + "," + res.y + ")");


        System.out.print("Peça proposada pel contrincnat: ");
        for (int i = 0; i < 4; i++) {
            System.out.print(uep[i]);
        }
        System.out.println("\n");
        torn++;
        return new int[]{res.x,res.y,toPassPieceString.charAt(0) - '0',toPassPieceString.charAt(1)- '0',toPassPieceString.charAt(2)- '0',toPassPieceString.charAt(3)- '0'};
    }
}