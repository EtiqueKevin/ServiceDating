<?php

namespace gestion\core\dto;

class InputCompetenceSalarie extends DTO
{
    public string $idSalarie;
    public array $tabIdCompetences;

    public function __construct(string $idSalarie, array $tabIdCompetences){
        $this->idSalarie = $idSalarie;
        $this->tabIdCompetences = $tabIdCompetences;
    }

}