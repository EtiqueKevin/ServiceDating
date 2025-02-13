<?php

namespace gestion\core\domain\entities;

use gestion\core\dto\UtilisateurDTO;

class Utilisateur extends Entity
{
    protected  string $name;
    protected  string $surname;
    protected string $phone;

    public function __construct(string $name, string $surname, string $phone){
        $this->name = $name;
        $this->surname = $surname;
        $this->phone = $phone;
    }

    public function toDTO(): UtilisateurDTO
    {
        return new UtilisateurDTO($this);
    }

}