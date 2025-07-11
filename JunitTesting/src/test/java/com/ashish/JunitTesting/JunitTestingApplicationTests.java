package com.ashish.JunitTesting;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
class JunitTestingApplicationTests {

	@BeforeEach // run before each testnumber
	void setUp(){
		log.info("starting the method, setting up config");
	}

	@AfterEach // run after with each test
	void tearDown(){
		log.info("tearing down the method");
	}

	@BeforeAll
	static void setUpOnce(){
		log.info("setup once");
	}

	@AfterAll
	static void tearDownOnce(){
		log.info("tearing down once");
	}
	@Test
	void testNumberOne() {
		log.info("test one is run");
		int a =5;
		int b=4;
		int result = addTwoNumbers(a,b);

		Assertions.assertEquals(result,9);
//		Assertions.assertThat(result)
//				.isEqualTo(9)
//				.isCloseTo(10, Offset.offset(1));

	}

	@Test
//	@DisplayName("displaytest") // changes the name
//	@Disabled // disabled the name
	void testNumberTwo(){
		log.info("test two is run");

	}
	int addTwoNumbers(int a, int b) {
		return a+b;
	}


}
