<?php

namespace gestion\core\repositoryInterface;

use gestion\core\domain\entities\Utilisateur;

interface GestionRepositoryInterface
{
    public function getBesoinsAdmin(): array;
    public function getBesoinsByUser(string $id): array;

    public function saveUtilisateur(Utilisateur $utilisateur):void;
}