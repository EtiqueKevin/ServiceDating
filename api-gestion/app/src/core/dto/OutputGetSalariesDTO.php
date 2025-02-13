<?php

namespace gestion\core\dto;

class OutputGetSalariesDTO extends DTO
{
    private array $competences;
    private $id;
    private $name;
    private $surname;
    private $phone;

    public function __construct(array $competences, $id, $name, $surname, $phone) {
        $this->competences = $competences;
        $this->id = $id;
        $this->name = $name;
        $this->surname = $surname;
        $this->phone = $phone;
    }

    public function jsonSerialize(): array
    {
        return [
            'competences' => $this->competences,
            'id' => $this->id,
            'name' => $this->name,
            'surname' => $this->surname,
            'phone' => $this->phone
        ];
    }
}