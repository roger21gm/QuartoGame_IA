package Quatro;

import java.awt.Frame;
import javax.swing.JOptionPane;
/*
 * Es la classe que mantindra en memoria tot el nostre entorn de simulació.
 * Aqui és on trobarem el taulell actual. També hi tenim definida la creació de 
 * l'entorn i funcions de execució pas per pas.
 */

/**
 * @author Llorenç
 */
public class Tauler {

    //Atributs publics per facilitar acces

    //atributs privats
    private int dimx;
    private int dimy;
    private int[][] taulell;
    private int[] peça;
    private int jugador;
    private Player1 jugador1;
    private long p1time;
    private Player2 jugador2;
    private long p2time;


    public Tauler() {
        /*Constructor, inicialitza el tamany del taulell
         */
        //inicialitzem el tamany del taulell
        this.dimx = 4;   // minim 4
        this.dimy = 4; // minim 4
        this.taulell =  new int[this.dimx][this.dimy];
        this.jugador=1;
        for(int i = 0; i != this.dimx; i++){
            for (int j = 0; j != this.dimy; j++) {
                this.taulell[i][j]=-1;
            }
        }
        jugador1 = new Player1(this);
        jugador2 = new Player2(this);
        
        this.peça =  new int[4];
        this.peça[0] =(int) java.lang.Math.round( java.lang.Math.random() );
        this.peça[1] =(int) java.lang.Math.round( java.lang.Math.random() );
        this.peça[2] =(int) java.lang.Math.round( java.lang.Math.random() );
        this.peça[3] =(int) java.lang.Math.round( java.lang.Math.random() );

        this.p1time =0;
        this.p2time =0;
        
    }

    public int[][] getTaulell(){
        /*Retorna el taulell*/
        return this.taulell;
    }
    
    public int getpos(int x,int y) {
        /* retorna el valor d'una casella
        -1 = buida
        0 = 0000
        1 = 0001
        10=0010
        ....
         */
        return this.taulell[x][y];
    }
    
    private void setpos(int x,int y, int Color, int Forma, int Forat, int Tamany) {
        /* Afegeix una fitxa
         */
            if(this.taulell[x][y]==-1){//si es buida
                this.taulell[x][y]= Color*1000 + Forma*100 + Forat*10 + Tamany;
            }else{
                Frame frame = new Frame();
                JOptionPane.showMessageDialog(frame, "Jugador"+ this.jugador +" fa un moviment no valid!");
            }
    }

    public boolean Step() {
        boolean acabat=false;
        if (this.jugador==1){
            
            long startTime = System.nanoTime();
            int[] tir=jugador1.tirada(peça[0],peça[1],peça[2],peça[3]);
            long Time = System.nanoTime() - startTime;
            this.p1time=this.p1time + (Time);
            
            //posem la peça on diu
            setpos(tir[0],tir[1],peça[0],peça[1],peça[2],peça[3]);
            
            //Posem seguent peça
            peça[0]=tir[2];
            peça[1]=tir[3];
            peça[2]=tir[4];
            peça[3]=tir[5];
            
            int Valor=peça[0]*1000 + peça[1]*100 + peça[2]*10 + peça[3];
            //busquem la peça
            for(int i=0;i<this.getX();i++){
                for(int j=0;j<this.getY();j++){
                    if (this.getpos(i,j) == Valor){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Jugador"+ this.jugador +" proposa peça repetida!");
                    }
                }
            }
            
            acabat=fi();
            this.jugador=2;
            
        }else{
            long startTime = System.nanoTime();
            int[] tir=jugador2.tirada(peça[0],peça[1],peça[2],peça[3]);
            long Time = System.nanoTime() - startTime;
            this.p2time=this.p2time + (Time);
            
             //posem la peça on diu
            setpos(tir[0],tir[1],peça[0],peça[1],peça[2],peça[3]);
            
            //Posem seguent peça
            peça[0]=tir[2];
            peça[1]=tir[3];
            peça[2]=tir[4];
            peça[3]=tir[5];
            
            int Valor=peça[0]*1000 + peça[1]*100 + peça[2]*10 + peça[3];
            //busquem la peça
            for(int i=0;i<this.getX();i++){
                for(int j=0;j<this.getY();j++){
                    if (this.getpos(i,j) == Valor){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Jugador"+ this.jugador +" proposa peça repetida!");
                    }
                }
            }
                       
            acabat=fi();
            this.jugador=1;
        }
        //retornem true si falten passos de simulació
       /*//Per veure els tems d'execucio de cada jugador descomentar
        if (acabat){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Jugador 1 = "+ Long.toString(this.p1time/1000000000) +" Segons, Jugador 2 = " + Long.toString(this.p2time/1000000000) +"Segons" ); 
        }*/
        return acabat;
    }

    public double getX() {
        /*Metode que retorna la dimenció X del taulell de simulació, la
         * coordenada (0,0) es la de dalt a l'esquerra i la de baix a la dreta
         * es la (dimx,dimy)
         */
        return dimx;
    }

    public double getY() {
        /*Metode que retorna la dimenció X del taulell de simulació, la
         * coordenada (0,0) es la de dalt a l'esquerra i la de baix a la dreta
         * es la (dimx,dimy)
         */
        return dimy;
    }

    private boolean fi() {
        //si ha acabat el joc retornar true altrament false
        
       
        if (guanya(taulell[0][0],taulell[0][1],taulell[0][2],taulell[0][3])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (vertical 1)");
            return true;
        }
        
        if (guanya(taulell[1][0],taulell[1][1],taulell[1][2],taulell[1][3])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (vertical 2)");
            return true;
        }           
        
        if (guanya(taulell[2][0],taulell[2][1],taulell[2][2],taulell[2][3])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (vertical 3)");
            return true;
        }

        if (guanya(taulell[3][0],taulell[3][1],taulell[3][2],taulell[3][3])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (vertical 4)");
            return true;
        }

        
         if (guanya(taulell[0][0],taulell[1][0],taulell[2][0],taulell[3][0])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (horitzontal 1)");
            return true;
        }
        
        if (guanya(taulell[0][1],taulell[1][1],taulell[2][1],taulell[3][1])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (horitzontal 2)");
            return true;
        }           
        
        if (guanya(taulell[0][2],taulell[1][2],taulell[2][2],taulell[3][2])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (horitzontal 3)");
            return true;
        }

        if (guanya(taulell[0][3],taulell[1][3],taulell[2][3],taulell[3][3])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (horitzontal 4)");
            return true;
        }

        
        if (guanya(taulell[0][0],taulell[1][1],taulell[2][2],taulell[3][3])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (diagonal 1)");
            return true;
        }

        
        if (guanya(taulell[0][3],taulell[1][2],taulell[2][1],taulell[3][0])){
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Guanya jugador"+ Integer.toString(this.jugador) +"! (diagonal 2)");
            return true;
        }
        
        //fi de joc no queden caselles lliures ??
         for(int i=0;i<this.getX();i++){
            for(int j=0;j<this.getY();j++){
                if (this.getpos(i,j) == -1){
                    return false;
                }
            }
        }
        Frame frame2 = new Frame();
         JOptionPane.showMessageDialog(frame2, "Empat!");
        return true;
    }
    
    private boolean guanya(int p1, int p2, int p3, int p4){
        // 1 si es una combinacio guanyadora o si no
        if( p1==-1 || p2==-1 || p3==-1 || p4==-1){
            //hi ha algun dels 4 buit no podem guanyar
            return false;
        }else{
            //les 4 peçes tenen valor    

            //peça p1              
            int Color1=(int) (p1/1000);
            p1 = p1 - Color1 * 1000;
            int Forma1=(int) (p1%1000/100);
            p1 = p1 - Forma1 * 100;
            int Forat1=(int) (p1%1000/10);
            p1 = p1 - Forat1 * 10;
            int Tamany1=(int) (p1%1000/1);

            //peça p2
            int Color2=(int) (p2/1000);
            p2 = p2 - Color2 * 1000;
            int Forma2=(int) (p2%1000/100);
            p2 = p2 - Forma2 * 100;
            int Forat2=(int) (p2%1000/10);
            p2 = p2 - Forat2 * 10;
            int Tamany2=(int) (p2%1000/1);  

            //peça p3
            int Color3=(int) (p3/1000);
            p3 = p3 - Color3 * 1000;
            int Forma3=(int) (p3%1000/100);
            p3 = p3 - Forma3 * 100;
            int Forat3=(int) (p3%1000/10);
            p3 = p3 - Forat3 * 10;
            int Tamany3=(int) (p3%1000/1);

            //peça p4  
            int Color4=(int) (p4/1000);
            p4 = p4 - Color4 * 1000;
            int Forma4=(int) (p4%1000/100);
            p4 = p4 - Forma4 * 100;
            int Forat4=(int) (p4%1000/10);
            p4 = p4 - Forat4 * 10;
            int Tamany4=(int) (p4%1000/1); 

            int Color = Color1 + Color2 + Color3 + Color4;
            int Forma = Forma1 + Forma2 + Forma3 + Forma4;
            int Forat = Forat1 + Forat2 + Forat3 + Forat4;
            int Tamany = Tamany1 + Tamany2 + Tamany3 + Tamany4;

            return Color == 0 || Color == 4 || Forma==0 || Forma==4 || Forat==0 || Forat==4 || Tamany==0 || Tamany==4;
        }
    }
    
}
