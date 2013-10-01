package com.gmendezm.ticoroutes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gmendezm.ticoroutes.Principal;
import com.gmendezm.ticoroutes.model.Database;
import com.gmendezm.ticoroutes.model.Usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Utilerias {
	
	final public static String servidor = "http://www.gmendezm.com/TicoRoutes/" ;
	public static Database db;
	public static Activity main;
	public static Usuario usuarioLogueado;
	public static Activity activityQueHayQueCerrar;
	
	public Utilerias() {
		// TODO Auto-generated constructor stub
		db = new Database();
	}


	public static void abrirActivity(String nombreActivity, Activity esta){
		
		Class<?> laClase;
		try {
			laClase = Class.forName(nombreActivity);
			Intent intent = new Intent(esta, laClase);
			esta.startActivity(intent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	public void cambiarTexto(int idControl, String nuevoTexto, String tipo){
		if(tipo.equals("TextView")){
			TextView x = (TextView) main.findViewById(idControl);
			x.setText(nuevoTexto);
		}else if(tipo.equals("Button")){
			Button x = (Button) main.findViewById(idControl);
			x.setText(nuevoTexto);
		}
		
		
	}
	
	@SuppressWarnings("deprecation")
	public static AlertDialog mostrarMensaje(String titulo, String mensaje, Activity padre) {

		final AlertDialog alertDialog = new AlertDialog.Builder(padre).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensaje);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int which) {
				// puedes agregar aquí funciones
				alertDialog.cancel();
			}
		});
		 alertDialog.show();
		return alertDialog;
	}
	
	public static void mensajeToast(String mensaje, Activity padre){
		Toast.makeText(padre, mensaje, Toast.LENGTH_LONG).show();
	}
	
	
	 public static boolean esUnEmail(String correo) {
		 Pattern pat = null;
		 Matcher mat = null;
		 pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
		 mat = pat.matcher(correo);
		 if (mat.find()) {
			 System.out.println("[" + mat.group() + "]");
			 return true;
		 }else{
			 return false;
		 }
	 }
	 
	 
}
