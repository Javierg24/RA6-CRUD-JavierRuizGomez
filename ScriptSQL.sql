-- Crear tabla ASIGNATURAS
CREATE TABLE ASIGNATURAS (
    id_asignatura INT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE RESULTADOS (
    id_resultado INT,
    id_asignatura INT,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_resultado, id_asignatura),
    FOREIGN KEY (id_asignatura) REFERENCES ASIGNATURAS(id_asignatura) ON DELETE CASCADE
);


CREATE TABLE CRITERIOS (
    id_criterio INT,
    id_resultado INT,
    id_asignatura INT,
    porcentaje DECIMAL(5,2) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_criterio, id_resultado, id_asignatura),
    FOREIGN KEY (id_resultado, id_asignatura) REFERENCES RESULTADOS (id_resultado, id_asignatura) ON DELETE CASCADE,
    FOREIGN KEY (id_asignatura) REFERENCES ASIGNATURAS(id_asignatura) ON DELETE CASCADE
);


INSERT INTO ASIGNATURAS (id_asignatura, nombre) 
VALUES 
(1, 'Matemáticas'),
(2, 'Física'),
(3, 'Química');


INSERT INTO RESULTADOS (id_resultado, id_asignatura, nombre)
VALUES 
(1, 1, 'RA1'),
(2, 1, 'RA2'),
(3, 2, 'RA3'),
(4, 2, 'RA4'),
(5, 3, 'RA5');


INSERT INTO CRITERIOS (id_criterio, id_resultado, id_asignatura, porcentaje, nombre)
VALUES 
(1, 1, 1, 80.00, 'Ejercicio práctico'),
(2, 1, 1, 20.00, 'Examen final'),
(3, 2, 1, 50.00, 'Ejercicio práctico'),
(4, 3, 2, 70.00, 'Ejercicio práctico'),
(5, 4, 2, 30.00, 'Examen final'),
(6, 5, 3, 60.00, 'Ejercicio práctico');

DELIMITER $$

CREATE TRIGGER before_delete_asignatura
BEFORE DELETE ON ASIGNATURAS
FOR EACH ROW
BEGIN
    -- Eliminar primero los criterios asociados a la asignatura
    DELETE FROM CRITERIOS WHERE id_asignatura = OLD.id_asignatura;

    -- Luego eliminar los resultados asociados a la asignatura
    DELETE FROM RESULTADOS WHERE id_asignatura = OLD.id_asignatura;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER eliminar_criterio_al_borrar_resultado
AFTER DELETE ON RESULTADOS
FOR EACH ROW
BEGIN
    DELETE FROM CRITERIOS WHERE id_resultado = OLD.id_resultado AND id_asignatura = OLD.id_asignatura;
END $$

DELIMITER ;


drop table criterios;

drop table resultados;

drop table asignaturas;