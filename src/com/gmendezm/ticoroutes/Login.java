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
import android.widget.TextView;

public class Login extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.login);
		
		configurarInicioSesion();
	}
	
	private void configurarInicioSesion(){
		Button botonIniciarSesion = (Button) findViewById(R.id.botonIniciarSesion);
		final EditText editTextUsername = (EditText) findViewById(R.id.editTextUsernameInicioSesion);
		final EditText editTextPassword = (EditText) findViewById(R.id.editTextPasswordInicioSesion);
		
		final Activity esta = this;
		
		OnClickListener botonIniciarSesionListener = new OnClickListener(){

			@Override
			public void onClick(View v) {

				String username = editTextUsername.getText().toString();
				String password = editTextPassword.getText().toString();
				
				if(username.equals("") && password.equals("")){
					Utilerias.mostrarMensaje("Llena todos los datos", "No has escrito datos", esta);
				}
				else if(username.equals("")){
					Utilerias.mostrarMensaje("Llena todos los datos", "Te falta colocar el nombre de usuario", esta);
				}
				else if(password.equals("")){
					Utilerias.mostrarMensaje("Llena todos los datos", "Te falta colocar el password", esta);
				} else{
					Usuario u = new Usuario(username, password);
					if(u.creado){
											
						Utilerias.usuarioLogueado = u;
						finish();
						Utilerias.activityQueHayQueCerrar.finishFromChild(esta);
						Utilerias.abrirActivity("com.gmendezm.ticoroutes.Principal",esta);
						
					} else{
						Utilerias.mostrarMensaje("Error de Autenticación", "No se ha podido iniciar sesión con estos datos", esta);
					}
				}
			}
		};
		botonIniciarSesion.setOnClickListener(botonIniciarSesionListener);
	}
	

	
}
