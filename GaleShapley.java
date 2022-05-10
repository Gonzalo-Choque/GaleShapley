/*	Ejercicio1: Algoritmo de Gale Shapley
*	Autor: Gonzalo Joel, Choque Dongo
*/

package Lab_02;

public class GaleShapley {
	public static void main(String args[]) {
		String h[]= {"Victor","William","Xavier","Yancey","Zeus"};//Victor es [0],William es [1],Xavier es [2],Yancey es [3],Zeus es [4]
		int prefH[][]= {{1,0,3,4,2},{3,1,0,2,4},{1,4,2,3,0},{0,3,2,1,4},{1,3,0,4,2}};
		
		String m[]= {"Amy","Bertha","Claire","Diane","Erika"};//Amy es [0],Bertha es [1],Claire es [2],Diane es [3],Erika es [4]
		int prefM[][]= {{4,0,1,3,2},{2,1,3,0,4},{1,2,3,4,0},{0,4,3,2,1},{3,1,4,2,0}};
		
		int empPHombres[][] = galeShapley(prefH,prefM);//los hombres proponen y las mujeres aceptan
		int empPMujeres[][] = galeShapley(prefM,prefH);//las mujeres proponen y los hombres aceptan
		
		System.out.println("Proponen los hombres:");
		for(int i = 0; i <h.length;i++) {//emparejamiento cuando los hombres proponen
			System.out.println("["+h[empPHombres[i][0]]+"; "+m[empPHombres[i][1]]+"]");
		}
		System.out.println("Proponen las mujeres:");
		for(int i = 0; i <h.length;i++) {//emparejamiento cuando las mujeres proponen
			System.out.println("["+m[empPMujeres[i][0]]+"; "+h[empPMujeres[i][1]]+"]");
		}
		///////////////
		String hC[]= {"Yancey","William","Xavier","Victor","Zeus"};//Victor es [3],Yancey es [0]
		int prefHCambiado[][]= {{0,3,2,1,4},{3,1,0,2,4},{1,4,2,3,0},{1,0,3,4,2},{1,3,0,4,2}};

		int prefMCambiado[][]= {{4,3,1,0,2},{2,1,0,3,4},{1,2,0,4,3},{3,4,0,2,1},{0,1,4,2,3}};
		
		
		int empPCambiado[][] = galeShapley(prefHCambiado,prefMCambiado);
		System.out.println("Cambiando el orden de los hombres:");
		for(int i = 0; i <h.length;i++) {//emparejamiento cuando los hombres proponen haciendo un cambio
			System.out.println("["+hC[empPCambiado[i][0]]+"; "+m[empPCambiado[i][1]]+"]");
		}
	}
	public static int[][] galeShapley(int[][] prefH, int[][] prefM){
		int parejas = prefH.length;

		boolean HconPareja[] = new boolean[parejas];//si hn tiene pareja
		boolean MconPareja[] = new boolean[parejas];//si mn tiene pareja
		int j[] = new int[parejas];//matris de intentos para los 4 hombres
		
		int resultadoH[][] = new int [parejas][2];//hombre con su pareja
		int resultadoM[][] = new int [parejas][2];//mujer con su pareja
		
		for(int i = 0; i<parejas;i++) {//rellenando porque al inicio nadie tiene pareja
			HconPareja[i]=false;
			MconPareja[i]=false;
			j[i]=0;
		}
		
		int h=0;//hombres con pareja
		int hn,mn;//un hombre, una mujer
		
		int i = 0;//numero de hombre
		//int i = parejas-1;//[escogiendo de atras pa adelante]
		while(h<parejas) {//bucle de gale Shapley
			while(HconPareja[i]!=false) {//Buscando un hombre sin pareja
				i++;
				//i--;[escogiendo de atras pa adelante]
			}
			hn = i;//un hombre sin pareja
			mn=prefH[hn][j[hn]];//mujer de la lista de preferencias
			if(MconPareja[mn]==false) {//si la mujer no tiene pareja
				resultadoH[i][0]=hn;
				resultadoH[i][1]=mn;
				HconPareja[hn]=true;
				resultadoM[mn][0]=mn;
				resultadoM[mn][1]=hn;
				MconPareja[mn]=true;
				h++;//se creo una nueva pareja
				j[hn]+=1;//se aumenta el intento en su lista de preferencias
				i=0;
				//i=parejas-1;//[escogiendo de atras pa adelante]
				
			}
			else if(mPrefiereAH(mn,hn,prefM,resultadoM)==true) {//si la mujer prefiere al hombre antes que a su pareja
				int hSinPareja = resultadoM[mn][1];
				resultadoH[hn][0]=hn;
				resultadoH[hn][1]=mn;
				HconPareja[hn]=true;
				resultadoM[mn][0]=mn;
				resultadoM[mn][1]=hn;
				MconPareja[mn]=true;
				HconPareja[hSinPareja]=false;//dejando sin pareja a la antigua pareja de mn
				j[hn]+=1;//se aumenta el intento en su lista de preferencias
				i=0;
				//i=parejas-1;//[escogiendo de atras pa adelante]
			}
			else {//la mujer rechaza al hombre
				j[hn]+=1;//se aumenta el intento en su lista de preferencias
			}
		}
		return resultadoH;
	}
	private static boolean mPrefiereAH(int mn, int hn, int[][] prefM, int[][]resultadoM) {
		int i = 0;
		while(hn-prefM[mn][i]!=0) {//mientras que el hombre sea diferente a el i de la lista de preferencia de la mujer
			if(resultadoM[mn][1]==prefM[mn][i]) {//si la pareja actual esta antes que el hombre retorna falso
				return false;
			}
			i++;
		}
		return true;//retorna verdadero si no se encontro a la pareja antes que el hombre
	}
}
