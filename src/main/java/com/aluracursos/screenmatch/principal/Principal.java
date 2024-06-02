package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.models.DataSerie;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.models.Temporadas;
import com.aluracursos.screenmatch.services.APIData;
import com.aluracursos.screenmatch.services.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private APIData consumoApi = new APIData();
    private final String URL_BASE =  "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b622ec3b";
    private ConvertData conversor = new ConvertData();


    public void mostrarMenu(){
        System.out.println("Escribe el nombre de la serie a buscar: ");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(' ', '+')+API_KEY);
        var datos = conversor.obtenerDatos(json, DataSerie.class);
        System.out.println(datos);

        List<Temporadas> temporadas = new ArrayList<>();
        for (int i = 1; i < datos.totalDeTemporadas() ; i++) {
            json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(' ', '+')+"&Season="+i+"&apikey=b622ec3b");

            var datosTemporadas = conversor.obtenerDatos(json, Temporadas.class);
            temporadas.add(datosTemporadas);
        }
        //temporadas.forEach(System.out::println);

        //Mostrar solo titulo de episodios por temporada
        /*for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<Episodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }*/

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir datos a una lista de tipo Episodios

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        // Top 5 episodios
        System.out.println("Top 5 episodios");
        episodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(Episodio::evaluacion).reversed())
                .limit(5).forEach(System.out::println);
    }

}
