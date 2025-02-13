<?php

namespace gestion\core\dto;

use gestion\core\domain\entities\Competence;

class CompetenceDTO extends DTO
{
    private string $id;
    private string $nom;
    private string $description;

    public function __construct(Competence $competence) {
        $this->id = $competence->id;
        $this->nom = $competence->nom;
        $this->description = $competence->description;
    }

    public function jsonSerialize(): array {
        return [
            'id' => $this->id,
            'nom' => $this->nom,
            'description' => $this->description
        ];
    }
}