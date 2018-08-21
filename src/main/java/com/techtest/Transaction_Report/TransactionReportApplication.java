package com.techtest.Transaction_Report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.nio.file.*;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class TransactionReportApplication implements CommandLineRunner{

    @Value("${InputFile}")
    String strInputFile;

    @Value("${OutputFile}")
    String strOutputFile;

    Path inputFilepath, outputFilepath;

    private static final Logger logger = LoggerFactory.getLogger(TransactionReportApplication.class);

	public static void main(String[] args) {SpringApplication.run(TransactionReportApplication.class, args);}

	@Override
	public void run(String... args){
	    try {
            inputFilepath = Paths.get(strInputFile);
            outputFilepath = Paths.get(strOutputFile);

            Map<String, Map<String, Long>> mpReport = readInputFile(inputFilepath);
            writeOutputFile(mpReport, outputFilepath);

        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
	}

	public Map<String, Map<String, Long>> readInputFile(Path inputFilepath) throws Exception {
	    return Files.lines(inputFilepath)
                .collect(Collectors.groupingBy(line -> line.substring(3, 19),
                        Collectors.groupingBy(line -> line.substring(25, 45),
                                Collectors.summingLong(
                                        line -> Integer.parseInt(line.substring(51, 62).trim()) - Integer.parseInt(line.substring(62, 73).trim())
                                )
                        )
                ));
    }

    public void writeOutputFile(Map<String, Map<String, Long>> mpReport, Path outputFilepath) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Client_Information,Product_Information,Total_Transaction_Amount\n");
        for (String keyClient : mpReport.keySet()) {
            for (String keyProduct: mpReport.get(keyClient).keySet()) {
                sb.append(keyClient+","+keyProduct+","+mpReport.get(keyClient).get(keyProduct)+"\n");
            }
        }

        Files.write(outputFilepath, sb.toString().getBytes());
    }
}
