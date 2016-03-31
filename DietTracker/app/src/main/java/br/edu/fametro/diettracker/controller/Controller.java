package br.edu.fametro.diettracker.controller;

/*
 * Controlador da aplicação
 * */

public class Controller {

    /* Instância única do controlador */
    private static Controller mInstance = new Controller();

    /* Construtor interno do controlador */
    private Controller() {
    }

    /* Método para prover a instância para uma outra classe */
    public static synchronized Controller getInstance() {
        return mInstance;
    }

}
