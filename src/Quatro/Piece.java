package Quatro;

public class Piece implements Cloneable{
    public int index;
    public int xor;

    Piece(int index) {
        this.index = index;
        xor = index ^ 15;
    }

    Piece(){
        index = -1;
        xor = -1;
    }

    Piece(int colorin, int formain, int foratin, int tamanyin){
        this.index = Integer.parseInt("" + colorin + formain + foratin + tamanyin, 2);
        xor = index ^ 15;
    }

    String getPieceAsString(){
        return String.format("%4s", Integer.toBinaryString(index)).replace(' ', '0');
    }

    boolean isValid(){
        return index >= 0 && index < 16;
    }
}
