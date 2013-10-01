package com.gmendezm.ticoroutes;


import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gmendezm.R;


public class Portada extends Activity {
	
	private Utilerias u;
	SoundPool soundPool;
	int miSonidoId = -1;
	private ModuloUsuarios usuarios;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portada);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // Requiere API 9
		StrictMode.setThreadPolicy(policy);
		
		// Oculta la barra de título
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}
		
		Utilerias.main = this;
		u = new Utilerias();
		usuarios = new ModuloUsuarios();
		
		cargarSonido("sonido.ogg");
		agregarListenersPortada();
		
		if(usuarios.getCantidadUsuarios() == 0){
			u.abrirActivity("com.gmendezm.ticoroutes.RegistrarAdministrador",this);
		}else{
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.portada, menu);
		return true;
	}
	
	public void agregarListenersPortada(){

		ImageButton boton_entrar_modo_visitante = (ImageButton) findViewById(R.id.boton_entrar_modo_visitante);
		ImageButton boton_abrir_formulario_login = (ImageButton) findViewById(R.id.boton_abrir_formulario_login);
		ImageButton boton_abrir_formulario_registro = (ImageButton) findViewById(R.id.boton_abrir_formulario_registro);
		ImageButton boton_abrir_sitio_web = (ImageButton) findViewById(R.id.boton_abrir_sitio_web);
		ImageButton boton_salir = (ImageButton) findViewById(R.id.boton_salir);
		
		final Activity esta = this;
	
		
		OnClickListener boton_entrar_modo_visitante_listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				reproducirSonido("sonido.ogg");
				Utilerias.activityQueHayQueCerrar = esta;
				u.abrirActivity("com.gmendezm.ticoroutes.Principal",esta);
			}
		};
		
		OnClickListener boton_abrir_formulario_login_listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reproducirSonido("sonido.ogg");
				Utilerias.activityQueHayQueCerrar = esta;
				u.abrirActivity("com.gmendezm.ticoroutes.Login",esta);
			}
		};
		
		OnClickListener boton_abrir_formulario_registro_listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reproducirSonido("sonido.ogg");
				Utilerias.activityQueHayQueCerrar = esta;
				u.abrirActivity("com.gmendezm.ticoroutes.Registro",esta);
			}
		};
		
		OnClickListener boton_abrir_sitio_web_listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reproducirSonido("sonido.ogg");
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ticoroutes"));
				startActivity(browserIntent);
				//u.abrirActivity("com.gmendezm.ticoroutes.Login",esta);
			}
		};
		
		OnClickListener boton_salir_listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				reproducirSonido("sonido.ogg");	
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
					
			}
		};
		
		
		boton_entrar_modo_visitante.setOnClickListener(boton_entrar_modo_visitante_listener);
		boton_abrir_formulario_login.setOnClickListener(boton_abrir_formulario_login_listener);
		boton_abrir_formulario_registro.setOnClickListener(boton_abrir_formulario_registro_listener);
		boton_abrir_sitio_web.setOnClickListener(boton_abrir_sitio_web_listener);
		boton_salir.setOnClickListener(boton_salir_listener);
		
	}
	
	
	
	// Debe cargarse con el constructor
	public void cargarSonido(String ruta){
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0); 
		
			// El primer parametro es la cantidad de sonidos concurrentes
			// El segundo parámetro es el tipo de sonido 
		try{
			AssetManager assetManager = getAssets();
			AssetFileDescriptor descriptor = assetManager.openFd(ruta);
			miSonidoId = soundPool.load(descriptor, 1);
		}
		catch(IOException e){
			//textView.setText("No se ha podido cargar el efecto de sonido desde asset: " + e.getMessage());
			Toast.makeText(this, "No se pudo cargar el sonido", 1);
		}
		
	}

	public void reproducirSonido(String ruta){
				
			if(miSonidoId != -1){
				soundPool.play(miSonidoId, 1, 1, 0, 0, 1);
			}
	
		
		
	}

	

	



}
