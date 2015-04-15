package Logica;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Tokens.Asignacion;
import Tokens.Comentarios;
import Tokens.Entonces;
import Tokens.Escribir;
import Tokens.Fin;
import Tokens.FinPrograma;
import Tokens.Identificador;
import Tokens.Inicio;
import Tokens.InicioPrograma;
import Tokens.Leer;
import Tokens.Mientras;
import Tokens.Numero;
import Tokens.OperadorAritmetico;
import Tokens.OperadorRelacional;
import Tokens.Si;
import Tokens.Token;

public class LexerLogic {
	
	private String[] palabraReservada= {"null","inicio","si", "entonces", "fin", "mientras", "leer", "escribir", "=", "@"};
	ArrayList<Token> tokens=new ArrayList<Token>();
	ArrayList<String> palabrasR = new ArrayList<String>();
	ArrayList<String> palabras=new ArrayList<String>();
	AFD myADF= new AFD();
	
	@Override
	public String toString() {
		return "Lexer [tokens=" + tokens + "]";
	}
	

	public void substringText(String myString){
		String temp=myString;
		int fin = 0;
		//Separar en palabras
		while(temp.contains(" ")){
			fin=temp.indexOf(" ");
			palabras.add(temp.substring(0,fin));//Agregar una palabra al substring
			temp=temp.substring(fin + 1, temp.length());//Nuevo tamanio del string 
		} 
		palabras.add(temp);	
		setTokenText();
		
		for (int i = 0; i < tokens.size(); i++) {
			System.out.println(tokens.get(i).getClass());
		}
		
		//Escribir en archivo 
		try{
			PrintWriter pw = new PrintWriter(new FileWriter("tokens.txt"));
			//Acomodar los tokens
			for (int i=0; i < tokens.size(); i++){
				pw.println(i +" -- "  + tokens.get(i));
			}
			pw.close();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Se produjo un error: " + ex);
		}
		
		try{
			PrintWriter pw = new PrintWriter(new FileWriter("variables.txt"));
			//Acomodar los tokens
			for (int i=0; i < palabrasR.size(); i++){
				pw.println(i +" -- "  + palabrasR.get(i));
			}
			pw.close();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Se produjo un error: " + ex);
		}
	}
		
	//Crear Tokens
	public void setTokenText(){
		Token myToken=null;
		boolean flg=true;
		//validar para crear token
		for(int i=0; palabras.size()>i; i++){
			flg=true;
			for (int j = 0; j < palabraReservada.length; j++) {
				//checar si sigue "de" "programa"
				if((palabras.size()-i)-1 >= 2){
					if(palabras.get(i+1).equals("de") && palabras.get(i+2).equals("programa")){
						if(palabras.get(i).equals("inicio")){
							tipoToken(10);
							i+=2;
							flg=false;
						}else if (palabras.get(i).equals("fin")){
							tipoToken(11);
							i+=2;
							flg=false;
						}
					}
				}
				
				//Solo palabras reservadas 
				if(palabras.size() > i){
					if(palabras.get(i).equals(palabraReservada[j])){
						tipoToken(j);
						flg=false;
					}
				}
			}
			if(flg){
				//solo AFD id y #
				if(myADF.strValido(palabras.get(i))){
					myToken = new Identificador(palabras.get(i));
					tokens.add(myToken);
					palabrasR.add(palabras.get(i));
					System.out.println(tokens);
				}else if(myADF.digValido(palabras.get(i))){
					myToken = new Numero(palabras.get(i));
					tokens.add(myToken);
					System.out.println(tokens);
				}else if(myADF.esOpAritmetico(palabras.get(i))){
					myToken = new OperadorAritmetico(palabras.get(i));
					tokens.add(myToken);
					System.out.println(tokens);
				}else if(myADF.esOpRelacional(palabras.get(i))){
					myToken = new OperadorRelacional(palabras.get(i));
					tokens.add(myToken);
					System.out.println(tokens);
				}else{
					System.out.println("str error");
				}
			}
		}
	}
	
	
	//Crea tipo de token y lo agrega
	public void tipoToken(int key) {
		Token myToken=null;
		switch (key) {
		case 1:
			myToken = new Inicio(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 2:
			myToken = new Si(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 3:
			myToken = new Entonces(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 4:
			myToken = new Fin(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 5:
			myToken = new Mientras(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 6:
			myToken = new Leer(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 7:
			myToken = new Escribir(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 8:
			myToken = new Asignacion(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 9:
			myToken = new Comentarios(palabraReservada[key]);
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 10:
			myToken = new InicioPrograma("inicio de programa");
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		case 11:
			myToken = new FinPrograma("fin de programa");
			tokens.add(myToken);
			System.out.println(tokens);
			break;
		default:
			System.out.println(palabraReservada[key]);
			break;
		}			
	}
}
