package com.gmendezm.ticoroutes;

import com.gmendezm.R;
import com.gmendezm.ticoroutes.model.Database;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdministrarUsuarios extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_usuarios);
		cargarListaUsuarios();
	}
	
	
	private void cargarListaUsuarios(){
		String[] usuarios = Utilerias.db.getListaUsernames();
		
		if(usuarios.length > 0){
			//ArrayAdapter<String> adaptadorUsuarios = new ArrayAdapter<String>(this, R.id.listViewUsuariosAdministrar,	usuarios);
			
			//ListView listViewUsuariosAdministrar = (ListView) findViewById(R.id.listViewUsuariosAdministrar);
			//listViewUsuariosAdministrar.setAdapter(adaptadorUsuarios);
		}
		
		
	}
}
