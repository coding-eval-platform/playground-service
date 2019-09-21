-- First refactor the execution_requests_inputs table,
-- setting new names for the table and columns.
ALTER TABLE execution_requests_inputs
    RENAME TO execution_requests_program_arguments;
ALTER TABLE execution_requests_program_arguments
    RENAME COLUMN input TO program_argument;
ALTER TABLE execution_requests_program_arguments
    RENAME COLUMN input_order TO program_argument_order;

-- Then create the stdin table
CREATE TABLE execution_requests_stdin
(
    request_id  BIGINT,
    input       VARCHAR,
    input_order INT,
    FOREIGN KEY (request_id) REFERENCES execution_requests (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Then add the compiler flags columns
ALTER TABLE execution_requests
    ADD COLUMN compiler_flags VARCHAR;


-- Now refactor the results schema.
-- First drop all tables.
DROP TABLE timed_out_execution_result;
DROP TABLE finished_execution_results_outputs;
DROP TABLE finished_execution_results_error_outputs;
DROP TABLE finished_execution_results;
DROP TABLE compile_error_execution_results_errors;
DROP TABLE compile_error_execution_results;
DROP TABLE initialization_error_execution_result;
DROP TABLE unknown_error_execution_result;
DROP TABLE execution_results;


-- And then create new ones.
CREATE TABLE execution_responses
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    exit_code  BIGINT,
    result     VARCHAR,
    request_id BIGINT,
    FOREIGN KEY (request_id) REFERENCES execution_requests (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE execution_response_outputs
(
    result_id    BIGINT,
    output       VARCHAR,
    output_order INT,
    FOREIGN KEY (result_id) REFERENCES execution_responses (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE execution_response_error_outputs
(
    result_id          BIGINT,
    error_output       VARCHAR,
    error_output_order INT,
    FOREIGN KEY (result_id) REFERENCES execution_responses (id) ON DELETE CASCADE ON UPDATE CASCADE
);
