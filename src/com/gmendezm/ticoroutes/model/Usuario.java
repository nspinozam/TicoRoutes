package com.gmendezm.ticoroutes.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmendezm.ticoroutes.Utilerias;

// Clase para la tabla Usuarios
public class Usuario {
	private int id_usuario;
	private String username;
	private String password;
	private String email; // default NULL
	private int tipo; // 0 administrador 1 moderador 2 miembro, default 2
	private int estado; // 0 Normal 1 banneado, default 0
	private String fecha_registro;
	public boolean creado = false;
	
	public Usuario(String u, String p) {
		getInfoUsuario(u, p);
	}
	
	// Setters
	public void setEmail(String email) {
			this.email = email;
		}
	
	public void setEstado(int estado) {
			this.estado = estado;
		}
	
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	//Getters
	public String getEmail() {
		return email;
	}public int getEstado() {
		return estado;
	}public String getFecha_registro() {
		return fecha_registro;
	}public int getId_usuario() {
		return id_usuario;
	}public String getPassword() {
		return password;
	}public int getTipo() {
		return tipo;
	}public String getUsername() {
		return username;
	}
	
	
	public static boolean insertarUsuario(String username, String password){
		String listaPares[][]  = {{"username",username}, {"password",password}};
		String cadenaDeInsercion = "usuarios/insertar_usuario";
		return Database.insercion(listaPares, cadenaDeInsercion);
	}
	
	public static boolean insertarAdministrador(String username, String password){
		String listaPares[][]  = {{"username",username}, {"password",password}};
		String cadenaDeInsercion = "usuarios/insertar_usuario_administrador";
		return Database.insercion(listaPares, cadenaDeInsercion);
	}
	
	public static boolean registrarUsuario(String username, String password, String email){
		String listaPares[][]  = {{"username",username}, {"password",password}, {"email",email}};
		String cadenaDeInsercion = "usuarios/registrar_usuario";
		return Database.insercion(listaPares, cadenaDeInsercion);
	}
	
	public static int cantidadUsuarios(){
		int cantidad = Database.cantidadFilas("usuarios/contar_usuarios");
		return cantidad;
	}
	
	public Usuario getInfoUsuario(String usernameRecibido, String passwordRecibido) {

		String result = "";
		// JSONArray array = null;

		try { // Lo requiere el BufferReader
				// Preparamos para enviar las variables al archivo
				// application/controllers/usuarios.php
			List<NameValuePair> pair = new ArrayList<NameValuePair>();
			pair.add(new BasicNameValuePair("username", usernameRecibido));
			pair.add(new BasicNameValuePair("password", passwordRecibido));

			// Le pasamos la lista a httpPost para convertirla en un formato web.
			String url = Utilerias.servidor + "usuarios/existe_usuario_con_este_password";
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(pair));

			// Enviamos los datos y obtenemos una respuesta del servicio web
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpPost, Utilerias.db.HTTP_CONTEXT);

			// Revisamos la respuesta que nos devolvió el servicio web, leyendo el archivo
			String line = "";
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			while ((line = rd.readLine()) != null) {
				result += line;
			}

			JSONObject jsonobject = new JSONObject(result);
			String salida = jsonobject.getString("resultado");
			System.out.println(salida);

			if (salida.equals("true")) {
				// Están nombradas así para asimilarse a la tabla, por esto no utilizan camelCase
				int id_usuario = jsonobject.getInt("id_usuario");
				String username = jsonobject.getString("username");
				String password = jsonobject.getString("password");
				String email = jsonobject.getString("email");
				String fecha_registro = jsonobject.getString("fecha_registro");
				int tipo = jsonobject.getInt("tipo");
				int estado = jsonobject.getInt("estado");

				// Retornamos los valores.
				//Usuario u = new Usuario();
				this.setId_usuario(id_usuario);
				this.setUsername(username);
				this.setPassword(password);
				this.setEmail(email);
				this.setFecha_registro(fecha_registro);
				this.setTipo(tipo);
				this.setEstado(estado);
				this.creado = true;
								
				//return u;
				return this;

			} else {

				return null;
			}

		} catch (Exception e) {
			//System.out.println(e.getMessage());
			return null;
		}
	}

	public static boolean existeUsuario(String usernameRecibido) {

		String result = "";
		// JSONArray array = null;

		try { // Lo requiere el BufferReader
				// Preparamos para enviar las variables al archivo
				// application/controllers/usuarios.php
			List<NameValuePair> pair = new ArrayList<NameValuePair>();
			pair.add(new BasicNameValuePair("username", usernameRecibido));

			// Le pasamos la lista a httpPost para convertirla en un formato web.
			String url = Utilerias.servidor + "usuarios/existe_usuario_con_este_username";
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(pair));

			// Enviamos los datos y obtenemos una respuesta del servicio web
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpPost, Utilerias.db.HTTP_CONTEXT);

			// Revisamos la respuesta que nos devolvió el servicio web, leyendo el archivo
			String line = "";
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			while ((line = rd.readLine()) != null) {
				result += line;
			}

			JSONObject jsonobject = new JSONObject(result);
			String salida = jsonobject.getString("resultado");
			System.out.println(salida);

			if (salida.equals("true")) {
				return true;
								
				//return u;

			} else {

				return false;
			}

		} catch (Exception e) {
			//System.out.println(e.getMessage());
			//return null;
			return false;
		}
	}

	


	


}
