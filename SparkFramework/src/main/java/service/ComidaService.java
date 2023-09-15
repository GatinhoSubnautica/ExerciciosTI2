package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dao.ComidaDAO;
import model.Comida;
import spark.Request;
import spark.Response;

public class ComidaService {

	private ComidaDAO comidaDAO;

	public Object add(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		float preco = Float.parseFloat(request.queryParams("preco"));
		int quantidade = Integer.parseInt(request.queryParams("quantidade"));
		LocalDateTime dataFab = LocalDateTime.parse(request.queryParams("dataFab"));
		LocalDate dataVal = LocalDate.parse(request.queryParams("dataVal"));

		int id = comidaDAO.getMaxId() + 1;

		Comida comida = new Comida(id, descricao, preco, quantidade, dataFab, dataVal);

		comidaDAO.add(comida);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Comida comida = (Comida) comidaDAO.getComida(id);
		
		if (comida != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<produto>\n" + 
            		"\t<id>" + comida.getId() + "</id>\n" +
            		"\t<descricao>" + comida.getDescricao() + "</descricao>\n" +
            		"\t<preco>" + comida.getPreco() + "</preco>\n" +
            		"\t<quantidade>" + comida.getQuant() + "</quantidade>\n" +
            		"\t<fabricacao>" + comida.getDataFab() + "</fabricacao>\n" +
            		"\t<validade>" + comida.getDataVal() + "</validade>\n" +
            		"</produto>\n";
        } else {
            response.status(404); // 404 Not found
            return "Comida " + id + " não encontrada.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Comida comida = (Comida) comidaDAO.getComida(id);

        if (comida != null) {
        	comida.setDescricao(request.queryParams("descricao"));
        	comida.setPreco(Float.parseFloat(request.queryParams("preco")));
        	comida.setQuant(Integer.parseInt(request.queryParams("quantidade")));
        	comida.setDataFab(LocalDateTime.parse(request.queryParams("dataFabricacao")));
        	comida.setDataVal(LocalDate.parse(request.queryParams("dataValidade")));

        	comidaDAO.atualizarComida(comida);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Comida não encontrada.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Comida comida = (Comida) comidaDAO.getComida(id);

        if (comida != null) {

        	comidaDAO.remove(comida);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Comida não encontrada.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<comida type=\"array\">");
		for (Comida comida : comidaDAO.getAll()) {
			returnValue.append("\n<comida>\n" + 
            		"\t<id>" + comida.getId() + "</id>\n" +
            		"\t<descricao>" + comida.getDescricao() + "</descricao>\n" +
            		"\t<preco>" + comida.getPreco() + "</preco>\n" +
            		"\t<quantidade>" + comida.getQuant() + "</quantidade>\n" +
            		"\t<fabricacao>" + comida.getDataFab() + "</fabricacao>\n" +
            		"\t<validade>" + comida.getDataVal() + "</validade>\n" +
            		"</comida>\n");
		}
		returnValue.append("</comida>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}