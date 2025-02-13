<?php

namespace gestion\core\dto;

use gestion\core\domain\entities\Utilisateur;

class UtilisateurDTO extends DTO
{
    private string $id;
    private string $name;
    private string $surname;
    private string $phone;

    public function __construct(Utilisateur $utilisateur) {
        $this->id = $utilisateur->id;
        $this->name = $utilisateur->name;
        $this->surname = $utilisateur->surname;
        $this->phone = $utilisateur->phone;
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