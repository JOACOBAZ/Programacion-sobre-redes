package ar.edu.et32.flujoDatos;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class lecturas {

	BufferedReader lector;
	
	public lecturas() {
		//Buffered(almacenamiento) ->   reader (es algo que une) -> consola(canal)
		lector = new BufferedReader( new InputStreamReader( System.in ) );
	}

	public BufferedReader getLector() {
		return lector;
	}
	
}


