package com.gmendezm.ticoroutes;

import com.gmendezm.R;
import com.gmendezm.ticoroutes.model.Usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistrarAdministrador extends Activity{
	
	@Override
	public void onBackPressed() {
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar_administrador);
		
		final EditText usuarioEditText = (EditText) findViewById(R.id.usuarioEditTextRegAdmin);
		final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditTextRegAdmin);
		final EditText repasswordEditText = (EditText) findViewById(R.id.repasswordEditTextRegAdmin);
		Button botonRegistrarAdministrador = (Button) findViewById(R.id.botonRegistrarAdministrador);
		
		usuarioEditText.setBackgroundColor(getResources().getColor(android.R.color.primary_text_dark_nodisable));
		passwordEditText.setBackgroundColor(getResources().getColor(android.R.color.primary_text_dark_nodisable));
		repasswordEditText.setBackgroundColor(getResources().getColor(android.R.color.primary_text_dark_nodisable));
		
	
		OnClickListener registrarAdministradorListener = new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				 String username = usuarioEditText.getText().toString();
				 String password = passwordEditText.getText().toString();
				 String repassword = repasswordEditText.getText().toString();
				 
				if(username.equals("")){
					mostrarMensaje("Debe llenar todos los campos", "Llena el campo del nombre de usuario");
				}
				else if(password.equals("")){
					mostrarMensaje("Debe llenar todos los campos", "Llena el campo de la contraseña");
				}
				else if(username.equals("")){
					mostrarMensaje("Debe llenar todos los campos", "Llene el campo de comprobación de contraseña");
				}
				else if(!password.equals(repassword)){
					mostrarMensaje("Corrije tu contraseña", "No coincide la contraseña, intenta reescribiéndola.");
				}else{
					Usuario.insertarAdministrador(username, repassword);
					mostrarMensajeAdministradorRegistrado("Bienvenido a TicoRoutes", "Se ha registrado correctamente");
				}
			}
		};
		
		botonRegistrarAdministrador.setOnClickListener(registrarAdministradorListener);
	}

	public void mostrarMensajeAdministradorRegistrado(String titulo, String mensaje){
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensaje);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int which) {
				// puedes agregar aquí funciones
				alertDialog.cancel();
				finish();
			}
		});
		 alertDialog.show();
		
	}
	
	@SuppressWarnings("deprecation")
	public AlertDialog mostrarMensaje(String titulo, String mensaje) {

		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
	
}

