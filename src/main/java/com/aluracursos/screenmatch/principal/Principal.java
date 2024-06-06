package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.models.DataSerie;
import com.aluracursos.screenmatch.models.DatosEpisodio;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.models.Temporadas;
import com.aluracursos.screenmatch.services.APIData;
import com.aluracursos.screenmatch.services.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        // Top 5 episodios
       /* System.out.println("Top 5 episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primer filtro debug: " + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5).forEach(System.out::println);*/

        //Convirtiendo datos a una lista tipo Episodio
        List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d))).collect(Collectors.toList());

        //Busqueda de episodios por año
        /*System.out.println("Indique la fecha a buscar: ");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream().filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episodio: " + e.getNumeroEpisodio() +
                                " Fecha de Lanzamiento: " + e.getFechaDeLanzamiento().format(dtf)
                )); */

        //Buscar episodios por titulo
        System.out.println("Escriba el título del episodio: ");
        var nombreTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(nombreTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()) {
            System.out.println(episodioBuscado);
        }else {
            System.out.println("Episodio no encontrado");
        }
    }

}
