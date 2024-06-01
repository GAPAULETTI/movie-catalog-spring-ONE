package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Episodio(
        @JsonAlias("Title")
        String titulo,
        @JsonAlias("Episode")
        Integer numeroDeEpisodio,
        @JsonAlias("imdbRating")
        String evaluacion,
        @JsonAlias("Released")
        String fechaDeLanzamiento) {

}
