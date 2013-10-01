package com.gmendezm.ticoroutes;

import com.gmendezm.ticoroutes.model.Usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

public class ModuloUsuarios{

	
	
	public ModuloUsuarios() {
		
	}
	
	
	public int getCantidadUsuarios(){
		return Usuario.cantidadUsuarios();
	}
	
	
	public void onClickInsertarUsuario(String username, String password){
		boolean resultadoInsercionUsuario = Usuario.insertarUsuario(username, password);
		
		if(resultadoInsercionUsuario){
			mostrarMensaje("Inserción Perfecta", username+" "+password);
		}else{
			mostrarMensaje("Error de Inserción", password);
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public AlertDialog mostrarMensaje(String titulo, String mensaje) {

		final AlertDialog alertDialog = new AlertDialog.Builder(Utilerias.main).create();
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
	
	public View findViewById(int id){
		return Utilerias.main.findViewById(id);
	}
	
	/*
	private void configurarListeners(){
		final EditText usernameEditText = (EditText) findViewById(R.id.username);
		final EditText passwordEditText = (EditText) findViewById(R.id.password);
		Button botonInsertarUsuario = (Button) findViewById(R.id.botonInsertarUsuario);
		
		OnClickListener botonInsertarUsuarioListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String username = usernameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				onClickInsertarUsuario(username, password);
			}
		};
		
		botonInsertarUsuario.setOnClickListener(botonInsertarUsuarioListener);
	}
	*/
	

}
