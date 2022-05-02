package com.example.matrix_v1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import spencerstudios.com.bungeelib.Bungee;

public class pasosActivity extends AppCompatActivity {
    int flagcombtres = 0;
    static float correccionFlagCombDos;
    static float correcionFlagCombDosDos;
    boolean comprobadorFlagCombDos = true;
    int comprobador =0;
    int x=0;
    int a = 0;
    int aaa= 1;
    int invisible = 0;
    int contador, flag;
    GridLayout[] arrayGrids;
    TextView[] arrayTxt, txt2;
    TextView[] uno;
    float[] resultados;
    static float [][] matriz;
    Bundle parametros;
    LinearLayout base;
    float [] valormatriz;
    Button regresar;
    ConstraintLayout.LayoutParams lparams;
    int flagcomb;
    int boton;
    int determinante;
    boolean ajuste3x3 = false;
    float ajuste = 0.0f;
    int seguirDospordos, seguirTresportres = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pasos);
        base = findViewById(R.id.layoutBase);
        seguirDospordos = 1;
        //Toast.makeText(this, "hola"+dimensiones.dimension, Toast.LENGTH_SHORT).show();

        parametros = this.getIntent().getExtras();

        if(parametros!=null){
             valormatriz = parametros.getFloatArray("valorarreglo");
            contador = parametros.getInt("contador");
            flagcomb = parametros.getInt("flagcomb");
            determinante = parametros.getInt("determinante");
            boton = parametros.getInt("boton");

            flag= parametros.getInt("flag");

            if(flag==1){
                resultados = new float[3];
                resultados = parametros.getFloatArray("resultados");
            }

            matriz = new float[contador][contador+1];
            arrayTxt = new TextView[(contador*3)];
            txt2= new TextView[contador*3];
            uno = new TextView[contador+15];
            arrayGrids = new GridLayout[contador*5];


            acomodarArreglo(valormatriz);
            gaussjordan(contador);

        }

        regresar = findViewById(R.id.btnVolver2);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(pasosActivity.this, OperacionesActivity.class);
                Intent volver2 =  new Intent(pasosActivity.this, CombLinealActivity.class);
                if(flag==1){
                    volver2.putExtra("valorarreglo",valormatriz);
                    startActivity(volver2);
                    Bungee.slideRight(pasosActivity.this);
                }else if(flag==0) {
                    startActivity(volver);
                    Bungee.slideRight(pasosActivity.this);
                }

                finish();
            }
        });


    }

    public void acomodarArreglo(float[] arreglo){
        int i=0;
        for (int x=0; x<contador; x++){
            for (int y=0; y<contador+1; y++){
                matriz[x][y]= arreglo[i];
                i++;
            }
        }
        if(Float.compare(arreglo[2], 0.0f)==0 && Float.compare(arreglo[6], 0.0f)==0 && Float.compare(arreglo[10], 0.0f)==0){
            invisible = 3;
        }
    }

    public void pivote(float matriz[][], int piv, int var) {
        float temp = 0;
        temp = matriz[piv][piv];
        for (int y = 0; y < (var + 1); y++) {
            if(contador == 3 && matriz[2][0]==0.0 && matriz[2][1] == 0.0 && flagcomb == 3 ){
                seguirTresportres = 0;
                break;
            }
            if(contador == 3 && matriz[1][0]==0.0 && matriz[2][0] == 0.0 && flagcomb == 2 ){
                seguirTresportres = 0;
                break;
            }
            if(contador == 2 && matriz[1][0]==0.0 && flagcomb == 5){
                seguirDospordos = 0;
                break;
            }
            matriz[piv][y] = matriz[piv][y] / temp;
        }
    }

    public void hacerceros(float matriz[][], int piv, int var) {
        for (int x = 0; x < var; x++) {
            if (x != piv) {
                float c = matriz[x][piv];
                for (int z = 0; z < (var + 1); z++) {
                    matriz[x][z] = ((-1 * c) * matriz[piv][z]) + matriz[x][z];

                    //correccion flagcomb2
                    if (flagcomb == 2){
                        if (contador == 3 && matriz[1][0] == 0.0 && matriz[1][1] == 0.0 && flagcomb == 2) {
                            correccionFlagCombDos = 0;
                        } else if (contador == 3 && matriz[1][0] == 0.0 && matriz[2][0] != 0.0 && flagcomb == 2) {
                            correccionFlagCombDos = matriz[2][0];
                            correcionFlagCombDosDos = matriz[2][1];
                        }

                    if (contador == 3 && matriz[1][0] == 0.0 && matriz[2][0] == 0.0 && flagcomb == 2 && x == 2 && z == 3) {
                        seguirTresportres = 0;
                        break;
                    }
                }
                    //correccion flagcomb2
                }
            }
        }
    }

    public  void gaussjordan( int contador) {
        int piv = 0;

        int b=0;
        //Toast.makeText(this, "matriz"+contador, Toast.LENGTH_SHORT).show();
        if(contador==2){
            for (a= 0; a < contador+1; a++) {

                    txt2[a] = new TextView(this);
                    txt2[a].setTextAppearance(this, R.style.fonttext);
                    txt2[a].setText("Renglón: "+(b+1)+"\n"+"Entre el pivote ");
                    txt2[a].setPadding(0,50,0,50);

                    base.addView(txt2[a]);
                    pivote(matriz, piv, contador);
                    if (seguirDospordos == 1 || seguirTresportres == 1){
                    }else{
                        txt2[a].setVisibility(View.INVISIBLE);
                        break;
                    }
                    muestramatriz(matriz,contador, x);
                    x++;
                    hacerceros(matriz, piv, contador);
                    uno[a] = new TextView(this);
                    uno[a].setTextAppearance(this, R.style.fonttext);
                    uno[a].setPadding(0,50,0,50);
                    //uno[a].setText("Haciendo ceros");

                    base.addView(uno[a]);
                    muestramatriz(matriz,contador, x);
                    x++;
                    a++;
                    b++;
                    piv++;


            }

        }else{
            for (int a = 0; a < contador+1; a++) {
                txt2[a] = new TextView(this);
                txt2[a].setTextAppearance(this, R.style.fonttext);
                txt2[a].setText("Renglón: "+(b+1)+"\n"+"Entre el pivote ");
                txt2[a].setPadding(0,50,0,50);

                base.addView(txt2[a]);
                if(seguirTresportres == 1) {
                    pivote(matriz, piv, contador);
                }

                if (seguirTresportres == 1){
                }else{
                    txt2[a].setVisibility(View.INVISIBLE);
                    break;
                }

                muestramatriz(matriz,contador, x);
                x++;

                if(dimensiones.dimension.equals("3") && b==1){
                    ajuste3x3 = true;
                    ajuste = matriz[0][3];
                }

                    hacerceros(matriz, piv, contador);
                    uno[a] = new TextView(this);
                    uno[a].setPadding(0, 50, 0, 50);
                    uno[a].setTextAppearance(this, R.style.fonttext);
                    //uno[a].setText("Haciendo ceros");

                    base.addView(uno[a]);
                    muestramatriz(matriz, contador, x);
                    x++;
                    a++;
                    b++;
                    piv++;

                if (flagcomb == 3 && determinante == 1 && seguirTresportres ==1){
                    txt2[a] = new TextView(this);
                    txt2[a].setTextAppearance(this, R.style.fonttext);
                    txt2[a].setText("Renglón: "+(b+1)+"\n"+"Entre el pivote ");
                    txt2[a].setPadding(0,50,0,50);

                    base.addView(txt2[a]);
                    pivote(matriz, piv, contador);
                    if (seguirTresportres == 1){
                    }else{
                        txt2[a].setVisibility(View.INVISIBLE);
                        break;
                    }

                    muestramatriz(matriz,contador, x);
                    x++;

                    if(dimensiones.dimension.equals("3") && b==1){
                        ajuste3x3 = true;
                        ajuste = matriz[0][3];
                    }

                    hacerceros(matriz, piv, contador);
                    uno[a] = new TextView(this);
                    uno[a].setPadding(0, 50, 0, 50);
                    uno[a].setTextAppearance(this, R.style.fonttext);
                    //uno[a].setText("Haciendo ceros");

                    base.addView(uno[a]);
                    if(comprobadorFlagCombDos) {
                        muestramatriz(matriz, contador, x);
                    }
                    b++;
                }

            }
        }
        if(flag==1){
            piv=0;

            for (int a = 0; a < contador; a++) {
                pivote(matriz, piv, contador);
                hacerceros(matriz, piv, contador);
                piv++;

                if(a==2) {
                    int view=0;
                    uno[view] = new TextView(this);
                    uno[view].setPadding(0,50,0,50);
                    uno[view].setText("última iteración: ");
                    uno[view].setTextAppearance(this, R.style.fonttext);
                    base.addView(uno[view]);

                    if (flagcomb != 2){
                        muestramatriz(matriz, contador, x);
                    }

                    else if(flagcomb == 2 && comprobadorFlagCombDos) {
                        muestramatriz(matriz, contador, x);
                    }
                }

            }
        }




    }

    public void muestramatriz(float matriz[][], int var, int grid) {
        int i=0;
        arrayGrids[grid] = new GridLayout(this);
        arrayGrids[grid].setColumnCount((contador+1));

        arrayGrids[grid].setPadding(135,150,0,0);
        arrayGrids[grid].setBackgroundResource(R.drawable.rect2);

            for (int x = 0; x < var; x++) {
            for (int y = 0; y < (var+1); y++) {

                    if (!Float.isNaN(matriz[x][y]) ) {
                        //correccion flagcomb2
                        if (flagcomb == 2){
                            if (grid == 0 && flagcomb == 2) {
                                if (x == 2 && y == 0) {

                                    correccionFlagCombDos = matriz[x][y];
                                }
                                if (x == 2 && y == 1) {
                                    correcionFlagCombDosDos = matriz[x][y];
                                }


                            }

                        if (grid == 1 && flagcomb == 2) {

                            if ((matriz[1][0] == 0.0 || matriz[1][0] == -0.0) && (matriz[1][1] == 0.0 || matriz[1][1] == -0.0)) {
                                comprobador = 1;
                            }

                            if (x == 2) {
                                if (y == 1 && comprobador != 1) {
                                    matriz[x][y] = correcionFlagCombDosDos;
                                }
                                if (y == 0 && comprobador != 1) {
                                    matriz[x][y] = correccionFlagCombDos;
                                }
                            }
                        }
                    }
                        //correccion flag comb 2

                            arrayTxt[x] = new TextView(this);
                            arrayTxt[x].setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);

                            if(y==contador){
                                arrayTxt[x].setText(" | "+matriz[x][y]);
                                if(arrayTxt[x].getText().toString().equals(" | -0.0") || arrayTxt[x].getText().toString().equals(" Infinity")){
                                    arrayTxt[x].setText(" | 0.0");
                                }
                            }else{

                                arrayTxt[x].setText(" | "+matriz[x][y]);
                                if(arrayTxt[x].getText().toString().equals(" | -0.0" ) || arrayTxt[x].getText().toString().equals(" Infinity")){
                                    arrayTxt[x].setText(" | 0.0");
                                }
                            }

                            if(ajuste3x3 && x==0 && y==3){
                                arrayTxt[x].setText(" | "+ajuste);
                            }

                            arrayGrids[grid].addView(arrayTxt[x]);

                            if(arrayTxt[x].getText().toString().equals(" | 0.0")){
                                i = i+1;
                            }
                    }
                    else{
                            arrayTxt[x] = new TextView(this);

                            if(y==contador){
                                arrayTxt[x].setText(" | 0.0");
                            } else{
                                arrayTxt[x].setText(" | 0.0");
                            }


                        arrayGrids[grid].addView(arrayTxt[x]);
                        if(arrayTxt[x].getText().toString().equals(" | 0.0")){
                            i = i+1;
                        }
                    }

                }

            }







        if(i == 12){

        }else{
            //Toast.makeText(this, "inv :"+invisible, Toast.LENGTH_SHORT).show();


            //hacer invisible cuando se desplaza
            if(invisible == 3 && dimensiones.dimension.equals("3") && flagcomb != 2 && boton != 1) {
                arrayGrids[grid].getChildAt(2).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(6).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(10).setVisibility(View.GONE);

            }else if(invisible == 3 && dimensiones.dimension.equals("3") && flagcomb != 2 && boton == 1){
                arrayGrids[grid].getChildAt(2).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(6).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(10).setVisibility(View.GONE);
            }

            if(flagcomb == 0){
                arrayGrids[grid].getChildAt(8).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(9).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(10).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(11).setVisibility(View.GONE);
            }
            if(flagcomb == 2){
                arrayGrids[grid].getChildAt(3).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(7).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(11).setVisibility(View.GONE);
            }
            /*if(MainActivity.esCombinacion == true){
               arrayGrids[grid].getChildAt(3).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(7).setVisibility(View.GONE);
                arrayGrids[grid].getChildAt(11).setVisibility(View.GONE);
            }*/

            base.addView(arrayGrids[grid]);
        }
        //Toast.makeText(this, "flagcomb :"+flagcomb +" "+determinante, Toast.LENGTH_SHORT).show();

    }


}
