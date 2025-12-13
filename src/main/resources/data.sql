CREATE TABLE IF NOT EXISTS veiculos(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  marca VARCHAR(255),
  modelo VARCHAR(255),
  placa VARCHAR(255),
  ano INT,
  cor VARCHAR(255),
  valor_diaria DECIMAL(10,2)
);

INSERT INTO veiculos (marca, modelo, placa, ano, cor, valor_diaria) VALUES ('BMW', 'X6', 'ABC-123', 2019, 'Preto', 1000.00);