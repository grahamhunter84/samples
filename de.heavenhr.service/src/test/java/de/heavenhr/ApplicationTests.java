package de.heavenhr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import de.heavenhr.service.IApplicationService;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {

	@Autowired
	IApplicationService service;

	@Test
	public void contextLoads() {
	}

}
