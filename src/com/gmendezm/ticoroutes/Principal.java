package com.gmendezm.ticoroutes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmendezm.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Principal extends android.support.v4.app.FragmentActivity implements OnItemSelectedListener{

	public static GoogleMap mapa;
	public static Location miUbicacion;
	private Toast mensaje;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.principal);
		cambiarLayout(R.layout.sub_mapa);
		
		
		// Oculta la barra de título
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}
		
		Utilerias.mensajeToast("Bienvenido. Seleccione una ubicación en el mapa,\n o utilice su propia ubicación.", this);
		
		
		configurarMapa();
		configurarSpinner();
		configurarGUI();
		
		configurarSegunTipoDeUsuario();
		
	}
	
	private void configurarSegunTipoDeUsuario(){
		configurarBotonDeAdministradorSegunTipoDeUsuario();
	}
	
	private void configurarBotonDeAdministradorSegunTipoDeUsuario(){
		Button botonAdministrador = (Button) findViewById(R.id.botonMenuAdministrador);
		if(Utilerias.usuarioLogueado != null){
			if(Utilerias.usuarioLogueado.getTipo() == 0){ // 0 admin 1 mod 2 usuario
				configurarMenuAdministrador();
				
			}else{
				botonAdministrador.setVisibility(View.INVISIBLE);
			}
		} else{
			botonAdministrador.setVisibility(View.INVISIBLE);
		}
	}
	
	private void configurarGUI(){
		TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);
		
		if(Utilerias.usuarioLogueado != null){
			textViewUsername.setText("Bienvenido " + Utilerias.usuarioLogueado.getUsername().toString());
		}else{
			textViewUsername.setText("Usuario Visitante");
		}
	}
		
	private void configurarSpinner(){
		llenarSpinner();
		Spinner spinnerMenuRapido  = (Spinner) findViewById(R.id.menuRapido);
		AdapterView.OnItemSelectedListener spinnerMenuRapidoListener = new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				cambiarLayoutSegunMenuRapido();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// No implementado
			}
		};
		spinnerMenuRapido.setOnItemSelectedListener(spinnerMenuRapidoListener);
	}

	
	
	private void cambiarLayoutSegunMenuRapido(){
		Spinner spinnerMenuRapido  = (Spinner) findViewById(R.id.menuRapido);
		String opcionSeleccionada = spinnerMenuRapido.getSelectedItem().toString();
		
		if (opcionSeleccionada.equals("Vista: Mapa")) {
			mostrarLayoutMapa();
		}
		else if (opcionSeleccionada.equals("Vista: Actualizaciones")) {
			ocultarLayoutMapa();
			insertarLayout(R.layout.sub_actualizaciones);
		}
		else if (opcionSeleccionada.equals("Vista: Eventos")) {
			ocultarLayoutMapa();
			insertarLayout(R.layout.sub_eventos);
		}
		else if (opcionSeleccionada.equals("Vista: Albumes")) {
			ocultarLayoutMapa();
			insertarLayout(R.layout.sub_albumes);
		}
		else if (opcionSeleccionada.equals("Vista: Comentarios")) {
			ocultarLayoutMapa();
			insertarLayout(R.layout.sub_comentarios);
		}
		else if (opcionSeleccionada.equals("Vista: Categorias")) {
			ocultarLayoutMapa();
			insertarLayout(R.layout.sub_categorias);
		}

	}
	
	private void mostrarLayoutMapa(){
		LinearLayout layoutCambiante = (LinearLayout) findViewById(R.id.layoutCambiante);
		View view = layoutCambiante.getChildAt(0);
		view.setVisibility(View.VISIBLE);
	}
	
	private void ocultarLayoutMapa(){
		LinearLayout layoutCambiante = (LinearLayout) findViewById(R.id.layoutCambiante);
		View view = layoutCambiante.getChildAt(0);
		view.setVisibility(View.GONE);
	}
	
	private void insertarLayout(int idLayout){
		ViewGroup layoutCambiante = (ViewGroup)  findViewById(R.id.layoutCambiante);
		View layoutInsertable = getLayoutInflater().inflate(idLayout, layoutCambiante, false);
		layoutCambiante.addView(layoutInsertable, 1); // En la posición 1, pues en la 0 está el mapa
		
	}	
	
	
	private void eliminarLayoutsExceptoElMapa(){
		LinearLayout layoutCambiante = (LinearLayout) findViewById(R.id.layoutCambiante);
		for ( int i = 1; i < layoutCambiante.getChildCount();  i++ ){ // Inicia en 1 para no borrar el mapa, que es el cero
		    View view = layoutCambiante.getChildAt(i);
		    layoutCambiante.removeView(view);
		   // view.setVisibility(View.GONE); // Or whatever you want to do with the view.
		}
	}
	
	
	private void cambiarLayout(int idCategoriaNueva){
		
		if(idCategoriaNueva == R.id.mapaGrafico){
		 
		    ViewGroup parent = (ViewGroup)  findViewById(R.id.layoutCambiante);
		    
		    parent.removeAllViewsInLayout();
		    //eliminarLayoutsExceptoElMapa();
		    View categorias = getLayoutInflater().inflate(idCategoriaNueva, parent, false);
		    parent.addView(categorias, 0); // En la posición 1, pues en la 0 está el mapa
		}else{
ViewGroup parent = (ViewGroup)  findViewById(R.id.layoutCambiante);
		    
		    //parent.removeAllViewsInLayout();
		    eliminarLayoutsExceptoElMapa();
		    View categorias = getLayoutInflater().inflate(idCategoriaNueva, parent, false);// aca se cae
		    parent.addView(categorias, 0); // En la posición 1, pues en la 0 está el mapa
		}
	}
	
	private void llenarSpinner(){
		// Creamos un arreglo con la lista de tipos de mapas que pueda recibir el spinner
		String[] opciones = new String[] { "Vista: Mapa", "Vista: Actualizaciones", "Vista: Eventos",	"Vista: Albumes", "Vista: Categorias", "Vista: Comentarios" };
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,	opciones);

		Spinner spinnerOpciones = (Spinner) findViewById(R.id.menuRapido);
		spinnerOpciones.setAdapter(adaptador);
		//spinnerOpciones.setOnItemSelectedListener(onitem_spiner);
	}
	
	private void configurarPerspectiva(){
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.362204106460675, -84.50889587402344), 35));

		mapa.animateCamera(CameraUpdateFactory.zoomIn());

		// Establece el centro del mapa a TEC SSC
		CameraPosition posicionDeCamara = new CameraPosition.Builder().target(new LatLng(10.362204106460675, -84.50889587402344)) 
				.zoom(17) // Establece el zoom
				.bearing(90) // Orientación de la cámara al este
				.tilt(30) // Sets the tilt of the camera to 30 degrees
				.build(); // Creates a CameraPosition from the builder
		
		mapa.animateCamera(CameraUpdateFactory.newCameraPosition(posicionDeCamara));
		
		mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
	}

	public void configurarMenuAdministrador(){
		Button botonMostrarMenuAdministracion = (Button) findViewById(R.id.botonMenuAdministrador);
		
		OnClickListener botonMenuAdministracionListener =  new OnClickListener(){

			@Override
			public void onClick(View v) {
				mostrarMenuAdministrador(v);
			} 
			
		};

		botonMostrarMenuAdministracion.setOnClickListener(botonMenuAdministracionListener);
			
	}
	
	private void mostrarMenuAdministrador(View v){

		PopupMenu popupMenuAdministrador = new PopupMenu(this, v);
		popupMenuAdministrador.inflate(R.menu.menu_administrador);
		final Activity esta = this;
		
		// This activity implements OnMenuItemClickListener
		popupMenuAdministrador.setOnMenuItemClickListener(new OnMenuItemClickListener() {

		
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.item_administrar_usuarios:
					Utilerias.abrirActivity("com.gmendezm.ticoroutes.AdministrarUsuarios", esta);
					
					return true;
					
				case R.id.item_administrar_categorias:
					//reproducirSonido("sonido.ogg");
					Utilerias.abrirActivity("com.gmendezm.ticoroutes.AdministrarCategorias", esta);
					
					
					return true;

				case R.id.item_administrar_sitios:
					
					return true;

				case R.id.item_administrar_albumes_y_fotos:
					
					return true;
					
				case R.id.item_administrar_eventos:
					
					return true;
					
				case R.id.item_administrar_comentarios:
					
					return true;

				default:
					return false;
			}
			}
		});

	
		popupMenuAdministrador.show();
	}
	
	private void onClickLargoSobreMapa(LatLng posicion, final Activity esta){
		
		final Double latitud = posicion.latitude;
		final Double longitud = posicion.longitude;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(esta);

		// se obtiene el layout inflater
		LayoutInflater inflater = esta.getLayoutInflater();

		// Cargamos el layout personalizado para el dialogo 
		// Pasamos null como padre pues este layout irá en el dialogo
		View layout = inflater.inflate(R.layout.agregar_marca, null);

		final EditText editTextNombreMarca = (EditText) layout.findViewById(R.id.editTextNombreMarca);
		final EditText editTextSnippetMarca = (EditText) layout.findViewById(R.id.editTextSnippetMarca);

		// se sustituye la vista por la que se acaba de cargar
		builder.setView(layout);
		builder.setTitle("AtrŽvete a agregar una marca");
		// agregar los botones necesarios


		
		builder.setPositiveButton("Agregar Marca",	new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,	int which) {
				String nombreMarca = editTextNombreMarca.getText().toString();
				String snippetMarca = editTextSnippetMarca.getText().toString();
				if(!nombreMarca.equals("")){
					MarkerOptions marca = new MarkerOptions().position(new LatLng(latitud, longitud));
					marca.title(nombreMarca);
					marca.snippet(snippetMarca);
					marca.icon(BitmapDescriptorFactory.fromResource(R.drawable.marca));
					mapa.addMarker(marca);
					Utilerias.mensajeToast("Agregaste " + nombreMarca, esta );
				}
				else{
					Utilerias.mensajeToast("No fue agregada la marca", esta);
				}
			}
		});

		builder.setNegativeButton("No agregar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,	int which) {
				Utilerias.mensajeToast("No fue agregada la marca", esta);
			}
		});

		Dialog dialogo = builder.create();
		dialogo.show();
		
		//******************************************************************
		
		
	}
	
	private void configurarMapa(){
		mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaGrafico)).getMap();
		mapa.clear();
		mapa.setMyLocationEnabled(true);
		miUbicacion = mapa.getMyLocation();
		
		configurarPerspectiva();

		final Activity esta = this;
		
		OnMapLongClickListener clickLargoSobreElMapaListener = new OnMapLongClickListener(){
			@Override
			public void onMapLongClick(LatLng posicion) {
				onClickLargoSobreMapa(posicion, esta);				
			}
		};
		mapa.setOnMapLongClickListener(clickLargoSobreElMapaListener);
	}
	
	

	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
