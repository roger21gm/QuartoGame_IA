package Quatro;


/**
 * @author Usuari
 */
public class Player1 {
    private AlphaBetaAgent ABagent;
    private Tauler taulerJoc;

    Player1(Tauler entrada){
        ABagent = new AlphaBetaAgent();
        taulerJoc = entrada;
    }


    public int[] tirada(int colorin, int formain, int foratin, int tamanyin){
     //colorin - Color de la peça a colocar -> 	0 = Blanc 	1 = Negre
     //formain - Forma de la peça a colocar -> 	0 = Rodona 	1 = Quadrat
     //foratin - Forat de la peça a colocar -> 	0 = No  	1 = Si
     //tamanyin - Forat de la peça a colocar -> 0 = Petit 	1 = Gran

        Piece toPlacePiece = new Piece(colorin,formain,foratin,tamanyin);
        Resultat res = ABagent.alphaBetaThink(toPlacePiece, 1, taulerJoc);
        Piece toPassPiece = res.nextPiece;
        String toPassPieceString = toPassPiece.getPieceAsString();


        int[] uep = {toPassPieceString.charAt(0) - '0', toPassPieceString.charAt(1) - '0',toPassPieceString.charAt(2)- '0',toPassPieceString.charAt(3)- '0'};

        System.out.println("(" + res.x + "," + res.y + ")");

        for (int i = 0; i < 4; i++) {
            System.out.print(uep[i]);
        }
        System.out.println();

        //Un retorn per defecte
        return new int[]{res.x,res.y,toPassPieceString.charAt(0) - '0',toPassPieceString.charAt(1)- '0',toPassPieceString.charAt(2)- '0',toPassPieceString.charAt(3)- '0'};
        //format del retorn vector de 6 int {posX[0a3], posY[0a3], color[0o1] forma[0o1], forat[0o1], tamany[0o1]}
        //posX i posY es la posicio on es coloca la peça d'entrada
        //color forma forat i tamany descriuen la peça que colocara el contrari
        //color -  	0 = Blanc 	1 = Negre
        //forma -  	0 = Rodona 	1 = Quadrat
        //forat - 	0 = No  	1 = Si
        //tamany -      0 = Petit 	1 = Gran
    }
}
