SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `techchallenge`
--
DROP DATABASE IF EXISTS `techchallenge`;
CREATE DATABASE IF NOT EXISTS `techchallenge` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `techchallenge`;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tipo_usuario`
--

DROP TABLE IF EXISTS `tipo_usuario`;
CREATE TABLE `tipo_usuario` (
  `id` bigint(20) NOT NULL,
  `tipo_usuario` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Despejando dados para a tabela `tipo_usuario`
--

INSERT INTO `tipo_usuario` (`id`, `tipo_usuario`) VALUES(1, 'ADMIN');
INSERT INTO `tipo_usuario` (`id`, `tipo_usuario`) VALUES(2, 'CLIENTE');
INSERT INTO `tipo_usuario` (`id`, `tipo_usuario`) VALUES(3, 'DONO');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `email` varchar(150) NOT NULL,
  `login` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `data_ultima_alteracao` datetime NOT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `tipo_usuario` varchar(50) NOT NULL,
  `tipo_usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Despejando dados para a tabela `usuarios`
--

INSERT INTO `usuarios` (`id`, `nome`, `email`, `login`, `senha`, `data_ultima_alteracao`, `endereco`, `tipo_usuario`, `tipo_usuario_id`) VALUES(1, 'João da Silva', 'joao.silva@example.com', 'joaosilva', 'senha123', '2025-12-09', 'Rua A, 100 - Centro', 'ADMIN', 1);
INSERT INTO `usuarios` (`id`, `nome`, `email`, `login`, `senha`, `data_ultima_alteracao`, `endereco`, `tipo_usuario`, `tipo_usuario_id`) VALUES(2, 'Maria Oliveira', 'maria.oliveira@example.com', 'mariaoli', 'senha123', '2025-12-09', 'Av. Brasil, 250', 'DONO', 3);
INSERT INTO `usuarios` (`id`, `nome`, `email`, `login`, `senha`, `data_ultima_alteracao`, `endereco`, `tipo_usuario`, `tipo_usuario_id`) VALUES(3, 'Pedro Souza', 'pedro.souza@example.com', 'pedrosz', 'senha123', '2025-12-09', 'Rua Flores, 80', 'CLIENTE', 2);
INSERT INTO `usuarios` (`id`, `nome`, `email`, `login`, `senha`, `data_ultima_alteracao`, `endereco`, `tipo_usuario`, `tipo_usuario_id`) VALUES(4, 'Ana Costa', 'ana.costa@example.com', 'anacosta', 'senha123', '2025-12-09', 'Rua Azul, 45', 'CLIENTE', 2);
INSERT INTO `usuarios` (`id`, `nome`, `email`, `login`, `senha`, `data_ultima_alteracao`, `endereco`, `tipo_usuario`, `tipo_usuario_id`) VALUES(5, 'Lucas Pereira', 'lucas.pereira@example.com', 'lucasp', 'senha123', '2025-12-09', 'Travessa Verde, 12', 'CLIENTE', 2);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tipo_usuario` (`tipo_usuario`);

--
-- Índices de tabela `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_usuarios_email` (`email`),
  ADD UNIQUE KEY `uk_usuarios_login` (`login`),
  ADD KEY `fk_usuarios_tipo_usuario` (`tipo_usuario_id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuarios_tipo_usuario` FOREIGN KEY (`tipo_usuario_id`) REFERENCES `tipo_usuario` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
