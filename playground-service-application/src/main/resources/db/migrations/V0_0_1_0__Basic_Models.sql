CREATE TABLE execution_requests
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    code     TEXT,
    timeout  BIGINT,
    language VARCHAR
);

CREATE TABLE execution_requests_inputs
(
    request_id  BIGINT,
    input       VARCHAR,
    input_order INT,
    FOREIGN KEY (request_id) REFERENCES execution_requests (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE execution_results
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    request_id BIGINT,
    FOREIGN KEY (request_id) REFERENCES execution_requests (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE timed_out_execution_result
(
    result_id BIGINT NOT NULL,
    PRIMARY KEY (result_id),
    FOREIGN KEY (result_id) REFERENCES execution_results (id)
);

CREATE TABLE finished_execution_results
(
    exit_code BIGINT,
    result_id BIGINT NOT NULL,
    PRIMARY KEY (result_id),
    FOREIGN KEY (result_id) REFERENCES execution_results (id)
);

CREATE TABLE finished_execution_results_outputs
(
    result_id    BIGINT,
    output       VARCHAR,
    output_order INT,
    FOREIGN KEY (result_id) REFERENCES finished_execution_results (result_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE finished_execution_results_error_outputs
(
    result_id          BIGINT,
    error_output       VARCHAR,
    error_output_order INT,
    FOREIGN KEY (result_id) REFERENCES finished_execution_results (result_id) ON DELETE CASCADE ON UPDATE CASCADE
);
