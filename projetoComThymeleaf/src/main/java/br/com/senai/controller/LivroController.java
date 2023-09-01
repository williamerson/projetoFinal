package br.com.senai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.senai.model.Livro;
import br.com.senai.repository.LivroRepository;

@Controller
public class LivroController {

	@Autowired
	private LivroRepository livroRepository;

	@GetMapping("/")
	public String paginaprincipal() {
		return "index";
	}
	
	@GetMapping("/adm")
	public String paginaAdm(Model model) {
		List<Livro> livros = livroRepository.findAll();
		model.addAttribute("livros", livros);
		return "adm";
	}

	@GetMapping("/livro")
	public String listarLivros(Model model) {
		List<Livro> livros = livroRepository.findAll();
		model.addAttribute("livros", livros);
		return "livros";

	}

	@GetMapping("/cadastrarLivro")
	public String paginaAdicionarLivro(Livro livro) {
		return "adicionar_Livro";
	}

	@PostMapping("/adicionarLivro")
	public String adicionaLivro(@Validated Livro livro, Errors erros, BindingResult result, Model model) {
		if (result.hasErrors() || (null != erros && erros.getErrorCount() > 0)) {
			return "adicionar_Livro";
		}
		livroRepository.save(livro);
		return "redirect:/livro";
	}

	@GetMapping("/editar/{id}")
	public String paginaAtualizarLivro(@PathVariable("id") long id, Model model) {

		Livro livro = livroRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("identificador do livro é inválido" + id));

		model.addAttribute("livro", livro);
		return "editar_Livro";
	}

	@PostMapping("/atualizar/{id}")
	public String atualizarLivro(@PathVariable("id") long id, @Valid Livro livro, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			livro.setId(id);
			return "editar_Livro";
		}
		livroRepository.save(livro);
		return "redirect:/adm";
	}
	@GetMapping("/delete/{id}") 
	public String deletarLivro(@PathVariable("id") long id, Model model) {
		Livro livro = livroRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Identificador do livro inválido"+id));
		livroRepository.delete(livro);
		return "redirect:/livro";
		
	}
}
