package br.com.muxi.equipments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TerminalRepositoryTest {
	
	 @Autowired
	 private TestEntityManager entityManager;
	 
	 @Autowired
	 private TerminalRepository repository;
	 
	 @Test
	 public void findByLogic() {
		 Terminal terminal = new Terminal(1234);
		 terminal.setModel("XX");
		 entityManager.persist(terminal);
		 entityManager.flush();
		 
		 Terminal result = repository.findByLogic(1234);
		 assertNotNull("Couldn´t find the terminal 1234.", result);
		 assertThat(result.getLogic()).isEqualTo(terminal.getLogic());
	 }

}
