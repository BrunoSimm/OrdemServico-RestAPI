package com.brunosimm.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brunosimm.osworks.api.model.Comentario;
import com.brunosimm.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.brunosimm.osworks.domain.exception.NegocioException;
import com.brunosimm.osworks.domain.model.Cliente;
import com.brunosimm.osworks.domain.model.OrdemServico;
import com.brunosimm.osworks.domain.model.StatusOrdemServico;
import com.brunosimm.osworks.domain.repository.ClienteRepository;
import com.brunosimm.osworks.domain.repository.ComentarioRepository;
import com.brunosimm.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		 
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado."));
				
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		
		OrdemServico ordemServico = buscar(ordemServicoId);
		ordemServico.finalizar();
		ordemServicoRepository.save(ordemServico);
	}

	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario ();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
		
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(()-> new NegocioException("Ordem de serviço não encontrada"));
	}
	
}
