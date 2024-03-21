ALTER TABLE pacientes ADD ativo TINYINT NOT NULL;
UPDATE pacientes SET ativo = 1;
ALTER TABLE pacientes MODIFY ativo TINYINT NOT NULL;