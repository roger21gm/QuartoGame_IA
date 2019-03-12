package Quatro;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaulerExtended {


    private Piece[][] tauler;
    private int pecesColocades;
    private boolean[] piecesPool;

    TaulerExtended(){
        tauler = new Piece[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tauler[i][j] = new Piece();
            }
        }
        pecesColocades = 0;
        piecesPool = new boolean[16];
        for(int i = 0; i<16; i++){
            piecesPool[i] = true;
        }
    }

    TaulerExtended(Piece[][] tau, int pecesC, boolean[] pool){
        tauler = new Piece[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tauler[i][j] = new Piece(tau[i][j].index);
            }
        }
        pecesColocades = pecesC;
        piecesPool = pool.clone();
    }

    TaulerExtended(Tauler tau){
        pecesColocades = 0;
        piecesPool = new boolean[16];
        for(int i = 0; i<16; i++){
            piecesPool[i] = true;
        }

        tauler = new Piece[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(tau.getpos(i,j) != -1) {
                    int indexPeca = Integer.parseInt(String.valueOf(tau.getpos(i,j)),2);
                    tauler[i][j] = new Piece(indexPeca);
                    pecesColocades++;
                    piecesPool[indexPeca] = false;
                }
                else tauler[i][j] = new Piece();
            }
        }

    }


    public Piece getPiece(int x, int y) {
        return tauler[x][y];
    }

    public void setPiece(int x, int y, Piece piece){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(tauler[x][y].index == piece.index){
                    System.out.println("La peça " + piece.index + " ja existeix al tauler!!");
                    break;
                }
            }
        }
        tauler[x][y] = piece;
        pecesColocades++;
        piecesPool[piece.index] = false;


    }

    public TaulerExtended copy(){
        return new TaulerExtended(tauler, pecesColocades, piecesPool);
    }

    public Piece[][] getTauler(){
        return tauler;
    }

    public int getNPecesColocades(){
        return pecesColocades;
    }

    public int getNPecesDisponibles(){
        return 16-pecesColocades;
    }

    public boolean[] getPecesDisponibles() {
        return piecesPool;
    }

    public void print() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(tauler[i][j].index != -1)
                    System.out.print(String.format("%4s", Integer.toBinaryString(tauler[i][j].index)).replace(' ', '0') + "  ");
                else
                    System.out.print("----  ");
            }
            System.out.println();
        }

    }

    public Piece getRandomAvailabePiece() {

        Random rand = new Random();
        int index = rand.nextInt(16);

        while(!piecesPool[index]){
            index = rand.nextInt(16);
        }

        return new Piece(index);
    }
}
