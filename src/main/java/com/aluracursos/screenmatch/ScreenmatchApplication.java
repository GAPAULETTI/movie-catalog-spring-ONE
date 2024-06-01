package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.models.DataSerie;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.models.Temporadas;
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
		var APIData = new APIData();
		var json = APIData.obtenerDatos("http://www.omdbapi.com/?t=game+of+thrones&apikey=3558db");
		System.out.println(json);

		ConvertData conversor = new ConvertData();
		var datos = conversor.obtenerDatos(json, DataSerie.class);
		System.out.println(datos);

		json = APIData.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=b622ec3b");
		Episodio episodio = conversor.obtenerDatos(json, Episodio.class);
		System.out.println(episodio);

		List<Temporadas> temporadas = new ArrayList<>();
		for (int i = 1; i < datos.totalDeTemporadas() ; i++) {
			json = APIData.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season="+i+"&apikey=b622ec3b");

			var datosTemporadas = conversor.obtenerDatos(json, Temporadas.class);
			temporadas.add(datosTemporadas);
		}
		temporadas.forEach(System.out::println);

	}
}
