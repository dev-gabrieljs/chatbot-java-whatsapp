package com.br.chatbot.service;

import com.br.chatbot.entity.Categoria;
import com.br.chatbot.entity.Cliente;
import com.br.chatbot.entity.Produto;
import com.br.chatbot.repository.CategoriaRepository;
import com.br.chatbot.repository.ClienteRepository;
import com.br.chatbot.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final Map<String, String> userState;
    private final TwillioService twillioService;

    public ChatbotService(ClienteRepository clienteRepository, CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository, Map<String, String> userState, TwillioService twillioService) {
        this.clienteRepository = clienteRepository;
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.userState = userState;
        this.twillioService = twillioService;
    }

    public String processMessage(String from, String message) {
        // Verifica o estado atual do usuário
        if (!userState.containsKey(from)) {
            userState.put(from, "AWAITING_MATRICULA");
            twillioService.sendMessage("Qual sua matrícula para continuarmos?", from);
            return "Qual sua matrícula para continuarmos?";
        }

        String state = userState.get(from);
        String response;

        switch (state) {
            case "AWAITING_MATRICULA" -> response = processMatricula(from, message);
            case "AWAITING_CATEGORY" -> response = processCategory(from, message);
            case "AWAITING_ACTION" -> response = processAction(from, message);
            default -> {
                userState.put(from, "AWAITING_MATRICULA");
                response = "Algo deu errado, vamos começar de novo. Qual sua matrícula?";
            }
        }

        twillioService.sendMessage(response, from);
        return response;
    }


    private String processMatricula(String from, String matricula) {
        Optional<Cliente> clienteOpt = clienteRepository.findByMatricula(matricula);

        if (clienteOpt.isPresent()) {
            userState.put(from, "AWAITING_CATEGORY");
            return "Bem-vindo, " + clienteOpt.get().getNome() + "!\n" +
                    "Qual categoria de produtos você deseja?\n" +
                    "1 - Bebidas\n" +
                    "2 - Alimentos\n" +
                    "3 - Higiene\n" +
                    "4 - Limpeza";
        } else {
            return "Matrícula não encontrada. Por favor, tente novamente.";
        }
    }

    private String processCategory(String from, String categoria) {
        // Mapeia os números para os nomes das categorias
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("1", "Bebidas");
        categoryMap.put("2", "Alimentos");
        categoryMap.put("3", "Higiene");
        categoryMap.put("4", "Limpeza");

        // Converte a escolha do usuário para o nome da categoria correspondente
        if (categoryMap.containsKey(categoria)) {
            categoria = categoryMap.get(categoria);
        }

        // Busca a categoria correspondente no banco de dados
        String finalCategoria = categoria;
        Optional<Categoria> categoriaOpt = categoriaRepository.findAll().stream()
                .filter(c -> c.getNomeCategoria().equalsIgnoreCase(finalCategoria))
                .findFirst();

        if (categoriaOpt.isPresent()) {
            // Busca os produtos da categoria encontrada
            List<Produto> produtos = produtoRepository.findByCategoria_Id(categoriaOpt.get().getId());

            // Formata a lista de produtos para exibição
            String produtosListados = produtos.stream()
                    .map(p -> p.getNomeProduto() + " - R$ " + p.getPreco())
                    .collect(Collectors.joining("\n"));

            // Adiciona a opção de voltar ao final da lista de produtos
            produtosListados += "\n\nDigite 'Voltar' para escolher outra categoria.";
            userState.put(from, "AWAITING_ACTION");

            return produtosListados;
        } else {
            // Caso a categoria não seja encontrada
            return "Categoria não encontrada. Por favor, escolha entre Bebidas, Alimentos, Higiene ou Limpeza.";
        }
    }

    private String processAction(String from, String action) {
        if ("Voltar".equalsIgnoreCase(action)) {
            userState.put(from, "AWAITING_CATEGORY");
            return "Qual categoria de produtos você deseja?\n1 - Bebidas\n2 - Alimentos\n3 - Higiene\n4 - Limpeza";
        } else {
            return "Opção inválida. Digite 'Voltar' para escolher outra categoria ou escolha um produto.";
        }
    }
}
