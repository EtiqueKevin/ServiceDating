<?php

namespace gestion\core\domain\entities;

use gestion\core\dto\DTO;

class InputBesoinDTO extends DTO
{
    private string $client_id;
    private string $competence_id;
    private string $description;

    public function __construct(string $client_id, string $competence_id, string $description)
    {
        $this->client_id = $client_id;
        $this->competence_id = $competence_id;
        $this->description = $description;
    }

}