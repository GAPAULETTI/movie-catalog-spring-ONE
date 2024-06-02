package com.aluracursos.screenmatch.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EjemploStream {
    public void muestraEjempo(){
        List<String> nombres = Arrays.asList("Brenda", "Luis", "Maria", "Eric", "Genesys");

       //nombres.stream().sorted().limit(4).forEach(System.out::println);
        nombres.stream().sorted().filter(n -> n.startsWith("B")).map(n -> n.toUpperCase()).forEach(System.out::println);



    }
}
