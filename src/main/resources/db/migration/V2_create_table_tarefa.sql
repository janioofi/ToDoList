CREATE TABLE tb_tarefa
(
    id_tarefa        BIGINT       NOT NULL,
    descricao        VARCHAR(255) NOT NULL,
    status           VARCHAR(255) NOT NULL,
    data_criacao     TIMESTAMP,
    data_atualizacao TIMESTAMP,
    id_usuario       BIGINT,
    CONSTRAINT pk_tb_tarefa PRIMARY KEY (id_tarefa)
);

ALTER TABLE tb_tarefa
    ADD CONSTRAINT FK_TB_TAREFA_ON_ID_USUARIO FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario);