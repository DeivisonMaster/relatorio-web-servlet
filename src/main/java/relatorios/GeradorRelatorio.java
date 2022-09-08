package relatorios;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

public class GeradorRelatorio {
	private String nomeArquivo;
	private Map<String, Object> parametros;
	private Connection conexao;
	
	public GeradorRelatorio(String nomeArquivo, Map<String, Object> parametros, Connection conexao) {
		this.nomeArquivo = nomeArquivo;
		this.parametros = parametros;
		this.conexao = conexao;
	}
	
	public void geraRelatorioSaidaPdf(OutputStream saida) {
		try {
//			JasperCompileManager.compileReportToFile(this.nomeArquivo);
			JasperPrint jasperPrint = JasperFillManager.fillReport(this.nomeArquivo, this.parametros, this.conexao);
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(saida));
			SimplePdfReportConfiguration configPdf = new SimplePdfReportConfiguration();
			exporter.setConfiguration(configPdf);
			exporter.exportReport();
			
			this.conexao.close();
		} catch (JRException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
