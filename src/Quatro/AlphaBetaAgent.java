package Quatro;

public class AlphaBetaAgent {

    int x;
    int y;
    Piece nextPiece;
    Resultat res;
    TaulerExtended tauler;

    AlphaBetaAgent(){
        x = -1;
        y = -1;
        nextPiece = null;
        res = new Resultat();
        tauler = new TaulerExtended();
    }


    public Resultat alphaBetaThink(Piece a, int depth, Tauler tau){

        tauler = new TaulerExtended(tau);

        minValue(a, depth, -1000000, 1000000);
        return res;
    }


    private int minValue(Piece a, int depth, int alpha, int beta){
        int local_beta = 1000000;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j<4; j++){
                if(!tauler.getPiece(j,i).isValid()){
                    tauler.setPiece(j,i,a);
                    boolean haGuanyat = quartoWin(tauler);
                    if(tauler.getNPecesColocades() == 16){
                        //This placement filled up the board which means that
                        //we can't go further down so we just update the x, j
                        //and return the value of this end state
                        res.x = j;
                        res.y = i;
                        return (haGuanyat ? 1 : 0)  * -100;
                    }
                    else if(haGuanyat){
                        //This is the maximum we can get, which means that we won the game
                        //no reason to go further down since this lead to a victory
                        res.x = j;
                        res.y = i;
                        return -100;
                    }
                    else if(depth == 0){
                        //We have reached the bottom of the recursion
                        //and we need only evaluate the possible placements
                        //of the piece that we have gotten
                        int valor = -quartoHeuristic(tauler);
                        if(valor < local_beta){
                            local_beta = valor;
                            res.x = j;
                            res.y = i;
                        }
                        if(local_beta <= alpha)
                            return local_beta;
                    }
                    else{
                        boolean[] piecesAvailable = tauler.getPecesDisponibles();

                        for(int k = 0; k<16; k++){
                            if(piecesAvailable[k]){
                                int val = maxValue(new Piece(k), depth-1, alpha, local_beta);
                                if(val < local_beta){
                                    //The value we got from below is smaller
                                    //than the best beta we have found which
                                    //means we want that, so we need to update
                                    //x, y and update beta
                                    res.x = j;
                                    res.y = i;
                                    res.nextPiece = new Piece(k);
                                    local_beta = val;
                                }
                                if(local_beta <= alpha) return local_beta;
                            }
                        }
                    }

                }
            }
        }
        return local_beta;
    }

    private int maxValue(Piece a, int depth, int alpha, int beta){
        int local_alpha = -1000000;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j<4; j++){
                if(!tauler.getPiece(j,i).isValid()){
                    tauler.setPiece(j,i,a);
                    boolean haGuanyat = quartoWin(tauler);
                    if(tauler.getNPecesColocades() == 16){
                        //This placement filled up the board which means that
                        //we can't go further down so we just update the x, j
                        //and return the value of this end state
                        res.x = j;
                        res.y = i;
                        return (haGuanyat ? 1 : 0)  * 100;
                    }
                    else if(haGuanyat){
                        //This is the maximum we can get, which means that we won the game
                        //no reason to go further down since this lead to a victory
                        res.x = j;
                        res.y = i;
                        return 100;
                    }
                    else if(depth == 0){
                        //We have reached the bottom of the recursion
                        //and we need only evaluate the possible placements
                        //of the piece that we have gotten
                        int valor = quartoHeuristic(tauler);
                        if(valor > local_alpha){
                            local_alpha = valor;
                            res.x = j;
                            res.y = i;
                        }
                        if(local_alpha >= beta)
                            return local_alpha;
                    }
                    else{
                        boolean[] piecesAvailable = tauler.getPecesDisponibles();

                        for(int k = 0; k<16; k++){
                            if(piecesAvailable[k]){
                                int val = minValue(new Piece(k), depth-1, local_alpha, beta);
                                if(val > local_alpha){
                                    //The value we got from below is better
                                    //than what we have currently found
                                    //which means we need to keep this
                                    //position and update our alpha
                                    res.x = j;
                                    res.y = i;
                                    res.nextPiece = new Piece(k);
                                    local_alpha = val;
                                }
                                if(local_alpha >= beta) return local_alpha;
                            }
                        }
                    }

                }
            }
        }
        return local_alpha;
    }


    //This method should return a value between [-100, 100] where -100 is shait, 0 is a draw and 100 is great
    private int quartoHeuristic(TaulerExtended tau) {
        if(quartoWin(tau)){
            return 100;
        }
        if(quartoSecureLoss(tau)){
            return -100;
        }
        if(tau.getNPecesColocades() == 16){
            return 0;
        }
        int val = 0;

        //update the value with different checks to arrive at a final return-value;
        val -= tripleNeg(tau) * 10; //We want to avoid negative situations like the plague
        //There are places where we can win, but it's far from guaranteed
        val += triplePos(tau) * 5;
        return val;
    }

    private int triplePos(TaulerExtended tau) {
        int nTriples = 0;
        int pecesDisponibles = tau.getNPecesDisponibles();
        TripePiece tp = new TripePiece();

        for(int i=0; i<4; i++){
            //Horitzontal
            int pecesHoritzontals = piecesTriple(tau, tau.getPiece(0,i), tau.getPiece(1,i), tau.getPiece(2,i),tau.getPiece(3,i), tp);
            if(pecesHoritzontals > 0){
                //Since there is an odd number of pieces which can not
                //complete the triple we found we might be able to
                //force our opponent to give us a piece which we
                //can win with
                if( ((pecesDisponibles - pecesHoritzontals) % 2) != 0)
                    nTriples++;
            }

            //Vertical
            int pecesVerticals = piecesTriple(tau, tau.getPiece(i,0), tau.getPiece(i,1), tau.getPiece(i,2),tau.getPiece(i,3), tp);
            if(pecesVerticals > 0)
                if( ((pecesDisponibles - pecesVerticals) % 2) != 0)
                    nTriples++;
        }

        //Diagonal1
        int pecesD1 = piecesTriple(tau, tau.getPiece(0,0), tau.getPiece(1,1), tau.getPiece(2,2),tau.getPiece(3,3), tp);
        if(pecesD1 > 0)
            if( ((pecesDisponibles - pecesD1) % 2) != 0)
                nTriples++;

        //Diagonal2

        int pecesD2 = piecesTriple(tau, tau.getPiece(0,3), tau.getPiece(1,2), tau.getPiece(2,1),tau.getPiece(3,0), tp);
        if(pecesD2 > 0) {
            if (((pecesDisponibles - pecesD2) % 2) != 0)
                nTriples++;
        }
        return nTriples;
    }

    private int tripleNeg(TaulerExtended tau) {
        int nTriples = 0;
        int pecesDisponibles = tau.getNPecesDisponibles();
        TripePiece tp = new TripePiece();

        for(int i=0; i<4; i++){
            //Horitzontal
            int pecesHoritzontals = piecesTriple(tau, tau.getPiece(0,i), tau.getPiece(1,i), tau.getPiece(2,i),tau.getPiece(3,i), tp);
            if(pecesHoritzontals > 0){
                // Si hi ha un nombre parell de peces que no lliguen a la poisició lliure,
                // estarem forçats a donar a l'oponent una peça guanyadora
                if( ((pecesDisponibles - pecesHoritzontals) % 2) == 0)
                    nTriples++;
            }

            //Vertical
            int pecesVerticals = piecesTriple(tau, tau.getPiece(i,0), tau.getPiece(i,1), tau.getPiece(i,2),tau.getPiece(i,3), tp);
            if(pecesVerticals > 0)
                if( ((pecesDisponibles - pecesVerticals) % 2) == 0)
                    nTriples++;
        }

        //Diagonal1
        int pecesD1 = piecesTriple(tau, tau.getPiece(0,0), tau.getPiece(1,1), tau.getPiece(2,2),tau.getPiece(3,3), tp);
        if(pecesD1 > 0)
            if( ((pecesDisponibles - pecesD1) % 2) == 0)
                nTriples++;

        //Diagonal2

        int pecesD2 = piecesTriple(tau, tau.getPiece(0,3), tau.getPiece(1,2), tau.getPiece(2,1),tau.getPiece(3,0), tp);
        if(pecesD2 > 0) {
            if (((pecesDisponibles - pecesD2) % 2) == 0)
                nTriples++;
        }

        return nTriples;
    }

    private boolean quartoSecureLoss(TaulerExtended tau) {

        TripePiece[] tps = new TripePiece[10];
        int nTps = 0;

        for(int i = 0; i<4; i++){
            TripePiece tp = new TripePiece();
            //Horitzontal
            if(piecesTriple(tau, tau.getPiece(0,i),tau.getPiece(1,i), tau.getPiece(2,i), tau.getPiece(3,i), tp) > 0){
                tps[nTps] = tp;
                nTps++;
            }

            TripePiece tp2 = new TripePiece();
            //Vertical
            if(piecesTriple(tau, tau.getPiece(i,0),tau.getPiece(i,1), tau.getPiece(i,2), tau.getPiece(i,3), tp2) > 0){
                tps[nTps] = tp2;
                nTps++;
            }
        }

        TripePiece tp3 = new TripePiece();
        if(piecesTriple(tau, tau.getPiece(0,0),tau.getPiece(1,1), tau.getPiece(2,2), tau.getPiece(3,3), tp3) > 0){
            tps[nTps] = tp3;
            nTps++;
        }

        TripePiece tp4 = new TripePiece();
        if(piecesTriple(tau, tau.getPiece(0,3),tau.getPiece(1,2), tau.getPiece(2,1), tau.getPiece(3,0), tp4) > 0){
            tps[nTps] = tp4;
            nTps++;
        }

        for(int i=0; i<nTps-1; i++){
            for(int j=i+1; j<nTps; j++) {

                int val1 = tps[i].a.index & tps[i].b.index & tps[i].c.index;
                int val2 = tps[j].a.index & tps[j].b.index & tps[j].c.index;
                int xor1 = tps[i].a.xor & tps[i].b.xor & tps[i].c.xor;
                int xor2 = tps[j].a.xor & tps[j].b.xor & tps[j].c.xor;

                if (val1 == xor2 || val2 == xor1)
                    return true;
            }
        }
        return false;
    }

    private int piecesTriple(TaulerExtended tau, Piece a, Piece b, Piece c, Piece d, TripePiece tp){
        boolean[] pecesDisponibles = tau.getPecesDisponibles();
        Piece g;
        int piece_count;


        if(a.isValid() && b.isValid() && c.isValid() && !d.isValid()){
            if(((a.index & b.index & c.index) > 0) || ((a.xor & b.xor & c.xor) > 0)){
                piece_count=0;
                for(int i=0; i<16; i++){
                    if(pecesDisponibles[i]){
                        g = new Piece(i);
                        if(guanya(a, b, c, g)){
                            piece_count++;
                        }
                    }
                }
                if(piece_count>0){
                    tp.a = a;
                    tp.b = b;
                    tp.c = c;
                    return piece_count;
                }
            }
        }else if(a.isValid() && b.isValid() && !c.isValid() && d.isValid()){
            if(((a.index & b.index & d.index) > 0) || ((a.xor & b.xor & d.xor) > 0)){
                piece_count=0;
                for(int i=0; i<16; i++){
                    if(pecesDisponibles[i]){
                        g = new Piece(i);
                        if(guanya(a, b, d, g)){
                            piece_count++;
                        }
                    }
                }
                if(piece_count>0){
                    tp.a = a;
                    tp.b = b;
                    tp.c = d;
                    return piece_count;
                }
            }
        }else if(a.isValid() && !b.isValid() && c.isValid() && d.isValid()){
            if(((a.index & c.index & d.index) > 0) || ((a.xor & c.xor & d.xor) > 0)){
                piece_count=0;
                for(int i=0; i<16; i++){
                    if(pecesDisponibles[i]){
                        g = new Piece(i);
                        if(guanya(a, c, d, g)){
                            piece_count++;
                        }
                    }
                }
                if(piece_count>0){
                    tp.a = a;
                    tp.b = c;
                    tp.c = d;
                    return piece_count;
                }
            }
        }else if(!a.isValid() && b.isValid() && c.isValid() && d.isValid()) {
            if (((b.index & c.index & d.index) > 0) || ((b.xor & c.xor & d.xor) > 0)) {
                piece_count = 0;
                for (int i = 0; i < 16; i++) {
                    if (pecesDisponibles[i]) {
                        g = new Piece(i);
                        if (guanya(b, c, d, g)) {
                            piece_count++;
                        }
                    }
                }
                if (piece_count > 0) {
                    tp.a = b;
                    tp.b = c;
                    tp.c = d;
                    return piece_count;
                }
            }
        }
        return 0;
    }

    private boolean quartoWin(TaulerExtended tau) {
        for(int i = 0; i < 4; i++){
            //Horizontal
            if(guanya(tau.getPiece(0,i), tau.getPiece(1,i), tau.getPiece(2,i), tau.getPiece(3,i))){
                return true;
            }
            //Vertical
            if(guanya(tau.getPiece(i,0), tau.getPiece(i,1), tau.getPiece(i,2), tau.getPiece(i,3))){
                return true;
            }
        }
        if(guanya(tau.getPiece(0,0), tau.getPiece(1,1), tau.getPiece(2,2), tau.getPiece(3,3))){
            return true;
        }
        if(guanya(tau.getPiece(0,3), tau.getPiece(1,2), tau.getPiece(2,1), tau.getPiece(3,0))) {
            return true;
        }
        return false;
    }

    private boolean guanya(Piece p1, Piece p2, Piece p3, Piece p4){
        // 1 si es una combinacio guanyadora o si no
        if( !p1.isValid() || !p2.isValid() || !p3.isValid() || !p4.isValid() ){
            //hi ha algun dels 4 buit no podem guanyar
            return false;
        }else{
            return (p1.index & p2.index & p3.index & p4.index) > 0 || (p1.xor & p2.xor & p3.xor & p4.xor) > 0;
        }
    }

    public class TripePiece{
        public Piece a;
        public Piece b;
        public Piece c;

        TripePiece(){
            a = new Piece();
            b = new Piece();
            c = new Piece();
        }
    }



}





