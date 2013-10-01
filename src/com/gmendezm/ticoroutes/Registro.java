package com.gmendezm.ticoroutes;

import com.gmendezm.R;
import com.gmendezm.R.id;
import com.gmendezm.ticoroutes.model.Usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends Activity{


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.registro);
		
		configurarRegistroUsuarios();
	}
	
	private void configurarRegistroUsuarios(){
		
		final Activity esta = this;
		
		final EditText editTextUsernameRegistro = (EditText) findViewById(R.id.editTextUsernameRegistro);
		final EditText editTextEmailRegistro = (EditText) findViewById(R.id.editTextEmailRegistro);
		final EditText editTextPasswordRegistro = (EditText) findViewById(R.id.editTextPasswordRegistro);
		final EditText editTextRepasswordRegistro = (EditText) findViewById(R.id.editTextRepasswordRegistro);
		
		Button botonRegistro = (Button) findViewById(R.id.botonRegistro);
		
		OnClickListener botonRegistroListener = new OnClickListener(){


			
			@Override
			public void onClick(View v) {
				String username = editTextUsernameRegistro.getText().toString();
				String password = editTextPasswordRegistro.getText().toString();
				String repassword = editTextRepasswordRegistro.getText().toString();
				String email = editTextEmailRegistro.getText().toString();
				
				if(username.equals("")){
					Utilerias.mostrarMensaje("Llena todos los datos", "Debes ingresar algun nombre de usuario", esta);
				}else if(password.equals("")){
					Utilerias.mostrarMensaje("Llena todos los datos", "Debes ingresar tu password", esta);
				}else if(repassword.equals("")){
					Utilerias.mostrarMensaje("Llena todos los datos", "Debes confirmar tu password", esta);
				} else if(email.equals("")){
					Utilerias.mostrarMensaje("Llena todos los datos", "Debes ingresar algun correo electrónico", esta);
				}else if(!password.equals(repassword)){
					Utilerias.mostrarMensaje("No coincide el password", "Debes confirmar correctamente el password", esta);
				}/*else if(!Utilerias.esUnEmail(email)){
					Utilerias.mostrarMensaje("Error de formato", "El formato de tu email es incorrecto", esta);
				}*/else if(Usuario.existeUsuario(username)){
					Utilerias.mostrarMensaje("Error de coincidencia", "Este nombre de usuario ya fue registrado", esta);
				}
				
				// else if ya existe este nombre de usuario
				
				else { // Se registra al usuario
					
					Usuario.registrarUsuario(username, password, email);
					
					Usuario u = new Usuario(username, password);
					Utilerias.usuarioLogueado = u;
					mostrarMensajeRegistroExitoso("Usuario registro", "Te haz registrado correctamente en TicoRoutes");
					
					
					
				}
				
			}};
			
			botonRegistro.setOnClickListener(botonRegistroListener);
	}
	
	@SuppressWarnings("deprecation")
	private AlertDialog mostrarMensajeRegistroExitoso(String titulo, String mensaje) {
		
		final Activity esta = this;
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensaje);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int which) {
				// puedes agregar aquí funciones
				alertDialog.cancel();
				Utilerias.abrirActivity("com.gmendezm.ticoroutes.Principal",esta);
				finish();
			}
		});
		 alertDialog.show();
		return alertDialog;
	}
	
}
