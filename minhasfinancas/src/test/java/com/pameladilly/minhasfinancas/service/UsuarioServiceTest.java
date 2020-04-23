package com.pameladilly.minhasfinancas.service;

import org.aspectj.apache.bcel.util.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pameladilly.minhasfinancas.exception.RegraNegocioException;
import com.pameladilly.minhasfinancas.model.entity.Usuario;
import com.pameladilly.minhasfinancas.model.repository.UsuarioRepository;
import com.pameladilly.minhasfinancas.service.impl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	
	UsuarioService service;
	UsuarioRepository repository;
	
	@Before
	public void setUp() {
		repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioServiceImpl(repository);
		
	}
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		service.validarEmail("usuario@email.com");
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		
		service.validarEmail("usuario@email.com");
		
	}
	
	//apenas um comentário qualquer
}
