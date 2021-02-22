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

CREATE UNIQUE INDEX contact_uuid_type_index ON public.contact (resume_uuid, type);