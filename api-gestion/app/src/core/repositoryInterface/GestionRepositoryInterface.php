<?php

namespace gestion\core\repositoryInterface;

use gestion\core\domain\entities\Besoin;

use gestion\core\domain\entities\Utilisateur;

interface GestionRepositoryInterface
{
    public function getBesoinsAdmin(): array;
    public function getBesoinsByUser(string $id): array;
    public function creerBesoin(string $id, string $competence_id, string $description): Besoin;

    public function saveUtilisateur(Utilisateur $utilisateur):void;
    public function modifierBesoin(string $id_besoin, string $id_user, string $competence_id, string $description):Besoin;
    public function getSalaries(array $idUsers): array;
    public function getCompetencesBySalarie(string $id): array;
    public function getCompetencesByClient(array $clients): array;
}