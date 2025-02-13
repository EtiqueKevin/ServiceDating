CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "utilisateurs";
CREATE TABLE "public"."utilisateurs" (
    "utilisateur_id" uuid NOT NULL,
    "name" character varying(100) NOT NULL,
    "surname" character varying(100) NOT NULL,
    "phone" character varying(20),
    CONSTRAINT "utilisateurs_id" PRIMARY KEY ("utilisateur_id")
) WITH (oids = false);

DROP TABLE IF EXISTS "competences";
CREATE TABLE "public"."competences" (
    "competence_id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "name" character varying(100) NOT NULL,
    "description" character varying(255) NOT NULL,
    CONSTRAINT "competences_id" PRIMARY KEY ("competence_id")
) WITH (oids = false);

DROP TABLE IF EXISTS "salaries_competences";
CREATE TABLE "public"."salaries_competences" (
    "id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "salarie_id" uuid NOT NULL,
    "competence_id" uuid NOT NULL,
    "interest" smallint NOT NULL,
    CONSTRAINT "salaries_competences_salarie_id" FOREIGN KEY ("salarie_id") REFERENCES "utilisateurs"("utilisateur_id"),
    CONSTRAINT "salaries_competences_competence_id" FOREIGN KEY ("competence_id") REFERENCES "competences"("competence_id"),
    CONSTRAINT "salaries_competences_id" PRIMARY KEY ("id")
) WITH (oids = false);

DROP TABLE IF EXISTS "besoins";
CREATE TABLE "public"."besoins" (
    "besoin_id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "client_id" uuid NOT NULL,
    "competence_id" uuid NOT NULL,
    "description" character varying(255) NOT NULL,
    "status" smallint DEFAULT 0 NOT NULL,
    "date_init_besoin" date DEFAULT CURRENT_DATE,
    CONSTRAINT "besoins_client_id" FOREIGN KEY ("client_id") REFERENCES "utilisateurs"("utilisateur_id"),
    CONSTRAINT "besoins_competence_id" FOREIGN KEY ("competence_id") REFERENCES "competences"("competence_id"),
    CONSTRAINT "besoins_id" PRIMARY KEY ("besoin_id")
) WITH (oids = false);

DROP TABLE IF EXISTS "attributions";
CREATE TABLE "public"."attributions" (
    "attribution_id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "besoin_id" uuid NOT NULL,
    "salarie_id" uuid NOT NULL,
    "date_attribution" date DEFAULT CURRENT_DATE,
    CONSTRAINT "attributions_besoin_id" FOREIGN KEY ("besoin_id") REFERENCES "besoins"("besoin_id"),
    CONSTRAINT "attributions_salarie_id" FOREIGN KEY ("salarie_id") REFERENCES "utilisateurs"("utilisateur_id"),
    CONSTRAINT "attributions_id" PRIMARY KEY ("attribution_id")
) WITH (oids = false);

-- INSERT TABLE UTILISATEURS
INSERT INTO "utilisateurs" ("utilisateur_id", "name", "surname", "phone") VALUES 
('d40c9841-48f6-4c67-b1b7-b61610e5c998', 'Etique', 'Kevin', '0606060606'), -- SALARIE
('fb276eac-c8f5-4327-bb09-163421ef1af3', 'Netange', 'Clément', '0606060606'), -- CLIENT
('c157f3e0-bc57-41c4-8f1f-0c626f084695', 'Quilliec', 'Amaury', '0606060606'); -- GESTIONNAIRE

-- INSERT TABLE COMPETENCES
-- 10 compétence dans des domaines différents 
INSERT INTO "competences" ("competence_id", "name", "description") VALUES 
('68295599-af70-4d44-a6e1-60a02909e2ce', 'Plomberie', 'Réparation de fuite d eau'),
('c99c2369-a7d1-4892-ace9-cebce6551601', 'Electricité', 'Installation de prise électrique'),
('aee81d3f-52d4-4448-88ad-1a8482017480', 'Informatique', 'Installation de logiciel'),
('75850cc8-3e13-4b25-9dbe-3b8de2877d95', 'Jardinage', 'Tonte de pelouse'),
('35ee6c56-b824-4a01-a50f-868034fc84a1', 'Peinture', 'Peinture de mur'),
('676e3b90-7638-4163-933d-820b2baf8dca', 'Maçonnerie', 'Construction de mur'),
('5076c7b8-1422-4189-be2b-f0edb7767c70', 'Cuisine', 'Préparation de repas'),
('b4052ecd-803e-4d01-b198-8ba7a2da039c', 'Mécanique', 'Changement de pneu'),
('dfc93c36-b75f-4100-9c52-b03825ac782f', 'Couture', 'Réparation de vêtement'),
('fa8bf38f-4a9e-49c3-b7e7-af65d44f2a39', 'Bricolage', 'Installation de meuble');

-- INSERT TABLE SALARIES_COMPETENCES
-- Kevin à 5 compétences
INSERT INTO "salaries_competences" ("salarie_id", "competence_id", "interest") VALUES 
('d40c9841-48f6-4c67-b1b7-b61610e5c998', '68295599-af70-4d44-a6e1-60a02909e2ce', 1),
('d40c9841-48f6-4c67-b1b7-b61610e5c998', 'c99c2369-a7d1-4892-ace9-cebce6551601', 3),
('d40c9841-48f6-4c67-b1b7-b61610e5c998', 'aee81d3f-52d4-4448-88ad-1a8482017480', 5),
('d40c9841-48f6-4c67-b1b7-b61610e5c998', '75850cc8-3e13-4b25-9dbe-3b8de2877d95', 7),
('d40c9841-48f6-4c67-b1b7-b61610e5c998', '35ee6c56-b824-4a01-a50f-868034fc84a1', 9);

-- INSERT TABLE BESOINS
-- Clément a 2 besoins
INSERT INTO "besoins" ("besoin_id", "client_id", "competence_id", "description") VALUES 
('b0a5ee4e-8c33-4f4e-8258-d817f346e93f','fb276eac-c8f5-4327-bb09-163421ef1af3', '676e3b90-7638-4163-933d-820b2baf8dca', 'Construction de mur dans ma maison'),
('d23da99a-ef66-45d2-8267-f0dd059f6a4c','fb276eac-c8f5-4327-bb09-163421ef1af3', '75850cc8-3e13-4b25-9dbe-3b8de2877d95', 'Tonte de pelouse dans mon jardin');

-- INSERT TABLE ATTRIBUTIONS
-- Kevin a 1 attribution
INSERT INTO "attributions" ("besoin_id", "salarie_id") VALUES 
('b0a5ee4e-8c33-4f4e-8258-d817f346e93f', 'd40c9841-48f6-4c67-b1b7-b61610e5c998');




