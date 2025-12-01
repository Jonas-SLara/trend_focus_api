INSERT INTO topico (titulo, descricao, area_conhecimento) VALUES
('Inteligência Artificial', 'Tópicos sobre IA e Aprendizado de Máquina.', 'ENGENHARIAS'),
('Economia Global', 'Tendências e análises de mercado internacional.', 'CIÊNCIAS_SOCIAIS_APLICADAS'),
('Clima e Sustentabilidade', 'Notícias e artigos sobre mudanças climáticas e ESG.', 'CIÊNCIAS_EXATAS_E_DA_TERRA');


INSERT INTO termo_chave (termo, id_topico) VALUES
('Machine Learning', 1),
('Redes Neurais', 1),
('PIB', 2),
('Taxa de Juros', 2),
('Crédito de Carbono', 3);

-- DML: Inserção de Notícias
INSERT INTO noticia (titulo, resumo, url_original, url_imagem, data_publicacao, data_coleta, fonte) VALUES
(
    'Gigantes de Tecnologia Lançam Modelo de IA Aprimorado',
    'Novo modelo focado em processamento de linguagem natural (NLP) promete revolucionar o atendimento ao cliente e a análise de dados.',
    'https://noticiaia.com/modelo-novo',
    'https://www.ufsm.br/app/themes/ufsm/images/brasao.svg',
    '2025-10-25 09:00:00',
    NOW(),
    'Tecnologia Hoje'
),
(
    'Bancos Centrais Avaliam Impacto do Aumento da Taxa de Juros',
    'Análise profunda sobre como as recentes decisões de política monetária afetam a inflação e o crescimento econômico no cenário global.',
    'https://economiaonline.com/taxa-juros',
    'https://www.ufsm.br/app/themes/ufsm/images/brasao.svg',
    '2025-10-26 14:30:00',
    NOW(),
    'Global Finance News'
),
(
    'Empresas Adotam Estratégias de Crédito de Carbono para Neutralidade',
    'O aumento da pressão por ESG leva mais corporações a investir em projetos de compensação e compra de Crédito de Carbono.',
    'https://sustentabilidadeja.com/credito-carbono',
    'https://www.ufsm.br/app/themes/ufsm/images/brasao.svg',
    '2025-10-27 11:45:00',
    NOW(),
    'Planeta Verde'
),
(
    'Estudo Revela Uso de Redes Neurais na Medicina Diagnóstica',
    'Pesquisadores utilizam Redes Neurais complexas para identificar padrões em imagens médicas com precisão superior à humana.',
    'https://cienciadigital.com/redes-neurais-medicina',
    'https://www.ufsm.br/app/themes/ufsm/images/brasao.svg',
    '2025-10-28 08:20:00',
    NOW(),
    'Jornal de Pesquisa'
),
(
    'Projeção do PIB Global Revisada para Baixo Após Conflitos',
    'Especialistas reavaliam as expectativas de crescimento do Produto Interno Bruto (PIB) devido a tensões geopolíticas.',
    'https://economiaonline.com/pib-revisao',
    'https://www.ufsm.br/app/themes/ufsm/images/brasao.svg',
    '2025-10-29 16:10:00',
    NOW(),
    'Global Finance News'
),
(
    'Novo Algoritmo de Machine Learning Otimiza Cadeia de Suprimentos',
    'Um avanço em Machine Learning está ajudando empresas a prever a demanda com maior precisão e reduzir desperdícios.',
    'https://noticiaia.com/machine-learning-supply',
    'https://www.ufsm.br/app/themes/ufsm/images/brasao.svg',
    '2025-10-30 10:55:00',
    NOW(),
    'Tecnologia Hoje'
);

-- DML: Inserção de Relacionamentos Noticia-Termo
-- Notícia 1 (IA Aprimorada) -> Termo: Machine Learning
INSERT INTO noticia_termo (noticia_id, termo_id) VALUES (
    (SELECT id FROM noticia WHERE titulo = 'Gigantes de Tecnologia Lançam Modelo de IA Aprimorado'),
    (SELECT id FROM termo_chave WHERE termo = 'Machine Learning')
);

-- Notícia 2 (Taxa de Juros) -> Termo: Taxa de Juros
INSERT INTO noticia_termo (noticia_id, termo_id) VALUES (
    (SELECT id FROM noticia WHERE titulo = 'Bancos Centrais Avaliam Impacto do Aumento da Taxa de Juros'),
    (SELECT id FROM termo_chave WHERE termo = 'Taxa de Juros')
);

-- Notícia 3 (Crédito de Carbono) -> Termo: Crédito de Carbono
INSERT INTO noticia_termo (noticia_id, termo_id) VALUES (
    (SELECT id FROM noticia WHERE titulo = 'Empresas Adotam Estratégias de Crédito de Carbono para Neutralidade'),
    (SELECT id FROM termo_chave WHERE termo = 'Crédito de Carbono')
);

-- Notícia 4 (Redes Neurais) -> Termo: Redes Neurais
INSERT INTO noticia_termo (noticia_id, termo_id) VALUES (
    (SELECT id FROM noticia WHERE titulo = 'Estudo Revela Uso de Redes Neurais na Medicina Diagnóstica'),
    (SELECT id FROM termo_chave WHERE termo = 'Redes Neurais')
);

-- Notícia 5 (PIB Global) -> Termo: PIB
INSERT INTO noticia_termo (noticia_id, termo_id) VALUES (
    (SELECT id FROM noticia WHERE titulo = 'Projeção do PIB Global Revisada para Baixo Após Conflitos'),
    (SELECT id FROM termo_chave WHERE termo = 'PIB')
);

-- Notícia 6 (Machine Learning) -> Termo: Machine Learning
INSERT INTO noticia_termo (noticia_id, termo_id) VALUES (
    (SELECT id FROM noticia WHERE titulo = 'Novo Algoritmo de Machine Learning Otimiza Cadeia de Suprimentos'),
    (SELECT id FROM termo_chave WHERE termo = 'Machine Learning')
);