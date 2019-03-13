package Quatro;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AlphaBetaAgent {

    public int comptador;

    AlphaBetaAgent() {
        comptador = 0;
    }


    public Resultat alphaBetaThink(Piece a, int depth, Tauler tau) {
        TaulerExtended tauler = new TaulerExtended(tau);
        Resultat res = new Resultat();

        maxValue(a, tauler, depth, res, -1000000, 1000000);

        if (res.nextPiece == null)
            res.nextPiece = new Piece();

        return res;
    }

    public Resultat randomStep(Piece a, Tauler tau) {
        TaulerExtended tauler = new TaulerExtended(tau);

        Random rand = new Random();

        int x = rand.nextInt(4);
        int y = rand.nextInt(4);
        while (tauler.getPiece(x, y).isValid()) {
            x = rand.nextInt(4);
            y = rand.nextInt(4);
        }



        Resultat res = new Resultat();

        res.nextPiece = tauler.getRandomAvailabePiece();
        while(res.nextPiece == a){
            res.nextPiece = tauler.getRandomAvailabePiece();
        }

        res.x = x;
        res.y = y;

        return res;
    }

    private int minValue(Piece a, TaulerExtended tau, int depth, Resultat result, int alpha, int beta) {
        int localBeta = Integer.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!tau.getPiece(j, i).isValid()) {
                    TaulerExtended tauAux = tau.copy();
                    tauAux.setPiece(j, i, a);
                    boolean haGuanyat = quartoWin(tauAux);
                    if (tauAux.getNPecesColocades() == 16) {
                        //El tauler està ple, per tant, ja no cal
                        //que seguim explorant aquest camí.
                        result.x = j;
                        result.y = i;
                        result.nextPiece = new Piece();

                        //En cas de tauler ple, en node MIN, pot ser que hagi perdut (-100) o bé que hi hagi empat (0).
                        return (haGuanyat ? 1 : 0) * -100;
                    } else if (haGuanyat) {
                        //En aquest estat perdem la partida, per tant, no cal que
                        //explorem més branques en aquest camí, ja que hem obtingut 
                        //la puntuació mínima (-100)
                        result.x = j;
                        result.y = i;
                        result.nextPiece = new Piece();
                        return -100;
                    } else if (depth == 0) {
                        //Hem arribat al final de la recursió (profunditat)
                        //és un node fulla... toca evaluar els possibles estats.
                        int valor = -quartoHeuristic(tauAux);
                        if (valor < localBeta) {
                            localBeta = valor;
                            result.x = j;
                            result.y = i;
                        }
                        if (localBeta <= alpha)
                            return localBeta;
                    } else {
                        Iterator<Piece> piecesAvailable = tauAux.getPecesDisponibles().iterator();

                        while (piecesAvailable.hasNext()) {
                            Resultat resAux = new Resultat();
                            Piece pecaAux = piecesAvailable.next();
                            int val = maxValue(pecaAux, tauAux, depth - 1, resAux, alpha, localBeta);
                            if (val < localBeta) {
                                //El valor obtingut per sota és menor
                                //que el millor beta que hem trobat fins ara.
                                //Hem d'actualitzar el resultat i beta.
                                result.x = j;
                                result.y = i;
                                result.nextPiece = pecaAux;
                                localBeta = val;
                            }
                            if (localBeta <= alpha) return localBeta;
                        }
                    }

                }

            }
        }
        return localBeta;
    }

    private int maxValue(Piece a, TaulerExtended tau, int depth, Resultat result, int alpha, int beta) {
        Integer local_alpha = Integer.MIN_VALUE;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!tau.getPiece(j, i).isValid()) {
                    TaulerExtended tauAux = tau.copy();
                    tauAux.setPiece(j, i, a);
                    boolean haGuanyat = quartoWin(tauAux);
                    if (tauAux.getNPecesColocades() == 16) {
                        //El tauler està ple, per tant,
                        //no cal que seguim buscant.
                        result.x = j;
                        result.y = i;
                        result.nextPiece = new Piece();
                        return (haGuanyat ? 1 : 0) * 100;
                    } else if (haGuanyat) {
                        //És el màxim valor que podem obtenir, ja que implica victòria.
                        //Per tant, no cal que seguim explorant aquesta branca.
                        result.x = j;
                        result.y = i;
                        result.nextPiece = new Piece();
                        return 100;
                    } else if (depth == 0) {
                        //Hem arribat al final de la recursió
                        //i hem d'avaluar les possibles posicions
                        //de les peces disponibles..
                        int valor = quartoHeuristic(tauAux);
                        if (valor > local_alpha) {
                            local_alpha = valor;
                            result.x = j;
                            result.y = i;
                        }
                        if (local_alpha >= beta)
                            return local_alpha;
                    } else {
                        Iterator<Piece> piecesAvailable = tauAux.getPecesDisponibles().iterator();

                        while (piecesAvailable.hasNext()) {
                            Resultat resAux = new Resultat();
                            Piece pecaAux = piecesAvailable.next();
                            int val = minValue(pecaAux, tauAux, depth - 1, resAux, local_alpha, beta);
                            if (val > local_alpha) {
                                //El valor que hem obtingut de fills és millor
                                //que el que haviem trobat fins ara
                                //això implica que hem d'actualitzar posició i alpha
                                result.x = j;
                                result.y = i;
                                result.nextPiece = pecaAux;
                                local_alpha = val;
                            }
                            //Si l'alpha és major que el beta
                            //no hem de continuar ja que el node MIN de per sobre
                            //escollirà sempre el camí que condueixi a beta.
                            if (local_alpha >= beta) return local_alpha;
                        }
                    }

                }
            }
        }
        return local_alpha;
    }


    //Aquest mètode ha de retornar un valor entre [-100,100] on -100 és derrota, 0 és empat i 100 és victòria.
    private int quartoHeuristic(TaulerExtended tau) {

        comptador++;
        if (quartoWin(tau)) {
            return 100;
        }
        if (quartoDerrotaSegura(tau)) {
            return -100;
        }
        if (tau.getNPecesColocades() == 16) {
            return 0;
        }
        int val = 0;

        //Actualitzem el valor amb les diferents heurístiques.

        //Volem evitar al màxim un cas de tres peces consecutives en desavantatge.
        val -= tripleNeg(tau) * 10;
        //Hi ha algunes posicions que ens podrien donar victòria, però lluny de ser una aposta segura.
        val += triplePos(tau) * 5;

        return val;
    }

    private int triplePos(TaulerExtended tau) {
        int nTriples = 0;
        int pecesDisponibles = tau.getNPecesDisponibles();
        TripePiece tp = new TripePiece();

        for (int i = 0; i < 4; i++) {
            //Horitzontal
            int pecesHoritzontals = piecesTriple(tau, tau.getPiece(0, i), tau.getPiece(1, i), tau.getPiece(2, i), tau.getPiece(3, i), tp);
            if (pecesHoritzontals > 0) {
                //Com que hi ha un nombre imparell de peces que no poden
                //completar la tripleta que hem trobat, hauriem de ser capaços
                //de forçar l'oponent a donar-nos una peça que ens faci guanyar.
                if (((pecesDisponibles - pecesHoritzontals) % 2) != 0)
                    nTriples++;
            }

            //Vertical
            int pecesVerticals = piecesTriple(tau, tau.getPiece(i, 0), tau.getPiece(i, 1), tau.getPiece(i, 2), tau.getPiece(i, 3), tp);
            if (pecesVerticals > 0)
                if (((pecesDisponibles - pecesVerticals) % 2) != 0)
                    nTriples++;
        }

        //Diagonal1
        int pecesD1 = piecesTriple(tau, tau.getPiece(0, 0), tau.getPiece(1, 1), tau.getPiece(2, 2), tau.getPiece(3, 3), tp);
        if (pecesD1 > 0)
            if (((pecesDisponibles - pecesD1) % 2) != 0)
                nTriples++;

        //Diagonal2

        int pecesD2 = piecesTriple(tau, tau.getPiece(0, 3), tau.getPiece(1, 2), tau.getPiece(2, 1), tau.getPiece(3, 0), tp);
        if (pecesD2 > 0) {
            if (((pecesDisponibles - pecesD2) % 2) != 0)
                nTriples++;
        }
        return nTriples;
    }

    private int tripleNeg(TaulerExtended tau) {
        int nTriples = 0;
        int pecesDisponibles = tau.getNPecesDisponibles();
        TripePiece tp = new TripePiece();

        for (int i = 0; i < 4; i++) {
            //Horitzontal
            int pecesHoritzontals = piecesTriple(tau, tau.getPiece(0, i), tau.getPiece(1, i), tau.getPiece(2, i), tau.getPiece(3, i), tp);
            if (pecesHoritzontals > 0) {
                // Si hi ha un nombre parell de peces que no lliguen a la poisició lliure,
                // probablement estarem forçats a donar a l'oponent una peça guanyadora (si l'oponent juga bé).
                if (((pecesDisponibles - pecesHoritzontals) % 2) == 0)
                    nTriples++;
            }

            //Vertical
            int pecesVerticals = piecesTriple(tau, tau.getPiece(i, 0), tau.getPiece(i, 1), tau.getPiece(i, 2), tau.getPiece(i, 3), tp);
            if (pecesVerticals > 0)
                if (((pecesDisponibles - pecesVerticals) % 2) == 0)
                    nTriples++;
        }

        //Diagonal1
        int pecesD1 = piecesTriple(tau, tau.getPiece(0, 0), tau.getPiece(1, 1), tau.getPiece(2, 2), tau.getPiece(3, 3), tp);
        if (pecesD1 > 0)
            if (((pecesDisponibles - pecesD1) % 2) == 0)
                nTriples++;

        //Diagonal2

        int pecesD2 = piecesTriple(tau, tau.getPiece(0, 3), tau.getPiece(1, 2), tau.getPiece(2, 1), tau.getPiece(3, 0), tp);
        if (pecesD2 > 0) {
            if (((pecesDisponibles - pecesD2) % 2) == 0)
                nTriples++;
        }

        return nTriples;
    }




    /*
        Quan hi ha dos línies de 3 peces diferents que tenen valors oposats d'almenys un atribut,
        donant qualsevol peça a l'oponent, tenim asegurada la derrota.

        Per trobar aquest estat, busquem totes les 3-pecesLinia i després les comparem entre elles
        per saber si algunes d'elles tenen valors oposats d'algun atribut.
     */
    private boolean quartoDerrotaSegura(TaulerExtended tau) {

        TripePiece[] tps = new TripePiece[10];
        int nTps = 0;

        for (int i = 0; i < 4; i++) {
            TripePiece tp = new TripePiece();
            //Horitzontal
            if (piecesTriple(tau, tau.getPiece(0, i), tau.getPiece(1, i), tau.getPiece(2, i), tau.getPiece(3, i), tp) > 0) {
                tps[nTps] = tp;
                nTps++;
            }

            TripePiece tp2 = new TripePiece();
            //Vertical
            if (piecesTriple(tau, tau.getPiece(i, 0), tau.getPiece(i, 1), tau.getPiece(i, 2), tau.getPiece(i, 3), tp2) > 0) {
                tps[nTps] = tp2;
                nTps++;
            }
        }

        TripePiece tp3 = new TripePiece();
        if (piecesTriple(tau, tau.getPiece(0, 0), tau.getPiece(1, 1), tau.getPiece(2, 2), tau.getPiece(3, 3), tp3) > 0) {
            tps[nTps] = tp3;
            nTps++;
        }

        TripePiece tp4 = new TripePiece();
        if (piecesTriple(tau, tau.getPiece(0, 3), tau.getPiece(1, 2), tau.getPiece(2, 1), tau.getPiece(3, 0), tp4) > 0) {
            tps[nTps] = tp4;
            nTps++;
        }

        for (int i = 0; i < nTps - 1; i++) {
            for (int j = i + 1; j < nTps; j++) {

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

    private int piecesTriple(TaulerExtended tau, Piece a, Piece b, Piece c, Piece d, TripePiece tp) {
        Piece g;
        int piece_count;
        if (a.isValid() && b.isValid() && c.isValid() && !d.isValid()) {
            if (((a.index & b.index & c.index) > 0) || ((a.xor & b.xor & c.xor) > 0)) {
                piece_count = 0;

                for (Piece piece : tau.getPecesDisponibles()) {
                    g = piece;
                    if (guanya(a, b, c, g)) {
                        piece_count++;
                    }
                }
                if (piece_count > 0) {
                    tp.a = a;
                    tp.b = b;
                    tp.c = c;
                    return piece_count;
                }
            }
        } else if (a.isValid() && b.isValid() && !c.isValid() && d.isValid()) {
            if (((a.index & b.index & d.index) > 0) || ((a.xor & b.xor & d.xor) > 0)) {
                piece_count = 0;
                for (Piece piece : tau.getPecesDisponibles()) {
                    g = piece;
                    if (guanya(a, b, d, g)) {
                        piece_count++;
                    }
                }
                if (piece_count > 0) {
                    tp.a = a;
                    tp.b = b;
                    tp.c = d;
                    return piece_count;
                }
            }
        } else if (a.isValid() && !b.isValid() && c.isValid() && d.isValid()) {
            if (((a.index & c.index & d.index) > 0) || ((a.xor & c.xor & d.xor) > 0)) {
                piece_count = 0;
                for (Piece piece : tau.getPecesDisponibles()) {
                    g = piece;
                    if (guanya(a, c, d, g)) {
                        piece_count++;
                }
                }
                if (piece_count > 0) {
                    tp.a = a;
                    tp.b = c;
                    tp.c = d;
                    return piece_count;
                }
            }
        } else if (!a.isValid() && b.isValid() && c.isValid() && d.isValid()) {
            if (((b.index & c.index & d.index) > 0) || ((b.xor & c.xor & d.xor) > 0)) {
                piece_count = 0;
                for (Piece piece : tau.getPecesDisponibles()) {
                    g = piece;
                    if (guanya(b, c, d, g)) {
                        piece_count++;
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
        for (int i = 0; i < 4; i++) {
            //Horizontal
            if (guanya(tau.getPiece(0, i), tau.getPiece(1, i), tau.getPiece(2, i), tau.getPiece(3, i))) {
                return true;
            }
            //Vertical
            if (guanya(tau.getPiece(i, 0), tau.getPiece(i, 1), tau.getPiece(i, 2), tau.getPiece(i, 3))) {
                return true;
            }
        }
        if (guanya(tau.getPiece(0, 0), tau.getPiece(1, 1), tau.getPiece(2, 2), tau.getPiece(3, 3))) {
            return true;
        }
        if (guanya(tau.getPiece(0, 3), tau.getPiece(1, 2), tau.getPiece(2, 1), tau.getPiece(3, 0))) {
            return true;
        }
        return false;
    }

    private boolean guanya(Piece p1, Piece p2, Piece p3, Piece p4) {
        // 1 si es una combinacio guanyadora o si no
        if (!p1.isValid() || !p2.isValid() || !p3.isValid() || !p4.isValid()) {
            //hi ha algun dels 4 buit no podem guanyar
            return false;
        } else {
            return (p1.index & p2.index & p3.index & p4.index) > 0 || (p1.xor & p2.xor & p3.xor & p4.xor) > 0;
        }
    }

    public class TripePiece {
        public Piece a;
        public Piece b;
        public Piece c;

        TripePiece() {
            a = new Piece();
            b = new Piece();
            c = new Piece();
        }
    }


}





