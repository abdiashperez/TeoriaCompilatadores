// Analizador Lexico
// Materia: Teoria de Compiladores
// Alumno: Vargas Vazquez Francisco Javier 
// Matricula: 00287381
// Grupo: 571
// Fecha: 09/Abril/2015

package validacionDeTokens;

import java.io.*;
import java.util.ArrayList;

import listaDeTokens.Asignacion;
import listaDeTokens.Comentario;
import listaDeTokens.Entonces;
import listaDeTokens.Escribir;
import listaDeTokens.Fin;
import listaDeTokens.FinDePrograma;
import listaDeTokens.Identificador;
import listaDeTokens.Inicio;
import listaDeTokens.InicioDePrograma;
import listaDeTokens.Leer;
import listaDeTokens.Mientras;
import listaDeTokens.Numero;
import listaDeTokens.OpeAritmetico;
import listaDeTokens.OpeRelacional;
import listaDeTokens.Si;
import listaDeTokens.SiNo;
import listaDeTokens.Tokens;


public class Lexer {
	ArrayList <Tokens> lista = new ArrayList <Tokens> ();
	ArrayList <String> tabla = new ArrayList <String> ();
	String palabraReservada = "entonces escribir fin inicio de programa leer mientras si sino @";
	String operadores = "= + - * / < > == != <= >=";
	BufferedReader bf = null;
	String cadenaDeArchivo = null;
	boolean esLetra,esDigito,esPunto,esIdentificador,esNumero,existe=true;
	
	//Metodo para leer el archivo txt y pasarlo a una cadena (string)
	public String leerArchivo (String direccion) {
		String expresionRegular = null;
		try {
			bf = new BufferedReader (new FileReader(direccion));
			String token = " ";
			String bfRead;
			while((bfRead = bf.readLine()) != null){
				token = token + bfRead;
			}
			expresionRegular = token;
		} 
		catch (Exception e){
			System.out.println("No se encontro archivo");
		}
	 return expresionRegular; 
	}
	
	//Metodo para separar tokens del archivo txt (1 por 1)
	public String[] separarArchivo() { 
		String[] archivoSeparado = null;
		cadenaDeArchivo = leerArchivo("Programa.txt");
		archivoSeparado = cadenaDeArchivo.split(" ");
	    return archivoSeparado;		
	}
	
	//Metodo para crear tabla de tokens y tabla de variables 
	public void crearTablas (){
		String [] token = separarArchivo();

		
		for (int i = 0; i < token.length; i++) {
			
			//Busqueda de palabras reservadas
			if (palabraReservada.contains(token[i])) {
	            if (token[i].equals("entonces")){
	            	Entonces e = new Entonces(token[i]);
	            	lista.add(e);	}
            
	            if (token[i].equals("escribir")){
	            	Escribir es = new Escribir(token[i]);
	            	lista.add(es);	}
            
	            if (token[i].equals("fin")){ 
	            	if(token[i+1].equals("de")) {
	            		if(token[i+2].equals("programa")){
	            			String aux = token[i]+" "+token[i+1]+" "+token[i+2];
	            			FinDePrograma fdp = new FinDePrograma(aux);;
	            			lista.add(fdp);	}	} 
	            	else {
	            		Fin f = new Fin (token[i]);
		                lista.add(f);	}	}
            
	            if (token[i].equals("inicio")){
	            	if (token[i+1].equals("de")){
	            		if(token[i+2].equals("programa")){
	            			String aux = token[i]+" "+token[i+1]+" "+token[i+2];
	            			InicioDePrograma idp = new InicioDePrograma(aux);
	            			lista.add(idp);	}	}
	            	else {
	            		Inicio in = new Inicio(token[i]);
	            		lista.add(in);	}	}
            
	            if (token[i].equals("leer")){
	            	Leer le = new Leer(token[i]);
	            	lista.add(le);	}
	            
	            if (token[i].equals("mientras")){
	            	Mientras m = new Mientras(token[i]);
	            	lista.add(m);	}
	            
	            if (token[i].equals("si")){
	            	Si si = new Si(token[i]);
	            	lista.add(si);	}
	            
	            if (token[i].equals("sino")){
	            	SiNo sn = new SiNo(token[i]);
	            	lista.add(sn);	}
	            
	            if (token[i].equals("@")){
	            	Comentario c = new Comentario(token[i]);
	            	lista.add(c);	} 
			} 
            
			//Busqueda de asignacion y operadores aritmeticos y relacionales
			else if(operadores.contains(token[i])){
				if (token[i].equals("=")){        	
					Asignacion a = new Asignacion(token[i]);
		        	lista.add(a);	}
				
				if (token[i].equals("+")||token[i].equals("-")||token[i].equals("*")||token[i].equals("/")){
					OpeAritmetico opeA = new OpeAritmetico(token[i]);
		        	lista.add(opeA);	}
				
				if (token[i].equals("<")||token[i].equals(">")||token[i].equals("==")||token[i].equals("!=")
						||token[i].equals("<=")||token[i].equals(">=")){
					OpeRelacional opeR = new OpeRelacional(token[i]);
		        	lista.add(opeR);	}
            }
			//Busqueda de variables y numeros
			else {
				char[] palabra = token[i].toCharArray();				
				esLetra = Character.isLetter(palabra[0]);
				esDigito = Character.isDigit(palabra[0]);
				esIdentificador = true;
				esNumero = true;
				existe = true;
				
				if (esLetra == true){
					for(int n = 1; n < palabra.length; n++) {
						if ((palabra[n] >= 65 && palabra[n] <= 90)||(palabra[n] >= 97 && palabra[n] <= 127)||(palabra[n] == 95)) {
							//Vacio							
						}	
						
						else 
							esIdentificador = false;
					}	
				
					if (esIdentificador = true){
							Identificador id = new Identificador(token[i]);
							lista.add(id);
							//Valida tabla de variables
							if (tabla.size() == 0)
								tabla.add(token[i]);
							else {
								for(int j = 0; j<tabla.size(); j++)	{
									if((token[i].equals(tabla.get(j))))	{
										existe = false;
										j = tabla.size();
									}						
								}
								
								if (existe)	
									tabla.add(token[i]);
								
								existe = true;//
							}	
						}
				}
				
				if (esDigito == true){
					for(int n = 1; n < palabra.length; n++){
						if(palabra[n] >= 48 && palabra[n] <= 57){
							//Vacio
						}
						else
							esNumero = false;	
					}
					
					if (esNumero == true){
						Numero nu = new Numero(token[i]);
						lista.add(nu);	
					}
				}
			}
		}
		//Creacion de Lista de Tokens
    	try{
    		PrintWriter pw = new PrintWriter(new FileWriter("Lista de tokens.txt"));
    		for (int j=0; j < lista.size(); j++){
    			pw.println(j +" "+ lista.get(j));
    		}
    		pw.close();
    	}
    	catch(Exception ex) { }
    	//Creacion de tabla de variables
    	try{
    		PrintWriter pw = new PrintWriter(new FileWriter("Tabla de variables.txt"));
    		for (int j=0; j < tabla.size(); j++){
    			pw.println(j +" "+ tabla.get(j));
    		}
    		pw.close();
    	}
    	catch(Exception ex) { }
	}
}
	

