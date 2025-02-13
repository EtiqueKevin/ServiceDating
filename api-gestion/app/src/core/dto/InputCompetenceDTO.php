<?php

namespace gestion\core\dto;

class InputCompetenceDTO extends DTO
{
    public ?string $id;
    public string $name;
    public string $description;

    public function __construct( string $name, string $description){
        $this->id = null;
        $this->name = $name;
        $this->description = $description;
    }

    public function setId(string $id) {
        $this->id=$id;
    }

}