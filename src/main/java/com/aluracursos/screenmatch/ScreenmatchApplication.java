package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.models.DataSerie;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.models.Temporadas;
import com.aluracursos.screenmatch.principal.EjemploStream;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.services.APIData;
import com.aluracursos.screenmatch.services.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		/*EjemploStream ejemplo1 = new EjemploStream();
		ejemplo1.muestraEjempo();*/

		Principal principal = new Principal();
		principal.mostrarMenu();



	}
}
