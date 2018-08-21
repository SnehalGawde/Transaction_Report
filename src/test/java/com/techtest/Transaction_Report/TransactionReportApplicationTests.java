package com.techtest.Transaction_Report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionReportApplicationTests {

	@InjectMocks
	TransactionReportApplication app = new TransactionReportApplication();

	Path inputFilepath;
	Path outputFilepath = Paths.get("src/test/resources/Output.csv");

	//		Client_Information 1, Product_Information 1, QUANTITY LONG > QUANTITY SHORT
	@Test
	public void testCase1() throws Exception {
		inputFilepath = Paths.get("src/test/resources/InputTestCase1.txt");
		assertEquals("{CL  432100020001={FUSGX NK    20100910=1}}", app.readInputFile(inputFilepath).toString());
	}

	//		Client_Information >1, Product_Information 1
	@Test
	public void testCase2() throws Exception{
		inputFilepath = Paths.get("src/test/resources/InputTestCase2.txt");

		Map<String, Map<String, Long>> mpActual = app.readInputFile(inputFilepath);

		assertEquals(new Long(0), mpActual.get("CL  432122220001").get("FUSGX NK    20100910"));
		assertEquals(new Long(1111111111), mpActual.get("CL  444400020001").get("FUSGX NK    20100910"));
		assertEquals(new Long(0), mpActual.get("CL  432100021111").get("FUSGX NK    20100910"));
		assertEquals(new Long(1111111111), mpActual.get("CL01432100020001").get("FUSGX NK    20100910"));
	}

	//		Client_Information 1, Product_Information >1
	@Test
	public void testCase3() throws Exception{
		inputFilepath = Paths.get("src/test/resources/InputTestCase3.txt");

		Map<String, Long> mpActualOutput = app.readInputFile(inputFilepath).get("CL  432100020001");

		assertEquals(new Long(1234467891), mpActualOutput.get("FUSGX NK    20181230"));
		assertEquals(new Long(0), mpActualOutput.get("FUSGX NK123A20100910"));
		assertEquals(new Long(-1111111111L), mpActualOutput.get("FFSGX NK    20100910"));
		assertEquals(new Long(0), mpActualOutput.get("FUSGX1NK    20100910"));
	}

	//		Client_Information >1, Product_Information >1
	@Test
	public void testCase4() throws Exception{
		inputFilepath = Paths.get("src/test/resources/InputTestCase4.txt");

		Map<String, Map<String, Long>> mpActualOutput = app.readInputFile(inputFilepath);

		Map<String, Long> mpActualOutputCL1 = mpActualOutput.get("CL  444444420001");
		Map<String, Long> mpActualOutputCL2 = mpActualOutput.get("CL  432122220001");

		assertEquals(new Long(123456780), mpActualOutputCL1.get("FUSGX NK123A20100910"));
		assertEquals(new Long(1357924671), mpActualOutputCL2.get("FUSGX1NK    20100910"));
	}

	@Test
	public void testCase5() throws Exception{
		inputFilepath = Paths.get("src/test/resources/InputTestCase4.txt");

		Files.deleteIfExists(outputFilepath);
		app.writeOutputFile(app.readInputFile(inputFilepath), outputFilepath);

		assertTrue(Files.exists(outputFilepath));

		List<String> lstActual = Files.readAllLines(outputFilepath);
		List<String> lstExpected = new ArrayList<>();

		lstExpected.add("Client_Information,Product_Information,Total_Transaction_Amount");
		lstExpected.add("CL  444444420001,FUSGX NK123A20100910,123456780");
		lstExpected.add("CL  432122220001,FUSGX1NK    20100910,1357924671");

		assertEquals(lstActual,lstExpected);
	}

}
