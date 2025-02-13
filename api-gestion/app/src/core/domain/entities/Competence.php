<?php

namespace gestion\core\domain\entities;

use gestion\core\dto\CompetenceDTO;

class Competence extends Entity
{
    private string $nom;
    private string $description;

    public function __construct(string $nom, string $description)
    {
        $this->nom = $nom;
        $this->description = $description;
    }

    public function getNom(): string
    {
        return $this->nom;
    }

    public function getDescription(): string
    {
        return $this->description;
    }

    public function toDTO(): CompetenceDTO
    {
        return new CompetenceDTO($this);
    }
}