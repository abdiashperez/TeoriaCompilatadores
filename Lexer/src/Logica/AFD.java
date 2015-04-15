package Logica;

public class AFD {
	private boolean b=false,e=false, g=false, h=false, k=false,f=false,I=false,j=false;
	
	//Letra minuscula 
	public boolean esLetra(char c){
		if((c >= 97 && c <= 122)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean esLetra_Num(char c){
		if((c >= 97 && c <= 122)||(c >= 48 && c <= 57)||(c == 95)){
			return true;
		}else{
			return false;
		}
	}
	public boolean esNum(char c){
		if((c >= 48 && c <= 57)){
			return true;
		}else{
			return false;
		}
	}
	
	//Operador aritmetico 
	public boolean esOpAritmetico(String str) {
		if(str.equals("%")|| str.equals("/") || str.equals("*") || str.equals("+") || str.equals("-")){
			return true;
		}else{
			return false;
		}
	}
	//Operador Relacional 
	public boolean esOpRelacional(String str) {
		if(str.equals("==")|| str.equals("!=") || str.equals("<") || str.equals(">") || str.equals("<=") || str.equals("=>")){
			return true;
		}else{
			return false;
		}
	}
	
	public void noValidos(){
		b=false;
		g=false;
		h=false;
		k=false;
	}
	
	public boolean strValido(String str){
		int size= str.length();//Tamamio del String
		char[] myChar=str.toCharArray();
		
		for (int i=0; i<size; i++){
			if(esLetra(myChar[i]) && i==0){
				b=true;
			}
			if(esLetra_Num(myChar[i]) && b){
				b=true;
			}
		}
		if(b){
			b=false;
			return true;
		}else{
			return false;
		}
	}
	
	public boolean digValido(String str){
		int size= str.length();
		char[] myChar=str.toCharArray();
		
		for (int i=0; i<size; i++){
			
			if(esLetra(myChar[i])){
				if (str.indexOf('.') > str.indexOf(myChar[i])){
					noValidos();
					i=size-1;
				}
			}
			if(esLetra(myChar[i]) && !(myChar[i]=='e' || myChar[i]=='E')){
				if (str.indexOf('+') > str.indexOf(myChar[i])){
					noValidos();
					i=size-1;
				}
				if (str.indexOf('-') > str.indexOf(myChar[i])){
					noValidos();
					i=size-1;
				}
			}
			//ningun estado
			if((!(esLetra_Num(myChar[i])) && !(myChar[i]=='+' || myChar[i]=='-' || myChar[i]=='.'))){
				noValidos();
				i=size-1;
			}
			if(((!esNum(myChar[i])) && !(myChar[i]=='e' || myChar[i]=='E' || myChar[i]=='.')) && (g||h)){
				noValidos();
				i=size-1;
			}
			//estado G-->G
			if(esNum(myChar[i]) && g){
				g=true;
			}
			if(myChar[i]=='.' && h){
				noValidos();
				i=size-1;
			}
			//estado H-->H
			if(esNum(myChar[i]) && h){
				h=true;
			}
			if((esLetra(myChar[i]) || myChar[i]=='+' || myChar[i]=='-' || myChar[i]=='.' || myChar[i]=='_') && k){
				noValidos();
				i=size-1;
			}
			//estado K-->K
			if(esNum(myChar[i]) && k){
				k=true;
			}
			//estado A-->E
			if(myChar[i]=='.' && i==0){
				e=true;
			}
			//estado A-->F
			if((myChar[i]=='+' || myChar[i]=='-') && i==0){
				f=true;
			}
			//estado A-->G
			if(esNum(myChar[i]) && i==0){
				g=true;
			}
			//estado F-->G
			if(esNum(myChar[i]) && (f && i == 1)){
				g=true;
				f=false;
			}
			//estado F-->E
			if(myChar[i]=='.' && (f && i == 1)){
				e=true;
				f=false;
			}
			//estado E-->H
			if(esNum(myChar[i]) && e){
				h=true;
				e=false;
			}
			//estado G-->H
			if(myChar[i]=='.' && g){
				h=true;
				f=false;
				g=false;
			}
			//estado G-->I
			if((myChar[i]=='e' || myChar[i]=='E') && g){
				I=true;
				g=false;
			}
			//estado H-->I
			if((myChar[i]=='e' || myChar[i]=='E') && h){
				I=true;
				h=false;
			}
			//estado I-->J
			if((myChar[i]=='+' || myChar[i]=='-') && I){
				j=true;
				I=false;
			}
			//estado I-->K
			if(esNum(myChar[i]) && I){
				k=true;
				I=false;
			}
			//estado J-->K
			if(esNum(myChar[i]) && j){
				k=true;
				j=false;
			}
		}
		if(str.indexOf('.') > -1){
			if ((str.indexOf('-') > str.indexOf('.')) || (str.indexOf('+') > str.indexOf('.'))){
				noValidos();
			}
		}
		
		if(g||h||k){
			noValidos();
			return true;
		}else{
			return false;
		}
	}
}
