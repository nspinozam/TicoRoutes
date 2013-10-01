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
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmendezm.ticoroutes.Utilerias;

public class Database {
	
	public static final BasicHttpContext HTTP_CONTEXT = new BasicHttpContext(); // Inserción de datos en la base de datos
	public static final BasicCookieStore COOKIE_STORE = new BasicCookieStore(); // Requiere una instrucción en el constructor
	
	public Database() {
		HTTP_CONTEXT.setAttribute(ClientContext.COOKIE_STORE, COOKIE_STORE);
	}
	
	
public static boolean insercion(String[][] listaPares, String cadenaDeInsercion){
	try {
		HttpClient httpclient = new DefaultHttpClient();

		String url = Utilerias.servidor + cadenaDeInsercion;

		HttpPost httppost = new HttpPost(url);

		List<NameValuePair> pair = new ArrayList<NameValuePair>();

		for(int i =0; i < listaPares.length; i++){
			String nombreCampo = listaPares[i][0].toString();
			String valorCampo = listaPares[i][1].toString();
			pair.add(new BasicNameValuePair(nombreCampo, valorCampo));
		}

		httppost.setEntity((HttpEntity) new UrlEncodedFormEntity(pair));

		httpclient.execute(httppost, Utilerias.db.HTTP_CONTEXT); // Se encarga de realizar la inserción
		
		return true;

	} catch (IOException ioex) {
		return false;

	}
}
	
	public static int cantidadFilas(String cadenaDeConsulta){
		JSONArray arrayJson = Utilerias.db.consultar(cadenaDeConsulta);
		if(arrayJson == null){
			System.out.println("ESSSSS NULLLLL");
		}
		JSONObject objetoJson;
		int cantidad = 0;

		try {
			objetoJson = arrayJson.getJSONObject(0);
			String cantidadString = objetoJson.getString("cantidad");
			cantidad = Integer.parseInt(cantidadString);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return cantidad;
	}
	
	public static String[] getListaUsernames(){
		JSONArray usuarios = consultar("usuarios/consultar_usuarios");
		String[] usernames;
		JSONObject objetoJson;
		
		usernames = new String[usuarios.length()];
		
		for (int i = 0; i < usuarios.length(); i++) {
			try {
				objetoJson = usuarios.getJSONObject(i);
				String nombre = objetoJson.getString("username");
				usernames[i] = nombre;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		return usernames;
	}
	
	private static JSONArray consultar(String cadenaDeConsulta) {
		String s = "";
		JSONArray array = null;
		String url = Utilerias.servidor + cadenaDeConsulta;
		System.out.println(url);

		try {
			HttpClient httpClient = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost(url);
			HttpResponse response = httpClient.execute(httpPost, Utilerias.db.HTTP_CONTEXT);

			String line = "";
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			while ((line = rd.readLine()) != null) {
				s += line;
			}
			
			System.out.println(s);

		} catch (Exception e) {
			return array;
		}

		try {
			array = new JSONArray(s);
			return array;
		} catch (Exception e) {
			return array;
		}
		
	}

	
}
