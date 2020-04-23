package com.pameladilly.minhasfinancas.service;

import java.util.Optional;

import org.aspectj.apache.bcel.util.Repository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pameladilly.minhasfinancas.exception.ErroAutenticacao;
import com.pameladilly.minhasfinancas.exception.RegraNegocioException;
import com.pameladilly.minhasfinancas.model.entity.Usuario;
import com.pameladilly.minhasfinancas.model.repository.UsuarioRepository;
import com.pameladilly.minhasfinancas.service.impl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	
	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;
	
	@Before
	public void setUp() {
		service = new UsuarioServiceImpl(repository);
		
	}
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		String email = "usuario@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		Usuario result = service.autenticar(email, senha);
		
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test(expected = ErroAutenticacao.class)
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		service.autenticar("usuario@email.com", "senha");
		
	}
	
	@Test(expected = ErroAutenticacao.class)
	public void deveLancarErroQuandoSenhaNaoBater() {
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email("usuario@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		service.autenticar("usuario@email.com", "123");
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
