package app;

import static spark.Spark.*;

import service.ComidaService;

public class Aplicacao {
	
	private static ComidaService comidaService = new ComidaService();
	
    public static void main(String[] args) {
        port(6789);

        post("/comida", (request, response) -> comidaService.add(request, response));

        get("/comida/:id", (request, response) -> comidaService.get(request, response));

        get("/comida/update/:id", (request, response) -> comidaService.update(request, response));

        get("/comida/delete/:id", (request, response) -> comidaService.remove(request, response));

        get("/comida", (request, response) -> comidaService.getAll(request, response));
               
    }
}
