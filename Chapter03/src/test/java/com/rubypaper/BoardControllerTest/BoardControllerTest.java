package com.rubypaper.BoardControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

// @WebMvcTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
// @TestMethodOrder(OrderAnnotation.class) // 메서드의 순서 지정 가능 => 제일 작은 숫자부터 실행함(음수도 가능)
public class BoardControllerTest {
	/*
	 @Autowired private MockMvc mockMvc;
	 
	 @Test
	 
	 @Order(2)
	 public void testHello() throws Exception {
	 	mockMvc.perform(get("/hello").param("name", "둘리"))
	 		.andExpect(status().isOk()) .andExpect(content().string("Hello, 둘리!"))
	 		.andDo(print()); }
	 	
	 @Test
	 
	 @Order(1)
	 public void testHelloJson2() throws Exception { 
	 ObjectMapper objectMapper = new ObjectMapper(); MvcResult mvcResult =
	 		mockMvc.perform(get("/getBoard").param("seq", "100"))
	 			.andExpect(status().isOk())
	 			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	 			.andExpect(jsonPath("$.writer").value("테스터")) .andDo(print()) .andReturn();
	 String jsonString = mvcResult.getResponse().getContentAsString(); BoardVO
	 board = objectMapper.readValue(jsonString, BoardVO.class);
	 assertEquals(board.getSeq(), 100); }
	 */

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testHello() {
		String result = restTemplate.getForObject("/hello?name=둘리", String.class);
		assertEquals("Hello, 둘리!", result);
	}
}
