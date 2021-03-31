CREATE DATABASE resume_app
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS resume
(
    uuid CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS contact
(
    id SERIAL,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type TEXT NOT NULL,
    value TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS section
(
  id SERIAL,
  resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type TEXT NOT NULL,
  value TEXT NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index ON public.contact (resume_uuid, type);
CREATE UNIQUE INDEX section_uuid_type_index ON public.section (resume_uuid, type);


INSERT INTO resume (uuid, full_name) VALUES ('f4c7ce75-95de-42b7-a707-f07d6e81254f', 'vasya pupkin');
INSERT INTO resume (uuid, full_name) VALUES ('368e354a-9d10-4788-8805-2ecece812224', 'vladimir pukin');
INSERT INTO resume (uuid, full_name) VALUES ('5d71b2c5-28cc-4fd5-ba5e-111407b5ace3', 'nurs ebatb');