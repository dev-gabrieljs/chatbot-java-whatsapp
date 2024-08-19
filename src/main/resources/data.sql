INSERT INTO tb_clientes (nome, matricula)
SELECT 'Gabriel', '12345'
WHERE NOT EXISTS (SELECT 1 FROM tb_clientes WHERE matricula = '12345');

INSERT INTO tb_categorias (nome_categoria)
SELECT 'Bebidas'
WHERE NOT EXISTS (SELECT 1 FROM tb_categorias WHERE nome_categoria = 'Bebidas');

INSERT INTO tb_categorias (nome_categoria)
SELECT 'Alimentos'
WHERE NOT EXISTS (SELECT 1 FROM tb_categorias WHERE nome_categoria = 'Alimentos');

INSERT INTO tb_categorias (nome_categoria)
SELECT 'Higiene'
WHERE NOT EXISTS (SELECT 1 FROM tb_categorias WHERE nome_categoria = 'Higiene');

INSERT INTO tb_categorias (nome_categoria)
SELECT 'Limpeza'
WHERE NOT EXISTS (SELECT 1 FROM tb_categorias WHERE nome_categoria = 'Limpeza');

INSERT INTO tb_categorias (nome_categoria)
SELECT 'Congelados'
WHERE NOT EXISTS (SELECT 1 FROM tb_categorias WHERE nome_categoria = 'Congelados');

-- Categoria: Bebidas

INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Coca-Cola 2L', 7.50, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Bebidas')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Coca-Cola 2L');

INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Suco de Laranja 1L', 5.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Bebidas')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Suco de Laranja 1L');

-- Categoria: Alimentos
INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Arroz 5kg', 18.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Alimentos')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Arroz 5kg');

INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Feijão 1kg', 7.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Alimentos')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Feijão 1kg');

-- Categoria: Higiene
INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Sabonete 90g', 2.50, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Higiene')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Sabonete 90g');

INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Shampoo 300ml', 12.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Higiene')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Shampoo 300ml');

-- Categoria: Limpeza
INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Detergente 500ml', 3.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Limpeza')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Detergente 500ml');

INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Desinfetante 1L', 6.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Limpeza')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Desinfetante 1L');

-- Categoria: Congelados
INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Pizza Congelada', 20.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Congelados')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Pizza Congelada');

INSERT INTO tb_produtos (nome_produto, preco, id_categoria)
SELECT 'Hambúrguer Congelado', 15.00, (SELECT id FROM tb_categorias WHERE nome_categoria = 'Congelados')
WHERE NOT EXISTS (SELECT 1 FROM tb_produtos WHERE nome_produto = 'Hambúrguer Congelado');

