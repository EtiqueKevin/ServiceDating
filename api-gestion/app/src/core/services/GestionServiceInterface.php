<?php

namespace gestion\core\services;

use gestion\core\dto\AuthUserDTO;
use gestion\core\dto\InputCompetenceSalarie;
use gestion\core\dto\InputUtilisateurDTO;

interface GestionServiceInterface
{
    public function getBesoinsAdmin(): array;
    public function getBesoinsByUser(string $id): array;
    public function createUtilisateur(InputUtilisateurDTO $iud):void;
    public function createAuth(AuthUserDTO $auth):string;
    public function associationSalarieCompetence(InputCompetenceSalarie $inputCompetenceSalarie):void;
}