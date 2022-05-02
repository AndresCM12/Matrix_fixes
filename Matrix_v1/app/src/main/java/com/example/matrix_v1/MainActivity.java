package com.example.matrix_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import spencerstudios.com.bungeelib.Bungee;


public class MainActivity extends AppCompatActivity {

    int flagcombtres = 0;
    static float correccionFlagCombDos;
    static float correcionFlagCombDosDos;
    boolean corrccionFlagCombDosBoolean = false;
    int contadorDeCiclos = 1;
    boolean comprobadorFlagCombDos = true;
    static float[][] matriz, auxiliar, auxiliar2;
    static float[] valoresmatriz,escalares,apy;
    float[] arregloaEnviar;
    TextView txtRenglonUno_1,txtRenglonUno_2,txtRenglonUno_3,txtRenglonUno_4,txtRenglonUno_5, txtRenglonUno_a;
    TextView txtRenglonDos_1,txtRenglonDos_2,txtRenglonDos_3,txtRenglonDos_4,txtRenglonDos_5, txtRenglonDos_b;
    TextView txtRenglonTres_1,txtRenglonTres_2,txtRenglonTres_3,txtRenglonTres_4,txtRenglonTres_5, txtRenglonTres_c;
    int contador;
    String titulo;
    String descripcion;
    Intent next, next2;
    int flagComb;
    int boton;
    String fomr;
    Button btnCombinacionLineal;
    int determinante, determinanteCalc;
    boolean ajuste3x3 = false;
    boolean ajuste3x3Dos = false;
    float ajuste = 0.0f;
    int seguirDospordos = 1;
    int seguirTresportres = 1;
    static boolean esCombinacion = false;
    boolean cuadrada = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        contador=2;
        escalares = new float[3];
        auxiliar = new float[3][3+1];
        auxiliar2 = new float[3][3+1];

        txtRenglonUno_1 = findViewById(R.id.txt1);
        txtRenglonUno_2 = findViewById(R.id.txt2);
        txtRenglonUno_3 = findViewById(R.id.txt3);
        txtRenglonUno_a = findViewById(R.id.txtA);

        txtRenglonDos_1 = findViewById(R.id.txt6);
        txtRenglonDos_2 = findViewById(R.id.txt7);
        txtRenglonDos_3 = findViewById(R.id.txt8);

        txtRenglonDos_b = findViewById(R.id.txtB);

        txtRenglonTres_1 = findViewById(R.id.txt11);
        txtRenglonTres_2 = findViewById(R.id.txt12);
        txtRenglonTres_3 = findViewById(R.id.txt13);

        txtRenglonTres_c = findViewById(R.id.txtC);
        btnCombinacionLineal = findViewById(R.id.btnUno);

        interpretarContadorTextViews(contador);
        final Button btnMenos = findViewById(R.id.btnMenos);
        final Button btnMas = findViewById(R.id.btnMas);

        btnMenos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (contador ==3) {
                    contador --;
                }
                interpretarContadorTextViews(contador);
             }
        });
        btnMas.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (contador ==2) {
                    contador ++;
                }
                interpretarContadorTextViews(contador);
            }
        });


        btnCombinacionLineal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int flag=0;
                next = new Intent(MainActivity.this, OperacionesActivity.class);
                titulo = "Combinación lineal";
                descripcion = "";
                next.putExtra("contador",contador);
                next.putExtra("titulo",titulo);
                next.putExtra("descripcion",descripcion);
                try{
                    mandarDatos(contador);
                }catch(Exception e){
                    crearAlerta();
                    flag=1;
                }
                if (flag==0) {
                    boton = 0;
                    next.putExtra("boton", boton);
                    String compComb = comprobarCombinacionLineal();
                    next.putExtra("determinante", determinante);
                    next.putExtra("comp", compComb);
                    next.putExtra("flagcomb", flagComb);
                    next.putExtra("arreglo", arregloaEnviar);
                    next.putExtra("arreglo2", valoresmatriz);
                    for(int a=0; a<apy.length; a++){
                        apy[a] = 0;
                   }
                    next.putExtra("arreglo3", apy);
                    startActivity(next);
                }flag=0;




            }
        });

        final Button btnIndependenciaLineal = findViewById(R.id.btnDos);
        btnIndependenciaLineal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int flag =0;
                    next = new Intent(MainActivity.this, OperacionesActivity.class);
                    titulo = "";

                    String compInd = "";
                    next.putExtra("contador",contador);
                    next.putExtra("titulo",titulo);

               try{
                    mandarDatos(contador);

              }catch(Exception e) {
                   int duration= Toast.LENGTH_SHORT;
                   crearAlerta();
                   flag = 1;

               }
                if(flag==0) {
                    boton = 1;
                    next.putExtra("boton", boton);
                    descripcion = "";
                    next.putExtra("descripcion",descripcion);
                    compInd = comprobarInd();
                    //Toast.makeText(MainActivity.this, "Determinante"+determinanteCalc, Toast.LENGTH_SHORT).show();
                    next.putExtra("determinante", determinante);
                    next.putExtra("comp", compInd);
                    next.putExtra("flagcomb", flagComb);
                    next.putExtra("arreglo", arregloaEnviar);
                    next.putExtra("arreglo2", valoresmatriz);
                    next.putExtra("arreglo3", apy);
                    startActivity(next);
                }flag=0;
            }
        });


        final Button btnCambioBase = findViewById(R.id.btnTres);
        btnCambioBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=0;
                next = new Intent(MainActivity.this, OperacionesActivity.class);
                titulo = "conjunto generador de base "+contador+"x"+contador;
                String compInd = "";
                next.putExtra("contador",contador);
                next.putExtra("titulo",titulo);
                next.putExtra("conjunto", true);

                try{
                    mandarDatos(contador);
                    String compBase = comprobarBase();
                    next.putExtra("comp", compBase);
                }catch(Exception e){
                    int duration= Toast.LENGTH_SHORT;
                    crearAlerta();
                    flag=1;
                }
                if (flag==0) {
                    boton = 0;
                    next.putExtra("boton", boton);
                    descripcion = "Si el determinante de la matriz es = 0 \n No tiene inversa, en consecuencia los vectores no generan el espacio vectorial\n";
                    next.putExtra("descripcion",descripcion);
                    next.putExtra("flagcomb", flagComb);
                    next.putExtra("arreglo", arregloaEnviar);
                    next.putExtra("arreglo2", valoresmatriz);
                    next.putExtra("arreglo3", apy);
                    next.putExtra("key", 1);
                    String compBase = comprobarInd();
                    compBase = comprobarBase();
                    next.putExtra("comp", compBase);
                    next.putExtra("determinante", determinante);
                    startActivity(next);
                }flag=0;
            }
        });



        final Button btnArrow = findViewById(R.id.btnArrow);
        btnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ayuda = new Intent(MainActivity.this, ayuda.class);
                startActivity(ayuda);
                Bungee.slideUp(MainActivity.this);
            }
        });
    }



        public void interpretarContadorTextViews(int cont){
        matriz = new float[cont][cont + 1];

        switch (cont){
            case (2):
                txtRenglonUno_1.setVisibility(View.VISIBLE);
                txtRenglonUno_2.setVisibility(View.VISIBLE);
                txtRenglonDos_1.setVisibility(View.VISIBLE);
                txtRenglonDos_2.setVisibility(View.VISIBLE);
                txtRenglonUno_3.setVisibility(View.INVISIBLE);
                txtRenglonDos_3.setVisibility(View.INVISIBLE);
                txtRenglonTres_1.setVisibility(View.INVISIBLE);
                txtRenglonTres_2.setVisibility(View.INVISIBLE);
                txtRenglonTres_3.setVisibility(View.INVISIBLE);


                txtRenglonUno_a.setVisibility(View.VISIBLE);
                txtRenglonDos_b.setVisibility(View.VISIBLE);

                txtRenglonUno_3.setVisibility(View.INVISIBLE);
                txtRenglonDos_3.setVisibility(View.INVISIBLE);

                txtRenglonUno_1.setEnabled(true);
                txtRenglonUno_2.setEnabled(true);
                txtRenglonDos_1.setEnabled(true);
                txtRenglonDos_2.setEnabled(true);
                txtRenglonUno_a.setEnabled(true);
                txtRenglonDos_b.setEnabled(true);
                txtRenglonUno_3.setEnabled(false);
                txtRenglonDos_3.setEnabled(false);
                txtRenglonTres_1.setEnabled(false);
                txtRenglonTres_2.setEnabled(false);
                txtRenglonTres_3.setEnabled(false);
                txtRenglonTres_c.setEnabled(false);
                txtRenglonTres_c.setVisibility(View.INVISIBLE);



                break;
            case (3):
                txtRenglonUno_3.setVisibility(View.VISIBLE);
                txtRenglonDos_3.setVisibility(View.VISIBLE);
                txtRenglonTres_1.setVisibility(View.VISIBLE);
                txtRenglonTres_2.setVisibility(View.VISIBLE);
                txtRenglonTres_3.setVisibility(View.VISIBLE);

                txtRenglonTres_c.setEnabled(true);
                txtRenglonTres_c.setVisibility(View.VISIBLE);



                txtRenglonUno_3.setEnabled(true);
                txtRenglonDos_3.setEnabled(true);
                txtRenglonTres_1.setEnabled(true);
                txtRenglonTres_2.setEnabled(true);
                txtRenglonTres_3.setEnabled(true);




                break;
        }
    }

        public void mandarDatos(int contador) {
       next2 = new Intent(MainActivity.this, OperacionesActivity.class);

        switch (contador) {
            case (2):
                    TextView[] txtViews2x2 = {txtRenglonUno_1,txtRenglonUno_2, txtRenglonUno_a, txtRenglonDos_1,txtRenglonDos_2, txtRenglonDos_b};
                    arregloaEnviar = new float[txtViews2x2.length];
                    llenarMatriz();
                    gaussjordan(txtViews2x2, contador);
                    break;
                    case (3):
                        TextView[] txtViews3x3 = { txtRenglonUno_1,txtRenglonUno_2,txtRenglonUno_3, txtRenglonUno_a,
                                txtRenglonDos_1,txtRenglonDos_2,txtRenglonDos_3, txtRenglonDos_b, txtRenglonTres_1,txtRenglonTres_2,txtRenglonTres_3, txtRenglonTres_c};
                        arregloaEnviar = new float[txtViews3x3.length];
                        llenarMatriz();
                        gaussjordan(txtViews3x3, contador);

                        break;
                }

        }

        public  void llenarMatriz(){
            int b=0;

            switch (contador){

                //flagComb 1 = 3 columnas 2 renglones (3x2) (normal)
                //flagComb 5 = 2 columnas 2 renglones (2x2) (sin a y b)
                //flagComb 2 = 2 columnas 3 renglones (2x3)
                //flagComb 3 = 3 columnas 3 renglones (3x3) (sin a y b y c)
                //flagComb 0 = 4 columnas 2 renglones (4x2)
                //flagComb 4 = 4 columnas 3 renglones (4x3) (normal)


                case(2):
                    dimensiones.setDimension("1");
                    if(txtRenglonUno_a.getText().toString().equals("") && txtRenglonDos_b.getText().toString().equals("")){
                        flagComb = 5;
                    }else{
                        flagComb = 1;
                    }
                    apy = new float[11];
                    apy[0] = Integer.parseInt(txtRenglonUno_1.getText().toString());
                    apy[1] = Integer.parseInt(txtRenglonUno_2.getText().toString());

                    apy[3] = Integer.parseInt(txtRenglonDos_1.getText().toString());
                    apy[4] = Integer.parseInt(txtRenglonDos_2.getText().toString());

                    float a2x2;
                    float b2x2;
                    float uno2x2 = Float.parseFloat(txtRenglonUno_1.getText().toString());
                    float dos2x2 = Float.parseFloat(txtRenglonUno_2.getText().toString());
                    try{
                        a2x2 = Float.parseFloat(txtRenglonUno_a.getText().toString());
                        b2x2 = Float.parseFloat(txtRenglonDos_b.getText().toString());
                    }catch (Exception e){
                        a2x2 = 0;
                        b2x2=0;
                    }


                    float tres2x2 = Float.parseFloat(txtRenglonDos_1.getText().toString());
                    float cuatro2x2 = Float.parseFloat(txtRenglonDos_2.getText().toString());


                    matriz[0][0] =  uno2x2;
                    matriz[0][1] =  dos2x2;
                    matriz[0][2] =  a2x2;

                    matriz[1][0] =  tres2x2;
                    matriz[1][1] =  cuatro2x2;
                    matriz[1][2] =  b2x2;

                    break;

                case(3):


                    float uno3x3 = Float.parseFloat(txtRenglonUno_1.getText().toString());
                    float dos3x3 = Float.parseFloat(txtRenglonUno_2.getText().toString());



                    float cuatro3x3 = Float.parseFloat(txtRenglonDos_1.getText().toString());
                    float cinco3x3 = Float.parseFloat(txtRenglonDos_2.getText().toString());


                    //flagComb 2 = 2 columnas 3 renglones (2x3)
                    if(txtRenglonUno_3.getText().toString().equals("") && txtRenglonDos_3.getText().toString().equals("") && txtRenglonTres_3.getText().toString().equals("") &&
                            txtRenglonUno_a.getText().toString().equals("") && txtRenglonDos_b.getText().toString().equals("") && txtRenglonTres_c.getText().toString().equals("")

                            ||

                            txtRenglonUno_3.getText().toString().equals("0") && txtRenglonDos_3.getText().toString().equals("0") && txtRenglonTres_3.getText().toString().equals("0") &&
                            txtRenglonUno_a.getText().toString().equals("") && txtRenglonDos_b.getText().toString().equals("") && txtRenglonTres_c.getText().toString().equals("")

                    ){

                        apy = new float[11];
                        apy[0] = Integer.parseInt(txtRenglonUno_1.getText().toString());
                        apy[1] = Integer.parseInt(txtRenglonUno_2.getText().toString());
                        apy[2] = 0;

                        apy[3] = Integer.parseInt(txtRenglonDos_1.getText().toString());
                        apy[4] = Integer.parseInt(txtRenglonDos_2.getText().toString());
                        apy[5] = 0;

                        apy[6] = Integer.parseInt(txtRenglonTres_1.getText().toString());
                        apy[7] = Integer.parseInt(txtRenglonTres_2.getText().toString());
                        apy[8] = 0;

                        float a3x3 = 0;
                        float b3x3 = 0;
                        float c3x3 = 0;

                        matriz[0][3] = a3x3;
                        matriz[1][3] = b3x3;
                        matriz[2][3] = c3x3;

                        float tres3x3 = 0;
                        float seis3x3 = 0;
                        float nueve3x3 = 0;

                        matriz[0][2] = tres3x3;
                        matriz[1][2] = seis3x3;
                        matriz[2][2] = nueve3x3;

                        matriz[0][0] = uno3x3;
                        matriz[0][1] = dos3x3;


                        matriz[1][0] = cuatro3x3;
                        matriz[1][1] = cinco3x3;

                        float siete3x3 = Float.parseFloat(txtRenglonTres_1.getText().toString());
                        float ocho3x3 = Float.parseFloat(txtRenglonTres_2.getText().toString());

                        matriz[2][0] = siete3x3;
                        matriz[2][1] = ocho3x3;
                        dimensiones.setDimension("2");

                        cuadrada = false;
                        flagComb = 2;
                    }

                    //flagComb 3 = 3 columnas 3 renglones (3x3)
                    else if(txtRenglonUno_a.getText().toString().equals("") && txtRenglonDos_b.getText().toString().equals("") &&
                        txtRenglonTres_c.getText().toString().equals("") || txtRenglonUno_a.getText().toString().equals("0") && txtRenglonDos_b.getText().toString().equals("0") &&
                            txtRenglonTres_c.getText().toString().equals("0") ){

                        apy = new float[11];
                        apy[0] = Integer.parseInt(txtRenglonUno_1.getText().toString());
                        apy[1] = Integer.parseInt(txtRenglonUno_2.getText().toString());
                        apy[2] = Integer.parseInt(txtRenglonUno_3.getText().toString());

                        apy[3] = Integer.parseInt(txtRenglonDos_1.getText().toString());
                        apy[4] = Integer.parseInt(txtRenglonDos_2.getText().toString());
                        apy[5] = Integer.parseInt(txtRenglonDos_3.getText().toString());

                        apy[6] = Integer.parseInt(txtRenglonTres_1.getText().toString());
                        apy[7] = Integer.parseInt(txtRenglonTres_2.getText().toString());
                        apy[8] = Integer.parseInt(txtRenglonTres_3.getText().toString());

                        float tres3x3 = Float.parseFloat(txtRenglonUno_3.getText().toString());
                        float a3x3 = Float.parseFloat(txtRenglonUno_3.getText().toString());
                        float b3x3 = Float.parseFloat(txtRenglonDos_3.getText().toString());
                        float c3x3 = Float.parseFloat(txtRenglonTres_3.getText().toString());
                        float seis3x3 = Float.parseFloat(txtRenglonDos_3.getText().toString());
                        matriz[0][2] = a3x3;
                        matriz[1][2] = b3x3;
                        matriz[2][2] = c3x3;

                        float siete3x3 = Float.parseFloat(txtRenglonTres_1.getText().toString());
                        float ocho3x3 = Float.parseFloat(txtRenglonTres_2.getText().toString());
                        float nueve3x3 = Float.parseFloat(txtRenglonTres_3.getText().toString());

                        tres3x3 = 0;
                        seis3x3 = 0;
                        nueve3x3 = 0;

                        matriz[0][3] = tres3x3;
                        matriz[1][3] = seis3x3;
                        matriz[2][3] = nueve3x3;

                        matriz[0][1] = dos3x3;
                        matriz[1][1] = cinco3x3;
                        matriz[2][0]= siete3x3;
                        matriz[2][1]= ocho3x3;
                        dimensiones.setDimension("3");

                        flagComb = 3;
                    }

                    else if (txtRenglonTres_1.getText().toString().equals("") && txtRenglonTres_2.getText().toString().equals("")
                            && txtRenglonTres_3.getText().toString().equals("") && txtRenglonTres_c.getText().toString().equals("")) {

                        apy = new float[11];
                        apy[0] = Integer.parseInt(txtRenglonUno_1.getText().toString());
                        apy[1] = Integer.parseInt(txtRenglonUno_2.getText().toString());
                        apy[2] = Integer.parseInt(txtRenglonUno_3.getText().toString());

                        apy[3] = Integer.parseInt(txtRenglonDos_1.getText().toString());
                        apy[4] = Integer.parseInt(txtRenglonDos_2.getText().toString());
                        apy[5] = Integer.parseInt(txtRenglonDos_3.getText().toString());

                        apy[6] = 0;
                        apy[7] = 0;
                        apy[8] = 0;

                        float siete3x3 = 0;
                        float ocho3x3 = 0;
                        float nueve3x3 = 0;
                        float c3x3 = 0;
                        float seis3x3 = Float.parseFloat(txtRenglonDos_3.getText().toString());
                        float tres3x3 = Float.parseFloat(txtRenglonUno_3.getText().toString());
                        float a3x3 = Float.parseFloat(txtRenglonUno_a.getText().toString());
                        float b3x3 = Float.parseFloat(txtRenglonDos_b.getText().toString());

                        matriz[0][2] = tres3x3;
                        matriz[1][2] = seis3x3;
                        matriz[2][0] = siete3x3;
                        matriz[2][1] = ocho3x3;
                        matriz[2][2] = nueve3x3;
                        matriz[2][3] = c3x3;
                        matriz[0][1] = dos3x3;
                        matriz[1][1] = cinco3x3;
                        matriz[0][3] = a3x3;
                        matriz[1][3] = b3x3;
                        flagComb = 0;
                        dimensiones.setDimension("0");

                    }else {


                        float seis3x3 = Float.parseFloat(txtRenglonDos_3.getText().toString());
                        float tres3x3 = Float.parseFloat(txtRenglonUno_3.getText().toString());
                        float a3x3 = Float.parseFloat(txtRenglonUno_a.getText().toString());
                        float b3x3 = Float.parseFloat(txtRenglonDos_b.getText().toString());
                            float siete3x3 = Float.parseFloat(txtRenglonTres_1.getText().toString());
                            float ocho3x3 = Float.parseFloat(txtRenglonTres_2.getText().toString());
                            float nueve3x3 = Float.parseFloat(txtRenglonTres_3.getText().toString());
                            float c3x3 = Float.parseFloat(txtRenglonTres_c.getText().toString());
                        matriz[0][1] = dos3x3;
                        matriz[1][1] = cinco3x3;
                            matriz[0][2] = tres3x3;
                            matriz[1][2] = seis3x3;
                            matriz[2][0] = siete3x3;
                            matriz[2][1] = ocho3x3;
                            matriz[2][2] = nueve3x3;
                            matriz[2][3] = c3x3;
                             matriz[0][3] = a3x3;
                            matriz[1][3] = b3x3;
                            flagComb = 4;
                            dimensiones.setDimension("4");


                        apy = new float[11];
                        apy[0] = Integer.parseInt(txtRenglonUno_1.getText().toString());
                        apy[1] = Integer.parseInt(txtRenglonUno_2.getText().toString());
                        apy[2] = Integer.parseInt(txtRenglonUno_3.getText().toString());

                        apy[3] = Integer.parseInt(txtRenglonDos_1.getText().toString());
                        apy[4] = Integer.parseInt(txtRenglonDos_2.getText().toString());
                        apy[5] = Integer.parseInt(txtRenglonDos_3.getText().toString());

                        apy[6] = Integer.parseInt(txtRenglonTres_1.getText().toString());
                        apy[7] = Integer.parseInt(txtRenglonTres_2.getText().toString());
                        apy[8] = Integer.parseInt(txtRenglonTres_3.getText().toString());

                    }
                    matriz[0][0] = uno3x3;
                    matriz[1][0] = cuatro3x3;
                    break;

            }
            int i=0;

            valoresmatriz = new float[30];


            for (int x=0; x<contador; x++){
                for (int y=0; y<contador+1; y++){
                    auxiliar2[x][y]=matriz[x][y];
                    valoresmatriz[i]=matriz[x][y];
                    i++;
                }
            }
            if(flagComb == 0 && flagComb == 2 && flagComb == 4 && flagComb == 1){
                cuadrada = false;
            }

        }

        public void pivote(float matriz[][], int piv, int var) {
            float temp = 0;
            temp = matriz[piv][piv];
            for (int y = 0; y < (var + 1); y++) {
                if(contador == 3 && matriz[2][0]==0.0 && matriz[2][1] == 0.0 && flagComb == 3 ){
                    seguirTresportres = 0;
                    break;
                }

                if(contador == 2 && matriz[1][0]==0.0 && flagComb == 5){
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

                        if (flagComb == 3){
                            if(contador == 3 && matriz[2][0] == 0.0 && matriz[2][1] == 0.0 && flagComb == 3){
                                //ajuste = matriz[0][2];
                                ajuste3x3Dos = true;
                            }
                        }

                        //correccion flagcomb 2
                        if (flagComb == 2){
                            if (contador == 3 && matriz[1][0] == 0.0 && matriz[1][1] == 0.0 && flagComb == 2) {
                                correccionFlagCombDos = 0;
                            } else if (contador == 3 && matriz[1][0] == 0.0 && matriz[2][0] != 0.0 && flagComb == 2) {
                                correccionFlagCombDos = matriz[2][0];
                                correcionFlagCombDosDos = matriz[2][1];
                            }

                        if (contador == 3 && matriz[1][0] == 0.0 && matriz[2][0] == 0.0 && flagComb == 2 && x == 2 && z == 3) {
                            seguirTresportres = 0;
                            break;
                        }
                    }
                        //correccion flagcomb 2
                    }
                }
            }
        }

        public void muestramatriz(float matriz[][], int var,TextView[] txts) {
        int i=0;

        for (int x = 0; x < var; x++) {
            for (int y = 0; y < (var+1); y++) {

                if (!Float.isNaN(matriz[x][y])) {
                    if(!Float.isInfinite(matriz[x][y])) {

                        if(flagComb == 3){
                            if (i==5 && flagcombtres ==0) {
                                ajuste = matriz[0][2];
                                flagcombtres = 1;
                            }
                            if(ajuste3x3Dos && flagcombtres != 0){
                                arregloaEnviar[2] = ajuste;
                            }
                        }

                        if(ajuste3x3 && i==3){
                            //arregloaEnviar[i] = ajuste;
                            i++;
                        }else{
                            arregloaEnviar[i] = matriz[x][y];
                            i++;
                        }
                        //correccion flagcomb2
                        if(flagComb == 2) {
                            if (correccionFlagCombDos != 0.0 && i == 10 && correcionFlagCombDosDos != 0.0) {
                                arregloaEnviar[8] = correccionFlagCombDos;
                                arregloaEnviar[9] = correcionFlagCombDosDos;
                            }
                        }
                        //correccion flagcomb2

                    }
                }
            contadorDeCiclos = contadorDeCiclos + 1;
            }

        }


    }

        public  void gaussjordan(TextView[] txts, int contador) {
            int piv = 0;

            for (int a = 0; a < contador; a++) {

                if(contador==3) {

                    for (int x = 0; x < 3; x++) {
                        for (int y = 0; y < (3+1); y++) {

                            if (!Float.isNaN(matriz[x][y])) {
                                if(!Float.isInfinite(matriz[x][y])) {
                                    auxiliar[x][y] = matriz[x][y];

                                }
                            }


                        }

                    }
                }

                if(seguirTresportres == 1) {
                    pivote(matriz, piv, contador);
                }
                if (seguirDospordos == 1 || seguirTresportres == 1){
                }else{
                    break;
                }
                        if(seguirTresportres == 1) {
                            hacerceros(matriz, piv, contador);
                        }
                piv++;
                        if (flagComb != 2){
                            muestramatriz(matriz, contador, txts);
                        }

                    else if(flagComb == 2 && comprobadorFlagCombDos) {
                        muestramatriz(matriz, contador, txts);
                    }

            }

        }

        public String comprobarBase(){

            if(!Python.isStarted()){
                Python.start(new AndroidPlatform(this));
            }
            Python py = Python.getInstance();
            PyObject pyobj = py.getModule("matrix");
            Log.wtf("valores", ""+ apy[0]+" "+apy[1]+" "+apy[2]+" "+apy[3] );
            PyObject function = pyobj.callAttr("det",apy,contador);


            String comprobar = ""+function.toString();

        return comprobar;
        }

        public String comprobarInd(){
        String devolver;
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("matrix");
        Log.wtf("valores", ""+ apy[0]+" "+apy[1]+" "+apy[2]+" "+apy[3] );
        PyObject function = pyobj.callAttr("det",apy,contador);
        PyObject function2 = pyobj.callAttr("det2",apy,contador);
        determinanteCalc = Math.round(Float.parseFloat(""+function2.toString()));
        String comprobar =""+function.toString();
        if(comprobar.equals("No son ") && seguirDospordos == 0 && seguirTresportres == 0){
            devolver = "Son Linealmente Dependientes ";
            determinante = 0;
            //funciona para matrices de 2 x 3
            if(flagComb == 2){

                int cont = 0;
                for (int x = 0; x < 2; x++)
                {
                    escalares[x] = matriz[x][2];
                }

                int resula1= Math.round(auxiliar2[0][0]*escalares[0] + auxiliar2[0][1]*escalares[1]);
                if(resula1==auxiliar2[0][2]) {
                    cont++;
                }

                int resula2= Math.round(auxiliar2[1][0]*escalares[0] + auxiliar2[1][1]*escalares[1]);
                if(resula2==auxiliar2[1][2]) {
                    cont++;
                }


                if (cont==2){
                    //por si solo queda z
                    if(Float.compare(matriz[2][0],0.0f)==0 && Float.compare(matriz[2][1],0.0f)==0 && Float.compare(matriz[2][2],0.0f)==0 &&
                            Float.compare(matriz[2][2],0.0f) != 0 ){
                        devolver = "Son Linealmente Dependientes ";
                        determinante = 0;
                    }else if(seguirDospordos != 0){
                        devolver = "Son Linealmente Independientes ";
                        determinante = 1;
                    }

                }else{
                    devolver = "Son Linealmente Dependientes ";
                    determinante = 0;
                }

            }
            //funciona para matrices de 2 x 3

            //funciona para matrices de 3 x 3
            if(flagComb == 3){

                //por si solo resulta que tenemos a
                    if(Float.isNaN(matriz[1][0]) && Float.isNaN(matriz[1][1]) && Float.isNaN(matriz[1][2]) &&
                            Float.isNaN(matriz[2][0]) && Float.isNaN(matriz[2][1]) && Float.isNaN(matriz[2][2]) && seguirTresportres == 0){
                        devolver = "Son Linealmente Dependientes ";
                        determinante = 0;
                    }else{
                        devolver = "Son Linealmente Independientes ";
                        determinante = 1;
                    }

            }
            //funciona para matrices de 3 x 3

        }else{
            devolver = "";
            if(seguirTresportres != 0 && seguirDospordos != 0){
                devolver = "Son Linealmente Independientes";
                determinante = 1;
            }else{
                devolver = "Son Linealmente Dependientes";
                determinante = 0;
            }


        }
        if(determinanteCalc != 0){
            devolver = "Son Linealmente Independientes";
            determinante = 1;
        }

        return devolver;
    }

        public String comprobarCombinacionLineal() {
            if(!Python.isStarted()){
                Python.start(new AndroidPlatform(this));
            }
            Python py = Python.getInstance();
            PyObject pyobj = py.getModule("matrix");
            PyObject function2 = pyobj.callAttr("det2",apy,contador);
            determinanteCalc = Math.round(Float.parseFloat(""+function2.toString()));

            esCombinacion = true;
            //flagComb 1 = 3 columnas 2 renglones (3x2)
            //flagComb 5 = 2 columnas 2 renglones (2x2) (sin a y b)
            //flagComb 2 = 2 columnas 3 renglones (2x3)
            //flagComb 3 = 3 columnas 3 renglones (3x3)
            //flagComb 0 = 4 columnas 2 renglones (4x2)
            //flagComb 4 = 4 columnas 3 renglones (4x3) (normal)

            //obtenemos escalares
            if (flagComb == 3)
            {

                for (int x = 0; x < 3; x++)
                {
                    escalares[x] = matriz[x][2];
                }

            }

            else if(flagComb == 1)
            {
                for (int x = 0; x < 2; x++)
                {
                    escalares[x] = matriz[x][2];
                }
            }

            else if(flagComb == 2)
            {
                for (int x = 0; x < 2; x++)
                {
                    escalares[x] = matriz[x][2];
                }
            }
            else if(flagComb == 5)
            {
                for (int x = 0; x < 2; x++)
                {
                    escalares[x] = matriz[x][2];
                }
            }

            else
            {
                for (int x = 0; x < 3; x++)
                {
                    escalares[x] = matriz[x][3];
                }
            }
            //fin obtenemos escalares

            String comprobar = " ";
            int cont = 0;

            //flagComb 1 = 3 columnas 2 renglones (2x2)
            //flagComb 5 = 2 columnas 2 renglones (2x2) (sin a y b)
            //flagComb 2 = 2 columnas 3 renglones (2x3)
            //flagComb 3 = 3 columnas 3 renglones (3x3)
            //flagComb 0 = 4 columnas 2 renglones (4x2)
            //flagComb 4 = 4 columnas 3 renglones (4x3) (normal)
            switch (flagComb){
            case(2):
                float resula1= Math.round(auxiliar2[0][0]*escalares[0] + auxiliar2[0][1]*escalares[1]);
                if(resula1==auxiliar2[0][2]) {
                    cont++;
                }

                float resula2= Math.round(auxiliar2[1][0]*escalares[0] + auxiliar2[1][1]*escalares[1]);
                if(resula2==auxiliar2[1][2]) {
                    cont++;
                }

                if (cont==2){
                    comprobar = "Sí es ";
                }else{
                    comprobar = "No es ";
                }
                break;

            case(3):
                if(!Float.isNaN(escalares[2]) || Float.isInfinite(escalares[2])){

                    //Toast.makeText(this, escalares[2]+"", Toast.LENGTH_SHORT).show();
                    float resul1= Math.round(auxiliar2[0][0]*escalares[0] + auxiliar2[0][1]*escalares[1]);
                    if(resul1==auxiliar2[0][2]) {
                        cont++;
                    }

                    float resul2= Math.round(auxiliar2[1][0]*escalares[0] + auxiliar2[1][1]*escalares[1]);
                    if(resul2==auxiliar2[1][2]) {
                        cont++;
                    }

                    float resul3= Math.round(auxiliar2[2][0]*escalares[0] + auxiliar2[2][1]*escalares[1]);
                    if(resul3==auxiliar2[2][2]) {
                        cont++;
                    }
                    //Toast.makeText(this, " val 1 "+escalares[0] +" val 2 "+escalares[1], Toast.LENGTH_SHORT).show();

                    if (cont==3){
                        comprobar = "Sí es ";
                    }else{
                        comprobar = "No es ";
                    }
                    break;
                }

                 else{
                    //Toast.makeText(this, escalares[2]+" else", Toast.LENGTH_SHORT).show();
                    float resul1= Math.round(auxiliar2[0][0]*escalares[0] + auxiliar2[0][1]*escalares[1]);
                    if(resul1==auxiliar2[0][2]) {
                        cont++;
                    }

                    float resul2= Math.round(auxiliar2[1][0]*escalares[0] + auxiliar2[1][1]*escalares[1]);
                    if(resul2==auxiliar2[1][2]) {
                        cont++;
                    }


                    if (cont==2){
                        comprobar = "Sí es ";
                    }else{
                        comprobar = "No es ";
                    }
                    break;
                }



            case(4):

                float resulb1= Math.round(auxiliar2[0][0]*escalares[0] + auxiliar2[0][1]*escalares[1]+ auxiliar2[0][2]*escalares[2]);
                if(resulb1==auxiliar2[0][3]) {
                    cont++;
                }

                float resulb2= Math.round(auxiliar2[1][0]*escalares[0] + auxiliar2[1][1]*escalares[1]+ auxiliar2[1][2]*escalares[2]);
                if(resulb2==auxiliar2[1][3]) {
                    cont++;
                }

                float resulb3= Math.round(auxiliar2[2][0]*escalares[0] + auxiliar2[2][1]*escalares[1]+ auxiliar2[2][2]*escalares[2]);
                if(resulb3==auxiliar2[2][3]) {
                    cont++;
                }

                if (cont==3){
                    comprobar = "Sí es ";
                }else{
                    comprobar = "No es ";
                }
                break;

            case(0):
                break;

                case (5):
                    float resul26= Math.round(auxiliar2[0][0]*escalares[0] + auxiliar2[0][1]*escalares[1]);
                    if(resul26==auxiliar2[0][2]) {
                        cont++;
                    }

                    float resul27= Math.round(auxiliar2[1][0]*escalares[0] + auxiliar2[1][1]*escalares[1]);
                    if(resul27==auxiliar2[1][2]) {
                        cont++;
                    }


                    if (cont==2 && seguirDospordos==1){
                        comprobar = "Sí es ";
                    }else{
                        comprobar = "No es ";
                    }
                    break;

            case (1):
                float resul21= Math.round(auxiliar2[0][0]*escalares[0] + auxiliar2[0][1]*escalares[1]);
                if(resul21==auxiliar2[0][2]) {
                    cont++;
                }

                float resul22= Math.round(auxiliar2[1][0]*escalares[0] + auxiliar2[1][1]*escalares[1]);
                if(resul22==auxiliar2[1][2]) {
                    cont++;
                }


                if (cont==2 && seguirDospordos==1){
                    comprobar = "Sí es ";
                }else{
                    comprobar = "No es ";
                }
                break;


        }
        if(flagComb != 3 ){
            if( cuadrada && determinanteCalc !=0){
                comprobar = "Sí es ";
            }else{
                comprobar = "No es ";
            }
        }

        return comprobar;
    }

        public void crearAlerta(){

            final Dialog dlgMiCuadroDialogo = new Dialog(this);
            dlgMiCuadroDialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //asignamos el layout
            dlgMiCuadroDialogo.setContentView(R.layout.alerta);
            //vincular widgets
            TextView titulo;
            EditText datos;
            Button ok;

            ok = dlgMiCuadroDialogo.findViewById(R.id.btnOk);


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlgMiCuadroDialogo.cancel();
                }
            });
            dlgMiCuadroDialogo.show();
        }
}


