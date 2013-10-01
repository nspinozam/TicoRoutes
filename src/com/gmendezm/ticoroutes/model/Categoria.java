package com.gmendezm.ticoroutes.model;

public class Categoria {
	int id_categoria;
	String nombre_categoria;
	int estado_categoria;
	
	public void setEstado_categoria(int estado_categoria) {
			this.estado_categoria = estado_categoria;
		}
	
	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}
	
	public void setNombre_categoria(String nombre_categoria) {
		this.nombre_categoria = nombre_categoria;
	}
	
	public int getEstado_categoria() {
		return estado_categoria;
	}
	
	public int getId_categoria() {
		return id_categoria;
	}
	
	public String getNombre_categoria() {
		return nombre_categoria;
	}
	
	public static int cantidadCategorias(){
		int cantidad = Database.cantidadFilas("categorias/contar_categorias");
		return cantidad;
	}
	
	public static boolean insertarCategoria(String nombreCategoria, String estadoCategoria){
		String listaPares[][]  = {{"nombre_categoria",nombreCategoria}, {"estado_categoria",estadoCategoria}};
		String cadenaDeInsercion = "categorias/insertar_categoria";
		return Database.insercion(listaPares, cadenaDeInsercion);
	}
}


