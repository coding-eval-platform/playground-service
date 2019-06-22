CREATE TABLE compile_error_execution_results
(
    result_id BIGINT NOT NULL,
    PRIMARY KEY (result_id),
    FOREIGN KEY (result_id) REFERENCES execution_results (id)
);

CREATE TABLE compile_error_execution_results_errors
(
    result_id   BIGINT,
    error       VARCHAR,
    error_order INT,
    FOREIGN KEY (result_id) REFERENCES compile_error_execution_results (result_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE initialization_error_execution_result
(
    result_id BIGINT NOT NULL,
    PRIMARY KEY (result_id),
    FOREIGN KEY (result_id) REFERENCES execution_results (id)
);

CREATE TABLE unknown_error_execution_result
(
    result_id BIGINT NOT NULL,
    PRIMARY KEY (result_id),
    FOREIGN KEY (result_id) REFERENCES execution_results (id)
);
