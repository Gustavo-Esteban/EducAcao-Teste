package org.generation.TesteTeste.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.TesteTeste.model.UserLogin;
import org.generation.TesteTeste.model.Usuario;
import org.generation.TesteTeste.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	//VALIDAÇÃO PARA NÃO PERMITIR CRIAÇÃO DE USUARIO EXISTENTE
	
	//public Usuario CadastrarUsuario(Usuario usuario) {
	
	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {
		
		
		if(repository.findByEmail(usuario.getEmail()).isPresent()) 
			return null;
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder =  encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		//return repository.save(usuario);
		return Optional.of(repository.save(usuario));
	}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByEmail(user.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				user.get().setToken(authHeader);				
				user.get().setNome(usuario.get().getNomeCompleto());
				user.get().setSenha(usuario.get().getSenha());

				return user;

			}
		}
		return null;
	}
}
