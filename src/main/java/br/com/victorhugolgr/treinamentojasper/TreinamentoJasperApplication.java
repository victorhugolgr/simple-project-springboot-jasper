package br.com.victorhugolgr.treinamentojasper;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@SpringBootApplication
@Slf4j
public class TreinamentoJasperApplication implements CommandLineRunner {
	
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(TreinamentoJasperApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 String compileReportToFile = JasperCompileManager.compileReportToFile("/Users/victor.hugo/JaspersoftWorkspace/MyReports/treinamento.jrxml");
		 
		 Map<String, Object> parametros = new HashMap<String, Object>();
		 parametros.put("PRM_TIPO_CATEGORIA", "AEREO");
		 Connection connection = dataSource.getConnection();
		 
		 JasperPrint print = JasperFillManager.fillReport(compileReportToFile, parametros, connection);
		 
		 byte[] exportReportToPdf = JasperExportManager.exportReportToPdf(print);
		 FileUtils.writeByteArrayToFile(new File("veiculos.pdf"), exportReportToPdf);
		 
		 log.info(compileReportToFile);
		 
		 connection.close();
	}

}
