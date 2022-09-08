package relatorios;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import relatorios.util.FabricaConexoes;

/**
 * Servlet implementation class RelatorioServlet
 */
@WebServlet("/gastos")
public class RelatorioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String nome = request.getServletContext().getRealPath("/WEB-INF/relatorio/gastos_mes.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
			Date dataIni = formata.parse(request.getParameter("data_ini"));
			Date dataFim = formata.parse(request.getParameter("data_fim"));
			
			parametros.put("DATA_INI", dataIni);
			parametros.put("DATA_FIM", dataFim);
			
			Connection conexao = new FabricaConexoes().getConnection();
			
			GeradorRelatorio geradorRelatorio = new GeradorRelatorio(nome, parametros, conexao);
			geradorRelatorio.geraRelatorioSaidaPdf(response.getOutputStream());
			
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		
	}

}
