package br.luahr.topicos1.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.luahr.topicos1.dto.CompraDTO;
import br.luahr.topicos1.dto.PedidoDTO;
import br.luahr.topicos1.dto.PedidoResponseDTO;
import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Pedido;
import br.luahr.topicos1.repository.FlorRepository;
import br.luahr.topicos1.repository.PedidoRepository;
import br.luahr.topicos1.repository.UsuarioRepository;
import jakarta.inject.Inject;

public class PedidoServiceImpl implements PedidoService {

    @Inject 
    FlorRepository florRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PedidoRepository pedidoRepository;


    @Override
    public PedidoResponseDTO insert(PedidoDTO pedidoDto, String login) {
        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDateTime.now());

        // calculo do total do pedido
        Double total = 0.0;
        for (CompraDTO compraDto : pedidoDto.itens()) {
            total += (compraDto.totalCompra() * compraDto.quantidadeProduto());
        }
        pedido.setTotalPedido(total);

        // adicionando os itens do pedido
        pedido.setItens(new ArrayList<Compra>());
        for (CompraDTO compraDto : pedidoDto.itens()) {
            Compra item = new Compra();
            item.setTotalCompra(compraDto.totalCompra());
            item.setQuantidadeProduto(compraDto.quantidadeProduto());
            item.setPedido(pedido);
            Flor flor = florRepository.findById(compraDto.itemFlor());
            item.setItemFlor(flor);

            // atualizado o estoque
            flor.setEstoque(flor.getEstoque() - item.getQuantidadeProduto());

            pedido.getItens().add(item);
        }

        // buscando o usuario pelo login
        pedido.setUsuario(usuarioRepository.findByLogin(login));

        // salvando no banco
        pedidoRepository.persist(pedido);

        // atualizando o estoque
  

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        return PedidoResponseDTO.valueOf(pedidoRepository.findById(id));
    }

    @Override
    public List<PedidoResponseDTO> findByAll() {
        return pedidoRepository.listAll()
                                .stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PedidoResponseDTO> findByAll(String login) {
        return pedidoRepository.listAll()
                                .stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();

    }
    
}
