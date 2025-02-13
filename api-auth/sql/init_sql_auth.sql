CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "users";
CREATE TABLE "public"."users" (
    "id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "email" character varying(100) NOT NULL,
    "password" character varying(255) NOT NULL,
    "role" int DEFAULT 0 NOT NULL,
    CONSTRAINT "users_email" UNIQUE ("email"),
    CONSTRAINT "users_id" PRIMARY KEY ("id")
) WITH (oids = false);

-- NETANGE Clément (DWM-2)
-- ETIQUE Kevin (DWM-2)
-- QUILLIEC Amaury (DWM-2)
-- BRUSON Paul (DWM-2)
-- CHEKLAT Ahmed Massi (DACS)
-- REVEILLARD Fabio (IL-1)
-- Adrien (IL-2)
-- MELLANO Louka (IL-1)

INSERT INTO "users" ("id", "email", "password", "role") VALUES 
('d40c9841-48f6-4c67-b1b7-b61610e5c998', 'etique.kevin@gmail.com', '$2y$10$aijJ5UE26qf2qBiutLAAwuawK6I3H1.rxSbKm4uXUEVK9JfWts6Mm' , 10),
('fb276eac-c8f5-4327-bb09-163421ef1af3', 'netange.clement@gmail.com', '$2y$10$aijJ5UE26qf2qBiutLAAwuawK6I3H1.rxSbKm4uXUEVK9JfWts6Mm' , 0),
('c157f3e0-bc57-41c4-8f1f-0c626f084695', 'quilliec.amaury@gmail.com', '$2y$10$aijJ5UE26qf2qBiutLAAwuawK6I3H1.rxSbKm4uXUEVK9JfWts6Mm' , 100);
