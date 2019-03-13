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
        if(index != -1)
            return String.format("%4s", Integer.toBinaryString(index)).replace(' ', '0');
        else
            return "----";
    }

    boolean isValid(){
        return index >= 0 && index < 16;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Piece)) return false;
        Piece o = (Piece) obj;
        return o.index == this.index;
    }

    @Override
    public int hashCode() {
        return index * 16 + xor;
    }
}
