CREATE TABLE tb_usuario
(
    id_usuario BIGINT       NOT NULL,
    usuario    VARCHAR(255) NOT NULL,
    senha      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tb_usuario PRIMARY KEY (id_usuario)
);

ALTER TABLE tb_usuario
    ADD CONSTRAINT uc_tb_usuario_usuario UNIQUE (usuario);