package devops.demo.computation_demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class CalculatorTest {
	private Calculator calculator;
	private static int count = 0;
	
	@BeforeAll
	static void beforeAll() {
		System.out.printf("[Before All] Calculator Test suite starting ... by krstone\n\n");
	}
	
	@BeforeEach
	void beforeEachTest(TestInfo testInfo) {
		calculator = new Calculator();
		System.out.printf("[Before Each] Starting Test #%d: %s\n", ++count, testInfo.getDisplayName());
	}
	
	@ParameterizedTest(name = "{0} + {1} = {2}")
	@DisplayName("Add two numbers")
	@MethodSource("provideAddData")
	public void add_twoNumbers(int input1, int input2, int expected) {
		
		int result = calculator.add(input1, input2);
		
		assertEquals(expected, result);
	}
	
	static Stream<Arguments> provideAddData() {
		
		return Stream.of(
				Arguments.of(100, 2, 102),
				Arguments.of(100, -2, 98),
				Arguments.of(-100, 2, -98),
				Arguments.of(-100, -2, -102)
				);
					
	}
	
	@ParameterizedTest(name = "{0} * {1} = {2}")
	@DisplayName("Mutliple two numbers")
	@CsvFileSource(resources = "/data/multiple.csv")
	public void multiple_twoNumbers(int input1, int input2, int expected) {
		
		int result = calculator.multiple(input1, input2);
		
		assertEquals(expected, result);
	}
	
	@ParameterizedTest(name = "{0} - {1} = {2}")
	@DisplayName("Substract two numbers")
	@CsvSource( {
		"100, 2, 98",
		"100, -2, 102",
		"-100, 2, -102",
		"-100, -2, -98"
	})
	public void substract_twoNumbers(int a, int b, int expected) {
		Calculator calculator = new Calculator();
		assertEquals(expected, calculator.substract(a, b));
	}
	
	@Test
	public void divide_byZero() {
		assertThrows(IllegalArgumentException.class, () -> calculator.divide(10, 0));
	}
	
	@AfterEach
	void afterEachTest(TestInfo testInfo) {
		System.out.printf("[After Each] Finished Test #%d: %s\n\n", count, testInfo.getDisplayName());
	}
	
	@AfterAll
	static void afterAll() {
		System.out.printf("\n[After All] completed %d test invocations by krstone \n", count);
	}


}
