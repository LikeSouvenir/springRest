INSERT INTO history ( operator, a, b, result, date) VALUES
('***', 11, -1.2, 999, '2017-03-14'),
('-?-', 11.111111121, -1.2, 000, '2017-03-16');

-- CREATE TABLE IF NOT EXISTS history (
--    id SERIAL PRIMARY KEY,
--    operator VARCHAR(20),
--    a FLOAT DEFAULT 0,
--    b FLOAT DEFAULT 0,
--    result FLOAT,
--    date LOCAL DATE