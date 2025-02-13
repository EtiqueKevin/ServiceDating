<?php

namespace gestion\core\dto;

use gestion\core\domain\entities\Utilisateur;

class InputUtilisateurDTO extends DTO
{
    protected string $id;
    protected string $name;
    protected string $surname;
    protected string $phone;

    public function __construct(string $id, string $name, string $surname, string $phone) {
        $this->id = $id;
        $this->name = $name;
        $this->surname = $surname;
        $this->phone = $phone;
    }

    public function jsonSerialize(): array
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'surname' => $this->surname,
            'phone' => $this->phone
        ];
    }

}